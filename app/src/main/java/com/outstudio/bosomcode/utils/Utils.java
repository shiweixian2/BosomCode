package com.outstudio.bosomcode.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mima123 on 15/8/7.
 */
public class Utils {

    /**
     * 获取当前日期
     * @return 格式化后的日期
     */
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 E");
        String date = dateFormat.format(new Date());
        return date;
    }
}
