package com.cg.mrice.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cg.mrice.InvertedLineChartActivity;
import com.cg.mrice.PieChartActivity;
import com.cg.mrice.R;
import com.cg.mrice.adapter.ZSAdapter;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.model.LotteryGamePeriodXml;
import com.cg.mrice.utils.BuilderManager;
import com.cg.mrice.utils.ToastUtil;
import com.cg.mrice.utils.XMLHelper;
import com.cg.mrice.view.boommenu.BoomButtons.HamButton;
import com.cg.mrice.view.boommenu.BoomButtons.OnBMClickListener;
import com.cg.mrice.view.boommenu.BoomMenuButton;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr on 2018/4/14.
 */

public class ZSActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.rv_zs)
    RecyclerView rv_zs;
    private String[] titles = new String[]{"时时彩", "七乐彩", "七星彩", "排列三", "排列五", "11选5", "快三"};
    final String[] bln = new String[]{"双色球", "大乐透", "时时彩", "七乐彩", "七星彩", "排列三", "排列五", "11选5", "快三"};


    final float[] blz = new float[]{28.3f, 22.6f, 11.7f, 9.5f, 7.7f, 10.6f, 3.4f, 2.7f, 3.5f};
    private ZSAdapter adapter;
    private List<LotteryGamePeriodXml> datas = new ArrayList<>();
    private List<LotteryGamePeriodXml> data;
    @BindView(R.id.lineChart)
    TextView lineChart;
    @BindView(R.id.pieChart)
    TextView pieChart;
    String name = "时时彩";
    private PieChart mChart;
    int type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_zs);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("历史开奖");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
                break;
            case 1:
                data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QLC, "period");
                break;
            case 2:
                data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QXC, "period");
                break;
            case 3:
                data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL3, "period");
                break;
            case 4:
                data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL5, "period");
                break;
            case 5:
                data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_D11, "period");
                break;
            case 6:
                data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_K3, "period");
                break;
        }
        initView();
        initChart();
    }

    private void initView() {
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.holo_red_light));
        tabLayout.setTabTextColors(R.color.colorTheme, R.color.holo_red_light);
        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]), i);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                name = titles[tab.getPosition()];
                switch (tab.getPosition()) {
                    case 0:
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
                        break;
                    case 1:
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QLC, "period");
                        break;
                    case 2:
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QXC, "period");
                        break;
                    case 3:
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL3, "period");
                        break;
                    case 4:
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL5, "period");
                        break;
                    case 5:
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_D11, "period");
                        break;
                    case 6:
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_K3, "period");
                        break;
                }
                if (data != null && data.size() > 0) {
                    datas.clear();
                    datas.addAll(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
        if (data != null && data.size() > 0) {
            datas.clear();
            datas.addAll(data);
        }
        adapter = new ZSAdapter(R.layout.zs_item, datas);
        View v = LayoutInflater.from(this).inflate(R.layout.layout_zs_top, null);
        mChart = (PieChart) v.findViewById(R.id.chartTop);
        adapter.addHeaderView(v);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_zs.setLayoutManager(layoutManager);
        rv_zs.setAdapter(adapter);
    }

    @OnClick({R.id.pieChart, R.id.lineChart})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pieChart:
                Intent intent = new Intent(ZSActivity.this, PieChartActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("data", data.get(0));
                startActivity(intent);
                break;
            case R.id.lineChart:
                Intent intent2 = new Intent(ZSActivity.this, InvertedLineChartActivity.class);
                intent2.putExtra("name", name);
                startActivity(intent2);
                break;
        }
    }

    private void initChart() {
        mChart.setBackgroundColor(Color.rgb(60, 65, 82));
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(0, 5, 0, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        //mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText("本月各彩种热度分布");
        mChart.setCenterTextSize(12f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        //mChart.setExtraBottomOffset(10);

        setData(9, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(7f);
        l.setYOffset(10f);
        l.setEnabled(false);
        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        //mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(10f);
    }

    private void setData(int count, float range) {
        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry(blz[i],
                    bln[i],
                    getResources().getDrawable(R.drawable.ball_red_img)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "本月各彩种热度分布");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (datas != null) {
            datas.clear();
            datas = null;
        }
        if (data != null) {
            data.clear();
            data = null;
        }
    }
}
