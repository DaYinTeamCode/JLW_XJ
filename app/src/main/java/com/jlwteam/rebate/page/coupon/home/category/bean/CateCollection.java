package com.jlwteam.rebate.page.coupon.home.category.bean;

import com.androidex.util.TextUtil;
import com.androidex.zbuild.IKeepSource;


public class CateCollection implements IKeepSource {

    public static final int LOCAL_REC_ID = 0;

    private int cate_collection_id;
    private String name = TextUtil.TEXT_EMPTY;
    private String pic = TextUtil.TEXT_EMPTY;
    private int is_hot;
    private boolean localShowed;
    private boolean localIgnoreShowed;
    private boolean localChecked;
    private int localPos;
    private String spid;

    public boolean isLocalChecked() {

        return localChecked;
    }

    public void setLocalChecked(boolean localChecked) {

        this.localChecked = localChecked;
    }

    public int getLocalPos() {

        return localPos;
    }

    public void setLocalPos(int localPos) {

        this.localPos = localPos;
    }

    public int getCate_collection_id() {

        return cate_collection_id;
    }

    public void setCate_collection_id(int cate_collection_id) {

        this.cate_collection_id = cate_collection_id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = TextUtil.filterNull(name);
    }

    public String getPic() {

        return pic;
    }

    public void setPic(String pic) {

        this.pic = TextUtil.filterNull(pic);
    }

    public int getIs_hot() {

        return is_hot;
    }

    public void setIs_hot(int is_hot) {

        this.is_hot = is_hot;
    }

    public boolean isLocalRecType() {

        return LOCAL_REC_ID == cate_collection_id;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }


    /**
     * 是否是精选
     *
     * @return
     */
    public boolean isChosen() {

        return is_hot == 1;
    }

}
