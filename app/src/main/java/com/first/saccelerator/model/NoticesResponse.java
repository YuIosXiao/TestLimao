package com.first.saccelerator.model;

/**
 * 获得公告数据(认证)
 * Created by ZhengSheng on 2017/3/22.
 */

public class NoticesResponse extends ApiResponse {


    /**
     * notice : {"id":2,"content":"诺贝尔化学奖 （瑞典语：Nobelpriset i kemi）是诺贝尔奖的六个奖项之一，1895年设立，由瑞典皇家科学院每年..","created_at":1489995466}
     */

    private NoticeBean notice;

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return "NoticesResponse{" +
                "notice=" + notice +
                '}';
    }

    public static class NoticeBean {
        /**
         * id : 2
         * content : 诺贝尔化学奖 （瑞典语：Nobelpriset i kemi）是诺贝尔奖的六个奖项之一，1895年设立，由瑞典皇家科学院每年..
         * created_at : 1489995466
         */

        private int id;           // 公告ID
        private String content;   // 公告内容
        private long created_at;  // 公告创建时间

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        @Override
        public String toString() {
            return "NoticeBean{" +
                    "id=" + id +
                    ", content='" + content + '\'' +
                    ", created_at=" + created_at +
                    '}';
        }
    }
}
