package com.jlwteam.rebate.page.coupon.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.androidex.util.DensityUtil;
import com.androidex.util.VglpUtil;
import com.jlwteam.rebate.base.aframe.viewer.CpHttpFrameVFragmentViewer;
import com.jlwteam.rebate.page.coupon.home.widget.MainHomeHeaderTitleWidget;
import com.jzyd.lib.httptask.HttpFrameParams;
import com.sjteam.weiguan.R;

/**
 * 首页主框架
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2020-01-21 11:41
 */
public class MainHomeFragment extends CpHttpFrameVFragmentViewer {

    private MainHomeHeaderTitleWidget mMainHomeHeaderTitleWidget;

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

        mMainHomeHeaderTitleWidget = new MainHomeHeaderTitleWidget(getActivity());
        addTitleMiddleView(mMainHomeHeaderTitleWidget.getContentView(),
                VglpUtil.getLllpSS(VglpUtil.M, DensityUtil.dip2px(R.dimen.cp_title_bar_height)));
        getTitleView().setBackgroundResource(R.drawable.shape_bg_colorful_no_corner);
        setStatusbarView(getTitleView());
    }

    @Override
    protected void initContentView() {

    }

    public static MainHomeFragment newInstance(Context context) {

        return (MainHomeFragment) Fragment.instantiate(context, MainHomeFragment.class.getName());
    }
}
