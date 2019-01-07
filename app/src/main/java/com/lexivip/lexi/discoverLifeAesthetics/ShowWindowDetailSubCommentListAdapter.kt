package com.lexivip.lexi.discoverLifeAesthetics
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean

class ShowWindowDetailSubCommentListAdapter(res: Int, present: ShowWindowDetailPresenter) : BaseQuickAdapter<CommentBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: CommentBean) {
        val textViewCommentWho = helper.getView<TextView>(R.id.textViewCommentWho)
        textViewCommentWho.setOnClickListener {//跳转被回复人的个人中心
            if (TextUtils.isEmpty(item.reply_user_uid)) return@setOnClickListener
            PageUtil.jump2OtherUserCenterActivity(item.reply_user_uid)
        }

        if (item.is_reply_other){
            val spannableString = SpannableString("回复@${item.reply_user_name} :")
            val colorSpan = ForegroundColorSpan(Util.getColor(R.color.color_666))
            spannableString.setSpan(colorSpan, 2, spannableString.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            textViewCommentWho.text = spannableString
        }else{
            textViewCommentWho.text = ":"
        }
        helper.setText(R.id.textViewName, item.user_name)
        helper.setText(R.id.textViewComment, item.content)
        helper.addOnClickListener(R.id.textViewComment)
    }
}