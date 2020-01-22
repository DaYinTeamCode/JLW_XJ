package com.jlwteam.rebate.page.coupon.home.widget;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidex.imageloader.fresco.FrescoImageView;
import com.androidex.plugin.ExLayoutWidget;
import com.androidex.util.DensityUtil;
import com.androidex.util.ViewUtil;
import com.jlwteam.rebate.utils.CpDrawableUtil;
import com.sjteam.weiguan.R;

/**
 * 首页标题栏布局
 */
public class MainHomeHeaderTitleWidget extends ExLayoutWidget implements View.OnClickListener {

    private FrescoImageView mFivLeftOper, mFivRightOper;
    private FrameLayout mFlTitleDiv;
    private TextView mTvTitle;

    public MainHomeHeaderTitleWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, ViewGroup parent, Object... args) {

        View view = activity.getLayoutInflater().inflate(R.layout.activity_page_main_home_title_widget, parent, false);

        mFivLeftOper = view.findViewById(R.id.fivLeftOper);
        mFivLeftOper.setOnClickListener(this);
        ViewUtil.goneView(mFivLeftOper);

        mFivRightOper = view.findViewById(R.id.fivRightOper);
        mFivRightOper.setOnClickListener(this);
        ViewUtil.goneView(mFivRightOper);

        mFlTitleDiv = view.findViewById(R.id.flTitleDiv);
        mFlTitleDiv.setOnClickListener(this);
        ViewUtil.setViewBackground(mFlTitleDiv, CpDrawableUtil.getRectDrawable(DensityUtil.dip2px(20), Color.WHITE));
        mTvTitle = view.findViewById(R.id.tvTitle);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

}
