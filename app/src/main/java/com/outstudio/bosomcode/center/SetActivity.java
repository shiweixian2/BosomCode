package com.outstudio.bosomcode.center;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.login_register.Login;

/**
 * Created by android on 8/17/15.
 */
public class SetActivity extends Activity
{
    private Button exitAccount;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Handler handler = new Handler()
    {
        @Override public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 123)
                SetActivity.this.finish();
        }
    };


    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        preferences = getSharedPreferences(Login.SHARE_NAME, MODE_PRIVATE);
        exitAccount = (Button) findViewById(R.id.exit_account_button);
        exitAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                editor = preferences.edit();
                editor.clear();
                editor.putString(Login.SHARE_USERNAME, null);
                editor.putString(Login.SHARE_PASSWORD, null);
                editor.commit();

                Intent intent = new Intent(SetActivity.this, Login.class);
                startActivity(intent);
                handler.sendEmptyMessage(123);
                SetActivity.this.finish();

            }
        });
    }
}
