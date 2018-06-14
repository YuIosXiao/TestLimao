package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.PlansResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmn on 2017/4/19 0019.
 * 套餐数据
 */
public class PlansListDataSource extends BaseDataSource<PlansResponse.PlansBean> {
    /**
     * 表名
     */
    private static final String TABLE_NAME = "plans_list";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String _ID = "_id";               // 1076
        String NAME = "name";             // "青铜服"
        String PRICE = "price";           // 1
        String COINS = "coins";         // "按次"
        String PRESENT_COINS = "present_coins"; // 1491553783
        String DESCRIPTION = "description"; // 0
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum._ID + " INTEGER, "
            + Colum.NAME + " varchar(50), "
            + Colum.PRICE + " varchar(50), "
            + Colum.COINS + " INTEGER, "
            + Colum.PRESENT_COINS + " INTEGER, "
            + Colum.DESCRIPTION + " varchar(50))";

    public PlansListDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(PlansResponse.PlansBean plansBean) {
        return insert(plansBean.getId(), plansBean.getName(), plansBean.getPrice()
                , plansBean.getCoins(), plansBean.getPresent_coins(), plansBean.getDescription());
    }

    private synchronized long insert(int id, String name, String price, int coins, int present_coins, String description) {
        ContentValues values = new ContentValues();
        values.put(Colum._ID, id);
        values.put(Colum.NAME, name);
        values.put(Colum.PRICE, price);
        values.put(Colum.COINS, coins);
        values.put(Colum.PRESENT_COINS, present_coins);
        values.put(Colum.DESCRIPTION, description);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<PlansResponse.PlansBean> list) {
        boolean result = false;
        // 批量插入开启事务能提高效率
        mDb.beginTransaction();
        try {
            for (PlansResponse.PlansBean bean : list) {
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
    public long insertOrUpdate(PlansResponse.PlansBean plansBean) {
        return insertOrUpdate(plansBean.getId(), plansBean.getName(), plansBean.getPrice()
                , plansBean.getCoins(), plansBean.getPresent_coins(), plansBean.getDescription());
    }

    private long insertOrUpdate(int id, String name, String price, int coins, int present_coins, String description) {
        // 先判断该数据是否存在，已经存在就更新数据，不存在才插入该数据
        if (isExist(String.valueOf(id))) {
            return update(id, name, price, coins, present_coins, description);
        } else {
            return insert(id, name, price, coins, present_coins, description);

        }
    }

    @Override
    public int update(PlansResponse.PlansBean plansBean) {
        return update(plansBean.getId(), plansBean.getName(), plansBean.getPrice()
                , plansBean.getCoins(), plansBean.getPresent_coins(), plansBean.getDescription());
    }

    private synchronized int update(int id, String name, String price, int coins, int present_coins, String description) {
        ContentValues values = new ContentValues();
        values.put(Colum._ID, id);
        values.put(Colum.NAME, name);
        values.put(Colum.PRICE, price);
        values.put(Colum.COINS, coins);
        values.put(Colum.PRESENT_COINS, present_coins);
        values.put(Colum.DESCRIPTION, description);
        int result = mDb.update(TABLE_NAME, values, Colum._ID + "=?", new String[]{String.valueOf(id)});
        return result;
    }

    @Override
    public PlansResponse.PlansBean find(String findId) {
        PlansResponse.PlansBean bean = null;
        Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME + " where " + Colum._ID + "=?", new String[]{findId});
        if (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
            String price = cursor.getString(cursor.getColumnIndex(Colum.PRICE));
            int coins = cursor.getInt(cursor.getColumnIndex(Colum.COINS));
            int present_coins = cursor.getInt(cursor.getColumnIndex(Colum.PRESENT_COINS));
            String description = cursor.getString(cursor.getColumnIndex(Colum.DESCRIPTION));
            bean = new PlansResponse.PlansBean(_id, name, price,coins,present_coins,description);
        }
        cursor.close();
        return bean;
    }

    @Override
    public List<PlansResponse.PlansBean> findAll() {
        PlansResponse.PlansBean bean = null;
        List<PlansResponse.PlansBean> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum._ID, Colum.NAME, Colum.PRICE, Colum.COINS, Colum.PRESENT_COINS, Colum.DESCRIPTION}, null, null, null, null, Colum._ID);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Colum._ID));
            String name = cursor.getString(cursor.getColumnIndex(Colum.NAME));
            String price = cursor.getString(cursor.getColumnIndex(Colum.PRICE));
            int coins = cursor.getInt(cursor.getColumnIndex(Colum.COINS));
            int present_coins = cursor.getInt(cursor.getColumnIndex(Colum.PRESENT_COINS));
            String description = cursor.getString(cursor.getColumnIndex(Colum.DESCRIPTION));
            bean = new PlansResponse.PlansBean();
            bean.setId(_id);
            bean.setName(name);
            bean.setPrice(price);
            bean.setCoins(coins);
            bean.setPresent_coins(present_coins);
            bean.setDescription(description);
            result.add(bean);
        }
        cursor.close();
        return result;
    }

    @Override
    public int delete(String deletId) {
        int result = mDb.delete(TABLE_NAME, Colum._ID + "=?", new String[]{deletId});
        return result;
    }

    @Override
    public void clear() {
        mDb.delete("delete from " + TABLE_NAME, null, null);
    }

    @Override
    public boolean isExist(String searchId) {
        Cursor cursor = mDb.rawQuery("select " + Colum._ID + " from " + TABLE_NAME + " where " + Colum._ID + "=?", new String[]{searchId});
        boolean result = cursor.moveToNext();
        cursor.close();
        return result;
    }

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



}
