package com.outstudio.bosomcode.login_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.network.MyApplication;
import com.outstudio.bosomcode.network.VolleyInterface;
import com.outstudio.bosomcode.network.VolleyRequest;
import com.outstudio.bosomcode.right.SetTime;
import com.outstudio.bosomcode.utils.ToMD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

//    public static final String USERNAME_FLAG = "username_flag";
//    public static final String PASSWORD_FLAG = "password_flag";
//    public static final String BUNDLE_FLAG = "bundle_flag";

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
                    registerInNetwork(username, pwd);
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
        } else if (pwdConfirmPre.isEmpty() || pwdConfirmPre.equals("") || !pwdPre.equals(pwdConfirmPre)) {
            showTip("两次密码不一致");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证及注册
     *
     * @param username
     * @param password
     * @return
     */
    private void registerInNetwork(final String username, final String password) {

        VolleyRequest.RequestGet(Constant.REGISTER_CHECK_USER_URL + "&email=" + username, Constant.REGISTER_TAG,
                new VolleyInterface(this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                    @Override
                    public void onMySuccess(JSONObject jsonObject) {
                        Log.e("json", jsonObject.toString());
                        try {
                            String checkResult = jsonObject.getString(Constant.RESPONSE_KEY);
                            Log.e("check", "" + checkResult);
                            if (checkResult.equals(Constant.REGISTER_ACCOUNT_CAN_USE)) {
                                Log.e("zhuce", "正在注册");
                                Log.e("usernamePre", usernamePre);
                                Log.e("pwdPre", pwdPre);
//                                Map<String, String> registerMap = new HashMap<>();
//                                registerMap.put("email", usernamePre);
//                                registerMap.put(Constant.PASSWORD_KEY, pwdPre);
//                                VolleyRequest.RequestPost(Constant.REGISTER_URL, registerMap, Constant.REGISTER_TAG,
//                                        new VolleyInterface(Register.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
//                                            @Override
//                                            public void onMySuccess(JSONObject jsonObject) {
//                                                try {
//                                                    String registerResult = jsonObject.getString(Constant.RESPONSE_KEY);
//                                                    Log.e("registerResult", "" + registerResult);
//                                                    Log.e("REGISTER_SUCCESS", "" + Constant.REGISTER_SUCCESS);
//                                                    Log.e("username", username);
//                                                    Log.e("password", password);
//                                                    if (registerResult.equals(Constant.REGISTER_SUCCESS)) {
//                                                        Log.e("zhuce", "注册成功");
//                                                        Intent intent = new Intent(Register.this, RegisterSucceed.class);
//                                                        Bundle bundle = new Bundle();
//                                                        bundle.putString(Constant.USERNAME_FLAG, username);
//                                                        bundle.putString(Constant.PASSWORD_FLAG, password);
//                                                        intent.putExtra(Constant.BUNDLE_FLAG, bundle);
//                                                        startActivity(intent);
//                                                        Register.this.finish();
//                                                    }
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onMyError(VolleyError volleyError) {
//                                                showTip("网络原因,注册失败!");
//                                            }
//                                        });

                                VolleyRequest.RequestGet(Constant.REGISTER_URL+"&email="+usernamePre+"&password="+pwdPre, Constant.REGISTER_TAG, new VolleyInterface(Register.this,
                                        VolleyInterface.mListener,VolleyInterface.mErrorListener) {
                                    @Override
                                    public void onMySuccess(JSONObject jsonObject) {
                                        Log.e("json",jsonObject.toString());
                                        String registerResult = null;
                                        try {
                                            registerResult = jsonObject.getString(Constant.RESPONSE_KEY);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if (registerResult.equals(Constant.REGISTER_SUCCESS)) {
                                            Log.e("zhuce", "注册成功");
                                            Intent intent = new Intent(Register.this, RegisterSucceed.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString(Constant.USERNAME_FLAG, usernamePre);
                                            bundle.putString(Constant.PASSWORD_FLAG, pwdPre);
                                            intent.putExtra(Constant.BUNDLE_FLAG, bundle);
                                            startActivity(intent);
                                            Register.this.finish();
                                        }
                                    }

                                    @Override
                                    public void onMyError(VolleyError volleyError) {

                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMyError(VolleyError volleyError) {
                        showTip("连接出错");
                        Log.e("volleyError", volleyError.toString());
                    }
                });


    }


    private void showTip(String content) {
        Toast.makeText(Register.this, content, Toast.LENGTH_SHORT).show();
    }
}
