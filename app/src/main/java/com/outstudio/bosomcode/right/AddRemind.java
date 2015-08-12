package com.outstudio.bosomcode.right;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.utils.FileUtil;
import com.outstudio.bosomcode.utils.DateUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mima123 on 15/8/10.
 */
public class AddRemind extends Activity implements View.OnClickListener {

    private Button setTimebt = null;
    private ImageButton calcelBt = null;
    private ImageButton confirmBt = null;
    private TextView timeText = null;
    private EditText contentEdit = null;

    private ArrayList<Integer> hourList = null;
    private ArrayList<Integer> minuteList = null;
    private ArrayList<Integer> mealList = null;

    //文件夹名称
    private static final String directoryName = FileUtil.rootDirectory + "AddRemind";
    private File destDir = new File(directoryName);

    private static final int REQUEST_CODE = 100;
    //RightFragment请求的返回值
    public static final int RESULT_CODE = 111;
    public static final String BUNDLE_KEY = "add_remind_bundle_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remind);
        contentEdit = (EditText) findViewById(R.id.add_remind_edit);
        setTimebt = (Button) findViewById(R.id.add_remind_time_bt);
        calcelBt = (ImageButton) findViewById(R.id.add_remind_cancel_bt);
        confirmBt = (ImageButton) findViewById(R.id.add_remind_confirm_bt);
        timeText = (TextView) findViewById(R.id.add_remind_time);
        timeText.setText(DateUtil.getTime());
        setTimebt.setOnClickListener(this);
        calcelBt.setOnClickListener(this);
        confirmBt.setOnClickListener(this);
    }

    /**
     * 处理用药方面信息的保存
     */
    private void manageFile() {
        FileUtil.getInstance().makeDir(destDir);
        File file = new File(destDir.getAbsolutePath() + "/" + DateUtil.setFileDate());
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(contentEdit.getText().toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置闹钟
     */
    private void setAlarmClock() {

        for (int i = 0; i < hourList.size(); i++) {
            Log.e("hour", "" + hourList.get(i));
            Log.e("minute", "" + minuteList.get(i));
            Log.e("meal",""+mealList.get(i));
            AlarmManager alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
            Intent alarmIntent = new Intent(AddRemind.this, AlarmActivity.class);
            PendingIntent pi = PendingIntent.getActivity(AddRemind.this, 0, alarmIntent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hourList.get(i));
            calendar.set(Calendar.MINUTE, minuteList.get(i));
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
            Log.e("alarm", "闹钟" + (i + 1) + "设置成功");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.add_remind_time_bt:
                intent = new Intent(AddRemind.this, SetTime.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.add_remind_cancel_bt:
                this.finish();
                break;
            case R.id.add_remind_confirm_bt:
                setAlarmClock();
                manageFile();
                sendResult();
                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)  {
            if (resultCode == SetTime.RESULT_CODE) {
                Bundle bundle = data.getBundleExtra(SetTime.BUNDLE_KEY);
                hourList = bundle.getIntegerArrayList(SetTime.HOUR_KEY);
                minuteList = bundle.getIntegerArrayList(SetTime.MINUTE_KEY);
                mealList = bundle.getIntegerArrayList(SetTime.MEAL_KEY);
            }
        }
    }

    /**
     * 发送数据回AddRemind类
     */
    private void sendResult() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(SetTime.HOUR_KEY, hourList);
        bundle.putIntegerArrayList(SetTime.MINUTE_KEY, minuteList);
        bundle.putIntegerArrayList(SetTime.MEAL_KEY, mealList);
        intent.putExtra(BUNDLE_KEY, bundle);
        this.setResult(RESULT_CODE, intent);
    }

}
