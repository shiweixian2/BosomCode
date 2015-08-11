package com.outstudio.bosomcode.right;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.utils.MyTimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mima123 on 15/8/11.
 */
public class SetTime extends Activity implements View.OnClickListener {

    private Button addBt = null;
    private ListView listView = null;
    private Button cancelBt = null;
    private Button confirmBt = null;
    //  private RadioButton beforeRadio = null;
//  private RadioButton afterRadio = null;
    private RadioGroup radioGroup = null;
    private List<Map<String, Object>> listItems = new ArrayList<>();
    private List<Map<String, Object>> timeList = new ArrayList<>();
    private SimpleAdapter simpleAdapter = null;
    private static final String ICON_FLAG = "icon_flag";
    private static final String TIME_FLAG = "time_flag";
    private static final String MEAL_FLAG = "meal_flag";
    public static final String HOUR_FLAG = "hour_flag";
    public static final String MINUTE_FALG = "minute_flag";

    private String meal = "饭后";

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remind_set_time);
        addBt = (Button) findViewById(R.id.add_remind_set_time_add_time_bt);
        listView = (ListView) findViewById(R.id.add_remind_set_time_listView);
        radioGroup = (RadioGroup) findViewById(R.id.add_remind_set_time_radioGroup);
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.for_add_remind_set_time_fragment_listview,
                new String[]{ICON_FLAG, TIME_FLAG, MEAL_FLAG}, new int[]{R.id.add_remind_set_time_clock,
                R.id.add_remind_set_time_text, R.id.add_remind_set_meal_text});
        listView.setAdapter(simpleAdapter);
//        beforeRadio = (RadioButton) findViewById(R.id.add_remind_before_meal_radio);
//        afterRadio = (RadioButton) findViewById(R.id.add_remind_after_meal_radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                meal = checkedId == R.id.add_remind_before_meal_radio ? "饭前" : "饭后";
                simpleAdapter.notifyDataSetChanged();
            }
        });
        addBt.setOnClickListener(this);
    }

    /**
     * 设置时间的对话框
     */
    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        new MyTimePickerDialog(SetTime.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setData(hourOfDay, minute);
                setTimeData(hourOfDay, minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

    }


    /**
     * 设置适配器的数据
     *
     * @param hourOfDay
     * @param minute
     */
    private void setData(int hourOfDay, int minute) {
        Map<String, Object> map = new HashMap<>();
        String hourStr = "" + hourOfDay;
        String minuteStr = "" + minute;
        if (hourOfDay < 10) {
            hourStr = "0" + hourOfDay;
        }
        if (minute < 10) {
            minuteStr = "0" + minute;
        }
        String time = hourStr + ":" + minuteStr;
        if (6 <= hourOfDay && hourOfDay < 12) {
            map.put(ICON_FLAG, R.drawable.right_fragment_morning_clock);
        } else if (hourOfDay > 12 && hourOfDay < 18) {
            map.put(ICON_FLAG, R.drawable.right_fragment_afternooon_clock);
        } else {
            map.put(ICON_FLAG, R.drawable.right_fragment_night_clock);
        }
        map.put(TIME_FLAG, time);
        map.put(MEAL_FLAG, meal);
        listItems.add(map);
        simpleAdapter.notifyDataSetChanged();
        Log.e("test", "" + count);
        count++;
    }

    /**
     * 获取适配器的数据
     *
     * @return
     */
    private List<Map<String, Object>> getData() {
        return listItems;
    }

    /**
     * 清除数据
     */
    private void removeAllListData() {
        for (int i = 0; i < listItems.size(); i++) {
            listItems.remove(i);
        }
        for (int i = 0; i < timeList.size(); i++) {
            timeList.remove(i);
        }
        simpleAdapter.notifyDataSetChanged();
    }

    /**
     * 存储时间数据
     * @param hourOfDay
     * @param minute
     */
    private void setTimeData(int hourOfDay, int minute) {
        Map<String, Object> map = new HashMap<>();
        map.put(HOUR_FLAG, hourOfDay);
        map.put(MINUTE_FALG, minute);
        timeList.add(map);
    }

    public List<Map<String, Object>> getTimeData() {
        return timeList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_remind_set_time_add_time_bt:
                showTimeDialog();
                break;
            case R.id.add_remind_set_time_cancel_bt:
                removeAllListData();
                this.finish();
                break;
            case R.id.add_remind_set_time_confirm_bt:
                this.finish();
                break;
        }
    }
}
