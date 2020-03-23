package com.jlwteam.rebate.common.account;

import android.content.Context;
import android.content.SharedPreferences;

import com.androidex.prefs.ExSharedPrefs;
import com.androidex.util.TextUtil;

/**
 *  百川账号存储
 *
 * Create By DaYin(gaoyin_vip@126.com) on 2020/3/23 19:14
 */
public class AliAuthPrefs {

    private static final int LOGIN_TYPE_BAICHUAN = 1;
    private static final int LOGIN_TYPE_ALIWEB = 2;

    private final String KEY_TB_USER_ID = "tb_user_id";
    private final String KEY_TB_OPEN_ID = "tb_open_id";
    private final String KEY_TB_NICKNAME = "tb_nickname";
    private final String KEY_TB_AVATAR = "tb_avatar";
    private final String KEY_TB_SID = "s_id";
    private final String KEY_TB_LOGIN_TYPE = "tb_login_type";

    private static AliAuthPrefs mInstance;
    private ExSharedPrefs mExSharedPrefs;

    private AliAuthPrefs(Context context) {

        mExSharedPrefs = new ExSharedPrefs(context, "ali_account");
    }

    public static AliAuthPrefs getInstance(Context context) {

        if (mInstance == null)
            mInstance = new AliAuthPrefs(context);

        return mInstance;
    }

    private void release() {

        //nothing
    }

    public static void releaseInstance() {

        if (mInstance != null) {

            mInstance.release();
            mInstance = null;
        }
    }

    /**
     * 获取淘宝id
     *
     * @return
     */
    public String getUserId() {

        return mExSharedPrefs.getString(KEY_TB_USER_ID, TextUtil.TEXT_EMPTY);
    }

    /**
     * 获取openId
     *
     * @return
     */
    public String getOpenId() {

        return mExSharedPrefs.getString(KEY_TB_OPEN_ID, TextUtil.TEXT_EMPTY);
    }

    /**
     * 获取昵称
     *
     * @return
     */
    public String getNickName() {

        return mExSharedPrefs.getString(KEY_TB_NICKNAME, TextUtil.TEXT_EMPTY);
    }

    /**
     * 获取头像
     *
     * @return
     */
    public String getAvatar() {

        return mExSharedPrefs.getString(KEY_TB_AVATAR, TextUtil.TEXT_EMPTY);
    }

    /**
     * 获取头像
     *
     * @return
     */
    public String getSid() {

        return mExSharedPrefs.getString(KEY_TB_SID, TextUtil.TEXT_EMPTY);
    }

    public void clear() {

        SharedPreferences.Editor editor = mExSharedPrefs.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 存储淘宝授权信息
     *
     * @param userId
     * @param openId
     * @param nickName
     * @param avatar
     */
    public void saveAliWebLogin(String userId, String openId, String nickName, String avatar, String sid) {

        save(userId, openId, nickName, avatar, sid, LOGIN_TYPE_ALIWEB);
    }

    public void saveBaiChuanLogin(String userId, String openId, String nickName, String avatar) {

        save(userId, openId, nickName, avatar, LOGIN_TYPE_BAICHUAN);
    }

    private void save(String userId, String openId, String nickName, String avatar, int type) {

        SharedPreferences.Editor editor = mExSharedPrefs.edit();
        putCommonArgs(userId, openId, nickName, avatar, type, editor);
        editor.commit();
    }

    private void save(String userId, String openId, String nickName, String avatar, String sid, int type) {

        SharedPreferences.Editor editor = mExSharedPrefs.edit();
        putCommonArgs(userId, openId, nickName, avatar, type, editor);
        editor.putString(KEY_TB_SID, sid);
        editor.commit();
    }

    private void putCommonArgs(String userId, String openId, String nickName, String avatar, int type, SharedPreferences.Editor editor) {

        editor.putString(KEY_TB_USER_ID, userId);
        editor.putString(KEY_TB_OPEN_ID, openId);
        editor.putString(KEY_TB_NICKNAME, nickName);
        editor.putString(KEY_TB_AVATAR, avatar);
        editor.putInt(KEY_TB_LOGIN_TYPE, type);
    }
}
