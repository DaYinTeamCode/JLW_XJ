package com.jlwteam.rebate.page.feeds.discover;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.androidex.imageloader.fresco.FrescoImageView;
import com.androidex.statusbar.StatusBarManager;
import com.androidex.util.CollectionUtil;
import com.androidex.util.DensityUtil;
import com.androidex.util.DeviceUtil;
import com.androidex.util.ViewUtil;
import com.androidex.widget.rv.lisn.item.OnExRvItemViewClickListener;
import com.androidex.widget.rv.vh.ExRvItemViewHolderBase;
import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.util.L;
import com.jlwteam.rebate.page.feeds.MainFeedsFragment;
import com.jlwteam.rebate.page.feeds.discover.bean.FeedsVideoListResult;
import com.jlwteam.rebate.page.feeds.discover.bean.FeedsVideoResult;
import com.jzyd.lib.httptask.HttpFrameParams;
import com.jzyd.lib.refresh.sqkbswipe.SqkbSwipeRefreshLayout;
import com.jlwteam.rebate.R;
import com.jlwteam.rebate.base.aframe.CpHttpFrameXrvFragment;
import com.jlwteam.rebate.page.feeds.discover.adapter.VideoDetailAdapter;
import com.jlwteam.rebate.page.feeds.discover.impl.OnViewPagerListener;
import com.jlwteam.rebate.page.feeds.discover.impl.ViewPagerLayoutManager;
import com.jlwteam.rebate.page.feeds.discover.utils.FeedsVideoHttpUtils;
import com.jlwteam.rebate.page.feeds.discover.viewholder.VideoDetailViewHolder;
import com.jlwteam.rebate.stat.StatRecyclerViewNewAttacher;
import com.jlwteam.rebate.view.load.LoadingView;
import com.jlwteam.rebate.widget.video.VideoController;

import java.util.List;

import javax.annotation.Nullable;

/**
 * 发现视频Fragment
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/11 4:34 PM
 */
public class DiscoverVideoFragment extends CpHttpFrameXrvFragment<FeedsVideoListResult>
        implements SqkbSwipeRefreshLayout.OnRefreshListener, StatRecyclerViewNewAttacher.DataItemListener, OnExRvItemViewClickListener {

    private static IjkVideoView mIjkVideoView;
    private VideoController mVideoController;
    private int mCurrentPosition;
    private VideoDetailAdapter mVideoDetailAdapter;
    private boolean mIsPullRefresh;
    private static ImageView mIvVideoPlay;
    private static LoadingView mVideoLoadingView;
    private FrameLayout mFlController;
    private FrescoImageView mCoverImg;
    private boolean mIsCurrPageVisible;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setContentSwipeRefreshRecyclerView();
        getExDecorView().setBackgroundColor(0Xff161723);
        getExDecorView().setPadding(0
                , StatusBarManager.getInstance().getStatusbarHeight(getActivity())
                , 0, DensityUtil.dip2px(48f));
        setPageLimit(10);
        executeFrameImpl();
    }

    @Override
    public void onPause() {

        super.onPause();

    }

    @Override
    public void onResume() {

        super.onResume();
        /*** 设置标题栏为透明状态 */
        getTitleView().setBackgroundColor(0x02000000);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (mIjkVideoView != null) {

            mIjkVideoView.setVideoController(null);
            mIjkVideoView.release();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            if (mIjkVideoView != null && !ViewUtil.isShow(mIvVideoPlay)) {

                mIjkVideoView.resume();
            }
            mIsCurrPageVisible = true;
        } else {

            if (mIjkVideoView != null) {

                mIjkVideoView.pause();
            }
            mIsCurrPageVisible = false;
        }
    }

    @Override
    public void onSupportShowToUserChanged(boolean isShowToUser, int from) {

        super.onSupportShowToUserChanged(isShowToUser, from);
    }

    public void onSupporUserChanged(boolean isShowToUser, int from) {

        if (isShowToUser && from == MainFeedsFragment.FROM_MAIN_FEEDS) {

            if (mIjkVideoView != null && !ViewUtil.isShow(mIvVideoPlay)) {

                mIjkVideoView.resume();
            }
            mIsCurrPageVisible = true;
        } else {

            if (mIjkVideoView != null) {

                mIjkVideoView.pause();
                mIsCurrPageVisible = false;
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        getTitleView().setBackgroundColor(0x02000000);
        setStatusbarView(getTitleView());
    }

    @Override
    protected void initContentView() {

        getSwipeView().setProgressViewEndTarget(true, DensityUtil.dip2px(60) + getTitleViewHeight());
        setDisabledImageResId(R.mipmap.ic_page_tip_data_empty);
        setDisabledTextResId(R.string.common_data_none);

        initIjkVideoWidget();
        initVideoAdapter();
        initRecycleViewWidget();
    }

    /***
     *  初始化播放器组件
     */
    private void initIjkVideoWidget() {

        mIjkVideoView = new IjkVideoView(getActivity());
        mIjkVideoView.setLooping(true);
        mIjkVideoView.setPlayOnMobileNetwork(true);
        mVideoController = new VideoController(getActivity());
        mIjkVideoView.setVideoController(mVideoController);
        mIjkVideoView.addOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {

            }

            @Override
            public void onPlayStateChanged(int playState) {

                if (mIjkVideoView == null) {

                    return;
                }

                if (!mIsCurrPageVisible && mIjkVideoView.isPlaying()) {

                    mIjkVideoView.pause();
                }
                switch (playState) {
                    case IjkVideoView.STATE_IDLE:
                        L.e("STATE_IDLE");
                        ViewUtil.showView(mVideoLoadingView);
                        ViewUtil.showView(mCoverImg);
                        break;
                    case IjkVideoView.STATE_PLAYING:
                        ViewUtil.goneView(mCoverImg);
                        ViewUtil.goneView(mVideoLoadingView);
                        break;
                    case IjkVideoView.STATE_PREPARED:
                        L.e("STATE_PREPARED");
                        break;
                }
            }
        });
    }

    /***
     *  初始化视频适配器
     */
    private void initVideoAdapter() {

        mVideoDetailAdapter = new VideoDetailAdapter(getActivity());
        mVideoDetailAdapter.setOnExRvItemViewClickListener(this);
    }

    /***
     *  初始化列表组件
     */
    private void initRecycleViewWidget() {

        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(getActivity(), OrientationHelper.VERTICAL);
        getRecyclerView().setLayoutManager(layoutManager);
        StatRecyclerViewNewAttacher statRecyclerViewNewAttacher = new StatRecyclerViewNewAttacher(getRecyclerView());
        statRecyclerViewNewAttacher.setDataItemListener(this);
        getRecyclerView().addOnChildAttachStateChangeListener(statRecyclerViewNewAttacher);
        getRecyclerView().setAdapter(mVideoDetailAdapter);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {

                if (mCurrentPosition == position) {

                    mIjkVideoView.release();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {

                if (mCurrentPosition == position) {

                    return;
                }
                startPlay(position);
                mCurrentPosition = position;
            }
        });
    }

    /*------------------------------------ 网络相关请求 --------------------------------------------*/

    protected void executeFrameImpl() {

        executeFrameRefresh();
    }

    protected void executePullImpl() {

        executeFrameRefresh();
    }

    protected void executeFailedRetryImpl() {

        executeFrameRefresh();
    }

    @Override
    protected HttpFrameParams getPageHttpParams(int nextPageStartIndex, int pageLimit) {

        return new HttpFrameParams(FeedsVideoHttpUtils.getFeedsVideoParams(nextPageStartIndex, pageLimit)
                , FeedsVideoListResult.class);
    }

    /***
     *   刷新Loading
     */
    @Override
    protected void switchLoadingOnFrameRefresh() {

        if (mIsPullRefresh) {

            switchContent();
        } else {

            super.switchLoadingOnFrameCache();
        }
    }

    @Override
    protected void switchFailedOnFrameRefresh(int failedCode, String msg) {

        if (mIsPullRefresh) {

            if (getSwipeView() != null) {

                getSwipeView().setRefreshing(false);
            }
        } else {

            super.switchFailedOnFrameRefresh(failedCode, msg);
        }
    }

    /*-------------------------------下拉刷新相关回调-----------------------------*/

    @Override
    protected void onTipViewClick() {

        if (DeviceUtil.isNetworkDisable()) {

            showToast(R.string.toast_network_none);
        } else {

            executeFailedRetryImpl();
        }
    }

    /*-------------------------------下拉刷新相关回调-----------------------------*/

    @Override
    public void onRefresh() {

        if (DeviceUtil.isNetworkEnable()) {

            mIsPullRefresh = true;
            executePullImpl();
        } else {

            showToast(R.string.toast_network_none);
            if (getSwipeView() != null) {

                getSwipeView().setRefreshing(false);
            }
        }
    }

    @Override
    public void onRefreshCompleted() {

        mIsPullRefresh = false;

        //自动播放第一条
        startPlay(0);
    }

    @Override
    protected boolean invalidateContent(FeedsVideoListResult result) {

        if (getSwipeView() != null) {

            getSwipeView().setRefreshing(false);
        }

        if (result == null) {

            return false;
        }

        if (CollectionUtil.isEmpty(result.getList())) {

            return false;
        }
        super.invalidateContent(result);
        return true;
    }

    @Override
    protected void onLoadMoreFailed(int failedCode, String msg) {

        super.onLoadMoreFailed(failedCode, msg);
    }

    /***
     * 刷新列表数据
     *
     * @param result
     * @return
     */
    @Override
    protected List<?> invalidateContentGetList(FeedsVideoListResult result) {

        return result != null ? result.getList() : null;
    }

    @Override
    protected void showContent() {

        super.showContent();
    }

    @Override
    public void onExRvItemViewClick(View view, int dataPos) {

        Object object = mVideoDetailAdapter.getDataItem(dataPos);
        if (object instanceof FeedsVideoResult) {

        }
    }

    @Override
    public void onRecyclerViewDataItemStatShow(int dataPos) {

        if (dataPos == 0) {

            //自动播放第一条
            startPlay(0);
        }
    }

    /***
     *  开始播放视频
     *
     * @param position
     */
    private void startPlay(int position) {

        Object obj = mVideoDetailAdapter.getDataItem(position);

        if (obj instanceof FeedsVideoResult) {

            FeedsVideoResult feedsVideoResult = (FeedsVideoResult) obj;
            View itemView = getRecyclerView().getChildAt(0);
            ExRvItemViewHolderBase childViewHolder = getRecyclerView().getChildViewHolder(itemView);
            if (childViewHolder instanceof VideoDetailViewHolder) {

                VideoDetailViewHolder viewHolder = (VideoDetailViewHolder) childViewHolder;
                FrameLayout frameLayout = viewHolder.itemView.findViewById(R.id.container);

                CardView cardView = new CardView(getActivity());
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
                cardView.setRadius(DensityUtil.dip2px(8f));
                frameLayout.addView(cardView, layoutParams);
                ViewParent parent = mIjkVideoView.getParent();
                if (parent instanceof CardView) {

                    ((CardView) parent).removeView(mIjkVideoView);
                }
                cardView.addView(mIjkVideoView);
                mIjkVideoView.setUrl(feedsVideoResult.getOpenUrls());
                mIjkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
                mIjkVideoView.start();

                mCoverImg = viewHolder.itemView.findViewById(R.id.thumb);
                mIvVideoPlay = viewHolder.itemView.findViewById(R.id.ivPlay);

                mVideoLoadingView = viewHolder.itemView.findViewById(R.id.videoLoadingView);
                if (ViewUtil.isShow(mIvVideoPlay)) {

                    ViewUtil.hideView(mIvVideoPlay);
                }
                mFlController = viewHolder.itemView.findViewById(R.id.flController);
                onControllerWidget(mFlController);
            }
        }
    }

    /***
     *
     * @param frameLayout
     */
    private void onControllerWidget(FrameLayout frameLayout) {

        if (frameLayout == null) {

            return;
        }

        frameLayout.setOnClickListener(v -> {

            if (mIjkVideoView == null || mIvVideoPlay == null) {
                return;
            }
            if (mIjkVideoView.isPlaying()) {

                mIjkVideoView.pause();
                ViewUtil.showView(mIvVideoPlay);
            } else {

                ViewUtil.hideView(mIvVideoPlay);
                mIjkVideoView.resume();
            }
        });
    }

    /***
     *   发现视频
     *
     * @param context
     * @return
     */
    public static DiscoverVideoFragment newInstance(Context context) {

        return (DiscoverVideoFragment) Fragment.instantiate(context, DiscoverVideoFragment.class.getName());
    }
}
