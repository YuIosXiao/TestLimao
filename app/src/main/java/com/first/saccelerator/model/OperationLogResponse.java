package com.first.saccelerator.model;

/**
 * 提交操作日志(认证)
 * Created by XQ on 2017/4/13.
 */

public class OperationLogResponse {

    private int operationid;  // 本次操作日志ID
    private long operationtime;//操作时间
    private String operationusename;//用户名
    private int operationuserid;//用户id

    public OperationLogResponse(int operationid, long operationtime, String operationusename, int operationuserid) {
        this.operationid = operationid;
        this.operationtime = operationtime;
        this.operationusename = operationusename;
        this.operationuserid = operationuserid;
    }

    public int getOperationuserid() {
        return operationuserid;
    }

    public void setOperationuserid(int operationuserid) {
        this.operationuserid = operationuserid;
    }

    public int getOperationid() {
        return operationid;
    }

    public void setOperationid(int operationid) {
        this.operationid = operationid;
    }

    public long getOperationtime() {
        return operationtime;
    }

    public void setOperationtime(long operationtime) {
        this.operationtime = operationtime;
    }

    public String getOperationusename() {
        return operationusename;
    }

    public void setOperationusename(String operationusename) {
        this.operationusename = operationusename;
    }

    @Override
    public String toString() {
        return "OperationLogResponse{" +
                "operationid=" + operationid +
                ", operationtime=" + operationtime +
                ", operationusename='" + operationusename + '\'' +
                ", operationuserid=" + operationuserid +
                '}';
    }
}
