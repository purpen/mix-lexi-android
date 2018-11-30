package com.lexivip.lexi.mine.dynamic

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.*
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.user.login.UserProfileUtil

class AdapterDynamicAdapter(@LayoutRes res: Int) : BaseQuickAdapter<ShopWindowBean, BaseViewHolder>(res) {
    private val dp20: Int by lazy { DimenUtil.dp2px(20.0) }
    private val dp15: Int by lazy { DimenUtil.dp2px(15.0) }
    private val layoutParams: LinearLayout.LayoutParams by lazy { LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (ScreenUtil.getScreenWidth() - DimenUtil.dp2px(40.0)) / 3) }

    override fun convert(helper: BaseViewHolder, item: ShopWindowBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30), ImageSizeConfig.SIZE_AVA)
        helper.setText(R.id.textViewName, item.user_name)
        helper.setText(R.id.textViewTime, DateUtil.getDateByTimestamp(item.updated_at, "MM月dd日"))

        val imageViewMore = helper.getView<ImageView>(R.id.imageViewMore)

        if (TextUtils.equals(item.uid, UserProfileUtil.getUserId())) {
            imageViewMore.visibility = View.VISIBLE
        } else {
            imageViewMore.visibility = View.GONE
        }

        helper.addOnClickListener(R.id.imageViewMore)
        helper.addOnClickListener(R.id.textViewLike)

        helper.addOnClickListener(R.id.textViewComment)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = CustomLinearLayoutManager(recyclerView.context)
        linearLayoutManager.setHorizontalScrollEnabled(false)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val adapter = ShopWindowDynamicAdapter(R.layout.adapter_pure_imageview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

        adapter.setOnItemClickListener { _, _, _ ->
            PageUtil.jump2ShopWindowDetailActivity(item.rid)
        }

        if (item.product_count <= 3) {
            adapter.setNewData(item.product_covers)
        } else {
            adapter.setNewData(item.product_covers.subList(0, 3))
        }
        if (recyclerView.itemDecorationCount == 0) recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, DimenUtil.dp2px(5.0), Util.getColor(android.R.color.transparent)))

        helper.setText(R.id.textViewTitle1, item.title)
        helper.setText(R.id.textViewTitle2, item.description)

        helper.setText(R.id.textViewPicNum, "${item.product_count}图")

        val relativeLayoutBox = helper.getView<RelativeLayout>(R.id.relativeLayoutBox)
        relativeLayoutBox.layoutParams = layoutParams
        layoutParams.leftMargin = dp15
        val textViewLike = helper.getView<TextView>(R.id.textViewLike)

        if (item.is_like) {
            textViewLike.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_click_favorite_selected, dp20), null, null, null)
        } else {
            textViewLike.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_click_favorite_normal, dp20), null, null, null)
        }

        if (item.like_count == 0) {
            textViewLike.text = " "
        } else {
            textViewLike.text = "${Util.getNumberString(item.like_count)}"
        }

        val textViewComment = helper.getView<TextView>(R.id.textViewComment)
        textViewComment.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_click_comment, dp20), null, null, null)

        if (item.comment_count == 0) {
            textViewComment.text = " "
        } else {
            textViewComment.text = "${Util.getNumberString(item.comment_count)}"
        }
    }
}