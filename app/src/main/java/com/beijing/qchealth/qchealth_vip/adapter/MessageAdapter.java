package com.beijing.qchealth.qchealth_vip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.entity.TestBean;

import java.util.List;

/**
 * Created by lhy on 2017/6/30.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TestBean> mDate;
    

    public MessageAdapter(Context mContext, List<TestBean> mDate) {
        
        this.mContext = mContext;
        this.mDate = mDate;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext, R.layout.item_mess,null);
        Holder holder=new Holder(view);
        return holder; 
        
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder= (Holder) holder;
        if (position==0){
            mHolder.tvCount.setVisibility(View.GONE);
            mHolder.tvName.setText("聊天会话列表");
        }else if (position==1){
            mHolder.tvCount.setVisibility(View.GONE);
            mHolder.tvName.setText("进入单聊");
            
        }else if (position==2){
            mHolder.tvCount.setVisibility(View.GONE);
            mHolder.tvName.setText("");
        }
        
        mHolder.itemView.setOnClickListener(new RecClickListener(mHolder.itemView,position));
    }

    @Override
    public int getItemCount() {
        return 3;
    }
    
    class  Holder extends RecyclerView.ViewHolder {

        TextView tvCount,tvName;
        public Holder(View itemView) {
            super(itemView);
            tvCount= (TextView) itemView.findViewById(R.id.tv_mess_count);
            tvName= (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
    
    
    public interface OnItemClickLIstener{
        void onItemClick(View view, int pos);
    }
    private OnItemClickLIstener onItemClickLIstener;

    public OnItemClickLIstener getOnItemClickLIstener() {
        return onItemClickLIstener;
    }

    public void setOnItemClickLIstener(OnItemClickLIstener onItemClickLIstener) {
        this.onItemClickLIstener = onItemClickLIstener;
    }
    
    public  class RecClickListener implements View.OnClickListener{
        private View mView;
        private int position;

        public RecClickListener(View view, int position) {
            this.mView = view;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (onItemClickLIstener!=null){
                onItemClickLIstener.onItemClick(mView,position);
            }
        }
    }
    
}
