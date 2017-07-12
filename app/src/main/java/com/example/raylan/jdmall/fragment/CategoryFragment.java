package com.example.raylan.jdmall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raylan.jdmall.R;

/**
 * Created by Raylan on 2017/7/10.
 */

public class CategoryFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category,container,false);
    }

    @Override
    protected void initView() {

    }
}
