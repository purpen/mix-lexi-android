package com.lexivip.lexi.publishShopWindow

import android.view.View
import android.widget.TextView
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R

class AdapterShopWindowHotTags(layoutResId: Int):BaseQuickAdapter<HotShopWindowTagsBean.DataBean.KeywordsBean, BaseViewHolder>(layoutResId){
    override fun convert(helper: BaseViewHolder, item: HotShopWindowTagsBean.DataBean.KeywordsBean) {

        helper.setText(R.id.textView,"# "+item.name)
        helper.setText(R.id.textViewCount,Util.getNumberString(item.numbers)+" 人参与")
        val textView = helper.getView<TextView>(R.id.textViewActivity)
        if (item.type==1){
            textView.visibility = View.VISIBLE
        }else{
            textView.visibility = View.GONE
        }
    }
}