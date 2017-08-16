package com.beijing.qchealth.qchealth_vip.http;

import java.util.List;


/**
 * @Description:this class is user for  返回的model
 * @author wang
 * @date 2015-11-17 上午11:47:14
 * @version V1.0
 */
public class ResultModel {

	/**
	 * if return jsonArray use model list
	 */
	private List<?> modelList = null;
	/**
	 * if return jsonObject use model list
	 */
	private Object model;

	public List<?> getModelList() {
		return modelList;
	}
	public void setModelList(List<?> modelList) {
		this.modelList = modelList;
	}
	public <T> T getModel() {
		return (T) model;
	}
	public void setModel(Object model) {
		this.model = model;
	}

	private int count;

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
