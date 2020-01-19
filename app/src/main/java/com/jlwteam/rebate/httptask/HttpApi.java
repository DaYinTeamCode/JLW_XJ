package com.jlwteam.rebate.httptask;

/**
 * 服务端API接口
 * Create By DaYin(gaoyin_vip@126.com) on 2019/7/11 3:40 PM
 */
public interface HttpApi {

    /***api服务器 */
    String API_ONLINE_DOMAIN = "http://api.xiamiservice.com";

    /*** 获取小视频Feeds流*/
    String URL_VIDEO_FEEDS = "/api/reptile/media/info/selectPage";

    /*** 获取微信授权登录信息*/
    String URL_WX_AUTH = "/api/user/weichat/login";
}
