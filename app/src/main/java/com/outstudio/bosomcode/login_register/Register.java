package com.outstudio.bosomcode.login_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.utils.ToMD5;

/**
 * 注册界面
 * Created by shiweixian on 2015/8/4.
 */
public class Register extends Activity implements View.OnClickListener {

    private EditText usernameEdit = null;
    private EditText pwdEdit = null;
    private EditText pwdConfirmEdit = null;
    private Button registerBt = null;
    private TextView backToLogin = null;

    private String usernamePre = null;
    private String pwdPre = null;
    private String pwdConfirmPre = null;

    private String username = null;
    private String pwd = null;

    public static final String USERNAME_FLAG = "username_flag";
    public static final String PASSWORD_FLAG = "password_flag";
    public static final String BUNDLE_FLAG = "bundle_flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
    }

    /**
     * 初始化界面组件
     */
    private void initView() {
        usernameEdit = (EditText) findViewById(R.id.register_username);
        pwdEdit = (EditText) findViewById(R.id.register_pwd);
        pwdConfirmEdit = (EditText) findViewById(R.id.register_pwd_confirm);
        registerBt = (Button) findViewById(R.id.register_now_bt);
        backToLogin = (TextView) findViewById(R.id.register_back_to_login);
        registerBt.setOnClickListener(this);
        backToLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.register_now_bt:
                usernamePre = usernameEdit.getText().toString();
                pwdPre = pwdEdit.getText().toString();
                pwdConfirmPre = pwdConfirmEdit.getText().toString();
                boolean localRight = verifyInfoLocally(usernamePre, pwdPre, pwdConfirmPre);
                if (localRight) {
                    username = ToMD5.getMD5(usernamePre);
                    pwd = ToMD5.getMD5(pwdPre);
                    boolean ok = verifyInfoNetwork(username, pwd);
                    if (ok) {
                        intent = new Intent(Register.this, RegisterSucceed.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(USERNAME_FLAG, username);
                        bundle.putString(PASSWORD_FLAG, pwd);
                        intent.putExtra(BUNDLE_FLAG, bundle);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.register_back_to_login:
                intent = new Intent(Register.this, Login.class);
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
    private boolean verifyInfoLocally(String usernamePre, String pwdPre, String pwdConfirmPre) {
        if (usernamePre.isEmpty() || usernamePre.equals("")) {
            showTip("邮箱号不能为空");
            return false;
        } else if (!usernamePre.matches(".*@.*")) {
            showTip("邮箱格式不正确");
            return false;
        } else if (pwdPre.isEmpty() || pwdPre.equals("")) {
            showTip("密码不能为空");
            return false;
        } else if (pwdConfirmPre.isEmpty() || pwdConfirmPre.equals("") || !pwd.equals(pwdConfirmPre)) {
            showTip("两次密码不一致");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 网络验证用户的登录信息
     *
     * @param username
     * @param password
     * @return
     */
    private boolean verifyInfoNetwork(String username, String password) {
        return true;
    }

    private void showTip(String content) {
        Toast.makeText(Register.this, content, Toast.LENGTH_SHORT).show();
    }
}
