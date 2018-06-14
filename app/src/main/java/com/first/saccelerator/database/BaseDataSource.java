package com.first.saccelerator.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 *  数据库中表的管理基类
 * Created by ZhengSheng on 2017/3/20.
 */

public abstract class BaseDataSource<T> {

    protected SQLiteDatabase mDb;

    public BaseDataSource(DBHelper dbHelper) {
        mDb = dbHelper.getWritableDatabase();
    }

    /**
     * 关闭数据库，释放资源
     */
    public void close() {
        if (mDb != null) {
            mDb.close();
            mDb = null;
        }
    }

    /**
     * 插入一条记录
     * @param t
     * @return
     */
    public abstract long insert(T t);

    /**
     * 批量插入记录
     * 使用事务处理效率更高，同时也避免了脏数据
     * @param list
     * @return
     */
    public abstract boolean insertList(List<T> list);

    /**
     * 插入或更新记录
     * 先查询该记录是否存在
     * 不存在执行插入操作，存在执行更新操作
     * @param t
     * @return
     */
    public abstract long insertOrUpdate(T t);

    /**
     * 更新一条记录
     * @param t
     * @return
     */
    public abstract int update(T t);

    /**
     * 根据ID查询某一条记录
     * @param findId
     * @return
     */
    public abstract T find(String findId);

    /**
     * 查询所有的记录
     * @return
     */
    public abstract List<T> findAll();

    /**
     * 根据ID删除某一条记录
     * @param deletId
     * @return
     */
    public abstract int delete(String deletId);

    /**
     * 清空表中的所有记录
     */
    public abstract void clear();

    /**
     * 根据ID判断某条记录是否存在
     * @param searchId
     * @return
     */
    public abstract boolean isExist(String searchId);

    /**
     * 获取记录条数
     * @return
     */
    public abstract long getCount();

}