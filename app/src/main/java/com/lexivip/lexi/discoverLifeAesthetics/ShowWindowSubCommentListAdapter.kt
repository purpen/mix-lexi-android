package com.lexivip.lexi.discoverLifeAesthetics
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DateUtil
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean

class ShowWindowSubCommentListAdapter(res: Int) : BaseQuickAdapter<CommentBean, BaseViewHolder>(res) {
    private val bounds: Rect by lazy { Rect(0,0,DimenUtil.getDimensionPixelSize(R.dimen.dp13),DimenUtil.getDimensionPixelSize(R.dimen.dp13)) }

    override fun convert(helper: BaseViewHolder, item: CommentBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar,imageViewAvatar,DimenUtil.getDimensionPixelSize(R.dimen.dp20))
        val textViewPraise = helper.getView<TextView>(R.id.textViewPraise)
        helper.setText(R.id.textViewTime,DateUtil.getDateByTimestamp(item.created_at))

        val icon = ContextCompat.getDrawable(AppApplication.getContext(), R.mipmap.icon_praise_normal)
        icon?.bounds = bounds
        textViewPraise.setCompoundDrawables(icon,null,null,null)

        if (item.praise_count>0){
            textViewPraise.text = "${item.praise_count}"
        }else{
            textViewPraise.text = Util.getString(R.string.text_praise)
        }

        helper.addOnClickListener(R.id.textViewPraise)
        helper.setText(R.id.textViewName,item.user_name)
        helper.setText(R.id.textViewComment,item.content)

    }
}