package com.thn.lexi.mine

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider

class AdapterDesignPavilion(layoutResId: Int) : BaseQuickAdapter<DesignPavilionBean, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: DesignPavilionBean) {

        val imageViewShop = helper.getView<ImageView>(R.id.imageViewShop)

        GlideUtil.loadImageWithRadius(item.logo,imageViewShop,DimenUtil.getDimensionPixelSize(R.dimen.dp4))

        helper.setText(R.id.textViewTitle,item.name)
        helper.setText(R.id.textViewCount,"${item.store_products_counts}件商品")

        val buttonFocus = helper.getView<Button>(R.id.buttonFocus)

        if (item.followed_status==1){
            buttonFocus.text = Util.getString(R.string.text_focused)
            buttonFocus.textSize = 13f
            buttonFocus.setTextColor(Util.getColor(R.color.color_6ed7af))
            buttonFocus.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_focus_pavilion,0,0,0)
            buttonFocus.setBackgroundResource(R.drawable.bg_color5fe4b1_radius4)
        }else{
            buttonFocus.textSize = 14f
            buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
            buttonFocus.text = Util.getString(R.string.text_focus)
            buttonFocus.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            buttonFocus.setBackgroundResource(R.drawable.bg_coloreff3f2_radius4)
        }

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL,false)
        val adapter = DesignPavilionProductAdapter(R.layout.adapter_pure_imageview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

        if (recyclerView.itemDecorationCount==0) recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, DimenUtil.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))

        val list = ArrayList<String>()

        for (product in item.products){
            list.add(product.cover)
        }

        adapter.setNewData(list)

        helper.addOnClickListener(R.id.imageViewShop)

        helper.addOnClickListener(R.id.buttonFocus)
    }
}