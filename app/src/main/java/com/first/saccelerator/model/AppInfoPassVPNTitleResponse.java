package com.first.saccelerator.model;

import java.io.Serializable;

/**
 * Created by XQ on 2018/5/24.
 * 手机应用是否允许通过VPN标题 实体类
 */

public class AppInfoPassVPNTitleResponse implements Serializable {

    private static final long serialVersionUID = -7620435178023928252L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
