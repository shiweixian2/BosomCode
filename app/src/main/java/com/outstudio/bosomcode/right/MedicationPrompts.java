package com.outstudio.bosomcode.right;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.outstudio.bosomcode.R;

/**
 * Created by mima123 on 15/8/12.
 */
public class MedicationPrompts extends Activity {

    private TextView promptsTextView = null;
    private TextView nameTextView = null;
    private TextView phoneTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_prompts);
        promptsTextView = (TextView) findViewById(R.id.medication_prompts_textView);
        nameTextView = (TextView) findViewById(R.id.medication_prompts_doctor_name);
        phoneTextView = (TextView) findViewById(R.id.medication_prompts_doctor_phone);

    }

    private void laadData(){

    }
}
