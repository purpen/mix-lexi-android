package com.lexivip.lexi.search
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class AdapterHotSearch(layoutResId: Int) : BaseQuickAdapter<HotSearchBean.DataBean.SearchItemsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: HotSearchBean.DataBean.SearchItemsBean) {
        helper.setText(R.id.textView,item.query_word)
        val imageView = helper.getView<ImageView>(R.id.imageView)

        when(helper.adapterPosition){
            0 -> imageView.setImageResource(R.mipmap.icon_rank1)
            1 -> imageView.setImageResource(R.mipmap.icon_rank2)
            2 -> imageView.setImageResource(R.mipmap.icon_rank3)
            else->{
                imageView.setImageResource(R.mipmap.icon_rank_other)
            }
        }
    }
}