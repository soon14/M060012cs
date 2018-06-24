package com.cg.mrice.fragment;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cg.mrice.R;
import com.cg.mrice.RulesActivity;
import com.cg.mrice.adapter.LDetaisAdapter;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.model.LotteryGamePeriodXml;
import com.cg.mrice.utils.ShareUtils;
import com.cg.mrice.utils.ToastUtil;
import com.cg.mrice.utils.XMLHelper;
import com.cg.mrice.view.boommenu.BoomButtons.HamButton;
import com.cg.mrice.view.boommenu.BoomButtons.OnBMClickListener;
import com.cg.mrice.view.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by app on 2018/4/16.
 */
public class HistoryDetails extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ImageView img;
    @BindView(R.id.footb)
    TextView footb;
    TextView tTitle;
    TextView tTime;
    TextView b1;
    TextView b2;
    TextView b3;
    TextView b4;
    TextView b5;
    TextView b6;
    TextView b7;
    TextView jc;
    TextView sales;
    private String title = "时时彩";
    private String gameEn = "ssc";
    String[] balls;
    private LotteryGamePeriodXml data;

    private TextView lm1, lm2, lm3, lm4, lm5, lm6;
    private TextView lr1, lr2, lr3, lr4, lr5, lr6;
    private BoomMenuButton bmb2;
    private int[] imgs = new int[]{R.drawable.dynamic_fill, R.drawable.share_fill};
    private String[] mainTxt = new String[]{"玩法规则", "分 享"};
    private String[] subTxt = new String[]{"多种玩法 一目了然", "把号码分享给好友吧"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_details);
        ButterKnife.bind(this);
        initButton();
        initView();
    }

    private void initButton() {
        bmb2 = (BoomMenuButton) findViewById(R.id.bmb2);
        for (int i = 0; i < bmb2.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder().listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    switch (index) {
                        case 0:
                            Intent intent = new Intent(HistoryDetails.this, RulesActivity.class);
                            if ("时时彩".equals(title)) {
                                intent.putExtra("name", "时时彩");
                            } else if ("双色球".equals(title)) {
                                intent.putExtra("name", "双色球");
                            } else if ("大乐透".equals(title)) {
                                intent.putExtra("name", "大乐透");
                            } else if ("七乐彩".equals(title)) {
                                intent.putExtra("name", "七乐彩");
                            } else if ("七星彩".equals(title)) {
                                intent.putExtra("name", "七星彩");
                            } else if ("排列三".equals(title)) {
                                intent.putExtra("name", "排列三");
                            } else if ("排列五".equals(title)) {
                                intent.putExtra("name", "排列五");
                            } else if ("快三".equals(title)) {
                                intent.putExtra("name", "快三");
                            } else if ("11选5".equals(title)) {
                                intent.putExtra("name", "11选5");
                            } else if ("任选九".equals(title)) {
                                intent.putExtra("name", "任选九");
                            } else if ("胜负彩".equals(title)) {
                                intent.putExtra("name", "胜负彩");
                            } else {
                                intent.putExtra("name", "时时彩");
                            }
                            startActivity(intent);
                            break;
                        case 1:
                            if (data != null) {
                                ShareUtils.share(HistoryDetails.this, title + ":" + data.getAwardNo());
                            } else {
                                ShareUtils.share(HistoryDetails.this, title);
                            }
                            break;
                    }
                }
            }).normalImageRes(imgs[i]).normalText(mainTxt[i]).subNormalText(subTxt[i]);
            bmb2.addBuilder(builder);
        }
    }

    private void initView() {
        toolbar.setTitle("开奖详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.bottom_kj).setVisibility(View.GONE);
        tTitle = (TextView) findViewById(R.id.title);
        tTime = (TextView) findViewById(R.id.time);
        img = (ImageView) findViewById(R.id.detailsimg);
        jc = (TextView) findViewById(R.id.jc);
        sales = (TextView) findViewById(R.id.sales);

        b1 = (TextView) findViewById(R.id.b11);
        b2 = (TextView) findViewById(R.id.b21);
        b3 = (TextView) findViewById(R.id.b31);
        b4 = (TextView) findViewById(R.id.b41);
        b5 = (TextView) findViewById(R.id.b51);
        b6 = (TextView) findViewById(R.id.b61);
        b7 = (TextView) findViewById(R.id.b71);
        lm1 = (TextView) findViewById(R.id.line_m1);
        lm2 = (TextView) findViewById(R.id.line_m2);
        lm3 = (TextView) findViewById(R.id.line_m3);
        lm4 = (TextView) findViewById(R.id.line_m4);
        lm5 = (TextView) findViewById(R.id.line_m5);
        lm6 = (TextView) findViewById(R.id.line_m6);

        lr1 = (TextView) findViewById(R.id.line_r1);
        lr2 = (TextView) findViewById(R.id.line_r2);
        lr3 = (TextView) findViewById(R.id.line_r3);
        lr4 = (TextView) findViewById(R.id.line_r4);
        lr5 = (TextView) findViewById(R.id.line_r5);
        lr6 = (TextView) findViewById(R.id.line_r6);
        initData();
    }

    private void initData() {
        data = (LotteryGamePeriodXml) getIntent().getSerializableExtra("data");
        title = getIntent().getStringExtra("gameEn");
        Random random = new Random();
        if ("ssq".equals(title)) {
            title = "双色球";
            balls = data.getAwardNo().replace(":", " ").split(" ");
            img.setImageResource(R.drawable.lottery_icon_ssq);
            b7.setBackgroundResource(R.drawable.ball_blue_s);
            b1.setText(balls[0]);
            b2.setText(balls[1]);
            b3.setText(balls[2]);
            b4.setText(balls[3]);
            b5.setText(balls[4]);
            b6.setText(balls[5]);
            b7.setText(balls[6]);
            lm1.setText(random.nextInt(2) + "");
            lm2.setText(random.nextInt(100) + "");
            lm3.setText(random.nextInt(999) + 3 + "");
            lm4.setText(random.nextInt(999) + 32 + "");
            lm5.setText(random.nextInt(999) + 320 + "");
            lm6.setText(random.nextInt(999) + 32000 + "");
            lr1.setText("14232000");
            lr2.setText("900000");
            lr3.setText("20000");
            lr4.setText("1000");
            lr5.setText("10");
            lr6.setText("5");
        } else if ("qlc".equals(title)) {
            title = "七乐彩";
            balls = data.getAwardNo().replace(":", " ").split(" ");
            img.setImageResource(R.drawable.lottery_icon_qlc);
            b1.setText(balls[0]);
            b2.setText(balls[1]);
            b3.setText(balls[2]);
            b4.setText(balls[3]);
            b5.setText(balls[4]);
            b6.setText(balls[5]);
            b7.setText(balls[6]);
            lm1.setText(random.nextInt(99) + "");
            lm2.setText(random.nextInt(1000) + "");
            lm3.setText(random.nextInt(999) + 1000 + "");
            lm4.setText(random.nextInt(999) + 400 + "");
            lm5.setText(random.nextInt(999) + 3 + "");
            lm6.setText(random.nextInt(999) + 32 + "");
            lr1.setText("5000000");
            lr2.setText("2200000");
            lr3.setText("50000");
            lr4.setText("1000");
            lr5.setText("100");
            lr6.setText("5");
        } else if ("dlt".equals(title)) {
            title = "大乐透";
            balls = data.getAwardNo().replace(":", " ").split(" ");
            img.setImageResource(R.drawable.lottery_icon_lotto);
            b1.setText(balls[0]);
            b2.setText(balls[1]);
            b3.setText(balls[2]);
            b4.setText(balls[3]);
            b5.setText(balls[4]);
            b6.setText(balls[5]);
            b7.setText(balls[6]);
            b6.setBackgroundResource(R.drawable.ball_blue_s);
            b7.setBackgroundResource(R.drawable.ball_blue_s);
            lm1.setText(random.nextInt(2) + "");
            lm2.setText(random.nextInt(100) + "");
            lm3.setText(random.nextInt(999) + 32 + "");
            lm4.setText(random.nextInt(999) + 320 + "");
            lm5.setText(random.nextInt(999) + 3200 + "");
            lm6.setText(random.nextInt(999) + 32000 + "");
            lr1.setText("9000000");
            lr2.setText("2200000");
            lr3.setText("100000");
            lr4.setText("3000");
            lr5.setText("200");
            lr6.setText("5");
        } else if ("qxc".equals(title)) {
            title = "七星彩";
            balls = data.getAwardNo().split(" ");
            img.setImageResource(R.drawable.lottery_icon_qxc);
            b1.setText(balls[0]);
            b2.setText(balls[1]);
            b3.setText(balls[2]);
            b4.setText(balls[3]);
            b5.setText(balls[4]);
            b6.setText(balls[5]);
            b7.setText(balls[6]);
            lm1.setText(random.nextInt(99) + "");
            lm2.setText(random.nextInt(1000) + "");
            lm3.setText(random.nextInt(999) + 32 + "");
            lm4.setText(random.nextInt(999) + 320 + "");
            lm5.setText(random.nextInt(999) + 320 + "");
            lm6.setText(random.nextInt(999) + 3200 + "");
            lr1.setText("5000000");
            lr2.setText("1200000");
            lr3.setText("90000");
            lr4.setText("3000");
            lr5.setText("500");
            lr6.setText("10");
        } else if ("pl3".equals(title)) {
            title = "排列三";
            balls = data.getAwardNo().split(" ");
            img.setImageResource(R.drawable.lottery_icon_pl3);
            b1.setText(balls[0]);
            b2.setText(balls[1]);
            b3.setText(balls[2]);
            b4.setVisibility(View.GONE);
            b5.setVisibility(View.GONE);
            b6.setVisibility(View.GONE);
            b7.setVisibility(View.GONE);
            lm1.setText(random.nextInt(999) + 8000 + "");
            lm2.setText(random.nextInt(1000) + "");
            lm3.setText(random.nextInt(999) + 32000 + "");
            lm4.setText("-");
            lm5.setText("-");
            lm6.setText("-");
            lr1.setText("1040");
            lr2.setText("346");
            lr3.setText("173");
            lr4.setText("-");
            lr5.setText("-");
            lr6.setText("-");
        } else if ("pl5".equals(title)) {
            title = "排列五";
            balls = data.getAwardNo().split(" ");
            img.setImageResource(R.drawable.lottery_icon_pl5);
            b1.setText(balls[0]);
            b2.setText(balls[1]);
            b3.setText(balls[2]);
            b4.setText(balls[3]);
            b5.setText(balls[4]);
            b6.setVisibility(View.GONE);
            b7.setVisibility(View.GONE);
            lm1.setText(random.nextInt(999) + 8000 + "");
            lm2.setText(random.nextInt(1000) + "");
            lm3.setText(random.nextInt(999) + 320 + "");
            lm4.setText(random.nextInt(999) + 3200 + "");
            lm5.setText(random.nextInt(999) + 32000 + "");
            lm6.setText("-");
            lr1.setText("1040");
            lr2.setText("346");
            lr3.setText("173");
            lr4.setText("20");
            lr5.setText("5");
            lr6.setText("-");
        } else if ("kuai3".equals(title)) {
            title = "快三";
            balls = data.getAwardNo().split(" ");
            img.setImageResource(R.drawable.lottery_icon_k3);
            b1.setText(balls[0]);
            b2.setText(balls[1]);
            b3.setText(balls[2]);
            b4.setVisibility(View.GONE);
            b5.setVisibility(View.GONE);
            b6.setVisibility(View.GONE);
            b7.setVisibility(View.GONE);
            lm1.setText(random.nextInt(999) + 8000 + "");
            lm2.setText(random.nextInt(1000) + "");
            lm3.setText(random.nextInt(999) + 32000 + "");
            lm4.setText("-");
            lm5.setText("-");
            lm6.setText("-");
            lr1.setText("1040");
            lr2.setText("346");
            lr3.setText("173");
            lr4.setText("-");
            lr5.setText("-");
            lr6.setText("-");
        } else if ("d11".equals(title)) {
            title = "11选5";
            balls = data.getAwardNo().split(" ");
            img.setImageResource(R.drawable.lottery_icon_11x5);
            b1.setText(balls[0]);
            b2.setText(balls[1]);
            b3.setText(balls[2]);
            b4.setText(balls[3]);
            b5.setText(balls[4]);
            b6.setVisibility(View.GONE);
            b7.setVisibility(View.GONE);
            lm1.setText(random.nextInt(999) + 8000 + "");
            lm2.setText(random.nextInt(1000) + "");
            lm3.setText(random.nextInt(999) + 320 + "");
            lm4.setText(random.nextInt(999) + 3200 + "");
            lm5.setText(random.nextInt(999) + 32000 + "");
            lm6.setText("-");
            lr1.setText("1040");
            lr2.setText("346");
            lr3.setText("173");
            lr4.setText("20");
            lr5.setText("5");
            lr6.setText("-");
        } else if (title.contains("football")) {
            if (title.equals("football_9")) {
                title = "任选九";
                img.setImageResource(R.drawable.lottery_icon_rx9);
            } else {
                title = "胜负彩";
                img.setImageResource(R.drawable.lottery_icon_sfc);
            }
            footb.setVisibility(View.VISIBLE);
            footb.setText(data.getAwardNo());
            lm1.setText(random.nextInt(999) + 8000 + "");
            lm2.setText(random.nextInt(1000) + "");
            lm3.setText(random.nextInt(999) + 320 + "");
            lm4.setText(random.nextInt(999) + 3200 + "");
            lm5.setText(random.nextInt(999) + 32000 + "");
            lm6.setText("-");
            lr1.setText("1040");
            lr2.setText("346");
            lr3.setText("173");
            lr4.setText("20");
            lr5.setText("5");
            lr6.setText("-");
        } else {
            title = "新疆时时彩";
            balls = data.getAwardNo().split(" ");
            img.setImageResource(R.drawable.lottery_icon_new_ssc);
            b1.setText(balls[0]);
            b2.setText(balls[1]);
            b3.setText(balls[2]);
            b4.setText(balls[3]);
            b5.setText(balls[4]);
            b6.setVisibility(View.GONE);
            b7.setVisibility(View.GONE);
            lm1.setText(random.nextInt(999) + 8000 + "");
            lm2.setText(random.nextInt(1000) + "");
            lm3.setText(random.nextInt(999) + 320 + "");
            lm4.setText(random.nextInt(999) + 3200 + "");
            lm5.setText(random.nextInt(999) + 32000 + "");
            lm6.setText("-");
            lr1.setText("1040");
            lr2.setText("346");
            lr3.setText("173");
            lr4.setText("20");
            lr5.setText("5");
            lr6.setText("-");
        }
        tTitle.setText(title);
        tTime.setText("第 " + data.getPeriodName() + " 期");
        jc.setText(data.getTotalpool());
        sales.setText(data.getTotalsale());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (data != null) {
            data = null;
        }
    }
}
