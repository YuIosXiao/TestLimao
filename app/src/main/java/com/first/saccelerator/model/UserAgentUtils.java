package com.first.saccelerator.model;

import com.first.saccelerator.utils.StringUtils;

/**
 * Created by XQ on 2017/12/18.
 * 根据 user agent string 来判断出客户端的浏览器以及平台等信息
 */
public class UserAgentUtils {

    public UserAgentResponse judgeBrowser(String userAgent) {
        UserAgentResponse agentResponse = new UserAgentResponse();
        if (!StringUtils.isBlank(userAgent)) {
            if (userAgent.contains("mqqbrowser")) {// QQ浏览器
                agentResponse.setBrowsertype("mqqbrowser");

                String temp = userAgent.substring(userAgent.indexOf("mqqbrowser/") + 11);
                String browserversion = "";
                if (temp.indexOf(" ") < 0) {
                    browserversion = temp;
                } else {
                    browserversion = temp.substring(0, temp.indexOf(" "));
                }
                agentResponse.setBrowserversion(browserversion);

                judgeKernel(agentResponse, userAgent);
            } else if (userAgent.contains("ucbrowser")) {
                agentResponse.setBrowsertype("ucbrowser");

                String temp = userAgent.substring(userAgent.indexOf("ucbrowser/") + 10);
                String browserversion = "";
                if (temp.indexOf(" ") < 0) {
                    browserversion = temp;
                } else {
                    browserversion = temp.substring(0, temp.indexOf(" "));
                }
                agentResponse.setBrowserversion(browserversion);

                judgeKernel(agentResponse, userAgent);
            } else if (userAgent.contains("firefox")) {
                agentResponse.setBrowsertype("firefox");

                String temp = userAgent.substring(userAgent.indexOf("firefox/") + 8);
                String browserversion = "";
                if (temp.indexOf(" ") < 0) {
                    browserversion = temp;
                } else {
                    browserversion = temp.substring(0, temp.indexOf(" "));
                }
                agentResponse.setBrowserversion(browserversion);

                judgeKernel(agentResponse, userAgent);
            } else if (userAgent.contains("mb2345browser")) {
                agentResponse.setBrowsertype("mb2345browser");

                String temp = userAgent.substring(userAgent.indexOf("mb2345browser/") + 14);
                String browserversion = "";
                if (temp.indexOf(" ") < 0) {
                    browserversion = temp;
                } else {
                    browserversion = temp.substring(0, temp.indexOf(" "));
                }
                agentResponse.setBrowserversion(browserversion);

                judgeKernel(agentResponse, userAgent);
            } else if (userAgent.contains("liebaofast")) {
                agentResponse.setBrowsertype("liebaofast");

                String temp = userAgent.substring(userAgent.indexOf("liebaofast/") + 11);
                String browserversion = "";
                if (temp.indexOf(" ") < 0) {
                    browserversion = temp;
                } else {
                    browserversion = temp.substring(0, temp.indexOf(" "));
                }
                agentResponse.setBrowserversion(browserversion);

                judgeKernel(agentResponse, userAgent);
            } else if (userAgent.contains("sogoumobilebrowser")) {
                agentResponse.setBrowsertype("sogoumobilebrowser");

                String temp = userAgent.substring(userAgent.indexOf("sogoumobilebrowser/") + 19);
                String browserversion = "";
                if (temp.indexOf(" ") < 0) {
                    browserversion = temp;
                } else {
                    browserversion = temp.substring(0, temp.indexOf(" "));
                }
                agentResponse.setBrowserversion(browserversion);

                judgeKernel(agentResponse, userAgent);
            } else if (userAgent.contains("360se")) {
                agentResponse.setBrowsertype("360se");

                String temp = userAgent.substring(userAgent.indexOf("360se/") + 6);
                String browserversion = "";
                if (temp.indexOf(" ") < 0) {
                    browserversion = temp;
                } else {
                    browserversion = temp.substring(0, temp.indexOf(" "));
                }
                agentResponse.setBrowserversion(browserversion);

                judgeKernel(agentResponse, userAgent);
            } else if (userAgent.contains("maxthon")) {
                agentResponse.setBrowsertype("maxthon");

                String temp = userAgent.substring(userAgent.indexOf("maxthon/") + 8);
                String browserversion = "";
                if (temp.indexOf(" ") < 0) {
                    browserversion = temp;
                } else {
                    browserversion = temp.substring(0, temp.indexOf(" "));
                }
                agentResponse.setBrowserversion(browserversion);

                judgeKernel(agentResponse, userAgent);
            } else if (userAgent.contains("chrome")) {
                agentResponse.setBrowsertype("chrome");

                String temp = userAgent.substring(userAgent.indexOf("chrome/") + 7);
                String browserversion = "";
                if (temp.indexOf(" ") < 0) {
                    browserversion = temp;
                } else {
                    browserversion = temp.substring(0, temp.indexOf(" "));
                }
                agentResponse.setBrowserversion(browserversion);

                judgeKernel(agentResponse, userAgent);
            } else {
                agentResponse.setBrowsertype("未知");
                agentResponse.setBrowserversion("未知");
                judgeKernel(agentResponse, userAgent);
            }
        } else {
            agentResponse.setBrowsertype("");
            agentResponse.setBrowserversion("");
            agentResponse.setKerneltype("");
            agentResponse.setKernelversion("");
        }

        return agentResponse;
    }

    public UserAgentResponse judgeKernel(UserAgentResponse agentResponse, String userAgent) {
        if (userAgent.contains("applewebkit")) {// 苹果、谷歌内核
            agentResponse.setKerneltype("applewebkit");

            String temp1 = userAgent.substring(userAgent.indexOf("applewebkit/") + 12);
            String kernelversion = "";
            if (temp1.indexOf(" ") < 0) {
                kernelversion = temp1;
            } else {
                kernelversion = temp1.substring(0, temp1.indexOf(" "));
            }
            agentResponse.setKernelversion(kernelversion);
        } else if (userAgent.contains("trident")) {// IE内核
            agentResponse.setKerneltype("trident");

            String temp1 = userAgent.substring(userAgent.indexOf("trident/") + 8);
            String kernelversion = "";
            if (temp1.indexOf(" ") < 0) {
                kernelversion = temp1;
            } else {
                kernelversion = temp1.substring(0, temp1.indexOf(" "));
            }
            agentResponse.setKernelversion(kernelversion);
        } else if (userAgent.contains("gecko")) {// 火狐内核
            agentResponse.setKerneltype("gecko");

            String temp1 = userAgent.substring(userAgent.indexOf("gecko/") + 6);
            String kernelversion = "";
            if (temp1.indexOf(" ") < 0) {
                kernelversion = temp1;
            } else {
                kernelversion = temp1.substring(0, temp1.indexOf(" "));
            }
            agentResponse.setKernelversion(kernelversion);
        } else if (userAgent.contains("presto")) {// opera内核
            agentResponse.setKerneltype("presto");

            String temp1 = userAgent.substring(userAgent.indexOf("presto/") + 7);
            String kernelversion = "";
            if (temp1.indexOf(" ") < 0) {
                kernelversion = temp1;
            } else {
                kernelversion = temp1.substring(0, temp1.indexOf(" "));
            }
            agentResponse.setKernelversion(kernelversion);
        } else {
            agentResponse.setKerneltype("未知");
            agentResponse.setKernelversion("未知");
        }

        return agentResponse;
    }


}
