package com.jlwteam.rebate.page.coupon.home.bean;

import com.androidex.util.CollectionUtil;

import java.util.List;


public class HomePageCacheInfo {

    private List<?> listData;
    private boolean fromPage;
    private int pageNum;
    private boolean pageIsOver;
    private int sortType;
    private int mCouponPageNum;
    private boolean mCouponPageMode;
    private boolean needToScrollTop;
    private boolean headIsVisible;
    private int couponPopIndex;
    private boolean isHomeCache;//是否是缓存数据

    public List<?> getListData() {

        return listData;
    }

    public void setListData(List<?> listData) {

        this.listData = listData;
    }

    public boolean isFromPage() {

        return fromPage;
    }

    public void setFromPage(boolean fromPage) {

        this.fromPage = fromPage;
    }

    public int getPageNum() {

        return pageNum;
    }

    public void setPageNum(int pageNum) {

        this.pageNum = pageNum;
    }

    public boolean isListDataEmpty() {

        return CollectionUtil.isEmpty(listData);
    }

    public int getSortType() {

        return sortType;
    }

    public void setSortType(int sortType) {

        this.sortType = sortType;
    }

    public int getCouponPageNum() {

        return mCouponPageNum;
    }

    public void setCouponPageNum(int couponPageNum) {

        this.mCouponPageNum = couponPageNum;
    }

    public boolean getCouponPageMode() {

        return mCouponPageMode;
    }

    public void setCouponPageMode(boolean couponPageMode) {

        this.mCouponPageMode = couponPageMode;
    }

    public boolean isNeedToScrollTop() {

        return needToScrollTop;
    }

    public void setNeedToScrollTop(boolean needToScrollTop) {

        this.needToScrollTop = needToScrollTop;
    }

    public boolean headIsVisible() {

        return headIsVisible;
    }

    public void setHeadIsVisible(boolean headIsVisible) {

        this.headIsVisible = headIsVisible;
    }


    public int getCouponPopIndex() {

        return couponPopIndex;
    }

    public void setCouponPopIndex(int couponPopIndex) {

        this.couponPopIndex = couponPopIndex;
    }

    public boolean isPageIsOver() {

        return pageIsOver;
    }

    public void setPageIsOver(boolean pageIsOver) {

        this.pageIsOver = pageIsOver;
    }

    public boolean isHomeCache() {

        return isHomeCache;
    }

    public void setIsHomeCache(boolean cache) {

        isHomeCache = cache;
    }
}
