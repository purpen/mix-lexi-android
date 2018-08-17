package com.thn.lexi.selectionGoodsCenter

import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.R

class AdapterGoodsClassify(@LayoutRes res: Int) : BaseQuickAdapter<GoodsClassifyBean.DataBean.CategoriesBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: GoodsClassifyBean.DataBean.CategoriesBean) {

        val textView = helper.getView<TextView>(R.id.textView)
        if (item.selected){
            textView.setBackgroundResource(R.drawable.corner_border5fe4b1_radius4)
        }else{
            textView.setBackgroundResource(R.drawable.corner_bg_f5f7f9)
        }

        textView.text = item.name
    }
}