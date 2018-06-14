package com.first.saccelerator.model;

/**
 * Created by XQ on 2018/2/8.
 * 弹出广告实体类
 */
public class PopUpAdResponse {


    /**
     * is_enabled : false
     * day_times : 500
     * image_url : http://p3pgf4sih.bkt.clouddn.com/popup_01.png
     * link_url : http://qq.com
     * updated_at : 1970-01-01 12:00:00
     */

    private boolean is_enabled;
    private int day_times;
    private String image_url;
    private String link_url;
    private String updated_at;

    public boolean isIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public int getDay_times() {
        return day_times;
    }

    public void setDay_times(int day_times) {
        this.day_times = day_times;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
