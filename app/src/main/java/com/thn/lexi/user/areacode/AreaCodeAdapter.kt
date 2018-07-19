package com.thn.lexi.user.areacode

import android.support.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class AreaCodeAdapter(@LayoutRes res: Int) : BaseQuickAdapter<CountryAreaCodeBean.DataBean.AreaCodesBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: CountryAreaCodeBean.DataBean.AreaCodesBean) {
        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewCode,item.areacode)
    }
}