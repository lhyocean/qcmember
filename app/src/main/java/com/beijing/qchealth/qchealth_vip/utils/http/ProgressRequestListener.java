package com.beijing.qchealth.qchealth_vip.utils.http;

public interface ProgressRequestListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}