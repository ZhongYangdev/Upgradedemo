package com.yang.upgradedemo.android.model.network;

import com.yang.upgradedemo.android.model.AppInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @作者 向贵龙
 * @创建时间 2022/12/9 15:27
 * @描述 APP信息api接口
 */
public interface IAppInfoServices {

    /**
     * 获取APP版本信息
     */
    @GET("app_info.json")
    Call<AppInfoResponse> getAppInfo();
}
