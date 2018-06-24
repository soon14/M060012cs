package com.cg.mrice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.adapter.LDetaisAdapter;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.fragment.XCodeActivity;
import com.cg.mrice.fragment.ZSActivity;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.LotteryBean;
import com.cg.mrice.model.LotteryGamePeriodXml;
import com.cg.mrice.utils.ShareUtils;
import com.cg.mrice.utils.Utils;
import com.cg.mrice.utils.XMLHelper;
import com.cg.mrice.view.boommenu.BoomButtons.HamButton;
import com.cg.mrice.view.boommenu.BoomButtons.OnBMClickListener;
import com.cg.mrice.view.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr on 2018/4/14.
 */

public class LotteryAllDetails extends AppCompatActivity implements VolleyCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lottery_details_rv)
    RecyclerView rv;
    LDetaisAdapter adapter;
    ImageView img;
    TextView tTitle;
    TextView tTime;
    TextView footb;
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
    private List<LotteryGamePeriodXml> datas = new ArrayList<>();
    private List<LotteryGamePeriodXml> data;

    private TextView lm1, lm2, lm3, lm4, lm5, lm6;
    private TextView lr1, lr2, lr3, lr4, lr5, lr6;

    private LotteryBean.LotteryDetails lotteryDetails;
    private StringRequest request = null;
    private BoomMenuButton bmb2;
    private int[] imgs = new int[]{R.drawable.marketing_fill, R.drawable.task_fill, R.drawable.share_fill};
    private String[] mainTxt = new String[]{"图表分析", "走势分布", "分 享"};
    private String[] subTxt = new String[]{"码号走势 清晰展现", "查看各个彩种近期分布情况", "把号码分享给好友吧"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_details);
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
                            Intent intent = new Intent(LotteryAllDetails.this, BarChartActivity.class);
                            intent.putExtra("title", title);
                            intent.putExtra("code", lotteryDetails.getAwardNo());
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intent1 = new Intent(LotteryAllDetails.this, ZSActivity.class);
                            startActivity(intent1);
                            break;
                        case 2:
                            if (data != null) {
                                ShareUtils.share(LotteryAllDetails.this, title + ":" + lotteryDetails.getAwardNo());
                            } else {
                                ShareUtils.share(LotteryAllDetails.this, title);
                            }
                            break;
                    }
                }
            }).normalImageRes(imgs[i]).normalText(mainTxt[i]).subNormalText(subTxt[i]);
            bmb2.addBuilder(builder);
        }
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        lotteryDetails = (LotteryBean.LotteryDetails) getIntent().getSerializableExtra("data");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        View v = LayoutInflater.from(this).inflate(R.layout.lottery_top, null);
        tTitle = (TextView) v.findViewById(R.id.title);
        tTime = (TextView) v.findViewById(R.id.time);
        img = (ImageView) v.findViewById(R.id.detailsimg);
        jc = (TextView) v.findViewById(R.id.jc);
        sales = (TextView) v.findViewById(R.id.sales);

        footb = (TextView) v.findViewById(R.id.footb);
        b1 = (TextView) v.findViewById(R.id.b11);
        b2 = (TextView) v.findViewById(R.id.b21);
        b3 = (TextView) v.findViewById(R.id.b31);
        b4 = (TextView) v.findViewById(R.id.b41);
        b5 = (TextView) v.findViewById(R.id.b51);
        b6 = (TextView) v.findViewById(R.id.b61);
        b7 = (TextView) v.findViewById(R.id.b71);
        lm1 = (TextView) v.findViewById(R.id.line_m1);
        lm2 = (TextView) v.findViewById(R.id.line_m2);
        lm3 = (TextView) v.findViewById(R.id.line_m3);
        lm4 = (TextView) v.findViewById(R.id.line_m4);
        lm5 = (TextView) v.findViewById(R.id.line_m5);
        lm6 = (TextView) v.findViewById(R.id.line_m6);

        lr1 = (TextView) v.findViewById(R.id.line_r1);
        lr2 = (TextView) v.findViewById(R.id.line_r2);
        lr3 = (TextView) v.findViewById(R.id.line_r3);
        lr4 = (TextView) v.findViewById(R.id.line_r4);
        lr5 = (TextView) v.findViewById(R.id.line_r5);
        lr6 = (TextView) v.findViewById(R.id.line_r6);

        tTitle.setText(title);
        tTime.setText("第 " + lotteryDetails.getPeriodName() + " 期");
        jc.setText(lotteryDetails.getTotalPool());
        sales.setText(lotteryDetails.getTotalSale());

        initData();

        adapter = new LDetaisAdapter(R.layout.ldetails_item, datas);
        adapter.addHeaderView(v);
        adapter.setGameEn(gameEn);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        initNet();
    }

    private void initData() {
        Random random = new Random();
        if ("双色球".equals(title)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSQ, "period");
            datas.clear();
            datas.addAll(data);
            balls = lotteryDetails.getAwardNo().replace(":", " ").split(" ");
            gameEn = "ssq";
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
        } else if ("七乐彩".equals(title)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QLC, "period");
            datas.clear();
            datas.addAll(data);
            balls = lotteryDetails.getAwardNo().replace(":", " ").split(" ");
            gameEn = "qlc";
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
        } else if ("大乐透".equals(title)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_DLT, "period");
            datas.clear();
            datas.addAll(data);
            balls = lotteryDetails.getAwardNo().replace(":", " ").split(" ");
            gameEn = "dlt";
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
        } else if ("七星彩".equals(title)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QXC, "period");
            datas.clear();
            datas.addAll(data);
            balls = lotteryDetails.getAwardNo().split(" ");
            gameEn = "qxc";
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
        } else if ("排列三".equals(title)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL3, "period");
            datas.clear();
            datas.addAll(data);
            balls = lotteryDetails.getAwardNo().split(" ");
            gameEn = "pl3";
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
        } else if ("排列五".equals(title)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL5, "period");
            datas.clear();
            datas.addAll(data);
            balls = lotteryDetails.getAwardNo().split(" ");
            gameEn = "pl5";
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
        } else if (title.contains("快三")) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_K3, "period");
            datas.clear();
            datas.addAll(data);
            balls = lotteryDetails.getAwardNo().split(" ");
            gameEn = "kuai3";
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
        } else if (title.contains("11选5")) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_D11, "period");
            datas.clear();
            datas.addAll(data);
            balls = lotteryDetails.getAwardNo().split(" ");
            gameEn = "jxd11";
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
        } else if (title.contains("任选九") || title.contains("胜负彩")) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_RX9, "period");
            datas.clear();
            datas.addAll(data);
            gameEn = "football_9";
            if (title.contains("任选九")) {
                img.setImageResource(R.drawable.lottery_icon_rx9);
            } else {
                img.setImageResource(R.drawable.lottery_icon_sfc);
            }
            footb.setVisibility(View.VISIBLE);
            footb.setText(lotteryDetails.getAwardNo());
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
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
            datas.clear();
            datas.addAll(data);
            balls = lotteryDetails.getAwardNo().split(" ");
            gameEn = "ssc";
            img.setImageResource(R.drawable.lottery_icon_ssc);
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
    }

    private void initNet() {
        HashMap params = new HashMap<String, String>();
        params.put("count", "30");
        params.put("period", lotteryDetails.getPeriodName());
        params.put("gameEn", lotteryDetails.getGameEn());
        request = new VolleyUtils(1000, this).getStringRequest(params, Utils.GET_LOTTERY_HISTORY_DATA);
        if (request != null) {
            request.setTag(LotteryAllDetails.class.getSimpleName());
            VolleyTool.getInstance(this).getmRequestQueue().add(request);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_rules:
                intent = new Intent(LotteryAllDetails.this, RulesActivity.class);
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
                } else if (title.contains("快三")) {
                    intent.putExtra("name", "快三");
                } else if (title.contains("11选5")) {
                    intent.putExtra("name", "11选5");
                } else if (title.contains("任选九")) {
                    intent.putExtra("name", "任选九");
                } else if (title.contains("胜负彩")) {
                    intent.putExtra("name", "胜负彩");
                } else {
                    intent.putExtra("name", "时时彩");
                }
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(String s, int tag) {
        if (isFinishing()) {
            return;
        }
        try {
            if (!TextUtils.isEmpty(s)) {
                data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, s, "period");
                if (data != null && data.size() > 0) {
                    datas.clear();
                    datas.addAll(data);
                    adapter.notifyDataSetChanged();
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError, int tag) {
        Log.e("---", "");
    }

    @Override
    public void onFailedResponse(String s) {
        Log.e("---", "");
    }

    @Override
    protected void onDestroy() {
        if (request != null) {
            VolleyTool.getInstance(this).release(LotteryAllDetails.class.getSimpleName());
        }

        if (datas != null) {
            datas.clear();
            datas = null;
        }
        if (data != null) {
            data.clear();
            data = null;
        }
        super.onDestroy();
    }
}
