package com.thn.lexi.discoverLifeAesthetics

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.R

class AdapterRecommendShowWindow(layoutResId: Int) : BaseQuickAdapter<ShowWindowBean.DataBean.ShopWindowsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ShowWindowBean.DataBean.ShopWindowsBean) {

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30))

        val textViewName = helper.getView<TextView>(R.id.textViewName)
        textViewName.text = item.user_name
        if (item.is_official){
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_show_window_official,0)
        }else{
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        }


        helper.setText(R.id.textViewTitle1, item.title)
        helper.setText(R.id.textViewTitle2, item.description)

        val textViewFocus = helper.getView<TextView>(R.id.textViewFocus)

        if (item.is_follow) {
            textViewFocus.text = Util.getString(R.string.text_focused)
            textViewFocus.setTextColor(Util.getColor(R.color.color_999))
        } else {
            textViewFocus.text = Util.getString(R.string.text_focus)
            textViewFocus.setTextColor(Util.getColor(R.color.color_6ed7af))
        }

        if (item.products.isEmpty()) return

        val list = ArrayList<String>()

        if (item.products.size <= 3) {
            for (product in item.products) {
                list.add(product.cover)
            }
        } else {
            val subList = item.products.subList(0, 3)
            for (product in subList) {
                list.add(product.cover)
            }
        }

        val list1 = ArrayList<ShowWindowProductAdapter.MultipleItem>()

        for (i in list.indices) {
            if (i == 0) {//第一张图占2列宽
                list1.add(ShowWindowProductAdapter.MultipleItem(list[i], ShowWindowProductAdapter.MultipleItem.ITEM_TYPE_SPAN2, ShowWindowProductAdapter.MultipleItem.ITEM_SPAN2_SIZE))
            } else {//占1列
                list1.add(ShowWindowProductAdapter.MultipleItem(list[i], ShowWindowProductAdapter.MultipleItem.ITEM_TYPE_SPAN1, ShowWindowProductAdapter.MultipleItem.ITEM_SPAN1_SIZE))
            }
        }

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
        val adapter = ShowWindowProductAdapter(list1)
        val gridLayoutManager = GridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        adapter.setSpanSizeLookup { _, position ->
            list1[position].spanSize
        }

        if (recyclerView.itemDecorationCount>0) return
        val size = DimenUtil.getDimensionPixelSize(R.dimen.dp2)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) {
                    outRect.right = size
                } else {
                    outRect.right = 0
                }

                if (position == 1) {
                    outRect.bottom = size
                } else {
                    outRect.bottom = 0
                }
            }
        })

    }
}