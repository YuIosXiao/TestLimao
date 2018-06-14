package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.ProxyIpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/7/10.
 * 代理IP去向数据
 */
public class ProxyIpDataSource extends BaseDataSource<ProxyIpResponse> {

    /**
     * 表名
     */
    private static final String TABLE_NAME = "proxy_ip";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String USERID = "userid";
        String CREATTIME = "creattime";
        String SOURCEADDRESS = "sourceaddress";
        String TARGETADDRESS = "targetaddress";
        String NODEID = "nodeid";
        String TARGETIP = "targetip";
        String PORT = "port";
        String ISPROXY = "isProxy";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE if not exists " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.USERID + " INTEGER, "
            + Colum.CREATTIME + " INTEGER, "
            + Colum.SOURCEADDRESS + " VARCHAR(100), "
            + Colum.TARGETADDRESS + " VARCHAR(100), "
            + Colum.NODEID + " INTEGER, "
            + Colum.TARGETIP + " VARCHAR(100), "
            + Colum.PORT + " INTEGER, "
            + Colum.ISPROXY + " INTEGER) ";


    public ProxyIpDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(ProxyIpResponse proxyIpResponse) {
        return insert(proxyIpResponse.getUserid(), proxyIpResponse.getCreattime(), proxyIpResponse.getSourceaddress()
                , proxyIpResponse.getTargetaddress(), proxyIpResponse.getNodeid(), proxyIpResponse.getTargetip(), proxyIpResponse.getPort(), proxyIpResponse.getIsProxy());
    }

    public synchronized long insert(int userid, long creattime, String sourceaddress, String targetaddress, int nodeid, String targetip, int port, int isProxy) {
        ContentValues values = new ContentValues();
        values.put(Colum.USERID, userid);
        values.put(Colum.CREATTIME, creattime);
        values.put(Colum.SOURCEADDRESS, sourceaddress);
        values.put(Colum.TARGETADDRESS, targetaddress);
        values.put(Colum.NODEID, nodeid);
        values.put(Colum.TARGETIP, targetip);
        values.put(Colum.PORT, port);
        values.put(Colum.ISPROXY, isProxy);
//        LogUtils.i("operationid----->" + operationid);
//        LogUtils.i("operationtime----->" + operationtime);
//        LogUtils.i("operationusename----->" + operationusename);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<ProxyIpResponse> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(ProxyIpResponse proxyIpResponse) {
        return 0;
    }


    public long insertOrUpdate(int userid, long creattime, String sourceaddress, String targetaddress, int nodeid, String targetip, int port, int isProxy) {
        // 先判断该数据是否存在，已经存在就更新数据，不存在才插入该数据
        if (isExist(targetaddress, creattime + "")) {
            return update(userid, creattime, sourceaddress, targetaddress, nodeid, targetip, port, isProxy);
        } else {
            return insert(userid, creattime, sourceaddress, targetaddress, nodeid, targetip, port, isProxy);
        }
    }


    @Override
    public int update(ProxyIpResponse proxyIpResponse) {
        return 0;
    }


    public synchronized long update(int userid, long creattime, String sourceaddress, String targetaddress, int nodeid, String targetip, int port, int isProxy) {
        ContentValues values = new ContentValues();
        values.put(Colum.USERID, userid);
        values.put(Colum.CREATTIME, creattime);
        values.put(Colum.SOURCEADDRESS, sourceaddress);
        values.put(Colum.TARGETADDRESS, targetaddress);
        values.put(Colum.NODEID, nodeid);
        values.put(Colum.TARGETIP, targetip);
        values.put(Colum.PORT, port);
        values.put(Colum.ISPROXY, isProxy);
        int result = mDb.update(TABLE_NAME, values, Colum.TARGETADDRESS + "=?", new String[]{String.valueOf(targetaddress)});
        return result;
    }


    @Override
    public ProxyIpResponse find(String findId) {
        return null;
    }

    @Override
    public List<ProxyIpResponse> findAll() {
        List<ProxyIpResponse> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.USERID, Colum.CREATTIME, Colum.SOURCEADDRESS,
                Colum.TARGETADDRESS, Colum.NODEID, Colum.TARGETIP, Colum.PORT, Colum.ISPROXY}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int userid = cursor.getInt(cursor.getColumnIndex(Colum.USERID));
            long creattime = cursor.getLong(cursor.getColumnIndex(Colum.CREATTIME));
            String sourceaddress = cursor.getString(cursor.getColumnIndex(Colum.SOURCEADDRESS));
            String targetaddress = cursor.getString(cursor.getColumnIndex(Colum.TARGETADDRESS));
            int nodeid = cursor.getInt(cursor.getColumnIndex(Colum.NODEID));
            String targetip = cursor.getString(cursor.getColumnIndex(Colum.TARGETIP));
            int port = cursor.getInt(cursor.getColumnIndex(Colum.PORT));
            int isproxy = cursor.getInt(cursor.getColumnIndex(Colum.ISPROXY));
            result.add(new ProxyIpResponse(userid, creattime, sourceaddress, targetaddress, nodeid, targetip, port, isproxy));
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
        updatesequence(TABLE_NAME);
    }

    @Override
    public boolean isExist(String searchId) {
        return false;
    }


    public boolean isExist(String targetaddress, String creattime) {
        Cursor cursor = mDb.rawQuery("select " + Colum.TARGETADDRESS + " from " + TABLE_NAME + " where " + Colum.TARGETADDRESS + "=? and " + Colum.CREATTIME + "=?", new String[]{targetaddress, creattime});
        boolean result = cursor.moveToNext();
        cursor.close();
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
