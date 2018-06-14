package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.LoginRecordResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/11.
 * 登录历史记录
 */
public class LoginRecordDataSource extends BaseDataSource<LoginRecordResponse> {

    /**
     * 表名
     */
    private static final String TABLE_NAME = "login_record";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String _ID = "id";
        String CREATEDTIME = "createdtime";
        String USERNAME = "username";
        String PASSWORD = "password";
        String LOGINTIME = "logintime";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum._ID + " INTEGER, "
            + Colum.CREATEDTIME + " INTEGER, "
            + Colum.USERNAME + " VARCHAR(50), "
            + Colum.PASSWORD + " VARCHAR(50), "
            + Colum.LOGINTIME + " varchar(50))";

    public LoginRecordDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }


    /**
     * 新增一条记录
     */
    @Override
    public long insert(LoginRecordResponse loginRecordResponse) {
        return insert(loginRecordResponse.getId(), loginRecordResponse.getCreatedtime(), loginRecordResponse.getUsername(), loginRecordResponse.getPassword(), loginRecordResponse.getLogintime());
    }

    private synchronized long insert(int id, long createdtime, String username, String password, String logintime) {
//        LogUtils.i("id----->" + id);
//        LogUtils.i("createdtime----->" + createdtime);
//        LogUtils.i("username----->" + username);
//        LogUtils.i("password----->" + password);
//        LogUtils.i("logintime----->" + logintime);

        ContentValues values = new ContentValues();
        values.put(Colum._ID, id);
        values.put(Colum.CREATEDTIME, createdtime);
        values.put(Colum.USERNAME, username);
        values.put(Colum.PASSWORD, password);
        values.put(Colum.LOGINTIME, logintime);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<LoginRecordResponse> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(LoginRecordResponse loginRecordResponse) {
        return 0;
    }


    /**
     * 更新某一条记录
     */
    @Override
    public int update(LoginRecordResponse loginRecordResponse) {
        return update(loginRecordResponse.getId(), loginRecordResponse.getCreatedtime(), loginRecordResponse.getUsername(), loginRecordResponse.getPassword(), loginRecordResponse.getLogintime());
    }

    private synchronized int update(int id, long createdtime, String username, String password, String logintime) {
        ContentValues values = new ContentValues();
        values.put(Colum._ID, id);
        values.put(Colum.CREATEDTIME, createdtime);
        values.put(Colum.USERNAME, username);
        values.put(Colum.PASSWORD, password);
        values.put(Colum.LOGINTIME, logintime);
        int result = mDb.update(TABLE_NAME, values, Colum._ID + "=?", new String[]{String.valueOf(id)});
        return result;
    }

    /**
     * 根据ID查询某条记录
     */
    @Override
    public LoginRecordResponse find(String findId) {
        LoginRecordResponse bean = null;
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where " + Colum._ID + "=?", new String[]{findId});
        if (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            long createdtime = cursor.getInt(cursor.getColumnIndex(Colum.CREATEDTIME));
            String username = cursor.getString(cursor.getColumnIndex(Colum.USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(Colum.PASSWORD));
            String logintime = cursor.getString(cursor.getColumnIndex(Colum.LOGINTIME));
            bean = new LoginRecordResponse(_id, createdtime, username, password, logintime);
        }
        cursor.close();
        return bean;
    }

    /**
     * 查找所有数据
     *
     * @return
     */
    @Override
    public List<LoginRecordResponse> findAll() {
        List<LoginRecordResponse> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum._ID, Colum.CREATEDTIME, Colum.USERNAME, Colum.PASSWORD, Colum.LOGINTIME}, null, null, null, null, Colum.LOGINTIME + " desc", "3");
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            long createdtime = cursor.getInt(cursor.getColumnIndex(Colum.CREATEDTIME));
            String username = cursor.getString(cursor.getColumnIndex(Colum.USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(Colum.PASSWORD));
            String logintime = cursor.getString(cursor.getColumnIndex(Colum.LOGINTIME));
            result.add(new LoginRecordResponse(_id, createdtime, username, password, logintime));
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
        mDb.delete("delete from " + TABLE_NAME, null, null);
        updatesequence(TABLE_NAME);
    }

    /**
     * 根据serverId判断对应的记录是否存在
     *
     * @return
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
