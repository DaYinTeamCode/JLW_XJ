package com.jlwteam.rebate.page.coupon.home.page.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainHomePageFrameLayout extends FrameLayout {

    public MainHomePageFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MainHomePageFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainHomePageFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void resetViewHeightIfChanged(int viewHeight) {

        ViewGroup.LayoutParams vglp;
        vglp = getLayoutParams();
        if (vglp != null && vglp.height != viewHeight) {

            vglp.height = viewHeight;
            setLayoutParams(vglp);
        }
    }

    public void resetMarginTopIfChanged(int marginTop) {

        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        if (mlp != null && mlp.topMargin != marginTop) {

            mlp.topMargin = marginTop;
            setLayoutParams(mlp);
        }
    }
}
