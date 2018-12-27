package com.lexivip.lexi.discoverLifeAesthetics
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean

class ShowWindowSubCommentListAdapter(res: Int, present: ShowWindowCommentPresenter) : BaseQuickAdapter<CommentBean, BaseViewHolder>(res) {
    private val dp13 by lazy { DimenUtil.dp2px(13.0) }

    private val presenter:ShowWindowCommentPresenter by lazy { present }
    override fun convert(helper: BaseViewHolder, item: CommentBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar,imageViewAvatar,DimenUtil.getDimensionPixelSize(R.dimen.dp20),ImageSizeConfig.SIZE_AVA)
        val textViewSubPraise = helper.getView<TextView>(R.id.textViewSubPraise)
        helper.setText(R.id.textViewTime, DateUtil.getSpaceTime(item.created_at*1000))

        if (item.praise_count > 0) {
            textViewSubPraise.text = "${item.praise_count}"
            if (item.is_praise){
                textViewSubPraise.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_praise_active, dp13), null, null, null)
                textViewSubPraise.setTextColor(Util.getColor(R.color.color_ff6666))
            }else{
                textViewSubPraise.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_praise_normal, dp13), null, null, null)
                textViewSubPraise.setTextColor(Util.getColor(R.color.color_999))
            }
        } else {
            textViewSubPraise.setTextColor(Util.getColor(R.color.color_999))
            textViewSubPraise.text = Util.getString(R.string.text_praise)
            textViewSubPraise.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_praise_normal, dp13), null, null, null)
        }

        val textViewCommentWho = helper.getView<TextView>(R.id.textViewCommentWho)
        val spannableString = SpannableString("回复@${item.reply_user_name}:")
        val colorSpan = ForegroundColorSpan(Util.getColor(R.color.color_666))
        spannableString.setSpan(colorSpan, 2,spannableString.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        textViewCommentWho.text = spannableString

        helper.setText(R.id.textViewName,item.user_name)

        val textViewComment = helper.getView<TextView>(R.id.textViewComment)
        textViewComment.text
        helper.setText(R.id.textViewComment," ${item.content}")

        textViewCommentWho.setOnClickListener {//跳转个人中心
            ToastUtil.showInfo("跳转个人中心")
//            PageUtil.jump2OtherUserCenterActivity("")
        }

        helper.addOnClickListener(R.id.textViewComment)

        textViewSubPraise.setOnClickListener {//点赞
            presenter.praiseComment(textViewSubPraise, item,this)
        }
    }
}