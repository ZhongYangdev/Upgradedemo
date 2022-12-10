package com.yang.upgradedemo.android.model.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者 向贵龙
 * @创建时间 2022/12/9 9:01
 * @描述 Retrofit构建器
 */
public class RetrofitCreator {

    private static final String BASE_URL = "http://10.0.2.2/";//公共请求头
    private static RetrofitCreator sInstance = new RetrofitCreator();

    private final Retrofit mRetrofit;

    private RetrofitCreator() {
        //构建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        //构建Retrofit对象
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static RetrofitCreator getInstance() {
        if (sInstance == null) {
            sInstance = new RetrofitCreator();
        }
        return sInstance;
    }

    /**
     * 获取APP信息接口动态对象
     */
    public IAppInfoServices getAppInfoService() {
        return mRetrofit.create(IAppInfoServices.class);
    }
}
