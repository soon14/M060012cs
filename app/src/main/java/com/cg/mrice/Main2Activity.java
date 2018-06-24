package com.cg.mrice;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.cg.mrice.adapter.HistoryAdapter;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.fragment.HistoryActivity;
import com.cg.mrice.fragment.HistoryDetails;
import com.cg.mrice.fragment.YaoHaoDetails;
import com.cg.mrice.fragment.ZSActivity;
import com.cg.mrice.model.LotteryGamePeriodXml;
import com.cg.mrice.utils.ToastUtil;
import com.cg.mrice.utils.Utils;
import com.cg.mrice.utils.XMLHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class Main2Activity extends AppCompatActivity {

    private JZVideoPlayerStandard jzVideoPlayerStandard;
    int redNum = 33;
    int blueNum = 16;
    int lineNum = 8;
    int jxNum = 1;
    @BindView(R.id.group_red)
    LinearLayout group_red;
    @BindView(R.id.group_blue)
    LinearLayout group_blue;
    @BindView(R.id.layout_group_blue)
    LinearLayout layout_group_blue;
    @BindView(R.id.jxjz)
    TextView jxjz;

    private List<Integer> list_red = new ArrayList<>();
    private List<Integer> list_blue = new ArrayList<>();
    private int max_red = 6;
    private int max_blue = 1;
    @BindView(R.id.y1)
    TextView y1;
    @BindView(R.id.y2)
    TextView y2;
    @BindView(R.id.y3)
    TextView y3;
    @BindView(R.id.y4)
    TextView y4;
    @BindView(R.id.y5)
    TextView y5;
    @BindView(R.id.y6)
    TextView y6;
    @BindView(R.id.y7)
    TextView y7;
    @BindView(R.id.y8)
    TextView y8;
    @BindView(R.id.jx)
    TextView jx;
    @BindView(R.id.qr)
    TextView qr;
    @BindView(R.id.yyy)
    TextView yyy;
    TextView[] yr;
    TextView[] yb;

    String title = "";
    String gameEn = "ssc";
    boolean showBlueGroup = false;
    private ArrayList<String> randatas = new ArrayList<>();
    private PopupWindow popupWindow;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.layout3)
    LinearLayout layout3;


    @BindView(R.id.rv_history)
    RecyclerView rv_history;
    HistoryAdapter adapter;
    private List<LotteryGamePeriodXml> datas = new ArrayList<>();
    private List<LotteryGamePeriodXml> data;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications:
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initView();
        initVideo();
        initModule2();
        initLottery();
    }

    private void initModule2() {
        title = "双色球";
        yyy.setText(R.string.app_name);
        initPopup();
        if ("时时彩".equals(title)) {
            gameEn = "ssc";
            redNum = 9;
            max_red = 5;
            yr = new TextView[]{y1, y2, y3, y4, y5};
            y6.setVisibility(View.GONE);
            y7.setVisibility(View.GONE);
        } else if ("双色球".equals(title)) {
            gameEn = "ssq";
            max_red = 6;
            max_blue = 1;
            yr = new TextView[]{y1, y2, y3, y4, y5, y6};
            yb = new TextView[]{y7};
            y7.setBackgroundResource(R.drawable.ball_red_s);
            y7.setVisibility(View.VISIBLE);
            showBlueGroup = true;
        } else if ("大乐透".equals(title)) {
            gameEn = "dlt";
            max_red = 5;
            max_blue = 2;
            yr = new TextView[]{y1, y2, y3, y4, y5};
            yb = new TextView[]{y6, y7};
            y6.setBackgroundResource(R.drawable.ball_blue_img);
            y7.setBackgroundResource(R.drawable.ball_blue_img);
            showBlueGroup = true;
        } else if ("七乐彩".equals(title)) {
            gameEn = "qlc";
            max_red = 7;
            max_blue = 1;
            yr = new TextView[]{y1, y2, y3, y4, y5, y6, y7};
            yb = new TextView[]{y8};
            y8.setBackgroundResource(R.drawable.ball_blue_img);
            y8.setVisibility(View.VISIBLE);
            showBlueGroup = true;
        } else if ("七星彩".equals(title)) {
            gameEn = "qxc";
            redNum = 9;
            max_red = 7;
            yr = new TextView[]{y1, y2, y3, y4, y5, y6, y7};
        } else if ("排列三".equals(title) || "快三".equals(title)) {
            gameEn = "pl3";
            redNum = 9;
            max_red = 3;
            yr = new TextView[]{y1, y2, y3};
            y4.setVisibility(View.GONE);
            y5.setVisibility(View.GONE);
            y6.setVisibility(View.GONE);
            y7.setVisibility(View.GONE);
        } else if ("排列五".equals(title)) {
            gameEn = "pl5";
            redNum = 9;
            max_red = 5;
            yr = new TextView[]{y1, y2, y3, y4, y5};
            y6.setVisibility(View.GONE);
            y7.setVisibility(View.GONE);
        } else if ("11选5".equals(title)) {
            gameEn = "d11";
            redNum = 9;
            max_red = 5;
            yr = new TextView[]{y1, y2, y3, y4, y5};
            y6.setVisibility(View.GONE);
            y7.setVisibility(View.GONE);
        } else {
            max_red = 6;
            max_blue = 1;
            yr = new TextView[]{y1, y2, y3, y4, y5, y6};
            yb = new TextView[]{y7};
            showBlueGroup = true;
        }

        int lines = (int) Math.ceil((redNum / lineNum));

        Log.e("--", lines + "");
        int b = 1;
        LinearLayout vGroup;
        for (int i = 0; i < lines; i++) {
            vGroup = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = 20;
            vGroup.setLayoutParams(layoutParams);
            vGroup.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout child;
            for (int j = 0; j < lineNum; j++) {
                child = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.yaohao_item, null);
                if (b > redNum) {
                    child.setVisibility(View.INVISIBLE);
                }
                final CheckBox cb = (CheckBox) child.findViewById(R.id.cb);
                cb.setTextColor(getResources().getColor(R.color.holo_red_light));
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (list_red.size() == max_red) {
                                cb.setChecked(false);
                                ToastUtil.showToast("最多 " + max_red + " 个号码");
                                return;
                            }
                            cb.setTextColor(Color.WHITE);
                            list_red.add(Integer.parseInt(cb.getText().toString()));
                        } else {
                            list_red.remove((Integer) Integer.parseInt(cb.getText().toString()));
                            cb.setTextColor(getResources().getColor(R.color.holo_red_light));
                        }
                        Collections.sort(list_red);
                        showResult();
                    }
                });
                cb.setText("" + b);
                cb.setTag("" + b);
                b++;
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.weight = 1;
                child.setLayoutParams(lp);
                vGroup.addView(child);
            }
            group_red.addView(vGroup);
        }

        if (showBlueGroup) {
            int b2 = 1;
            int line = (int) Math.ceil((blueNum / lineNum));
            Log.e("--", line + "");
            for (int i = 0; i < line; i++) {
                vGroup = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin = 20;
                vGroup.setLayoutParams(layoutParams);
                vGroup.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout child;
                for (int j = 0; j < lineNum; j++) {
                    child = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.yaohao_item, null);
                    if (b2 > blueNum) {
                        child.setVisibility(View.INVISIBLE);
                    }
                    final CheckBox cb = (CheckBox) child.findViewById(R.id.cb);
                    cb.setTextColor(getResources().getColor(R.color.main_blue));
                    cb.setBackgroundResource(R.drawable.ball_select);
                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                if (list_blue.size() == max_blue) {
                                    cb.setChecked(false);
                                    ToastUtil.showToast("最多 " + max_blue + " 个号码");
                                    return;
                                }
                                cb.setTextColor(Color.WHITE);
                                list_blue.add(Integer.parseInt(cb.getText().toString()));
                            } else {
                                cb.setTextColor(getResources().getColor(R.color.main_blue));
                                list_blue.remove((Integer) Integer.parseInt(cb.getText().toString()));
                            }
                            Collections.sort(list_blue);
                            showResult();
                        }
                    });
                    cb.setText("" + b2);
                    cb.setTag("" + b2);
                    b2++;
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.weight = 1;
                    child.setLayoutParams(lp);
                    vGroup.addView(child);
                }
                group_blue.addView(vGroup);
            }
        } else {
            layout_group_blue.setVisibility(View.GONE);
        }
    }

    private void showResult() {
        for (TextView t : yr) {
            t.setText("");
        }
        if (showBlueGroup) {
            for (TextView t : yb) {
                t.setText("");
            }
        }
        if (list_red.size() > 0) {
            for (int i = 0; i < list_red.size(); i++) {
                yr[i].setText(list_red.get(i) + "");
            }
        }
        if (showBlueGroup) {
            if (list_blue.size() > 0) {
                for (int i = 0; i < list_blue.size(); i++) {
                    yb[i].setText(list_blue.get(i) + "");
                }
            }
        }
    }

    private void randomCode(int z) {
        randatas.clear();
        StringBuffer sb;
        int[] codes;
        int[] code_b;
        for (int i = 0; i < z; i++) {
            sb = new StringBuffer("");
            codes = Utils.randomArray(1, redNum, max_red);
            for (int r = 0; r < codes.length; r++) {
                sb.append(codes[r] + ",");
            }
            if (showBlueGroup) {
                code_b = Utils.randomArray(1, blueNum, max_blue);
                for (int b = 0; b < code_b.length; b++) {
                    sb.append(code_b[b] + ",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            randatas.add(sb.toString());
        }
        Intent i = new Intent(Main2Activity.this, YaoHaoDetails.class);
        i.putExtra("gameEn", gameEn);
        i.putStringArrayListExtra("data", randatas);
        startActivity(i);
    }

    @OnClick({R.id.ssc, R.id.ssq, R.id.dlt, R.id.pl3, R.id.pl5, R.id.qxc, R.id.qlc, R.id.jx, R.id.qr, R.id.jxjz})
    public void onClick(View v) {
        Intent i = new Intent(Main2Activity.this, ZSActivity.class);
        switch (v.getId()) {
            case R.id.ssc:
                i.putExtra("type", 0);
                startActivity(i);
                break;
            case R.id.ssq:
                i.putExtra("type", 5);
                startActivity(i);
                break;
            case R.id.dlt:
                i.putExtra("type", 6);
                startActivity(i);
                break;
            case R.id.pl3:
                i.putExtra("type", 3);
                startActivity(i);
                break;
            case R.id.pl5:
                i.putExtra("type", 4);
                startActivity(i);
                break;
            case R.id.qxc:
                i.putExtra("type", 2);
                startActivity(i);
                break;
            case R.id.qlc:
                i.putExtra("type", 1);
                startActivity(i);
                break;
            case R.id.jx:
                randomCode(jxNum);
                break;
            case R.id.qr:
                if (list_red.size() != max_red) {
                    ToastUtil.showToast("请先选择号码，也可点击 机选 按钮随机选号");
                    return;
                }

                if (showBlueGroup) {
                    if (list_blue.size() != max_blue) {
                        ToastUtil.showToast("请先选择号码，也可点击 机选 按钮随机选号");
                        return;
                    }
                }

                StringBuffer sb = new StringBuffer("");
                if (list_red.size() > 0) {
                    for (int j = 0; j < list_red.size(); j++) {
                        sb.append(list_red.get(j) + ",");
                    }
                }
                if (showBlueGroup) {
                    if (list_blue.size() > 0) {
                        for (int j = 0; j < list_blue.size(); j++) {
                            sb.append(list_blue.get(j) + ",");
                        }
                    }
                }
                sb.deleteCharAt(sb.length() - 1);
                randatas.clear();
                randatas.add(sb.toString());
                i = new Intent(Main2Activity.this, YaoHaoDetails.class);
                i.putExtra("gameEn", gameEn);
                i.putStringArrayListExtra("data", randatas);
                startActivity(i);
                break;
            case R.id.jxjz:
                popupWindow.showAsDropDown(v, 0, 0);
                break;
        }
    }

    private void initView() {
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
    }

    private void initVideo() {
        jzVideoPlayerStandard.setUp("http://flv2.bn.netease.com/videolib3/1804/21/snODP7519/SD/snODP7519-mobile.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        Glide.with(getApplicationContext()).load(R.drawable.kjbg).into(jzVideoPlayerStandard.thumbImageView);

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

    private void initPopup() {
        View v = LayoutInflater.from(this).inflate(R.layout.popup_jxjz, null);
        v.findViewById(R.id.jx1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jxNum = 1;
                jxjz.setText("机选一注");
                popupWindow.dismiss();
            }
        });
        v.findViewById(R.id.jx5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jxNum = 5;
                jxjz.setText("机选五注");
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(300, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(v);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
    }

    private void initLottery() {
        datas.clear();
        data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
        if (data != null && data.size() > 0) {
            datas.addAll(data);
        }
        adapter = new HistoryAdapter(R.layout.lottery_item, datas);
        adapter.setGameEn("ssc");
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_history.setLayoutManager(layoutManager);
        rv_history.setAdapter(adapter);
        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(Main2Activity.this, HistoryDetails.class);
                i.putExtra("gameEn", "ssc");
                i.putExtra("data", data.get(position));
                startActivity(i);
            }
        });
    }


    @Override
    protected void onDestroy() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        super.onDestroy();
    }

}
