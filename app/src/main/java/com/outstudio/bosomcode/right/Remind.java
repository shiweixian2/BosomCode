package com.outstudio.bosomcode.right;

import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/26.
 */
public class Remind implements Serializable
{

    private int hour;
    private int minutes;
    private boolean enabled;

    public Remind()
    {
        this.minutes = 0;
        this.hour = 0;
        this.enabled = false;
    }

    public Remind(int hour, int minutes, boolean enabled)
    {
        this.enabled = enabled;
        this.hour = hour;
        this.minutes = minutes;
    }

    public int getHour()
    {
        return hour;
    }

    public void setHour(int hour)
    {
        this.hour = hour;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public void setMinutes(int minutes)
    {
        this.minutes = minutes;
    }

    public boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String toString()
    {
        String hourStr;
        String minuteStr;
        //为了让数据更好看
        if (hour < 10)
            hourStr = "0" + hour;
        else
            hourStr = "" + hour;
        if (minutes < 10)
            minuteStr = "0" + minutes;
        else
            minuteStr = "" + minutes;
        return hourStr + ":" + minuteStr;
    }

    public static String RemindList2String(List<Remind> list)
    {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try
        {
            // 然后将得到的字符数据装载到ObjectOutputStream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    byteArrayOutputStream);
            // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
            for (int i = 0; i < list.size(); i++)
            {
                objectOutputStream.writeObject(list.get(i));
            }

            objectOutputStream.writeObject(null);
            // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
            String WeatherListString = new String(Base64.encode(
                    byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            // 关闭objectOutputStream
            objectOutputStream.close();
            return WeatherListString;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;

    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Remind> String2RemindList(String remindListString)
    {
        ArrayList<Remind> list = new ArrayList<Remind>();
        Remind remind;
        byte[] mobileBytes = Base64.decode(remindListString.getBytes(),
                Base64.DEFAULT);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = null;
        try
        {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);

            while (( remind =(Remind)objectInputStream.readObject()) != null)
            {
                list.add(remind);
            }

            objectInputStream.close();
            return list;
        } catch (ClassNotFoundException e)
        {
            Log.e("Exception", "class not found");
            e.printStackTrace();
        } catch (IOException e)
        {
            Log.e("Exception", e.toString());
        }
        return list;
    }
}
