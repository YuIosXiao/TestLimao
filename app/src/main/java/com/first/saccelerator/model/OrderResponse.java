package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by lmn on 2017/9/19 0019.
 * 获取当前用户的订单记录实体类
 */
public class OrderResponse {

        /**
         * logs : [{"order_number":"201707061499329408164948e1","created_at":1499329408,"has_paid":false,"plan_name":"体验套餐","price":"0.0","coins":0},{"order_number":"2017070414991512763976e2f5","created_at":1499151276,"has_paid":true,"plan_name":"体验套餐","price":"1.0","coins":5},{"order_number":"2017070414991505931447dbfe","created_at":1499150593,"has_paid":false,"plan_name":"普通套餐","price":"0.0","coins":0}]
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
             * order_number : 201707061499329408164948e1
             * created_at : 1499329408
             * has_paid : false
             * plan_name : 体验套餐
             * price : 0.0
             * coins : 0
             */

            private String order_number;
            private int created_at;
            private boolean has_paid;
            private String plan_name;
            private String price;
            private int coins;

            public String getOrder_number() {
                return order_number;
            }

            public void setOrder_number(String order_number) {
                this.order_number = order_number;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public boolean isHas_paid() {
                return has_paid;
            }

            public void setHas_paid(boolean has_paid) {
                this.has_paid = has_paid;
            }

            public String getPlan_name() {
                return plan_name;
            }

            public void setPlan_name(String plan_name) {
                this.plan_name = plan_name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getCoins() {
                return coins;
            }

            public void setCoins(int coins) {
                this.coins = coins;
            }
        }
    }
