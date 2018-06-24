package com.cg.mrice.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.PanActivity;
import com.cg.mrice.R;
import com.cg.mrice.adapter.HistoryAdapter;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.LotteryBean;
import com.cg.mrice.model.LotteryGamePeriodXml;
import com.cg.mrice.utils.ToastUtil;
import com.cg.mrice.utils.Utils;
import com.cg.mrice.utils.XMLHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr on 2018/4/14.
 */

public class HistoryActivity extends AppCompatActivity implements VolleyCallBack {

    @BindView(R.id.gameSelect)
    Button gameSelect;
    @BindView(R.id.rv_history)
    RecyclerView rv_history;
    @BindView(R.id.sr)
    SwipeRefreshLayout sr;
    HistoryAdapter adapter;
    private List<LotteryGamePeriodXml> datas = new ArrayList<>();
    private String gameEn = "ssc";
    private AlertDialog.Builder builder;
    private String[] arrs = new String[]{"时时彩", "双色球", "大乐透", "七乐彩", "七星彩", "排列三", "排列五", "11选5", "任选九", "胜负彩", "快三"};
    private List<LotteryGamePeriodXml> data;
    private StringRequest stringRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("开奖历史");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gameEn = getIntent().getStringExtra("gameEn");
        initData();
        initView();
        //initNet();
        //initDialog();
    }

    private void initView() {
       /* datas.clear();
        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
        if (data != null && data.size() > 0) {
            datas.addAll(data);
        }*/
        adapter = new HistoryAdapter(R.layout.lottery_item, datas);
        adapter.setGameEn(gameEn);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_history.setLayoutManager(layoutManager);
        rv_history.setAdapter(adapter);
        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(HistoryActivity.this, HistoryDetails.class);
                i.putExtra("gameEn", gameEn);
                i.putExtra("data", data.get(position));
                startActivity(i);
            }
        });
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        sr.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        sr.setColorSchemeResources(R.color.colorAccent);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sr.setRefreshing(false);
                        ToastUtil.showToast("已经是最新数据");
                    }
                }, 1000);
            }
        });
    }

    private void initData() {
        if ("ssq".equals(gameEn)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSQ, "period");
        } else if ("qlc".equals(gameEn)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QLC, "period");
        } else if ("dlt".equals(gameEn)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_DLT, "period");
        } else if ("pl3".equals(gameEn)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL3, "period");
        } else if ("pl5".equals(gameEn)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL5, "period");
        } else if ("qxc".equals(gameEn)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QXC, "period");
        } else if ((gameEn.contains("kuai3"))) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_K3, "period");
        } else if (gameEn.contains("d11")) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_D11, "period");
        } else if (gameEn.contains("ssc")) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
        } else if (gameEn.contains("football")) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_RX9, "period");
        } else {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_K3, "period");
        }
        if (data != null && data.size() > 0) {
            datas.clear();
            datas.addAll(data);
        }
    }

    private void initNet() {
        if (PanActivity.datas != null && PanActivity.datas.size() > 0) {
            boolean flag = true;
            LotteryBean.LotteryDetails details = null;
            for (LotteryBean.LotteryDetails lotteryDetails : PanActivity.datas) {
                if (gameEn.equals(lotteryDetails.getGameEn())) {
                    details = lotteryDetails;
                    flag = false;
                }
            }
            if (flag) return;

            HashMap params = new HashMap<String, String>();
            params.put("count", "50");
            params.put("period", details.getPeriodName());
            params.put("gameEn", gameEn);
            stringRequest = new VolleyUtils(1000, this).getStringRequest(params, Utils.GET_LOTTERY_HISTORY_DATA);
            if (stringRequest != null) {
                stringRequest.setTag(HistoryActivity.class.getSimpleName());
                VolleyTool.getInstance(this).getmRequestQueue().add(stringRequest);
            }
        }
    }

    private void initDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setItems(arrs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        gameEn = "ssc";
                        gameSelect.setText("当前选择：时时彩");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                    case 1:
                        gameEn = "ssq";
                        gameSelect.setText("当前选择：双色球");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSQ, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                    case 2:
                        gameEn = "dlt";
                        gameSelect.setText("当前选择：超级大乐透");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_DLT, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                    case 3:
                        gameEn = "qlc";
                        gameSelect.setText("当前选择：七乐彩");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QLC, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                    case 4:
                        gameEn = "qxc";
                        gameSelect.setText("当前选择：七星彩");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QXC, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                    case 5:
                        gameEn = "pl3";
                        gameSelect.setText("当前选择：排列三");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL3, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                    case 6:
                        gameEn = "pl5";
                        gameSelect.setText("当前选择：排列五");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL5, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                    case 7:
                        gameEn = "d11";
                        gameSelect.setText("当前选择：11选5");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_D11, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                    case 8:
                        gameEn = "football_9";
                        gameSelect.setText("当前选择：任选九（足球）");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_RX9, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                    case 9:
                        gameEn = "football_sfc";
                        gameSelect.setText("当前选择：胜负彩（足球）");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SFC, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                    case 10:
                        gameEn = "kuai3";
                        gameSelect.setText("当前选择：快三");
                        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_K3, "period");
                        if (data != null && data.size() > 0) {
                            datas.clear();
                            datas.addAll(data);
                        }
                        break;
                }
                adapter.setGameEn(gameEn);
                adapter.notifyDataSetChanged();
            }
        });
        builder.create();
    }

    @OnClick({R.id.gameSelect})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gameSelect:
                builder.show();
                break;
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

    }

    @Override
    public void onFailedResponse(String s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (stringRequest != null) {
            VolleyTool.getInstance(this).release(HistoryActivity.class.getSimpleName());
        }
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
