package com.outstudio.bosomcode.center;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.utils.FileUtil;
import com.outstudio.bosomcode.utils.GridAdapter;
import com.outstudio.bosomcode.utils.PictureHelper;

/**
 * 签到类
 *
 * @author Shi Wei Xian
 */
public class ShareThought extends Activity implements OnClickListener {

    private GridView gridView = null;
    private EditText signText = null;
    private Button submitBt = null;
    private ImageButton takePhotoBt = null;
    private ImageButton loadLocalPhotoBt = null;
    // 当前的时间
    private static String mTime = null;
    // 定义文件夹路径
    private static final String directoryName = FileUtil.rootDirectory + "ShareThought";
    private File destDir = new File(directoryName);
    private File mImageFile = null;
    // 定义使用系统相机拍照的请求值
    private static final int photoFlag = 1;
    // 定义从图库获取图片的请求值
    private static final int RESULT_LOAD_IMAGE = 2;
    public static String uri = null;
    // 创建一个链表，存储缩略图
    public static List<String> list = null;
    // 声明自定义adapter
    public static GridAdapter adapter = null;
    // 定义缩略图的宽高
    private static int imageWidth = 180;
    private static int imageHeight = 180;
    public static final String mark = "position";
    private WindowManager wm = null;
    public static int ScreenWidth = 0;
    public static int ScreenHeight = 0;
    private int deletePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_thought); // //键盘遮挡在Man中设置
        FileUtil.getInstance().makeDir(destDir);
        wm = this.getWindowManager();
        ScreenWidth = wm.getDefaultDisplay().getWidth();
        ScreenHeight = wm.getDefaultDisplay().getHeight();
        imageWidth = ScreenWidth / 3 - 60;
        imageHeight = imageWidth;
        list = new ArrayList<>();
        // 实例化GridView
        gridView = (GridView) findViewById(R.id.gridView_check_on);
        // 实例化EditText
        signText = (EditText) findViewById(R.id.share_thought_edit);
        /*
         * 实例化Button
		 */
        // 提交按钮
        submitBt = (Button) findViewById(R.id.share_thought_share_bt);
        // 拍照按钮
        takePhotoBt = (ImageButton) findViewById(R.id.share_thought_camera);
        // 从本地获取图片按钮
        loadLocalPhotoBt = (ImageButton) findViewById(R.id.share_thought_load_local_photo);
        // 为按钮添加监听器
        submitBt.setOnClickListener(this);
        takePhotoBt.setOnClickListener(this);
        loadLocalPhotoBt.setOnClickListener(this);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("position", position + "");
                Intent intent = new Intent(ShareThought.this,
                        ShowImageDetail.class);
                intent.putExtra(mark, position);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, 0);
            }
        });

        gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                deletePosition = position;
                showDialog();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUri() != null) {
            adapter = new GridAdapter(ShareThought.this, getUri(), imageWidth,
                    imageHeight);
            gridView.setAdapter(adapter);
        }
    }

    /**
     * 加载缩略图至SimpleAdapter上，并为girdView设置adapter
     */
    private List<String> getUri() {

        return list;
    }

    /**
     * 将图片的路径加到list中
     *
     * @param uri 图片的本地路径
     */
    public void addUri(String uri) {
        list.add(uri);
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("删除记录")
                .setMessage("您是否要删除本条记录?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUri(deletePosition);
                        adapter = new GridAdapter(ShareThought.this, getUri(),
                                imageWidth, imageHeight);
                        gridView.setAdapter(adapter);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    public void deleteUri(int position) {
        String tempUri = list.get(position);
        if (tempUri.contains(directoryName)) {
            File file = new File(tempUri);
            file.delete();
        }
        list.remove(position);
    }


    /**
     * 设置按钮的点击事件
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_thought_share_bt:
                if (signText.getText().toString().equals("")) {
                    Toast.makeText(this, "分享内容不能为空。", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case R.id.share_thought_camera:
                useCamera();
                break;
            case R.id.share_thought_load_local_photo:
                Intent pictureIntent;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    pictureIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // 4.4推荐用此方式，4.4以下的API需要再兼容
                    pictureIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    pictureIntent.setType("image/*");
                } else {
                    pictureIntent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }

                startActivityForResult(pictureIntent, RESULT_LOAD_IMAGE);
                break;
            default:
                break;
        }
    }

    /**
     * 使用系统的相机进行拍照
     */
    private void useCamera() {
        // 跳转到系统的相机,拍照
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指明文件的存储位置
        mTime = getTime();
        mImageFile = new File(destDir.getAbsoluteFile() + "/" + mTime + ".png");
        uri = null;
        uri = mImageFile.getAbsolutePath();
        Log.d("AddContents", mImageFile.getName());
        Log.d("useCamera", uri);
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImageFile));
        startActivityForResult(imageIntent, photoFlag);
    }

    /**
     * 获取系统的当前时间并对其进行格式化
     *
     * @return 格式化后的时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = format.format(date);
        return time;
    }

    /**
     * 监听相机返回的事件
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == photoFlag && resultCode == RESULT_OK) {
            Log.d("takephoto", uri);
            addUri(uri);
            adapter = new GridAdapter(ShareThought.this, getUri(), imageWidth,
                    imageHeight);
            gridView.setAdapter(adapter);
        } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && data != null) {
            Log.e("本地图库", resultCode + "");
            String picturePath = "";
            // 对于4.4及以上系统
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                Uri selectedImage = data.getData();
                picturePath = PictureHelper.getPath(this, selectedImage); // 获取图片的绝对路径
                // 小于4.4的系统
            } else {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
            }
            addUri(picturePath);
            Log.e("图片路径", picturePath);
            adapter = new GridAdapter(ShareThought.this, getUri(), imageWidth,
                    imageHeight);
            gridView.setAdapter(adapter);
        }
    }

}