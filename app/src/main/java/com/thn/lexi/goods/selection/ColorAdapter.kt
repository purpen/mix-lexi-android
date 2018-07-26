package com.thn.lexi.goods.selection

import android.support.annotation.LayoutRes
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import com.thn.lexi.goods.bean.SKUListData

class ColorAdapter(@LayoutRes res: Int):BaseQuickAdapter<SKUListData.DataBean.ColorsBean,BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: SKUListData.DataBean.ColorsBean) {
        val textView = helper.getView<TextView>(R.id.tvSku)
        if (item.valid) {
            textView.isEnabled = true
            if (item.selected) {
                textView.setBackgroundResource(R.drawable.corner_bg_6ed7af)
                textView.setTextColor(mContext.resources.getColor(android.R.color.white))
            } else {
                textView.setTextColor(mContext.resources.getColor(R.color.color_6ed7af))
                textView.setBackgroundResource(R.drawable.corner_border_6ed7af)
            }
        } else {
            textView.isEnabled = false
            textView.setTextColor(mContext.resources.getColor(R.color.color_666))
            textView.setBackgroundResource(R.drawable.corner_bg_eee)
        }
        textView.text = item.name
    }
}