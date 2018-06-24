package com.cg.mrice.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cg.mrice.R;
import com.cg.mrice.model.SportData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Mr on 2018/4/21.
 */

public class SportAdatper extends BaseQuickAdapter<SportData, BaseViewHolder> {

    private Context context;

    public SportAdatper(int layoutResId, @Nullable List<SportData> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SportData item) {
        helper.setText(R.id.zd, item.getZd()).setText(R.id.kd, item.getKd()).setText(R.id.jg, item.getResult()).setText(R.id.ss, item.getName()).setText(R.id.bf, item.getBf());
        Glide.with(context).load(item.getZdimg()).placeholder(R.drawable.national_flag_siluofake).into((ImageView) helper.getView(R.id.zdimg));
        Glide.with(context).load(item.getKdimg()).placeholder(R.drawable.national_flag_luomaniya).into((ImageView) helper.getView(R.id.kdimg));

        if ("德甲".equals(item.getName())) {
            helper.setBackgroundRes(R.id.ss, R.color.dj);
        } else if ("德乙".equals(item.getName())) {
            helper.setBackgroundRes(R.id.ss, R.color.dy);
        } else if ("西甲".equals(item.getName())) {
            helper.setBackgroundRes(R.id.ss, R.color.xj);
        } else if ("法甲".equals(item.getName())) {
            helper.setBackgroundRes(R.id.ss, R.color.fj);
        } else {
            helper.setBackgroundRes(R.id.ss, R.color.fy);
        }


        if ("胜".equals(item.getResult())) {
            helper.setTextColor(R.id.jg, context.getResources().getColor(R.color.holo_red_light));
        } else if ("平".equals(item.getResult())) {
            helper.setTextColor(R.id.jg, context.getResources().getColor(R.color.line3));
        } else {
            helper.setTextColor(R.id.jg, context.getResources().getColor(R.color.line7));
        }
    }
}
