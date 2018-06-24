package com.cg.mrice.model;

import android.text.TextUtils;

/**
 * Created by app on 2018/2/10.
 */
public class NewBean2000 {

    private String appid;
    private String appname;
    private String isshowwap;
    private String wapurl;
    private String status;
    private String desc;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getIsshowwap() {
        return isshowwap;
    }

    public void setIsshowwap(String isshowwap) {
        this.isshowwap = isshowwap;
    }

    public String getWapurl() {
        return wapurl;
    }

    public void setWapurl(String wapurl) {
        this.wapurl = wapurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSwitchOn() {
        return TextUtils.equals(isshowwap, "1");
    }
}
