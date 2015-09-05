package com.outstudio.bosomcode.center;


import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.camera.CameraManager;
import com.outstudio.bosomcode.decoding.CaptureActivityHandler;
import com.outstudio.bosomcode.decoding.InactivityTimer;
import com.outstudio.bosomcode.network.VolleyInterface;
import com.outstudio.bosomcode.network.VolleyRequest;
import com.outstudio.bosomcode.right.MedicationPrompts;
import com.outstudio.bosomcode.view.FindView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by huahu on 2015/8/10.
 */
public class QRScanActivity extends Activity implements Callback {

    private CaptureActivityHandler handler;
    private FindView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private Button cancelbutton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        // init CameraManager
        CameraManager.init(getApplication());
        viewfinderView = (FindView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        cancelbutton = (Button) findViewById(R.id.btn_cancel_scan);
        cancelbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //System.exit(0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(QRScanActivity.this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public FindView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * 解析二维码
     *
     * @param obj
     * @param barcode
     */
    public void handleDecode(final Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String url = obj.getText();
        Log.e("url", url);
        VolleyRequest.RequestGet(url, "content_get",
                new VolleyInterface(this, VolleyInterface.mListener,
                        VolleyInterface.mErrorListener) {
                    @Override
                    public void onMySuccess(JSONObject jsonObject) {

                        Log.e("json", jsonObject.toString());
                        try {

                            int ad = jsonObject.getInt("ad");
                            /**
                             * 厂家
                             */
                            if (ad == 2) {
                                ArrayList<String> list = new ArrayList<>();
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                list.add(name);  //0
                                String common_name = jsonObject
                                        .getString("common_name");
                                list.add(common_name);  //1
                                /*没有药品类别*/
                                String component = jsonObject
                                        .getString("component");
                                String properties = jsonObject
                                        .getString("properties");
                                String function_cate = jsonObject
                                        .getString("function_cate");
                                String indication = jsonObject
                                        .getString("indication");
                                list.add(indication);  //2
                                String spec = jsonObject.getString("spec");
                                String usage = jsonObject.getString("usage");
                                list.add(usage);  //3
                                String side_effect = jsonObject
                                        .getString("side_effect");
                                list.add(side_effect);  //4
                                String avoid = jsonObject.getString("avoid");
                                list.add(avoid);  //5
                                String attention = jsonObject
                                        .getString("attention");
                                list.add(attention);  //6
                                String interaction = jsonObject
                                        .getString("interaction");
                                String depot = jsonObject.getString("depot");
                                String validity = jsonObject.getString("validity");
                                list.add(validity);  //7
                                String createTime = jsonObject
                                        .getString("creatime");
                                list.add(createTime);  ///8
                                Intent intent = new Intent(QRScanActivity.this,
                                        MedicineInfo.class);
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("bundle_array1", list);
                                intent.putExtra("intent", bundle);
                                startActivity(intent);
                                QRScanActivity.this.finish();
                                /**
                                 * 医嘱
                                 */
                            } else if (ad == 1) {
                                ArrayList<String> list = new ArrayList<>();
                                ArrayList<String> times = new ArrayList<String>();
                                Log.e("ad==1", "执行了");
                                String id = jsonObject.getString("id");
                                String doc_name = jsonObject.getString("doc_name");
                                list.add(doc_name);  //list 0
                                String patient = jsonObject.getString("patient"); //病人姓名
                                String tel = jsonObject.getString("tel"); //医生电话
                                list.add(doc_name);  //list 1
                                String desc = jsonObject.getString("desc");//病情描述
                                String eat_1 = jsonObject.getString("eat_1");//用药时间
                                times.add(eat_1); //times 0
                                String eat_2 = jsonObject.getString("eat_2");
                                times.add(eat_2); //times 1
                                String eat_3 = jsonObject.getString("eat_3");
                                times.add(eat_3); //times 2
                                String qrcode = jsonObject.getString("qrcode");//二维码图片名称
                                String createtime = jsonObject.getString("createtime");//医嘱创建时间
                                JSONArray drugArray = jsonObject.getJSONArray("drugs");

                                ArrayList<String> names = new ArrayList<String>();
                                ArrayList<String> nums = new ArrayList<String>();
                                for (int i = 0; i < drugArray.length(); i++) {
                                    JSONObject contentObject = drugArray.getJSONObject(i);
//                                    ids.add(contentObject.getString("id"));
                                    names.add(contentObject.getString("name"));
                                    nums.add(contentObject.getString("num"));

                                    list.add(contentObject.getString("name"));
                                    list.add(contentObject.getString("num"));
                                }
                                Intent intent = new Intent(QRScanActivity.this,
                                        MedicineInfoDoctor.class);
                                Bundle bundle = new Bundle();
                                //其他数据链表
                                bundle.putStringArrayList("bundle_array1", list);
                                //药品名称链表
                                bundle.putStringArrayList("bundle_array2", names);
                                // 药品数量链表
                                bundle.putStringArrayList("bundle_array3", nums);
                                //时间链表
                                bundle.putStringArrayList("bundle_array4", times);
                                intent.putExtra("intent", bundle);
                                startActivity(intent);
                                QRScanActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onMyError(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                });
    }

    private String Analysis() {
        return null;
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {

        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }

        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
}
