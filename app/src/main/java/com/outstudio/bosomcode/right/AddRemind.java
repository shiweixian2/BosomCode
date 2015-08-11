package com.outstudio.bosomcode.right;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.utils.FileUtil;
import com.outstudio.bosomcode.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
//    private EditText amountEidt1 = null;
//    private Button addMedicineBt = null;
    //文件夹名称
    private static final String directoryName = FileUtil.rootDirectory + "AddRemind" + File.separator;
    private File destDir = new File(directoryName);

    public static final int REQUREST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remind);
        contentEdit = (EditText) findViewById(R.id.add_remind_edit);
        setTimebt = (Button) findViewById(R.id.add_remind_time_bt);
        calcelBt = (ImageButton) findViewById(R.id.add_remind_cancel_bt);
        confirmBt = (ImageButton) findViewById(R.id.add_remind_confirm_bt);
        timeText = (TextView) findViewById(R.id.add_remind_time);
//        amountEidt1 = (EditText) findViewById(R.id.add_remind_amount_edit);
//        addMedicineBt = (Button) findViewById(R.id.add_remind_add_medicine_bt);
        timeText.setText(Utils.getTime());
        setTimebt.setOnClickListener(this);
        calcelBt.setOnClickListener(this);
        confirmBt.setOnClickListener(this);
//        amountEidt1.setOnClickListener(this);
//        addMedicineBt.setOnClickListener(this);
    }

    /**
     * 处理用药方面信息的保存
     */
    private void manageFile() {
        FileUtil.getInstance().makeDir(destDir);
        File file = new File(destDir.getAbsolutePath()+Utils.getDirDate());
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
    private void setAlarmClock(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.add_remind_time_bt:
                intent = new Intent(AddRemind.this,SetTime.class);
                startActivityForResult(intent, REQUREST_CODE);
                break;
            case R.id.add_remind_cancel_bt:
                this.finish();
                break;
            case R.id.add_remind_confirm_bt:
                manageFile();
                this.finish();
                break;
//            case R.id.add_remind_add_medicine_bt:
//                addLayout();
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUREST_CODE){

        }
    }


    //    /**
//     * 添加一个LinearLayout布局
//     */
//    private void addLayout() {
//        LinearLayout linearLayout = new LinearLayout(this);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        linearLayout.setLayoutParams(layoutParams);
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        EditText editText = new EditText(this);
//        editText.setId(generateContentEditId());
//        editText.setWidth(0);
//        editText.
//    }
//
//    private int generateContentEditId() {
//        for (; ; ) {
//            final int result = amountEidt1.getId();
//            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
//            int newValue = result + 1;
//            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
//            return newValue;
//        }
//    }
//    private int generateAmountEditId() {
//        for (; ; ) {
//            final int result = amountEidt1.getId();
//            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
//            int newValue = result + 1;
//            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
//            return newValue;
//        }
//    }
}
