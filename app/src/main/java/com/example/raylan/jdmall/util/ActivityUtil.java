package com.example.raylan.jdmall.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.raylan.jdmall.activity.BaseActivity;


/**
 * Created by Raylan on 2017/7/9.
 */

public class ActivityUtil {

    public static void start(Context context, Class<? extends BaseActivity> baseActivityClass,
                             boolean isFinishSelf){
        Intent intent = new Intent(context,baseActivityClass);
        context.startActivity(intent);
        if (isFinishSelf){
            ((Activity)context).finish();
        }
    };
}
