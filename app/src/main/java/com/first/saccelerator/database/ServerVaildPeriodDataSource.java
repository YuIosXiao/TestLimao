package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.NodesResponse;
import com.first.saccelerator.model.RegisteredResponse;
import com.first.saccelerator.model.SigninResponse;
import com.first.saccelerator.model.SigninResponseV2;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器有效期管理类
 * Created by ZhengSheng on 2017/4/7.
 */

public class ServerVaildPeriodDataSource extends BaseDataSource<SigninResponseV2.UserNodeTypesBean> {

    /**
     * 表名
     */
    private static final String TABLE_NAME = "server_vaild_period";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String _ID = "_id";               // 1076
        String NAME = "name";             // "青铜服"
        String LEVEL = "level";           // 1
        String STATUS = "status";         // "按次"
        String EXPIRED_AT = "expired_at"; // 1491553783
        String USED_COUNT = "used_count"; // 0
        String NODE_TYPE_ID = "node_type_id"; // 1
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + Colum._ID + " INTEGER, "
            + Colum.NAME + " VARCHAR(60) NOT NULL, "
            + Colum.LEVEL + " INTEGER PRIMARY KEY, "
            + Colum.STATUS + " VARCHAR(30), "
            + Colum.EXPIRED_AT + " INTEGER, "
            + Colum.NODE_TYPE_ID + " INTEGER, "
            + Colum.USED_COUNT + " INTEGER);";

    public ServerVaildPeriodDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    /**
     * 新增一条记录
     *
     * @param bean
     */
    @Override
    public long insert(SigninResponseV2.UserNodeTypesBean bean) {
        return insert(bean.getId(), bean.getName(), bean.getLevel(), bean.getStatus(), bean.getExpired_at(), bean.getUsed_count(), bean.getNode_type_id());
    }

    public synchronized long insert(int _id, String name, int level, String status, long expired_at, int used_count, int node_type_id) {
        ContentValues values = new ContentValues();
        values.put(Colum._ID, _id);
        values.put(Colum.NAME, name);
        values.put(Colum.LEVEL, level);
        values.put(Colum.STATUS, status);
        values.put(Colum.EXPIRED_AT, expired_at);
        values.put(Colum.USED_COUNT, used_count);
        values.put(Colum.NODE_TYPE_ID, node_type_id);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    /**
     * 更新某一条记录
     *
     * @param bean
     */
    @Override
    public int update(SigninResponseV2.UserNodeTypesBean bean) {
        return update(bean.getId(), bean.getName(), bean.getLevel(), bean.getStatus(), bean.getExpired_at(), bean.getUsed_count(), bean.getNode_type_id());
    }

    public synchronized int update(int _id, String name, int level, String status, long expired_at, int used_count, int node_type_id) {
        ContentValues values = new ContentValues();
        values.put(Colum._ID, _id);
        values.put(Colum.NAME, name);
        values.put(Colum.LEVEL, level);
        values.put(Colum.STATUS, status);
        values.put(Colum.EXPIRED_AT, expired_at);
        values.put(Colum.USED_COUNT, used_count);
        values.put(Colum.NODE_TYPE_ID, node_type_id);
        int result = mDb.update(TABLE_NAME, values, Colum.LEVEL + "=?", new String[]{String.valueOf(level)});
        return result;
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
    public long insertOrUpdate(SigninResponseV2.UserNodeTypesBean bean) {
        return insertOrUpdate(bean.getId(), bean.getName(), bean.getLevel(), bean.getStatus(), bean.getExpired_at(), bean.getUsed_count(), bean.getNode_type_id());
    }

    public long insertOrUpdate(int _id, String name, int level, String status, long expired_at, int used_count, int node_type_id) {
        // 先判断该数据是否存在，已经存在就更新数据，不存在才插入该数据
        if (isExist(String.valueOf(level))) {
            return update(_id, name, level, status, expired_at, used_count, node_type_id);
        } else {
            return insert(_id, name, level, status, expired_at, used_count, node_type_id);
        }
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
    public boolean insertList(List<SigninResponseV2.UserNodeTypesBean> beanList) {
        boolean result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (SigninResponseV2.UserNodeTypesBean bean : beanList) {
                insertOrUpdate(bean);
            }
            mDb.setTransactionSuccessful();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }

        return result;
    }

    public boolean insertListRegisteredResponse(List<RegisteredResponse.UserNodeTypesBean> beanList) {
        boolean result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (RegisteredResponse.UserNodeTypesBean bean : beanList) {
                insertOrUpdate(bean.getId(), bean.getName(), bean.getLevel(), bean.getStatus()
                        , bean.getExpired_at(), bean.getUsed_count(), bean.getNode_type_id());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }

        return result;
    }


    /**
     * 根据ID查询某条记录
     *
     * @param findId
     * @return
     */
    @Override
    public SigninResponseV2.UserNodeTypesBean find(String findId) {
        SigninResponseV2.UserNodeTypesBean bean = null;
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where " + Colum.LEVEL + "=?", new String[]{findId});
        if (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            long expired_at = cursor.getInt(cursor.getColumnIndex(Colum.EXPIRED_AT));
            int level = cursor.getInt(cursor.getColumnIndex(Colum.LEVEL));
            int used_count = cursor.getInt(cursor.getColumnIndex(Colum.USED_COUNT));
            int node_type_id = cursor.getInt(cursor.getColumnIndex(Colum.NODE_TYPE_ID));
            String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
            String status = cursor.getString(cursor.getColumnIndex(Colum.STATUS));
            bean = new SigninResponseV2.UserNodeTypesBean(_id, name, level, status, expired_at, used_count, node_type_id);
        }

        cursor.close();
        return bean;
    }

    @Override
    public List<SigninResponseV2.UserNodeTypesBean> findAll() {
        List<SigninResponseV2.UserNodeTypesBean> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum._ID, Colum.NAME, Colum.LEVEL, Colum.STATUS, Colum.EXPIRED_AT, Colum.USED_COUNT, Colum.NODE_TYPE_ID}, null, null, null, null, Colum.LEVEL);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            long expired_at = cursor.getInt(cursor.getColumnIndex(Colum.EXPIRED_AT));
            int level = cursor.getInt(cursor.getColumnIndex(Colum.LEVEL));
            int used_count = cursor.getInt(cursor.getColumnIndex(Colum.USED_COUNT));
            int node_type_id = cursor.getInt(cursor.getColumnIndex(Colum.NODE_TYPE_ID));
            String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
            String status = cursor.getString(cursor.getColumnIndex(Colum.STATUS));
            result.add(new SigninResponseV2.UserNodeTypesBean(_id, name, level, status, expired_at, used_count, node_type_id));
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
        int result = mDb.delete(TABLE_NAME, Colum.LEVEL + "=?", new String[]{deletId});
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
        Cursor cursor = mDb.rawQuery("select " + Colum._ID + " from " + TABLE_NAME + " where " + Colum.LEVEL + "=?", new String[]{searchId});
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
     * 批量插入记录
     * 加入事务处理
     * 服务器列表中获取数据
     *
     * @param beanList
     * @return
     */
    public boolean insertListNodesResponse(List<NodesResponse.UserNodeTypesBean> beanList) {
        boolean result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (NodesResponse.UserNodeTypesBean bean : beanList) {
                insertOrUpdateNodesResponse(bean);
            }
            mDb.setTransactionSuccessful();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }

        return result;
    }

    /**
     * 插入或更新记录
     * 先查询该记录是否存在
     * 不存在执行插入操作，存在执行更新操作
     * 服务器列表
     *
     * @param bean
     * @return
     */
    public long insertOrUpdateNodesResponse(NodesResponse.UserNodeTypesBean bean) {
        return insertOrUpdateNodesResponse(bean.getId(), bean.getName(), bean.getLevel(), bean.getStatus(), bean.getExpired_at(), bean.getUsed_count(), bean.getNode_type_id());
    }

    public long insertOrUpdateNodesResponse(int _id, String name, int level, String status, long expired_at, int used_count, int node_type_id) {
        // 先判断该数据是否存在，已经存在就更新数据，不存在才插入该数据
        if (isExist(String.valueOf(level))) {
            return update(_id, name, level, status, expired_at, used_count, node_type_id);
        } else {
            return insert(_id, name, level, status, expired_at, used_count, node_type_id);
        }
    }


    /**
     * 根据ID查询某条记录
     *
     * @param findId
     * @return
     */
    public SigninResponse.UserNodeTypesBean findFormId(String findId) {
        SigninResponse.UserNodeTypesBean bean = null;
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where " + Colum.NODE_TYPE_ID + "=?", new String[]{findId});
        if (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            long expired_at = cursor.getInt(cursor.getColumnIndex(Colum.EXPIRED_AT));
            int level = cursor.getInt(cursor.getColumnIndex(Colum.LEVEL));
            int used_count = cursor.getInt(cursor.getColumnIndex(Colum.USED_COUNT));
            int node_type_id = cursor.getInt(cursor.getColumnIndex(Colum.NODE_TYPE_ID));
            String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
            String status = cursor.getString(cursor.getColumnIndex(Colum.STATUS));
            bean = new SigninResponse.UserNodeTypesBean(_id, name, level, status, expired_at, used_count, node_type_id);
        }
        cursor.close();
        return bean;
    }

    /**
     * 根据服务器类型ID更新服务器有效时间
     */
    public synchronized int updatetime(long expired_at, String node_type_id) {
//        LogUtils.i("expired_at----->" + expired_at);
//        LogUtils.i("node_type_id----->" + node_type_id);

        ContentValues values = new ContentValues();
        values.put(Colum.EXPIRED_AT, expired_at);
        int result = mDb.update(TABLE_NAME, values, Colum.NODE_TYPE_ID + "=?", new String[]{String.valueOf(node_type_id)});
        return result;
    }


}
