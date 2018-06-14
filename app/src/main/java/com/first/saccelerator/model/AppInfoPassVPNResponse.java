package com.first.saccelerator.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by XQ on 2018/5/24.
 * 手机应用是否允许通过VPN 实体类
 */

public class AppInfoPassVPNResponse implements Serializable {

    private static final long serialVersionUID = -7620435178023928252L;

    public static class BaseBean {
        private int primaryid;
        private String name;
        private String packageName;
        private String packagePath;
        private String versionName;
        private String versionCode;
        private int isSystem;
        private int isAllowed;//是否被允许通过
        private Drawable icon;


        public BaseBean() {

        }

        public BaseBean(int primaryid, String name, String packageName, String packagePath, String versionName, String versionCode, int isSystem, int isAllowed) {
            this.primaryid = primaryid;
            this.name = name;
            this.packageName = packageName;
            this.packagePath = packagePath;
            this.versionName = versionName;
            this.versionCode = versionCode;
            this.isSystem = isSystem;
            this.isAllowed = isAllowed;
        }

        public int getPrimaryid() {
            return primaryid;
        }

        public void setPrimaryid(int primaryid) {
            this.primaryid = primaryid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(String packagePath) {
            this.packagePath = packagePath;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public int getIsSystem() {
            return isSystem;
        }

        public void setIsSystem(int isSystem) {
            this.isSystem = isSystem;
        }

        public int getIsAllowed() {
            return isAllowed;
        }

        public void setIsAllowed(int isAllowed) {
            this.isAllowed = isAllowed;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }
    }

    public static class FirstBean extends BaseBean {

    }


    public static class SecondBean extends BaseBean {

    }


}
