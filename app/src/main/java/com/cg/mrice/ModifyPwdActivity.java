package com.cg.mrice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.LoginResponse;
import com.cg.mrice.utils.PhoneNumberUtil;
import com.cg.mrice.utils.ToastUtil;
import com.cg.mrice.utils.Utils;
import com.cg.mrice.view.CountdownInputBoxView;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by company on 2017/8/8.
 */

public class ModifyPwdActivity extends AppCompatActivity implements VolleyCallBack {

    private static final String TAG = "ModifyPwdActivity";
    @BindView(R.id.countdown_inputbox)
    CountdownInputBoxView mCountDownInputBoxView;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.modifyPwd)
    Button modifyButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /**
     * 是否点击了验证码
     */
    private boolean isClickVerify = false;
    private String mNumber;
    private String mPassword;
    private String smsCode;
    private String where;
    private JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        where = getIntent().getStringExtra("module");
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mCountDownInputBoxView.setTitle(getResources().getString(R.string.verifyCode));
        mCountDownInputBoxView.setEditLength(6);
        mCountDownInputBoxView.setEditInputType(InputType.TYPE_CLASS_NUMBER);
        mCountDownInputBoxView.getVerfyCodeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNumber = phoneEdit.getText().toString().trim();
                if (TextUtils.isEmpty(mNumber)) {
                    ToastUtil.showToast("请先输入手机号码");
                    return;
                }
                if (!PhoneNumberUtil.checkPhoneNumber(mNumber)) {
                    ToastUtil.showToast("手机号码格式错误");
                    return;
                }
                requestYZM(mNumber);
            }
        });
    }

    private void requestYZM(String phone) {
        HashMap params = new HashMap<String, String>();
        params.put("phone", phone);
        JSONObject object=new JSONObject(params);
        request = new VolleyUtils(1000, this).getJsonRequest(object, Utils.URL_LOGIN + "login/getValidMM");
        mCountDownInputBoxView.startCountdown();
        if (request != null) {
            request.setTag(ModifyPwdActivity.class.getSimpleName());
            VolleyTool.getInstance(this).getmRequestQueue().add(request);
        }
    }

    private void requestModify(String mNumber, String mPassword, String smsCode) {
        HashMap params = new HashMap<String, String>();
        params.put("name", smsCode);
        params.put("phone", mNumber);
        params.put("password", mPassword);
        JSONObject object=new JSONObject(params);
        request = new VolleyUtils(2000, this).getJsonRequest(object, Utils.URL_LOGIN + "login/updatePwd");
        if (request != null) {
            request.setTag(ModifyPwdActivity.class.getSimpleName());
            VolleyTool.getInstance(this).getmRequestQueue().add(request);
        }
    }


    @OnClick(R.id.modifyPwd)
    public void onClick(View v) {
        mNumber = phoneEdit.getText().toString().trim();
        mPassword = passwordEdit.getText().toString().trim();
        smsCode = mCountDownInputBoxView.getVerifycodeEdit().getText().toString().trim();
        if (TextUtils.isEmpty(mNumber)) {
            ToastUtil.showToast(getString(R.string.input_your_number));
            return;
        }
        if (!PhoneNumberUtil.checkPhoneNumber(mNumber)) {
            ToastUtil.showToast(getString(R.string.number_input_error));
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            ToastUtil.showToast(getString(R.string.input_your_password));
            return;
        }
        int len = mPassword.length();
        if (len < 6 || len > 15) {
            ToastUtil.showToast(getString(R.string.input_your_password_error));
            return;
        }
        if (TextUtils.isEmpty(smsCode)) {
            ToastUtil.showToast(getString(R.string.input_verifycode));
            return;
        }
        if (smsCode.length() != 6) {
            ToastUtil.showToast(getString(R.string.input_correct_verifycode));
            return;
        }
        requestModify(mNumber, mPassword, smsCode);
    }

    @Override
    public void onResponse(String s, int tag) {
        if (isFinishing()) {
            return;
        }
        try {
            LoginResponse response = CommonData.getGson().fromJson(s, LoginResponse.class);
            if (response == null) {
                return;
            }
            switch (tag) {
                case 1000:
                    if (response.isRequestSuccess()) {
                        ToastUtil.showToast("发送成功");
                    } else if (response.isRequestSituation()) {
                        mCountDownInputBoxView.cancleTime();
                        ToastUtil.showToast(getString(R.string.verifycode_failed));
                    } else {
                        mCountDownInputBoxView.cancleTime();
                        ToastUtil.showToast(getString(R.string.verifycode_repeat));
                    }
                    break;
                case 2000:
                    if (response.isRequestSuccess()) {
                        ToastUtil.showToast("修改成功");
                        finish();
                    } else if (response.isRequestFailed()) {
                        ToastUtil.showToast("修改失败");
                    } else {
                        ToastUtil.showToast("参数错误");
                    }
                    break;
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError, int tag) {

    }

    @Override
    public void onFailedResponse(String s) {
        ToastUtil.showToast(s);
    }

    @Override
    protected void onDestroy() {
        if (request != null) {
            VolleyTool.getInstance(this).release(ModifyPwdActivity.class.getSimpleName());
        }
        if (mCountDownInputBoxView != null) {
            mCountDownInputBoxView.cancleTime();
            mCountDownInputBoxView = null;
        }
        super.onDestroy();
    }
}
