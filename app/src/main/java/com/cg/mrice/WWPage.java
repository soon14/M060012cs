package com.cg.mrice;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cg.mrice.utils.ToastUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Mr on 2018/4/22.
 */

public class WWPage extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private Dialog dialog;
    private ProgressBar progressBar;
    private TextView proText;
    private File file;
    private int i = 0;
    private DownloadAPK downloadAPK;
    private String url = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww);
        url = getIntent().getStringExtra("url");
        View v = LayoutInflater.from(this).inflate(R.layout.download_dialog, null);
        proText = (TextView) v.findViewById(R.id.protext);
        progressBar = (ProgressBar) v.findViewById(R.id.probar);
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(v);
        dialog = builder.create();
        doStartApplicationWithPackageName("cc.cc", url);
    }

    @Override
    protected void onResume() {
        if (i == 100) {
            openFile(file);
        }
        super.onResume();
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
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            // 设置ComponentName参数1:packagename参数2:MainActivity路径
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
