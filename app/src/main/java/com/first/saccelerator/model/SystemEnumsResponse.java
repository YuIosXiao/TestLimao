package com.first.saccelerator.model;

import java.util.List;

/**
 * 获取版本渠道信息
 * Created by ZhengSheng on 2017/3/21.
 */

public class SystemEnumsResponse extends ApiResponse {

    private List<Enum> enums;

    public List<Enum> getEnums() {
        return enums;
    }

    public void setEnums(List<Enum> enums) {
        this.enums = enums;
    }

    @Override
    public String toString() {
        return "SystemEnumsResponse{" +
                "enums=" + enums +
                '}';
    }

    public static class Enum {

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Enum{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}