package com.example.raylan.jdmall.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.raylan.jdmall.R;
import com.example.raylan.jdmall.activity.MainActivity;
import com.example.raylan.jdmall.listener.IBottomBarClickListener;

/**
 * Created by Raylan on 2017/7/10.
 */

public class BottomBar extends LinearLayout implements View.OnClickListener {

    private Bottomllayout mTab_main;
    private Bottomllayout mTab_category;
    private Bottomllayout mTab_shopcar;
    private Bottomllayout mTab_mine;
    private IBottomBarClickListener mListener;
    private int mCurrenTabId;
    private TextView mCategoryTv;
    private TextView mMainTv;
    private TextView mShopcarTv;
    private TextView mMineTv;

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setIBottomBarClickListener(IBottomBarClickListener listener) {
        mListener = listener;
    }

    //当所有的控件布局，排布完成后，会调用onFinishInflate
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        mTab_main =  findViewById(R.id.frag_main_ll);
        mTab_category = findViewById(R.id.frag_category_ll);
        mTab_shopcar = findViewById(R.id.frag_shopcar_ll);
        mTab_mine = findViewById(R.id.frag_mine_ll);

        mTab_main.setOnClickListener(this);
        mTab_category.setOnClickListener(this);
        mTab_shopcar.setOnClickListener(this);
        mTab_mine.setOnClickListener(this);

        mMainTv = findViewById(R.id.frag_main);
        mCategoryTv = findViewById(R.id.frag_category);
        mShopcarTv = findViewById(R.id.frag_shopcar);
        mMineTv = findViewById(R.id.frag_mine);


        setFontType(mMainTv);
        setFontType(mCategoryTv);
        setFontType(mShopcarTv);
        setFontType(mMineTv);

        //模拟用户进行了第一次点击
        mTab_main.performClick();
    }

    private void setFontType(TextView tv) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/font.ttf");
        tv.setTypeface(tf);
    }

    @Override
    public void onClick(View view) {

        if (mCurrenTabId == view.getId()){
            return;
        }
        mTab_main.setSelected(view.getId()==R.id.frag_main_ll);
        mTab_category.setSelected(view.getId()==R.id.frag_category_ll);
        mTab_shopcar.setSelected(view.getId()==R.id.frag_shopcar_ll);
        mTab_mine.setSelected(view.getId()==R.id.frag_mine_ll);

        if (mListener!=null){
            mListener.onItemClick(view.getId());
            mCurrenTabId = view.getId();
        }
    }


}
