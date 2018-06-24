package com.cg.mrice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.fragment.*;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.LotteryBean;
import com.cg.mrice.utils.ToastUtil;
import com.cg.mrice.utils.Utils;
import com.cg.mrice.view.CircleMenuLayout;
import com.cg.mrice.view.ClipView;
import com.cg.mrice.view.FlowTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr on 2018/4/14.
 */

public class PanActivity extends AppCompatActivity implements VolleyCallBack {

    private long lastClickTime = 0;
    private CircleMenuLayout mCircleMenuLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    int[] imgIds = new int[]{R.drawable.m1,
            R.drawable.m2, R.drawable.m3,
            R.drawable.m4,
            R.drawable.m5};
    int[] imgIds2 = new int[]{R.drawable.info_football_lib,
            R.drawable.info_lottery_news, R.drawable.info_jc_furture,
            R.drawable.info_open_notice, R.drawable.info_play_skill};
    String[] strIds = new String[]{"走势分布", "模拟选号", "首 页", "最新开奖", "彩市资讯"};

    private String[] mItemTexts = new String[]{
            " 时时彩", " 双色球", " 大乐透", " 快三", " 任选九", " 胜负彩"};
    private int[] mItemImgs = new int[]{R.drawable.lottery_icon_gold_ssc,
            R.drawable.lottery_icon_gold_ssq, R.drawable.lottery_icon_gold_lotto,
            R.drawable.lottery_icon_gold_new_k3, R.drawable.lottery_icon_rx9,
            R.drawable.lottery_icon_sfc};
    public static List<LotteryBean.LotteryDetails> datas = new ArrayList<>();
    private StringRequest request = null;

    ViewPager vp_pagers;

    HashMap<Integer, ClipView> imageViewList = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        vp_pagers = (ViewPager) findViewById(R.id.vp_pagers);
        vp_pagers.setAdapter(pagerAdapter);
        vp_pagers.setPageTransformer(true, new FlowTransformer(vp_pagers));
        // 设置ViewPager的缓存页数，因为demo没有做缓存，所以为了方便就这么搞了，页数不多的时候可以把这里设置为总页数
        vp_pagers.setOffscreenPageLimit(imgIds.length);
        vp_pagers.setCurrentItem(2);


        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Intent i = new Intent(PanActivity.this, HistoryActivity.class);
                switch (pos) {
                    case 0:
                        i.putExtra("gameEn", "ssc");
                        break;
                    case 1:
                        i.putExtra("gameEn", "ssq");
                        break;
                    case 2:
                        i.putExtra("gameEn", "dlt");
                        break;
                    case 3:
                        i.putExtra("gameEn", "kuai3");
                        break;
                    case 4:
                        i.putExtra("gameEn", "football_9");
                        break;
                    case 5:
                        i.putExtra("gameEn", "football_sfc");
                        break;
                }
                startActivity(i);
            }

            @Override
            public void itemCenterClick(View view) {

            }
        });

        initNet();
    }

    private void initNet() {
        HashMap params = new HashMap<String, String>();
        params.put("mobileType", "android");
        request = new VolleyUtils(1000, this).getStringRequest(params, Utils.GET_LOTTERY_DATA);
        if (request != null) {
            request.setTag(PanActivity.class.getSimpleName());
            VolleyTool.getInstance(this).getmRequestQueue().add(request);
        }
    }

    @Override
    public void onResponse(String s, int tag) {
        if (isFinishing()) {
            return;
        }
        switch (tag) {
            case 1000:
                LotteryBean lotteryBean;
                try {
                    lotteryBean = CommonData.getGson().fromJson(s, LotteryBean.class);
                    if (lotteryBean.getData() != null && lotteryBean.getData().size() > 0) {
                        datas.addAll(lotteryBean.getData());
                    } else {
                        lotteryBean = CommonData.getGson().fromJson(CommonData.DATA_ALL, LotteryBean.class);
                        datas.addAll(lotteryBean.getData());
                    }
                } catch (Exception ex) {
                    lotteryBean = CommonData.getGson().fromJson(CommonData.DATA_ALL, LotteryBean.class);
                    datas.addAll(lotteryBean.getData());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError, int tag) {
        LotteryBean lotteryBean = CommonData.getGson().fromJson(CommonData.DATA_ALL, LotteryBean.class);
        datas.addAll(lotteryBean.getData());
    }

    @Override
    public void onFailedResponse(String s) {
        LotteryBean lotteryBean = CommonData.getGson().fromJson(CommonData.DATA_ALL, LotteryBean.class);
        datas.addAll(lotteryBean.getData());
    }

    @Override
    public void onBackPressed() {
        if (lastClickTime == 0) {
            lastClickTime = System.currentTimeMillis();
            ToastUtil.showToast(getString(R.string.login_out));
            return;
        }

        final long interval = System.currentTimeMillis() - lastClickTime;

        lastClickTime = System.currentTimeMillis();

        if (interval > 2000) {
            ToastUtil.showToast(getString(R.string.login_out));
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return imgIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ClipView clipView;
            if (imageViewList.containsKey(position)) {
                clipView = imageViewList.get(position);
            } else {
                View v = LayoutInflater.from(PanActivity.this).inflate(R.layout.layout_m, null);
                v.setBackgroundResource(imgIds[position]);
                ((ImageView) v.findViewById(R.id.mImg)).setImageResource(imgIds2[position]);
                ((TextView) v.findViewById(R.id.mTxt)).setText(strIds[position]);
                clipView = new ClipView(container.getContext());
                clipView.setId(position + 1);
                clipView.addView(v);
                imageViewList.put(position, clipView);
            }
            container.addView(clipView);
            clipView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = null;
                    switch (v.getId()) {
                        case 1:
                            i = new Intent(PanActivity.this, ZSActivity.class);
                            break;
                        case 2:
                            i = new Intent(PanActivity.this, XCodeActivity.class);
                            break;
                        case 3:
                            i = new Intent(PanActivity.this, ContentActivity.class);
                            break;
                        case 4:
                            i = new Intent(PanActivity.this, LotteryActivity.class);
                            break;
                        case 5:
                            i = new Intent(PanActivity.this, com.cg.mrice.fragment.NewsActivity.class);
                            break;
                    }
                    startActivity(i);
                }
            });
            return clipView;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_login:
                intent = new Intent(PanActivity.this, UserActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        if (request != null) {
            VolleyTool.getInstance(this).release(PanActivity.class.getSimpleName());
        }
        super.onDestroy();
    }
}
