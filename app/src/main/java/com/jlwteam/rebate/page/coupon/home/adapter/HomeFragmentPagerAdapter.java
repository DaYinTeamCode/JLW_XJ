package com.jlwteam.rebate.page.coupon.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.androidex.adapter.ExFragmentPagerStateAdapter;
import com.androidex.util.LogMgr;
import com.androidex.util.TextUtil;
import com.androidex.util.ViewUtil;
import com.jlwteam.rebate.page.coupon.home.bean.HomePageCacheInfo;
import com.jlwteam.rebate.page.coupon.home.category.bean.CateCollection;
import com.jlwteam.rebate.page.coupon.home.listener.IHomePager;
import com.jlwteam.rebate.page.coupon.home.rec.HomePageRecListFragment;

import java.util.HashMap;
import java.util.Map;


public class HomeFragmentPagerAdapter extends ExFragmentPagerStateAdapter<CateCollection> {

    private int mCacheId;
    private Map<String, HomePageCacheInfo> mCacheMap;
    private IHomePager.Listener mHomePageLisn;
    private HomePageCacheInfo mRecCateCacheInfo;
    private boolean mGenderIsMan;
    private int mRecPosition;
    private boolean mStatCanShow;
    private boolean mFromCache;

    public HomeFragmentPagerAdapter(Context context, FragmentManager fmtMgr) {

        super(context, fmtMgr);
        mCacheMap = new HashMap<String, HomePageCacheInfo>();
    }

    public void setItemStatCanShow(boolean canShow) {

        mStatCanShow = canShow;
    }

    public void setFromCache(boolean fromCache) {

        this.mFromCache = fromCache;
    }

    @Override
    public Fragment getItem(int position) {

        CateCollection category = getDataItem(position);
        IHomePager fra;

        if (category.isLocalRecType()) {

            fra = HomePageRecListFragment.newInstance(getContext(), position, mGenderIsMan, category.getCate_collection_id(), category.getName(), mStatCanShow, mFromCache);
            fra.setHomePageCacheInfo(mCacheId, mRecCateCacheInfo == null ? removeCache(String.valueOf(position)) : mRecCateCacheInfo);
            mRecPosition = position;
            mRecCateCacheInfo = null;
        } else {

            fra = HomePageRecListFragment.newInstance(getContext(), position, mGenderIsMan, category.getCate_collection_id(), category.getName(), mStatCanShow, mFromCache);
            fra.setHomePageCacheInfo(mCacheId, mRecCateCacheInfo == null ? removeCache(String.valueOf(position)) : mRecCateCacheInfo);
            mRecPosition = position;
        }
        fra.setHomePageListener(mHomePageLisn);
        return (Fragment) fra;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return getDataItem(position).getName();
    }

    @Override
    public void notifyDataSetChanged() {

        //清除缓存，修改cacheId
        mCacheMap.clear();
        mCacheId++;
        super.notifyDataSetChanged();
    }

    /**
     * 当销毁fragment时，做两件事情：
     * 1.保存fragment的数据和页码
     * 2.清除插入的运营位数据
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        super.destroyItem(container, position, object);

        CateCollection category = getDataItem(position);
        if (object instanceof IHomePager) {
            IHomePager fra = (IHomePager) object;
            if (category != null && fra != null && fra.getHomePageCacheId() == mCacheId) {
                putCache(String.valueOf(position), fra.getHomePageCacheInfo());
            }

            if (LogMgr.isDebug()) {
                LogMgr.d(simpleTag(), "HomeFraPagerAdapter destroyItem pos= " + position + ", cacheId=" + mCacheId + ", fra cacheId=" + fra.getHomePageCacheId() + ", fra=" + fra);
            }
        }


    }

    /**
     * 设置推荐分类的缓存数据
     *
     * @param cacheInfo
     */
    public void setRecCatePageCacheInfo(HomePageCacheInfo cacheInfo) {

        mRecCateCacheInfo = cacheInfo;
    }

    /**
     * 设置页面监听器
     *
     * @param lisn
     */
    public void setHomePageListener(IHomePager.Listener lisn) {

        mHomePageLisn = lisn;
    }

    /**
     * 设置性别是否是男性
     *
     * @param genderIsMan
     */
    public void setGenderIsMan(boolean genderIsMan) {

        mGenderIsMan = genderIsMan;
    }

//    /**
//     * 停止home主框架刷新
//     *
//     * @param vp
//     * @param pos
//     * @param result
//     */
//    public void stopHomeFrameActionRefresh(ViewPager vp, int pos, boolean success, HomeResult result) {
//
//        if (vp != null) {
//
//            IHomePager fra = (IHomePager) instantiateItem(vp, pos);
//            if (fra != null)
//                fra.stopHomeFrameActionRefresh(success, result);
//        }
//    }

    public IHomePager getFragment(ViewPager viewPager, int position) {

        return (IHomePager) instantiateItem(viewPager, position);
    }

    private boolean putCache(String key, HomePageCacheInfo pageInfo) {

        if (TextUtil.isEmpty(key) || pageInfo == null) {

            return false;
        } else {

            mCacheMap.put(key, pageInfo);
            return true;
        }
    }

    /**
     * 获取精选缓存
     *
     * @return
     */
    public HomePageCacheInfo getRecCateCacheDatas() {

        return mCacheMap != null ? mCacheMap.get(mRecPosition) : null;
    }

    private HomePageCacheInfo removeCache(String key) {

        return TextUtil.isEmpty(key) ? null : mCacheMap.remove(key);
    }

    private HomePageCacheInfo getCache(String key) {

        return TextUtil.isEmpty(key) ? null : mCacheMap.get(key);
    }

    public void scrollTop(ViewPager viewPager) {

        if (viewPager == null)
            return;

        //内存缓存页面滚动至顶部
        scrollMemoryPageToTop(viewPager);

        //清除其他页缓存状态
        ViewUtil.clearViewPagerState(this);
    }

    private void scrollMemoryPageToTop(ViewPager viewPager) {

        int postion = viewPager.getCurrentItem();
        int limit = viewPager.getOffscreenPageLimit();
        for (int i = postion - limit; i <= postion + limit; i++) {

            if (i >= 0 && i < getCount())
                scrollPageToTop(viewPager, i);
        }
    }

    private void scrollPageToTop(ViewPager viewPager, int postion) {

        IHomePager fra = getFragmentByPosition(viewPager, postion);
        if (fra != null)
            fra.scrollTop();
    }

    public IHomePager getFragmentByPosition(ViewPager viewPager, int position) {

        return (IHomePager) instantiateItem(viewPager, position);
    }
}
