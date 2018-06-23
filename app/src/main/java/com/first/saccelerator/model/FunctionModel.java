
package com.first.saccelerator.model;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class FunctionModel {

    private int icon;
    private String id;
    private String name;
    private Intent mIntent;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }

    public static List<FunctionModel> getFunctionList(Context context, String[] classname_, Object[] class_, int[] image_) {
        List<FunctionModel> functionModels = new ArrayList<>();

        for (int i = 0; i < classname_.length; i++) {
            FunctionModel appUninstall = new FunctionModel();
            appUninstall.setName(classname_[i]);
            appUninstall.setIcon(image_[i]);
            appUninstall.setIntent(new Intent(context, (Class<?>) class_[i]));
            functionModels.add(appUninstall);
        }
//        functionModels.add(appUninstall1);
//        functionModels.add(appUninstall2);
        return functionModels;
    }
}
