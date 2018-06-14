package com.first.saccelerator.ss.tcpip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommonMethods {

    public static InetAddress ipIntToInet4Address(int ip) {
        byte[] ipAddress = new byte[4];
        writeInt(ipAddress, 0, ip);
        try {
            return Inet4Address.getByAddress(ipAddress);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String ipIntToString(int ip) {
        return String.format("%s.%s.%s.%s", (ip >> 24) & 0x00FF,
                (ip >> 16) & 0x00FF, (ip >> 8) & 0x00FF, ip & 0x00FF);
    }

    public static String ipBytesToString(byte[] ip) {
        return String.format("%s.%s.%s.%s", ip[0] & 0x00FF, ip[1] & 0x00FF, ip[2] & 0x00FF, ip[3] & 0x00FF);
    }

    public static int ipStringToInt(String ip) {
        String[] arrStrings = ip.split("\\.");
        int r = (Integer.parseInt(arrStrings[0]) << 24)
                | (Integer.parseInt(arrStrings[1]) << 16)
                | (Integer.parseInt(arrStrings[2]) << 8)
                | Integer.parseInt(arrStrings[3]);
        return r;
    }

    public static int readInt(byte[] data, int offset) {
        int r = ((data[offset] & 0xFF) << 24)
                | ((data[offset + 1] & 0xFF) << 16)
                | ((data[offset + 2] & 0xFF) << 8) | (data[offset + 3] & 0xFF);
        return r;
    }

    public static short readShort(byte[] data, int offset) {
        int r = ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
        return (short) r;
    }

    public static void writeInt(byte[] data, int offset, int value) {
        data[offset] = (byte) (value >> 24);
        data[offset + 1] = (byte) (value >> 16);
        data[offset + 2] = (byte) (value >> 8);
        data[offset + 3] = (byte) (value);
    }

    public static void writeShort(byte[] data, int offset, short value) {
        data[offset] = (byte) (value >> 8);
        data[offset + 1] = (byte) (value);
    }

    // 网络字节顺序与主机字节顺序的转换

    public static short htons(short u) {
        int r = ((u & 0xFFFF) << 8) | ((u & 0xFFFF) >> 8);
        return (short) r;
    }

    public static short ntohs(short u) {
        int r = ((u & 0xFFFF) << 8) | ((u & 0xFFFF) >> 8);
        return (short) r;
    }

    public static int hton(int u) {
        int r = (u >> 24) & 0x000000FF;
        r |= (u >> 8) & 0x0000FF00;
        r |= (u << 8) & 0x00FF0000;
        r |= (u << 24) & 0xFF000000;
        return r;
    }

    public static int ntoh(int u) {
        int r = (u >> 24) & 0x000000FF;
        r |= (u >> 8) & 0x0000FF00;
        r |= (u << 8) & 0x00FF0000;
        r |= (u << 24) & 0xFF000000;
        return r;
    }

    // 计算校验和
    public static short checksum(long sum, byte[] buf, int offset, int len) {
        sum += getsum(buf, offset, len);
        while ((sum >> 16) > 0)
            sum = (sum & 0xFFFF) + (sum >> 16);
        return (short) ~sum;
    }

    public static long getsum(byte[] buf, int offset, int len) {
        long sum = 0; /* assume 32 bit long, 16 bit short */
        while (len > 1) {
            sum += readShort(buf, offset) & 0xFFFF;
            offset += 2;
            len -= 2;
        }

        if (len > 0) /* take care of left over byte */ {
            sum += (buf[offset] & 0xFF) << 8;
        }
        return sum;
    }

    // 计算IP包的校验和
    public static boolean ComputeIPChecksum(IPHeader ipHeader) {
        short oldCrc = ipHeader.getCrc();
        ipHeader.setCrc((short) 0);// 计算前置零
        short newCrc = CommonMethods.checksum(0, ipHeader.m_Data,
                ipHeader.m_Offset, ipHeader.getHeaderLength());
        ipHeader.setCrc(newCrc);
        return oldCrc == newCrc;
    }

    // 计算TCP或UDP的校验和
    public static boolean ComputeTCPChecksum(IPHeader ipHeader, TCPHeader tcpHeader) {
        ComputeIPChecksum(ipHeader);//计算IP校验和
        int ipData_len = ipHeader.getTotalLength() - ipHeader.getHeaderLength();// IP数据长度
        if (ipData_len < 0)
            return false;
        // 计算为伪首部和
        long sum = getsum(ipHeader.m_Data, ipHeader.m_Offset
                + IPHeader.offset_src_ip, 8);
        sum += ipHeader.getProtocol() & 0xFF;
        sum += ipData_len;

        short oldCrc = tcpHeader.getCrc();
        tcpHeader.setCrc((short) 0);// 计算前置0

        short newCrc = checksum(sum, tcpHeader.m_Data, tcpHeader.m_Offset, ipData_len);// 计算校验和

        tcpHeader.setCrc(newCrc);
        return oldCrc == newCrc;
    }

    // 计算TCP或UDP的校验和
    public static boolean ComputeUDPChecksum(IPHeader ipHeader, UDPHeader udpHeader) {
        ComputeIPChecksum(ipHeader);//计算IP校验和
        int ipData_len = ipHeader.getTotalLength() - ipHeader.getHeaderLength();// IP数据长度
        if (ipData_len < 0)
            return false;
        // 计算为伪首部和
        long sum = getsum(ipHeader.m_Data, ipHeader.m_Offset
                + IPHeader.offset_src_ip, 8);
        sum += ipHeader.getProtocol() & 0xFF;
        sum += ipData_len;

        short oldCrc = udpHeader.getCrc();
        udpHeader.setCrc((short) 0);// 计算前置0

        short newCrc = checksum(sum, udpHeader.m_Data, udpHeader.m_Offset, ipData_len);// 计算校验和

        udpHeader.setCrc(newCrc);
        return oldCrc == newCrc;
    }

    /**
     * 根据掩码位计算掩码
     */
//    public static int getMask(int masks) {
//        return -0x80000000 >> (masks - 1);
//    }
    public static String getMask(int masks) {
        if (masks == 1)
            return "128.0.0.0";
        else if (masks == 2)
            return "192.0.0.0";
        else if (masks == 3)
            return "224.0.0.0";
        else if (masks == 4)
            return "240.0.0.0";
        else if (masks == 5)
            return "248.0.0.0";
        else if (masks == 6)
            return "252.0.0.0";
        else if (masks == 7)
            return "254.0.0.0";
        else if (masks == 8)
            return "255.0.0.0";
        else if (masks == 9)
            return "255.128.0.0";
        else if (masks == 10)
            return "255.192.0.0";
        else if (masks == 11)
            return "255.224.0.0";
        else if (masks == 12)
            return "255.240.0.0";
        else if (masks == 13)
            return "255.248.0.0";
        else if (masks == 14)
            return "255.252.0.0";
        else if (masks == 15)
            return "255.254.0.0";
        else if (masks == 16)
            return "255.255.0.0";
        else if (masks == 17)
            return "255.255.128.0";
        else if (masks == 18)
            return "255.255.192.0";
        else if (masks == 19)
            return "255.255.224.0";
        else if (masks == 20)
            return "255.255.240.0";
        else if (masks == 21)
            return "255.255.248.0";
        else if (masks == 22)
            return "255.255.252.0";
        else if (masks == 23)
            return "255.255.254.0";
        else if (masks == 24)
            return "255.255.255.0";
        else if (masks == 25)
            return "255.255.255.128";
        else if (masks == 26)
            return "255.255.255.192";
        else if (masks == 27)
            return "255.255.255.224";
        else if (masks == 28)
            return "255.255.255.240";
        else if (masks == 29)
            return "255.255.255.248";
        else if (masks == 30)
            return "255.255.255.252";
        else if (masks == 31)
            return "255.255.255.254";
        else if (masks == 32)
            return "255.255.255.255";
        return "";
    }

}
