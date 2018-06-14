package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by XQ on 2017/6/9.
 * 获得帮助手册列表  实体类
 */
public class CommonProblemResponse {


    /**
     * help_manuals : [{"id":3,"title":"第一个标题3","content":"3第一个内容"},{"id":2,"title":"第一个标题","content":"第一个内容"}]
     * current_page : 1
     * total_pages : 1
     * total_count : 2
     */

    private int current_page;
    private int total_pages;
    private int total_count;
    private List<HelpManualsBean> help_manuals;

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

    public List<HelpManualsBean> getHelp_manuals() {
        return help_manuals;
    }

    public void setHelp_manuals(List<HelpManualsBean> help_manuals) {
        this.help_manuals = help_manuals;
    }

    public static class HelpManualsBean {
        /**
         * id : 3
         * title : 第一个标题3
         * content : 3第一个内容
         */

        private int id;
        private String title;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
