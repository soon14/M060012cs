package com.cg.mrice.http;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.MyApp;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.utils.Utils;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * volley网络访问类
 */
public class VolleyUtils {

    public static final String GLO_URL = "http://6711pc.com:8888/appgl/cp/getApp";

    private Context context;
    private int tag;
    private VolleyCallBack volleyCallBack;

    public VolleyUtils(int tag, VolleyCallBack volleyCallBack) {
        this.context = MyApp.getInstance();
        this.tag = tag;
        this.volleyCallBack = volleyCallBack;
    }

    public StringRequest getStringRequest(String url) {
        if (Utils.checknetstate(context)) {
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    if (volleyCallBack != null) {
                        volleyCallBack.onResponse(s, tag);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyCallBack != null) {
                        volleyCallBack.onErrorResponse(volleyError, tag);
                    }
                }
            }) {
            };
            return request;
        } else {
            if (volleyCallBack != null) {
                volleyCallBack.onFailedResponse("网络连接异常");
            }
            Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 网络请求
     *
     * @param params
     * @return
     */
    public StringRequest getStringRequest(final HashMap<String, String> params) {
        if (Utils.checknetstate(context)) {
            StringRequest request = new StringRequest(Request.Method.POST, GLO_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    if (volleyCallBack != null) {
                        volleyCallBack.onResponse(s, tag);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyCallBack != null) {
                        volleyCallBack.onErrorResponse(volleyError, tag);
                    }
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params;
                }

            };
            return request;
        } else {
            if (volleyCallBack != null) {
                volleyCallBack.onFailedResponse("网络连接异常");
            }
            Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 网络请求
     *
     * @param params
     * @return
     */
    public StringRequest getStringRequest(final HashMap<String, String> params, String url) {
        if (Utils.checknetstate(context)) {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    if (volleyCallBack != null) {
                        volleyCallBack.onResponse(s, tag);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyCallBack != null) {
                        volleyCallBack.onErrorResponse(volleyError, tag);
                    }
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params;
                }

            };
            return request;
        } else {
            if (volleyCallBack != null) {
                volleyCallBack.onFailedResponse("网络连接异常");
            }
            Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 网络请求
     *
     * @return
     */
    public JsonObjectRequest getJsonRequest(final JSONObject jsonObject, String url) {
        if (Utils.checknetstate(context)) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener() {
                @Override
                public void onResponse(Object s) {
                    if (volleyCallBack != null) {
                        volleyCallBack.onResponse(s.toString(), tag);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyCallBack != null) {
                        volleyCallBack.onErrorResponse(volleyError, tag);
                    }
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    return headers;
                }
            };
            return request;
        } else {
            if (volleyCallBack != null) {
                volleyCallBack.onFailedResponse("网络连接异常");
            }
            Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
