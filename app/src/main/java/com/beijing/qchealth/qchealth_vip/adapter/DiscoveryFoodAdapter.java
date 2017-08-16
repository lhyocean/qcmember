package com.beijing.qchealth.qchealth_vip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.entity.TestBean;

import java.util.List;

/**
 * Created by lhy on 2017/6/30.
 */

public class DiscoveryFoodAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TestBean> mDate;

    public DiscoveryFoodAdapter(Context mContext, List<TestBean> mDate) {
        
        this.mContext = mContext;
        this.mDate = mDate;
        
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext, R.layout.discovery_item_health,null);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder= (Holder) holder;
//        
//        if (mDate!=null&&mDate.size()>0){
//            TestBean bean=mDate.get(position);
//            if (bean.getImgRes()!=0){
//                mHolder.img.setImageResource(bean.getImgRes());
//            }else {
//                mHolder.img.setImageResource(R.drawable.food_one);
//            }
//            mHolder.tvName.setText(bean.getTitle()==null?"":bean.getTitle());
//        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
    
    class  Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName;
        public Holder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.img);
//            tvName= (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

}
