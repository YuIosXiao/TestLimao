package com.first.saccelerator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.facebook.stetho.common.LogUtil;

/**
 * 数据库帮助类
 * Created by ZhengSheng on 2017/3/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    // 数据库名
    private static final String DB_NAME = "saccelerator.db";
    // 数据库版本
    private static final int VERSION = 5;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建表
        sqLiteDatabase.execSQL(ServerVaildPeriodDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(ServerListRegionsDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(ServerListNodesDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(LoginRecordDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(OperationLogDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(ServerListNodeTypesDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(LoginFailedDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(NoOperationDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(ConnectionFailedDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(PlansListDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(SettingsDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(DynamicServersDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(ProxyLogDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(DynamicServerFailedDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(ProxyIpDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(RescueIPDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(UserhttpheadDataSource.CREATE_TABLE_SQL);
        sqLiteDatabase.execSQL(AppInfoPassVPNDataSource.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        LogUtil.i("onUpgrade--newVersion--->" + newVersion);
        switch (newVersion) {
            case 2:
                sqLiteDatabase.execSQL(ProxyIpDataSource.CREATE_TABLE_SQL);
                break;
            case 3:
                sqLiteDatabase.execSQL(ProxyIpDataSource.CREATE_TABLE_SQL);
                sqLiteDatabase.execSQL(RescueIPDataSource.CREATE_TABLE_SQL);
                break;
            case 4:
                sqLiteDatabase.execSQL(ProxyIpDataSource.CREATE_TABLE_SQL);
                sqLiteDatabase.execSQL(RescueIPDataSource.CREATE_TABLE_SQL);
                sqLiteDatabase.execSQL(UserhttpheadDataSource.CREATE_TABLE_SQL);
                break;
            case 5:
                sqLiteDatabase.execSQL(ProxyIpDataSource.CREATE_TABLE_SQL);
                sqLiteDatabase.execSQL(RescueIPDataSource.CREATE_TABLE_SQL);
                sqLiteDatabase.execSQL(UserhttpheadDataSource.CREATE_TABLE_SQL);
                sqLiteDatabase.execSQL(AppInfoPassVPNDataSource.CREATE_TABLE_SQL);
                break;
        }
    }
}
