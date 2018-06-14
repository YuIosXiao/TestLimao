package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.DynamicServersResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/21.
 * 动态IP  数据保存
 */
public class DynamicServersDataSource extends BaseDataSource<DynamicServersResponse.ServersBean> {

    /**
     * 表名
     */
    private static final String TABLE_NAME = "dynamic_servers";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String ID = "id";//服务器ID
        String URL = "url";
        String REGION = "region";//服务器区域
        String PRIORITY = "priority";//优先级
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.ID + " INTEGER, "
            + Colum.URL + " VARCHAR(50), "
            + Colum.REGION + " VARCHAR(50), "
            + Colum.PRIORITY + " INTEGER)";


    public DynamicServersDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(DynamicServersResponse.ServersBean serversBean) {
        return insert(serversBean.getId(), serversBean.getUrl(), serversBean.getRegion(), serversBean.getPriority());
    }

    private synchronized long insert(int id, String url, String region, int priority) {
        //        LogUtils.i("id----->" + id);
//        LogUtils.i("createdtime----->" + createdtime);
//        LogUtils.i("username----->" + username);
//        LogUtils.i("password----->" + password);
//        LogUtils.i("logintime----->" + logintime);

        ContentValues values = new ContentValues();
        values.put(Colum.ID, id);
        values.put(Colum.URL, url);
        values.put(Colum.REGION, region);
        values.put(Colum.PRIORITY, priority);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<DynamicServersResponse.ServersBean> list) {
        boolean result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (DynamicServersResponse.ServersBean bean : list) {
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

    @Override
    public long insertOrUpdate(DynamicServersResponse.ServersBean serversBean) {
        return insertOrUpdate(serversBean.getId(), serversBean.getUrl(), serversBean.getRegion(), serversBean.getPriority());
    }

    private long insertOrUpdate(int id, String url, String region, int priority) {
        // 先判断该数据是否存在，已经存在就更新数据，不存在才插入该数据
        if (isExist(String.valueOf(id))) {
            return update(id, url, region, priority);
        } else {
            return insert(id, url, region, priority);
        }
    }

    @Override
    public int update(DynamicServersResponse.ServersBean serversBean) {
        return update(serversBean.getId(), serversBean.getUrl(), serversBean.getRegion(), serversBean.getPriority());
    }

    private synchronized int update(int id, String url, String region, int priority) {
        ContentValues values = new ContentValues();
        values.put(Colum.ID, id);
        values.put(Colum.URL, url);
        values.put(Colum.REGION, region);
        values.put(Colum.PRIORITY, priority);
        int result = mDb.update(TABLE_NAME, values, Colum.ID + "=?", new String[]{String.valueOf(id)});
        return result;
    }

    @Override
    public DynamicServersResponse.ServersBean find(String findId) {
        DynamicServersResponse.ServersBean bean = null;
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where " + Colum.ID + "=?", new String[]{findId});
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Colum.ID));
            String url = cursor.getString(cursor.getColumnIndex(Colum.URL));
            String region = cursor.getString(cursor.getColumnIndex(Colum.REGION));
            int priority = cursor.getInt(cursor.getColumnIndex(Colum.PRIORITY));
            bean = new DynamicServersResponse.ServersBean(id, url, region, priority);
        }
        cursor.close();
        return bean;
    }

    @Override
    public List<DynamicServersResponse.ServersBean> findAll() {
        List<DynamicServersResponse.ServersBean> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_NAME, new String[]{Colum.ID, Colum.URL, Colum.REGION, Colum.PRIORITY}, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(Colum.ID));
                    String url = cursor.getString(cursor.getColumnIndex(Colum.URL));
                    String region = cursor.getString(cursor.getColumnIndex(Colum.REGION));
                    int priority = cursor.getInt(cursor.getColumnIndex(Colum.PRIORITY));
                    result.add(new DynamicServersResponse.ServersBean(id, url, region, priority));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 查找数据通过primaryid
     */
    public DynamicServersResponse.ServersBean findPrimaryid(String rowid) {
        DynamicServersResponse.ServersBean bean = null;
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where primaryid =?", new String[]{rowid});
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Colum.ID));
            String url = cursor.getString(cursor.getColumnIndex(Colum.URL));
            String region = cursor.getString(cursor.getColumnIndex(Colum.REGION));
            int priority = cursor.getInt(cursor.getColumnIndex(Colum.PRIORITY));
            bean = new DynamicServersResponse.ServersBean(id, url, region, priority);
        }
        cursor.close();
        return bean;
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
        Cursor cursor = mDb.rawQuery("select " + Colum.ID + " from " + TABLE_NAME + " where " + Colum.ID + "=?", new String[]{searchId});
        boolean result = cursor.moveToNext();
        cursor.close();
        return result;
    }

    @Override
    public long getCount() {
        return 0;
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
