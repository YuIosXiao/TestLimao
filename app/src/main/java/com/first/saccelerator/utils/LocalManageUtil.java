package com.first.saccelerator.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import com.first.saccelerator.constants.SPConstants;

import java.util.Locale;

/**
 * Created by XQ on 2018/5/9.
 * 选择语言 工具类
 */

public class LocalManageUtil {


    private final SharedPreferences mSharedPreferences;

    private Locale systemCurrentLocal = Locale.CHINA;

    public LocalManageUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(SPConstants.SP_USE_LANGUAGE, Context.MODE_PRIVATE);
    }

    public void saveLanguage(int select) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(SPConstants.USELANGUAGE.TAG_LANGUAGE, select);
        edit.commit();
    }

    public int getSelectLanguage() {
        return mSharedPreferences.getInt(SPConstants.USELANGUAGE.TAG_LANGUAGE, 0);
    }

    public Locale getSystemCurrentLocal() {
        return systemCurrentLocal;
    }

    public void setSystemCurrentLocal(Locale local) {
        systemCurrentLocal = local;
    }

    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    public Locale getSystemLocale(Context context) {
        return getSystemCurrentLocal();
    }

    public String getSelectLanguage(Context context) {
        switch (getSelectLanguage()) {
            case 0:
                return "自动";
            case 1:
                return "中文简体";
            case 2:
                return "中文繁体";
            case 3:
            default:
                return "英文";
        }
    }

    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    public Locale getSetLanguageLocale(Context context) {

        switch (getSelectLanguage()) {
            case 0:
                return getSystemLocale(context);
            case 1:
                return Locale.CHINA;
            case 2:
                return Locale.TAIWAN;
            case 3:
                return Locale.ENGLISH;
            default:
                return Locale.CHINA;
        }
    }

    public void saveSelectLanguage(Context context, int select) {
        saveLanguage(select);
        setApplicationLanguage(context);
    }

    public Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 设置语言类型
     */
    public void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale(context);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

    public void saveSystemCurrentLanguage(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        Log.d("LocalManageUtil", "locale----->" + locale.getLanguage());
        if ("en".equals(locale.getLanguage())) {
            saveLanguage(3);
        } else {
            saveLanguage(1);
        }
        setSystemCurrentLocal(locale);
    }

    public void onConfigurationChanged(Context context) {
        saveSystemCurrentLanguage(context);
        setLocal(context);
        setApplicationLanguage(context);
    }
}
