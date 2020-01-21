package com.jlwteam.rebate.page.coupon.collection;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.androidex.util.DensityUtil;
import com.androidex.util.VglpUtil;
import com.jlwteam.rebate.base.aframe.viewer.CpHttpFrameVFragmentViewer;
import com.jlwteam.rebate.page.coupon.home.widget.MainHomeHeaderTitleWidget;
import com.jzyd.lib.httptask.HttpFrameParams;
import com.sjteam.weiguan.R;

/**
 * 收藏主框架
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2020-01-21 11:41
 */
public class MainCollectionFragment extends CpHttpFrameVFragmentViewer {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.fragment_page_main_home_fragment);
        showLoading();
        showContent();
    }

    @Override
    protected HttpFrameParams getHttpParamsOnFrameExecute(Object... params) {
        return null;
    }

    @Override
    protected boolean invalidateContent(Object result) {

        return false;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        TextView textView = addTitleMiddleTextView("收藏夹");
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setTextColor(0XFFFFFFFF);
        getTitleView().setBackgroundResource(R.drawable.shape_bg_colorful_no_corner);
        setStatusbarView(getTitleView());
    }

    @Override
    protected void initContentView() {

    }

    public static MainCollectionFragment newInstance(Context context) {

        return (MainCollectionFragment) Fragment.instantiate(context, MainCollectionFragment.class.getName());
    }
}
