package com.cg.mrice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cg.mrice.R;
import com.cg.mrice.RulesActivity;
import com.cg.mrice.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr on 2018/4/14.
 */

public class XCodeActivity extends AppCompatActivity {
    @BindView(R.id.layoutssc)
    LinearLayout layoutssc;
    @BindView(R.id.layoutssq)
    LinearLayout layoutssq;
    @BindView(R.id.layoutdlt)
    LinearLayout layoutdlt;
    @BindView(R.id.layoutqlc)
    LinearLayout layoutqlc;
    @BindView(R.id.layoutqxc)
    LinearLayout layoutqxc;
    @BindView(R.id.layoutpl3)
    LinearLayout layoutpl3;
    @BindView(R.id.layoutpl5)
    LinearLayout layoutpl5;
    @BindView(R.id.layoutk3)
    LinearLayout layoutk3;
    @BindView(R.id.layoutklpk)
    LinearLayout layoutklpk;
    @BindView(R.id.layoutd11)
    LinearLayout layoutd11;

    @BindView(R.id.h_ssc)
    TextView h_ssc;
    @BindView(R.id.h_ssq)
    TextView h_ssq;
    @BindView(R.id.h_dlt)
    TextView h_dlt;
    @BindView(R.id.h_qlc)
    TextView h_qlc;
    @BindView(R.id.h_qxc)
    TextView h_qxc;
    @BindView(R.id.h_pl3)
    TextView h_pl3;
    @BindView(R.id.h_pl5)
    TextView h_pl5;
    @BindView(R.id.h_k3)
    TextView h_k3;
    @BindView(R.id.h_klpk)
    TextView h_klpk;
    @BindView(R.id.h_11x5)
    TextView h_11x5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rule);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("模拟选号");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.h_ssc, R.id.h_ssq, R.id.h_dlt, R.id.h_qxc, R.id.h_qlc, R.id.h_pl3, R.id.h_pl5, R.id.h_k3, R.id.h_klpk, R.id.h_11x5, R.id.layoutssc, R.id.layoutssq, R.id.layoutdlt, R.id.layoutqxc, R.id.layoutqlc, R.id.layoutpl3, R.id.layoutpl5, R.id.layoutk3, R.id.layoutklpk, R.id.layoutd11})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.layoutssc:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "时时彩");
                startActivity(intent);
                break;
            case R.id.layoutssq:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "双色球");
                startActivity(intent);
                break;
            case R.id.layoutdlt:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "大乐透");
                startActivity(intent);
                break;
            case R.id.layoutpl3:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "排列三");
                startActivity(intent);
                break;
            case R.id.layoutpl5:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "排列五");
                startActivity(intent);
                break;
            case R.id.layoutqxc:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "七星彩");
                startActivity(intent);
                break;
            case R.id.layoutqlc:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "七乐彩");
                startActivity(intent);
                break;
            case R.id.layoutk3:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "快三");
                startActivity(intent);
                break;
            case R.id.layoutklpk:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "快乐扑克");
                startActivity(intent);
                break;
            case R.id.layoutd11:
                intent = new Intent(this, YaoHaoAcitivity.class);
                intent.putExtra("name", "11选5");
                startActivity(intent);
                break;
            case R.id.h_ssc:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("gameEn", "ssc");
                startActivity(intent);
                break;
            case R.id.h_ssq:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("gameEn", "ssq");
                startActivity(intent);
                break;
            case R.id.h_dlt:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("gameEn", "dlt");
                startActivity(intent);
                break;
            case R.id.h_qlc:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("gameEn", "qlc");
                startActivity(intent);
                break;
            case R.id.h_qxc:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("gameEn", "qxc");
                startActivity(intent);
                break;
            case R.id.h_pl3:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("gameEn", "pl3");
                startActivity(intent);
                break;
            case R.id.h_pl5:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("gameEn", "pl5");
                startActivity(intent);
                break;
            case R.id.h_k3:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("gameEn", "kuai3");
                startActivity(intent);
                break;
            case R.id.h_klpk:
                ToastUtil.showToast("实开型玩法，暂无历史信息");
                break;
            case R.id.h_11x5:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("gameEn", "d11");
                startActivity(intent);
                break;

        }
    }
}
