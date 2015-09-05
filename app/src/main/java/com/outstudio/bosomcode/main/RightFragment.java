package com.outstudio.bosomcode.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.right.AddRemind;
import com.outstudio.bosomcode.right.MedicationPrompts;
import com.outstudio.bosomcode.right.Remind;
import com.outstudio.bosomcode.utils.ListBtAdapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mima123 on 15/8/6.
 */
public class RightFragment extends ListFragment implements View.OnClickListener
{
    public static final String SHARE_NAME = "REMIND";
    public static final String SHARE_STRING_NAME = "STRING";

    private ViewFlipper flipper = null;
    private Button addRemindBt = null;
    private ListView listView = null;
    private int[] imageId = {R.drawable.right_ftagment_medicine_info,
            R.drawable.right_ftagment_medicine_info, R.drawable.right_ftagment_medicine_info,
            R.drawable.right_ftagment_medicine_info};
    private String ICON_FLAG = "icon_flag";
    private String TIME_FLAG = "time_1_flag";
    private String MEAL_FLAG = "meal_flag";
    private String BT_FLAG = "bt_flag";

    private ArrayList<Remind> remindList = null;

    public static final String BUNDLE_KEY = "right_bundle_key";
    public static final String PROMPTS_KEY = "right_prompts_key";

    ListBtAdapter listBtAdapter = null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.right_fragment, container, false);
        initList();
        initView(view);
        return view;
    }

    private void initList()
    {
        remindList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String test = sharedPreferences.getString(SHARE_STRING_NAME, "");
        remindList = Remind.String2RemindList(test);
        //Log.e("the one", "" + remindList.get(0));

    }

    private void initView(View view)
    {
        flipper = (ViewFlipper) view.findViewById(R.id.right_fragment_flipper);
        addRemindBt = (Button) view.findViewById(R.id.right_fragment_add_remind_bt);
        listView = (ListView) view.findViewById(android.R.id.list);
        /**
         * 动态导入的方式为viewFlipper加入子view
         */
        for (int i = 0; i < imageId.length; i++)
        {
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
        addRemindBt.setOnClickListener(this);
        setListViewAdapter(listView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.e("Remind", "onActivityResult ");
        if (data == null)
            return;
        Bundle bundle = data.getExtras();
        Remind remind = null;
        if (bundle != null)
        {
            remind = new Remind(bundle.getInt(AddRemind.KEY_HOUR), bundle.getInt(AddRemind.KEY_MINUTE), bundle.getBoolean(AddRemind.KEY_ENABLED));
        }
        if (remind != null)
        {
            remindList.add(remind);
            String listString = Remind.RemindList2String(remindList);
            editor.putString(SHARE_STRING_NAME, listString);
            editor.commit();
        }
        setListViewAdapter(listView);
        Log.e("remindListSize in onAcR", "" + remindList.size());
    }

    /**
     * 获取listView的数据
     *
     * @return
     */
    private ArrayList<HashMap<String, Object>> getListViewData()
    {
        ArrayList<HashMap<String, Object>> listItems = new ArrayList<>();
        Log.e("remindListSize", "" + remindList.size());
        for (int i = 0; i < remindList.size(); i++)
        {
            HashMap<String, Object> map = new HashMap<>();
            String time = remindList.get(i).toString();
            map.put(TIME_FLAG, time);
            //根据不同时间段设置不同颜色的闹钟
            if (6 <= remindList.get(i).getHour() && remindList.get(i).getHour() < 12)
            {
                map.put(ICON_FLAG, R.drawable.right_fragment_morning_clock);
            } else if (remindList.get(i).getHour() > 12 && remindList.get(i).getHour() < 18)
            {
                map.put(ICON_FLAG, R.drawable.right_fragment_afternooon_clock);
            } else
            {
                map.put(ICON_FLAG, R.drawable.right_fragment_night_clock);
            }
            map.put(BT_FLAG, R.drawable.right_fragment_edit_icon);
            listItems.add(map);
        }
        Log.e("getListViewData", "" + listItems.size());
        return listItems;
    }

    /**
     * 为listView设置适配器
     */
    private void setListViewAdapter(ListView listView)
    {
        //生成适配器的Item和动态数组对应的元素
        listBtAdapter = new ListBtAdapter(getActivity(), getListViewData(),
                R.layout.for_right_fragment_listview, new String[]{ICON_FLAG, TIME_FLAG,
                MEAL_FLAG, BT_FLAG}, new int[]{R.id.right_fragment_clock, R.id.right_fragment_time,
                R.id.right_fragment_meal, R.id.right_fragment_edit_bt});
        listView.setAdapter(listBtAdapter);
    }

    /**
     * 获得ImageView
     *
     * @param imageId
     * @return
     */
    private ImageView getFlipperImageView(int imageId)
    {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(imageId);
        return imageView;
    }

    /**
     * listView监听事件
     *
     * @param
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        l.getItemAtPosition(position);
        //在这里书写listView的点击事件
        Log.e("main", "" + position);
        Intent tipIntent = new Intent(getActivity(), MedicationPrompts.class);
        Bundle bundle = new Bundle();
        //bundle.putString(PROMPTS_KEY, contentList.get(position));
        tipIntent.putExtra(BUNDLE_KEY, bundle);
        startActivity(tipIntent);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent;
        switch (v.getId())
        {
            case R.id.right_fragment_add_remind_bt:
                intent = new Intent(getActivity(), AddRemind.class);
                startActivityForResult(intent, 2);
                break;
        }
    }
}
