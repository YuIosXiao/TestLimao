package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.RescueIPServersResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/11/20.
 * 救援IP  数据保存
 */
public class RescueIPDataSource extends BaseDataSource<RescueIPServersResponse> {


    /**
     * 表名
     */
    private static final String TABLE_NAME = "rescue_ip";


    /**
     * 表中字段名称
     */
    private interface Colum {
        String ID = "id";//服务器ID
        String URL = "url";
        String REGION = "region";//服务器区域
        String PRIORITY = "priority";//优先级
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE if not exists " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.ID + " INTEGER, "
            + Colum.URL + " VARCHAR(50), "
            + Colum.REGION + " VARCHAR(50), "
            + Colum.PRIORITY + " INTEGER)";


    public RescueIPDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }


    @Override
    public long insert(RescueIPServersResponse rescueIPServersResponse) {
        return insert(rescueIPServersResponse.getUrl());
    }

    private synchronized long insert(String url) {
        ContentValues values = new ContentValues();
        values.put(Colum.URL, url);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    public synchronized long insert1(String url) {
        ContentValues values = new ContentValues();
        values.put(Colum.URL, url);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }


    @Override
    public boolean insertList(List<RescueIPServersResponse> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(RescueIPServersResponse rescueIPServersResponse) {
        return 0;
    }

    @Override
    public int update(RescueIPServersResponse rescueIPServersResponse) {
        return 0;
    }

    @Override
    public RescueIPServersResponse find(String findId) {
        return null;
    }

    @Override
    public List<RescueIPServersResponse> findAll() {
        List<RescueIPServersResponse> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_NAME, new String[]{Colum.URL}, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String url = cursor.getString(cursor.getColumnIndex(Colum.URL));
                    result.add(new RescueIPServersResponse(url));
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

    /**
     * 查找数据通过primaryid
     */
    public RescueIPServersResponse findPrimaryid(String rowid) {
        RescueIPServersResponse bean = null;
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where primaryid =?", new String[]{rowid});
        if (cursor.moveToNext()) {
            String url = cursor.getString(cursor.getColumnIndex(Colum.URL));
            bean = new RescueIPServersResponse(url);
        }
        cursor.close();
        return bean;
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
        return false;
    }

    @Override
    public long getCount() {
        return 0;
    }

    /**
     * 重置SQLite中的自动编号列
     *
     * @param table_name
     * @return
     */
    private synchronized int updatesequence(String table_name) {
        ContentValues values = new ContentValues();
        values.put("seq", 0);
        int result = mDb.update("sqlite_sequence", values, " name = ? ", new String[]{String.valueOf(table_name)});
        return result;
    }
}
