package com.example.raylan.jdmall.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.example.raylan.jdmall.controller.BaseController;
import com.example.raylan.jdmall.listener.IModelChangeListener;

/**
 * Created by Raylan on 2017/7/10.
 */

public abstract class BaseFragment extends Fragment implements IModelChangeListener {

    protected BaseController mController;
    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            handlerMessage(msg);
        }
    };


    protected void handlerMessage(Message msg) {
        //default Empty implement
    }
    protected abstract void initView();

    protected void initController(){
        //default Empty implement
    };

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
