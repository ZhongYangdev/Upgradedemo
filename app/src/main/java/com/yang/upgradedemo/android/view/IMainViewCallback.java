package com.yang.upgradedemo.android.view;

import com.yang.upgradedemo.android.model.AppInfoResponse;

/**
 * @作者 向贵龙
 * @创建时间 2022/12/9 15:30
 * @描述 主页UI回调接口
 */
public interface IMainViewCallback {

    /**
     * 加载中
     *
     * @param tip 提示信息
     */
    void onLoading(String tip);

    /**
     * 网络错误
     */
    void onNetworkError();

    /**
     * 数据为空
     */
    void onEmpty();

    /**
     * app信息加载到了
     *
     * @param appInfo app信息数据
     */
    void onAppInfoLoaded(AppInfoResponse appInfo);
}
