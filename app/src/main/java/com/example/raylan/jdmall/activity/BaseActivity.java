package com.example.raylan.jdmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

import com.example.raylan.jdmall.bean.RResult;
import com.example.raylan.jdmall.cons.IdiyMessage;
import com.example.raylan.jdmall.controller.BaseController;
import com.example.raylan.jdmall.listener.IModelChangeListener;

/**
 * Created by Raylan on 2017/7/9.
 */

public abstract class BaseActivity extends FragmentActivity implements IModelChangeListener {

    protected BaseController mController;
    protected Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
           handlerMessage(msg);
        }
    };

    /*
    switch (msg.what){
                case IdiyMessage.REGIST_ACTION_RESULT:
                    handleRegistResult((RResult) msg.obj);
                    break;
            }
     */

    protected void handlerMessage(Message msg){
        //default Empty implement
    }

    protected abstract void initView();
    protected void initController(){
        //default Empty implement
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void tip(String tipStr){
        Toast.makeText(this,tipStr,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onModelChanged(int action, Object... values) {
        //此处的代码会在子线程中运行

        /*这两句话作用一样
          mHandler.obtainMessage().sendToTarget();
          mHandler.sendMessage(mHandler.obtainMessage());
        */
        mHandler.obtainMessage(action,values[0]).sendToTarget();
    }
}
