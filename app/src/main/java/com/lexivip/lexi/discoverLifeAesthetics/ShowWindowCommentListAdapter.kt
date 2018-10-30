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
import com.lexivip.lexi.CustomLinearLayoutManager
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

class ShowWindowCommentListAdapter(res: Int, presenter: ShowWindowCommentPresenter) : BaseQuickAdapter<CommentBean, BaseViewHolder>(res) {
    private val present: ShowWindowCommentPresenter = presenter
    private var adapter: ShowWindowSubCommentListAdapter? = null
    private var footerView: View? = null
    private val size30 by lazy { DimenUtil.getDimensionPixelSize(R.dimen.dp30) }
    private val dp13 by lazy { DimenUtil.dp2px(13.0) }
    override fun convert(helper: BaseViewHolder, item: CommentBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, size30)
        val textViewPraise = helper.getView<TextView>(R.id.textViewPraise)
        helper.setText(R.id.textViewTime, DateUtil.getDateByTimestamp(item.created_at))
        if (item.praise_count > 0) {
            if (item.is_praise) { //我已点赞
                textViewPraise.setTextColor(Util.getColor(R.color.color_ff6666))
                textViewPraise.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_praise_active, dp13), null, null, null)
            } else {
                textViewPraise.text = Util.getString(R.string.text_praise)
                textViewPraise.setCompoundDrawables(Util.getDrawableWidthPxDimen(R.mipmap.icon_praise_normal, dp13), null, null, null)
            }
            textViewPraise.text = "${item.praise_count}"

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
        } else {
            recyclerView.visibility = View.VISIBLE
            adapter = ShowWindowSubCommentListAdapter(R.layout.adapter_subcomment_list, present)
            val linearLayoutManager = CustomLinearLayoutManager(AppApplication.getContext())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            linearLayoutManager.setScrollEnabled(false)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = adapter
            adapter!!.setNewData(item.sub_comments)
            if (recyclerView.itemDecorationCount == 0) recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))

            if (item.sub_comment_count > 0) {
                footerView = LayoutInflater.from(AppApplication.getContext()).inflate(R.layout.view_footer_sub_comment, null)
                val textView = footerView!!.findViewById<TextView>(R.id.textView)

                val count = adapter!!.data.size

                if (item.sub_comment_count == count) {
                    footerView!!.visibility = View.GONE
                } else {
                    footerView!!.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE
                    textView.text = "查看${item.sub_comment_count - count}条回复"
                }

                if (adapter!!.footerLayoutCount == 0) adapter!!.addFooterView(footerView)

                textView.setOnClickListener { view ->
                    //当前评论id就是子评论pid
                    present.loadMoreSubComments(item, view, this)
                }
            } else {
                footerView = null
            }
        }


    }


    /**
     * 设置子评论点赞状态
     */
//    fun setPraiseCommentState(doPraise: Boolean, position: Int) {
//        if (clickedSubCommentsBean == null) return
//        if (doPraise) {
//            clickedSubCommentsBean!!.is_praise = true
//            clickedSubCommentsBean!!.praise_count += 1
//        } else {
//            clickedSubCommentsBean!!.is_praise = false
//            if (clickedSubCommentsBean!!.praise_count > 0) {
//                clickedSubCommentsBean!!.praise_count -= 1
//            }
//        }
//        notifyDataSetChanged()
//    }


    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(android.R.color.transparent)
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter!!.itemCount
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