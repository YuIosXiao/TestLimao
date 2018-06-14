package com.first.saccelerator.model;

/**
 * Created by lmn on 2017/4/20 0020.
 * 根据订单号获取订单状态(认证)
 */
public class TransactionStatusResponse {

    /**
     * is_completed : true
     * user : {"current_coins":3}
     * group : {"id":1,"name":"vip0","level":1,"need_coins":0}
     */

    private boolean is_completed;
    private UserBean user;
    private GroupBean group;

    public boolean isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }

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
         * current_coins : 3
         */

        private int current_coins;

        public int getCurrent_coins() {
            return current_coins;
        }

        public void setCurrent_coins(int current_coins) {
            this.current_coins = current_coins;
        }

        @Override
        public String toString() {
            return "UserBean{" +
                    "current_coins=" + current_coins +
                    '}';
        }
    }

    public static class GroupBean {
        /**
         * id : 1
         * name : vip0
         * level : 1
         * need_coins : 0
         */

        private int id;
        private String name;
        private int level;
        private int need_coins;

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

        @Override
        public String toString() {
            return "GroupBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", level=" + level +
                    ", need_coins=" + need_coins +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TransactionStatusResponse{" +
                "is_completed=" + is_completed +
                ", user=" + user +
                ", group=" + group +
                '}';
    }
}
