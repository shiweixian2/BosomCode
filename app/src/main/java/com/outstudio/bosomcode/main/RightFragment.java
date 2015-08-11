package com.outstudio.bosomcode.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.right.AddRemind;
import com.outstudio.bosomcode.utils.ListBtAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mima123 on 15/8/6.
 */
public class RightFragment extends ListFragment implements View.OnClickListener {

    private ViewFlipper flipper = null;
    private Button addRemindBt = null;
    private ListView listView = null;
    private int[] imageId = {R.drawable.right_ftagment_medicine_info,
            R.drawable.right_ftagment_medicine_info, R.drawable.right_ftagment_medicine_info,
            R.drawable.right_ftagment_medicine_info};

    //listView的数据
    private int[] icons = {R.drawable.right_fragment_morning_clock,
            R.drawable.right_fragment_afternooon_clock, R.drawable.right_fragment_night_clock};
    private String[] time = {"08:00", "13:00", "19:00"};
    private String[] beforeOrAfter = {"饭后", "饭后", "饭后"};

    private String ICON_FLAG = "icon_flag";
    private String TIME_1_FLAG = "time_1_flag";
    private String MEAL_FLAG = "meal_flag";
    private String BT_FLAG = "bt_flag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.right_fragment, container, false);
        initView(view);
        return view;

    }

    /**
     * 初始化界面组件
     *
     * @param view
     */
    private void initView(View view) {
        flipper = (ViewFlipper) view.findViewById(R.id.right_fragment_flipper);
        addRemindBt = (Button) view.findViewById(R.id.right_fragment_add_remind_bt);
        listView = (ListView) view.findViewById(android.R.id.list);
        /**
         * 动态导入的方式为viewFlipper加入子view
         */
        for (int i = 0; i < imageId.length; i++) {
            flipper.addView(getFlipperImageView(imageId[i]));
        }
        //为ViewFlipper设置动画效果
        flipper.setInAnimation(getActivity(), R.anim.from_right_in);
        flipper.setOutAnimation(getActivity(), R.anim.to_left_out);
        //设置ViewFlipper视图切换的时间间隔
        flipper.setFlipInterval(3000);
        //开始播放
        flipper.startFlipping();
        flipper.setOnClickListener(this);
        setListViewAdapter(listView);
        addRemindBt.setOnClickListener(this);
    }

    /**
     * 获得ImageView
     *
     * @param imageId
     * @return
     */
    private ImageView getFlipperImageView(int imageId) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(imageId);
        return imageView;
    }

    /**
     * 获取listView的数据
     *
     * @return
     */
    private ArrayList<HashMap<String, Object>> getListViewData() {
        ArrayList<HashMap<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < time.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(ICON_FLAG, icons[i]);
            map.put(TIME_1_FLAG, time[i]);
            map.put(MEAL_FLAG, beforeOrAfter[i]);
            map.put(BT_FLAG, R.drawable.right_fragment_edit_icon);
            listItems.add(map);
        }
        return listItems;
    }

    /**
     * 为listView设置适配器
     */
    private void setListViewAdapter(ListView listView) {
        //生成适配器的Item和动态数组对应的元素
        ListBtAdapter listBtAdapter = new ListBtAdapter(getActivity(), getListViewData(),
                R.layout.for_right_fragment_listview, new String[]{ICON_FLAG, TIME_1_FLAG,
                MEAL_FLAG, BT_FLAG}, new int[]{R.id.right_fragment_clock, R.id.right_fragment_time,
                R.id.right_fragment_meal, R.id.right_fragment_edit_bt});
        listView.setAdapter(listBtAdapter);
    }

    /**
     *listView监听事件
     * @param
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        l.getItemAtPosition(position);
        //在这里书写listView的点击事件
        Log.e("main", "" + position);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.right_fragment_add_remind_bt:
                intent = new Intent(getActivity(), AddRemind.class);
                startActivity(intent);
                break;
        }
    }
}
