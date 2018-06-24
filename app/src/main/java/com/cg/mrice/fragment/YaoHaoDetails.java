package com.cg.mrice.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cg.mrice.R;
import com.cg.mrice.adapter.YhAdapter;
import com.cg.mrice.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by app on 2018/4/20.
 */
public class YaoHaoDetails extends AppCompatActivity {

    private List<String> datas = new ArrayList<>();
    private List<String> data;
    private YhAdapter adapter;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.cxxh)
    TextView cxxh;
    @BindView(R.id.bchm)
    TextView bchm;
    private String gameEn = "ssq";
    @BindView(R.id.jbzs)
    TextView jbzs;
    @BindView(R.id.jzfx)
    TextView jzfx;
    @BindView(R.id.hzfx)
    TextView hzfx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaohao_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("选号详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gameEn = getIntent().getStringExtra("gameEn");
        data = getIntent().getStringArrayListExtra("data");
        if (data != null && data.size() > 0) {
            datas.addAll(data);
        }
        initView();
    }

    private void initView() {
        adapter = new YhAdapter(R.layout.yh_item, datas, gameEn);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.del:
                        AlertDialog.Builder builder = new AlertDialog.Builder(YaoHaoDetails.this, R.style.dialog_normal);
                        builder.setTitle("删除提示");
                        builder.setMessage("确定要删除这条数据吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                datas.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                        break;
                }
            }
        });
    }

    @OnClick({R.id.cxxh, R.id.bchm, R.id.jbzs, R.id.jzfx,R.id.hzfx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cxxh:
                finish();
                break;
            case R.id.bchm:
                if (datas.size() > 0) {
                    ToastUtil.showToast("保存成功");
                } else {
                    ToastUtil.showToast("请先返回选择号码");
                }
                break;
            case R.id.jbzs:
                Intent i = new Intent(YaoHaoDetails.this, YaoHaoChartActivity.class);
                i.putExtra("p", 1);
                startActivity(i);
                break;
            case R.id.jzfx:
                Intent i2 = new Intent(YaoHaoDetails.this, YaoHaoChartActivity.class);
                i2.putExtra("p", 2);
                startActivity(i2);
                break;
            case R.id.hzfx:
                Intent i3 = new Intent(YaoHaoDetails.this, YaoHaoChartActivity.class);
                i3.putExtra("p", 3);
                startActivity(i3);
                break;
        }
    }
}
