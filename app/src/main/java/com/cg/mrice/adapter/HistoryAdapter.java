package com.cg.mrice.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.cg.mrice.MyApp;
import com.cg.mrice.R;
import com.cg.mrice.model.LotteryGamePeriodXml;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Mr on 2018/4/10.
 */

public class HistoryAdapter extends BaseQuickAdapter<LotteryGamePeriodXml, BaseViewHolder> {

    private Context context;
    private String gameEn = "ssc";

    public HistoryAdapter(int layoutResId, @Nullable List<LotteryGamePeriodXml> data) {
        super(layoutResId, data);
        context = MyApp.getInstance();
    }

    public void setGameEn(String gameEn) {
        this.gameEn = gameEn;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LotteryGamePeriodXml multipleItem) {
        baseViewHolder.setText(R.id.time, multipleItem.getAwardTime()).setText(R.id.qh, "第 " + multipleItem.getPeriodName() + " 期");
        if ("0.00".equals(multipleItem.getTotalpool())) {
            baseViewHolder.setText(R.id.je, "-");
        } else {
            baseViewHolder.setText(R.id.je, multipleItem.getTotalpool());
        }
        String[] balls = multipleItem.getAwardNo().replace(":", " ").split(" ");
        if ("ssq".equals(gameEn)) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_ssq);
            baseViewHolder.setText(R.id.title, "双色球");
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.setText(R.id.b6, balls[5]);
            baseViewHolder.setText(R.id.b7, balls[6]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setBackgroundResource(R.drawable.ball_red);
            baseViewHolder.getView(R.id.b7).setBackgroundResource(R.drawable.ball_blue);
            baseViewHolder.setTextColor(R.id.b6, context.getResources().getColor(R.color.holo_red_light));
            baseViewHolder.setTextColor(R.id.b7, context.getResources().getColor(R.color.main_blue));
        } else if ("qlc".equals(gameEn)) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_qlc);
            baseViewHolder.setText(R.id.title, "七乐彩");
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.setText(R.id.b6, balls[5]);
            baseViewHolder.setText(R.id.b7, balls[6]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setBackgroundResource(R.drawable.ball_red);
            baseViewHolder.getView(R.id.b7).setBackgroundResource(R.drawable.ball_red);
            baseViewHolder.setTextColor(R.id.b6, context.getResources().getColor(R.color.holo_red_light));
            baseViewHolder.setTextColor(R.id.b7, context.getResources().getColor(R.color.holo_red_light));
        } else if ("dlt".equals(gameEn)) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_lotto);
            baseViewHolder.setText(R.id.title, "超级大乐透");
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.setText(R.id.b6, balls[5]);
            baseViewHolder.setText(R.id.b7, balls[6]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setBackgroundResource(R.drawable.ball_blue);
            baseViewHolder.getView(R.id.b7).setBackgroundResource(R.drawable.ball_blue);
            baseViewHolder.setTextColor(R.id.b6, context.getResources().getColor(R.color.main_blue));
            baseViewHolder.setTextColor(R.id.b7, context.getResources().getColor(R.color.main_blue));
        } else if ("pl3".equals(gameEn)) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_pl3);
            baseViewHolder.setText(R.id.title, "排列三");
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
            baseViewHolder.setTextColor(R.id.b6, context.getResources().getColor(R.color.holo_red_light));
            baseViewHolder.setTextColor(R.id.b7, context.getResources().getColor(R.color.holo_red_light));
        } else if ("pl5".equals(gameEn)) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_pl5);
            baseViewHolder.setText(R.id.title, "排列五");
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
            baseViewHolder.setTextColor(R.id.b6, context.getResources().getColor(R.color.holo_red_light));
            baseViewHolder.setTextColor(R.id.b7, context.getResources().getColor(R.color.holo_red_light));
        } else if ("qxc".equals(gameEn)) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_qxc);
            baseViewHolder.setText(R.id.title, "七星彩");
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.setText(R.id.b6, balls[5]);
            baseViewHolder.setText(R.id.b7, balls[6]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setBackgroundResource(R.drawable.ball_red);
            baseViewHolder.getView(R.id.b7).setBackgroundResource(R.drawable.ball_red);
            baseViewHolder.setTextColor(R.id.b6, context.getResources().getColor(R.color.holo_red_light));
            baseViewHolder.setTextColor(R.id.b7, context.getResources().getColor(R.color.holo_red_light));
        } else if ((gameEn.contains("kuai3"))) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_k3);
            if ("hbkuai3".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "河北快三");
            } else if ("gxkuai3".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "广西快三");
            } else if ("nmgkuai3".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "内蒙古快三");
            } else if ("oldkuai3".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "快三");
            } else {
                baseViewHolder.setText(R.id.title, "新快三");
            }
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
        } else if (gameEn.contains("d11")) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_11x5);
            if ("zjd11".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "浙江11选5");
            } else if ("jxd11".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "江西11选5");
            } else if ("hljd11".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "黑龙江11选5");
            } else if ("lnd11".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "辽宁11选5");
            } else if ("gdd11".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "广东11选5");
            } else {
                baseViewHolder.setText(R.id.title, "11选5");
            }
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
        } else if (gameEn.contains("ssc")) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_new_ssc);
            if ("jxssc".equals(gameEn)) {
                baseViewHolder.setText(R.id.title, "重庆时时彩");
            } else {
                baseViewHolder.setText(R.id.title, "新疆时时彩");
            }
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
        } else if (gameEn.contains("football")) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.VISIBLE);
            if ("football_9".equals(gameEn)) {
                baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_rx9);
                baseViewHolder.setText(R.id.title, "任选九（足球）");
            } else {
                baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_sfc);
                baseViewHolder.setText(R.id.title, "胜负彩（足球）");
            }
            baseViewHolder.setText(R.id.pre_txt, multipleItem.getAwardNo());
        } else if ("x3d".equals(gameEn)) {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_fc3d);
            baseViewHolder.setText(R.id.title, "福彩3D");
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
            baseViewHolder.setTextColor(R.id.b6, context.getResources().getColor(R.color.holo_red_light));
            baseViewHolder.setTextColor(R.id.b7, context.getResources().getColor(R.color.holo_red_light));
        } else {
            baseViewHolder.getView(R.id.pre_txt).setVisibility(View.VISIBLE);
            baseViewHolder.setImageResource(R.id.img, R.drawable.lottery_icon_klpk);
            baseViewHolder.setText(R.id.title, "快乐扑克");
            baseViewHolder.setText(R.id.pre_txt, multipleItem.getAwardNo());
        }
    }
}
