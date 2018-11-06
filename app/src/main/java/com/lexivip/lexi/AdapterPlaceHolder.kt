package com.lexivip.lexi

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class AdapterPlaceHolder(layoutResId:Int):BaseQuickAdapter<String, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: String) {

    }
}