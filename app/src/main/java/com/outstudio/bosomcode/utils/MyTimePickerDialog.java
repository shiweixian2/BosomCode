package com.outstudio.bosomcode.utils;

import android.app.TimePickerDialog;
import android.content.Context;

/**
 * Created by mima123 on 15/8/11.
 */
public class MyTimePickerDialog extends TimePickerDialog {

    public MyTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute, is24HourView);
    }

    public MyTimePickerDialog(Context context, int theme, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
        super(context, theme, callBack, hourOfDay, minute, is24HourView);
    }

    @Override
    protected void onStop() {

    }
}
