package com.example.raylan.jdmall.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.raylan.jdmall.JDApplication;
import com.example.raylan.jdmall.R;
import com.example.raylan.jdmall.bean.RLoginResult;
import com.example.raylan.jdmall.bean.RResult;
import com.example.raylan.jdmall.cons.IdiyMessage;
import com.example.raylan.jdmall.controller.UserController;
import com.example.raylan.jdmall.db.UserDao;
import com.example.raylan.jdmall.util.ActivityUtil;

/**
 * Created by Raylan on 2017/7/9.
 */

public class LoginActivity extends BaseActivity {


    private EditText mNameEt;
    private EditText mPwdEt;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.LOGIN_ACTION_RESULT:
                handleLoginResult((RResult) msg.obj);
                break;
            case IdiyMessage.SAVE_USERTODB_RESULT:
                handleSaveUser((Boolean) msg.obj);
                break;
            case IdiyMessage.GET_USER_ACTION_RESULT:
                handleGetUser(msg.obj);
                break;
        }
    }

    private void handleGetUser(Object c){
        if (c!=null){
            UserDao.UserInfo userInfo = (UserDao.UserInfo) c;
            mNameEt.setText(userInfo.username);
            mPwdEt.setText(userInfo.userpwd);
        }

    }

    private void handleSaveUser(Boolean ifSuccess){
        if (ifSuccess){
            ActivityUtil.start(this,MainActivity.class,true);
        }else {
            tip("登录异常");
        }
    }

    private void handleLoginResult(RResult resultBean) {
        if (resultBean.isSuccess()){
            //将账号密码保存到数据库，传递一个账号密码
            String name = mNameEt.getText().toString();
            String pwd = mPwdEt.getText().toString();
            mController.sendAsyncMessage(IdiyMessage.SAVE_USERTODB,
                    name,pwd);

            //将用户的信息保存到Application中
            RLoginResult rLoginResult =
                    JSON.parseObject(resultBean.getResult(), RLoginResult.class);
            //JDApplication.setRLoginResult(rLoginResult);
            ((JDApplication)getApplication()).setRLoginResult(rLoginResult);
        }else {
            tip("登录失败："+resultBean.getErrorMsg());
        }
    }

    @Override
    protected void initView() {
        mNameEt = findViewById(R.id.name_et);
        mPwdEt = findViewById(R.id.pwd_et);
        //如果数据库中有数据（账号，密码），应该显示出来
        mController.sendAsyncMessage(IdiyMessage.GET_USER_ACTION,0);
    }

    @Override
    protected void initController() {
        mController = new UserController(this);
        mController.setIModelChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initController();
        initView();
    }

    public void loginClick(View view){
        String name = mNameEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        if (TextUtils.isEmpty(name)||
                TextUtils.isEmpty(pwd)){
            tip("账号或密码为空");
            return;
        }
        //发送一个网络请求，去请求一个网络数据
        tip("正在登录，请稍后...");
        mController.sendAsyncMessage(IdiyMessage.LOGIN_ACTION,name,pwd);
    }

    public void registClick(View view){
        ActivityUtil.start(this,RegistActivity.class,false);
    }
}
