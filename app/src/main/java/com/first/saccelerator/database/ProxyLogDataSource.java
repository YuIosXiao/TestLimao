package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.entity.ProxyLogInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 服务器有效期管理类
 * Created by ZhengSheng on 2017/4/7.
 */

public class ProxyLogDataSource extends BaseDataSource<ProxyLogInfo> {

    /**
     * 表名
     */
    private static final String TABLE_NAME = "proxy_log";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String _ID = "_id";
        String HOST = "host";         // 域名
        String IP = "ip";             // ip
        String PORT = "port";         // 端口
        String IS_PROXY = "is_proxy"; // 0 false , 1 true
        String TIME = "time";         // 时间
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + Colum._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colum.HOST + " VARCHAR, "
            + Colum.IP + " VARCHAR, "
            + Colum.PORT + " INTEGER, "
            + Colum.IS_PROXY + " INTEGER(1), "
            + Colum.TIME + " INTEGER);";

    public ProxyLogDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    /**
     * 新增一条记录
     *
     * @param bean
     */
    @Override
    public long insert(ProxyLogInfo bean) {
        return insert(bean.host, bean.ip, bean.port, bean.isProxy, bean.time);
    }

    public synchronized long insert(String host, String ip, int port, int is_proxy, int time) {
        ContentValues values = new ContentValues();
        values.put(Colum.HOST, host);
        values.put(Colum.IP, ip);
        values.put(Colum.PORT, port);
        values.put(Colum.IS_PROXY, is_proxy);
        values.put(Colum.TIME, time);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    /**
     * 更新某一条记录
     *
     * @param bean
     */
    @Override
    public int update(ProxyLogInfo bean) {
        return 0;
    }

    /**
     * 插入或更新记录
     * 先查询该记录是否存在
     * 不存在执行插入操作，存在执行更新操作
     *
     * @param bean
     * @return
     */
    @Override
    public long insertOrUpdate(ProxyLogInfo bean) {
        return 0;
    }

    /**
     * 批量插入记录
     * 加入事务处理
     * 登录接口中获取数据
     *
     * @param beanList
     * @return
     */
    @Override
    public boolean insertList(List<ProxyLogInfo> beanList) {
        return false;
    }


    /**
     * 根据ID查询某条记录
     *
     * @param findId
     * @return
     */
    @Override
    public ProxyLogInfo find(String findId) {
        return null;
    }

    @Override
    public List<ProxyLogInfo> findAll() {
        return null;
    }

    /**
     * 分页查询
     *
     * @param offset
     * @param size
     * @return
     */
    public List<ProxyLogInfo> findAll(int offset, int size) {
        List<ProxyLogInfo> result = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " limit ?,?",
                new String[]{String.valueOf(offset), String.valueOf(size)});
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            String host = cursor.getString(cursor.getColumnIndex(Colum.HOST));
            String ip = cursor.getString(cursor.getColumnIndex(Colum.IP));
            int port = cursor.getInt(cursor.getColumnIndex(Colum.PORT));
            int is_proxy = cursor.getInt(cursor.getColumnIndex(Colum.IS_PROXY));
            int time = cursor.getInt(cursor.getColumnIndex(Colum.TIME));

            result.add(new ProxyLogInfo(_id, host, ip, port, is_proxy, time));
        }

        cursor.close();
        return result;
    }

    public List<ProxyLogInfo> findAllDate(String date) {
        List<ProxyLogInfo> result = new ArrayList<>();

        if (date == null) {
            Calendar c = Calendar.getInstance();//首先要获取日历对象
            int year = c.get(Calendar.YEAR); // 获取当前年份
            int month = c.get(Calendar.MONTH) + 1;// 获取当前月份
            int day = c.get(Calendar.DAY_OF_MONTH);// 获取当前天数
            date = String.format("%d-%d-%d", year, month, day);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int stime = 0;
        int etime = 0;
        try {
            stime = (int) (sdf.parse(date + " 00:00:00").getTime() / 1000);
            etime = (int) (sdf.parse(date + " 23:59:59").getTime() / 1000);
        } catch (ParseException e) {
            stime = 0;
            etime = 0;
        }
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where " + Colum.TIME + " BETWEEN ? AND ? ORDER BY " + Colum._ID + " DESC",
                new String[]{String.valueOf(stime), String.valueOf(etime)});
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            String host = cursor.getString(cursor.getColumnIndex(Colum.HOST));
            String ip = cursor.getString(cursor.getColumnIndex(Colum.IP));
            int port = cursor.getInt(cursor.getColumnIndex(Colum.PORT));
            int is_proxy = cursor.getInt(cursor.getColumnIndex(Colum.IS_PROXY));
            int time = cursor.getInt(cursor.getColumnIndex(Colum.TIME));

            result.add(new ProxyLogInfo(_id, host, ip, port, is_proxy, time));
        }

        cursor.close();
        return result;
    }

    /**
     * 删除某一条记录
     *
     * @param deletId
     */
    @Override
    public synchronized int delete(String deletId) {
        return 0;
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
     *
     * @return
     */
    @Override
    public boolean isExist(String searchId) {
        return false;
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


    private synchronized int updatesequence(String table_name) {
        ContentValues values = new ContentValues();
        values.put("seq", 0);
        int result = mDb.update("sqlite_sequence", values, " name = ? ", new String[]{String.valueOf(table_name)});
        return result;
    }

}