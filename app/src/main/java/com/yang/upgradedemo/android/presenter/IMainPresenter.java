package com.yang.upgradedemo.android.presenter;

import com.yang.upgradedemo.android.view.IMainViewCallback;

/**
 * @作者 向贵龙
 * @创建时间 2022/12/9 15:31
 * @描述 主页动作接口
 */
public interface IMainPresenter {

    /**
     * 注册UI回调接口
     *
     * @param callback 主页UI回调接口
     */
    void registerViewCallback(IMainViewCallback callback);

    /**
     * 取消注册UI回调接口
     */
    void unregisterViewCallback();

    /**
     * 获取App信息
     */
    void getAppInfo();
}
