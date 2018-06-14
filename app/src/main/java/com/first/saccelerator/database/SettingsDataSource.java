package com.first.saccelerator.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.first.saccelerator.model.RegisteredResponse;
import com.first.saccelerator.model.SigninResponseV2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2017/4/20.
 * 登录获取中配置信息
 */
public class SettingsDataSource extends BaseDataSource<SigninResponseV2.SettingsBean> {


    /**
     * 表名
     */
    private static final String TABLE_NAME = "settings";

    /**
     * 表中字段名称
     */
    private interface Colum {
        String NOTICE_CONTENT = "notice_content";
        String SHARE_URL = "share_url";
        String ANDROID_VERSION = "android_version";
        String IS_UPDATE = "is_update";
        String UPDATE_CONTENT = "update_content";
        String SHARE_IMG = "share_img";
    }

    /**
     * 创建表的sql语句
     * 在DBHelper中使用并创建表
     */
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + "(" + " primaryid integer primary key autoincrement, "
            + Colum.NOTICE_CONTENT + " VARCHAR(500), "
            + Colum.SHARE_URL + " VARCHAR(300), "
            + Colum.ANDROID_VERSION + " VARCHAR(50), "
            + Colum.IS_UPDATE + " varchar(50), "
            + Colum.UPDATE_CONTENT + " varchar(500), "
            + Colum.SHARE_IMG + " varchar(300))";


    public SettingsDataSource(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long insert(SigninResponseV2.SettingsBean settingsBean) {
        return insert(settingsBean.getNOTICE_CONTENT(), settingsBean.getSHARE_URL(), settingsBean.getANDROID_VERSION(), settingsBean.getIS_UPDATE(), settingsBean.getUPDATE_CONTENT(), settingsBean.getSHARE_IMG());
    }

    private synchronized long insert(String notice_content, String share_url, String android_version, String is_update, String update_content, String share_img) {
        ContentValues values = new ContentValues();
        values.put(Colum.NOTICE_CONTENT, notice_content);
        values.put(Colum.SHARE_URL, share_url);
        values.put(Colum.ANDROID_VERSION, android_version);
        values.put(Colum.IS_UPDATE, is_update);
        values.put(Colum.UPDATE_CONTENT, update_content);
        values.put(Colum.SHARE_IMG, share_img);
//        LogUtils.i("operationid----->" + operationid);
//        LogUtils.i("operationtime----->" + operationtime);
//        LogUtils.i("operationusename----->" + operationusename);
        long result = mDb.insert(TABLE_NAME, null, values);
        return result;
    }

    @Override
    public boolean insertList(List<SigninResponseV2.SettingsBean> list) {
        return false;
    }

    @Override
    public long insertOrUpdate(SigninResponseV2.SettingsBean settingsBean) {
        return 0;
    }

    @Override
    public int update(SigninResponseV2.SettingsBean settingsBean) {
        return 0;
    }


    @Override
    public SigninResponseV2.SettingsBean find(String findId) {
        return null;
    }

    @Override
    public List<SigninResponseV2.SettingsBean> findAll() {
        List<SigninResponseV2.SettingsBean> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.NOTICE_CONTENT, Colum.SHARE_URL
                , Colum.ANDROID_VERSION, Colum.IS_UPDATE, Colum.UPDATE_CONTENT, Colum.SHARE_IMG}, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String notice_content = cursor.getString(cursor.getColumnIndex(Colum.NOTICE_CONTENT));
            String share_url = cursor.getString(cursor.getColumnIndex(Colum.SHARE_URL));
            String android_version = cursor.getString(cursor.getColumnIndex(Colum.ANDROID_VERSION));
            String is_update = cursor.getString(cursor.getColumnIndex(Colum.IS_UPDATE));
            String update_content = cursor.getString(cursor.getColumnIndex(Colum.UPDATE_CONTENT));
            String share_img = cursor.getString(cursor.getColumnIndex(Colum.SHARE_IMG));
            result.add(new SigninResponseV2.SettingsBean(notice_content, share_url, android_version, is_update, update_content, share_img));
        }
        cursor.close();
        return result;
    }

    public List<RegisteredResponse.SettingsBean> findAllRegisteredResponse() {
        List<RegisteredResponse.SettingsBean> result = new ArrayList<>();
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{Colum.NOTICE_CONTENT, Colum.SHARE_URL
                , Colum.ANDROID_VERSION, Colum.IS_UPDATE, Colum.UPDATE_CONTENT, Colum.SHARE_IMG}, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String notice_content = cursor.getString(cursor.getColumnIndex(Colum.NOTICE_CONTENT));
            String share_url = cursor.getString(cursor.getColumnIndex(Colum.SHARE_URL));
            String android_version = cursor.getString(cursor.getColumnIndex(Colum.ANDROID_VERSION));
            String is_update = cursor.getString(cursor.getColumnIndex(Colum.IS_UPDATE));
            String update_content = cursor.getString(cursor.getColumnIndex(Colum.UPDATE_CONTENT));
            String share_img = cursor.getString(cursor.getColumnIndex(Colum.SHARE_IMG));
            result.add(new RegisteredResponse.SettingsBean(notice_content, share_url, android_version, is_update, update_content, share_img));
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
    }

    @Override
    public boolean isExist(String searchId) {
        return false;
    }

    @Override
    public long getCount() {
        return 0;
    }
}
