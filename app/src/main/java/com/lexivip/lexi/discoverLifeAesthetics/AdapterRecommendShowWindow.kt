package com.lexivip.lexi.discoverLifeAesthetics
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import me.gujun.android.taggroup.TagGroup

class AdapterRecommendShowWindow(layoutResId: Int) : BaseQuickAdapter<ShowWindowBean.DataBean.ShopWindowsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ShowWindowBean.DataBean.ShopWindowsBean) {

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30))

        val textViewName = helper.getView<TextView>(R.id.textViewName)
        textViewName.text = item.user_name
        if (item.is_official) {
            textViewName.setCompoundDrawables(null,null,Util.getDrawableWidthDimen(R.mipmap.icon_show_window_official,R.dimen.dp25,R.dimen.dp13),null)
        } else {
            textViewName.setCompoundDrawables(null,null,null,null)
        }

        val textViewLike = helper.getView<TextView>(R.id.textViewLike)

        if (item.is_like){
            textViewLike.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_click_favorite_selected,R.dimen.dp20,R.dimen.dp20),null,null,null)
        }else{
            textViewLike.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_click_favorite_normal,R.dimen.dp20,R.dimen.dp20),null,null,null)
        }

        val textViewComment = helper.getView<TextView>(R.id.textViewComment)
        textViewComment.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_click_comment,R.dimen.dp20,R.dimen.dp20),null,null,null)

        val textViewShare = helper.getView<TextView>(R.id.textViewShare)
        textViewShare.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_click_share,R.dimen.dp20,R.dimen.dp20),null,null,null)

        helper.addOnClickListener(R.id.textViewLike)

        helper.addOnClickListener(R.id.textViewComment)

        helper.addOnClickListener(R.id.textViewShare)

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

        val imageView0 = helper.getView<ImageView>(R.id.imageView30)
        val imageView1 = helper.getView<ImageView>(R.id.imageView31)
        val imageView2 = helper.getView<ImageView>(R.id.imageView32)

        GlideUtil.loadImageWithFading(list[0],imageView0)
        GlideUtil.loadImageWithFading(list[1],imageView1)
        GlideUtil.loadImageWithFading(list[2],imageView2)

        val textView = helper.getView<TextView>(R.id.textView)

        if (size>3){
            textView.visibility = View.VISIBLE
            textView.text = "+"+(size-3)
        }else{
            textView.visibility = View.GONE
        }

        //设置标签
        val mTagGroup = helper.getView<TagGroup>(R.id.tagGroup)
        mTagGroup.setTags(listOf("#生活", "#美学", "#夏天girl", "#东东"))
        mTagGroup.setOnTagClickListener { tag: String? -> ToastUtil.showInfo(tag) }
    }
}