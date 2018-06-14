package com.first.saccelerator.model;

/**
 * Created by XQ on 2018/2/6.
 * 开屏广告实体类
 */
public class OpenScreenAdResponse {


    /**
     * is_enabled : true
     * overtime : 1801
     * ad : {"id":1,"duration":20,"image_url":"http://test.com/1.png","link_url":"http://test.com","updated_at":"2018-02-05 18:42:14"}
     */

    private boolean is_enabled;
    private long overtime;
    private AdBean ad;

    public boolean isIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public long getOvertime() {
        return overtime;
    }

    public void setOvertime(long overtime) {
        this.overtime = overtime;
    }

    public AdBean getAd() {
        return ad;
    }

    public void setAd(AdBean ad) {
        this.ad = ad;
    }

    public static class AdBean {
        /**
         * id : 1
         * duration : 20
         * image_url : http://test.com/1.png
         * link_url : http://test.com
         * updated_at : 2018-02-05 18:42:14
         */

        private int id;
        private int duration;
        private String image_url;
        private String link_url;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
