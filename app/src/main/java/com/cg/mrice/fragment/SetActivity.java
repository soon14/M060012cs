package com.cg.mrice.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.cg.mrice.AboutUsActivity;
import com.cg.mrice.R;
import com.cg.mrice.VersionActivity;
import com.cg.mrice.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr on 2018/4/14.
 */

public class SetActivity extends AppCompatActivity {
    @BindView(R.id.openMsg)
    Switch aSwitch;
    @BindView(R.id.clear)
    TextView clear;
    @BindView(R.id.bbgx)
    TextView bbgx;
    @BindView(R.id.gywm)
    TextView gywm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_me);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToastUtil.showToast("消息通知已打开");
                } else {
                    ToastUtil.showToast("消息通知已关闭");
                }
            }
        });
    }

    @OnClick({R.id.clear, R.id.bbgx, R.id.gywm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear:
                AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.dialog_normal);
                // 设置对话框的标题
                builder.setTitle("系统设置");
                builder.setMessage("确定要清除缓存吗？清除后本地缓存内容将会丢失！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showToast("缓存清除完成");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.bbgx:
                Intent ii = new Intent(this, VersionActivity.class);
                startActivity(ii);
                break;
            case R.id.gywm:
                Intent i2 = new Intent(this, AboutUsActivity.class);
                startActivity(i2);
                break;
        }
    }

}
