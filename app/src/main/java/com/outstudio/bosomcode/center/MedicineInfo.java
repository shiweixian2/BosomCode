package com.outstudio.bosomcode.center;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.outstudio.bosomcode.R;

import java.util.ArrayList;

/**
 * Created by mima123 on 15/8/14.
 */
public class MedicineInfo extends Activity {

    TextView name;
    TextView common_name;
    TextView indication;
    TextView usage;
    TextView side_affects;
    TextView avoid;
    TextView attention;
    TextView validity_time;
    TextView create_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_info);
        initView();
        ArrayList<String> list = getIntent().getBundleExtra("intent").getStringArrayList("bundle_array1");
        name.setText(list.get(0));
        common_name.setText(list.get(1));
        indication.setText(list.get(2));
        usage.setText(list.get(3));
        side_affects.setText(list.get(4));
        avoid.setText(list.get(5));
        attention.setText(list.get(6));
        validity_time.setText(list.get(7));
        create_time.setText(list.get(8));

    }

    private void initView() {
        name = (TextView) findViewById(R.id.medicine_info_name);
        common_name = (TextView) findViewById(R.id.medicine_info_commmon_name);
        indication = (TextView) findViewById(R.id.medicine_info_indication);
        usage = (TextView) findViewById(R.id.medicine_info_usage);
        side_affects = (TextView) findViewById(R.id.medicine_info_side_affects);
        avoid = (TextView) findViewById(R.id.medicine_info_avoid);
        attention = (TextView) findViewById(R.id.medicine_info_attention);
        validity_time = (TextView) findViewById(R.id.medicine_info_validity_time);
        create_time = (TextView) findViewById(R.id.medicine_info_create_time);
    }
}
