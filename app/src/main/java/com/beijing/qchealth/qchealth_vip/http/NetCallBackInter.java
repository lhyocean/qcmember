package com.beijing.qchealth.qchealth_vip.http;

public interface NetCallBackInter {
	
	public void onStart();

	public void onSuccess(String mssage, ResultModel resultBody);

	public abstract void onFail(boolean dataError, int errorCode, String mssage) ;
	
	public void onCancel();

	public void onFinish();
	
	public void onProgress(int bytesWritten, int totalSize);
	


}
