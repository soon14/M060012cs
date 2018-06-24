package com.cg.mrice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.LoginResponse;
import com.cg.mrice.utils.PhoneNumberUtil;
import com.cg.mrice.utils.SPUtils;
import com.cg.mrice.utils.ToastUtil;
import com.cg.mrice.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by app on 2018/4/17.
 */
public class UserActivity extends AppCompatActivity implements VolleyCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.updatePassword)
    TextView updatePassword;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.openMsg)
    Switch aSwitch;
    @BindView(R.id.clear)
    TextView clear;
    @BindView(R.id.bbgx)
    TextView bbgx;
    @BindView(R.id.loginOut)
    Button loginOut;
    @BindView(R.id.headImg)
    ImageView headImg;
    @BindView(R.id.gywm)
    TextView gywm;
    private ProgressDialog progressDialog;
    boolean isLogin = false;

    @BindView(R.id.login)
    Button loginButton;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.findpass)
    TextView findPass;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.container_login)
    LinearLayout containLogin;
    private String mUserName, mPassWord;
    private boolean isIn;
    private int STARTCODE_REGISTER = 100;
    private int STARTCODE_MODIFY = 101;
    private JsonObjectRequest request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        toolbar.setTitle("个人中心");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登录...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        isLogin = SPUtils.getBoolean("login", false);
        if (isLogin) {
            containLogin.setVisibility(View.GONE);
            tvPhone.setText(SPUtils.getString("userinfo", "游客") + "," + "欢迎您");
            loginOut.setVisibility(View.VISIBLE);
        } else {
            containLogin.setVisibility(View.VISIBLE);
            loginOut.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.clear, R.id.bbgx, R.id.register, R.id.findpass, R.id.login, R.id.gywm, R.id.headImg, R.id.updatePassword, R.id.tv_phone, R.id.loginOut})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginOut:
                if (isLogin) {
                    loginOut();
                } else {
                    ToastUtil.showToast("您还没有登录");
                }
                break;
            case R.id.updatePassword:
                updatePwd();
                break;
            case R.id.clear:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            case R.id.register:
                toRegisterActivity();
                break;
            case R.id.login:
                mUserName = phoneEdit.getText().toString().trim();
                mPassWord = passwordEdit.getText().toString().trim();
                if (TextUtils.isEmpty(mUserName)) {
                    ToastUtil.showToast(getString(R.string.input_your_number));
                    return;
                }

                if (!PhoneNumberUtil.checkPhoneNumber(mUserName)) {
                    ToastUtil.showToast(getString(R.string.number_input_error));
                    return;
                }

                if (TextUtils.isEmpty(mPassWord)) {
                    ToastUtil.showToast(getString(R.string.input_your_password));
                    return;
                }

                int len = mPassWord.length();
                if (len < 6 || len > 15) {
                    ToastUtil.showToast(getString(R.string.input_your_password_error));
                    return;
                }
                requestLogin(mUserName, mPassWord);
                break;
            case R.id.findpass:
                toModifyPwd();
                break;
        }
    }

    private void requestLogin(String phone, String pwd) {
        HashMap params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("password", pwd);
        JSONObject object = new JSONObject(params);
        request = new VolleyUtils(1000, this).getJsonRequest(object, Utils.URL_LOGIN + "login");
        if (request != null) {
            request.setTag(UserActivity.class.getSimpleName());
            progressDialog.show();
            VolleyTool.getInstance(this).getmRequestQueue().add(request);
        }
    }

    /**
     * 修改密码
     */

    private void updatePwd() {
        Intent intent = new Intent(this, ModifyPwdActivity.class);
        startActivity(intent);
    }

    /**
     * 登录流程
     */
    private void loginOut() {
        isLogin = false;
        SPUtils.putBoolean("login", false);
        tvPhone.setText("未登录，点击登录");
        loginOut.setVisibility(View.GONE);
        containLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 22) {
                isLogin = SPUtils.getBoolean("login", false);
                tvPhone.setText(SPUtils.getString("userinfo", "游客") + "," + "欢迎您");
                loginOut.setVisibility(View.VISIBLE);
            }
        }
    }

    private void toModifyPwd() {
        Intent intent = new Intent(this, ModifyPwdActivity.class);
        intent.putExtra("module", "login");
        startActivityForResult(intent, STARTCODE_MODIFY);
    }

    /**
     * 去注册
     */
    private void toRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, STARTCODE_REGISTER);
    }

    @Override
    public void onResponse(String s, int tag) {
        if (isFinishing()) {
            return;
        }
        progressDialog.dismiss();
        try {
            LoginResponse response = CommonData.getGson().fromJson(s, LoginResponse.class);
            if (response == null) {
                return;
            }
            switch (tag) {
                case 1000:
                    if (response.isRequestSuccess()) {
                        ToastUtil.showToast("登录成功");
                        containLogin.setVisibility(View.GONE);
                        SPUtils.putBoolean("login", true);
                        SPUtils.putString("userinfo", response.getUser().getPhone());
                        isLogin = SPUtils.getBoolean("login", false);
                        tvPhone.setText(response.getUser().getPhone() + "," + "欢迎您");
                        loginOut.setVisibility(View.VISIBLE);
                    } else if (response.isRequestSituation()) {
                        ToastUtil.showToast("登录失败");
                    } else {
                        ToastUtil.showToast("登录失败");
                    }
                    break;
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError, int tag) {
        Log.e("----",volleyError.getMessage());
    }

    @Override
    public void onFailedResponse(String s) {
        ToastUtil.showToast(s);
    }

    @Override
    protected void onDestroy() {
        if (request != null) {
            VolleyTool.getInstance(this).release(UserActivity.class.getSimpleName());
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        super.onDestroy();
    }
}
