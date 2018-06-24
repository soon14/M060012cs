package com.cg.mrice.model;

import java.io.Serializable;

/**
 * Created by app on 2017/12/18.
 */
public class LotteryGamePeriodXml implements Serializable{
    private String periodName;
    private String awardNo;
    private String awardTime;
    private String totalsale;
    private String totalpool;
    private String extra;
    private String luckyBlue;

    public LotteryGamePeriodXml() {
    }

    public LotteryGamePeriodXml(String periodName, String awardNo, String awardTime, String totalsale, String totalpool, String extra, String luckyBlue) {
        this.periodName = periodName;
        this.awardNo = awardNo;
        this.awardTime = awardTime;
        this.totalsale = totalsale;
        this.totalpool = totalpool;
        this.extra = extra;
        this.luckyBlue = luckyBlue;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getAwardNo() {
        return awardNo;
    }

    public void setAwardNo(String awardNo) {
        this.awardNo = awardNo;
    }

    public String getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(String awardTime) {
        this.awardTime = awardTime;
    }

    public String getTotalsale() {
        return totalsale;
    }

    public void setTotalsale(String totalsale) {
        this.totalsale = totalsale;
    }

    public String getTotalpool() {
        return totalpool;
    }

    public void setTotalpool(String totalpool) {
        this.totalpool = totalpool;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getLuckyBlue() {
        return luckyBlue;
    }

    public void setLuckyBlue(String luckyBlue) {
        this.luckyBlue = luckyBlue;
    }
}
