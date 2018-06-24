package com.cg.mrice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cg.mrice.data.NewsData;
import com.cg.mrice.model.NewsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr on 2018/4/10.
 */

public class NewsDetails extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.content)
    TextView content;
    private int position = 0;
    private ProgressDialog progressDialog;
    public List<NewsInfo> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        position = getIntent().getIntExtra("position", 0);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载,请稍后...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("新闻资讯");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        datas.clear();
        datas.addAll(NewsData.getNews());
        datas.addAll(NewsData.getNews2());
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                NewsInfo newsInfo = datas.get(position);
                title.setText(newsInfo.getTitle());
                time.setText(newsInfo.getTime());
                content.setText(newsInfo.getSourceUrl());
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (datas != null) {
            datas.clear();
            datas = null;
        }
    }
}
