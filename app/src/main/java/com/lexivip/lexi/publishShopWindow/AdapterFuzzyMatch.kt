package com.lexivip.lexi.publishShopWindow
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class AdapterFuzzyMatch(layoutResId: Int) : BaseQuickAdapter<FuzzyMatchTagsBean.DataBean.KeywordsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: FuzzyMatchTagsBean.DataBean.KeywordsBean) {
        helper.setText(R.id.textView,"# "+item.name)
    }
}