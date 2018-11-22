package com.lexivip.lexi.discoverLifeAesthetics

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.user.login.UserProfileUtil
import me.gujun.android.taggroup.TagGroup

class AdapterRecommendShowWindow(layoutResId: Int) : BaseQuickAdapter<ShopWindowBean, BaseViewHolder>(layoutResId) {
    private val dp124: Int by lazy { ScreenUtil.getScreenWidth() / 3 }
    private val dp250: Int by lazy { dp124 * 2 + 1 }
    private val dp13: Int by lazy { DimenUtil.dp2px(13.0) }
    private val dp20: Int by lazy { DimenUtil.dp2px(20.0) }
    private val dp2: Int by lazy { DimenUtil.dp2px(2.0) }

    private val layoutParams250: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp250, dp250) }
    private val layoutParams31: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp124, dp124) }
    private val layoutParams32: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp124, dp124) }
    override fun convert(helper: BaseViewHolder, item: ShopWindowBean) {

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30),ImageSizeConfig.SIZE_AVA)

        val textViewName = helper.getView<TextView>(R.id.textViewName)
        textViewName.text = item.user_name

        if (item.is_expert) {
            textViewName.setCompoundDrawables(null, null, Util.getDrawableWidthPxDimen(R.mipmap.icon_shop_window_star, dp13), null)
        } else {
            textViewName.setCompoundDrawables(null, null, null, null)

        }

        val imageViewStar = helper.getView<ImageView>(R.id.imageViewStar)

        if (item.is_official) {
            imageViewStar.visibility = View.VISIBLE
        } else {
            imageViewStar.visibility = View.GONE
        }


        val textViewLike = helper.getView<TextView>(R.id.textViewLike)

        if (item.is_like) {
            textViewLike.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_click_favorite_selected, dp20), null, null, null)
        } else {
            textViewLike.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_click_favorite_normal, dp20), null, null, null)
        }

        val textViewComment = helper.getView<TextView>(R.id.textViewComment)
        textViewComment.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_click_comment, dp20), null, null, null)

        val textViewShare = helper.getView<TextView>(R.id.textViewShare)
        textViewShare.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_click_share, dp20), null, null, null)

        helper.addOnClickListener(R.id.textViewLike)

        helper.addOnClickListener(R.id.textViewComment)

        helper.addOnClickListener(R.id.textViewShare)

        val textViewLikeCount = helper.getView<TextView>(R.id.textViewLikeCount)
        if (item.like_count == 0) {
            textViewLikeCount.visibility = View.GONE
        } else {
            textViewLikeCount.visibility = View.VISIBLE
            helper.setText(R.id.textViewLikeCount, "${item.like_count}" + Util.getString(R.string.text_favorite))
        }
        val textViewCommentCount = helper.getView<TextView>(R.id.textViewCommentCount)
        if (item.comment_count == 0) {
            textViewCommentCount.visibility = View.GONE
        } else {
            textViewCommentCount.visibility = View.VISIBLE
            helper.setText(R.id.textViewCommentCount, "${item.comment_count}" + Util.getString(R.string.text_comment_count))
        }

        val viewDot = helper.getView<View>(R.id.viewDot)
        if (item.comment_count > 0 && item.like_count > 0) {
            viewDot.visibility = View.VISIBLE
        } else {
            viewDot.visibility = View.GONE
        }

        helper.setText(R.id.textViewTitle1, item.title)
        helper.setText(R.id.textViewTitle2, item.description)

        val textViewFocus = helper.getView<TextView>(R.id.textViewFocus)

        if (TextUtils.equals(UserProfileUtil.getUserId(), item.uid) || item.is_official) { //官方和自己隐藏关注按钮
            textViewFocus.visibility = View.GONE
        } else {
            textViewFocus.visibility = View.VISIBLE
            if (item.is_follow) {
                textViewFocus.text = Util.getString(R.string.text_focused)
                textViewFocus.setTextColor(Util.getColor(R.color.color_999))
            } else {
                textViewFocus.text = Util.getString(R.string.text_focus)
                textViewFocus.setTextColor(Util.getColor(R.color.color_6ed7af))
            }
        }


        helper.addOnClickListener(R.id.textViewFocus)

        // 设置3张产品图
        if (item.products.isEmpty()) return

        val textView = helper.getView<TextView>(R.id.textView)
        val list = ArrayList<String>()

        val size = item.products.size

        if (size <= 3) {
            for (product in item.products) {
                list.add(product.cover)
            }
            textView.visibility = View.GONE
        } else {
            val subList = item.products.subList(0, 3)
            for (product in subList) {
                list.add(product.cover)
            }
            textView.visibility = View.VISIBLE
            textView.text = "+" + (size - 3)
        }

        val imageView30 = helper.getView<ImageView>(R.id.imageView30)
        val imageView31 = helper.getView<ImageView>(R.id.imageView31)
        val imageView32 = helper.getView<ImageView>(R.id.imageView32)
        val relativeLayoutImage32 = helper.getView<RelativeLayout>(R.id.relativeLayoutImage32)
        imageView30.layoutParams = layoutParams250
        imageView31.layoutParams = layoutParams31
        layoutParams31.addRule(RelativeLayout.END_OF, R.id.imageView30)
        layoutParams31.marginStart = dp2

        layoutParams32.addRule(RelativeLayout.BELOW, R.id.imageView31)
        layoutParams32.addRule(RelativeLayout.ALIGN_LEFT, R.id.imageView31)
        layoutParams32.topMargin = dp2 / 2
        relativeLayoutImage32.layoutParams = layoutParams32

        GlideUtil.loadImageWithDimenAndRadius(list[0], imageView30, 0, dp250, dp250,ImageSizeConfig.SIZE_P500)
        GlideUtil.loadImageWithDimenAndRadius(list[1], imageView31, 0, dp124,ImageSizeConfig.SIZE_P30X2)
        GlideUtil.loadImageWithDimenAndRadius(list[2], imageView32, 0, dp124,ImageSizeConfig.SIZE_P30X2)

//        helper.addOnClickListener(R.id.imageView30)
//        helper.addOnClickListener(R.id.imageView31)
//        helper.addOnClickListener(R.id.imageView32)
//        helper.addOnClickListener(R.id.textView)


        if (item.keywords == null || item.keywords.isEmpty()) return
        //设置标签
        val mTagGroup = helper.getView<TagGroup>(R.id.tagGroup)

        val arrayList = ArrayList<String>()

        for (word in item.keywords) {
            arrayList.add("#$word")
        }
        mTagGroup.setTags(arrayList)
        mTagGroup.setOnTagClickListener {tag->
            PageUtil.jump2RelateShopWindowActivity(tag.removePrefix("#"))
        }
    }
}