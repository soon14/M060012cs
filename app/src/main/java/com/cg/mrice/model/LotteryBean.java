package com.cg.mrice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr on 2017/12/16.
 */
public class LotteryBean implements Serializable {

    private int result;
    private String totalBonus;
    private List<LotteryDetails> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(String totalBonus) {
        this.totalBonus = totalBonus;
    }

    public List<LotteryDetails> getData() {
        return data;
    }

    public void setData(List<LotteryDetails> data) {
        this.data = data;
    }

    public class LotteryDetails implements Serializable {

        private String awardNo;
        private String awardTime;
        private String extra;
        private String gameEn;
        private String luckyBlue;
        private String periodName;
        private String totalPool;
        private String totalSale;

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

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getGameEn() {
            return gameEn;
        }

        public void setGameEn(String gameEn) {
            this.gameEn = gameEn;
        }

        public String getLuckyBlue() {
            return luckyBlue;
        }

        public void setLuckyBlue(String luckyBlue) {
            this.luckyBlue = luckyBlue;
        }

        public String getPeriodName() {
            return periodName;
        }

        public void setPeriodName(String periodName) {
            this.periodName = periodName;
        }

        public String getTotalPool() {
            return totalPool;
        }

        public void setTotalPool(String totalPool) {
            this.totalPool = totalPool;
        }

        public String getTotalSale() {
            return totalSale;
        }

        public void setTotalSale(String totalSale) {
            this.totalSale = totalSale;
        }
    }

}
