package com.jlwteam.rebate.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.androidex.util.DensityUtil;

public class CpDrawableUtil {

    public static GradientDrawable getRectDrawable(float radius, int color) {

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setColor(color);
        return gd;
    }

    /**
     * 获取带圆角线框 drawable
     *
     * @param radius     圆角
     * @param borderSize 线框大小
     * @param color      线框颜色
     * @return
     */
    public static Drawable getRectBorderDrawable(int radius, int borderSize, int color) {

        return GradientDrawable(borderSize, color, Color.TRANSPARENT, radius);
    }

    /**
     * 获取闪屏跳过背景
     *
     * @return
     */
    public static GradientDrawable getSplashSkipBg() {

        return GradientDrawable(6, 0X66FFFFFF, 0X66000000, DensityUtil.dip2px(16));
    }

    public static GradientDrawable GradientDrawable(int strokeWidth, int strokeColor, int fillColor, int roundRadius) {

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    /**
     * 获取搜索输入背景
     *
     * @param radius
     * @return
     */
    public static GradientDrawable getTitleSearchEditBg(int radius) {

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setColor(0XFFEDEDED);
        return gd;
    }

    public static GradientDrawable getTitleSearchBtnBg(int radius) {

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setColor(0XFFFE6564);
        return gd;
    }

    /**
     * tag标签背景
     *
     * @return
     */
    public static GradientDrawable getSearchTagBg() {

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(DensityUtil.dip2px(2));
        gd.setColor(0XFFF4F4F4);
        return gd;
    }

    /**
     * 获取渐变
     *
     * @param startColor
     * @param endColor
     * @return
     */
    public static GradientDrawable getLinearGradientBg(int startColor, int endColor) {

        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{startColor, endColor});
        return gd;
    }

}
