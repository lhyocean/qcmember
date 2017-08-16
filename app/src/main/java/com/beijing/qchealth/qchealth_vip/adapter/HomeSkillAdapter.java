package com.beijing.qchealth.qchealth_vip.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.activity.ContainerActivity;
import com.beijing.qchealth.qchealth_vip.entity.TestBean;
import com.beijing.qchealth.qchealth_vip.fragment.home.ImageContainerFragment;

import java.util.List;

/**
 * Created by lhy on 2017/6/30.
 */

public class HomeSkillAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TestBean> mDate;

    public HomeSkillAdapter(Context mContext, List<TestBean> mDate) {
        
        this.mContext = mContext;
        this.mDate = mDate;
        
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext, R.layout.item_home_skill,null);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Holder mHolder= (Holder) holder;
        
        if (mDate!=null&&mDate.size()>0){
            TestBean bean=mDate.get(position);
            if (bean.getImgRes()!=0){
                mHolder.img.setImageResource(bean.getImgRes());
            }else {
                mHolder.img.setImageResource(R.drawable.food_one);
            }
            mHolder.tvName.setText(bean.getTitle()==null?"":bean.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    switch (mDate.get(position).getImgRes()) {
                        
                        case R.drawable.food_one:
                            bundle=new Bundle();
                            bundle.putString("title","美味早餐");
                            bundle.putInt("resId",R.drawable.food_one_detail);
                            
                            break;
                        case R.drawable.food_two:
                            bundle=new Bundle();
                            bundle.putString("title","营养午餐");
                            bundle.putInt("resId",R.drawable.food_two_detail);
                            
                            break;
                        case R.drawable.food_three:
                            bundle=new Bundle();
                            bundle.putString("title","气质晚餐");
                            bundle.putInt("resId",R.drawable.food_three_detail);
                            
                            break;
                        case R.drawable.skill_one:
                            bundle=new Bundle();
                            bundle.putString("title","激素平衡");
                            bundle.putInt("resId",R.drawable.skill_one_detail);
                            
                            break;
                        case R.drawable.skill_two:
                            bundle=new Bundle();
                            bundle.putString("title","排毒技法");
                            bundle.putInt("resId",R.drawable.skill_two_detail);
                            break;
                        case R.drawable.skill_three:
                            bundle=new Bundle();
                            bundle.putString("title","韵肌技法");
                            bundle.putInt("resId",R.drawable.skill_three_detail);
                            break;
                    }
                    ContainerActivity.startActivity(mContext, ImageContainerFragment.class,bundle);
            
                }
            });
            
            
            
        }
        
        
        
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
            tvName= (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

}
