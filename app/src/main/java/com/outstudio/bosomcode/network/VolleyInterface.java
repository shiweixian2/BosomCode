package com.outstudio.bosomcode.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by mima123 on 15/8/13.
 */
public abstract class VolleyInterface {

    public static Context mContext;
    public static Response.Listener<JSONObject> mListener;
    public static Response.ErrorListener mErrorListener;

    public VolleyInterface(Context context, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        mContext = context;
        mListener = listener;
        mErrorListener = errorListener;
    }

    public abstract void onMySuccess(JSONObject jsonObject);
    public abstract void onMyError(VolleyError volleyError);

    public Response.Listener<JSONObject> loadingListener(){
        mListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //可以全局显示对话框等
                onMySuccess(jsonObject);
            }
        };
        return mListener;
    }
    public Response.ErrorListener errorListener(){
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //可以全局提示请求失败等
                onMyError(volleyError);
            }
        };
        return mErrorListener;
    }

}
