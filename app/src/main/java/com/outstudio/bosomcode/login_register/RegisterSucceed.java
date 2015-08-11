package com.outstudio.bosomcode.login_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.outstudio.bosomcode.main.MainActivity;
import com.outstudio.bosomcode.R;

/**
 * 注册成功后的界面
 * Created by mima123 on 15/8/4.
 */
public class RegisterSucceed extends Activity implements View.OnClickListener {

    private Button loginNowBt = null;
    private Button loginOtherWayBt = null;

    //立即登录所用的账号,密码(从注册界面获得)
    private String username = null;
    private String password = null;

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
                Bundle bundle = getIntent().getBundleExtra(Register.BUNDLE_FLAG);
                username = bundle.getString(Register.USERNAME_FLAG);
                password = bundle.getString(Register.PASSWORD_FLAG);
                boolean hasLogin = login(username, password);
                //如果登录成功,跳转到主页面,并将登录信息传递过去,以从后台获得个人信息
                if (hasLogin) {
                    intent = new Intent(RegisterSucceed.this, MainActivity.class);
                    Bundle infoForMain = new Bundle();
                    infoForMain.putString(Register.USERNAME_FLAG, username);
                    infoForMain.putString(Register.PASSWORD_FLAG, password);
                    intent.putExtra(Register.BUNDLE_FLAG, bundle);
                    startActivity(intent);
                }
                break;
            case R.id.register_succeed_login_other_way_bt:
                intent = new Intent(RegisterSucceed.this, Login.class);
                startActivity(intent);
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
    private boolean login(String username, String password) {

        return false;
    }
}
