package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.UserhttpheadResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/12/19.
 * VPN 翻墙后，http、https等请求中用户信息等数据操作处理
 */
public class UserhttpheadDataSource extends BaseDataSource<UserhttpheadResponse.ListBean> {


    /**
     * 表名
     */
    private static final String TABLE_NAME = "user_httphead";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String UID = "uid";//用户的账号ID
        String UNM = "unm";//用户的账号名
        String UIP = "uip";//用户访问此地址时的IP地址
        String CNTY = "cnty";//用户IP的地址解析的所属国家名称
        String PVC = "pvc";//用户IP的地址解析的所属省份名称
        String CITY = "city";//用户IP的地址解析的所属市名称
        String LONGITUDE = "longitude";//经度
        String LATITUDE = "latitude";//纬度
        String TYPE = "type";//定位类型
        String UUID = "uuid";//用户访问此地址时设备的唯一标识
        String SIM1 = "sim1";//用户第一张手机sim卡的卡号
        String SIM2 = "sim2";//用户第二张手机sim卡的卡号
        String IMEI1 = "imei1";//用户第一张imei卡的电子串号
        String IMEI2 = "imei2";//用户第二张imei卡的电子串号
        String CC = "cc";//用户使用的客户端渠道名称
        String CVN = "cvn";//用户使用的客户端版本名称
        String CV = "cv";//用户使用的客户端版本号
        String NTE = "nte";//用户访问此地址时所使用的网络环境,有以下几种,括号里面是说明: wifi, eth(有线), mobile(移动)
        String NTO = "nto";//用户访问此地址时网络运营商的名称
        String NTB = "ntb";//用户访问此地址时的网络频段
        String CT = "ct";//代理形式： proxy, direct
        String CD = "cd";//客户端机器时间；存在用户修改自己设备时间，导致访问时间不准的情况
        String SD = "sd";//此条访问记录提交到后台的时间
        String TTP = "ttp";//后续会持续增加地址类型: http, https, ftp
        String TTA = "tta";//目标网站地址、IP：用户访问网站的域名或者IP
        String TTPOT = "ttpot";//用户访问网站域名或者IP时所使用的端口
        String TTU = "ttu";//用户访问网站客户端能解析到的详细路径
        String PRNI = "prni";//用户通过的代理服务器的node_id
        String PRI = "pri";//用户通过的代理服务器的IP
        String DN = "dn";//用户自定义的设备名称
        String DM = "dm";//用户设备的机型名称
        String DR = "dr";//用户的设备分辨率
        String DOS = "dos";//用户访问此地址时设备的操作系统名称
        String DOSV = "dosv";//用户访问此地址时操作系统的版本号
        String BT = "bt";//用户访问此地址时浏览器的类型名称
        String BV = "bv";//用户访问此地址时浏览器的版本号
        String BK = "bk";//用户访问此地址时浏览器所使用的内核名称
        String BKV = "bkv";//用户访问此地址时浏览器所使用内核的版本号
    }


    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "create table if not exists " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.UID + " VARCHAR(100), "
            + Colum.UNM + " VARCHAR(100), "
            + Colum.UIP + " VARCHAR(100), "
            + Colum.CNTY + " VARCHAR(100), "
            + Colum.PVC + " VARCHAR(100), "
            + Colum.CITY + " VARCHAR(100), "
            + Colum.LONGITUDE + " VARCHAR(100), "
            + Colum.LATITUDE + " VARCHAR(100), "
            + Colum.TYPE + " VARCHAR(100), "
            + Colum.UUID + " VARCHAR(100), "
            + Colum.SIM1 + " VARCHAR(100), "
            + Colum.SIM2 + " VARCHAR(100), "
            + Colum.IMEI1 + " VARCHAR(100), "
            + Colum.IMEI2 + " VARCHAR(100), "
            + Colum.CC + " VARCHAR(100), "
            + Colum.CVN + " VARCHAR(100), "
            + Colum.CV + " VARCHAR(100), "
            + Colum.NTE + " VARCHAR(100), "
            + Colum.NTO + " VARCHAR(100), "
            + Colum.NTB + " VARCHAR(100), "
            + Colum.CT + " VARCHAR(100), "
            + Colum.CD + " VARCHAR(100), "
            + Colum.SD + " VARCHAR(100), "
            + Colum.TTP + " VARCHAR(100), "
            + Colum.TTA + " VARCHAR(100), "
            + Colum.TTPOT + " VARCHAR(100), "
            + Colum.TTU + " VARCHAR(3000), "
            + Colum.PRNI + " VARCHAR(100), "
            + Colum.PRI + " VARCHAR(100), "
            + Colum.DN + " VARCHAR(100), "
            + Colum.DM + " VARCHAR(100), "
            + Colum.DR + " VARCHAR(100), "
            + Colum.DOS + " VARCHAR(100), "
            + Colum.DOSV + " VARCHAR(100), "
            + Colum.BT + " VARCHAR(100), "
            + Colum.BV + " VARCHAR(100), "
            + Colum.BK + " VARCHAR(100), "
            + Colum.BKV + " VARCHAR(100)) ";


    public UserhttpheadDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }


    @Override
    public long insert(UserhttpheadResponse.ListBean listBean) {
        return insert(
                listBean.getUid() + "", listBean.getUnm(), listBean.getUip(),
                listBean.getCnty(), listBean.getPvc(), listBean.getCity(),
                listBean.getLoc().getCoordinates().get(0) + "", listBean.getLoc().getCoordinates().get(1) + "", listBean.getLoc().getType(),
                listBean.getUuid(), listBean.getSim1(), listBean.getSim2(),
                listBean.getImei1(), listBean.getImei2(), listBean.getCc(),
                listBean.getCvn(), listBean.getCv(), listBean.getNte(),
                listBean.getNto(), listBean.getNtb(), listBean.getCt(),
                listBean.getCd(), listBean.getSd(), listBean.getTtp(),
                listBean.getTta(), listBean.getTtpot() + "", listBean.getTtu(),
                listBean.getPrni() + "", listBean.getPri(), listBean.getDn(), listBean.getDm(),
                listBean.getDr(), listBean.getDos(), listBean.getDosv(),
                listBean.getBt(), listBean.getBv(), listBean.getBk(), listBean.getBkv());
    }

    public synchronized long insert(
            String uid, String unm, String uip,
            String cnty, String pvc, String city,
            String aDouble, String aDouble1, String type,
            String uuid, String sim1, String sim2,
            String imei1, String imei2, String cc,
            String cvn, String cv, String nte,
            String nto, String ntb, String ct,
            String cd, String sd, String ttp,
            String tta, String ttpot, String ttu,
            String prni, String pri, String dn, String dm,
            String dr, String dos, String dosv,
            String bt, String bv, String bk, String bkv) {
        long result = 0;
        if (mDb != null) {
            try {
                mDb.beginTransaction();

                ContentValues values = new ContentValues();
                values.put(Colum.UID, uid);
                values.put(Colum.UNM, unm);
                values.put(Colum.UIP, uip);
                values.put(Colum.CNTY, cnty);
                values.put(Colum.PVC, pvc);
                values.put(Colum.CITY, city);
                values.put(Colum.LONGITUDE, aDouble);
                values.put(Colum.LATITUDE, aDouble1);
                values.put(Colum.TYPE, type);
                values.put(Colum.UUID, uuid);
                values.put(Colum.SIM1, sim1);
                values.put(Colum.SIM2, sim2);
                values.put(Colum.IMEI1, imei1);
                values.put(Colum.IMEI2, imei2);
                values.put(Colum.CC, cc);
                values.put(Colum.CVN, cvn);
                values.put(Colum.CV, cv);
                values.put(Colum.NTE, nte);
                values.put(Colum.NTO, nto);
                values.put(Colum.NTB, ntb);
                values.put(Colum.CT, ct);
                values.put(Colum.CD, cd);
                values.put(Colum.SD, sd);
                values.put(Colum.TTP, ttp);
                values.put(Colum.TTA, tta);
                values.put(Colum.TTPOT, ttpot);
                values.put(Colum.TTU, ttu);
                values.put(Colum.PRNI, prni);
                values.put(Colum.PRI, pri);
                values.put(Colum.DN, dn);
                values.put(Colum.DM, dm);
                values.put(Colum.DR, dr);
                values.put(Colum.DOS, dos);
                values.put(Colum.DOSV, dosv);
                values.put(Colum.BT, bt);
                values.put(Colum.BV, bv);
                values.put(Colum.BK, bk);
                values.put(Colum.BKV, bkv);
                result = mDb.insert(TABLE_NAME, null, values);//返回-1就是发生错误了

                mDb.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDb.endTransaction();
            }
        }
        return result;
    }

    public static boolean result = true;

    @Override
    public boolean insertList(List<UserhttpheadResponse.ListBean> beanList) {
        result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (UserhttpheadResponse.ListBean bean : beanList) {
                insert(bean);
            }
            mDb.setTransactionSuccessful();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
            result = true;
        }
        return false;
    }

    @Override
    public long insertOrUpdate(UserhttpheadResponse.ListBean bean) {
        return 0;
    }

    @Override
    public int update(UserhttpheadResponse.ListBean listBean) {
        return 0;
    }

    @Override
    public UserhttpheadResponse.ListBean find(String findId) {
        return null;
    }

    @Override
    public List<UserhttpheadResponse.ListBean> findAll() {
        List<UserhttpheadResponse.ListBean> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{
                "primaryid",
                Colum.UID,
                Colum.UNM,
                Colum.UIP,
                Colum.CNTY,
                Colum.PVC,
                Colum.CITY,
                Colum.LONGITUDE,
                Colum.LATITUDE,
                Colum.TYPE,
                Colum.UUID,
                Colum.SIM1,
                Colum.SIM2,
                Colum.IMEI1,
                Colum.IMEI2,
                Colum.CC,
                Colum.CVN,
                Colum.CV,
                Colum.NTE,
                Colum.NTO,
                Colum.NTB,
                Colum.CT,
                Colum.CD,
                Colum.SD,
                Colum.TTP,
                Colum.TTA,
                Colum.TTPOT,
                Colum.TTU,
                Colum.PRNI,
                Colum.PRI,
                Colum.DN,
                Colum.DM,
                Colum.DR,
                Colum.DOS,
                Colum.DOSV,
                Colum.BT,
                Colum.BV,
                Colum.BK,
                Colum.BKV
        }, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int uid = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Colum.UID)));
            String unm = cursor.getString(cursor.getColumnIndex(Colum.UNM));
            String uip = cursor.getString(cursor.getColumnIndex(Colum.UIP));
            String cnty = cursor.getString(cursor.getColumnIndex(Colum.CNTY));
            String pvc = cursor.getString(cursor.getColumnIndex(Colum.PVC));
            String city = cursor.getString(cursor.getColumnIndex(Colum.CITY));

            double longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Colum.LONGITUDE)));
            double latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Colum.LATITUDE)));
            String type = cursor.getString(cursor.getColumnIndex(Colum.TYPE));
            List<Double> coordinates = new ArrayList<>();
            coordinates.add(longitude);
            coordinates.add(latitude);
            UserhttpheadResponse.ListBean.LocBean locBean = new UserhttpheadResponse.ListBean.LocBean(coordinates, type);

            String uuid = cursor.getString(cursor.getColumnIndex(Colum.UUID));
            String sim1 = cursor.getString(cursor.getColumnIndex(Colum.SIM1));
            String sim2 = cursor.getString(cursor.getColumnIndex(Colum.SIM2));
            String imei1 = cursor.getString(cursor.getColumnIndex(Colum.IMEI1));
            String imei2 = cursor.getString(cursor.getColumnIndex(Colum.IMEI2));
            String cc = cursor.getString(cursor.getColumnIndex(Colum.CC));
            String cvn = cursor.getString(cursor.getColumnIndex(Colum.CVN));
            String cv = cursor.getString(cursor.getColumnIndex(Colum.CV));
            String nte = cursor.getString(cursor.getColumnIndex(Colum.NTE));
            String nto = cursor.getString(cursor.getColumnIndex(Colum.NTO));
            String ntb = cursor.getString(cursor.getColumnIndex(Colum.NTB));
            String ct = cursor.getString(cursor.getColumnIndex(Colum.CT));
            String cd = cursor.getString(cursor.getColumnIndex(Colum.CD));
            String sd = cursor.getString(cursor.getColumnIndex(Colum.SD));
            String ttp = cursor.getString(cursor.getColumnIndex(Colum.TTP));
            String tta = cursor.getString(cursor.getColumnIndex(Colum.TTA));
            int ttpot = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Colum.TTPOT)));
            String ttu = cursor.getString(cursor.getColumnIndex(Colum.TTU));
            int prni = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Colum.PRNI)));
            String pri = cursor.getString(cursor.getColumnIndex(Colum.PRI));
            String dn = cursor.getString(cursor.getColumnIndex(Colum.DN));
            String dm = cursor.getString(cursor.getColumnIndex(Colum.DM));
            String dr = cursor.getString(cursor.getColumnIndex(Colum.DR));
            String dos = cursor.getString(cursor.getColumnIndex(Colum.DOS));
            String dosv = cursor.getString(cursor.getColumnIndex(Colum.DOSV));
            String bt = cursor.getString(cursor.getColumnIndex(Colum.BT));
            String bv = cursor.getString(cursor.getColumnIndex(Colum.BV));
            String bk = cursor.getString(cursor.getColumnIndex(Colum.BK));
            String bkv = cursor.getString(cursor.getColumnIndex(Colum.BKV));
            int primaryid = cursor.getInt(cursor.getColumnIndex("primaryid"));

            result.add(new UserhttpheadResponse.ListBean(
                    primaryid,
                    bk,
                    bkv,
                    bt,
                    bv,
                    cc,
                    cd,
                    city,
                    cnty,
                    ct,
                    cv,
                    cvn,
                    dm,
                    dn,
                    dos,
                    dosv,
                    dr,
                    imei1,
                    imei2,
                    locBean,
                    ntb,
                    nte,
                    nto,
                    pri,
                    prni,
                    pvc,
                    sd,
                    sim1,
                    sim2,
                    tta,
                    ttp,
                    ttpot,
                    ttu,
                    uid,
                    uip,
                    unm,
                    uuid
            ));
        }
        cursor.close();
        return result;
    }

    public List<UserhttpheadResponse.ListBean> findAllFromNumber(String number) {
        Cursor cursor = null;
        List<UserhttpheadResponse.ListBean> result = new ArrayList<>();
        try {
            cursor = mDb.query(TABLE_NAME, new String[]{
                    "primaryid",
                    Colum.UID,
                    Colum.UNM,
                    Colum.UIP,
                    Colum.CNTY,
                    Colum.PVC,
                    Colum.CITY,
                    Colum.LONGITUDE,
                    Colum.LATITUDE,
                    Colum.TYPE,
                    Colum.UUID,
                    Colum.SIM1,
                    Colum.SIM2,
                    Colum.IMEI1,
                    Colum.IMEI2,
                    Colum.CC,
                    Colum.CVN,
                    Colum.CV,
                    Colum.NTE,
                    Colum.NTO,
                    Colum.NTB,
                    Colum.CT,
                    Colum.CD,
                    Colum.SD,
                    Colum.TTP,
                    Colum.TTA,
                    Colum.TTPOT,
                    Colum.TTU,
                    Colum.PRNI,
                    Colum.PRI,
                    Colum.DN,
                    Colum.DM,
                    Colum.DR,
                    Colum.DOS,
                    Colum.DOSV,
                    Colum.BT,
                    Colum.BV,
                    Colum.BK,
                    Colum.BKV
            }, null, null, null, null, null, number);
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    while (cursor.moveToNext()) {
                        int uid = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Colum.UID)));
                        String unm = cursor.getString(cursor.getColumnIndex(Colum.UNM));
                        String uip = cursor.getString(cursor.getColumnIndex(Colum.UIP));
                        String cnty = cursor.getString(cursor.getColumnIndex(Colum.CNTY));
                        String pvc = cursor.getString(cursor.getColumnIndex(Colum.PVC));
                        String city = cursor.getString(cursor.getColumnIndex(Colum.CITY));

                        double longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Colum.LONGITUDE)));
                        double latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Colum.LATITUDE)));
                        String type = cursor.getString(cursor.getColumnIndex(Colum.TYPE));
                        List<Double> coordinates = new ArrayList<>();
                        coordinates.add(longitude);
                        coordinates.add(latitude);
                        UserhttpheadResponse.ListBean.LocBean locBean = new UserhttpheadResponse.ListBean.LocBean(coordinates, type);

                        String uuid = cursor.getString(cursor.getColumnIndex(Colum.UUID));
                        String sim1 = cursor.getString(cursor.getColumnIndex(Colum.SIM1));
                        String sim2 = cursor.getString(cursor.getColumnIndex(Colum.SIM2));
                        String imei1 = cursor.getString(cursor.getColumnIndex(Colum.IMEI1));
                        String imei2 = cursor.getString(cursor.getColumnIndex(Colum.IMEI2));
                        String cc = cursor.getString(cursor.getColumnIndex(Colum.CC));
                        String cvn = cursor.getString(cursor.getColumnIndex(Colum.CVN));
                        String cv = cursor.getString(cursor.getColumnIndex(Colum.CV));
                        String nte = cursor.getString(cursor.getColumnIndex(Colum.NTE));
                        String nto = cursor.getString(cursor.getColumnIndex(Colum.NTO));
                        String ntb = cursor.getString(cursor.getColumnIndex(Colum.NTB));
                        String ct = cursor.getString(cursor.getColumnIndex(Colum.CT));
                        String cd = cursor.getString(cursor.getColumnIndex(Colum.CD));
                        String sd = cursor.getString(cursor.getColumnIndex(Colum.SD));
                        String ttp = cursor.getString(cursor.getColumnIndex(Colum.TTP));
                        String tta = cursor.getString(cursor.getColumnIndex(Colum.TTA));
                        int ttpot = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Colum.TTPOT)));
                        String ttu = cursor.getString(cursor.getColumnIndex(Colum.TTU));
                        int prni = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Colum.PRNI)));
                        String pri = cursor.getString(cursor.getColumnIndex(Colum.PRI));
                        String dn = cursor.getString(cursor.getColumnIndex(Colum.DN));
                        String dm = cursor.getString(cursor.getColumnIndex(Colum.DM));
                        String dr = cursor.getString(cursor.getColumnIndex(Colum.DR));
                        String dos = cursor.getString(cursor.getColumnIndex(Colum.DOS));
                        String dosv = cursor.getString(cursor.getColumnIndex(Colum.DOSV));
                        String bt = cursor.getString(cursor.getColumnIndex(Colum.BT));
                        String bv = cursor.getString(cursor.getColumnIndex(Colum.BV));
                        String bk = cursor.getString(cursor.getColumnIndex(Colum.BK));
                        String bkv = cursor.getString(cursor.getColumnIndex(Colum.BKV));
                        int primaryid = cursor.getInt(cursor.getColumnIndex("primaryid"));


                        result.add(new UserhttpheadResponse.ListBean(
                                primaryid,
                                bk,
                                bkv,
                                bt,
                                bv,
                                cc,
                                cd,
                                city,
                                cnty,
                                ct,
                                cv,
                                cvn,
                                dm,
                                dn,
                                dos,
                                dosv,
                                dr,
                                imei1,
                                imei2,
                                locBean,
                                ntb,
                                nte,
                                nto,
                                pri,
                                prni,
                                pvc,
                                sd,
                                sim1,
                                sim2,
                                tta,
                                ttp,
                                ttpot,
                                ttu,
                                uid,
                                uip,
                                unm,
                                uuid
                        ));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }


    @Override
    public int delete(String deletId) {
        return 0;
    }

    @Override
    public void clear() {
        mDb.delete(TABLE_NAME, null, null);
        updatesequence(TABLE_NAME);
    }

    /**
     * 根据primaryid删除数据
     *
     * @param list
     */
    public void deleteDataFromPrimaryid(List<UserhttpheadResponse.ListBean> list) {
        try {
            mDb.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                mDb.delete(TABLE_NAME, "primaryid = ?", new String[]{list.get(i).getPrimaryid() + ""});
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }
    }

    /**
     * 删除重复数据
     */
    public void deleteDuplicateData() {
        try {
            mDb.beginTransaction();
            mDb.execSQL("delete from user_httphead where user_httphead.primaryid not in (select MAX(user_httphead.primaryid) from user_httphead group by ttu,tta)");
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }
    }


    @Override
    public boolean isExist(String searchId) {
        return false;
    }

    @Override
    public long getCount() {
        deleteDuplicateData();//获得数据库条数的时候，先删除重复数据

        Cursor cursor = null;
        long result = 0;
        try {
            cursor = mDb.rawQuery("select count('primaryid') from " + TABLE_NAME, null);
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    result = cursor.getLong(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }


    private synchronized int updatesequence(String table_name) {
        ContentValues values = new ContentValues();
        values.put("seq", 0);
        int result = mDb.update("sqlite_sequence", values, " name = ? ", new String[]{String.valueOf(table_name)});
        return result;
    }
}
