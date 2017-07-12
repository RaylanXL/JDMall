package com.example.raylan.jdmall.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.raylan.jdmall.bean.RResult;
import com.example.raylan.jdmall.cons.IdiyMessage;
import com.example.raylan.jdmall.cons.NetworkConst;
import com.example.raylan.jdmall.db.UserDao;
import com.example.raylan.jdmall.util.AESUtils;
import com.example.raylan.jdmall.util.NetworkUtil;

import java.util.HashMap;

/**
 * Created by Raylan on 2017/7/9.
 */

public class UserController extends BaseController{

    private UserDao mUserDao;

    public UserController(Context c) {
        super(c);
        mUserDao = new UserDao(mContext);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        //登录的请求
        switch (action){
            case IdiyMessage.LOGIN_ACTION:
                RResult rResult = login((String)values[0],(String)values[1]);
                //跟Activity说数据加载完成了
                mIModelChangeListener.onModelChanged(IdiyMessage.LOGIN_ACTION_RESULT,rResult);
                break;
            case IdiyMessage.REGIST_ACTION:
                RResult rResult1 = regist((String)values[0],(String)values[1]);
                mIModelChangeListener.onModelChanged(IdiyMessage.REGIST_ACTION_RESULT,rResult1);
                break;
            case IdiyMessage.SAVE_USERTODB:
                boolean saveUserResult = saveUserToDb((String)values[0],(String)values[1]);
                mIModelChangeListener.onModelChanged(IdiyMessage.SAVE_USERTODB_RESULT,saveUserResult);
                break;
            case IdiyMessage.GET_USER_ACTION:
                UserDao.UserInfo userInfo = getUser();
                mIModelChangeListener.onModelChanged(IdiyMessage.GET_USER_ACTION_RESULT,userInfo);
                break;
            case IdiyMessage.CLEAR_USER_ACTION:
                clearUser();
                mIModelChangeListener.onModelChanged(IdiyMessage.CLEAR_USER_ACTION_RESULT,0);
                break;

        }
    }

    private void clearUser(){
        mUserDao.clearUsers();
    }

    private UserDao.UserInfo getUser(){
        UserDao.UserInfo userInfo = mUserDao.getLastUser();
        if (userInfo!=null){
            try {
                userInfo.username = AESUtils.decrypt(userInfo.username);
                userInfo.userpwd = AESUtils.decrypt(userInfo.userpwd);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return userInfo;
        }
        return null;

    }

    private boolean saveUserToDb(String name,String pwd){

        mUserDao.clearUsers();
        //可逆性加密
        try {
            name = AESUtils.encrypt(name);
            pwd = AESUtils.encrypt(pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mUserDao.saveUser(name,pwd);
    }

    private RResult regist(String name,String pwd){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username",name);
        hashMap.put("pwd",pwd);
        String jsonStr =
            NetworkUtil.doPost(NetworkConst.REGIST_URL,hashMap);
        return JSON.parseObject(jsonStr,RResult.class);
    }

    private RResult login(String name, String pwd) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username",name);
        hashMap.put("pwd",pwd);
        String jsonStr =
                NetworkUtil.doPost(NetworkConst.LOGIN_URL,hashMap);

        //处理JSON数据  JSON的两种解析方式 ：GSON FastJson
        //FastJSon实现解析比较简单，不过不能解析嵌套的对象，他可以将一个嵌套的对象转为JSON
        return JSON.parseObject(jsonStr,RResult.class);
    }


}
