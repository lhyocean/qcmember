package com.beijing.qchealth.qchealth_vip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.entity.TestBean;
import com.beijing.qchealth.qchealth_vip.utils.Common;
import com.beijing.qchealth.qchealth_vip.utils.ImageUtil;
import com.beijing.qchealth.qchealth_vip.view.CircleImageView;

import java.util.List;

/**
 * Created by lhy on 2017/6/30.
 */

public class HomeConsultAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TestBean> mDate;

    public HomeConsultAdapter(Context mContext, List<TestBean> mDate) {
        
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
        String headurl= Common.HEADIMG[(position%5)];
        ImageUtil.loadPicNet(headurl,mHolder.imgHead);
        mHolder.tvContent.setText(Common.CONTENTS[position%5]);
        mHolder.tvName.setText(Common.NAMES[position%5]);
        mHolder.tvTime.setText(Common.TIMES[position%5]);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
    
    class  Holder extends RecyclerView.ViewHolder {

        CircleImageView imgHead;
        TextView tvName,tvContent,tvTime;
        public Holder(View itemView) {
            super(itemView);
            imgHead= (CircleImageView) itemView.findViewById(R.id.img_head);
            tvContent= (TextView) itemView.findViewById(R.id.tv_content);
            tvName= (TextView) itemView.findViewById(R.id.tv_name);
            tvTime= (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

}
