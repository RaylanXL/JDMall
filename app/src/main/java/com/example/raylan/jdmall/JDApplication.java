package com.example.raylan.jdmall;

import android.app.Application;

import com.example.raylan.jdmall.bean.RLoginResult;

/**
 * Created by Raylan on 2017/7/11.
 */

public class JDApplication extends Application {

    public RLoginResult mRLoginResult;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setRLoginResult(RLoginResult rLoginResult) {
        mRLoginResult = rLoginResult;
    }
}
