package com.beijing.qchealth.qchealth_vip.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.base.BaseActivity;
import com.youzan.sdk.event.AbsAuthEvent;
import com.youzan.sdk.event.AbsChooserEvent;
import com.youzan.sdk.event.AbsShareEvent;
import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.plugin.YouzanBrowser;
import com.youzan.sdk.web.plugin.YouzanClient;

import butterknife.Bind;
import butterknife.ButterKnife;


public class StoreActivity extends BaseActivity {
    private static final int CODE_REQUEST_LOGIN = 0x101;
    private YouzanBrowser mView;

    @Bind(R.id.rl_left_container)
    RelativeLayout rlLeftContainer;
    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.img_left)
    ImageView imgLeft;
    @Bind(R.id.title_content)
    TextView titleContent;
    @Bind(R.id.rl_right_container)
    RelativeLayout rlRightContainer;
    @Bind(R.id.img_right)
    ImageView imgRight;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.line_view)
    View lineView;
    @Bind(R.id.rl_close)
    RelativeLayout rlClose;
    @Bind(R.id.img_close)
    ImageView imgClose;
    private String initurl = "https://h5.youzan.com/v2/showcase/homepage?alias=1ih5uh8kh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=View.inflate(this,R.layout.activity_store,null);
        LinearLayout mRootView= (LinearLayout) view.findViewById(R.id.ll_web_container);
        mView=new YouzanBrowser(this);
        mRootView.addView(mView);
        setContentView(view);
        
        ButterKnife.bind(this);
        setupYouzanView(mView);
         
        setTitleStyle();
        //替换成需要展示入口的链接
       
        mView.loadUrl(initurl);
//        mView.loadUrl("https://h5.youzan.com/v2/showcase/homepage?alias=wfrp44vt");
        
        
        mView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                
                super.onPageFinished(view, url);
                Log.e("----", "onPageFinished: -----" +url );
                if (url.equals(initurl)){
                    rlClose.setVisibility(View.GONE);
                }else {
                    rlClose.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        
    }

    private void setTitleStyle() {
        titleContent.setText("食药房");
        imgLeft.setImageResource(R.drawable.arrow_left_back);
        imgClose.setImageResource(R.drawable.login_close);
        rlClose.setVisibility(View.GONE);
        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
                StoreActivity.this.finish();
            }
        });
        
        
        rlLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mView.pageGoBack()) {
                   StoreActivity.super.onBackPressed();
                }
            }
        });
        
    }

    private void setupYouzanView(YouzanClient client) {
        //订阅认证事件
        client.subscribe(new AbsAuthEvent() {
            /**
             * 有赞SDK认证回调.
             * 在加载有赞的页面时, SDK相应会回调该方法.
             *
             * 从自己的服务器上请求同步认证后组装成{@link com.youzan.sdk.YouzanToken}, 调用{code view.sync(token);}同步信息.
             *
             * @param view 发起回调的视图
             * @param needLogin 表示当下行为是否需要需要用户角色的认证信息, True需要.
             */
            @Override
            public void call(View view, boolean needLogin) {
                /**
                 * <pre>
                 *     建议代码逻辑:
                 *
                 *     判断App内的用户是否登录?
                 *       => 已登录: 请求带用户角色的认证信息(login接口);
                 *       => 未登录: needLogin为true, 唤起App内登录界面, 请求带用户角色的认证信息(login接口);
                 *       => 未登录: needLogin为false, 请求不带用户角色的认证信息(initToken接口).
                 *  </pre>
                 */

                //实现代码略...
            }
        });

        //订阅文件选择事件
        client.subscribe(new AbsChooserEvent() {
            @Override
            public void call(View view, Intent intent, int requestCode) throws ActivityNotFoundException {
                //调用系统图片选择器
                startActivity(intent);
            }
        });

        //订阅分享事件
        client.subscribe(new AbsShareEvent() {
            @Override
            public void call(View view, GoodsShareModel data) {
                /**
                 * 在获取数据后, 可以使用其他分享SDK来提高分享体验.
                 * 这里调用系统分享来简单演示分享的过程.
                 */
                String content = String.format("%s %s", data.getDesc(), data.getLink());
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, content);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, data.getTitle());
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /**
             * 用户登录成功返回, 从自己的服务器上请求同步认证后组装成{@link com.youzan.sdk.YouzanToken},
             * 调用{code view.sync(token);}同步信息.
             */
            if (CODE_REQUEST_LOGIN == requestCode) {
                //mView.sync(token);
            } else {
                //处理文件上传
                mView.receiveFile(requestCode, data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!mView.pageGoBack()) {
            super.onBackPressed();
        }
    }
}
