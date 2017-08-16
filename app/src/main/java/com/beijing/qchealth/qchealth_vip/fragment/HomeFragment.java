package com.beijing.qchealth.qchealth_vip.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.MainActivity;
import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.activity.ContainerActivity;
import com.beijing.qchealth.qchealth_vip.activity.TestAlipayActivity;
import com.beijing.qchealth.qchealth_vip.adapter.HomeSkillAdapter;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;
import com.beijing.qchealth.qchealth_vip.entity.TestBean;
import com.beijing.qchealth.qchealth_vip.event.UpDataMessEvent;
import com.beijing.qchealth.qchealth_vip.fragment.home.FreeConsultFragment;
import com.beijing.qchealth.qchealth_vip.fragment.home.ImageContainerFragment;
import com.beijing.qchealth.qchealth_vip.utils.HxMessUtil;
import com.beijing.qchealth.qchealth_vip.view.SpacesItemDecoration;
import com.beijing.qchealth.qchealth_vip.view.verticalScrollTextview.DataSetAdapter;
import com.beijing.qchealth.qchealth_vip.view.verticalScrollTextview.VerticalRollingTextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by lhy on 2017/6/30.
 */

public class HomeFragment extends BaseFragment implements  VerticalRollingTextView.OnItemClickListener {
    
    @Bind(R.id.running_tips)
    VerticalRollingTextView mRollingTextView;        

    @Bind(R.id.img_mess)
    ImageView imgMess;
    @Bind(R.id.img_hint)
    TextView imgHint;
    @Bind(R.id.rl_mess)
    RelativeLayout relativeLayout;
    private List<String> strings=new ArrayList<>();
    
    List<String>  names=new ArrayList<>();
    List<Integer> imgIds=new ArrayList<>();
    List<TestBean> mBeanlist=new ArrayList<>();

    List<String>  skillnames=new ArrayList<>();
    List<Integer>  imgSkills=new ArrayList<>();
    List<TestBean> skills=new ArrayList<>();
    
    @Bind(R.id.rec_special_skill)
    RecyclerView recSkill;
    @Bind(R.id.rec_special_food)
    RecyclerView recFood;
    @Bind(R.id.banner)
    ConvenientBanner mBanner;
    private List<Integer> imgRes=new ArrayList<>();
    


    @Override
    public View initRootView() {
        
        mRootView=View.inflate(mContext, R.layout.fragment_home,null);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        
        return mRootView;
    }

    @Override
    public void setViews() {
        
        strings.add("7月15日晚上21:00~22:00系统升级");
        strings.add("7月18日下午18:00~20:00系统升级");
        
        mRollingTextView.setDataSetAdapter(new DataSetAdapter<String>(strings) {
            @Override
            public String text(String o) {

                if (o==null){
                    return "";
                }else {
                    if (o!=null){
                        return o;
                    }else {
                        return "";
                    }
                }
            }
        });
        mRollingTextView.setOnItemClickListener(this);
        
        
        imgIds.clear();
        imgIds.add(R.drawable.food_one);
        imgIds.add(R.drawable.food_two);
        imgIds.add(R.drawable.food_three);
        
        names.clear();
        names.add("美味早餐");
        names.add("营养午餐");
        names.add("气质晚餐");

        for (int i = 0; i < 3; i++) {
            
            TestBean bean=new TestBean();
            bean.setImgRes(imgIds.get(i));
            bean.setTitle(names.get(i));
            mBeanlist.add(bean);
        }
        imgSkills.clear();
        imgSkills.add(R.drawable.skill_one);
        imgSkills.add(R.drawable.skill_two);
        imgSkills.add(R.drawable.skill_three);

        skillnames.clear();
        skillnames.add("激素平衡");
        skillnames.add("排毒技法");
        skillnames.add("韵肌技法");

        for (int i = 0; i < 3; i++) {

            TestBean bean=new TestBean();
            bean.setImgRes(imgSkills.get(i));
            bean.setTitle(skillnames.get(i));
            skills.add(bean);
        }
        
        
        
        HomeSkillAdapter adapter=new HomeSkillAdapter(mContext,mBeanlist);
        HomeSkillAdapter adapterskill=new HomeSkillAdapter(mContext,skills);
        
        GridLayoutManager manager=new GridLayoutManager(mContext, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        GridLayoutManager managerskill=new GridLayoutManager(mContext, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        
        
        recFood.setLayoutManager(manager);
        recSkill.setLayoutManager(managerskill);
        recSkill.addItemDecoration(new SpacesItemDecoration(5,0,5,0));
        recFood.addItemDecoration(new SpacesItemDecoration(5,0,5,0));
        recFood.setAdapter(adapter);
        
        recSkill.setAdapter(adapterskill);


        imgRes .clear();
        imgRes.add(R.drawable.banner);
        mBanner.setPages(new CBViewHolderCreator<BannerHolder>() {
            @Override
            public BannerHolder createHolder() {
                return new BannerHolder();
            }
        },imgRes).setCanLoop(true);
        
        
    }

    @Override
    public void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);        
        return rootView;
    }


    @Override
    public void onItemClick(VerticalRollingTextView view, int index) {

        ToastUtil.shortShowToast("点击了position---"+index);

    }

    @Override
    public void onResume() {
        super.onResume();
        
        if (mRollingTextView!=null&&strings!=null&&strings.size()>0){
            mRollingTextView.run();
        }

        int count= HxMessUtil.getHxUnreadMessCount();
        updataMessCount(count);
    }

    private void updataMessCount(int count) {
        if (imgHint==null){
            return;
        }
        if (count>0){
            imgHint.setVisibility(View.VISIBLE);
        }else {
            imgHint.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        
        if (mRollingTextView!=null&&mRollingTextView.isRunning()){
            mRollingTextView.stop();
        }
    }
    
    @OnClick({R.id.img_mess,R.id.rl_mess,R.id.ll_nutrition,R.id.ll_health,R.id.ll_sport,R.id.ll_sleep,R.id.ll_chinese_medical,
            R.id.ll_free_consult,R.id.ll_environment,R.id.ll_super_project,R.id.ll_health_record,R.id.ll_doctor_team
             ,R.id.rl_doctor_room,R.id.rl_food_medical_room,R.id.ll_sign_doctor})
    public void onClick(View view){
        Bundle bundle;
        switch (view.getId()) {
            case R.id.img_mess:
            case R.id.rl_mess:
                Intent intent=new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                break;
            
            case R.id.ll_nutrition:
                  
                 Intent inten=new Intent(mContext, TestAlipayActivity.class);
                 mContext.startActivity(inten);
                 
                break;
            
            case R.id.ll_free_consult:

                ToastUtil.shortShowToast("在线咨询---待开发");
                break;
            
            case R.id.ll_health:
                bundle=new Bundle();
                bundle.putString("title","心理");
                bundle.putInt("resId",R.drawable.health_evaluation);
                ContainerActivity.startActivity(mContext, ImageContainerFragment.class,bundle);
                
                break;
            case R.id.ll_sport:
                ToastUtil.shortShowToast("运动---待开发");
                
                break;
            case R.id.ll_sleep:
                bundle=new Bundle();
                bundle.putString("title","睡眠");
                bundle.putInt("resId",R.drawable.health_evaluation);
                ContainerActivity.startActivity(mContext, ImageContainerFragment.class,bundle);
                break;
            case R.id.ll_chinese_medical:
                bundle=new Bundle();
                bundle.putString("title","中医");
                bundle.putInt("resId",R.drawable.health_evaluation);
                ContainerActivity.startActivity(mContext, ImageContainerFragment.class,bundle);
                break;
            case R.id.ll_doctor_team:
                bundle=new Bundle();
                bundle.putString("title","行为医美");
                bundle.putInt("resId",R.drawable.health_evaluation);
                ContainerActivity.startActivity(mContext, ImageContainerFragment.class,bundle);
                break;
            case R.id.ll_environment:
                bundle=new Bundle();
                bundle.putString("title","行为医美");
                bundle.putInt("resId",R.drawable.health_evaluation);
                ContainerActivity.startActivity(mContext, ImageContainerFragment.class,bundle);
                
                break;
            case R.id.ll_super_project:
                ContainerActivity.startActivity(mContext, FreeConsultFragment.class,null);
                break;
            case R.id.ll_health_record:
                ToastUtil.shortShowToast("健康档案---待开发");
                
                break;
            
            case R.id.rl_food_medical_room:
                ToastUtil.shortShowToast("是药房---待开发");
                
                break;
            case R.id.rl_doctor_room:
                bundle=new Bundle();
                bundle.putString("title","云诊室");
                bundle.putInt("resId",R.drawable.yun_doctor_room);
                ContainerActivity.startActivity(mContext, ImageContainerFragment.class,bundle);
                
                break;
            
            case R.id.ll_sign_doctor:

                bundle=new Bundle();
                bundle.putString("title","签约");
                bundle.putInt("resId",R.drawable.sign_doctor);
                ContainerActivity.startActivity(mContext, ImageContainerFragment.class,bundle);
                
                
                break;
            
        }
        
        
    }
    
    public void onEventMainThread(UpDataMessEvent event){

        switch (event.getState()) {
            case UpDataMessEvent.MESSAGE_COUNT:
                if (event.getMessage() instanceof Integer){
                    Integer count= (Integer) event.getMessage();
                    updataMessCount(count);
                }
                break;
        }
    }
    class BannerHolder implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {

            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }
        
        @Override
        public void UpdateUI(Context context, int position, Integer data) {
             imageView.setImageResource(data);
        }
    }
    
}
