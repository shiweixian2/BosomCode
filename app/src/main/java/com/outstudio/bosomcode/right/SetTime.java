package com.outstudio.bosomcode.right;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    private ImageButton cancelBt = null;
    private ImageButton confirmBt = null;
    //  private RadioButton beforeRadio = null;
//  private RadioButton afterRadio = null;
    private RadioGroup radioGroup = null;
    private List<Map<String, Object>> listItems = new ArrayList<>();
    private ArrayList<Integer> hourList = new ArrayList<>();
    private ArrayList<Integer> minuteList = new ArrayList<>();
    private ArrayList<Integer> mealList = new ArrayList<>();
    private SimpleAdapter simpleAdapter = null;
    private static final String ICON_FLAG = "icon_flag";
    private static final String TIME_FLAG = "time_flag";
    private static final String MEAL_FLAG = "meal_flag";
    public static final String HOUR_KEY = "hour_key";
    public static final String MINUTE_KEY = "minute_key";
    public static final String MEAL_KEY = "meal_key";
    public static final String BUNDLE_KEY = "set_time_bundle_key";
    public static final int RESULT_CODE = 1000;

    private String meal = "饭后";
    public static final int BEFORE_MEAL_FLAG = 1;
    public static final int AFTER_MEAL_FLAG = 2;
    private int meal_flag = 0;

//    public static final String DATA_FLAG = "data_flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remind_set_time);
        addBt = (Button) findViewById(R.id.add_remind_set_time_add_time_bt);
        listView = (ListView) findViewById(R.id.add_remind_set_time_listView);
        radioGroup = (RadioGroup) findViewById(R.id.add_remind_set_time_radioGroup);
        cancelBt = (ImageButton) findViewById(R.id.add_remind_set_time_cancel_bt);
        confirmBt = (ImageButton) findViewById(R.id.add_remind_set_time_confirm_bt);
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
                setMealData(meal);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        addBt.setOnClickListener(this);
        cancelBt.setOnClickListener(this);
        confirmBt.setOnClickListener(this);
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
                setHourData(hourOfDay);
                setMinuteData(minute);
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
        while (listItems.size() != 0) {
            listItems.remove(0);
        }
        while (hourList.size() != 0) {
            hourList.remove(0);
        }
        while (minuteList.size() != 0) {
            minuteList.remove(0);
        }
        simpleAdapter.notifyDataSetChanged();
    }

    /**
     * 存储小时数据
     *
     * @param hourOfDay
     */
    private void setHourData(int hourOfDay) {
        hourList.add(hourOfDay);
    }

    /**
     * 存储分钟数据
     *
     * @param minute
     */
    private void setMinuteData(int minute) {
        minuteList.add(minute);
    }
    /**
     * 存储饭前饭后数据
     *
     * @param meal
     */
    private void setMealData(String meal) {
        if (meal.equals("饭前")){
            meal_flag = BEFORE_MEAL_FLAG;
            mealList.add(meal_flag);
        }else if (meal.equals("饭后")){
            meal_flag = AFTER_MEAL_FLAG;
            mealList.add(meal_flag);
        }
    }

    private ArrayList<Integer> getHourData() {
        return hourList;
    }

    private ArrayList<Integer> getMinuteData() {
        return minuteList;
    }
    private ArrayList<Integer> getMealData() {
        return mealList;
    }

    /**
     * 发送数据回AddRemind类
     */
    private void sendResult(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(HOUR_KEY,getHourData());
        bundle.putIntegerArrayList(MINUTE_KEY,getMinuteData());
        bundle.putIntegerArrayList(MEAL_KEY, getMealData());
        intent.putExtra(BUNDLE_KEY, bundle);
        this.setResult(RESULT_CODE,intent);
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
                sendResult();
                this.finish();
                break;
        }
    }
}
