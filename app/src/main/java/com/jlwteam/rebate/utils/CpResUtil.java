package com.jlwteam.rebate.utils;


import com.jlwteam.rebate.app.XJApp;
import com.jlwteam.rebate.R;

public class CpResUtil {

    private static int mTitleHeight;
    private static int mMainTabHeight;

    public static int getTitleBarHeight() {

        if (mTitleHeight == 0) {

            mTitleHeight = XJApp.getContext().getResources().getDimensionPixelSize(R.dimen.cp_title_bar_height);
        }
        return mTitleHeight;
    }

    public static int getMainTabHeight() {

        if (mMainTabHeight == 0) {

            mMainTabHeight = XJApp.getContext().getResources().getDimensionPixelSize(R.dimen.cp_page_main_act_menu_bar_height);
        }
        return mMainTabHeight;
    }
}
