package com.beijing.qchealth.qchealth_vip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.entity.TestBean;

import java.util.List;

/**
 * Created by lhy on 2017/6/30.
 */

public class TestAdapter  extends RecyclerView.Adapter {
    private Context mContext;
    private List<TestBean> mDate;

    public TestAdapter(Context mContext, List<TestBean> mDate) {
        
        this.mContext = mContext;
        this.mDate = mDate;
        
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext, R.layout.item_consult,null);
        Holder holder=new Holder(view);
        return holder; 
        
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder= (Holder) holder;
        

    }

    @Override
    public int getItemCount() {
        return 10;
    }
    
    class  Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }

}
