package com.outstudio.bosomcode.network;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by mima123 on 15/8/13.
 */
public class VolleyRequest {

    public static JsonObjectRequest jsonObjectRequest;

    public static void RequestGet(String url, String tag, VolleyInterface vif) {
        MyApplication.getHttpQueues().cancelAll(tag);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, vif.loadingListener(), vif.errorListener());
        jsonObjectRequest.setTag(tag);
        MyApplication.getHttpQueues().add(jsonObjectRequest);
        MyApplication.getHttpQueues().start();
    }

//    public static void RequestPost(String url, Map<String, String> map, String tag, VolleyInterface vif) {
//        MyApplication.getHttpQueues().cancelAll(tag);
//        JSONObject object = new JSONObject(map);
//        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, vif.loadingListener(), vif.errorListener());
//        jsonObjectRequest.setTag(tag);
//        MyApplication.getHttpQueues().add(jsonObjectRequest);
//        MyApplication.getHttpQueues().start();
//    }
    public static void RequestPost( String url, final Map<String, String> params,  String tag,VolleyInterface vif) {
        MyApplication.getHttpQueues().cancelAll(tag);
        JSONObject object = new JSONObject(params);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,object,vif.loadingListener(), vif.errorListener());
        jsonObjectRequest.setTag(tag);
        MyApplication.getHttpQueues().add(jsonObjectRequest);
        MyApplication.getHttpQueues().start();
    }

}
