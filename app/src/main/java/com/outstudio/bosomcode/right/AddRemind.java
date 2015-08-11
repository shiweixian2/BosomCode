package com.outstudio.bosomcode.right;

import android.app.Activity;
import android.app.TimePickerDialog;
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
import java.util.Calendar;

/**
 * Created by mima123 on 15/8/10.
 */
public class AddRemind extends Activity implements View.OnClickListener {

    private Button setTimebt = null;
    private ImageButton calcelBt = null;
    private ImageButton confirmBt = null;
    private TextView timeText = null;
    private EditText contentEdit1 = null;
//    private EditText amountEidt1 = null;
//    private Button addMedicineBt = null;
    //文件夹名称
    private static final String directoryName = FileUtil.rootDirectory + "AddRemind" + File.separator;
    private File destDir = new File(directoryName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_remind);
        contentEdit1 = (EditText) findViewById(R.id.add_remind_edit);
        setTimebt = (Button) findViewById(R.id.add_remind_time_bt);
        calcelBt = (ImageButton) findViewById(R.id.add_remind_cancel_bt);
        confirmBt = (ImageButton) findViewById(R.id.add_remind_confirm_bt);
        timeText = (TextView) findViewById(R.id.add_remind_time);
//        amountEidt1 = (EditText) findViewById(R.id.add_remind_amount_edit);
//        addMedicineBt = (Button) findViewById(R.id.add_remind_add_medicine_bt);
        timeText.setText(Utils.getTime());
        contentEdit1.setSelection(0);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_remind_time_bt:
                showTimeDialog();
                break;
            case R.id.add_remind_cancel_bt:
                break;
            case R.id.add_remind_confirm_bt:
                break;
//            case R.id.add_remind_add_medicine_bt:
//                addLayout();
//                break;
        }
    }


    /**
     * 设置时间的对话框
     */
    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(AddRemind.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeText.setText(hourOfDay + ":" + minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
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
