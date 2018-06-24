package com.cg.mrice.model;

import android.text.TextUtils;

/**
 * Created by Mr on 2017/12/11.
 */

public class SwithBeanTwo {

    private String id;
    private String appname;
    private String status;
    private String updateurl;
    private String updateflag;
    private String url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateurl() {
        return updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl;
    }

    public String getUpdateflag() {
        return updateflag;
    }

    public void setUpdateflag(String updateflag) {
        this.updateflag = updateflag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSwitchOn() {
        return TextUtils.equals(status, "1");
    }

}
