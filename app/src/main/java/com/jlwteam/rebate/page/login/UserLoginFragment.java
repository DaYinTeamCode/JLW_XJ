package com.jlwteam.rebate.page.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.androidex.util.LogMgr;
import com.androidex.util.TextUtil;
import com.ex.android.http.task.HttpTask;
import com.jlwteam.rebate.app.AppConfig;
import com.jlwteam.rebate.common.account.AliAccountUtil;
import com.jzyd.lib.httptask.ExResponse;
import com.jzyd.lib.httptask.HttpFrameParams;
import com.jzyd.lib.httptask.JzydJsonListener;
import com.jlwteam.rebate.R;
import com.jlwteam.rebate.base.aframe.HttpFrameFragment;
import com.jlwteam.rebate.page.login.bean.WxBind;
import com.jlwteam.rebate.page.login.prefs.AccountPrefs;
import com.jlwteam.rebate.page.login.utils.LoginHttpUtils;
import com.jlwteam.rebate.syncer.EventBusUtils;
import com.jlwteam.rebate.view.toast.ExToast;
import com.jlwteam.rebate.wxapi.WXEventListner;
import com.jlwteam.rebate.wxapi.manager.WXManagerHandler;
import com.jlwteam.rebate.wxapi.manager.WeChatUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/24 11:30 PM
 */
public class UserLoginFragment extends HttpFrameFragment {

    private Unbinder unbinder;
    private static HttpTask mHttpTask;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.fragment_user_login);
    }

    @Override
    protected void onSupportShowToUserChanged(boolean isShowToUser, int from) {

        super.onSupportShowToUserChanged(isShowToUser, from);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

    }

    @Override
    protected void initContentView() {

        unbinder = ButterKnife.bind(this, getExDecorView());
        getExDecorView().setBackgroundColor(0XFFFFFFFF);
    }

    @OnClick(R.id.ivBack)
    public void backClick() {

        finishActivity();
    }

    /***
     *   微信授权登录
     */
    @OnClick(R.id.tvLogin)
    public void onLogin() {

        startAliLogin();
    }

    /***
     *   阿里百川登录
     *
     */
    private void startAliLogin() {

        AliAccountUtil.loginByBaichuan(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int result, String userId, String nick) {

                Log.i("UserLogin",
                        String.format("登录成功 登录结果：%s 用户ID：%s 用户昵称：%s", result, userId, nick));
            }

            @Override
            public void onFailure(int code, String message) {

                Log.i("UserLogin",
                        String.format("登录失败 错误吗%s  错误信息：%s", code, message));
            }
        });
    }

    /**
     * 开始绑定微信
     */
    private void startBindWx() {

        // 微信回调
        WXManagerHandler.getInstance().register(new WXEventListner() {

            @Override
            public void onResp(BaseResp baseResp) {

                if (baseResp instanceof SendAuth.Resp) {

                    SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                    if (resp != null && !TextUtil.isEmpty(resp.code)) {

                        performWxBind(resp.code);
                        // 微信授权成功回调
                        LogMgr.i("UserLoginFragment", resp.code + " " + resp.country);
                    }
                }
            }

            @Override
            public void onReq(BaseReq baseReq) {

            }
        });
        /*** 微信授权 */
        WeChatUtils.authWeChat(getActivity(), AppConfig.wxKey);
    }

    /*---------------------------------------- 网络监听回调 ----------------------------------------*/

    /**
     * 用户微信绑定
     *
     * @param code
     */
    public void performWxBind(final String code) {

        if (mHttpTask != null && mHttpTask.isRunning()) {

            return;
        }

        final JzydJsonListener<WxBind> lisn = new JzydJsonListener<WxBind>(WxBind.class) {

            @Override
            public void onTaskSuccess(ExResponse<WxBind> resp) {

                super.onTaskSuccess(resp);
            }

            @Override
            public void onTaskResult(WxBind result) {

                if (result != null) {

                    saveWeixinInfo(result);
                } else {

                    ExToast.makeText("授权失败，请重试！");
                }

            }

            @Override
            public void onTaskFailed(int failedCode, String msg) {

                mHttpTask = null;
                ExToast.makeText("授权失败，请重试！");
            }
        };

        mHttpTask = new HttpTask();
        mHttpTask.setHttpTaskParams(LoginHttpUtils.getWxchatBindParams(code));
        mHttpTask.setListener(lisn);
        mHttpTask.execute();
    }

    /***
     *
     * @param wxBind
     */
    private void saveWeixinInfo(WxBind wxBind) {

        if (wxBind != null) {

            AccountPrefs.getInstance().saveWechatInfo(wxBind.getToken()
                    , wxBind.getUnionId()
                    , wxBind.getNickName()
                    , wxBind.getHeadImageUrl());

            /*** 发送EventBus 事件 */
            EventBusUtils.post(wxBind);
            if (getActivity() != null) {

                getActivity().finish();
            }
        }
    }

    @Override
    protected HttpFrameParams getHttpParamsOnFrameExecute(Object... params) {

        return null;
    }

    @Override
    protected boolean invalidateContent(Object result) {

        return false;
    }

    /**
     * 实例化 Fra
     *
     * @param context
     * @return
     */
    public static UserLoginFragment newInstance(Context context) {

        Bundle bundle = new Bundle();
        return (UserLoginFragment) Fragment.instantiate(context, UserLoginFragment.class.getName(), bundle);
    }
}
