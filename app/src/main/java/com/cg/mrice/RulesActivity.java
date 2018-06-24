package com.cg.mrice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.cg.mrice.data.RuleData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by app on 2018/4/11.
 */
public class RulesActivity extends AppCompatActivity {

    @BindView(R.id.content)
    TextView content;
    private ProgressDialog progressDialog;
    private String name = "时时彩";
    private String str = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载,请稍后...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        name = getIntent().getStringExtra("name");
        if ("时时彩".equals(name)) {
            str = RuleData.RULE_SSC;
        } else if ("双色球".equals(name)) {
            str = RuleData.RULE_SSQ;
        } else if ("大乐透".equals(name)) {
            str = RuleData.RULE_DLT;
        } else if ("七乐彩".equals(name)) {
            str = RuleData.RULE_QLC;
        } else if ("七星彩".equals(name)) {
            str = RuleData.RULE_QXC;
        } else if ("排列三".equals(name)) {
            str = RuleData.RULE_PL3;
        } else if ("排列五".equals(name)) {
            str = RuleData.RULE_PL5;
        } else if ("快三".equals(name)) {
            str = RuleData.RULE_K3;
        } else if ("快乐扑克".equals(name)) {
            str = RuleData.RULE_KLPK;
        } else if ("11选5".equals(name)) {
            str = RuleData.RULE_D11;
        } else if ("任选九".equals(name)) {
            str = RuleData.RULE_RX9;
        } else if ("胜负彩".equals(name)) {
            str = RuleData.RULES_SFC;
        }
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name + "玩法规则");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                content.setText(Html.fromHtml(str));
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }
}
