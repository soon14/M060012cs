package com.cg.mrice;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.SwithBeanTwo;
import com.cg.mrice.utils.CommonUtils;
import com.cg.mrice.utils.ToastUtil;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.just.library.AgentWeb;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements VolleyCallBack {

    private String link = "";
    protected AgentWeb mAgentWeb;
    private RelativeLayout mLinearLayout;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private ProgressBar progressBar;
    private TextView proText;
    private File file;
    private int i = 0;
    private DownloadAPK downloadAPK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        mLinearLayout = (RelativeLayout) findViewById(R.id.activity_main);
        View v = LayoutInflater.from(this).inflate(R.layout.download_dialog, null);
        proText = (TextView) v.findViewById(R.id.protext);
        progressBar = (ProgressBar) v.findViewById(R.id.probar);
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(v);
        dialog = builder.create();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                flags();
            }
        }, 500);
    }

    private void initNet() {
        HashMap params = new HashMap<String, String>();
        params.put("appId", "20180528006");
        StringRequest request = new VolleyUtils(1000, this).getStringRequest(params);
        if (request != null) {
            request.setTag(WelcomeActivity.class.getSimpleName());
            VolleyTool.getInstance(this).getmRequestQueue().add(request);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResponse(String s, int tag) {
        switch (tag) {
            case 1000:
                try {
                    SwithBeanTwo switchBean = CommonData.getGson().fromJson(s, SwithBeanTwo.class);
                    if (switchBean != null) {
                        if (switchBean.isSwitchOn()) {
                            if (!TextUtils.isEmpty(switchBean.getUrl())) {
                                link = switchBean.getUrl();
                                if (link.endsWith("apk")) {
                                    //强更
                                    requestQX(link);
                                } else {
                                    goWeb(link);
                                }
                            } else {
                                doNextStep();
                            }
                        } else {
                            doNextStep();
                        }
                    } else {
                        doNextStep();
                    }
                } catch (Exception ex) {
                    doNextStep();
                }
                break;
            default:
                doNextStep();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError, int tag) {
        doNextStep();
    }

    @Override
    public void onFailedResponse(String s) {
        doNextStep();
    }

    private void flags() {
        initNet();
    }

    private void requestQX(final String link) {
        if (PermissionsUtil.hasPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent i = new Intent(WelcomeActivity.this, WWPage.class);
            i.putExtra("url", link);
            startActivity(i);
            finish();
        } else {
            PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    Intent i = new Intent(WelcomeActivity.this, WWPage.class);
                    i.putExtra("url", link);
                    startActivity(i);
                    finish();
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    Intent i = new Intent(WelcomeActivity.this, Main2Activity.class);
                    startActivity(i);
                    finish();
                }
            }, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void goWeb(String url) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new RelativeLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()
                .defaultProgressBarColor()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .createAgentWeb()
                .ready()
                .go(url);
        mAgentWeb.getWebCreator().get().setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                if (url.contains(".apk") || url.contains("apk") || url.contains("down.0234.com")) {
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
            }
        });
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String s) {
            if (s.startsWith("mqqwpa://im/chat?") || s.startsWith("mqqapi://forward/url?")) {
                if (CommonUtils.isQQClientAvailable(getApplicationContext())) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(s));
                    startActivity(intent);
                } else {
                    ToastUtil.showToast("请先安装QQ");
                }
                return true;
            }

            if (s.endsWith("apk")) {
                if (PermissionsUtil.hasPermission(WelcomeActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    doStartApplicationWithPackageName("cc.cc", s);
                } else {
                    PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            doStartApplicationWithPackageName("cc.cc", s);
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            Intent i = new Intent(WelcomeActivity.this, Main2Activity.class);
                            startActivity(i);
                            finish();
                        }
                    }, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                return true;
            }

            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null) {
            if (mAgentWeb.handleKeyEvent(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        if (i == 100) {
            openFile(file);
        }
        super.onResume();
    }

    /**
     * 点击下一步
     */
    private void doNextStep() {
        toHomeActivity();
    }

    private void toHomeActivity() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }

    class DownloadAPK extends AsyncTask<String, Integer, String> {

        TextView tv;
        ProgressBar pb;

        public DownloadAPK(ProgressBar progressBarg, TextView textView) {
            this.pb = progressBarg;
            this.tv = textView;
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;

            try {
                url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(30000);

                int fileLength = conn.getContentLength();
                bis = new BufferedInputStream(conn.getInputStream());
                String fileName = Environment.getExternalStorageDirectory().getPath() + "/mrice/update.apk";
                file = new File(fileName);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                } else {
                    file.delete();
                }
                fos = new FileOutputStream(file);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while (!isCancelled() && (count = bis.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressBar.setProgress(progress[0]);
            tv.setText("已下载" + progress[0] + "%");
            i = progress[0];
        }


        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            openFile(file);
            ToastUtil.showToast("下载完成");
        }


    }

    @SuppressLint("WrongConstant")
    private void doStartApplicationWithPackageName(String packagename, String url) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;

        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageinfo = null;
        }
        if (packageinfo == null) {
            builder.show();
            downloadAPK = new DownloadAPK(progressBar, proText);
            downloadAPK.execute(url);
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.HomeActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            // 设置ComponentName参数1:packagename参数2:HomeActivity路径
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            startActivity(intent);
        }
    }

    //打开APK程序代码

    private void openFile(File file) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
