package com.thn.lexi.search
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class AdapterFuzzyMatch(layoutResId: Int) : BaseQuickAdapter<FuzzyWordMatchListBean.DataBean.SearchItemsBean, BaseViewHolder>(layoutResId) {
    private val colorMatch: Int by lazy { Util.getColor(R.color.color_6ed7af) }
    override fun convert(helper: BaseViewHolder, item: FuzzyWordMatchListBean.DataBean.SearchItemsBean) {

        val name = SpannableString(item.name)

        var startIndex = 0

        for (match in item.matches) {
            if (match.match) {
                val length = match.word.length
                val index = item.name.indexOf(match.word, startIndex)
                startIndex = index + length
                //将所有匹配为true变颜色
                name.setSpan(ForegroundColorSpan(colorMatch), index, index + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        helper.setText(R.id.textView, name)
        helper.setText(R.id.textViewType, item.title)
    }
}