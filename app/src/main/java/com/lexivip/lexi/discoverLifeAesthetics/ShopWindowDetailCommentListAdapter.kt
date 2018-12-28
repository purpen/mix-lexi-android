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
import com.lexivip.lexi.*
import com.lexivip.lexi.beans.CommentBean
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

class ShopWindowDetailCommentListAdapter(res: Int, presenter: ShowWindowDetailPresenter) : BaseQuickAdapter<CommentBean, BaseViewHolder>(res) {
    private val present: ShowWindowDetailPresenter = presenter
    private var adapter: ShowWindowDetailSubCommentListAdapter? = null
    private var footerView: View? = null
    private val size30 by lazy { DimenUtil.getDimensionPixelSize(R.dimen.dp30) }
    private val dp13 by lazy { DimenUtil.dp2px(13.0) }
    private var shopWindowData:ShowWindowDetailBean.DataBean?=null

    private var subCommentClickListener: ShowWindowCommentListAdapter.OnSubCommentClickListener? = null

    fun setOnSubCommentClickListener(subCommentClickListener: ShowWindowCommentListAdapter.OnSubCommentClickListener){
        this.subCommentClickListener = subCommentClickListener
    }

    override fun convert(helper: BaseViewHolder, item: CommentBean) {
        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar, imageViewAvatar, size30,ImageSizeConfig.SIZE_AVA)
        imageViewAvatar.setOnClickListener {
            PageUtil.jump2OtherUserCenterActivity(item.uid)
        }

        val textViewPraise = helper.getView<TextView>(R.id.textViewPraise)
        helper.setText(R.id.textViewTime, DateUtil.getSpaceTime(item.created_at*1000L))
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
        helper.addOnClickListener(R.id.textViewComment)

        helper.setText(R.id.textViewName, item.user_name)
        helper.setText(R.id.textViewComment, item.content)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)

        if (item.sub_comment_count == 0) {
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
            adapter = ShowWindowDetailSubCommentListAdapter(R.layout.adapter_shopwindow_detail_subcomment, present)
            val linearLayoutManager = CustomLinearLayoutManager(AppApplication.getContext())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            linearLayoutManager.setScrollEnabled(false)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = adapter
            adapter!!.setNewData(item.sub_comments)
            if (recyclerView.itemDecorationCount == 0) recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))

            adapter!!.setOnItemChildClickListener { _, _, position ->
                val commentBean = item.sub_comments[position]
                subCommentClickListener?.onClick(commentBean)
            }

            if (item.sub_comment_count > 0) {
                footerView = LayoutInflater.from(AppApplication.getContext()).inflate(R.layout.view_footer_sub_comment, null)
                val textViewName = footerView!!.findViewById<TextView>(R.id.textViewName)
                val textViewReplyNum = footerView!!.findViewById<TextView>(R.id.textViewReplyNum)
                val count = adapter!!.data.size

                if (item.sub_comment_count == count) {
                    footerView!!.visibility = View.GONE
                } else {
                    footerView!!.visibility = View.VISIBLE
                    textViewName.visibility = View.VISIBLE
                    textViewReplyNum.visibility = View.VISIBLE
                    textViewName.text = "${item.sub_comments[0].user_name}"
                    textViewReplyNum.text = "等人共${item.sub_comment_count}条回复"
                }

                if (adapter!!.footerLayoutCount == 0) adapter!!.addFooterView(footerView)

                textViewReplyNum.setOnClickListener { //跳转评论列表
                    if (shopWindowData==null) return@setOnClickListener
                    PageUtil.jump2ShopWindowCommentListActivity(shopWindowData!!)
                }
            } else {

                footerView = null
            }
        }


    }

    /**
     * 设置橱窗数据
     */

    fun setWindowData(data: ShowWindowDetailBean.DataBean) {
        shopWindowData = data
    }

    /**
     * 刷新子评论列表
     */
    fun notifySubCommentList() {
        adapter?.notifyDataSetChanged()
    }


    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(android.R.color.transparent)
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter!!.itemCount
            val divider: Y_Divider?
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