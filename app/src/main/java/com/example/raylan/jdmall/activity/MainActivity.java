package com.example.raylan.jdmall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.raylan.jdmall.R;
import com.example.raylan.jdmall.fragment.CategoryFragment;
import com.example.raylan.jdmall.fragment.HomeFragment;
import com.example.raylan.jdmall.fragment.MyJDFragment;
import com.example.raylan.jdmall.fragment.ShopcarFragment;
import com.example.raylan.jdmall.listener.IBottomBarClickListener;
import com.example.raylan.jdmall.ui.BottomBar;

public class MainActivity extends BaseActivity implements IBottomBarClickListener {

    private BottomBar mBottomBar;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        initView();
    }

    @Override
    protected void initView() {
        //1.初始化底部栏
        mBottomBar = findViewById(R.id.bottom_bar);
        mBottomBar.setIBottomBarClickListener(this);
        //2.初始化HomeFragment
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.top_bar,new HomeFragment());
        mTransaction.commit();
    }

    @Override
    public void onItemClick(int action) {

        mTransaction = mFragmentManager.beginTransaction();
        switch (action){
            case R.id.frag_main_ll:
                mTransaction.replace(R.id.top_bar,new HomeFragment());
                break;
            case R.id.frag_category_ll:
                mTransaction.replace(R.id.top_bar,new CategoryFragment());
                break;
            case R.id.frag_shopcar_ll:
                mTransaction.replace(R.id.top_bar,new ShopcarFragment());
                break;
            case R.id.frag_mine_ll:
                mTransaction.replace(R.id.top_bar,new MyJDFragment());
                break;
        }
        mTransaction.commit();
    }
}
