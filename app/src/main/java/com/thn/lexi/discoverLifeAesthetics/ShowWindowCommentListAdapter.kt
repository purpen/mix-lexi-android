package com.thn.lexi.discoverLifeAesthetics

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DateUtil
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

class ShowWindowCommentListAdapter(res: Int, presenter: ShowWindowCommentPresenter) : BaseQuickAdapter<ShowWindowCommentListBean.DataBean.CommentsBean, BaseViewHolder>(res) {
    private val present:ShowWindowCommentPresenter = presenter
    private val adapter: ShowWindowSubCommentListAdapter by lazy { ShowWindowSubCommentListAdapter(R.layout.adapter_subcomment_list) }
    override fun convert(helper: BaseViewHolder, item: ShowWindowCommentListBean.DataBean.CommentsBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, DimenUtil.getDimensionPixelSize(R.dimen.dp30))
        val textViewPraise = helper.getView<TextView>(R.id.textViewPraise)
        helper.setText(R.id.textViewTime, DateUtil.getDateByTimestamp(item.created_at))
        if (item.praise_count > 0) {
            textViewPraise.setTextColor(Util.getColor(R.color.color_ff6666))
            textViewPraise.text = "${item.praise_count}"
            textViewPraise.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_praise_active, 0, 0, 0)
        } else {
            textViewPraise.setTextColor(Util.getColor(R.color.color_999))
            textViewPraise.text = Util.getString(R.string.text_praise)
            textViewPraise.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_praise_normal, 0, 0, 0)
        }

        helper.addOnClickListener(R.id.textViewPraise)
        helper.addOnClickListener(R.id.textViewReply)

        helper.setText(R.id.textViewName, item.user_name)
        helper.setText(R.id.textViewComment, item.content)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(AppApplication.getContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.setNewData(item.sub_comments)
        if (recyclerView.itemDecorationCount == 0) recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))

        adapter.setOnItemChildClickListener { adapter, view, position ->
            val subCommentsBean = adapter.getItem(position) as ShowWindowCommentListBean.DataBean.CommentsBean.SubCommentsBean
            when(view.id){
                R.id.textViewPraise ->{
                    if (subCommentsBean.is_praise){
                        present.cancelPraiseComment(subCommentsBean.comment_id,position,view,true)
                    }else{
                        present.praiseComment(subCommentsBean.comment_id,position,view,true)
                    }
                }
            }
        }
    }


    /**
     * 设置子评论点赞状态
     */
    fun setPraiseCommentState(doPraise: Boolean, position: Int){
        val subCommentsBean = adapter.getItem(position) as ShowWindowCommentListBean.DataBean.CommentsBean.SubCommentsBean
        if (doPraise) {
            subCommentsBean.praise_count += 1
        } else {
            if (subCommentsBean.praise_count > 0) {
                subCommentsBean.praise_count -= 1
            }
        }
        adapter.notifyItemChanged(position)
    }

    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(android.R.color.transparent)
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter.itemCount
            var divider: Y_Divider? = null
            when (itemPosition) {
                count - 2 -> {

                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                count - 1 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                else -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, 5f, 0f, 0f)
                            .create()
                }
            }

            return divider
        }
    }
}