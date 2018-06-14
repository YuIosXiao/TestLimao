package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.NodesResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/10.
 * 服务器列表区域数据
 */
public class ServerListRegionsDataSource extends BaseDataSource<NodesResponse.NodeTypesBean.NodeRegionsBean> {

    /**
     * 表名
     * 服务器列表区域数据
     */
    private static final String TABLE_NAME = "server_regions";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String ID = "id";    // 3
        String NAME = "name";  //美国
        String ABBR = "abbr";  // US
        String NODETYPESID = "nodetypesid";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.ID + " INTEGER, "
            + Colum.NAME + " VARCHAR(60), "
            + Colum.ABBR + " VARCHAR(30), "
            + Colum.NODETYPESID + " INTEGER); ";

    public ServerListRegionsDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(NodesResponse.NodeTypesBean.NodeRegionsBean nodeRegionsBean) {
        return 0;
    }

    @Override
    public boolean insertList(List<NodesResponse.NodeTypesBean.NodeRegionsBean> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(NodesResponse.NodeTypesBean.NodeRegionsBean nodeRegionsBean) {
        return 0;
    }

    @Override
    public int update(NodesResponse.NodeTypesBean.NodeRegionsBean nodeRegionsBean) {
        return 0;
    }


    public long insert(NodesResponse.NodeTypesBean.NodeRegionsBean nodeRegionsBean, int nodetypesid) {
        return insert(nodeRegionsBean.getId(), nodeRegionsBean.getName(), nodeRegionsBean.getAbbr(), nodetypesid);
    }

    private synchronized long insert(int id, String name, String abbr, int nodetypesid) {
        ContentValues values = new ContentValues();
        values.put(Colum.ID, id);
        values.put(Colum.NAME, name);
        values.put(Colum.ABBR, abbr);
        values.put(Colum.NODETYPESID, nodetypesid);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    public int update(NodesResponse.NodeTypesBean.NodeRegionsBean nodeRegionsBean, int nodetypesid) {
        return update(nodeRegionsBean.getId(), nodeRegionsBean.getName(), nodeRegionsBean.getAbbr(), nodetypesid);
    }

    private synchronized int update(int id, String name, String abbr, int nodetypesid) {
        ContentValues values = new ContentValues();
        values.put(Colum.ID, id);
        values.put(Colum.NAME, name);
        values.put(Colum.ABBR, abbr);
        values.put(Colum.NODETYPESID, nodetypesid);
        int result = mDb.update(TABLE_NAME, values, Colum.ID + "=?", new String[]{String.valueOf(id)});
        return result;
    }

    public boolean insertList(List<NodesResponse.NodeTypesBean.NodeRegionsBean> list, int nodetypesid) {
        boolean result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (NodesResponse.NodeTypesBean.NodeRegionsBean bean : list) {
                insertOrUpdate(bean, nodetypesid);
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

    public long insertOrUpdate(NodesResponse.NodeTypesBean.NodeRegionsBean nodeRegionsBean, int nodetypesid) {
        return insertOrUpdate(nodeRegionsBean.getId(), nodeRegionsBean.getName(), nodeRegionsBean.getAbbr(), nodetypesid);
    }

    private long insertOrUpdate(int id, String name, String abbr, int nodetypesid) {
        // 先判断该数据是否存在，已经存在就更新数据，不存在才插入该数据
        if (isExist(String.valueOf(id))) {
            return update(id, name, abbr, nodetypesid);
        } else {
            return insert(id, name, abbr, nodetypesid);
        }

    }


    @Override
    public NodesResponse.NodeTypesBean.NodeRegionsBean find(String findId) {
        return null;
    }

    @Override
    public List<NodesResponse.NodeTypesBean.NodeRegionsBean> findAll() {
        return null;
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
     * 根据服务器类型ID查询数据
     */
    public List<NodesResponse.NodeTypesBean.NodeRegionsBean> findAllFromNodeTypesID(int nodetypesid) {
        NodesResponse.NodeTypesBean.NodeRegionsBean bean = null;
        List<NodesResponse.NodeTypesBean.NodeRegionsBean> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.ID, Colum.NAME, Colum.ABBR}, "nodetypesid = ?", new String[]{nodetypesid + ""}, null, null, Colum.ID);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum.ID));
            String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
            String abbr = cursor.getString(cursor.getColumnIndex(Colum.ABBR));
            bean = new NodesResponse.NodeTypesBean.NodeRegionsBean();
            bean.setId(_id);
            bean.setName(name);
            bean.setAbbr(abbr);
            result.add(bean);
        }
        cursor.close();
        return result;


    }


}
