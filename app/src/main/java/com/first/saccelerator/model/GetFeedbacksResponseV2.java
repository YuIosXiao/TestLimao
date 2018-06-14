package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by LMN on 2017/8/5 0005.
 * 获取反馈列表v2实体类
 */
public class GetFeedbacksResponseV2 {

        /**
         * feedbacks : [{"id":6,"type":2,"content":"The fact ","status":0,"created_at":1497235791,"has_read":false},{"id":5,"type":0,"content":"gtpup说体系嘻嘻嘻嘻嘻嘻嘻嘻 会谈中组图天他小心翼翼与天蝎座总有一个人在万事如意有些人","status":0,"created_at":1496987506,"has_read":false},{"id":4,"type":3,"content":"数据噼里啪啦就噼里啪啦","status":0,"created_at":1496986955,"has_read":false},{"id":3,"type":2,"content":"后所有者权益","status":1,"created_at":1496980423,"has_read":false}]
         * current_page : 1
         * total_pages : 1
         * total_count : 4
         */

        private int current_page;
        private int total_pages;
        private int total_count;
        private List<FeedbacksBean> feedbacks;

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

        public List<FeedbacksBean> getFeedbacks() {
            return feedbacks;
        }

        public void setFeedbacks(List<FeedbacksBean> feedbacks) {
            this.feedbacks = feedbacks;
        }

        public static class FeedbacksBean {
            /**
             * id : 6
             * type : 2
             * content : The fact
             * status : 0
             * created_at : 1497235791
             * has_read : false
             */

            private int id;
            private int type;
            private String content;
            private int status;
            private int created_at;
            private boolean has_read;

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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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
        }
    }
