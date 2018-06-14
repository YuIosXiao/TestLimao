package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.NoOperationResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/18.
 * 用户未操作数据
 */
public class NoOperationDataSource extends BaseDataSource<NoOperationResponse> {
    /**
     * 表名
     */
    private static final String TABLE_NAME = "nooperation_log";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String USERNAME = "username";
        String CREATTIME = "creattime";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.USERNAME + " varchar(50), "
            + Colum.CREATTIME + " INTEGER) ";


    public NoOperationDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(NoOperationResponse noOperationResponse) {
        return insert(noOperationResponse.getUsername(), noOperationResponse.getCreattime());
    }

    private synchronized long insert(String username, long creattime) {
        ContentValues values = new ContentValues();
        values.put(Colum.USERNAME, username);
        values.put(Colum.CREATTIME, creattime);
//        LogUtils.i("operationid----->" + operationid);
//        LogUtils.i("operationtime----->" + operationtime);
//        LogUtils.i("operationusename----->" + operationusename);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<NoOperationResponse> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(NoOperationResponse noOperationResponse) {
        return 0;
    }

    @Override
    public int update(NoOperationResponse noOperationResponse) {
        return 0;
    }

    @Override
    public NoOperationResponse find(String findId) {
        return null;
    }

    @Override
    public List<NoOperationResponse> findAll() {
        List<NoOperationResponse> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.USERNAME, Colum.CREATTIME}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex(Colum.USERNAME));
            long creattime = cursor.getLong(cursor.getColumnIndex(Colum.CREATTIME));
            result.add(new NoOperationResponse(username, creattime));
        }
        cursor.close();
        return result;
    }

    @Override
    public int delete(String deletId) {
        int result = mDb.delete(TABLE_NAME, Colum.USERNAME + "=?", new String[]{""});
        return result;
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
