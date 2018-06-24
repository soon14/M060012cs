package com.cg.mrice.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.cg.mrice.CNewsActivity;
import com.cg.mrice.LotteryDetails;
import com.cg.mrice.R;
import com.cg.mrice.adapter.SportAdatper;
import com.cg.mrice.data.NewsData;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.NewsInfo;
import com.cg.mrice.model.SportData;
import com.cg.mrice.utils.Utils;
import com.cg.mrice.view.RadarMarkerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Mr on 2018/4/14.
 */

public class ContentActivity extends AppCompatActivity implements VolleyCallBack, View.OnClickListener {

    Banner banner;
    protected int res;
    public List<NewsInfo> data = new ArrayList<>();
    private List<SportData> datas = new ArrayList<>();
    private SportAdatper adapter;
    private RecyclerView recyclerView;
    LinearLayout dt1;
    LinearLayout dt2;
    LinearLayout dt3;

    TextView dt_title1;
    TextView dt_title2;
    TextView dt_title3;
    TextView dt_time1;
    TextView dt_time2;
    TextView dt_time3;

    private JZVideoPlayerStandard jzVideoPlayerStandard;
    private StringRequest stringRequest;
    private RadarChart mChart;
    private Dialog dialog;
    private TextView d_ss;
    private TextView d_lc;
    private TextView d_pls;
    private TextView d_plp;
    private TextView d_plf;
    private TextView d_state;
    private LinearLayout ss1;
    private LinearLayout ss2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initDialog();
        initView();
        initVideo();
        initNet();
    }

    private void initDialog() {
        dialog = new Dialog(this);
        View v = LayoutInflater.from(this).inflate(R.layout.pl_dialog, null);
        d_ss = (TextView) v.findViewById(R.id.d_ss);
        d_lc = (TextView) v.findViewById(R.id.d_lc);
        d_pls = (TextView) v.findViewById(R.id.d_pls);
        d_plp = (TextView) v.findViewById(R.id.d_plp);
        d_plf = (TextView) v.findViewById(R.id.d_plf);
        d_state = (TextView) v.findViewById(R.id.d_state);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.9); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    private void initVideo() {
        jzVideoPlayerStandard.setUp("http://www.cwl.gov.cn/upload/resources/video/2018/03/23/36985.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "福彩演播室");
        Glide.with(getApplicationContext()).load(R.drawable.video_bg).into(jzVideoPlayerStandard.thumbImageView);

    }

    private void initView() {
        View v = LayoutInflater.from(this).inflate(R.layout.activity_content_top, null);
        ss1 = (LinearLayout) v.findViewById(R.id.ss1);
        ss2 = (LinearLayout) v.findViewById(R.id.ss2);
        ss1.setOnClickListener(this);
        ss2.setOnClickListener(this);
        initChart(v);
        banner = (Banner) v.findViewById(R.id.banner);
        List<Integer> urls = new ArrayList<Integer>();
        urls.add(R.drawable.banner1);
        urls.add(R.drawable.banner2);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(urls);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(ContentActivity.this, CNewsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        View vb = LayoutInflater.from(this).inflate(R.layout.activity_content_bottom, null);
        jzVideoPlayerStandard = (JZVideoPlayerStandard) vb.findViewById(R.id.videoplayer);
        dt1 = (LinearLayout) vb.findViewById(R.id.dt1);
        dt2 = (LinearLayout) vb.findViewById(R.id.dt2);
        dt3 = (LinearLayout) vb.findViewById(R.id.dt3);
        dt1.setOnClickListener(this);
        dt2.setOnClickListener(this);
        dt3.setOnClickListener(this);

        dt_title1 = (TextView) vb.findViewById(R.id.dt_title1);
        dt_time1 = (TextView) vb.findViewById(R.id.dt_time1);
        dt_title2 = (TextView) vb.findViewById(R.id.dt_title2);
        dt_time2 = (TextView) vb.findViewById(R.id.dt_time2);
        dt_title3 = (TextView) vb.findViewById(R.id.dt_title3);
        dt_time3 = (TextView) vb.findViewById(R.id.dt_time3);

        data.addAll(NewsData.getTitleNews());
        dt_title1.setText(data.get(2).getTitle());
        dt_time1.setText(data.get(2).getTime());
        dt_title2.setText(data.get(3).getTitle());
        dt_time2.setText(data.get(3).getTime());
        dt_title3.setText(data.get(4).getTitle());
        dt_time3.setText(data.get(4).getTime());

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        adapter = new SportAdatper(R.layout.item_sport_item, datas, this);
        adapter.addHeaderView(v);
        adapter.addFooterView(vb);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                d_ss.setText(datas.get(position).getName());
                d_lc.setText(datas.get(position).getLc());
                d_pls.setText(datas.get(position).getPl_s());
                d_plp.setText(datas.get(position).getPl_p());
                d_plf.setText(datas.get(position).getPl_f());
                d_state.setText(datas.get(position).getState());
                dialog.show();
            }
        });

    }

    private void initChart(View v) {
        mChart = (RadarChart) v.findViewById(R.id.chart1);
        mChart.setBackgroundColor(Color.rgb(60, 65, 82));
        Description description = new Description();
        description.setText("赛事胜负比例分析");
        description.setTextColor(Color.WHITE);
        mChart.setDescription(description);
        mChart.getDescription().setEnabled(true);
        mChart.setWebLineWidth(1f);
        mChart.setWebColor(Color.LTGRAY);
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(Color.LTGRAY);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
        setData();
        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"德甲", "德乙", "西甲", "法甲", "英超"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);
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

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.dt1:
                intent = new Intent(this, CNewsActivity.class);
                intent.putExtra("position", 2);
                startActivity(intent);
                break;
            case R.id.dt2:
                intent = new Intent(this, CNewsActivity.class);
                intent.putExtra("position", 3);
                startActivity(intent);
                break;
            case R.id.dt3:
                intent = new Intent(this, CNewsActivity.class);
                intent.putExtra("position", 4);
                startActivity(intent);
                break;
            case R.id.ss1:
                d_ss.setText("德乙");
                d_lc.setText("第35轮");
                d_pls.setText("2.28");
                d_plp.setText("3.52");
                d_plf.setText("2.96");
                d_state.setText("未开始");
                dialog.show();
                break;
            case R.id.ss2:
                d_ss.setText("德甲");
                d_lc.setText("第36轮");
                d_pls.setText("2.51");
                d_plp.setText("3.11");
                d_plf.setText("2.91");
                d_state.setText("未开始");
                dialog.show();
                break;
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(path).into(imageView);
        }
    }

    private void initNet() {
        stringRequest = new VolleyUtils(1000, this).getStringRequest(Utils.GET_SPORT_DATA);
        if (stringRequest != null) {
            stringRequest.setTag(ContentActivity.class.getSimpleName());
            VolleyTool.getInstance(this).getmRequestQueue().add(stringRequest);
        }
    }

    @Override
    public void onResponse(String s, int tag) {
        if (isFinishing()) {
            return;
        }
        try {
            if (!TextUtils.isEmpty(s)) {
                List<SportData> list = getData(s);
                if (list != null && list.size() > 0) {
                    datas.clear();
                    datas.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    getSportError();
                }
            } else {
                getSportError();
            }
        } catch (Exception ex) {
            getSportError();
        }

    }

    public List<SportData> getData(String html) throws Exception {
        //获取的数据，存放在集合中
        List<SportData> data = new ArrayList<SportData>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements = doc.select("dl[id=gameList]").select("dd");
        for (Element ele : elements) {
            String cc = ele.select("span[class=period]").text();
            String name = ele.select("span[class=events]").select("a").text();
            String time = ele.select("span[class=playTime]").text();
            String lc = ele.select("span[class=round]").text();
            String state = ele.select("span[class=type]").text();
            String zd = ele.select("span[class=host]").text();
            String zdimg = ele.select("span[class=host]").select("img").attr("src");
            String bf = ele.attr("score");
            String kd = ele.select("span[class=guest]").text();
            String kdimg = ele.select("span[class=guest]").select("img").attr("src");
            String result = ele.attr("bidscore");
            Elements pl = ele.select("span[class=odds]").select("em");
            String pl_s = pl.get(0).text();
            String pl_p = pl.get(1).text();
            String pl_f = pl.get(2).text();
            SportData newsInfo = new SportData();
            newsInfo.setCc(cc);
            newsInfo.setName(name);
            newsInfo.setTime(time);
            newsInfo.setLc(lc);
            newsInfo.setState(state);
            newsInfo.setZd(zd);
            newsInfo.setZdimg(zdimg);
            newsInfo.setKd(kd);
            newsInfo.setKdimg(kdimg);
            newsInfo.setBf(bf);
            newsInfo.setResult(result);
            newsInfo.setPl_s(pl_s);
            newsInfo.setPl_p(pl_p);
            newsInfo.setPl_f(pl_f);
            data.add(newsInfo);
        }
        //返回数据
        return data;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError, int tag) {
        getSportError();
    }

    @Override
    public void onFailedResponse(String s) {
        getSportError();
    }

    private void getSportError() {
        try {
            String str = com.cg.mrice.data.SportData.SPORT_DATA;
            List<SportData> ll = getData(str);
            if (ll != null && ll.size() > 0) {
                datas.clear();
                datas.addAll(ll);
            }
        } catch (Exception ex) {

        }
    }

    public void setData() {

        int cnt = 5;
        float[] arrs1 = new float[]{42.502266f, 41.5211f, 32.821526f, 54.565994f, 70.90033f};
        float[] arrs2 = new float[]{86.724915f, 57.27724f, 54.396088f, 26.545204f, 64.713f};
        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            entries1.add(new RadarEntry(arrs1[i]));
            entries2.add(new RadarEntry(arrs2[i]));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "上周");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "本周");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (stringRequest != null) {
            VolleyTool.getInstance(this).release(ContentActivity.class.getSimpleName());
        }
        if (data != null) {
            data.clear();
            data = null;
        }
        if (datas != null) {
            datas.clear();
            datas = null;
        }
    }
}
