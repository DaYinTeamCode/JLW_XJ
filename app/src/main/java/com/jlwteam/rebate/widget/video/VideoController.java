package com.jlwteam.rebate.widget.video;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.androidex.imageloader.fresco.FrescoImageView;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.util.L;
import com.jlwteam.rebate.R;


/**
 * Create By DaYin(gaoyin_vip@126.com) on 2019/7/9 9:08 PM
 */
public class VideoController extends BaseVideoController {

    private LinearLayout mLlthumb;
    private FrescoImageView thumb;

    public VideoController(@NonNull Context context) {
        super(context);
    }

    public VideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.layout_video_controller;
    }

    @Override
    protected void initView() {
        super.initView();
        mLlthumb = mControllerView.findViewById(R.id.ll_thumb);
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);

        switch (playState) {
            case IjkVideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                mLlthumb.setVisibility(VISIBLE);
                break;
            case IjkVideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                mLlthumb.setVisibility(GONE);
                break;
            case IjkVideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                break;
        }
    }

//    public FrescoImageView getThumb() {
//
//        return thumb;
//    }
}
