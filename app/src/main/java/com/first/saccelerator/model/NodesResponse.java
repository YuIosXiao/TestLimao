package com.first.saccelerator.model;

import java.util.List;

/**
 * 获得所有服务器节点数据(认证)
 * Created by XQ on 2017/4/07.
 */

public class NodesResponse {


    private List<UserNodeTypesBean> user_node_types;
    private List<NodeTypesBean> node_types;

    private boolean show_icon;

    public boolean isShow_icon() {
        return show_icon;
    }

    public void setShow_icon(boolean show_icon) {
        this.show_icon = show_icon;
    }

    public List<UserNodeTypesBean> getUser_node_types() {
        return user_node_types;
    }

    public void setUser_node_types(List<UserNodeTypesBean> user_node_types) {
        this.user_node_types = user_node_types;
    }

    public List<NodeTypesBean> getNode_types() {
        return node_types;
    }


    public void setNode_types(List<NodeTypesBean> node_types) {
        this.node_types = node_types;
    }

    public static class UserNodeTypesBean {
        /**
         * id : 861
         * name : 青铜服
         * level : 1
         * status : 按次
         * expired_at : 1491533894
         * used_count : 0
         * node_type_id : 1
         */

        private int id;
        private String name;
        private int level;
        private String status;
        private int expired_at;
        private int used_count;
        private int node_type_id;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(int expired_at) {
            this.expired_at = expired_at;
        }

        public int getUsed_count() {
            return used_count;
        }

        public void setUsed_count(int used_count) {
            this.used_count = used_count;
        }

        public int getNode_type_id() {
            return node_type_id;
        }

        public void setNode_type_id(int node_type_id) {
            this.node_type_id = node_type_id;
        }
    }

    public static class NodeTypesBean {
        /**
         * id : 1
         * name : 青铜服
         * level : 1
         * expense_coins : 1
         * user_group_id : 1
         * user_group_level : 1
         * description:"测试1"
         * node_regions : [{"id":3,"name":"美国","abbr":"us","nodes":[{"id":7,"name":"美国01","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0},{"id":8,"name":"美国02","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0},{"id":9,"name":"美国03","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0}]}]
         */

        private int index;
        private int id;
        private String name;
        private int level;
        private int expense_coins;
        private int user_group_id;
        private int user_group_level;
        private String description;
        private List<NodeRegionsBean> node_regions;
        private int tab_id;

        public int getTab_id() {
            return tab_id;
        }

        public void setTab_id(int tab_id) {
            this.tab_id = tab_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

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

        public int getExpense_coins() {
            return expense_coins;
        }

        public void setExpense_coins(int expense_coins) {
            this.expense_coins = expense_coins;
        }

        public int getUser_group_id() {
            return user_group_id;
        }

        public void setUser_group_id(int user_group_id) {
            this.user_group_id = user_group_id;
        }

        public int getUser_group_level() {
            return user_group_level;
        }

        public void setUser_group_level(int user_group_level) {
            this.user_group_level = user_group_level;
        }

        public List<NodeRegionsBean> getNode_regions() {
            return node_regions;
        }

        public void setNode_regions(List<NodeRegionsBean> node_regions) {
            this.node_regions = node_regions;
        }

        public static class NodeRegionsBean {
            /**
             * id : 3
             * name : 美国
             * abbr : us
             * nodes : [{"id":7,"name":"美国01","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0},{"id":8,"name":"美国02","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0},{"id":9,"name":"美国03","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0}]
             */

            private int id;
            private String name;
            private String abbr;
            private List<NodesBean> nodes;


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

            public String getAbbr() {
                return abbr;
            }

            public void setAbbr(String abbr) {
                this.abbr = abbr;
            }

            public List<NodesBean> getNodes() {
                return nodes;
            }

            public void setNodes(List<NodesBean> nodes) {
                this.nodes = nodes;
            }

            public static class NodesBean {
                /**
                 * id : 7
                 * name : 美国01
                 * url : 119.23.64.170
                 * port : 8388
                 * password : asdf
                 * encrypt_method : aes-128-cfb
                 * connections_count : 0
                 */

                private int id;
                private String name;
                private String url;
                private int port;
                private String password;
                private String encrypt_method;
                private int connections_count;

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

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getPort() {
                    return port;
                }

                public void setPort(int port) {
                    this.port = port;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getEncrypt_method() {
                    return encrypt_method;
                }

                public void setEncrypt_method(String encrypt_method) {
                    this.encrypt_method = encrypt_method;
                }

                public int getConnections_count() {
                    return connections_count;
                }

                public void setConnections_count(int connections_count) {
                    this.connections_count = connections_count;
                }
            }
        }
    }
}
