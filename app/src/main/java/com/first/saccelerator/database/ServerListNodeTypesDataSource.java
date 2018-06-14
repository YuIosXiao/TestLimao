package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.NodesResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/17.
 */
public class ServerListNodeTypesDataSource extends BaseDataSource<NodesResponse.NodeTypesBean> {

    /**
     * 表名
     * 服务器列表服务器数据
     */
    private static final String TABLE_NAME = "server_nodetypes";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String ID = "id";
        String NAME = "name";
        String LEVEL = "level";
        String EXPENSE_COINS = "expense_coins";
        String USER_GROUP_ID = "user_group_id";
        String USER_GROUP_LEVEL = "user_group_level";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.ID + " INTEGER, "
            + Colum.NAME + " VARCHAR(60) NOT NULL, "
            + Colum.LEVEL + " INTEGER, "
            + Colum.EXPENSE_COINS + " INTEGER, "
            + Colum.USER_GROUP_ID + " INTEGER, "
            + Colum.USER_GROUP_LEVEL + " INTEGER) ";


    public ServerListNodeTypesDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(NodesResponse.NodeTypesBean nodeTypesBean) {
        return insert(nodeTypesBean.getId(), nodeTypesBean.getName(), nodeTypesBean.getLevel()
                , nodeTypesBean.getExpense_coins(), nodeTypesBean.getUser_group_id(), nodeTypesBean.getUser_group_level());
    }

    private synchronized long insert(int id, String name, int level, int expense_coins, int user_group_id, int user_group_level) {
        ContentValues values = new ContentValues();
        values.put(Colum.ID, id);
        values.put(Colum.NAME, name);
        values.put(Colum.LEVEL, level);
        values.put(Colum.EXPENSE_COINS, expense_coins);
        values.put(Colum.USER_GROUP_ID, user_group_id);
        values.put(Colum.USER_GROUP_LEVEL, user_group_level);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<NodesResponse.NodeTypesBean> list) {
        boolean result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (NodesResponse.NodeTypesBean bean : list) {
                insertOrUpdate(bean);
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

    @Override
    public long insertOrUpdate(NodesResponse.NodeTypesBean nodeTypesBean) {
        return insertOrUpdate(nodeTypesBean.getId(), nodeTypesBean.getName(), nodeTypesBean.getLevel()
                , nodeTypesBean.getExpense_coins(), nodeTypesBean.getUser_group_id(), nodeTypesBean.getUser_group_level());
    }

    private long insertOrUpdate(int id, String name, int level, int expense_coins, int user_group_id, int user_group_level) {
        // 先判断该数据是否存在，已经存在就更新数据，不存在才插入该数据
        if (isExist(String.valueOf(id))) {
            return update(id, name, level, expense_coins, user_group_id, user_group_level);
        } else {
            return insert(id, name, level, expense_coins, user_group_id, user_group_level);
        }
    }

    @Override
    public int update(NodesResponse.NodeTypesBean nodeTypesBean) {
        return update(nodeTypesBean.getId(), nodeTypesBean.getName(), nodeTypesBean.getLevel()
                , nodeTypesBean.getExpense_coins(), nodeTypesBean.getUser_group_id(), nodeTypesBean.getUser_group_level());
    }

    private synchronized int update(int id, String name, int level, int expense_coins, int user_group_id, int user_group_level) {
        ContentValues values = new ContentValues();
        values.put(Colum.ID, id);
        values.put(Colum.NAME, name);
        values.put(Colum.LEVEL, level);
        values.put(Colum.EXPENSE_COINS, expense_coins);
        values.put(Colum.USER_GROUP_ID, user_group_id);
        values.put(Colum.USER_GROUP_LEVEL, user_group_level);
        int result = mDb.update(TABLE_NAME, values, Colum.ID + "=?", new String[]{String.valueOf(id)});
        return result;
    }

    @Override
    public NodesResponse.NodeTypesBean find(String findId) {
        return null;
    }

    @Override
    public List<NodesResponse.NodeTypesBean> findAll() {
        NodesResponse.NodeTypesBean bean = null;
        List<NodesResponse.NodeTypesBean> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.ID, Colum.NAME, Colum.LEVEL, Colum.EXPENSE_COINS, Colum.USER_GROUP_ID, Colum.USER_GROUP_LEVEL}, null, null, null, null, Colum.ID);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum.ID));
            String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
            int level = cursor.getInt(cursor.getColumnIndex(Colum.LEVEL));
            int expense_coins = cursor.getInt(cursor.getColumnIndex(Colum.EXPENSE_COINS));
            int user_group_id = cursor.getInt(cursor.getColumnIndex(Colum.USER_GROUP_ID));
            int user_group_level = cursor.getInt(cursor.getColumnIndex(Colum.USER_GROUP_LEVEL));
            bean = new NodesResponse.NodeTypesBean();
            bean.setId(_id);
            bean.setName(name);
            bean.setLevel(level);
            bean.setExpense_coins(expense_coins);
            bean.setUser_group_id(user_group_id);
            bean.setUser_group_level(user_group_level);
            result.add(bean);
        }
        cursor.close();
        return result;
    }

    @Override
    public int delete(String deletId) {
        int result = mDb.delete(TABLE_NAME, Colum.ID + "=?", new String[]{deletId});
        return result;
    }

    @Override
    public void clear() {
        mDb.delete("delete from " + TABLE_NAME, null, null);
    }

    @Override
    public boolean isExist(String searchId) {
        Cursor cursor = mDb.rawQuery("select " + Colum.ID + " from " + TABLE_NAME + " where " + Colum.ID + "=?", new String[]{searchId});
        boolean result = cursor.moveToNext();
        cursor.close();
        return result;
    }

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
}
