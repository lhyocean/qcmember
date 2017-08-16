package com.beijing.qchealth.qchealth_vip.http;


public abstract class NetCallBack implements NetCallBackInter {
    @Override
    public void onStart() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onProgress(int bytesWritten, int totalSize) {

    }

    public abstract void onSuccess(String mssage, ResultModel model);

    public abstract void onFail(boolean dataError, int errorCode, String mssage);


}
