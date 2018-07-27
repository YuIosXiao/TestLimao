package com.first.saccelerator.model;

import java.io.Serializable;

/**
 * Created by XQ on 2018/7/27.
 */

public class UserCenterResponse implements Serializable {

    private static final long serialVersionUID = -7620435178023928252L;

    private String name;
    private int imageid;
    private Object classname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public Object getClassname() {
        return classname;
    }

    public void setClassname(Object classname) {
        this.classname = classname;
    }
}
