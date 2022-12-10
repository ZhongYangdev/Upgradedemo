package com.yang.upgradedemo.android.model;

/**
 * @作者 向贵龙
 * @创建时间 2022/12/9 15:19
 * @描述 APP信息返回数据模型
 */
public class AppInfoResponse {

    /**
     * 版本号
     */
    private int version_code;
    /**
     * 版本名称
     */
    private String version_name;
    /**
     * 是否强制更新
     */
    private boolean isForceUpd;
    /**
     * 安装装包地址
     */
    private String apk_url;

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public boolean isIsForceUpd() {
        return isForceUpd;
    }

    public void setIsForceUpd(boolean isForceUpd) {
        this.isForceUpd = isForceUpd;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }
}
