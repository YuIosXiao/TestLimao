package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by Z on 2017/4/17.
 */

public class GetFeedbacksResponse {


    /**
     * feedbacks : [{"id":1,"type":1,"content":"程序经常闪退，怎么办？","is_processed":false,"processed_at":0,"reply_content":null,"created_at":1490252180,"has_read":true,"has_read_at":null}]
     * current_page : 1
     * total_pages : 1
     */

    private int current_page;
    private int total_pages;
    private List<FeedbacksBean> feedbacks;
    private int total_count;


    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

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

    public List<FeedbacksBean> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbacksBean> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public static class FeedbacksBean {
        /**
         * id : 1
         * type : 1
         * content : 程序经常闪退，怎么办？
         * is_processed : false
         * processed_at : 0
         * reply_content : null
         * created_at : 1490252180
         * has_read : true
         * has_read_at : null
         */

        private int id;
        private int type;
        private String content;
        private boolean is_processed;
        private int processed_at;
        private String reply_content;
        private int created_at;
        private boolean has_read;
        private String has_read_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isIs_processed() {
            return is_processed;
        }

        public void setIs_processed(boolean is_processed) {
            this.is_processed = is_processed;
        }

        public int getProcessed_at() {
            return processed_at;
        }

        public void setProcessed_at(int processed_at) {
            this.processed_at = processed_at;
        }

        public String getReply_content() {
            return reply_content;
        }

        public void setReply_content(String reply_content) {
            this.reply_content = reply_content;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public boolean isHas_read() {
            return has_read;
        }

        public void setHas_read(boolean has_read) {
            this.has_read = has_read;
        }

        public String getHas_read_at() {
            return has_read_at;
        }

        public void setHas_read_at(String has_read_at) {
            this.has_read_at = has_read_at;
        }
    }
}
