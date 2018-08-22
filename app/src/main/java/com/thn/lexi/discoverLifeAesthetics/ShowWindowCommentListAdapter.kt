package com.thn.lexi.discoverLifeAesthetics

import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DateUtil
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class ShowWindowCommentListAdapter(res: Int) : BaseQuickAdapter<ShowWindowCommentListBean.DataBean.CommentsBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: ShowWindowCommentListBean.DataBean.CommentsBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar,imageViewAvatar,DimenUtil.getDimensionPixelSize(R.dimen.dp30))
        val textViewPraise = helper.getView<TextView>(R.id.textViewPraise)
        helper.setText(R.id.textViewTime,DateUtil.getDateByTimestamp(item.created_at))
        if (item.praise_count>0){
            textViewPraise.setTextColor(Util.getColor(R.color.color_ff6666))
            textViewPraise.text = "${item.praise_count}"
            textViewPraise.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_praise_active,0,0,0)
        }else{
            textViewPraise.setTextColor(Util.getColor(R.color.color_999))
            textViewPraise.text = Util.getString(R.string.text_praise)
            textViewPraise.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_praise_normal,0,0,0)
        }

        helper.setText(R.id.textViewName,item.user_name)
        helper.setText(R.id.textViewComment,item.content)
    }
}