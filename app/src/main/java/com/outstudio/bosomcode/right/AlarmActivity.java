package com.outstudio.bosomcode.right;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.outstudio.bosomcode.R;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mima123 on 15/8/12.
 */
public class AlarmActivity extends Activity implements GestureDetector.OnGestureListener {

    MediaPlayer mediaPlayer;
    GestureDetector detector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        //创建手势监测器
        detector = new GestureDetector(this,this);
        playMusic();
    }

    /**
     * 播放音乐
     */
    private void playMusic(){
        mediaPlayer = MediaPlayer.create(this,R.raw.music);
        mediaPlayer.setLooping(true);
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }


    /**
     * 结束音乐
     * 并结束该Activity
     */
    private void stopMusic(){
        mediaPlayer.stop();
        AlarmActivity.this.finish();
    }

    /**
     * 将Activity上的触碰事件交给GestureDetector处理
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float y1 = e1.getY();
        float y2 = e2.getY();
        if (y1 - y2 >30){
            stopMusic();
        }
        return true;
    }
}
