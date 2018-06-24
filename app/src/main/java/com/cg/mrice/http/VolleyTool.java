package com.cg.mrice.http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyTool {
    private static VolleyTool mInstance = null;
    private RequestQueue mRequestQueue;
    //private ImageLoader mImageLoader;

    private VolleyTool(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        //mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
    }

    public static VolleyTool getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyTool(context);
        }
        return mInstance;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

//	public ImageLoader getmImageLoader() {
//		return mImageLoader;
//	}

    public void release(String tag) {
        //this.mImageLoader = null;
        this.mRequestQueue.cancelAll(tag);
        this.mRequestQueue = null;
        mInstance = null;
    }
}
