package com.jlwteam.rebate.page.coupon.home.rec;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.androidex.util.LogMgr;
import com.jlwteam.rebate.base.aframe.CpHttpFrameXrvFragment;
import com.jlwteam.rebate.page.coupon.home.bean.HomePageCacheInfo;
import com.jlwteam.rebate.page.coupon.home.listener.IHomePager;
import com.jzyd.lib.httptask.HttpFrameParams;

public class HomePageRecListFragment extends CpHttpFrameXrvFragment implements IHomePager {

    private int mCacheId;
    private int mPagePos;
    private int mLoadingPageNum;
    private boolean mDataPageIsOver;
    private int mOriginLoadMoreSize;
    private boolean mLoadMoreRepeatLock;
    private int mCateId;
    private String mCateName;
    private int mScrollState;
    private boolean mInsertedRec;
    private long mPageOverLoadMoreTime;//标记 pageisover 自动loadmore 的时间
    private boolean mRefreshState;
    private static boolean mIsScrollToFeedTop; //是否滑动到feed顶部
    private boolean mFromCache;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        setSupportViewPagerMode(true);
        super.onActivityCreated(savedInstanceState);
        setContentRecyclerView();
        showLoading();
        showContent();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected HttpFrameParams getPageHttpParams(int nextPageStartIndex, int pageLimit) {
        return null;
    }

    @Override
    protected void initData() {

        mPagePos = getArgumentInt("pagePos");
        mCateId = getArgumentInt("cateId");
        mFromCache = getArgumentBoolean("fromCache");
        mCateName = getArgumentString("cateName");
    }

    @Override
    protected void initTitleView() {
        //nothing
    }

    @Override
    protected void initContentView() {

        setPullRefreshEnable(false);
        getRecyclerView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (LogMgr.isDebug()) {
            LogMgr.d(simpleTag(), "HomePageRecNewListFra pagePos = " + mPagePos + " initContentView vpMode = " + isSupportViewPagerMode() + ", vpSelected = " + isSupportPageSelected());
        }
    }

    @Override
    public int getHomePageCacheId() {
        return 0;
    }

    @Override
    public void setHomePageCacheInfo(int cacheId, HomePageCacheInfo cacheInfo) {

    }

    @Override
    public HomePageCacheInfo getHomePageCacheInfo() {
        return null;
    }

    @Override
    public void setHomePageListener(Listener listener) {

    }

    @Override
    public void onHomePageHiddenChanged(boolean hidden) {

    }

    @Override
    public boolean isHomePageHeadVisible() {
        return false;
    }

    @Override
    public void performHomePageActionRefresh(boolean needSwipeView) {

    }

    @Override
    public void onMainTabRepeatSingleTap() {

    }

    @Override
    public void onHomePageSelectChanged(boolean selected) {

    }

    @Override
    public void onHomePageSelectScrollIdle() {

    }

    @Override
    public int getHomePageNextPageNum() {
        return 0;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public boolean isPageRefreshState() {
        return false;
    }

    @Override
    public String getPageCateName() {
        return null;
    }

    @Override
    public void animationRefreshTip() {

    }

    @Override
    public void onMainTabDoubleTap() {

    }

    @Override
    public boolean canScrollUp() {
        return false;
    }

    @Override
    public void scrollTop() {

        super.scrollTop();
        if (getRecyclerView() != null) {

            getRecyclerView().scrollToPosition(0);
        }
    }

    @Override
    public View getScrollView() {
        return null;
    }

    @Override
    public void performStatShowNoForce() {

    }

    @Override
    public void setStatEnable(boolean enable) {

    }

    @Override
    public void clearStatShowPool() {

    }

    public static HomePageRecListFragment newInstance(Context context, int pagePos,
                                                      boolean genderIsMan, int cateId, String cateName, boolean statCanShow, boolean fromCache) {

        Bundle bundle = new Bundle();
        bundle.putInt("cateId", cateId);
        bundle.putString("cateName", cateName);
        bundle.putInt("pagePos", pagePos);
        bundle.putBoolean("genderIsMan", genderIsMan);
        bundle.putBoolean("statCanShow", statCanShow);
        bundle.putBoolean("fromCache", fromCache);
        return (HomePageRecListFragment) Fragment.instantiate(context, HomePageRecListFragment.class.getName(), bundle);
    }

}
