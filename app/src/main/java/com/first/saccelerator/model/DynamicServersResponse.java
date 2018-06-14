package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by XQ on 2017/4/21.
 * 动态IP信息
 */
public class DynamicServersResponse {


    private String type;


    private List<ServersBean> servers;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ServersBean> getServers() {
        return servers;
    }

    public void setServers(List<ServersBean> servers) {
        this.servers = servers;
    }

    public static class ServersBean {
        /**
         * id : 1
         * url : https://xiaoguoqi.com/
         * region : 香港
         * priority : 1
         */

        private int id;
        private String url;
        private String region;
        private int priority;

        public ServersBean(int id, String url, String region, int priority) {
            this.id = id;
            this.url = url;
            this.region = region;
            this.priority = priority;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }
    }
}
