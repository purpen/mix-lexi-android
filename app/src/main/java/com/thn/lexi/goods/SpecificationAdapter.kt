package com.thn.lexi.goods

import android.support.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.goods.bean.SKUListData

class SpecificationAdapter(@LayoutRes res: Int): BaseQuickAdapter<SKUListData.DataBean.ModesBean, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: SKUListData.DataBean.ModesBean) {

    }
}