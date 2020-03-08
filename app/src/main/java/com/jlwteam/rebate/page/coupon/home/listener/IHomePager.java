package com.jlwteam.rebate.page.coupon.home.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jlwteam.rebate.page.coupon.home.bean.HomePageCacheInfo;

public interface IHomePager {

    int getHomePageCacheId();

    void setHomePageCacheInfo(int cacheId, HomePageCacheInfo cacheInfo);

    HomePageCacheInfo getHomePageCacheInfo();

    void setHomePageListener(Listener listener);

    void onHomePageHiddenChanged(boolean hidden);

    boolean isHomePageHeadVisible();

    void performHomePageActionRefresh(boolean needSwipeView);

    void onMainTabRepeatSingleTap();

    void onHomePageSelectChanged(boolean selected);

    void onHomePageSelectScrollIdle();

    int getHomePageNextPageNum();

    boolean canScrollVertically();

    boolean isPageRefreshState();

    String getPageCateName();

    void animationRefreshTip();

    void onMainTabDoubleTap();

    boolean canScrollUp();

    void scrollTop();

    View getScrollView();

    void performStatShowNoForce();

    void setStatEnable(boolean enable);

    void clearStatShowPool();

    interface Listener {

        boolean onHomePageActionRefresh(int position, int sortType);

        void onHomePageActionRefreshCompleted(int position);

        //void onHomeFrameActionRefreshAbort();暂时没有该回调,有了再说
        void onHomePageHeadVisibleChanged(int position, boolean headVisible);

        void onViewScrollStateChanged(int state);

        void onRefreshStateChange(RecyclerView recyclerView, boolean curState);

        void onPageNumChanged(int position, int pageIndex, boolean isOver, boolean isHomeCache);

        void onScrollViewScrolled(View view, int dx, int dy, int findFirstCompletelyVisibleItemPosition, int insertOperSize);
    }
}
