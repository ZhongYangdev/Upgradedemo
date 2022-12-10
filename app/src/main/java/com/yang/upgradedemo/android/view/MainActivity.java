package com.yang.upgradedemo.android.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.king.app.updater.AppUpdater;
import com.yang.upgradedemo.android.CommonUtil;
import com.yang.upgradedemo.android.R;
import com.yang.upgradedemo.android.model.AppInfoResponse;
import com.yang.upgradedemo.android.presenter.IMainPresenter;
import com.yang.upgradedemo.android.presenter.MainPresenterImpl;

public class MainActivity extends AppCompatActivity implements IMainViewCallback {

    private static final String TAG = "MainActivity";

    private Button mBtnUpgrade;
    private TextView mTvVersionInfo;

    private IMainPresenter mPresenter;//逻辑层实例对象
    private AlertDialog mLoadingDialog;//加载中对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*初始化控件*/
        initViews();
        /*初始化数据*/
        initData();
        /*初始化逻辑层*/
        initPresenter();
        /*初始化事件*/
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册逻辑层
        MainPresenterImpl.getInstance().unregisterViewCallback();
    }

    private void initViews() {
        mBtnUpgrade = findViewById(R.id.btn_upgrade);
        mTvVersionInfo = findViewById(R.id.tv_versionInfo);
    }

    private void initData() {
        //获取当前版本号，并设置版本信息
        String[] infoArray = CommonUtil.getCurrentAppVersionInfo(this);
        String strInfo = "APP信息" + "\n" + "版本号（versionCode）：" + infoArray[0] + "\n" + "版本名称（versionName）：" + infoArray[1];
        mTvVersionInfo.setText(strInfo);
    }

    /**
     * 在onCreate方法中注册逻辑层，要记得在页面销毁时（onDestroy）注销注册，避免内存泄漏和UI回调触发空指针异常
     */
    private void initPresenter() {
        //获取逻辑层实例
        mPresenter = MainPresenterImpl.getInstance();
        //注册UI回调接口
        mPresenter.registerViewCallback(this);
    }

    private void initListener() {
        /*检查更新按钮点击事件，获取app信息，检查是否需要更新*/
        mBtnUpgrade.setOnClickListener(v -> mPresenter.getAppInfo());
    }

    //----------------------------------------------------------------- UI回调接口 -start -----------------------------------------------------------------

    @Override
    public void onLoading(String tip) {
        //显示加载中对话框，并将实例对象设置为成员变量，方便后续调用
        mLoadingDialog = CommonUtil.showLoadingDialog(this, "UI更新提示", tip);
    }

    @Override
    public void onNetworkError() {
        //关闭加载中对话框
        mLoadingDialog.dismiss();
        //显示网络错误提示对话框
        CommonUtil.createAlertDialogBuilder(this, "UI更新提示", "网络错误，请重试", true)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    public void onEmpty() {
        //关闭加载中对话框
        mLoadingDialog.dismiss();
        //显示空数据提示对话框
        CommonUtil.createAlertDialogBuilder(this, "UI更新提示", "获取数据为空！", true)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    public void onAppInfoLoaded(AppInfoResponse appInfo) {
        //关闭加载中对话框
        mLoadingDialog.dismiss();
        //获取服务器版本号
        int webVersionCode = appInfo.getVersion_code();
        //获取当前版本号
        String[] infoArray = CommonUtil.getCurrentAppVersionInfo(this);
        String currentVersionCode = infoArray[0];

        //格式化当前版本号
        int formatCurrentCode = TextUtils.isEmpty(currentVersionCode) ? 0 : Integer.parseInt(currentVersionCode);
        Log.d(TAG, "服务器版本 ==> " + webVersionCode + " 当前版本 ==> " + currentVersionCode);
        if (webVersionCode > formatCurrentCode) {
            //去处理有新版本的情况
            handleHasNewVersionApp(appInfo.isIsForceUpd(), appInfo.getApk_url());
        } else {
            //当前已是最新版本
            CommonUtil.createAlertDialogBuilder(this, "版本校验提示", "当前已是最新版本，无需更新！", true)
                    .setPositiveButton("确定", null)
                    .show();
        }
    }

    //----------------------------------------------------------------- UI回调接口 -end -------------------------------------------------------------------

    /**
     * 处理有新本的情况
     *
     * @param isForceUpd 是否为强制更新
     * @param apkUrl     安装包下载地址
     */
    private void handleHasNewVersionApp(boolean isForceUpd, String apkUrl) {
        //判断下载地址是否为空
        if (TextUtils.isEmpty(apkUrl)) {
            CommonUtil.createAlertDialogBuilder(this, "下载失败", "下载地址为空，无法正常下载！", true)
                    .setPositiveButton("确定", null)
                    .show();
            return;
        }

        if (isForceUpd) {
            //强制更新，直接下载安装
            doUpgrade(apkUrl);
        } else {
            //非强制更新，询问用户是否下载更新
            CommonUtil.createAlertDialogBuilder(this, "检测到新版本", "是否下载安装最新版本？", false)
                    .setPositiveButton("立即更新", (dialog, i) -> {
                        doUpgrade(apkUrl);
                        dialog.dismiss();
                    })
                    .setNegativeButton("暂不更新", null)
                    .show();
        }
    }

    /**
     * 更新升级
     *
     * @param apkUrl 安装包下载地址
     */
    private void doUpgrade(String apkUrl) {
        new AppUpdater.Builder()
                .setUrl(apkUrl)
                .build(this)
                .start();
    }
}
