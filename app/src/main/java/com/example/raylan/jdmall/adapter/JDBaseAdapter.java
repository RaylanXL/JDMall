package com.example.raylan.jdmall.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Raylan on 2017/7/12.
 */

public abstract class JDBaseAdapter<T> extends BaseAdapter {

    protected List<T> mDatas;

    public void setDatas(List<T> datas){
        mDatas=datas;
    }

    @Override
    public int getCount() {
        return mDatas!=null?mDatas.size():0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
