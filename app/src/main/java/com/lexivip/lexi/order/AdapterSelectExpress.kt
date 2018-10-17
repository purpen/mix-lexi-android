package com.lexivip.lexi.order

import android.support.annotation.LayoutRes
import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class AdapterSelectExpress(@LayoutRes res: Int) : BaseQuickAdapter<ExpressInfoBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: ExpressInfoBean) {

        helper.setText(R.id.textViewExpressName,item.express_name)

        helper.setText(R.id.textViewExpressTime,"物流时长：${item.min_days}至${item.max_days}天送达")

        helper.setText(R.id.textViewExpressMoney,"${item.freight}")

        val checkBox = helper.getView<CheckBox>(R.id.checkBox)

        checkBox.isChecked = item.is_default
    }
}