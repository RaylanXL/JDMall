package com.example.raylan.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raylan.jdmall.JDApplication;
import com.example.raylan.jdmall.R;
import com.example.raylan.jdmall.activity.LoginActivity;
import com.example.raylan.jdmall.cons.IdiyMessage;
import com.example.raylan.jdmall.controller.UserController;
import com.example.raylan.jdmall.util.ActivityUtil;

/**
 * Created by Raylan on 2017/7/10.
 */

public class MyJDFragment extends BaseFragment implements View.OnClickListener {

    private TextView mUserNameTv;
    private TextView mUserLevelTv;
    private TextView mWaitPayTv;
    private TextView mWaitReceiveTv;
    private JDApplication mJdApplication;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.CLEAR_USER_ACTION_RESULT:
                ActivityUtil.start(getActivity(), LoginActivity.class,true);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myjd,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initView();
    }

    @Override
    protected void initController() {
        mController = new UserController(getActivity());
        mController.setIModelChangeListener(this);
    }

    @Override
    protected void initView() {
        getActivity().findViewById(R.id.logout_btn).setOnClickListener(this);

        mUserNameTv = getActivity().findViewById(R.id.user_name_tv);
        mUserLevelTv = getActivity().findViewById(R.id.user_level_tv);
        mWaitPayTv = getActivity().findViewById(R.id.wait_pay_tv);
        mWaitReceiveTv = getActivity().findViewById(R.id.wait_receive_tv);


        mJdApplication = (JDApplication) getActivity().getApplication();
        mUserNameTv.setText(mJdApplication.mRLoginResult.getUserName());
        mWaitPayTv.setText(mJdApplication.mRLoginResult.getWaitPayCount()+"");
        mWaitReceiveTv.setText(mJdApplication.mRLoginResult.getWaitReceiveCount()+"");
        initUserLevel();

    }

    private void initUserLevel() {
        //1注册会员2铜牌会员3银牌会员4金牌会员5钻石会员
        String text="";
        switch (mJdApplication.mRLoginResult.getUserLevel()){
            case 1:
                text="注册会员";
                break;
            case 2:
                text="铜牌会员";
                break;
            case 3:
                text="银牌会员";
                break;
            case 4:
                text="金牌会员";
                break;
            case 5:
                text="钻石会员";
                break;

        }
        mUserLevelTv.setText(text);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.logout_btn:
               mController.sendAsyncMessage(IdiyMessage.CLEAR_USER_ACTION,0);
               break;
       }
    }

}
