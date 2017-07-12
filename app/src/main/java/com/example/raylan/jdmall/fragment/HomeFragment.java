package com.example.raylan.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.raylan.jdmall.R;
import com.example.raylan.jdmall.adapter.SecondKillAdapter;
import com.example.raylan.jdmall.bean.Banner;
import com.example.raylan.jdmall.bean.RSecondKill;
import com.example.raylan.jdmall.cons.IdiyMessage;
import com.example.raylan.jdmall.cons.NetworkConst;
import com.example.raylan.jdmall.controller.HomeController;
import com.example.raylan.jdmall.ui.HorizontalListView;
import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Raylan on 2017/7/10.
 */

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    private ViewPager mAdVp;
    private ADAdapter mAdAdapter;
    private HomeController mController;
    private LinearLayout mIndicator;
    private Timer mTimer;
    private HorizontalListView mSecondKillLv;
    private SecondKillAdapter mSecondKillAdapter;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.GET_BANNER_ACTION_RESULT:
                handleBannerResult((List<Banner>) msg.obj);
                break;
            case IdiyMessage.SECOND_KILL_ACTION_RESULT:
                //handleSecondKill(()msg.obj);
                break;
        }
    }

    private void handleSecondKill(List<RSecondKill> datas){

    }

    private void handleBannerResult(final List<Banner> datas){
        if (datas.size()!=0){

            mAdAdapter.setDatas(datas);
            mAdAdapter.notifyDataSetChanged();
            //有数据时设置此广告控件可见，否则默认为gone
            getActivity().findViewById(R.id.ad_rl).setVisibility(View.VISIBLE);

            //设置指示器
            initBannerIndicator(datas);
            //启动定时器
            initBannerTimer(datas);
        }

    }

    private void initBannerTimer(final List<Banner> datas) {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeBannerItem(datas);
            }
        },3000,3000);
    }

    private void changeBannerItem(final List<Banner> datas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int currentItem = mAdVp.getCurrentItem();
                currentItem++;
                if (currentItem>datas.size()-1){
                    currentItem=0;
                }
                mAdVp.setCurrentItem(currentItem);
            }
        });
    }

    private void initBannerIndicator(List<Banner> datas) {
        for (int i = 0; i < datas.size(); i++) {
            View view = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15,15);
            params.setMargins(10,0,0,0);
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.ad_indicator_bg);
            view.setEnabled(i==0);
            mIndicator.addView(view);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer!=null){
            mTimer.cancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initView();
        mController.sendAsyncMessage(IdiyMessage.GET_BANNER_ACTION,1);
        mController.sendAsyncMessage(IdiyMessage.SECOND_KILL_ACTION,0);
    }

    @Override
    protected void initController() {
        mController = new HomeController(getActivity());
        mController.setIModelChangeListener(this);
    }

    @Override
    protected void initView() {
        mAdVp = getActivity().findViewById(R.id.ad_vp);
        mAdAdapter = new ADAdapter();
        mAdVp.setAdapter(mAdAdapter);
        mAdVp.addOnPageChangeListener(this);
        mIndicator = getActivity().findViewById(R.id.ad_indicator);

        mSecondKillLv = getActivity().findViewById(R.id.horizon_listview);
        mSecondKillAdapter = new SecondKillAdapter();
        mSecondKillLv.setAdapter(mSecondKillAdapter);
    }


    public class ADAdapter extends PagerAdapter{

        private List<Banner> mDatas;
        private List<SmartImageView> mChildViews;

        @Override
        public int getCount() {
            return mDatas!=null?mDatas.size():0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setDatas(List<Banner> datas) {
            mDatas = datas;
            mChildViews = new ArrayList<>(mDatas.size());
            for (Banner banner: mDatas) {
                SmartImageView smiv = new SmartImageView(getActivity());
                //让图片占满父框体
                smiv.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                smiv.setScaleType(ImageView.ScaleType.FIT_XY);
                smiv.setImageUrl(NetworkConst.BASE_URL+banner.getAdUrl());
                Log.d("sxl", NetworkConst.BASE_URL+banner.getAdUrl());
                mChildViews.add(smiv);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SmartImageView smiv = mChildViews.get(position);
            container.addView(smiv);
            return smiv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            SmartImageView smiv = mChildViews.get(position);
            container.removeView(smiv);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        //让广告下的小点随着滑动而变化
        int childCount = mIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mIndicator.getChildAt(i).setEnabled(i==position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
