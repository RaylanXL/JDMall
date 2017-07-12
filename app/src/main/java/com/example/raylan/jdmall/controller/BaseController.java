package com.example.raylan.jdmall.controller;

import android.content.Context;

import com.example.raylan.jdmall.listener.IModelChangeListener;

/**
 * Created by Raylan on 2017/7/9.
 */

public abstract class BaseController {

    protected IModelChangeListener mIModelChangeListener;
    protected Context mContext;

    public void setIModelChangeListener(IModelChangeListener listener) {
        mIModelChangeListener = listener;
    }

    public BaseController(Context c){
        mContext = c;
    }

    /**
     * 进行网络请求
     * @param action  一个页面可能有多个网络请求，action是用来区分这些请求的
     * @param values  后面的请求参数不确定会有几个，就写为Object...
     */
    public void sendAsyncMessage(final int action, final Object... values) {
        new Thread(){
            public void run(){
                handleMessage(action,values);
            }
        }.start();
    }

    /**
     * 子类处理具体需求的业务代码
     * @param action
     * @param values
     */
    protected abstract void handleMessage(int action,Object... values);


}
