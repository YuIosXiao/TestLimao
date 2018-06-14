package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.OperationLogResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/13.
 * 操作日志数据库
 */
public class OperationLogDataSource extends BaseDataSource<OperationLogResponse> {

    /**
     * 表名
     */
    private static final String TABLE_NAME = "operation_log";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String _ID = "operationid";
        String OPERATIONTIME = "operationtime";
        String OPERATIONUSENAME = "operationusename";
        String OPERATIONUSERID = "operationuserid";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum._ID + " INTEGER, "
            + Colum.OPERATIONTIME + " INTEGER, "
            + Colum.OPERATIONUSENAME + " varchar(50), "
            + Colum.OPERATIONUSERID + " INTEGER)";


    public OperationLogDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }


    /**
     * 新增一条记录
     */
    @Override
    public long insert(OperationLogResponse operationLogResponse) {
        return insert(operationLogResponse.getOperationid(), operationLogResponse.getOperationtime(), operationLogResponse.getOperationusename(), operationLogResponse.getOperationuserid());
    }

    private synchronized long insert(int operationid, long operationtime, String operationusename, int operationuserid) {
        ContentValues values = new ContentValues();
        values.put(Colum._ID, operationid);
        values.put(Colum.OPERATIONTIME, operationtime);
        values.put(Colum.OPERATIONUSENAME, operationusename);
        values.put(Colum.OPERATIONUSERID, operationuserid);

//        LogUtils.i("operationid----->" + operationid);
//        LogUtils.i("operationtime----->" + operationtime);
//        LogUtils.i("operationusename----->" + operationusename);


        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<OperationLogResponse> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(OperationLogResponse operationLogResponse) {
        return 0;
    }


    /**
     * 更新某一条记录
     */
    @Override
    public int update(OperationLogResponse operationLogResponse) {
        return update(operationLogResponse.getOperationid(), operationLogResponse.getOperationtime(), operationLogResponse.getOperationusename(), operationLogResponse.getOperationuserid());
    }

    private synchronized int update(int operationid, long operationtime, String operationusename, int operationuserid) {
        ContentValues values = new ContentValues();
        values.put(Colum._ID, operationid);
        values.put(Colum.OPERATIONTIME, operationtime);
        values.put(Colum.OPERATIONUSENAME, operationusename);
        values.put(Colum.OPERATIONUSERID, operationuserid);
        int result = mDb.update(TABLE_NAME, values, Colum._ID + "=?", new String[]{String.valueOf(operationid)});
        return result;
    }


    /**
     * 根据ID查询某条记录
     */
    @Override
    public OperationLogResponse find(String findId) {
        OperationLogResponse bean = null;
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where " + Colum._ID + "=?", new String[]{findId});
        if (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            int operationtime = cursor.getInt(cursor.getColumnIndex(Colum.OPERATIONTIME));
            String operationusename = cursor.getString(cursor.getColumnIndex(Colum.OPERATIONUSENAME));
            int operationuserid = cursor.getInt(cursor.getColumnIndex(Colum.OPERATIONUSERID));
            bean = new OperationLogResponse(_id, operationtime, operationusename, operationuserid);
        }
        cursor.close();
        return bean;
    }

    /**
     * 查找所有数据
     */
    @Override
    public List<OperationLogResponse> findAll() {
        List<OperationLogResponse> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum._ID, Colum.OPERATIONTIME, Colum.OPERATIONUSENAME, Colum.OPERATIONUSERID}, null, null, null, null, Colum.OPERATIONTIME);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            int operationtime = cursor.getInt(cursor.getColumnIndex(Colum.OPERATIONTIME));
            String operationusename = cursor.getString(cursor.getColumnIndex(Colum.OPERATIONUSENAME));
            int operationuserid = cursor.getInt(cursor.getColumnIndex(Colum.OPERATIONUSERID));
            result.add(new OperationLogResponse(_id, operationtime, operationusename, operationuserid));
        }
        cursor.close();
        return result;
    }

    /**
     * 删除某一条记录
     */
    @Override
    public int delete(String deletId) {
        int result = mDb.delete(TABLE_NAME, Colum._ID + "=?", new String[]{deletId});
        return result;
    }

    /**
     * 清空表中的所有记录
     */
    @Override
    public void clear() {
        mDb.delete(TABLE_NAME, null, null);
        updatesequence(TABLE_NAME);
    }

    /**
     * 根据serverId判断对应的记录是否存在
     */
    @Override
    public boolean isExist(String searchId) {
        Cursor cursor = mDb.rawQuery("select " + Colum._ID + " from " + TABLE_NAME + " where " + Colum._ID + "=?", new String[]{searchId});
        boolean result = cursor.moveToNext();
        cursor.close();
        return result;
    }

    /**
     * 得到记录数
     *
     * @return
     */
    @Override
    public long getCount() {
        long result = 0;
        Cursor cursor = mDb.rawQuery("select count(" + Colum._ID + ") from " + TABLE_NAME, null);
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
