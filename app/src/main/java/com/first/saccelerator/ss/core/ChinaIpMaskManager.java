package com.first.saccelerator.ss.core;

import android.util.Log;
import android.util.SparseIntArray;

import com.first.saccelerator.R;
import com.first.saccelerator.ss.tcpip.CommonMethods;
import com.first.saccelerator.utils.FileUtils;
import com.first.saccelerator.utils.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * china ip 管理类    hahaha
 */
public class ChinaIpMaskManager {
    private static final String TAG = "ChinaIpMaskManager";

    static SparseIntArray ChinaIpMaskDict = new SparseIntArray(3000);
    static SparseIntArray MaskDict = new SparseIntArray();

    /**
     * 是否是中国IP
     *
     * @param ip
     * @return
     */
    public static boolean isIPInChina(int ip) {
        boolean found = false;
        for (int i = 0; i < MaskDict.size(); i++) {
            int mask = MaskDict.keyAt(i);
            int networkIP = ip & mask;
            String ipaddress = CommonMethods.ipIntToString(networkIP);
            int mask2 = ChinaIpMaskDict.get(networkIP);
            if (mask2 == mask) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * 加载中国的IP段数据，用于IP分流
     * 加载到ChinaIpMaskDict和ChinaIpMaskDict中
     * <p/>
     * 当VPNService启动后，在run()里调用该方法
     *
     * @param inputStream 读取的是“ipmask”文件
     */
    public static void loadFromFile(InputStream inputStream) {
        int count = 0;
        try {
            byte[] buffer = new byte[4096];
            while ((count = inputStream.read(buffer)) > 0) {
                for (int i = 0; i < count; i += 8) {
                    int ip = CommonMethods.readInt(buffer, i);
                    int mask = CommonMethods.readInt(buffer, i + 4);
                    ChinaIpMaskDict.put(ip, mask);
                    MaskDict.put(mask, mask);
                    Log.i(TAG, String.format("ChinaIpMask %s/%s\n", CommonMethods.ipIntToString(ip), CommonMethods.ipIntToString(mask)));
                }
            }
            inputStream.close();
            Log.i(TAG, String.format("ChinaIpMask records count: %d\n", ChinaIpMaskDict.size()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 读取文件从txt文档中
     * XQ
     * 1.0.2 之后废除
     *
     * @param path
     */
    public static void loadFromPath2(String path) {
        File file = new File(path);
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            loadFromFile(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 加载ip配置表，从文件路径
     * XQ
     *
     * @param path
     */
    public static void loadFromPath(String path) {
        FileInputStream inputStream = null;
        StringBuilder sBuilder = new StringBuilder();
        try {
            inputStream = new FileInputStream(path);
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                sBuilder.append(new String(buffer, 0, count, "UTF-8"));
            }

//            String[] items = sBuilder.toString().split("\\n");
            String[] items = sBuilder.toString().split("\\r\\n");
            for (String s : items) {
                String[] split = s.split("/");
                int ip = CommonMethods.ipStringToInt(split[0]);
                int mask = CommonMethods.ipStringToInt(CommonMethods.getMask(Integer.parseInt(split[1])));
                ChinaIpMaskDict.put(ip, mask);
                MaskDict.put(mask, mask);
                Log.i(TAG, String.format("IpMask %s/%s\n", CommonMethods.ipIntToString(ip), CommonMethods.ipIntToString(mask)));
            }
            Log.i(TAG, String.format("IpMask records count: %d\n", ChinaIpMaskDict.size()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 加载ip配置表，从本地raw中
     * XQ
     */
    public static void loadFromRAW(InputStream inputStream) {
        StringBuilder sBuilder = new StringBuilder();
        try {
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                sBuilder.append(new String(buffer, 0, count, "UTF-8"));
            }
            String[] items = sBuilder.toString().split("\\r\\n");
            for (String s : items) {
                String[] split = s.split("/");
                int ip = CommonMethods.ipStringToInt(split[0]);
                int mask = CommonMethods.ipStringToInt(CommonMethods.getMask(Integer.parseInt(split[1])));
                ChinaIpMaskDict.put(ip, mask);
                MaskDict.put(mask, mask);
                Log.i(TAG, String.format("IpMask %s/%s\n", CommonMethods.ipIntToString(ip), CommonMethods.ipIntToString(mask)));
            }
            Log.i(TAG, String.format("IpMask records count: %d\n", ChinaIpMaskDict.size()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 加载ip段方法
     * XQ
     * 1.0.2之后 使用
     */
    public static void loadIp(String[] items) {
        StringBuilder sBuilder = new StringBuilder();
        try {
            for (int i = 1; i < items.length; i++) {
                if (i == (items.length - 1)) {
                    sBuilder.append(items[i]);
                } else {
                    sBuilder.append(items[i] + " ");
                }
            }
            String[] sbuilderitems = sBuilder.toString().split(" ");
            for (String s : sbuilderitems) {
                String[] split = s.split("/");
                int ip = CommonMethods.ipStringToInt(split[0]);
                int mask = CommonMethods.ipStringToInt(CommonMethods.getMask(Integer.parseInt(split[1])));
                ChinaIpMaskDict.put(ip, mask);
                MaskDict.put(mask, mask);
                Log.i(TAG, String.format("IpMask %s/%s\n", CommonMethods.ipIntToString(ip), CommonMethods.ipIntToString(mask)));
            }
            Log.i(TAG, String.format("IpMask records count: %d\n", ChinaIpMaskDict.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 加载国内ip段
     * XQ
     * 1.0.2 之后 废除
     */
    public static List<String> chianlist;

    public static void loadIPChina(String filepath) {
        if (FileUtils.isFileExists(filepath)) {
            chianlist = FileUtils.readFile2List(filepath, "utf-8");
        }
    }

    public static boolean isChinaIP(String ip) {
        if (chianlist.contains(ip)) {
            return true;
        } else {
            return false;
        }
    }

    public static void loadIPChinaFromRaw() {
        String filepath = Utils.getContext().getExternalFilesDir(null) + File.separator + "china_ip_list.txt";
        InputStream in = Utils.getContext().getResources().openRawResource(R.raw.chinaiplist);
        FileUtils.writeFileFromIS(filepath, in, false);
        try {
            Thread.sleep(1000);
            if (FileUtils.isFileExists(filepath)) {
                chianlist = FileUtils.readFile2List(filepath, "utf-8");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 加载国外ip段
     * XQ
     * 1.0.2 之后 废除
     */
    public static List<String> foreignlist;

    public static void loadIPForeign(String filepath) {
        if (FileUtils.isFileExists(filepath)) {
            foreignlist = FileUtils.readFile2List(filepath, "utf-8");
        }
    }


    public static boolean isforeignIP(String ip) {
        if (foreignlist.contains(ip)) {
            return true;
        } else {
            return false;
        }
    }

    public static void loadIPForeignFromRaw() {
        String filepath = Utils.getContext().getExternalFilesDir(null) + File.separator + "foreign_ip_list2.txt";
        InputStream in = Utils.getContext().getResources().openRawResource(R.raw.foreigniplist);
        FileUtils.writeFileFromIS(filepath, in, false);
        try {
            Thread.sleep(1000);
            if (FileUtils.isFileExists(filepath)) {
                foreignlist = FileUtils.readFile2List(filepath, "utf-8");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
