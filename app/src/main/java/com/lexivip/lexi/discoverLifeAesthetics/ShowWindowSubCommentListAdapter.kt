package com.lexivip.lexi.discoverLifeAesthetics
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DateUtil
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean

class ShowWindowSubCommentListAdapter(res: Int, present: ShowWindowCommentPresenter) : BaseQuickAdapter<CommentBean, BaseViewHolder>(res) {
    private val dp13 by lazy { DimenUtil.dp2px(13.0) }

    private val presenter:ShowWindowCommentPresenter by lazy { present }
    override fun convert(helper: BaseViewHolder, item: CommentBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar,imageViewAvatar,DimenUtil.getDimensionPixelSize(R.dimen.dp20))
        val textViewSubPraise = helper.getView<TextView>(R.id.textViewSubPraise)
        helper.setText(R.id.textViewTime,DateUtil.getDateByTimestamp(item.created_at))

        if (item.sub_comment_count > 0) {

            textViewSubPraise.text = "${item.sub_comment_count}"
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

        helper.setText(R.id.textViewName,item.user_name)
        helper.setText(R.id.textViewComment,item.content)

        textViewSubPraise.setOnClickListener {//点赞
            presenter.praiseComment(textViewSubPraise, item,this)
        }
    }
}