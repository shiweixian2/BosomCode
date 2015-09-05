package com.outstudio.bosomcode.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * 使用post方式传输数据
 *
 * Created by mima123 on 15/8/13.
 */
public class Post {

    public Post(){}
    private static Post post = new Post();
    public static Post getInstance(){
        return post;
    }

    public static void Volley_Post(String url, Map<String,String> map,String tag){
        final JSONObject object = new JSONObject(map);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("reso",jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag(tag);
        MyApplication.getHttpQueues().add(request);
    }
}
