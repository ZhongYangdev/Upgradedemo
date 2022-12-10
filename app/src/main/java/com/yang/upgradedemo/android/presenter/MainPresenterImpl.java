package com.yang.upgradedemo.android.presenter;

import static java.net.HttpURLConnection.HTTP_OK;

import com.yang.upgradedemo.android.model.AppInfoResponse;
import com.yang.upgradedemo.android.view.IMainViewCallback;
import com.yang.upgradedemo.android.model.network.RetrofitCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @作者 向贵龙
 * @创建时间 2022/12/9 15:32
 * @描述 主页逻辑层，采取单例模式获取（动作接口实现类）
 */
public class MainPresenterImpl implements IMainPresenter {

    private static MainPresenterImpl sInstance = new MainPresenterImpl();

    private IMainViewCallback mCallback;

    private MainPresenterImpl() {
    }

    public static IMainPresenter getInstance() {
        if (sInstance == null) {
            sInstance = new MainPresenterImpl();
        }
        return sInstance;
    }

    @Override
    public void registerViewCallback(IMainViewCallback callback) {
        mCallback = callback;
    }

    @Override
    public void unregisterViewCallback() {
        mCallback = null;
    }

    @Override
    public void getAppInfo() {
        //通知UI正在加载中
        if (mCallback != null) {
            mCallback.onLoading("正在获取APP版本信息...");
        }
        //请求服务器，获取app信息数据
        Call<AppInfoResponse> task = RetrofitCreator.getInstance().getAppInfoService().getAppInfo();
        task.enqueue(new Callback<AppInfoResponse>() {
            @Override
            public void onResponse(Call<AppInfoResponse> call, Response<AppInfoResponse> response) {
                //请求成功
                int resultCode = response.code();
                AppInfoResponse infoResponse = response.body();
                if (resultCode == HTTP_OK && infoResponse != null) {
                    //请求结果为200，并且内容不为空，则将结果通知到UI
                    if (mCallback != null) {
                        mCallback.onAppInfoLoaded(infoResponse);
                    }
                } else {
                    //获取结果错误或者有问题，通知UI获取数据网络错误
                    if (mCallback != null) {
                        mCallback.onNetworkError();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppInfoResponse> call, Throwable t) {
                //请求失败，通知UI获取数据网络错误
                if (mCallback != null) {
                    mCallback.onNetworkError();
                }
            }
        });
    }

}
