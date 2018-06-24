package com.cg.mrice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cg.mrice.LotteryAllDetails;
import com.cg.mrice.PanActivity;
import com.cg.mrice.R;
import com.cg.mrice.adapter.LotteryAdapter;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.model.LotteryBean;
import com.cg.mrice.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Mr on 2018/4/14.
 */

public class LotteryActivity extends AppCompatActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    LotteryAdapter adapter;
    private List<LotteryBean.LotteryDetails> datas = new ArrayList<>();
    @BindView(R.id.refresh_lottery)
    SwipeRefreshLayout swipeRefreshLayout;
    private JZVideoPlayerStandard jzVideoPlayerStandard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("最新开奖");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initView();
        initVideo();
    }

    private void initView() {
        datas.clear();
        datas.addAll(PanActivity.datas);
        adapter = new LotteryAdapter(R.layout.lottery_item, datas);
        View v = LayoutInflater.from(this).inflate(R.layout.kj_vedio_top, null);
        jzVideoPlayerStandard = (JZVideoPlayerStandard) v.findViewById(R.id.videoplayer);
        adapter.addHeaderView(v);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(LotteryActivity.this, LotteryAllDetails.class);
                String gm = datas.get(position).getGameEn();
                if ("ssq".equals(gm)) {
                    i.putExtra("title", "双色球");
                } else if ("qlc".equals(gm)) {
                    i.putExtra("title", "七乐彩");
                } else if ("dlt".equals(gm)) {
                    i.putExtra("title", "大乐透");
                } else if ("pl3".equals(gm)) {
                    i.putExtra("title", "排列三");
                } else if ("pl5".equals(gm)) {
                    i.putExtra("title", "排列五");
                } else if ("qxc".equals(gm)) {
                    i.putExtra("title", "七星彩");
                } else if ((gm.contains("kuai3"))) {
                    if ("hbkuai3".equals(gm)) {
                        i.putExtra("title", "河北快三");
                    } else if ("gxkuai3".equals(gm)) {
                        i.putExtra("title", "广西快三");
                    } else if ("nmgkuai3".equals(gm)) {
                        i.putExtra("title", "内蒙古快三");
                    } else if ("oldkuai3".equals(gm)) {
                        i.putExtra("title", "快三");
                    } else {
                        i.putExtra("title", "新快三");
                    }
                } else if (gm.contains("d11")) {
                    if ("zjd11".equals(gm)) {
                        i.putExtra("title", "浙江11选5");
                    } else if ("jxd11".equals(gm)) {
                        i.putExtra("title", "江西11选5");
                    } else if ("hljd11".equals(gm)) {
                        i.putExtra("title", "黑龙江11选5");
                    } else if ("lnd11".equals(gm)) {
                        i.putExtra("title", "辽宁11选5");
                    } else if ("gdd11".equals(gm)) {
                        i.putExtra("title", "广东11选5");
                    } else {
                        i.putExtra("title", "11选5");
                    }
                } else if (gm.contains("ssc")) {
                    if ("jxssc".equals(gm)) {
                        i.putExtra("title", "重庆时时彩");
                    } else {
                        i.putExtra("title", "时时彩");
                    }
                } else if (gm.contains("football")) {
                    if ("football_9".equals(gm)) {
                        i.putExtra("title", "任选九（足球）");
                    } else {
                        i.putExtra("title", "胜负彩（足球）");
                    }
                } else {
                    ToastUtil.showToast("该彩种暂无详细信息，请选择其他彩种");
                    return;
                }
                i.putExtra("data", datas.get(position));
                startActivity(i);
            }
        });

        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        ToastUtil.showToast("已经是最新数据");
                    }
                }, 1000);
            }
        });
    }

    private void initVideo() {
        jzVideoPlayerStandard.setUp("http://flv2.bn.netease.com/videolib3/1804/21/snODP7519/SD/snODP7519-mobile.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        Glide.with(getApplicationContext()).load(R.drawable.kjbg).into(jzVideoPlayerStandard.thumbImageView);

    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (datas != null) {
            datas.clear();
            datas = null;
        }
    }
}
