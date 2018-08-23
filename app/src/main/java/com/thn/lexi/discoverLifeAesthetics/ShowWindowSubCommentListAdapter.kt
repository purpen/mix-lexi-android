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

class ShowWindowSubCommentListAdapter(res: Int) : BaseQuickAdapter<ShowWindowCommentListBean.DataBean.CommentsBean.SubCommentsBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: ShowWindowCommentListBean.DataBean.CommentsBean.SubCommentsBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar,imageViewAvatar,DimenUtil.getDimensionPixelSize(R.dimen.dp20))
        val textViewPraise = helper.getView<TextView>(R.id.textViewPraise)
        helper.setText(R.id.textViewTime,DateUtil.getDateByTimestamp(item.created_at))
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