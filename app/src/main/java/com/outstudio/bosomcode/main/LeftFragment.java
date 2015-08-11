package com.outstudio.bosomcode.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主界面之左边的界面
 * Created by mima123 on 15/8/6.
 */
public class LeftFragment extends Fragment {

    private ListView listView = null;
    //listView适配器的数据源
    private String[] names = {"吃了这些药 千万别开车", "吃坏肚子咋整? 小妙招来拆解", "九九九牌感冒灵 专治感冒",
            "吃了这些药 千万别开车", "吃坏肚子咋整? 小妙招来拆解", "九九九牌感冒灵 专治感冒",
            "吃了这些药 千万别开车", "吃坏肚子咋整? 小妙招来拆解", "九九九牌感冒灵 专治感冒"};
    private String[] sources = {"39健康网","[推广]","39健康网","[推广]","39健康网","[推广]","39健康网",
            "[推广]","39健康网"};
    private String[] publishTime = {"2015/08/07 13:23:35","2015/08/07 13:23:35","2015/08/07 13:23:35",
            "2015/08/07 13:23:35","2015/08/07 13:23:35","2015/08/07 13:23:35","2015/08/07 13:23:35",
            "2015/08/07 13:23:35","2015/08/07 13:23:35",};
    private int[] images = {R.drawable.medicine_info_image_1,R.drawable.medicine_info_image_2,
            R.drawable.medicine_info_image_3,R.drawable.medicine_info_image_1,
            R.drawable.medicine_info_image_2,R.drawable.medicine_info_image_3,
            R.drawable.medicine_info_image_1,R.drawable.medicine_info_image_2,
            R.drawable.medicine_info_image_3,};
    //listView的标签
    private static final String NAME_FLAG = "name_flag";
    private static final String SOURCE_FLAG = "source_flag";
    private static final String PUBLISH_TIME_FLAG = "publish_time_flag";
    private static final String IMAGE_FLAG = "image_flag";

    //当前日期
    private String date = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.left_fragment, container, false);
        initView(view);
        return view;

    }

    /**
     * 初始化界面组件
     */
    private void initView(View view){
        listView = (ListView) view.findViewById(R.id.left_fragment_listView);
        date = Utils.getDate();
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(),
                R.layout.for_left_fragment_listview,new String[]{NAME_FLAG,SOURCE_FLAG,
                PUBLISH_TIME_FLAG,IMAGE_FLAG},new int[]{R.id.left_fragment_medicine_name,
                R.id.left_fragment_medicine_source,R.id.left_fragment_publish_time,
                R.id.left_fragment_medicine_image});
        listView.setAdapter(adapter);
    }

    /**
     * 为listView的适配器设置数据
     *
     * @return
     */
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put(NAME_FLAG,names[i]);
            map.put(SOURCE_FLAG,sources[i]);
            map.put(PUBLISH_TIME_FLAG,publishTime[i]);
            map.put(IMAGE_FLAG, images[i]);
            listItems.add(map);
        }
        return listItems;
    }
}
