package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by lmn on 2017/8/7 0007.
 * 获得指定反馈的回复列表(认证)
 */
public class GetFeedbacksDetailResponse {

    /**
     * logs : [{"id":52,"member":"我","content":"这是一个反馈回复4","created_at":1501672369},{"id":53,"member":"我","content":"这是一个反馈回复6","created_at":1501727647},{"id":54,"member":"我","content":"这是一个反馈回复7","created_at":1501727683}]
     * current_page : 1
     * total_pages : 1
     * total_count : 3
     * new_message：false
     */

    private int current_page;
    private int total_pages;
    private int total_count;
    private List<LogsBean> logs;
    private boolean new_message;

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


    public boolean isNew_message() {
        return new_message;
    }

    public void setNew_message(boolean new_message) {
        this.new_message = new_message;
    }

    public List<LogsBean> getLogs() {
        return logs;
    }

    public void setLogs(List<LogsBean> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "GetFeedbacksDetailResponse{" +
                "current_page=" + current_page +
                ", total_pages=" + total_pages +
                ", total_count=" + total_count +
                ", logs=" + logs +
                ", new_message=" + new_message +
                '}';
    }

    public static class LogsBean {
        /**
         * id : 52
         * member : 我
         * content : 这是一个反馈回复4
         * created_at : 1501672369
         */

        private int id;
        private String member;
        private String content;
        private int created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        @Override
        public String toString() {
            return "LogsBean{" +
                    "id=" + id +
                    ", member='" + member + '\'' +
                    ", content='" + content + '\'' +
                    ", created_at=" + created_at +
                    '}';
        }
    }
}
