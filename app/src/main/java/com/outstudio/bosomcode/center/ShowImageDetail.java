package com.outstudio.bosomcode.center;

import android.view.MotionEvent;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.outstudio.bosomcode.R;

/**
 * 图片细节查看类
 *
 * @author Shi Wei Xian
 */
public class ShowImageDetail extends Activity implements OnGestureListener {
    private AdapterViewFlipper flipper = null;
    private FlipperAdapter adapter = null;
    WindowManager wm = null;
    private int ScreenWidth = 0;
    private int ScreenHeight = 0;
    private static int tempPosition = 0;
    // 定义手势检测器实例
    GestureDetector detector = null;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.share_thought_image_flipper);
        wm = this.getWindowManager();
        ScreenWidth = wm.getDefaultDisplay().getWidth();
        ScreenHeight = wm.getDefaultDisplay().getHeight();
        Log.d("屏幕宽度", ScreenWidth + "");
        Log.d("屏幕高度", ScreenHeight + "");
        for (int i = 0; i < ShareThought.list.size(); i++) {
            Log.d("list", ShareThought.list.get(i));
        }
        tempPosition = getIntent().getIntExtra(ShareThought.mark, 0);
        Log.d("tempPosition", tempPosition + "");
        adapter = new FlipperAdapter(this, ShareThought.list, ScreenWidth,
                ScreenHeight);
        flipper = (AdapterViewFlipper) findViewById(R.id.flipper);
        flipper.setAdapter(adapter);
        // 创建手势检测器
        detector = new GestureDetector(this, this);
    }

    private class FlipperAdapter extends BaseAdapter {
        private Context context = null;
        private LinearLayout layout = null;
        private List<String> list = null;
        private int ScreenWidth = 0;
        private int ScreenHeight = 0;
        private int tempPosition = ShowImageDetail.tempPosition;

        // 构造方法
        public FlipperAdapter(Context context, List<String> list,
                              int ScreenWidth, int ScreenHeight) {
            this.context = context;
            this.list = list;
            this.ScreenWidth = ScreenWidth;
            this.ScreenHeight = ScreenHeight;
        }

        // 获取list里的元素个数
        @Override
        public int getCount() {
            return list.size();
        }

        // 获取list里的元素
        @Override
        public Object getItem(int position) {
            return list.get(tempPosition);
        }

        // 获取list里的元素位置
        @Override
        public long getItemId(int position) {
            return tempPosition;
        }

        // 获取list里元素对应的视图
        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            tempPosition = ShowImageDetail.tempPosition;
            LayoutInflater inflater = LayoutInflater.from(context);
            layout = (LinearLayout) inflater.inflate(
                    R.layout.share_thought_show_image_detail, null);
            ImageView imgiv = (ImageView) layout
                    .findViewById(R.id.image_detail);
            Log.d("getView", tempPosition + "");
            Log.d("getView", list.get(tempPosition));
            imgiv.setImageBitmap(getImageThumbnail(list.get(tempPosition)));
            return layout;
        }

        /**
         * 获取图片缩略图
         *
         * @param uri
         * @param
         * @param
         * @return
         */
        public Bitmap getImageThumbnail(String uri) {
            Bitmap bitmap;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Log.w("ViewFlipperAdapter", uri);
            bitmap = BitmapFactory.decodeFile(uri, options);
            int width = ScreenWidth;
            int height = ScreenHeight;

            options.inJustDecodeBounds = false;
            int beWidth = options.outWidth / width;
            int beHeight = options.outHeight / height;
            int be = 1;
            if (beWidth < beHeight) {
                be = beWidth;
            } else {
                be = beHeight;
            }
            if (be <= 0) {
                be = 1;
            }
            options.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(uri, options);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            return bitmap;
        }
    }

    /**
     * 将该Activity上的触碰事件交给GestureDetector处理
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("onDown", "onDown");
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
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        Log.d("onFling", "onFling");
        float minXGap = 50;
        float preX = e1.getX();
        float currentX = e2.getX();
        if ((currentX - preX) > minXGap) {
            tempPosition -= 1;
            if (tempPosition < 0) {
                tempPosition = ShareThought.list.size() - 1;
            }
            Log.d("preBt", tempPosition + "");
            flipper.showPrevious();

        } else if ((preX - currentX) > minXGap) {
            tempPosition += 1;
            if (tempPosition >= ShareThought.list.size()) {
                tempPosition = 0;
            }
            Log.d("nexBt", tempPosition + "");
            flipper.showNext();
        }
        return true;
    }

}

