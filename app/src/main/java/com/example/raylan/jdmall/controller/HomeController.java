package com.example.raylan.jdmall.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.raylan.jdmall.bean.Banner;
import com.example.raylan.jdmall.bean.RResult;
import com.example.raylan.jdmall.bean.RSecondKill;
import com.example.raylan.jdmall.cons.IdiyMessage;
import com.example.raylan.jdmall.cons.NetworkConst;
import com.example.raylan.jdmall.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raylan on 2017/7/11.
 */

public class HomeController extends BaseController {

    public HomeController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action){
            case IdiyMessage.GET_BANNER_ACTION:
                mIModelChangeListener.onModelChanged(IdiyMessage.GET_BANNER_ACTION_RESULT,
                        loadBanner((Integer) values[0]));
                break;
            case IdiyMessage.SECOND_KILL_ACTION:
                mIModelChangeListener.onModelChanged(IdiyMessage.SECOND_KILL_ACTION_RESULT,
                         LoadSecondKill());
                break;
        }
    }

    private List<RSecondKill> LoadSecondKill(){
        String jsonStr = NetworkUtil.deGet(NetworkConst.SECONDKILL_URL);
        RResult rResult = JSON.parseObject(jsonStr,RResult.class);
        if (rResult.isSuccess()){
            try {
                JSONObject jsonObject = new JSONObject(rResult.getResult());
                String rowsJSON = jsonObject.getString("rows");
                return JSON.parseArray(rowsJSON,RSecondKill.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("sxl", "LoadSecondKill: ");
        return new ArrayList<>();

    }

    private List<Banner> loadBanner(int type){
        ArrayList<Banner> arrayList = new ArrayList<>();

        //开始一个网络请求
        String jsonStr =
                NetworkUtil.deGet(NetworkConst.BANNER_URL+"?adKind="+type);
        RResult rResult = JSON.parseObject(jsonStr, RResult.class);
        if (rResult.isSuccess()){
            return JSON.parseArray(rResult.getResult(),Banner.class);
        }
        return arrayList;
    }
}
