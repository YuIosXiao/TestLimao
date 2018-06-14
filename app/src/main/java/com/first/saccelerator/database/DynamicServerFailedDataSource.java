package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.DynamicServerFailedResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/5/5.
 */
public class DynamicServerFailedDataSource extends BaseDataSource<DynamicServerFailedResponse> {

    /**
     * 表名
     */
    private static final String TABLE_NAME = "dynamic_server_failed";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String USERNAME = "username";
        String URL = "url";
        String CREATETIME = "createtime";
        String USERID = "userid";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.USERNAME + " varchar(50), "
            + Colum.URL + " varchar(50), "
            + Colum.CREATETIME + " INTEGER, "
            + Colum.USERID + " INTEGER)";

    public DynamicServerFailedDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(DynamicServerFailedResponse dynamicServerFailedResponse) {
        return insert(dynamicServerFailedResponse.getUsername(), dynamicServerFailedResponse.getUrl(), dynamicServerFailedResponse.getCreatetime());
    }

    private synchronized long insert(String username, String url, long createtime) {
        ContentValues values = new ContentValues();
        values.put(Colum.USERNAME, username);
        values.put(Colum.URL, url);
        values.put(Colum.CREATETIME, createtime);
//        LogUtils.i("operationid----->" + operationid);
//        LogUtils.i("operationtime----->" + operationtime);
//        LogUtils.i("operationusename----->" + operationusename);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<DynamicServerFailedResponse> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(DynamicServerFailedResponse dynamicServerFailedResponse) {
        return 0;
    }

    @Override
    public int update(DynamicServerFailedResponse dynamicServerFailedResponse) {
        return 0;
    }

    @Override
    public DynamicServerFailedResponse find(String findId) {
        return null;
    }

    @Override
    public List<DynamicServerFailedResponse> findAll() {
        List<DynamicServerFailedResponse> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.USERNAME, Colum.URL, Colum.CREATETIME, Colum.USERID}, null, null, null, null, Colum.CREATETIME);
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex(Colum.USERNAME));
            String url = cursor.getString(cursor.getColumnIndex(Colum.URL));
            long createtime = cursor.getInt(cursor.getColumnIndex(Colum.CREATETIME));
            int userid = cursor.getInt(cursor.getColumnIndex(Colum.USERID));
            result.add(new DynamicServerFailedResponse(username, url, createtime, userid));
        }
        cursor.close();
        return result;
    }

    @Override
    public int delete(String deletId) {
        return 0;
    }

    @Override
    public void clear() {
        mDb.delete(TABLE_NAME, null, null);
    }

    @Override
    public boolean isExist(String searchId) {
        return false;
    }

    @Override
    public long getCount() {
        return 0;
    }


}
