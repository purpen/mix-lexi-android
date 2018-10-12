package com.thn.lexi.brandHouse;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AdapterBrandHouseArticle extends BaseQuickAdapter<BrandHouseArticelBean.DataBean.LifeRecordsBean,BaseViewHolder> {
    public AdapterBrandHouseArticle(int layoutResId, @Nullable List<BrandHouseArticelBean.DataBean.LifeRecordsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandHouseArticelBean.DataBean.LifeRecordsBean item) {

    }
}
