package com.example.raylan.jdmall.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.raylan.jdmall.R;
import com.example.raylan.jdmall.bean.RResult;
import com.example.raylan.jdmall.cons.IdiyMessage;
import com.example.raylan.jdmall.controller.UserController;

/**
 * Created by Raylan on 2017/7/10.
 */

public class RegistActivity extends BaseActivity {

    private EditText mNameEt;
    private EditText mPwdEt;
    private EditText mSurePwdEt;
    private CheckBox mCheckBox;
    private boolean isAgreeRes;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.REGIST_ACTION_RESULT:
                handleRegistResult((RResult) msg.obj);
                break;
        }
    }

    private void handleRegistResult(RResult resultBean) {

        tip(resultBean.isSuccess()?"注册成功！":resultBean.getErrorMsg());
        if (resultBean.isSuccess()){
            finish();
        }
    }


    @Override
    protected void initView() {
        mNameEt = findViewById(R.id.username_et);
        mPwdEt = findViewById(R.id.pwd_et);
        mSurePwdEt = findViewById(R.id.surepwd_et);
        mCheckBox = findViewById(R.id.agreeReg_cb);
    }

    @Override
    protected void initController() {
        mController = new UserController(this);
        mController.setIModelChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);


        initView();
        initController();
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isAgreeRes = true;
                }else {
                    isAgreeRes = false;
                }
            }
        });
    }

    public void registClick(View view){
        String name = mNameEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        String surePwd = mSurePwdEt.getText().toString();
        if (TextUtils.isEmpty(name)
                ||TextUtils.isEmpty(pwd)
                ||TextUtils.isEmpty(surePwd)){
            tip("请输入完整的信息！");
            return;
        }
        if (!pwd.equals(surePwd)){
            tip("两次密码不一致！");
            return;
        }
        if (!isAgreeRes){
            tip("请阅读协议，并点击同意！");
            return;
        }

        //注册用户
        mController.sendAsyncMessage(IdiyMessage.REGIST_ACTION,
                name,pwd);
    }


}
