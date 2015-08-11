package com.outstudio.bosomcode.login_register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.outstudio.bosomcode.main.MainActivity;
import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.utils.ToMD5;

/**
 * 登录界面
 * Created by shiweixian on 2015/8/4.
 */
public class Login extends Activity implements View.OnClickListener {

    private TextView forgetPwd = null;
    private EditText usernameEdit = null;
    private EditText passwordEdit = null;
    private Button loginBt = null;
    private Button toRegisterBt = null;
    //用户名
    private String usernamePre = null;
    //密码
    private String passwordPre = null;
    //加密后的用户名
    private String username = null;
    //加密后的密码
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
    }

    /**
     * 初始化界面组件
     */
    private void initView() {
        usernameEdit = (EditText) findViewById(R.id.login_username);
        passwordEdit = (EditText) findViewById(R.id.login_password);
        loginBt = (Button) findViewById(R.id.login_login_bt);
        toRegisterBt = (Button) findViewById(R.id.login_register_bt);
        forgetPwd = (TextView) findViewById(R.id.login_forget_pwd);
        forgetPwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        forgetPwd.getPaint().setAntiAlias(true);//抗锯齿
        loginBt.setOnClickListener(this);
        toRegisterBt.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login_login_bt:
                usernamePre = usernameEdit.getText().toString();
                passwordPre = passwordEdit.getText().toString();

                boolean isRightInfo = verifyInfoLocally(usernamePre, passwordPre);
                //本地验证成功
                if (isRightInfo) {
                    username = ToMD5.getMD5(usernamePre);
                    password = ToMD5.getMD5(passwordPre);
                    boolean ok = verifyInfoNetwork(username, password);
                    //与数据库交流验证成功
                    if (ok) {
                        Log.d("username", usernameEdit.getText().toString());
                        Log.d("username_md5", username);
                        Log.d("password", passwordEdit.getText().toString());
                        Log.d("password_md5", password);
                        showTip("登录成功");
                        intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        this.finish();
                    } else {
                        showTip("用户名或密码错误");
                    }
                }
                break;
            case R.id.login_register_bt:
                intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }

    /**
     * 本地验证用户信息格式的正确性
     *
     * @return
     */
    private boolean verifyInfoLocally(String usernamePre, String passwordPre) {
//        if (usernamePre.isEmpty() || usernamePre.equals("")) {
//            showTip("邮箱号不能为空");
//            return false;
//        } else if (passwordPre.isEmpty() || passwordPre.equals("")) {
//            showTip("密码不能为空");
//            return false;
//        } else if (!usernamePre.matches(".*@.*")) {
//            showTip("邮箱格式不正确");
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }

    /**
     * 从后台对比用户的登录信息
     *
     * @param username
     * @param password
     * @return
     */
    private boolean verifyInfoNetwork(String username, String password) {
        return true;
    }

    private void showTip(String content) {
        Toast.makeText(Login.this, content, Toast.LENGTH_SHORT).show();
    }
}
