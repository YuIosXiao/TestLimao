package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by lmn on 2017/7/20 0020.
 * 网站导航列表
 */
public class WebsitesResponse {

    /**
     * websites : [{"id":1,"sort_id":1,"name":"非死不可","url":"http://facebook.com","icon_url":"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png","description":"facebook"}]
     * current_page : 1
     * total_pages : 1
     */

    private int current_page;
    private int total_pages;
    private List<WebsitesBean> websites;

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

    public List<WebsitesBean> getWebsites() {
        return websites;
    }

    public void setWebsites(List<WebsitesBean> websites) {
        this.websites = websites;
    }

    @Override
    public String toString() {
        return "WebsitesResponse{" +
                "current_page=" + current_page +
                ", total_pages=" + total_pages +
                ", websites=" + websites +
                '}';
    }

    public static class WebsitesBean {
        /**
         * id : 1
         * sort_id : 1
         * website_type:网站标识, 1:推荐, 2:火热
         * name : 非死不可
         * url : http://facebook.com
         * icon_url : https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png
         * description : facebook
         */

        private int id;
        private int sort_id;
        private int website_type;
        private String name;
        private String url;
        private String icon_url;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSort_id() {
            return sort_id;
        }

        public void setSort_id(int sort_id) {
            this.sort_id = sort_id;
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

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getWebsite_type() {
            return website_type;
        }

        public void setWebsite_type(int website_type) {
            this.website_type = website_type;
        }

        @Override
        public String toString() {
            return "WebsitesBean{" +
                    "id=" + id +
                    ", sort_id=" + sort_id +
                    ", website_type=" + website_type +
                    ", name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", icon_url='" + icon_url + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
