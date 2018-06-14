package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by XQ on 2017/11/27.
 * 杂物类，用于数据传输，各种变量值可以随时保存
 */
public class DebrisResponse {

    private String token;
    private int fatherposition;
    private boolean costCoinsflag;
    private Object ViewHolder;
    private int index;
    private List<NodesResponse.NodeTypesBean> nodeTypesBeanList;


    public List<NodesResponse.NodeTypesBean> getNodeTypesBeanList() {
        return nodeTypesBeanList;
    }

    public void setNodeTypesBeanList(List<NodesResponse.NodeTypesBean> nodeTypesBeanList) {
        this.nodeTypesBeanList = nodeTypesBeanList;
    }

    public boolean isCostCoinsflag() {
        return costCoinsflag;
    }

    public void setCostCoinsflag(boolean costCoinsflag) {
        this.costCoinsflag = costCoinsflag;
    }

    public int getFatherposition() {
        return fatherposition;
    }

    public void setFatherposition(int fatherposition) {
        this.fatherposition = fatherposition;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getViewHolder() {
        return ViewHolder;
    }

    public void setViewHolder(Object viewHolder) {
        ViewHolder = viewHolder;
    }
}
