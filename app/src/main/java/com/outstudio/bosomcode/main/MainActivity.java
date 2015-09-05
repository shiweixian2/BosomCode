package com.outstudio.bosomcode.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.center.SetActivity;
import com.outstudio.bosomcode.slideMenu.MedicalHistory;
import com.outstudio.bosomcode.slideMenu.MedicineAvoid;
import com.outstudio.bosomcode.utils.BottomTab;
import com.outstudio.bosomcode.utils.MyScrollView;
import com.outstudio.bosomcode.utils.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity
        implements View.OnClickListener, ViewPager.OnPageChangeListener
{

    //listView数据标签
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    //数据源
    private int[] text = {R.string.slide_menu_collection, R.string.slide_menu_medical_history,
            R.string.slide_menu_medicine_avoid,
            R.string.slide_menu_my_doctor, R.string.slide_menu_keep_health, R.string.slide_menu_cure_disease,
            R.string.slide_menu_setting
    };
    private int[] icon = {R.drawable.user, R.drawable.user, R.drawable.user, R.drawable.user,
            R.drawable.user, R.drawable.user, R.drawable.user};

    //界面组件
    private MyScrollView mSwitchMenu = null;
    private ListView menuListView = null;

    private ViewPager mViewpager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;

    private List<BottomTab> mTabIndicators = new ArrayList<BottomTab>();
    private String date;
    private TextView dateText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.for_slide_menu_listview,
                new String[]{IMAGE, TEXT},
                new int[]{R.id.slide_menu_list_icon, R.id.slide_menu_list_text});
        menuListView.setAdapter(adapter);

        initDatas();
        mViewpager.setAdapter(mAdapter);
        mTabIndicators.get(0).setIconAlpha(0);
        mTabIndicators.get(1).setIconAlpha(1.0f);
        //第二个参数为true有动画效果
        mViewpager.setCurrentItem(1, false);
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            Intent intent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                /*if (position == 1){
                    intent = new Intent(MainActivity.this, MedicalHistory.class);
                    startActivity(intent);
                }else if (position == 2) {
                    intent = new Intent(MainActivity.this, MedicineAvoid.class);
                    startActivity(intent);
                }*/
                switch (position)
                {
                    case 1:
                        intent = new Intent(MainActivity.this, MedicalHistory.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, MedicineAvoid.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, SetActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    /**
     * 初始化界面组件
     */
    private void initView()
    {
        mSwitchMenu = (MyScrollView) findViewById(R.id.slide_menu);
        menuListView = (ListView) findViewById(R.id.slide_menu_listView);
        dateText = (TextView) findViewById(R.id.date_textView);
        date = DateUtil.getDate();
        dateText.setText(date);
        mViewpager = (ViewPager) findViewById(R.id.main_tab_viewpager);
        BottomTab one = (BottomTab) findViewById(R.id.main_tab_one);
        mTabIndicators.add(one);
        BottomTab two = (BottomTab) findViewById(R.id.main_tab_two);
        mTabIndicators.add(two);
        BottomTab three = (BottomTab) findViewById(R.id.main_tab_three);
        mTabIndicators.add(three);
        mViewpager.setOnPageChangeListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        one.setIconAlpha(1.0f);
    }

    /**
     * 初始化数据,跳转到不同的Fragment
     */
    private void initDatas()
    {
        LeftFragment leftFragment = new LeftFragment();
        CenterFragment centerFragment = new CenterFragment();
        RightFragment rightFragment = new RightFragment();
        mTabs.add(leftFragment);
        mTabs.add(centerFragment);
        mTabs.add(rightFragment);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return mTabs.get(position);
            }

            @Override
            public int getCount()
            {
                return mTabs.size();
            }
        };
    }

    /**
     * 按钮点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        resetOtherTabs();
        switch (v.getId())
        {
            case R.id.main_tab_one:
                mTabIndicators.get(0).setIconAlpha(1.0f);
                //第二个参数为true有动画效果
                mViewpager.setCurrentItem(0, false);
                break;
            case R.id.main_tab_two:
                mTabIndicators.get(1).setIconAlpha(1.0f);
                //第二个参数为true有动画效果
                mViewpager.setCurrentItem(1, false);
                break;
            case R.id.main_tab_three:
                mTabIndicators.get(2).setIconAlpha(1.0f);
                //第二个参数为true有动画效果
                mViewpager.setCurrentItem(2, false);
                break;
            default:
                break;
        }
    }

    /**
     * 重置其他的tABIndicator的颜色
     */
    private void resetOtherTabs()
    {
        for (int i = 0; i < mTabIndicators.size(); i++)
        {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
//        Log.d("TAG", "position = " + position + ", opsitionOffset = " + positionOffset);
        if (positionOffset > 0)
        {
            BottomTab left = mTabIndicators.get(position);
            BottomTab right = mTabIndicators.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position)
    {

    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    /**
     * 显示/关闭侧滑菜单按钮监听
     */
    public void toggle(View view)
    {
        mSwitchMenu.toggleMenu();
    }

    /**
     * 为listView的适配器设置数据
     *
     * @return
     */
    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < text.length; i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(IMAGE, icon[i]);
            map.put(TEXT, getResources().getString(text[i]));
            listItems.add(map);
        }
        return listItems;
    }


    /**
     * 解决Fragment无法使用onTouchEvent()方法
     */
    public interface MyTouchListener
    {
        void onTouchEvent(MotionEvent event);
    }

    // 保存MyTouchListener接口的列表
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener)
    {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener)
    {
        myTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        for (MyTouchListener listener : myTouchListeners)
        {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
