package com.thn.lexi.discoverLifeAesthetics

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import me.gujun.android.taggroup.TagGroup

class AdapterRecommendShowWindow(layoutResId: Int) : BaseQuickAdapter<ShowWindowBean.DataBean.ShopWindowsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ShowWindowBean.DataBean.ShopWindowsBean) {

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30))

        val textViewName = helper.getView<TextView>(R.id.textViewName)
        textViewName.text = item.user_name
        if (item.is_official) {
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_show_window_official, 0)
        } else {
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        val textViewLike = helper.getView<TextView>(R.id.textViewLike)

        if (item.is_like){
            textViewLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_click_favorite_selected,0,0,0)
        }else{
            textViewLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_click_favorite_normal,0,0,0)
        }

        helper.setText(R.id.textViewLikeCommentCount, "${item.like_count}"+Util.getString(R.string.text_favorite) + " · ${item.comment_count}" + Util.getString(R.string.text_comment_count))

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

        helper.addOnClickListener(R.id.textViewLike)

        helper.addOnClickListener(R.id.textViewComment)

        helper.addOnClickListener(R.id.textViewShare)


        // 设置3张产品图
        if (item.products.isEmpty()) return

        val list = ArrayList<String>()
        val size = item.products.size
        if ( size <= 3) {
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
        val showWindowProductAdapter = ShowWindowProductAdapter(list1,size)
        val gridLayoutManager = GridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = showWindowProductAdapter
        showWindowProductAdapter.setSpanSizeLookup { _, position ->
            list1[position].spanSize
        }

        if (recyclerView.itemDecorationCount == 0) {
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


        //设置标签
        val mTagGroup = helper.getView<TagGroup>(R.id.tagGroup)
        mTagGroup.setTags(listOf("#生活", "#美学", "#夏天girl", "#东东"))
        mTagGroup.setOnTagClickListener { tag: String? -> ToastUtil.showInfo(tag) }
    }
}