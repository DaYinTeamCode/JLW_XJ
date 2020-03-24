package com.jlwteam.rebate.page.coupon.home;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androidex.activity.ExFragment;
import com.androidex.util.DensityUtil;
import com.androidex.util.LogMgr;
import com.androidex.view.pager.ExViewPager;
import com.androidex.view.pager.indicator.TabStripIndicator;
import com.jlwteam.rebate.page.coupon.home.adapter.HomeFragmentPagerAdapter;
import com.jlwteam.rebate.page.coupon.home.category.bean.CateCollection;
import com.jlwteam.rebate.page.coupon.home.listener.IHomePager;
import com.jlwteam.rebate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页分类Fragment
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2020-01-22 15:33
 */
public class MainHomeCateFragment extends ExFragment implements ViewPager.OnPageChangeListener, TabStripIndicator.OnTabItemClickListener, IHomePager.Listener {

    private TabStripIndicator mTsiCate;
    private ExViewPager mVpCate;
    private HomeFragmentPagerAdapter mPageAdapter;
    private AppBarLayout mAppBarLayout;
    private int mLastScrollIdlePagePos;
    private int mCateClickPos = -1;
    private int mRecCatePageNum;
    private boolean mRecPageNumChanged;
    private boolean mRecCatePageIsOver;
    private boolean mRecCatePageDataIsHomeCache;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.fragment_page_main_home_cate_fragment);
        invalidateCateView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

    }

    @Override
    protected void initContentView() {

        mPageAdapter = new HomeFragmentPagerAdapter(getActivity(), getChildFragmentManager());
        mPageAdapter.setFragmentItemDataSetChangedEnable(true);
        mPageAdapter.setHomePageListener(this);
        mPageAdapter.setItemStatCanShow(true);

        mVpCate = (ExViewPager) findViewById(R.id.vpCate);
        mVpCate.setTryOnInterceptTouchEventException(true);
        mVpCate.setAdapter(mPageAdapter);

        mTsiCate = (TabStripIndicator) findViewById(R.id.tsiCate);
        mTsiCate.setColorTabTextDefault(0XFFFFFFFF);
        mTsiCate.setColorTabTextSelected(0XFFFFFFFF);
        mTsiCate.setIndicatorColor(0XFFFFFFFF);
        mTsiCate.setIndicatorRoundRect(true);
        mTsiCate.setIndicatorHeight(DensityUtil.dip2px(3f));
        mTsiCate.setTabPaddingLeftRight(DensityUtil.dip2px(10f));
        mTsiCate.setTextSize(DensityUtil.dip2px(12f));
        mTsiCate.setUnderlineHoriPadding(DensityUtil.dip2px(15f));
        mTsiCate.setIndicatorMarginBottom(DensityUtil.dip2px(4f));
        mTsiCate.setTextTypeface(Typeface.DEFAULT_BOLD);
        mTsiCate.setViewPager(mVpCate);
        mTsiCate.setOnPageChangeListener(this);
        mTsiCate.setOnTabItemClickListener(this);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        initAppBarLayoutListener(mAppBarLayout);

    }

    /***
     *   初始化AppBarLayout 监听
     */
    private void initAppBarLayoutListener(AppBarLayout barLayout) {

        if (barLayout == null) {

            return;
        }

        barLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private int latVerticalOffset;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == latVerticalOffset || appBarLayout == null) {
                    return;
                }

                if (LogMgr.isDebug()) {
                    LogMgr.e("onOffsetChanged", verticalOffset + "");
                }

                latVerticalOffset = verticalOffset;
            }
        });
    }

    private void invalidateCateView() {

        List<CateCollection> cateCollections = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            CateCollection cate = new CateCollection();
            if (i == 0) {

                cate.setName("精选");
                cate.setCate_collection_id(CateCollection.LOCAL_REC_ID);
            } else if (i == 1) {

                cate.setName("女装");

            } else if (i == 2) {

                cate.setName("女鞋");
            } else if (i == 3) {

                cate.setName("食品");
            } else if (i == 4) {

                cate.setName("美妆");
            } else if (i == 5) {

                cate.setName("百货");
            } else if (i == 6) {

                cate.setName("内衣");
            } else if (i == 7) {

                cate.setName("箱包");
            } else if (i == 8) {

                cate.setName("配饰");
            } else {

                cate.setName("男装" + i);
            }
            cate.setCate_collection_id(CateCollection.LOCAL_REC_ID + i);
            cate.setPic("http://ooy3lg2aq.bkt.clouddn.com/zhekou_category_tab/selected_recommend.jpg");
            cateCollections.add(cate);
        }
        mPageAdapter.setData(cateCollections);
        mPageAdapter.setGenderIsMan(true);
        mPageAdapter.setRecCatePageCacheInfo(null);
        mPageAdapter.setFromCache(false);
        mPageAdapter.notifyDataSetChanged();

        mVpCate.setCurrentItem(0, false);
        mTsiCate.setCurrentPosition(0);
        mTsiCate.notifyDataSetChanged();
        mTsiCate.scrollTo(0, 0);
        mVpCate.post(() -> {

            if (!isFinishing()) {

                onPageSelected(0, true);
            }
        });
    }

    private void onPageSelected(int position, boolean fromReset) {

        if (mPageAdapter == null)
            return;

        IHomePager selectedPageFra = mPageAdapter.getFragment(mVpCate, position);
        if (selectedPageFra != null) {

            selectedPageFra.onHomePageSelectChanged(true);
            selectedPageFra.setStatEnable(true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        onPageSelected(position, false);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        int currentItemPos = mVpCate.getCurrentItem();
        if (state == ViewPager.SCROLL_STATE_IDLE && currentItemPos != mLastScrollIdlePagePos) {

            IHomePager selectedPageFra = mPageAdapter.getFragment(mVpCate, mVpCate.getCurrentItem());
            mLastScrollIdlePagePos = currentItemPos;
            if (selectedPageFra != null) {
                selectedPageFra.onHomePageSelectScrollIdle();
            }

        }
    }

    @Override
    public void onTabItemClick(View view, int position) {

        mCateClickPos = position;
    }

    @Override
    public boolean onHomePageActionRefresh(int position, int sortType) {
        return false;
    }

    @Override
    public void onHomePageActionRefreshCompleted(int position) {

    }

    @Override
    public void onHomePageHeadVisibleChanged(int position, boolean headVisible) {

    }

    @Override
    public void onViewScrollStateChanged(int state) {

    }

    @Override
    public void onRefreshStateChange(RecyclerView recyclerView, boolean curState) {

    }

    @Override
    public void onPageNumChanged(int position, int pageIndex, boolean isOver, boolean isHomeCache) {

        if (position == 0) {

            mRecPageNumChanged = true;
            mRecCatePageNum = pageIndex;
            mRecCatePageIsOver = isOver;
            mRecCatePageDataIsHomeCache = isHomeCache;
        }
    }

    @Override
    public void onScrollViewScrolled(View view, int dx, int dy, int findFirstCompletelyVisibleItemPosition, int insertOperSize) {

    }

    public static MainHomeCateFragment newInstance(Context context) {

        return (MainHomeCateFragment) Fragment.instantiate(context, MainHomeCateFragment.class.getName());
    }
}
