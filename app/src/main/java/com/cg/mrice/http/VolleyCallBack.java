package com.cg.mrice.http;

import com.android.volley.VolleyError;

/**
 * volley回调
 */
public interface VolleyCallBack {
    /**
     * 成功返回
     * @param s 字符串
     * @param tag 标签
     */
    void onResponse(String s, int tag);

    /**
     * 失败返回
     * @param volleyError 字符串
     * @param tag 标签
     */
    void onErrorResponse(VolleyError volleyError, int tag);


    void onFailedResponse(String s);

}
