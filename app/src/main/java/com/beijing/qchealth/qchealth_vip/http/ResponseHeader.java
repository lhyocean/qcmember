package com.beijing.qchealth.qchealth_vip.http;

/**
 * Created by lhy on 2017/7/27.
 */

public class ResponseHeader {
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
