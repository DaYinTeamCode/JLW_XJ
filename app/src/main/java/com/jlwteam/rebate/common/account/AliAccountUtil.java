package com.jlwteam.rebate.common.account;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.androidex.util.ClipBoardUtil;
import com.androidex.util.TextUtil;
import com.jlwteam.rebate.app.XJApp;

/**
 * 百川账户帮助类
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2020/3/23 19:26
 */
public class AliAccountUtil {

    ///////////////////////////////////////////////////////////////////////////
    // 对外统一api，屏蔽了百川登录和web授权
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 是否是web授权模式
     *
     * @return true 未安装手淘，且命中走淘宝h5授权 否则为false 走百川授权
     */
    public static boolean isWebAuthMode() {

        return false;
    }

    /**
     * 获取淘宝用户id
     *
     * @return
     */
    public static String getUserId() {

        return XJApp.getAliAuthPrefs().getUserId();
    }

    /**
     * 获取淘宝openId
     * 百川有openId
     * v2.10.30 oss授权有openId，但是没存
     * 这里只返百川的openIdø
     *
     * @return
     */
    public static String getOpenId() {

        return XJApp.getAliAuthPrefs().getOpenId();
    }

    /**
     * 获取淘宝账号昵称
     * 如果百川里没有则从cookie中获取
     *
     * @return
     */
    public static String getNickName() {

        return XJApp.getAliAuthPrefs().getNickName();
    }

    /**
     * 获取淘宝用户头像 cookie方式还没做
     *
     * @return
     */
    public static String getAvatar() {

        return XJApp.getAliAuthPrefs().getAvatar();
    }

    /**
     * 淘宝sid
     *
     * @return
     */
    public static String getTbSid() {

        return XJApp.getAliAuthPrefs().getSid();
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {

        return !TextUtil.isEmpty(getAuthUserid());
    }

    /**
     * 获取最后一次登录账户的淘宝用户id
     * 首先取当前的，当前没有则取存储的
     *
     * @return
     */
    public static String getLastLoginUid() {

        String aliUid = getUserId();
        if (TextUtil.isEmpty(aliUid)) {

//            aliUid = CpApp.getCommonPrefs().getLastAliUid();
        }

        return aliUid;
    }

    /**
     * 获取最后一次登录账户的淘宝用户昵称
     * 首先取当前的，当前没有则取存储的
     *
     * @return
     */
    public static String getLastLoginNickName() {

        String nickName = getNickName();
        if (TextUtil.isEmpty(nickName)) {

//            nickName = CpApp.getCommonPrefs().getLastAliNickname();
        }

        return nickName;
    }

    ///////////////////////////////////////////////////////////////////////////
    // web oss 授权 api
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取webauth的淘宝用户id
     *
     * @return
     */
    private static String getAuthUserid() {

        return XJApp.getAliAuthPrefs().getUserId();
    }

    /**
     * webauth是否登录
     *
     * @return
     */
    public static boolean isWebAuthLogin() {

        return !TextUtil.isEmpty(getAuthUserid());
    }

    /**
     * 存储淘宝web auth授权信息
     *
     * @param userId
     * @param openId
     * @param nickName
     * @param avatar
     * @param sid
     */
    public static void saveWebAuthInfo(String userId, String openId, String nickName, String avatar, String sid) {

        XJApp.getAliAuthPrefs().saveAliWebLogin(userId, openId, nickName, avatar, sid);
    }

    /**
     * 清除web授权信息
     */
    private static void clearWebAuthInfo() {

        XJApp.getAliAuthPrefs().clear();
    }

    // TODO
//    /**
//     * 调起web授权
//     *
//     * @param activity
//     * @param pingbackPage
//     * @param callback
//     */
//    public static void loginByWebAuth(Activity activity, String key, int version, AlCallback callback) {

//        AliAuthorizeAct.startActivity(activity, key, version, null, pingbackPage, callback);
//    }

    ///////////////////////////////////////////////////////////////////////////
    // 百川api
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 返回百川登录信息中的openId
     *
     * @return
     */
    public static String getBaichuanOpenId() {

        Session session = getBaichuanSession();
        return session == null ? TextUtil.TEXT_EMPTY : TextUtil.filterNull(session.openId);
    }

    /**
     * 百川没有淘宝uid，从cookie中取
     * 百川登录后，cookie里也有uid
     *
     * @return
     */
    private static String getBaichuanUid() {

        Session session = getBaichuanSession();
        String uid = session == null ? TextUtil.TEXT_EMPTY : TextUtil.filterNull(session.userid);
        return uid;
    }

    public static String getAccessToken() {

        Session session = getBaichuanSession();
        String token = session == null ? TextUtil.TEXT_EMPTY : TextUtil.filterNull(session.topAccessToken);
        return token;
    }

    public static String getAuthCode() {

        Session session = getBaichuanSession();
        return session == null ? TextUtil.TEXT_EMPTY : TextUtil.filterNull(session.topAuthCode);
    }

    /**
     * 从百川中获取昵称
     *
     * @return
     */
    private static String getBaichuanNickName() {

        Session session = getBaichuanSession();
        return session == null ? TextUtil.TEXT_EMPTY : TextUtil.filterNull(session.nick);
    }

    /**
     * 从百川中获取头像
     *
     * @return
     */
    private static String getBaichuanAvatar() {

        Session session = getBaichuanSession();
        return session == null ? TextUtil.TEXT_EMPTY : TextUtil.filterNull(session.avatarUrl);
    }

    /**
     * 获取百川session
     *
     * @return
     */
    public static Session getBaichuanSession() {

        try {
            return AlibcLogin.getInstance().getSession();
        } catch (Throwable t) {
        }
        return null;
    }

    /**
     * 判断百川是否登录
     * 用的是百川sdk的是否登录标识
     *
     * @return
     */
    public static boolean isBaichuanSdkLogin() {

        try {
            return AlibcLogin.getInstance().isLogin();
        } catch (Throwable e) {
        }
        return false;
    }

    /**
     * 调起百川授权页
     *
     * @param callback
     * @return
     */
    public static boolean loginByBaichuan(final AlibcLoginCallback callback) {

        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
        if (alibcLogin == null)
            return false;

        final long startTime = System.currentTimeMillis();

        //缓存剪切板内容
        final CharSequence clipTextCache = ClipBoardUtil.getText();
        //淘宝登录页让标搜剪切板停止工作，产品说登录页不弹标搜
//        final boolean tbtWorking = TbtMgr.getInstance().isWorking();
//        if (tbtWorking) {
//            TbtMgr.getInstance().setWorkingState(false);
//        }

        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int result, String userId, String nick) {

                //更新存储淘宝信息
                saveBaiChuanAuthInfo();

                //将淘宝uid种在省钱域下的cookie里面
//                CpCookieMgr.getInstance().setTaobaoAccountCookie(getBaichuanUid());
//                CpCookieMgr.getInstance().resetSyncState();

                //如果被清空了，使用缓存的剪切板内容（淘宝登录页会消费剪切板里的淘宝链接文本，导致登录完成时无法触发标搜弹窗）
//                if (!TextUtil.isEmpty(clipTextCache) && TextUtil.isEmpty(ClipBoardUtil.getText())) {
//                    ClipBoardUtil.copyText(clipTextCache);
//                }

                //标搜恢复之前的工作状态, 该回调在切换到前后台才收到
//                TbtMgr.getInstance().setWorkingState(tbtWorking);
//                if (TbtMgr.getInstance().isWorking()) {
//                    TbtMgr.getInstance().performWorkByState();
//                }

                //外部回调
//                AliAccountSyncer.get().postLogin();
                if (callback != null)
                    callback.onSuccess(result, userId, nick);
            }

            @Override
            public void onFailure(int code, String msg) {

                //如果被清空了，使用缓存的剪切板内容（淘宝登录页会消费剪切板里的淘宝链接文本，导致登录完成时无法触发标搜弹窗）
//                if (!TextUtil.isEmpty(clipTextCache) && TextUtil.isEmpty(ClipBoardUtil.getText())) {
//                    ClipBoardUtil.copyText(clipTextCache);
//                }

//                //标搜恢复之前的工作状态, 该回调在切换到前后台才收到
//                TbtMgr.getInstance().setWorkingState(tbtWorking);
//                if (TbtMgr.getInstance().isWorking()) {
//                    TbtMgr.getInstance().performWorkByState();
//                }

                if (callback != null)
                    callback.onFailure(code, msg);
            }
        });
        return true;
    }

    /**
     * 注销百川账号
     *
     * @param callback
     */
    public static void logout(final AlibcLoginCallback callback) {

        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        if (alibcLogin == null)
            return;

        //存储要注销的淘宝账号
        final String uid = getUserId();
//        CpApp.getCommonPrefs().saveLastAliUid(uid);
//        CpApp.getCommonPrefs().saveLastAliNickname(getNickName());

        alibcLogin.logout(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i, String s, String s1) {

                //2.10.30新增，清空web授权记录的账号信息
                clearWebAuthInfo();

                if (AlibcLogin.getInstance() != null && AlibcLogin.getInstance().getSession() != null) {

                    AlibcLogin.getInstance().getSession().openId = null;
                }

                //百川登出时所会清空所有cookie，所以这里重新种一次
//                CpCookieMgr.getInstance().setTaobaoAccountCookie(null);
//                CpCookieMgr.getInstance().resetSyncState();

                //外部回调
//                AliAccountSyncer.get().postLogout();
                if (callback != null)
                    callback.onSuccess(i, s, s1);
            }

            @Override
            public void onFailure(int code, String msg) {

                if (callback != null)
                    callback.onFailure(code, msg);
            }
        });
    }

    /**
     * 保存百川授权信息至文件
     * <p>
     * author : pzwwei
     *
     * @date : 2020-01-15 17:30
     * @since V2.30.00
     */
    public static void saveBaiChuanAuthInfo() {

        XJApp.getAliAuthPrefs().saveBaiChuanLogin(getBaichuanUid(),
                getBaichuanOpenId(),
                getBaichuanNickName(),
                getBaichuanAvatar());
    }

    private static String simpleTag() {

        return AliAccountUtil.class.getSimpleName();
    }
}
