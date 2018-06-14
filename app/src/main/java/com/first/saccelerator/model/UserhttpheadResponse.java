package com.first.saccelerator.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XQ on 2017/12/15.
 * VPN 翻墙后，http、https等请求中用户信息等
 * 实体类
 */
public class UserhttpheadResponse implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * uid : 1234
         * unm : whatsyourname
         * uip : 123.123.133.13
         * cnty : 中国
         * pvc : 湖北
         * city : 武汉
         * loc : {"type":"point","coordinates":[32332.11,2754.2]}
         * uuid : 98ljkas0-22345lknasd-asdfafdsa
         * sim1 : asassad
         * sim2 : ASFA23525
         * imei1 : 98ljkas0-22345lknasd-asdfafdsa
         * imei2 : 98ljkas0-22345lknasd-asdfafdsa
         * cc : moren
         * cvn : jichu
         * cv : 1.0
         * nte : wifi
         * nto : chinamobile
         * ntb : 4g
         * ct : direct
         * cd : 2017-12-16T15:36:38.654017+08:00
         * sd : 2017-12-16T15:36:38.654018+08:00
         * ttp : https
         * tta : amazon.cn
         * ttpot : 443
         * ttu : https://amazon.cn
         * prni : 2
         * pri : 122.112.112.111
         * dn : gowinder's iphone
         * dm : iphone 8 plus
         * dr : 230x220
         * dos : IOS
         * dosv : 10.3
         * bt : safari
         * bv : 10.2
         * bk : Gecko
         * bkv : 2.2
         */

        private int uid;//用户的账号ID                                          $$$$$
        private String unm;//用户的账号名                                       $$$$$
        private String uip;//用户访问此地址时的IP地址                             $$$$$
        private String cnty;//用户IP的地址解析的所属国家名称                       $$$$$
        private String pvc;//用户IP的地址解析的所属省份名称                        $$$$$
        private String city;//用户IP的地址解析的所属市名称                         $$$$$
        private LocBean loc;//用户GPS位置                                       $$$$$
        private String uuid;//用户访问此地址时设备的唯一标识                        $$$$$
        private String sim1;//用户第一张手机sim卡的卡号                            $$$$$
        private String sim2;//用户第二张手机sim卡的卡号                            $$$$$
        private String imei1;//用户第一张SIM卡的电子串号                           $$$$$
        private String imei2;//用户第二张SIM卡的电子串号                           $$$$$
        private String cc;//用户使用的客户端渠道名称                               $$$$$
        private String cvn;//用户使用的客户端版本名称                              $$$$$
        private String cv;//用户使用的客户端版本号                                 $$$$$
        private String nte;//用户访问此地址时所使用的网络环境,有以下几种,括号里面是说明: wifi, eth(有线), mobile(移动) $$$$$
        private String nto;//用户访问此地址时网络运营商的名称                        $$$$$
        private String ntb;//用户访问此地址时的网络频段                             $$$$$
        private String ct;//代理形式： proxy, direct                             $$$$$
        private String cd;//客户端机器时间；存在用户修改自己设备时间，导致访问时间不准的情况   $$$$$
        private String sd;//此条访问记录提交到后台的时间                             $$$$$
        private String ttp;//后续会持续增加地址类型: http, https, ftp                 $$$$$
        private String tta;//目标网站地址、IP：用户访问网站的域名或者IP                  $$$$$
        private int ttpot;//用户访问网站域名或者IP时所使用的端口                         $$$$$
        private String ttu;//用户访问网站客户端能解析到的详细路径                        $$$$$
        private int prni;//用户通过的代理服务器的node_id                             $$$$$
        private String pri;//用户通过的代理服务器的IP                                $$$$$
        private String dn;//用户自定义的设备名称                                    $$$$$
        private String dm;//用户设备的机型名称                                     $$$$$
        private String dr;//用户的设备分辨率                                       $$$$$
        private String dos;//用户访问此地址时设备的操作系统名称                       $$$$$
        private String dosv;//用户访问此地址时操作系统的版本号                        $$$$$
        private String bt;//用户访问此地址时浏览器的类型名称                          $$$$$
        private String bv;//用户访问此地址时浏览器的版本号                            $$$$$
        private String bk;//用户访问此地址时浏览器所使用的内核名称                     $$$$$
        private String bkv;//用户访问此地址时浏览器所使用内核的版本号                   $$$$$
        private int primaryid;

        public ListBean() {
        }

        public ListBean(int primaryid, String bk, String bkv, String bt, String bv, String cc, String cd, String city, String cnty, String ct, String cv, String cvn, String dm, String dn, String dos, String dosv, String dr, String imei1, String imei2, LocBean loc, String ntb, String nte, String nto, String pri, int prni, String pvc, String sd, String sim1, String sim2, String tta, String ttp, int ttpot, String ttu, int uid, String uip, String unm, String uuid) {
            this.primaryid = primaryid;
            this.bk = bk;
            this.bkv = bkv;
            this.bt = bt;
            this.bv = bv;
            this.cc = cc;
            this.cd = cd;
            this.city = city;
            this.cnty = cnty;
            this.ct = ct;
            this.cv = cv;
            this.cvn = cvn;
            this.dm = dm;
            this.dn = dn;
            this.dos = dos;
            this.dosv = dosv;
            this.dr = dr;
            this.imei1 = imei1;
            this.imei2 = imei2;
            this.loc = loc;
            this.ntb = ntb;
            this.nte = nte;
            this.nto = nto;
            this.pri = pri;
            this.prni = prni;
            this.pvc = pvc;
            this.sd = sd;
            this.sim1 = sim1;
            this.sim2 = sim2;
            this.tta = tta;
            this.ttp = ttp;
            this.ttpot = ttpot;
            this.ttu = ttu;
            this.uid = uid;
            this.uip = uip;
            this.unm = unm;
            this.uuid = uuid;

        }

        public int getPrimaryid() {
            return primaryid;
        }

        public void setPrimaryid(int primaryid) {
            this.primaryid = primaryid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUnm() {
            return unm;
        }

        public void setUnm(String unm) {
            this.unm = unm;
        }

        public String getUip() {
            return uip;
        }

        public void setUip(String uip) {
            this.uip = uip;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getPvc() {
            return pvc;
        }

        public void setPvc(String pvc) {
            this.pvc = pvc;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public LocBean getLoc() {
            return loc;
        }

        public void setLoc(LocBean loc) {
            this.loc = loc;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getSim1() {
            return sim1;
        }

        public void setSim1(String sim1) {
            this.sim1 = sim1;
        }

        public String getSim2() {
            return sim2;
        }

        public void setSim2(String sim2) {
            this.sim2 = sim2;
        }

        public String getImei1() {
            return imei1;
        }

        public void setImei1(String imei1) {
            this.imei1 = imei1;
        }

        public String getImei2() {
            return imei2;
        }

        public void setImei2(String imei2) {
            this.imei2 = imei2;
        }

        public String getCc() {
            return cc;
        }

        public void setCc(String cc) {
            this.cc = cc;
        }

        public String getCvn() {
            return cvn;
        }

        public void setCvn(String cvn) {
            this.cvn = cvn;
        }

        public String getCv() {
            return cv;
        }

        public void setCv(String cv) {
            this.cv = cv;
        }

        public String getNte() {
            return nte;
        }

        public void setNte(String nte) {
            this.nte = nte;
        }

        public String getNto() {
            return nto;
        }

        public void setNto(String nto) {
            this.nto = nto;
        }

        public String getNtb() {
            return ntb;
        }

        public void setNtb(String ntb) {
            this.ntb = ntb;
        }

        public String getCt() {
            return ct;
        }

        public void setCt(String ct) {
            this.ct = ct;
        }

        public String getCd() {
            return cd;
        }

        public void setCd(String cd) {
            this.cd = cd;
        }

        public String getSd() {
            return sd;
        }

        public void setSd(String sd) {
            this.sd = sd;
        }

        public String getTtp() {
            return ttp;
        }

        public void setTtp(String ttp) {
            this.ttp = ttp;
        }

        public String getTta() {
            return tta;
        }

        public void setTta(String tta) {
            this.tta = tta;
        }

        public int getTtpot() {
            return ttpot;
        }

        public void setTtpot(int ttpot) {
            this.ttpot = ttpot;
        }

        public String getTtu() {
            return ttu;
        }

        public void setTtu(String ttu) {
            this.ttu = ttu;
        }

        public int getPrni() {
            return prni;
        }

        public void setPrni(int prni) {
            this.prni = prni;
        }

        public String getPri() {
            return pri;
        }

        public void setPri(String pri) {
            this.pri = pri;
        }

        public String getDn() {
            return dn;
        }

        public void setDn(String dn) {
            this.dn = dn;
        }

        public String getDm() {
            return dm;
        }

        public void setDm(String dm) {
            this.dm = dm;
        }

        public String getDr() {
            return dr;
        }

        public void setDr(String dr) {
            this.dr = dr;
        }

        public String getDos() {
            return dos;
        }

        public void setDos(String dos) {
            this.dos = dos;
        }

        public String getDosv() {
            return dosv;
        }

        public void setDosv(String dosv) {
            this.dosv = dosv;
        }

        public String getBt() {
            return bt;
        }

        public void setBt(String bt) {
            this.bt = bt;
        }

        public String getBv() {
            return bv;
        }

        public void setBv(String bv) {
            this.bv = bv;
        }

        public String getBk() {
            return bk;
        }

        public void setBk(String bk) {
            this.bk = bk;
        }

        public String getBkv() {
            return bkv;
        }

        public void setBkv(String bkv) {
            this.bkv = bkv;
        }

        public static class LocBean implements Serializable {
            /**
             * type : point
             * coordinates : [32332.11,2754.2]
             */

            private String type;
            private List<Double> coordinates;

            public LocBean() {
            }

            public LocBean(List<Double> coordinates, String type) {
                this.coordinates = coordinates;
                this.type = type;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Double> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<Double> coordinates) {
                this.coordinates = coordinates;
            }
        }
    }
}
