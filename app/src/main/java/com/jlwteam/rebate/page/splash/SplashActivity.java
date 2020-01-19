package com.jlwteam.rebate.page.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.androidex.statusbar.StatusBarManager;
import com.androidex.util.CompatUtil;
import com.ex.sdk.android.expermissions.ExEasyPermissions;
import com.gyf.immersionbar.ImmersionBar;
import com.jlwteam.rebate.page.main.MainActivity;
import com.jzyd.lib.activity.JzydFragmentActivity;
import com.jlwteam.rebate.manager.permisssion.deviceid.SqkbDeviceIdManager;
import com.jlwteam.rebate.page.splash.fragment.SplashFragment;

import java.util.List;

/**
 * 启动页
 * Create By DaYin(gaoyin_vip@126.com) on 2019/5/30 7:33 PM
 */
public class SplashActivity extends JzydFragmentActivity implements
        ISplashPage.SplashPageListener {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private boolean mFirstLaunch;

    /*** 定位对象 */
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setCurPageSlidebackSupport(false);
        super.onCreate(savedInstanceState);
        onBeforeSetContentFragment();
        setContentFragmentAndLaunchTask();
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .autoDarkModeEnable(true)
                .keyboardEnable(false)
                .init();

        //初始化定位
        initLocation();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
//        stopLocation();
//        destroyLocation();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

    }

    @Override
    protected void initContentView() {

    }

    /**
     * 请求相关权限
     */
    private void requestPermissions() {

        try {

            SqkbDeviceIdManager.getInstance().requestPhoneStatePermission(this, new ExEasyPermissions.ExPermissionCallbacks() {
                @Override
                public void onAleadyHasOrAllPermissionsGranted(int i, @NonNull List<String> list, boolean aleadyHasAllPermissions) {

                    startLauncherTask();
                }

                @Override
                public void onAlertSystemPermissionDialogStat(int requestCode, @NonNull List<String> perms) {
                    super.onAlertSystemPermissionDialogStat(requestCode, perms);
                }

                @Override
                public void onAlertRationaleDialogStat(int requestCode, @NonNull List<String> perms) {
                    super.onAlertRationaleDialogStat(requestCode, perms);
                }

                @Override
                public void onAlertAppSettingsDialogStat(int requestCode, @NonNull List<String> perms) {
                    super.onAlertAppSettingsDialogStat(requestCode, perms);
                }

                @Override
                public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
                }

                @Override
                public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
                    requestPermissions();
                }
            });

        } catch (Exception e) {
            startLauncherTask();

        }
    }

    private void setContentFragmentAndLaunchTask() {

        setContentFragmentByLaunchTime();
    }

    private void onBeforeSetContentFragment() {

        hideNavitionBar();
    }

    /**
     * 隐藏底部导航栏
     */
    private void hideNavitionBar() {

        if (CompatUtil.isGreatThanOrEqualToIcsVersion()) {

            int uiFlag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().getDecorView().setSystemUiVisibility(uiFlag);
        }
    }

    private void setContentFragmentByLaunchTime() {

        SplashFragment fra = SplashFragment.newInstance(this, !mFirstLaunch);
        fra.setSplashPageListener(this);
        setContentFragment(fra);
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(false);
        // 设置是否单次定位
        locationOption.setOnceLocation(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(true);
        //设置是否使用传感器
        locationOption.setSensorEnable(true);

        String strTimeout = "4000";
        if (!TextUtils.isEmpty(strTimeout)) {
            try {
                // 设置网络请求超时时间
                locationOption.setHttpTimeOut(Long.valueOf(strTimeout));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 默认的定位参数
     *
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {

        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(10000);//可选，设置网络请求超时时间。默认为10秒。在仅设备模式下无效
        mOption.setInterval(5000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(true);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = location -> {
        if (null != location) {

            StringBuffer sb = new StringBuffer();
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.getErrorCode() == 0) {
                sb.append("定位成功" + "\n");
                sb.append("定位类型: " + location.getLocationType() + "\n");
                sb.append("经    度    : " + location.getLongitude() + "\n");
                sb.append("纬    度    : " + location.getLatitude() + "\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");

                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.getSatellites() + "\n");
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");
            } else {
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + location.getErrorCode() + "\n");
                sb.append("错误信息:" + location.getErrorInfo() + "\n");
                sb.append("错误描述:" + location.getLocationDetail() + "\n");
            }
            sb.append("***定位质量报告***").append("\n");
            sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
            sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
            sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
            sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
            sb.append("****************").append("\n");
            //定位之后的回调时间

            //解析定位结果，
            String result = sb.toString();
            Log.i(TAG, "定位结果：" + result);
//            ToastUtil.showToast(result);
        } else {

            Log.i(TAG, "定位失败");
//            ToastUtil.showToast("定位失败，loc is null");
        }
    };

    /**
     * 跳转activity
     */
    @Override
    public void onForwardMainActivity(String adUrl, boolean isAd) {

        requestPermissions();
    }

    private void startLauncherTask() {

        StatusBarManager.getInstance().setStatusbarEnable(true);
        MainActivity.startActivityForIndex(this);
        finish();
    }
}
