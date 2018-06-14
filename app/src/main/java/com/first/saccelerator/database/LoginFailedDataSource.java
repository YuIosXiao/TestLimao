package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.LoginFailedResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/18.
 * 登录失败数据
 */
public class LoginFailedDataSource extends BaseDataSource<LoginFailedResponse> {


    /**
     * 表名
     */
    private static final String TABLE_NAME = "loginfailed_log";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String FAILEDID = "failedid";
        String USERNAME = "username";
        String LOGINTIME = "logintime";
        String UUID = "uuid";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.FAILEDID + " INTEGER, "
            + Colum.USERNAME + " varchar(50), "
            + Colum.LOGINTIME + " INTEGER, "
            + Colum.UUID + " varchar(50))";


    public LoginFailedDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(LoginFailedResponse loginFailedResponse) {
        return insert(loginFailedResponse.getFailedid(), loginFailedResponse.getUsername(), loginFailedResponse.getLogintime(), loginFailedResponse.getUuid());
    }

    private synchronized long insert(String failedid, String username, long logintime, String uuid) {
        ContentValues values = new ContentValues();
        values.put(Colum.FAILEDID, failedid);
        values.put(Colum.USERNAME, username);
        values.put(Colum.LOGINTIME, logintime);
        values.put(Colum.UUID, uuid);

//        LogUtils.i("operationid----->" + operationid);
//        LogUtils.i("operationtime----->" + operationtime);
//        LogUtils.i("operationusename----->" + operationusename);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<LoginFailedResponse> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(LoginFailedResponse loginFailedResponse) {
        return 0;
    }

    @Override
    public int update(LoginFailedResponse loginFailedResponse) {
        return 0;
    }

    @Override
    public LoginFailedResponse find(String findId) {
        return null;
    }

    @Override
    public List<LoginFailedResponse> findAll() {
        List<LoginFailedResponse> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.FAILEDID, Colum.USERNAME, Colum.LOGINTIME, Colum.UUID}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String failedid = cursor.getString(cursor.getColumnIndex(Colum.FAILEDID));
            String username = cursor.getString(cursor.getColumnIndex(Colum.USERNAME));
            long logintime = cursor.getLong(cursor.getColumnIndex(Colum.LOGINTIME));
            String uuid = cursor.getString(cursor.getColumnIndex(Colum.UUID));
            result.add(new LoginFailedResponse(failedid, username, logintime, uuid));
        }
        cursor.close();
        return result;
    }

    @Override
    public int delete(String deletId) {
        int result = mDb.delete(TABLE_NAME, Colum.FAILEDID + "=?", new String[]{deletId});
        return result;
    }

    @Override
    public void clear() {
        mDb.delete(TABLE_NAME, null, null);
        updatesequence(TABLE_NAME);
    }

    @Override
    public boolean isExist(String searchId) {
        Cursor cursor = mDb.rawQuery("select " + Colum.FAILEDID + " from " + TABLE_NAME + " where " + Colum.FAILEDID + "=?", new String[]{searchId});
        boolean result = cursor.moveToNext();
        cursor.close();
        return result;
    }

    @Override
    public long getCount() {
        long result = 0;
        Cursor cursor = mDb.rawQuery("select count(" + Colum.FAILEDID + ") from " + TABLE_NAME, null);
        if (cursor.moveToNext()) {
            result = cursor.getLong(0);
        }
        cursor.close();
        return result;
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
