package com.jlwteam.rebate.page.coupon.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import com.androidex.plugin.DelayBackHandler;
import com.androidex.util.DensityUtil;
import com.jlwteam.rebate.base.aframe.ExFragmentActivity;
import com.jlwteam.rebate.page.coupon.collection.MainCollectionFragment;
import com.jlwteam.rebate.page.coupon.discover.MainDiscoverFragment;
import com.jlwteam.rebate.page.coupon.home.MainHomeFragment;
import com.jlwteam.rebate.page.coupon.me.MainUserFragment;
import com.jlwteam.rebate.page.feeds.follow.FollowVideoFragment;
import com.jlwteam.rebate.view.tab.NavigateTabBar;
import com.jlwteam.rebate.view.toast.ExToast;
import com.sjteam.weiguan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页主框架
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2020-01-20 19:14
 */
public class MainActivity extends ExFragmentActivity implements DelayBackHandler.OnDelayBackListener {

    private static final String TAG_PAGE_HOME = "首页";
    private static final String TAG_PAGE_DISCOVER = "分类";
    private static final String TAG_PAGE_COLLECTION = "收藏";
    private static final String TAG_PAGE_USER = "我的";

    protected Unbinder unbinder;

    public static final int EXTRA_VALUE_LAUNCHER_TAB_INDEX = 1;

    private DelayBackHandler mDelayBackHandler;

    @BindView(R.id.mainTabBar)
    NavigateTabBar mNavigateTabBar;
    NavigateTabBar.ViewHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);

        unbinder = ButterKnife.bind(this);
        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);
        mNavigateTabBar.addTab(MainHomeFragment.class, new NavigateTabBar.TabParam(R.mipmap.ic_home_normal, R.mipmap
                .ic_home_selected, TAG_PAGE_HOME));
        mNavigateTabBar.addTab(MainDiscoverFragment.class, new NavigateTabBar.TabParam(R.mipmap.ic_fenlei_normal, R.mipmap
                .ic_fenlei_selected, TAG_PAGE_DISCOVER));
        mNavigateTabBar.addTab(MainCollectionFragment.class, new NavigateTabBar.TabParam(R.mipmap.ic_shoe_collect_normal, R
                .mipmap.ic_shoe_collect_selected, TAG_PAGE_COLLECTION));
        mNavigateTabBar.addTab(MainUserFragment.class, new NavigateTabBar.TabParam(R.mipmap.ic_more_normal, R.mipmap
                .ic_more_selected, TAG_PAGE_USER));
        mNavigateTabBar.setTabSelectListener(holder -> {
            switch (holder.tag) {
                case TAG_PAGE_HOME:
                    mNavigateTabBar.showFragment(holder);
                    break;
                case TAG_PAGE_DISCOVER:
                    mNavigateTabBar.showFragment(holder);
                    break;
                case TAG_PAGE_COLLECTION:
                    mNavigateTabBar.showFragment(holder);
                    break;
                case TAG_PAGE_USER:
                    if (mNavigateTabBar != null)
                        mNavigateTabBar.showFragment(holder);
                    break;
            }
        });
    }

    @Override
    protected void initData() {

        mDelayBackHandler = new DelayBackHandler();
        mDelayBackHandler.setOnDelayBackListener(this);
    }

    @Override
    protected void initTitleView() {

    }

    @Override
    protected void initContentView() {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    /**
     * back键点击
     */
    @Override
    public void onBackPressed() {

        if (mDelayBackHandler != null) {

            mDelayBackHandler.triggerPreBack();
        }
    }

    /***
     *  退出回调
     * @param preBack
     */
    @Override
    public void onDelayBack(boolean preBack) {

        perBackOrFinish(preBack);
    }

    /*---------------------------------------- 辅助函数 -------------------------------------------*/

    /**
     * 保存数据状态
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        if (mNavigateTabBar != null) {

            mNavigateTabBar.onSaveInstanceState(outState);
        }
    }

    /***
     *
     * @param preBack
     */
    private void perBackOrFinish(boolean preBack) {

        if (preBack) {

            ExToast.makeText("再按一次退出应用", Gravity.BOTTOM, DensityUtil.dip2px(60f)).show();
        } else {

            finish();
        }
    }

    /***
     * 打开主界面
     *
     * @param activity
     */
    public static void startActivityForIndex(Activity activity) {

        startActivity(activity, EXTRA_VALUE_LAUNCHER_TAB_INDEX, false, 0);
        activity.overridePendingTransition(R.anim.alpha_in, R.anim.push_exit_stop);
    }

    /***
     *  打开主界面
     *
     * @param context
     * @param launcherTab
     * @param newActivityTask
     * @param tabIndex
     */
    private static void startActivity(Context context, int launcherTab, boolean newActivityTask, int tabIndex) {

        Intent intent = new Intent();

        if (newActivityTask) {

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }
}
