package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by XQ on 2017/9/18.
 * 我的消费 实体类
 */
public class MyExpensesRecordResponse {


    /**
     * logs : [{"created_at":1499220797,"node_type_name":"王者服务","coins":3},{"created_at":1499155514,"node_type_name":"精英服务","coins":1},{"created_at":1499067594,"node_type_name":"王者服务","coins":3}]
     * current_page : 1
     * total_pages : 3
     * total_count : 26
     */

    private int current_page;
    private int total_pages;
    private int total_count;
    private List<LogsBean> logs;

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<LogsBean> getLogs() {
        return logs;
    }

    public void setLogs(List<LogsBean> logs) {
        this.logs = logs;
    }

    public static class LogsBean {
        /**
         * created_at : 1499220797
         * node_type_name : 王者服务
         * coins : 3
         */

        private long created_at;
        private String node_type_name;
        private int coins;

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public String getNode_type_name() {
            return node_type_name;
        }

        public void setNode_type_name(String node_type_name) {
            this.node_type_name = node_type_name;
        }

        public int getCoins() {
            return coins;
        }

        public void setCoins(int coins) {
            this.coins = coins;
        }
    }
}
