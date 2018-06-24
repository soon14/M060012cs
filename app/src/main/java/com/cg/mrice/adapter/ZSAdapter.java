package com.cg.mrice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.cg.mrice.MyApp;
import com.cg.mrice.R;
import com.cg.mrice.model.LotteryGamePeriodXml;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by app on 2018/2/23.
 */
public class ZSAdapter extends BaseQuickAdapter<LotteryGamePeriodXml, BaseViewHolder> {

    private Context context;

    public ZSAdapter(int layoutResId, @Nullable List<LotteryGamePeriodXml> data) {
        super(layoutResId, data);
        context = MyApp.getInstance();
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LotteryGamePeriodXml item) {
        String[] balls = item.getAwardNo().replace(":", " ").split(" ");
        baseViewHolder.setText(R.id.qh, item.getPeriodName());
        baseViewHolder.setBackgroundRes(R.id.b0, android.R.color.transparent);
        baseViewHolder.setBackgroundRes(R.id.b1, android.R.color.transparent);
        baseViewHolder.setBackgroundRes(R.id.b2, android.R.color.transparent);
        baseViewHolder.setBackgroundRes(R.id.b3, android.R.color.transparent);
        baseViewHolder.setBackgroundRes(R.id.b4, android.R.color.transparent);
        baseViewHolder.setBackgroundRes(R.id.b5, android.R.color.transparent);
        baseViewHolder.setBackgroundRes(R.id.b6, android.R.color.transparent);
        baseViewHolder.setBackgroundRes(R.id.b7, android.R.color.transparent);
        baseViewHolder.setBackgroundRes(R.id.b8, android.R.color.transparent);
        baseViewHolder.setBackgroundRes(R.id.b9, android.R.color.transparent);
        baseViewHolder.setTextColor(R.id.b0, Color.BLACK);
        baseViewHolder.setTextColor(R.id.b1, Color.BLACK);
        baseViewHolder.setTextColor(R.id.b2, Color.BLACK);
        baseViewHolder.setTextColor(R.id.b3, Color.BLACK);
        baseViewHolder.setTextColor(R.id.b4, Color.BLACK);
        baseViewHolder.setTextColor(R.id.b5, Color.BLACK);
        baseViewHolder.setTextColor(R.id.b6, Color.BLACK);
        baseViewHolder.setTextColor(R.id.b7, Color.BLACK);
        baseViewHolder.setTextColor(R.id.b8, Color.BLACK);
        baseViewHolder.setTextColor(R.id.b9, Color.BLACK);
        for (String b : balls) {
            if ("0".equals(b) || "00".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b0, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b0, context.getResources().getColor(R.color.white));
            } else if ("1".equals(b) || "01".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b1, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b1, context.getResources().getColor(R.color.white));
            } else if ("2".equals(b) || "02".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b2, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b2, context.getResources().getColor(R.color.white));
            } else if ("3".equals(b) || "03".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b3, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b3, context.getResources().getColor(R.color.white));
            } else if ("4".equals(b) || "04".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b4, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b4, context.getResources().getColor(R.color.white));
            } else if ("5".equals(b) || "05".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b5, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b5, context.getResources().getColor(R.color.white));
            } else if ("6".equals(b) || "06".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b6, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b6, context.getResources().getColor(R.color.white));
            } else if ("7".equals(b) || "07".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b7, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b7, context.getResources().getColor(R.color.white));
            } else if ("8".equals(b) || "08".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b8, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b8, context.getResources().getColor(R.color.white));
            } else if ("9".equals(b) || "09".equals(b)) {
                baseViewHolder.setBackgroundRes(R.id.b9, R.drawable.ball_red_s);
                baseViewHolder.setTextColor(R.id.b9, context.getResources().getColor(R.color.white));
            }
        }
    }
}
