package com.jlwteam.rebate.base.aframe.presenter;


import com.jlwteam.rebate.base.aframe.modeler.CpHttpFrameXrvModeler;

public abstract class CpHttpFrameXrvPresenter<V, M extends CpHttpFrameXrvModeler> extends CpBasePresenter<V, M> {

    public CpHttpFrameXrvPresenter(V viewer) {
        super(viewer);
    }

    protected CpHttpFrameXrvPresenter(V viewer, M modeler) {
        super(viewer, modeler);
    }

    /**
     * 页面初始化刷新
     *
     * @param isCache
     */
    public abstract void loadPageFrame(boolean isCache, Object... params);

    /**
     * 页面下拉刷新
     *
     * @param isCache
     */
    public abstract void loadPullRefresh(boolean isCache, Object... params);

    /**
     * 加载更多
     *
     * @param fromMan
     */
    public abstract void loadPageMore(boolean fromMan, Object... params);
}
