package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/12/18.
 * UserAgent实体类
 */
public class UserAgentResponse {

    private String browsertype;//浏览器类型(名称)
    private String browserversion;//浏览器版本号
    private String kerneltype;//内核名称
    private String kernelversion;//内核版本号


    public String getBrowsertype() {
        return browsertype;
    }

    public void setBrowsertype(String browsertype) {
        this.browsertype = browsertype;
    }

    public String getBrowserversion() {
        return browserversion;
    }

    public void setBrowserversion(String browserversion) {
        this.browserversion = browserversion;
    }

    public String getKerneltype() {
        return kerneltype;
    }

    public void setKerneltype(String kerneltype) {
        this.kerneltype = kerneltype;
    }

    public String getKernelversion() {
        return kernelversion;
    }

    public void setKernelversion(String kernelversion) {
        this.kernelversion = kernelversion;
    }
}
