package com.cg.mrice.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.cg.mrice.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by app on 2018/4/20.
 */
public class YhAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private String gameEn;

    public YhAdapter(@LayoutRes int layoutResId, @Nullable List<String> data, String gameEn) {
        super(layoutResId, data);
        this.gameEn = gameEn;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String item) {
        String[] balls = item.split(",");
        baseViewHolder.addOnClickListener(R.id.del);
        if ("ssq".equals(gameEn)) {
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
            baseViewHolder.getView(R.id.b6).setBackgroundResource(R.drawable.ball_red_s);
            baseViewHolder.getView(R.id.b7).setBackgroundResource(R.drawable.ball_red_s);
        } else if ("qlc".equals(gameEn)) {
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
            baseViewHolder.getView(R.id.b6).setBackgroundResource(R.drawable.ball_red_img);
            baseViewHolder.getView(R.id.b7).setBackgroundResource(R.drawable.ball_red_img);
        } else if ("dlt".equals(gameEn)) {
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
            baseViewHolder.getView(R.id.b6).setBackgroundResource(R.drawable.ball_blue_img);
            baseViewHolder.getView(R.id.b7).setBackgroundResource(R.drawable.ball_blue_img);
        } else if ("pl3".equals(gameEn)) {
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
        } else if ("pl5".equals(gameEn)) {
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
        } else if ("qxc".equals(gameEn)) {
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
            baseViewHolder.getView(R.id.b6).setBackgroundResource(R.drawable.ball_red_img);
            baseViewHolder.getView(R.id.b7).setBackgroundResource(R.drawable.ball_red_img);
        } else if ((gameEn.contains("kuai3"))) {
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
        } else if (gameEn.contains("d11")) {
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
        } else {
            baseViewHolder.setText(R.id.b1, balls[0]);
            baseViewHolder.setText(R.id.b2, balls[1]);
            baseViewHolder.setText(R.id.b3, balls[2]);
            baseViewHolder.setText(R.id.b4, balls[3]);
            baseViewHolder.setText(R.id.b5, balls[4]);
            baseViewHolder.getView(R.id.b4).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b5).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.b6).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.b7).setVisibility(View.GONE);
        }
    }
}
