package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by lmn on 2017/4/19 0019.
 * 获得套餐数据(认证) 解析类
 */
public class PlansResponse {

    private List<PlansBean> plans;

    public List<PlansBean> getPlans() {
        return plans;
    }

    public void setPlans(List<PlansBean> plans) {
        this.plans = plans;
    }

    @Override
    public String toString() {
        return "PlansResponse{" +
                "plans=" + plans +
                '}';
    }

    public static class PlansBean {

        public PlansBean() {
        }

        public PlansBean(int id, String name, String price, int coins, int present_coins, String description) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.coins = coins;
            this.present_coins = present_coins;
            this.description = description;
        }

        /**
         * id : 1
         * name : 体验套餐
         * price : 1.0
         * coins : 1
         * present_coins : 0
         * description : 这是一个体验套餐，1块钱1钻石
         */


        private int id;
        private String name;
        private String price;
        private int coins;
        private int present_coins;
        private String description;
        private boolean is_regular_time;//是否是包时间套餐: true包时或者包月，flase钻石买

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean is_regular_time() {
            return is_regular_time;
        }

        public void setIs_regular_time(boolean is_regular_time) {
            this.is_regular_time = is_regular_time;
        }

        @Override
        public String toString() {
            return "PlansBean{" +
                    "coins=" + coins +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    ", present_coins=" + present_coins +
                    ", description='" + description + '\'' +
                    ", is_regular_time=" + is_regular_time +
                    '}';
        }
    }
}
