package com.jlwteam.rebate.page.me;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidex.plugin.ExBaseWidget;
import com.androidex.util.DensityUtil;
import com.androidex.util.DeviceUtil;
import com.androidex.util.ExResUtil;
import com.androidex.util.ToastUtil;
import com.androidex.widget.rv.lisn.item.OnExRvItemViewClickListener;
import com.jlwteam.rebate.app.XJApp;
import com.jzyd.lib.httptask.HttpFrameParams;
import com.jlwteam.rebate.R;
import com.jlwteam.rebate.dialog.CpConfirmDialog;
import com.jlwteam.rebate.base.aframe.viewer.CpHttpFrameXrvFragmentViewer;
import com.jlwteam.rebate.page.login.UserLoginActivity;
import com.jlwteam.rebate.page.login.bean.WxBind;
import com.jlwteam.rebate.page.login.prefs.AccountPrefs;
import com.jlwteam.rebate.page.me.adapter.MainUserAdapter;
import com.jlwteam.rebate.page.me.bean.UserItemSet;
import com.jlwteam.rebate.page.me.decoration.MainUserItemDecoration;
import com.jlwteam.rebate.page.me.utils.MainUserDataUtil;
import com.jlwteam.rebate.page.me.widget.MainUserHeaderWidget;
import com.jlwteam.rebate.page.setting.AboutActivity;
import com.jlwteam.rebate.page.web.activity.BrowserActivity;
import com.jlwteam.rebate.syncer.EventBusUtils;
import com.jlwteam.rebate.utils.CpFontUtil;
import com.jlwteam.rebate.widget.TitleTransWidget;
import com.tencent.bugly.beta.Beta;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 用户中心Fragment
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/11 4:34 PM
 */
public class MainUserFragment extends CpHttpFrameXrvFragmentViewer implements OnExRvItemViewClickListener, ExBaseWidget.OnWidgetViewClickListener {

    private MainUserHeaderWidget mMainUserHeaderWidget;
    private TitleTransWidget mTitleWidget;
    private MainUserAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        setContentRecyclerView();
        showContent();
        getExDecorView().setBackgroundResource(R.color.cp_title_bar_bg);
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    @Override
    protected HttpFrameParams getHttpParamsOnFrameExecute(Object... params) {

        return null;
    }

    @Override
    protected boolean invalidateContent(Object result) {

        return true;
    }

    @Override
    protected void initData() {

        mAdapter = new MainUserAdapter();
        mAdapter.setOnExRvItemViewClickListener(this);
    }

    @Override
    protected void initTitleView() {

        mTitleWidget = new TitleTransWidget(getActivity(), getExDecorView(), false);
        mTitleWidget.setTitleViewBg(new ColorDrawable(Color.TRANSPARENT), ExResUtil.getDrawable(R.drawable.cp_title_bar_bg));
        mTitleWidget.setAlpha(0);

        TextView tvTitle = addTitleMiddleTextView("我的");
        tvTitle.setTextColor(R.drawable.cp_white);
        CpFontUtil.setFont(tvTitle);
        getTitleView().setClickable(false);
        setStatusbarView(mTitleWidget.getContentView());
    }

    @Override
    protected void initContentView() {

        mMainUserHeaderWidget = new MainUserHeaderWidget(getActivity());
        mMainUserHeaderWidget.setOnWidgetViewClickListener(this);
        WxBind wxBind = AccountPrefs.getInstance().getUserInfo();
        mMainUserHeaderWidget.invalidateContentView(wxBind);
        mAdapter.addHeaderView(mMainUserHeaderWidget.getContentView());

        mLayoutManager = new GridLayoutManager(getActivity(), 4);
        getRecyclerView().setLayoutManager(mLayoutManager);
        getRecyclerView().setGridSpanSizeLookUp(this::getGridSpanCount);
        getRecyclerView().addItemDecoration(new MainUserItemDecoration());
        getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                onRecyclerViewScroll();
            }
        });

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getRecyclerView().getLayoutParams();
        params.bottomMargin = DensityUtil.dip2px(48f);
        getRecyclerView().setLayoutParams(params);

        getRecyclerView().setAdapter(mAdapter);
        List<Object> objects = MainUserDataUtil.getUserItemDatas();
        mAdapter.setData(objects);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void invalidateFrame(Object data) {

    }

    @Override
    public void onLoadFrameFailed(int failedCode, String msg) {

    }

    @Override
    public void invalidateLoadMore(Object data) {

    }

    @Override
    public void invalidatePullRefresh(Object data) {

    }

    @Override
    public void onRefreshFailed(int failedCode, String msg) {

    }

    /**
     * 微信绑定
     *
     * @param wxBind
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxBindEvent(WxBind wxBind) {

        if (!isFinishing() && wxBind != null && mMainUserHeaderWidget != null) {

            mMainUserHeaderWidget.invalidateContentView(wxBind);
        }
    }

    @Override
    public void onWidgetViewClick(View v) {

        if (v.getId() == R.id.tvName) {

            onUserLoginOrLogOutClick();
        }
    }

    /***
     * 用户登录登录或者登出
     */
    private void onUserLoginOrLogOutClick() {

        if (AccountPrefs.getInstance().isLogin()) {

            showLogoutDialog();
        } else {

            UserLoginActivity.startActivity(getActivity());
        }
    }

    private void showLogoutDialog() {

        CpConfirmDialog dialog = new CpConfirmDialog(getActivity());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentText(" 是否退出登录？");
        dialog.setLeftButtonText("确定");
        dialog.setLeftButtonClickListener(dialog12 -> {

            AccountPrefs.getInstance().logOut();
            EventBusUtils.post(new WxBind());
            dialog12.dismiss();

        });

        dialog.setRightButtonText("取消");
        dialog.setRightButtonClickListener(dialog1 -> dialog1.dismiss());
        dialog.show();
    }

    /**
     * 列表点击
     *
     * @param view
     * @param dataPos
     */
    @Override
    public void onExRvItemViewClick(View view, int dataPos) {

        Object object = mAdapter.getDataItem(dataPos);

        if (object instanceof UserItemSet) {

            switch (((UserItemSet) object).getItemType()) {

                case UserItemSet.ABOUT_APP_TYPE:
                    onUserAboutAppClick();
                    break;
                case UserItemSet.SHREAD_APP_TYPE:
                    shreadAppClick();
                    break;
                case UserItemSet.SMALL_GAME_TYPE:
                    smallGameClick();
                    break;
                case UserItemSet.CHECK_UPDATE_APP:
                    onCheckUpdateAppClick();
                    break;
                case UserItemSet.HELP_FEED_BACK_TYPE:
                    onUserFeedBackClick();
                    break;
                case UserItemSet.PRAISE_TYPE:
                    onMarketpRraiseClick();
                    break;
                default:
                    break;
            }
        }
    }

    /***
     *  五星好评
     */
    private void onMarketpRraiseClick() {

        new Handler(Looper.getMainLooper()).post(() -> {

            String packageName = XJApp.getContext().getPackageName();
            if (TextUtils.isEmpty(packageName.trim())) {

                packageName = "com.sjteam.weiguan";
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {

                intent.setPackage(DeviceUtil.getBrandMarketPkg());
                XJApp.getContext().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    XJApp.getContext().startActivity(intent);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    Toast.makeText(XJApp.getContext(), "未发现应用市场", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /***
     *   APP关于
     */
    private void onUserAboutAppClick() {

        AboutActivity.startActivity(getActivity());
    }

    /***
     *  分享微信好友
     */
    private void shreadAppClick() {

        showToast("分享app给好友");
    }

    /***
     * /*  用户反馈
     *  *
     *  * 发起添加群流程。群号：微观短视频(421711174) 的 key 为： 6P5YFAOM7muXE0lENugMHGQ2cHfm3ucG
     *  * 调用 joinQQGroup(6P5YFAOM7muXE0lENugMHGQ2cHfm3ucG) 即可发起手Q客户端申请加群 微观短视频(421711174)
     *  *
     *  * @param key 由官网生成的key
     *  * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     * ***/
    private void onUserFeedBackClick() {

        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "6P5YFAOM7muXE0lENugMHGQ2cHfm3ucG"));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
        } catch (Exception e) {

            // 未安装手Q或安装的版本不支持
            ToastUtil.showToast("未安装手Q或安装的版本不支持");
        }
    }

    /***
     *  检测更新
     */
    private void onCheckUpdateAppClick() {

        /*** 检测更新版本 */
        try {

            Beta.checkUpgrade(true, false);
        } catch (Throwable ex) {

        }
    }

    /***
     * 小游戏
     */
    private void smallGameClick() {

        String path = "https://m.cudaojia.com/distt/welfareAT02/private/T/T094/index.html?business=money-1&appkey=45cc2d9c0d144632b96f73231e48117f&uid=96196731327A1B11F5B2F11741194B07&activityid=15025";
        BrowserActivity.startActivity(getContext(), path);
    }

    private void onRecyclerViewScroll() {

        if (mAdapter == null) {

            return;
        }
        float headerTop = mAdapter.getHeader().getConvertView().getTop();
        //从往上滚动48dp 后再做渐变
        headerTop = headerTop + DensityUtil.dip2px(48);
        int alpha = (int) (-headerTop / DensityUtil.dip2px(40f) * 255);
        mTitleWidget.setAlpha(alpha);
    }

    private int getGridSpanCount(int position) {

        int type = mAdapter.getDataItemViewType(position);
        switch (type) {
            default:
                return mLayoutManager.getSpanCount();
        }
    }

    public static MainUserFragment newInstance(Context context) {

        return (MainUserFragment) Fragment.instantiate(context, MainUserFragment.class.getName());
    }
}
