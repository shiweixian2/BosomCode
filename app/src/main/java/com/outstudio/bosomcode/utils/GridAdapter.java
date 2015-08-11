package com.outstudio.bosomcode.utils;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.outstudio.bosomcode.R;

/**
 * Created by shiweixian on 2015/5/27.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private LinearLayout layout;
    private List<String> list;
    private int imageWidth = 0;
    private int imageHeight = 0;

    public GridAdapter(Context context, List<String> list , int imageWidth, int imageHeight) {
        this.context = context;
        this.list = list;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({ "ViewHolder", "InflateParams" })
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (LinearLayout) inflater.inflate(R.layout.share_thought_imagethumb,
                null);
        ImageView imgiv = (ImageView) layout
                .findViewById(R.id.show_image_thumb);
        imgiv.setImageBitmap(getImageThumbnail(list.get(position), imageWidth, imageHeight));
        return layout;
    }

    /**
     * 获取图片缩略图
     *
     * @param uri
     * @param width
     * @param height
     * @return
     */
    public Bitmap getImageThumbnail(String uri, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Log.w("myadapter", uri);
        bitmap = BitmapFactory.decodeFile(uri, options);
        Log.w("myadapter", "可以吗");
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