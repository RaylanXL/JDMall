package com.example.raylan.jdmall.bean;

/**
 * RResult，第一个R代表的是返回Response
 * Created by Raylan on 2017/7/10.
 */

public class RResult {
    private boolean success;
    private String errorMsg;
    private String result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
