package com.first.saccelerator.model;

import java.util.List;

/**
 * +
 * 用户签到(认证)
 * Created by Z on 2017/4/14.
 */
public class CheckinResponse {

    /**
     * has_checkin_today : false
     * expired_at : 1490961394
     * days_of_month : 3
     * checkin_days : 3
     * last_checkin_at : 1490950806
     * present_time : 1
     * calendar : [29,30,31]
     */
    private boolean has_checkin_today; // false:今天第一次调签到接口；true:已经签到过再调签到接口
    private int expired_at;            // 用户签到并奖励时间后的青铜服务器类型的新过期时间
    private int days_of_month;         // 本月签到的天数
    private int checkin_days;          // 连续签到的天数
    private int last_checkin_at;       // 最后签到日期
    private int present_time;          // 赠送时间(小时)
    private List<Integer> calendar;    // 本月签到的日期列表(Array)

    public int getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(int expired_at) {
        this.expired_at = expired_at;
    }

    public int getDays_of_month() {
        return days_of_month;
    }

    public void setDays_of_month(int days_of_month) {
        this.days_of_month = days_of_month;
    }

    public int getCheckin_days() {
        return checkin_days;
    }

    public void setCheckin_days(int checkin_days) {
        this.checkin_days = checkin_days;
    }

    public int getLast_checkin_at() {
        return last_checkin_at;
    }

    public void setLast_checkin_at(int last_checkin_at) {
        this.last_checkin_at = last_checkin_at;
    }

    public int getPresent_time() {
        return present_time;
    }

    public void setPresent_time(int present_time) {
        this.present_time = present_time;
    }

    public List<Integer> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<Integer> calendar) {
        this.calendar = calendar;
    }

    public boolean isHas_checkin_today() {
        return has_checkin_today;
    }

    public void setHas_checkin_today(boolean has_checkin_today) {
        this.has_checkin_today = has_checkin_today;
    }

    @Override
    public String toString() {
        return "CheckinResponse{" +
                "has_checkin_today=" + has_checkin_today +
                ", expired_at=" + expired_at +
                ", days_of_month=" + days_of_month +
                ", checkin_days=" + checkin_days +
                ", last_checkin_at=" + last_checkin_at +
                ", present_time=" + present_time +
                ", calendar=" + calendar +
                '}';
    }
}
