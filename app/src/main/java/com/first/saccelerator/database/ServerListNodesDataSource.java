package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.NodesResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/10.
 * 服务器列表服务器数据
 */
public class ServerListNodesDataSource extends BaseDataSource<NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean> {

    /**
     * 表名
     * 服务器列表服务器数据
     */
    private static final String TABLE_NAME = "server_nodes";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String ID = "id";                              // 2
        String NAME = "name";                            //"香港02"
        String URL = "url";                              // "119.23.64.170"
        String PORT = "port";                            // 8388
        String PASSWORD = "password";                    // "asdf"
        String ENCRYPT_METHOD = "encrypt_method";        // "aes-128-cfb"
        String CONNECTIONS_COUNT = "connections_count";  // 0
        String REGIONSID = "regionsid";  // 区域ID
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.ID + " INTEGER, "
            + Colum.NAME + " VARCHAR(60) NOT NULL, "
            + Colum.URL + " VARCHAR(50), "
            + Colum.PORT + " INTEGER, "
            + Colum.PASSWORD + " VARCHAR(50), "
            + Colum.ENCRYPT_METHOD + " VARCHAR(50), "
            + Colum.CONNECTIONS_COUNT + " INTEGER, "
            + Colum.REGIONSID + " INTEGER) ";


    public ServerListNodesDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean nodesBean) {
        return 0;
    }

    @Override
    public boolean insertList(List<NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean nodesBean) {
        return 0;
    }

    @Override
    public int update(NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean nodesBean) {
        return 0;
    }

    @Override
    public NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean find(String findId) {
        return null;
    }

    @Override
    public List<NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean> findAll() {
        return null;
    }

    public long insert(NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean nodesBean, int regionsid) {
        return insert(nodesBean.getId(), nodesBean.getName(), nodesBean.getUrl()
                , nodesBean.getPort(), nodesBean.getPassword(), nodesBean.getEncrypt_method(), nodesBean.getConnections_count(), regionsid);
    }

    private synchronized long insert(int id, String name, String url
            , int port, String password, String encrypt_method, int connections_count, int regionsid) {
        ContentValues values = new ContentValues();
        values.put(Colum.ID, id);
        values.put(Colum.NAME, name);
        values.put(Colum.URL, url);
        values.put(Colum.PORT, port);
        values.put(Colum.PASSWORD, password);
        values.put(Colum.ENCRYPT_METHOD, encrypt_method);
        values.put(Colum.CONNECTIONS_COUNT, connections_count);
        values.put(Colum.REGIONSID, regionsid);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    public int update(NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean nodesBean, int regionsid) {
        return update(nodesBean.getId(), nodesBean.getName(), nodesBean.getUrl()
                , nodesBean.getPort(), nodesBean.getPassword(), nodesBean.getEncrypt_method(), nodesBean.getConnections_count(), regionsid);
    }

    private synchronized int update(int id, String name, String url, int port
            , String password, String encrypt_method, int connections_count, int regionsid) {
        ContentValues values = new ContentValues();
        values.put(Colum.ID, id);
        values.put(Colum.NAME, name);
        values.put(Colum.URL, url);
        values.put(Colum.PORT, port);
        values.put(Colum.PASSWORD, password);
        values.put(Colum.ENCRYPT_METHOD, encrypt_method);
        values.put(Colum.CONNECTIONS_COUNT, connections_count);
        values.put(Colum.REGIONSID, regionsid);
        int result = mDb.update(TABLE_NAME, values, Colum.ID + "=?", new String[]{String.valueOf(id)});
        return result;
    }

    public boolean insertList(List<NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean> list, int regionsid) {
        boolean result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean bean : list) {
                insertOrUpdate(bean, regionsid);
            }
            mDb.setTransactionSuccessful();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }
        return false;
    }


    public long insertOrUpdate(NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean nodesBean, int regionsid) {
        return insertOrUpdate(nodesBean.getId(), nodesBean.getName(), nodesBean.getUrl()
                , nodesBean.getPort(), nodesBean.getPassword(), nodesBean.getEncrypt_method(), nodesBean.getConnections_count(), regionsid);
    }

    private long insertOrUpdate(int id, String name, String url, int port, String password
            , String encrypt_method, int connections_count, int regionsid) {
        // 先判断该数据是否存在，已经存在就更新数据，不存在才插入该数据
        if (isExist(String.valueOf(id))) {
            return update(id, name, url, port, password, encrypt_method, connections_count, regionsid);
        } else {
            return insert(id, name, url, port, password, encrypt_method, connections_count, regionsid);
        }
    }


    /**
     * 删除某一条记录
     *
     * @param deletId
     */
    @Override
    public int delete(String deletId) {
        int result = mDb.delete(TABLE_NAME, Colum.ID + "=?", new String[]{deletId});
        return result;
    }

    /**
     * 清空表中的所有记录
     */
    @Override
    public void clear() {
        mDb.delete("delete from " + TABLE_NAME, null, null);
    }

    /**
     * 根据serverId判断对应的记录是否存在
     *
     * @return
     */
    @Override
    public boolean isExist(String searchId) {
        Cursor cursor = mDb.rawQuery("select " + Colum.ID + " from " + TABLE_NAME + " where " + Colum.ID + "=?", new String[]{searchId});
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
        Cursor cursor = mDb.rawQuery("select count(" + Colum.ID + ") from " + TABLE_NAME, null);
        if (cursor.moveToNext()) {
            result = cursor.getLong(0);
        }
        cursor.close();
        return result;
    }


    /**
     * 根据区域ID查询数据
     */
    public List<NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean> findAllFromRegionsId(int regions_id) {
        NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean bean = null;
        List<NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.ID, Colum.NAME, Colum.URL, Colum.PORT, Colum.PASSWORD, Colum.ENCRYPT_METHOD, Colum.CONNECTIONS_COUNT}, "regionsid = ?", new String[]{regions_id + ""}, null, null, Colum.ID);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum.ID));
            int port = cursor.getInt(cursor.getColumnIndex(Colum.PORT));
            int connections_count = cursor.getInt(cursor.getColumnIndex(Colum.CONNECTIONS_COUNT));
            String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
            String url = cursor.getString(cursor.getColumnIndex(Colum.URL));
            String password = cursor.getString(cursor.getColumnIndex(Colum.PASSWORD));
            String encrypt_method = cursor.getString(cursor.getColumnIndex(Colum.ENCRYPT_METHOD));
            bean = new NodesResponse.NodeTypesBean.NodeRegionsBean.NodesBean();
            bean.setId(_id);
            bean.setName(name);
            bean.setUrl(url);
            bean.setPort(port);
            bean.setPassword(password);
            bean.setEncrypt_method(encrypt_method);
            bean.setConnections_count(connections_count);
            result.add(bean);
        }
        cursor.close();
        return result;
    }


}
