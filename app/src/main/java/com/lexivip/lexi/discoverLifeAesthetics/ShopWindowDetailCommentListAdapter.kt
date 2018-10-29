package com.lexivip.lexi.discoverLifeAesthetics

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

class ShopWindowDetailCommentListAdapter(res: Int, presenter: ShowWindowDetailPresenter) : BaseQuickAdapter<CommentBean, BaseViewHolder>(res) {
    private val present: ShowWindowDetailPresenter = presenter
    private val adapter: ShowWindowSubCommentListAdapter by lazy { ShowWindowSubCommentListAdapter(R.layout.adapter_subcomment_list) }
    private var footerView: View? = null
    private val size30 by lazy { DimenUtil.dp2px(30.0) }
    private val dp13 by lazy { DimenUtil.dp2px(13.0) }

    override fun convert(helper: BaseViewHolder, item: CommentBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, size30)
        val textViewPraise = helper.getView<TextView>(R.id.textViewPraise)
        helper.setText(R.id.textViewTime, DateUtil.getDateByTimestamp(item.created_at))
        if (item.praise_count > 0) {
            textViewPraise.setTextColor(Util.getColor(R.color.color_ff6666))
            textViewPraise.text = "${item.praise_count}"
            textViewPraise.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_praise_active, dp13), null, null, null)
        } else {
            textViewPraise.setTextColor(Util.getColor(R.color.color_999))
            textViewPraise.text = Util.getString(R.string.text_praise)
            textViewPraise.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_praise_normal, dp13), null, null, null)
        }

        helper.addOnClickListener(R.id.textViewPraise)
        helper.addOnClickListener(R.id.textViewReply)

        helper.setText(R.id.textViewName, item.user_name)
        helper.setText(R.id.textViewComment, item.content)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)

        if (item.sub_comment_count == 0) {
            recyclerView.visibility = View.GONE
            return
        } else {
            recyclerView.visibility = View.VISIBLE
        }


        val linearLayoutManager = LinearLayoutManager(AppApplication.getContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.setNewData(item.sub_comments)
        if (recyclerView.itemDecorationCount == 0) recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))

        adapter.setOnItemChildClickListener { _, view, position ->
            val subCommentsBean = adapter.getItem(position) ?: return@setOnItemChildClickListener
            when (view.id) {
                R.id.textViewPraise -> {
                    present.praiseComment(subCommentsBean.comment_id, subCommentsBean.is_praise, position, view, true)
                }
            }
        }


        footerView = LayoutInflater.from(AppApplication.getContext()).inflate(R.layout.view_footer_sub_comment, null)

        footerView?.findViewById<TextView>(R.id.textView)?.text = "查看${item.sub_comment_count}条回复"

        if (adapter.footerLayoutCount == 0) adapter.addFooterView(footerView)

        footerView?.setOnClickListener { view ->
            //当前item.comment_id就是父评论的id

            item.comment_id = "111"

            present.loadMoreSubComments(item.comment_id, helper.adapterPosition, view)
        }

    }


    /**
     * 设置子评论点赞状态
     */
    fun setPraiseCommentState(doPraise: Boolean, position: Int) {
        val subCommentsBean = adapter.getItem(position) as CommentBean
        if (doPraise) {
            subCommentsBean.is_praise = true
            subCommentsBean.praise_count += 1
        } else {
            subCommentsBean.is_praise = false
            if (subCommentsBean.praise_count > 0) {
                subCommentsBean.praise_count -= 1
            }
        }
        adapter.notifyItemChanged(position)
    }

    /**
     * 添加子评论
     */
    fun addSubCommentsData(position: Int, comments: List<CommentBean>) {

        if (comments.isEmpty()) adapter.removeFooterView(footerView)

        val subComments = data[position].sub_comments
        if (subComments.size <= 2) { //第一次加载子评论,清空原来数据，重头开始第一页
            subComments.clear()
        }

        adapter.addData(comments)
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