package com.beijing.qchealth.qchealth_vip.view.verticalScrollTextview;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： liocean
 * 描述：VerticalRollingTextView的数据适配器
 */
public abstract class DataSetAdapter<T> {

    List<T> data = new ArrayList<>();

    public DataSetAdapter() {
    }

    public DataSetAdapter(List<T> data) {
        this.data = data;
    }

    /**
     * @param index 当前角标
     * @return 待显示的字符串
     */
    final public String getText(int index) {
        return text(data.get(index));
    }

    public abstract String text(T t);



    public int getItemCount() {
        return data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }


}
