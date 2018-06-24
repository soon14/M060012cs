package com.cg.mrice.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.cg.mrice.NewsDetails;
import com.cg.mrice.R;
import com.cg.mrice.adapter.NewsAdapter;
import com.cg.mrice.data.NewsData;
import com.cg.mrice.model.NewsInfo;
import com.cg.mrice.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr on 2018/4/14.
 */

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.rv_news)
    RecyclerView rv_news;
    NewsAdapter adapter;
    public List<NewsInfo> datas = new ArrayList<>();
    @BindView(R.id.refresh_news)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("彩市资讯");
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
        datas.clear();
        List<NewsInfo> data = NewsData.getNews();
        if (data != null && data.size() > 0) {
            datas.addAll(data);
        }
        adapter = new NewsAdapter(R.layout.layout_news, datas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_news.setLayoutManager(layoutManager);
        rv_news.setAdapter(adapter);
        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NewsActivity.this, NewsDetails.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        rv_news.addItemDecoration(decoration);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rv_news.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter.getData().size() < 20) {
                            adapter.addData(NewsData.getNews2());
                            adapter.loadMoreComplete();
                        } else {
                            ToastUtil.showToast("已经是全部新闻了，稍后再试试吧");
                            adapter.loadMoreEnd();
                        }
                    }
                }, 2000);
            }
        }, rv_news);
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        ToastUtil.showToast("刷新成功");
                    }
                }, 1000);
            }
        });
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (datas != null) {
            datas.clear();
            datas = null;
        }
    }
}
