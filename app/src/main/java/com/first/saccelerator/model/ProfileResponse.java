package com.first.saccelerator.model;

/**
 * Created by Z on 2017/4/22.
 */

public class ProfileResponse {


    /**
     * user : {"current_coins":9971}
     * group : {"id":1,"name":"vip0","level":1,"need_coins":0,"next_name":"VIP1","next_id":2,"next_need_coins":2}
     */

    private UserBean user;
    private GroupBean group;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public static class UserBean {
        /**
         * current_coins : 9971
         */

        private int current_coins;

        public int getCurrent_coins() {
            return current_coins;
        }

        public void setCurrent_coins(int current_coins) {
            this.current_coins = current_coins;
        }
    }

    public static class GroupBean {
        /**
         * id : 1
         * name : vip0
         * level : 1
         * need_coins : 0
         * next_name : VIP1
         * next_id : 2
         * next_need_coins : 2
         */

        private int id;
        private String name;
        private int level;
        private int need_coins;
        private String next_name;
        private int next_id;
        private int next_need_coins;

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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getNeed_coins() {
            return need_coins;
        }

        public void setNeed_coins(int need_coins) {
            this.need_coins = need_coins;
        }

        public String getNext_name() {
            return next_name;
        }

        public void setNext_name(String next_name) {
            this.next_name = next_name;
        }

        public int getNext_id() {
            return next_id;
        }

        public void setNext_id(int next_id) {
            this.next_id = next_id;
        }

        public int getNext_need_coins() {
            return next_need_coins;
        }

        public void setNext_need_coins(int next_need_coins) {
            this.next_need_coins = next_need_coins;
        }
    }
}
