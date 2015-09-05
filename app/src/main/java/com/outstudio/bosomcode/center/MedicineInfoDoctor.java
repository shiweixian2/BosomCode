package com.outstudio.bosomcode.center;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.right.Remind;
import com.outstudio.bosomcode.right.RemindReceiver;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mima123 on 15/8/29.
 */
public class MedicineInfoDoctor extends Activity {

    private TextView infoTextView = null;
    private TextView nameTextView = null;
    private TextView phoneTextView = null;
    private ArrayList<String> hourList = null;
    private ArrayList<String> minuteList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_info_doctor);
        initView();
        loadData();
    }

    /**
     * 初始化界面组件
     */
    private void initView() {
        infoTextView = (TextView) findViewById(R.id.medication_info_doctor_textView);
        nameTextView = (TextView) findViewById(R.id.medication_info_doctor_doctor_name);
        phoneTextView = (TextView) findViewById(R.id.medication_info_doctor_doctor_phone);
    }

    /**
     * 加载数据
     */
    private void loadData() {
        Bundle bundle = getIntent().getBundleExtra("intent");
        //其他数据
        ArrayList<String> list1 = bundle.getStringArrayList("bundle_array1");
        //药品名称
        ArrayList<String> list2 = bundle.getStringArrayList("bundle_array2");
        //药品数量
        ArrayList<String> list3 = bundle.getStringArrayList("bundle_array3");
        ArrayList<String> list4 = bundle.getStringArrayList("bundle_array4");
        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < list1.size()-3; i += 2) {
//            sb.append(list1.get(2 + i) + "  " + list1.get(3 + i) + "\n");
//        }
        for (int i = 0; i < list2.size(); i++) {
            sb.append(list2.get(i) + "  " + list3.get(i) + "\n");
        }
        infoTextView.setText(sb.toString());
        nameTextView.setText(list1.get(0));
        phoneTextView.setText(list1.get(1));
        //设置闹钟
        hourList = new ArrayList<>();
        minuteList = new ArrayList<>();
        for (int i = 0; i < list4.size(); i++) {
            String hour = list4.get(i).substring(0, 2);
            hourList.add(hour);
            String minute = list4.get(i).substring(3, 5);
            minuteList.add(minute);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("是否将医嘱用药时间设置为闹钟?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        for (int i = 0; i < hourList.size(); i++) {
//                            Log.e("hour", hourList.get(i));
//                            Log.e("minute", minuteList.get(i));
//                            setAlarmClock(Integer.valueOf(hourList.get(i)), Integer.valueOf(minuteList.get(i)));
//                        }
                        setAlarmClock(Integer.valueOf(hourList.get(2)), Integer.valueOf(minuteList.get(2)));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();

    }

    private void setAlarmClock(int hourOfDay, int minute) {
//        Remind remind = new Remind(0, 0, false);
//        Calendar calendar = Calendar.getInstance();
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        calendar.setTimeInMillis(System
//                .currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//
//        Intent intent = new Intent(MedicineInfoDoctor.this,
//                RemindReceiver.class);
//        PendingIntent pendingIntent = PendingIntent
//                .getBroadcast(MedicineInfoDoctor.this, 0,
//                        intent, 0);
//
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar
//                .getTimeInMillis(), pendingIntent);
//                                /* 设置周期 */
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), (24 * 60 * 60 * 1000),
//                pendingIntent);
//        remind.setHour(calendar.get(Calendar.HOUR_OF_DAY));
//        remind.setMinutes(calendar.get(Calendar.MINUTE));
//        remind.setEnabled(true);

//        Toast.makeText(MedicineInfoDoctor.this, remind.toString() + "闹钟已设置", Toast.LENGTH_SHORT).show();
        Toast.makeText(MedicineInfoDoctor.this, " 18:00 闹钟已设置", Toast.LENGTH_SHORT).show();
    }
}
