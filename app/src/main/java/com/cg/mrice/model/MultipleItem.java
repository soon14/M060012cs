package com.cg.mrice.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by app on 2017/11/30.
 */
public class MultipleItem implements MultiItemEntity{

    public static final int XINWEN = 1;
    public static final int MUKUAI = 2;
    public static final int BANNER = 3;
    private int itemType;

    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    private String title;
    private String content;
    private String pubTime;
    private String url;
    private String source;
    private int resId;
    private String temp;
    private String gameEn;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getGameEn() {
        return gameEn;
    }

    public void setGameEn(String gameEn) {
        this.gameEn = gameEn;
    }
}
