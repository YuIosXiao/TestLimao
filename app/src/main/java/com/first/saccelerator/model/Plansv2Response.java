package com.first.saccelerator.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XQ on 2018/3/24.
 * 获得套餐数据(认证)
 */
public class Plansv2Response {
    private List<TabsBean> tabs;

    public List<TabsBean> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabsBean> tabs) {
        this.tabs = tabs;
    }

    public static class TabsBean implements Serializable {
        /**
         * id : 1
         * name : 精英套餐
         * is_recommend : false
         * description : 访问Facebook、twitter、instagram等网站；使用telegram等通讯工具。
         * plans : [{"id":5,"name":"1个月","price":"21.0","currency_symbol":"¥","is_iap":false,"iap_id":"","is_regular_time":true,"coins":0,"present_coins":0,"is_recommend":false,"description1":"¥24","description2":"立省¥3","description3":"0.7/天"}]
         */

        /**
         * serialVersionUID的作用是在修改实体类后，可以正常的序列化和反序列化，在此不多说，大家可以谷歌百度下。
         */

        private static final long serialVersionUID = -7620435178023928252L;
        private int id;
        private String name;
        private boolean is_recommend;
        private String description;
        private List<PlansBean> plans;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(boolean is_recommend) {
            this.is_recommend = is_recommend;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<PlansBean> getPlans() {
            return plans;
        }

        public void setPlans(List<PlansBean> plans) {
            this.plans = plans;
        }

        public static class PlansBean implements Serializable {
            /**
             * id : 5
             * name : 1个月
             * price : 21.0
             * currency_symbol : ¥
             * is_iap : false
             * iap_id :
             * is_regular_time : true
             * coins : 0
             * present_coins : 0
             * is_recommend : false
             * description1 : ¥24
             * description2 : 立省¥3
             * description3 : 0.7/天
             */
            private static final long serialVersionUID = -7620435178023928252L;
            private int id;
            private String name;
            private String price;
            private String currency_symbol;
            private boolean is_iap;
            private String iap_id;
            private boolean is_regular_time;
            private int coins;
            private int present_coins;
            private boolean is_recommend;
            private String description1;
            private String description2;
            private String description3;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCurrency_symbol() {
                return currency_symbol;
            }

            public void setCurrency_symbol(String currency_symbol) {
                this.currency_symbol = currency_symbol;
            }

            public boolean isIs_iap() {
                return is_iap;
            }

            public void setIs_iap(boolean is_iap) {
                this.is_iap = is_iap;
            }

            public String getIap_id() {
                return iap_id;
            }

            public void setIap_id(String iap_id) {
                this.iap_id = iap_id;
            }

            public boolean isIs_regular_time() {
                return is_regular_time;
            }

            public void setIs_regular_time(boolean is_regular_time) {
                this.is_regular_time = is_regular_time;
            }

            public int getCoins() {
                return coins;
            }

            public void setCoins(int coins) {
                this.coins = coins;
            }

            public int getPresent_coins() {
                return present_coins;
            }

            public void setPresent_coins(int present_coins) {
                this.present_coins = present_coins;
            }

            public boolean isIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(boolean is_recommend) {
                this.is_recommend = is_recommend;
            }

            public String getDescription1() {
                return description1;
            }

            public void setDescription1(String description1) {
                this.description1 = description1;
            }

            public String getDescription2() {
                return description2;
            }

            public void setDescription2(String description2) {
                this.description2 = description2;
            }

            public String getDescription3() {
                return description3;
            }

            public void setDescription3(String description3) {
                this.description3 = description3;
            }
        }
    }
}
