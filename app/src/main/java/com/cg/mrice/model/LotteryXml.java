package com.cg.mrice.model;

/**
 * Created by app on 2017/12/18.
 */
public class LotteryXml {

    private String totalBonus;

    private LotteryGameXml game;

    public LotteryXml() {
    }

    public String getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(String totalBonus) {
        this.totalBonus = totalBonus;
    }

    public LotteryGameXml getGame() {
        return game;
    }

    public void setGame(LotteryGameXml game) {
        this.game = game;
    }
}


