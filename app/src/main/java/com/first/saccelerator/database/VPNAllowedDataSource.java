package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.VPNAllowedResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2018/5/14.
 * 被选择的应用是允许（禁止）通过vpn   数据
 */

public class VPNAllowedDataSource extends BaseDataSource<VPNAllowedResponse> {


    /**
     * 表名
     */
    private static final String TABLE_NAME = "vpn_allowed";


    /**
     * 表中字段名称
     */
    private interface Colum {
        String NAME = "name";//应用名称
        String PACKAGENAME = "packagename";//应用的包名
        String PACKAGEPATH = "packagepath";//应用的保存位置地址
        String VERSIONNAME = "versionname";//应用的当前版本名称
        String VERSIONCODE = "versioncode";//应用的版本号
        String ISSYSTEM = "issystem";//应用是否是系统应用
        String ISALLOWED = "isallowed";//应用是否被允许通过VPN
    }


    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "create table if not exists " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.NAME + " VARCHAR(100), "
            + Colum.PACKAGENAME + " VARCHAR(100), "
            + Colum.PACKAGEPATH + " VARCHAR(200), "
            + Colum.VERSIONNAME + " VARCHAR(100), "
            + Colum.VERSIONCODE + " VARCHAR(100), "
            + Colum.ISSYSTEM + " INTEGER, "//0表示fales，1表示true
            + Colum.ISALLOWED + " INTEGER) ";//0表示false，1表示true


    public VPNAllowedDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(VPNAllowedResponse vpnAllowedResponse) {
        return insert(vpnAllowedResponse.getName(), vpnAllowedResponse.getPackageName(), vpnAllowedResponse.getPackagePath(), vpnAllowedResponse.getVersionName(),
                vpnAllowedResponse.getVersionCode(), vpnAllowedResponse.getIsSystem(), vpnAllowedResponse.getIsAllowed());
    }

    public synchronized long insert(String name, String packageName, String packagePath, String versionName, String versionCode, int system, int allowed) {
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(Colum.NAME, name);
        values.put(Colum.PACKAGENAME, packageName);
        values.put(Colum.PACKAGEPATH, packagePath);
        values.put(Colum.VERSIONNAME, versionName);
        values.put(Colum.VERSIONCODE, versionCode);
        values.put(Colum.ISSYSTEM, system);
        values.put(Colum.ISALLOWED, allowed);
        result = mDb.insert(TABLE_NAME, null, values);//返回-1就是发生错误了
        return result;
    }

    @Override
    public int update(VPNAllowedResponse vpnAllowedResponse) {
        return update(vpnAllowedResponse.getName(), vpnAllowedResponse.getPackageName(), vpnAllowedResponse.getPackagePath(), vpnAllowedResponse.getVersionName(),
                vpnAllowedResponse.getVersionCode(), vpnAllowedResponse.getIsSystem(), vpnAllowedResponse.getIsAllowed());
    }

    private synchronized int update(String name, String packageName, String packagePath, String versionName, String versionCode, int isSystem, int isAllowed) {
        int result = 0;
        ContentValues values = new ContentValues();
        values.put(Colum.NAME, name);
        values.put(Colum.PACKAGENAME, packageName);
        values.put(Colum.PACKAGEPATH, packagePath);
        values.put(Colum.VERSIONNAME, versionName);
        values.put(Colum.VERSIONCODE, versionCode);
        values.put(Colum.ISSYSTEM, isSystem);
        values.put(Colum.ISALLOWED, isAllowed);
        result = mDb.update(TABLE_NAME, values, Colum.PACKAGENAME + "=?", new String[]{String.valueOf(packageName)});//返回-1就是发生错误了
        return result;
    }


    public static boolean result = true;

    @Override
    public boolean insertList(List<VPNAllowedResponse> list) {
        result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (VPNAllowedResponse bean : list) {
                insertOrUpdate(bean);
            }
            mDb.setTransactionSuccessful();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
            result = true;
            close();
        }
        return result;
    }

    @Override
    public long insertOrUpdate(VPNAllowedResponse vpnAllowedResponse) {
        return insertOrUpdate(vpnAllowedResponse.getName(), vpnAllowedResponse.getPackageName(), vpnAllowedResponse.getPackagePath(), vpnAllowedResponse.getVersionName(),
                vpnAllowedResponse.getVersionCode(), vpnAllowedResponse.getIsSystem(), vpnAllowedResponse.getIsAllowed());
    }

    private long insertOrUpdate(String name, String packageName, String packagePath, String versionName, String versionCode, int isSystem, int isAllowed) {
        // 先判断该数据是否存在，已经存在就更新数据，不存在才插入该数据
        if (isExist(String.valueOf(packageName))) {
            return update(name, packageName, packagePath, versionName, versionCode, isSystem, isAllowed);
        } else {
            return insert(name, packageName, packagePath, versionName, versionCode, isSystem, isAllowed);
        }
    }


    @Override
    public VPNAllowedResponse find(String findId) {
        return null;
    }

    @Override
    public List<VPNAllowedResponse> findAll() {
        List<VPNAllowedResponse> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_NAME, new String[]{
                    "primaryid",
                    Colum.NAME,
                    Colum.PACKAGENAME,
                    Colum.PACKAGEPATH,
                    Colum.VERSIONNAME,
                    Colum.VERSIONCODE,
                    Colum.ISSYSTEM,
                    Colum.ISALLOWED
            }, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int primaryid = cursor.getInt(cursor.getColumnIndex("primaryid"));
                    String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
                    String packagename = cursor.getString(cursor.getColumnIndex(Colum.PACKAGENAME));
                    String packagepath = cursor.getString(cursor.getColumnIndex(Colum.PACKAGEPATH));
                    String versionname = cursor.getString(cursor.getColumnIndex(Colum.VERSIONNAME));
                    String versioncode = cursor.getString(cursor.getColumnIndex(Colum.VERSIONCODE));
                    int issystem = cursor.getInt(cursor.getColumnIndex(Colum.ISSYSTEM));
                    int isallowed = cursor.getInt(cursor.getColumnIndex(Colum.ISALLOWED));
                    list.add(new VPNAllowedResponse(primaryid, name, packagename, packagepath, versionname, versioncode, issystem, isallowed));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 查找允许通过VPN的应用数据
     */
    public List<VPNAllowedResponse> findVPNAllow() {
        List<VPNAllowedResponse> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_NAME, new String[]{
                    "primaryid",
                    Colum.NAME,
                    Colum.PACKAGENAME,
                    Colum.PACKAGEPATH,
                    Colum.VERSIONNAME,
                    Colum.VERSIONCODE,
                    Colum.ISSYSTEM,
                    Colum.ISALLOWED
            }, Colum.ISALLOWED + "=?", new String[]{"1"}, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int primaryid = cursor.getInt(cursor.getColumnIndex("primaryid"));
                    String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
                    String packagename = cursor.getString(cursor.getColumnIndex(Colum.PACKAGENAME));
                    String packagepath = cursor.getString(cursor.getColumnIndex(Colum.PACKAGEPATH));
                    String versionname = cursor.getString(cursor.getColumnIndex(Colum.VERSIONNAME));
                    String versioncode = cursor.getString(cursor.getColumnIndex(Colum.VERSIONCODE));
                    int issystem = cursor.getInt(cursor.getColumnIndex(Colum.ISSYSTEM));
                    int isallowed = cursor.getInt(cursor.getColumnIndex(Colum.ISALLOWED));
                    list.add(new VPNAllowedResponse(primaryid, name, packagename, packagepath, versionname, versioncode, issystem, isallowed));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
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

    @Override
    public boolean isExist(String searchId) {
        boolean result = false;
        Cursor cursor = null;
        try {
            cursor = mDb.rawQuery("select " + Colum.PACKAGENAME + " from " + TABLE_NAME + " where " + Colum.PACKAGENAME + "=?", new String[]{searchId});
            if (cursor != null) {
                result = cursor.moveToNext();
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
    public long getCount() {
        return 0;
    }


    private synchronized int updatesequence(String table_name) {
        ContentValues values = new ContentValues();
        values.put("seq", 0);
        int result = mDb.update("sqlite_sequence", values, " name = ? ", new String[]{String.valueOf(table_name)});
        return result;
    }
}
