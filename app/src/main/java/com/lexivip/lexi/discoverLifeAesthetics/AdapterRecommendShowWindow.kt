package com.lexivip.lexi.discoverLifeAesthetics
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.basemodule.tools.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean
import me.gujun.android.taggroup.TagGroup

class AdapterRecommendShowWindow(layoutResId: Int) : BaseQuickAdapter<ShopWindowBean, BaseViewHolder>(layoutResId) {
    private val dp250: Int by lazy { ScreenUtil.getScreenWidth() * 2 / 3 }
    private val dp124: Int by lazy { ScreenUtil.getScreenWidth() / 3 }
    private val dp13: Int by lazy { DimenUtil.dp2px(13.0) }
    private val dp25: Int by lazy { DimenUtil.dp2px(25.0) }
    private val dp20: Int by lazy { DimenUtil.dp2px(20.0) }
    private val layoutParams250:RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp250,dp250) }
    private val layoutParams124:RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp124,dp124) }
    override fun convert(helper: BaseViewHolder, item: ShopWindowBean) {

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30))

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

        helper.setText(R.id.textViewLikeCommentCount, "${item.like_count}" + Util.getString(R.string.text_favorite) + " · ${item.comment_count}" + Util.getString(R.string.text_comment_count))

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

        helper.addOnClickListener(R.id.textViewFocus)

        // 设置3张产品图
        if (item.products.isEmpty()) return

        val list = ArrayList<String>()

        val size = item.products.size

        if (size <= 3) {
            for (product in item.products) {
                list.add(product.cover)
            }
        } else {
            val subList = item.products.subList(0, 3)
            for (product in subList) {
                list.add(product.cover)
            }
        }

        val imageView30 = helper.getView<ImageView>(R.id.imageView30)
        val imageView31 = helper.getView<ImageView>(R.id.imageView31)
        val imageView32 = helper.getView<ImageView>(R.id.imageView32)
        val relativeLayoutImage32 = helper.getView<RelativeLayout>(R.id.relativeLayoutImage32)
        imageView30.layoutParams = layoutParams250
        imageView31.layoutParams = layoutParams124
        relativeLayoutImage32.layoutParams = layoutParams124

        GlideUtil.loadImageWithDimenAndRadius(list[0], imageView30, 0, dp250, dp250)
        GlideUtil.loadImageWithDimenAndRadius(list[1], imageView31, 0, dp124)
        GlideUtil.loadImageWithDimenAndRadius(list[2], imageView32, 0, dp124)

        helper.addOnClickListener(R.id.imageView30)
        helper.addOnClickListener(R.id.imageView31)
        helper.addOnClickListener(R.id.imageView32)

        val textView = helper.getView<TextView>(R.id.textView)

        helper.addOnClickListener(R.id.textView)

        if (size > 3) {
            textView.visibility = View.VISIBLE
            textView.text = "+" + (size - 3)
        } else {
            textView.visibility = View.GONE
        }

        if (item.keywords == null || item.keywords.isEmpty()) return
        //设置标签
        val mTagGroup = helper.getView<TagGroup>(R.id.tagGroup)

        val arrayList = ArrayList<String>()

        for (word in item.keywords) {
            arrayList.add("#$word")
        }
        mTagGroup.setTags(arrayList)
//        mTagGroup.setOnTagClickListener { tag: String? -> ToastUtil.showInfo(tag) }
    }
}