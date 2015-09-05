package com.outstudio.bosomcode.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.outstudio.bosomcode.R;
import com.outstudio.bosomcode.center.QRScanActivity;
import com.outstudio.bosomcode.center.ShareThought;

/**
 * 目前存在的问题,无法使用手势
 * <p/>
 * Created by mima123 on 15/8/6.
 */
public class CenterFragment extends Fragment implements View.OnClickListener {

    private ViewFlipper flipper = null;
    private int[] imageId = {R.drawable.advertise_1, R.drawable.advertise_2, R.drawable.advertise_3, R.drawable.advertise_4};
    private Button scanCodeBt = null;
    private Button personSpecilistBt = null;
    private Button shareBt = null;
    private Button callHelpBt = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.center_fragment, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        flipper = (ViewFlipper) view.findViewById(R.id.center_fragment_view_flipper);
        scanCodeBt = (Button) view.findViewById(R.id.center_fragment_scanCode_bt);
        personSpecilistBt = (Button) view.findViewById(R.id.center_fragment_personal_specialist_bt);
        shareBt = (Button) view.findViewById(R.id.center_fragment_share_bt);
        callHelpBt = (Button) view.findViewById(R.id.center_fragment_call_help_bt);
        scanCodeBt.setOnClickListener(this);
        personSpecilistBt.setOnClickListener(this);
        shareBt.setOnClickListener(this);
        callHelpBt.setOnClickListener(this);
        /**
         * 动态导入的方式为viewFlipper加入子view
         */
        for (int i = 0; i < imageId.length; i++) {
            flipper.addView(getImageView(imageId[i]));
        }
        //为ViewFlipper设置动画效果
        flipper.setInAnimation(getActivity(), R.anim.from_right_in);
        flipper.setOutAnimation(getActivity(), R.anim.to_left_out);
        //设置ViewFlipper视图切换的时间间隔
        flipper.setFlipInterval(3000);
        //开始播放
        flipper.startFlipping();
    }


    /**
     * 获得ImageView
     *
     * @param imageId
     * @return
     */
    private ImageView getImageView(int imageId) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(imageId);
        return imageView;
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.center_fragment_scanCode_bt:
                intent = new Intent(getActivity(), QRScanActivity
                        .class);
                startActivity(intent);
                break;
            case R.id.center_fragment_personal_specialist_bt:
//                startActivity(intent);
                break;
            case R.id.center_fragment_share_bt:
                intent = new Intent(getActivity(), ShareThought.class);
                startActivity(intent);
                break;
            case R.id.center_fragment_call_help_bt:
                break;
            default:
                break;
        }
    }
}
