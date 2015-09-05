package com.outstudio.bosomcode.right;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.main.MainActivity;
import com.outstudio.bosomcode.utils.MyTimePickerDialog;

import java.util.Calendar;

/**
 * Created by mima123 on 15/8/10.
 */
public class AddRemind extends Activity implements View.OnClickListener
{

    public static final String KEY_HOUR = "HOUR";
    public static final String KEY_MINUTE = "MINUTE";
    public static final String KEY_ENABLED = "ENABLED";
    private Button setRemindTime;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private Remind remind;
    private ImageButton cancelButton;
    private ImageButton confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remind);
        setRemindTime = (Button) findViewById(R.id.set_remind_time_bt);
        cancelButton = (ImageButton) findViewById(R.id.add_remind_cancel_bt);
        confirmButton = (ImageButton) findViewById(R.id.add_remind_confirm_bt);
        calendar = Calendar.getInstance();
        setRemindTime.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        remind = new Remind(0, 0, false);

        /* 获取闹钟管理的实例 */
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.add_remind_cancel_bt:
                finish();
                break;
            case R.id.add_remind_confirm_bt:
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(KEY_ENABLED, remind.getEnabled());
                bundle.putInt(KEY_MINUTE, remind.getMinutes());
                bundle.putInt(KEY_HOUR, remind.getHour());
                intent.putExtras(bundle);
                setResult(2,intent);
                finish();
                break;
            case R.id.set_remind_time_bt:
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                new MyTimePickerDialog(AddRemind.this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute)
                            {
                                calendar.setTimeInMillis(System
                                        .currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);

                                Intent intent = new Intent(AddRemind.this,
                                        RemindReceiver.class);
                                PendingIntent pendingIntent = PendingIntent
                                        .getBroadcast(AddRemind.this, 0,
                                                intent, 0);

                                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar
                                        .getTimeInMillis(), pendingIntent);
                                /* 设置周期 */
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), (24 * 60 * 60 * 1000),
                                        pendingIntent);
                                String tmpS = "提醒时间为" + format(hourOfDay)
                                        + ":" + format(minute);
                                setRemindTime.setText(tmpS);
                                remind.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                                remind.setMinutes(calendar.get(Calendar.MINUTE));
                                remind.setEnabled(true);

                                Toast.makeText(AddRemind.this, remind.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }, hour, minute, true).show();
                break;
        }
    }

    private String format(int x)
    {
        String s = "" + x;
        if (s.length() == 1)
            s = "0" + s;
        return s;
    }

}
