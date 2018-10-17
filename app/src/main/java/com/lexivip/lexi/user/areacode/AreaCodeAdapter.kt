package com.lexivip.lexi.user.areacode

import android.support.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class AreaCodeAdapter(@LayoutRes res: Int) : BaseQuickAdapter<CountryAreaCodeBean.DataBean.AreaCodesBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: CountryAreaCodeBean.DataBean.AreaCodesBean) {
        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewCode,item.areacode)
    }
}