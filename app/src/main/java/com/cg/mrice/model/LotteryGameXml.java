package com.cg.mrice.model;

import java.util.List;

/**
 * Created by app on 2017/12/18.
 */
public class LotteryGameXml {

    private String en;
    private String nextAwardTime;
    private List<LotteryGamePeriodXml> data;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getNextAwardTime() {
        return nextAwardTime;
    }

    public void setNextAwardTime(String nextAwardTime) {
        this.nextAwardTime = nextAwardTime;
    }

    public List<LotteryGamePeriodXml> getData() {
        return data;
    }

    public void setData(List<LotteryGamePeriodXml> data) {
        this.data = data;
    }
}
