package com.outstudio.bosomcode.login_register;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.outstudio.bosomcode.main.MainActivity;
import com.outstudio.bosomcode.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * 注册成功后的界面
 * Created by mima123 on 15/8/4.
 */
public class RegisterSucceed extends Activity implements View.OnClickListener {

    private Button loginNowBt = null;
    private Button loginOtherWayBt = null;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public static final String SHARE_NAME = "bosomcode";
    public static final String SHARE_USERNAME = "share_username";
    public static final String SHARE_PASSWORD = "share_password";

    //立即登录所用的账号,密码(从注册界面获得)
    private String username = null;
    private String password = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                Log.e("handler", "收到数据");
                saveInfo(username, password);
                //如果登录成功,跳转到主页面,并将登录信息传递过去,以从后台获得个人信息
                Intent intent = new Intent(RegisterSucceed.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.USERNAME_FLAG, username);
                bundle.putString(Constant.PASSWORD_FLAG, password);
                intent.putExtra(Constant.BUNDLE_FLAG, bundle);
                startActivity(intent);
                RegisterSucceed.this.finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_succeed);
        loginNowBt = (Button) findViewById(R.id.register_succeed_login_now_bt);
        loginOtherWayBt = (Button) findViewById(R.id.register_succeed_login_other_way_bt);
        loginNowBt.setOnClickListener(this);
        loginOtherWayBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.register_succeed_login_now_bt:
                Bundle bundle = getIntent().getBundleExtra(Constant.BUNDLE_FLAG);
                username = bundle.getString(Constant.USERNAME_FLAG);
                password = bundle.getString(Constant.PASSWORD_FLAG);
                //登录
                login(username, password);
                break;
            case R.id.register_succeed_login_other_way_bt:
                intent = new Intent(RegisterSucceed.this, Login.class);
                startActivity(intent);
                this.finish();
                break;
        }

    }

    /**
     * 用于立即登录的方法
     *
     * @param username
     * @param password
     * @return
     */
    private void login(final String username, final String password) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String json = Jsoup.connect(Constant.LOGIN_URL).ignoreContentType(true).data("email", username).data("password", password).method(Connection.Method.POST).execute().body();
                    JSONObject object = new JSONObject(json);
                    int status = object.getInt(Constant.RESPONSE_KEY);
                    Log.e("status", "" + status);
                    if (status == Constant.LOGIN_SUCCESS) {
                        handler.sendEmptyMessage(0x123);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 保存登录信息
     *
     * @param username
     * @param password
     */
    public void saveInfo(String username, String password) {
        preferences = getSharedPreferences(SHARE_NAME, MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.putString(SHARE_USERNAME, username);
        editor.putString(SHARE_PASSWORD, password);
        editor.commit();
    }
}
