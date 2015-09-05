package com.outstudio.bosomcode.network;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mima123 on 15/8/13.
 */
public class MyApplication extends Application {

    public static RequestQueue queues;

    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
    }

    /**
     * 获取请求队列
     *
     * @return
     */
    public static RequestQueue getHttpQueues() {
        return queues;
    }
}
