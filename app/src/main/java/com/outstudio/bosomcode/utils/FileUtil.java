package com.outstudio.bosomcode.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by mima123 on 15/8/11.
 */
public class FileUtil {
    /**
     * 单例模式
     */
    private FileUtil() {
    }

    private static FileUtil fileUtil = new FileUtil();

    /**
     * 唯一可以获得该实例的方法
     *
     * @return
     */
    public static FileUtil getInstance() {
        return fileUtil;
    }

    public static final String rootDirectory = Environment
            .getExternalStorageDirectory().toString() + "/BosomCode/";

    public static final String ALARM_FILE_PATH = rootDirectory + "AddRemind/FilePath/";
    public static final String ALARM_CONTENT_FILE_PATH = ALARM_FILE_PATH + "CONTENT";
    public static final String ALARM_HOUR_FILE_PATH = ALARM_FILE_PATH + "HOUR";
    public static final String ALARM_MINUTE_FILE_PATH = ALARM_FILE_PATH + "MINUTE";
    public static final String ALARM_MEAL_FILE_PATH = ALARM_FILE_PATH + "MEAL";
    public static final String ALARM_TEST_FILE_PATH = ALARM_FILE_PATH + "TEST";

    /**
     * 创建文件夹并检查文件夹是否存在 以及读取的权限
     */
    public void makeDir(File destDir) {

        // 创建文件夹
        if (!destDir.exists()) {
            destDir.mkdirs();
            Log.d("文件检查", "正在创建文件夹");
        } else {
            Log.d("文件检查", "文件夹存在");
        }
        if (destDir.canRead()) {
            Log.d("文件检查", "文件夹可以读");
        }
        if (destDir.canWrite()) {
            Log.d("文件检查", "文件夹可以写");
            Log.d("文件检查", destDir.getAbsolutePath());
        } else {
            destDir.setWritable(true);
        }
    }


}
