package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.ConnectionFailedResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/18.
 * 连接失败数据
 */
public class ConnectionFailedDataSource extends BaseDataSource<ConnectionFailedResponse> {

    /**
     * 表名
     */
    private static final String TABLE_NAME = "connectionfailed_log";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String FAILEDID = "failedid";
        String USERNAME = "username";
        String CREATETIME = "createtime";
        String SERVERTYPENAME = "servertypename";
        String SERVERAREANAME = "serverareaname";
        String SERVERNAME = "servername";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.FAILEDID + " varchar(50), "
            + Colum.USERNAME + " varchar(50), "
            + Colum.CREATETIME + " INTEGER, "
            + Colum.SERVERTYPENAME + " varchar(50), "
            + Colum.SERVERAREANAME + " varchar(50), "
            + Colum.SERVERNAME + " varchar(50)) ";

    public ConnectionFailedDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(ConnectionFailedResponse connectionFailedResponse) {
        return insert(connectionFailedResponse.getFailedid(), connectionFailedResponse.getUsername(), connectionFailedResponse.getCreatetime()
                , connectionFailedResponse.getServertypename(), connectionFailedResponse.getServerareaname(), connectionFailedResponse.getServername());
    }

    private synchronized long insert(String failedid, String username, long createtime, String servertypename
            , String serverareaname, String servername) {
        ContentValues values = new ContentValues();
        values.put(Colum.FAILEDID, failedid);
        values.put(Colum.USERNAME, username);
        values.put(Colum.CREATETIME, createtime);
        values.put(Colum.SERVERTYPENAME, servertypename);
        values.put(Colum.SERVERAREANAME, serverareaname);
        values.put(Colum.SERVERNAME, servername);
//        LogUtils.i("operationid----->" + operationid);
//        LogUtils.i("operationtime----->" + operationtime);
//        LogUtils.i("operationusename----->" + operationusename);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;

    }

    @Override
    public boolean insertList(List<ConnectionFailedResponse> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(ConnectionFailedResponse connectionFailedResponse) {
        return 0;
    }

    @Override
    public int update(ConnectionFailedResponse connectionFailedResponse) {
        return 0;
    }

    @Override
    public ConnectionFailedResponse find(String findId) {
        return null;
    }

    @Override
    public List<ConnectionFailedResponse> findAll() {
        List<ConnectionFailedResponse> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.FAILEDID, Colum.USERNAME, Colum.CREATETIME, Colum.SERVERTYPENAME, Colum.SERVERAREANAME, Colum.SERVERNAME}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String failedid = cursor.getString(cursor.getColumnIndex(Colum.FAILEDID));
            String username = cursor.getString(cursor.getColumnIndex(Colum.USERNAME));
            long creattime = cursor.getLong(cursor.getColumnIndex(Colum.CREATETIME));
            String servertypename = cursor.getString(cursor.getColumnIndex(Colum.SERVERTYPENAME));
            String serverareaname = cursor.getString(cursor.getColumnIndex(Colum.SERVERAREANAME));
            String servername = cursor.getString(cursor.getColumnIndex(Colum.SERVERNAME));
            result.add(new ConnectionFailedResponse(failedid, username, creattime, servertypename, serverareaname, servername));
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
