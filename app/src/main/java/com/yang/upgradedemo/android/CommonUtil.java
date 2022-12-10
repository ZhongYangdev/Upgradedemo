package com.yang.upgradedemo.android;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

/**
 * @作者 向贵龙
 * @创建时间 2022/12/9 23:45
 * @描述 常用工具类
 */
public class CommonUtil {

    /**
     * 创建AlertDialog构建器
     *
     * @param title       对话框标题
     * @param content     对话框内容
     * @param focusCancel 点击外部是否关闭对话框
     * @return AlertDialog.Builder
     */
    public static AlertDialog.Builder createAlertDialogBuilder(
            Context context,
            String title,
            String content,
            boolean focusCancel
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(content)
                .setCancelable(focusCancel);
        return builder;
    }

    /**
     * 显示加载中对话框
     *
     * @param title   对话框标题
     * @param content 对话框内容
     */
    public static AlertDialog showLoadingDialog(
            Context context,
            String title,
            String content
    ) {
        //载布局
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        //找控件
        TextView tvTitle = dialogView.findViewById(R.id.tv_loadingTitle);
        TextView tvContent = dialogView.findViewById(R.id.tv_loadingContent);
        //绑数据
        tvTitle.setText(TextUtils.isEmpty(title) ? "UI更新提示" : title);
        tvContent.setText(TextUtils.isEmpty(content) ? "正在加载中，请稍后..." : content);
        //构建对话框，此处使用自定义布局
        return new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .show();
    }

    /**
     * 获取当前版本信息
     *
     * @return [0]：当前版本号，[1]：当前版本名称
     */
    public static String[] getCurrentAppVersionInfo(Context context) {
        int versionCode = 0;//APP版本号
        String versionName = null;//APP版本名称
        try {
            //获取APP版本号并赋值
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(0)).versionCode;
            //获取APP版本名称并赋值
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(0)).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return new String[]{String.valueOf(versionCode), versionName};
    }
}
