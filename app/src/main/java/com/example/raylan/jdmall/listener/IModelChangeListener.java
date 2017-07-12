package com.example.raylan.jdmall.listener;

/**
 * Created by Raylan on 2017/7/10.
 */

public interface IModelChangeListener {

    /**
     *跟UI说界面需要修改
     * @param action 返回处理不同UI的action
     */
    void onModelChanged(int action,Object... values);



}
