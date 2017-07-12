package com.example.raylan.jdmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.raylan.jdmall.R;
import com.example.raylan.jdmall.util.ActivityUtil;

/**
 * Created by Raylan on 2017/7/9.
 */

public class SplashActivity extends BaseActivity {

    private ImageView mImageView;

    @Override
    protected void initView() {
        mImageView = findViewById(R.id.logo_iv);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        alphaAnim();
    }

    //一般方法权限写private，权限不足时再改为protected，再不行再改为public
    private void alphaAnim(){
        AlphaAnimation animation = new AlphaAnimation(0.2f,1.0f);  //透明度由0.2变为不透明
        animation.setDuration(2000);  //执行时间2秒
        animation.setFillAfter(true);  //让动画停在最后一帧
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            //动画结束后会调用此方法
            @Override
            public void onAnimationEnd(Animation animation) {
                //启动登录
                ActivityUtil.start(SplashActivity.this,LoginActivity.class,true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mImageView.startAnimation(animation);
    }
}
