package com.cg.mrice.adapter;

import android.support.annotation.Nullable;

import com.cg.mrice.R;
import com.cg.mrice.model.NewsInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Mr on 2018/4/10.
 */

public class NewsAdapter extends BaseQuickAdapter<NewsInfo, BaseViewHolder> {

    public NewsAdapter(int layoutResId, @Nullable List<NewsInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsInfo item) {
        helper.setImageResource(R.id.imgNews, item.getImgUrl()).setText(R.id.txtNews, item.getTitle()).setText(R.id.timeNews, item.getTime());

    }
}
