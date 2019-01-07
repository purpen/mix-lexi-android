package com.lexivip.lexi.discoverLifeAesthetics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.widget.*
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.view.emotionkeyboardview.fragment.EmotionMainFragment
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.activity_show_window_comment_list.*


class ShowWindowCommentListActivity : BaseActivity(), ShowWindowCommentContract.View {

    override val layout: Int = R.layout.activity_show_window_comment_list

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: ShowWindowCommentPresenter by lazy { ShowWindowCommentPresenter(this) }

    private val adapter: ShowWindowCommentListAdapter by lazy { ShowWindowCommentListAdapter(R.layout.adapter_comment_list, presenter) }

    private lateinit var shopWindowData: ShowWindowDetailBean.DataBean
    private lateinit var emotionMainFragment: EmotionMainFragment

    // 父级评论
    private var pid: String = "0"

    //回复哪条评论
    private var replyId: String = "0"

    override fun setPresenter(presenter: ShowWindowCommentContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun getIntentData() {
        if (intent.hasExtra(TAG)) {
            shopWindowData = intent.getParcelableExtra(TAG)
        }
    }

    override fun initView() {
        swipeRefreshLayout.isEnabled = false
        customHeadView.setHeadCenterTxtShow(true, "${shopWindowData.comment_count}条评论")
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext))
        initEmotionFragment()
    }

    /**
     * 初始化EmotionMainFragment
     */
    private fun initEmotionFragment() {
        //构建传递参数
        val bundle = Bundle()
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true)
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false)

        bundle.putBoolean(EmotionMainFragment.IS_LIKE, shopWindowData.is_like)
        bundle.putInt(EmotionMainFragment.LIKE_COUNTS, shopWindowData.like_count)

        emotionMainFragment = EmotionMainFragment.newInstance(bundle)
        emotionMainFragment.bindToContentView(swipeRefreshLayout)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutEmotion, emotionMainFragment)
        transaction.addToBackStack(null)
        //提交修改
        transaction.commit()
    }

    override fun installListener() {

//        imageViewChangeInput.setOnClickListener{
//            if (showEmojiKeyBoard){ //关闭表情键盘
//                showEmojiKeyBoard = false
//                imageViewChangeInput.setImageResource(R.mipmap.icon_open_emoji)
//            }else{
//                imageViewChangeInput.setImageResource(R.mipmap.icon_text_input)
//                showEmojiKeyBoard = true
//            }
//        }


        emotionMainFragment.setOnSendCommentListener(object : IOnSendCommentListener {
            override fun onSend(sendButton: Button, editText: EditText) {
                if (UserProfileUtil.isLogin()) {
                    val content = editText.text.trim().toString()
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.showInfo("请先输入评论")
                        return
                    }
                    presenter.submitComment(shopWindowData.rid, pid, replyId, content, sendButton)
                    editText.text.clear()
                    emotionMainFragment.hideKeyBoard()
                } else {
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                }
            }
        })


        emotionMainFragment.setOnFavoriteClickListener(object : IOnFavoriteClickListener {
            override fun onClick(imageViewLike: ImageView, textViewLikeCount: TextView) {
                if (UserProfileUtil.isLogin()) {
                    presenter.favoriteShowWindow(shopWindowData.rid, imageViewLike, shopWindowData.is_like, textViewLikeCount)
                } else {
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                }
            }
        })

        //点击输入框外部关闭键盘
        adapter.setOnItemClickListener { _, _, _ ->
            if (emotionMainFragment.isUserInputEmpty()) {
                replyId = "0"
                pid = "0"
                emotionMainFragment.setEditTextHint(getString(R.string.text_add_comment))
            }
            emotionMainFragment.hideKeyBoard()
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(shopWindowData.rid, true)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData(shopWindowData.rid)
        }, recyclerView)


        /**
         * 子评论点击时
         */
        adapter.setOnSubCommentClickListener(object : ShowWindowCommentListAdapter.OnSubCommentClickListener {
            override fun onClick(commentBean: CommentBean) {
                showKeyboardAndReplyWho(commentBean)
            }
        })

        adapter.setOnItemChildClickListener { adapter, view, position ->

            val commentsBean = adapter.getItem(position) as CommentBean

            when (view.id) {
                R.id.imageViewAvatar, R.id.textViewName -> {
                    PageUtil.jump2OtherUserCenterActivity(commentsBean.uid)
                }
                R.id.textViewReply, R.id.textViewComment -> { //将被回复的评论id最为pid
                    showKeyboardAndReplyWho(commentsBean)
                }

                R.id.textViewPraise -> {
                    presenter.praiseComment(commentsBean.comment_id, commentsBean.is_praise, position, view, false)
                }
            }
        }
    }

    /**
     * 显示键盘和回复谁
     */
    fun showKeyboardAndReplyWho(commentsBean: CommentBean) {
        emotionMainFragment.showKeyBoard()
        replyId = commentsBean.comment_id
        pid = commentsBean.pid
        LogUtil.e("pid===$pid;;;;;;replyId==$replyId")
        emotionMainFragment.setEditTextHint("回复${commentsBean.user_name}:")
    }

    /**
     * 重置输入框为默认状态
     */
    private fun resetInputBarState() {
        replyId = "0"
        pid = "0"
        emotionMainFragment.resetInputBarState()
    }

    /**
     * 当评论提交成功
     */
    override fun noticeCommentSuccess(commentBean: CommentBean) {
        resetInputBarState()
        if (TextUtils.equals(commentBean.pid, "0")) { //评论橱窗
            adapter.addData(0, commentBean)
        } else {//添加到子评论列表开头,刷新子评论列表
            val list = adapter.data
            for (item in list) {
                if (TextUtils.equals(item.comment_id, commentBean.pid)) { //子评论数+1
                    item.sub_comment_count += 1
                    if (item.sub_comments == null) item.sub_comments = ArrayList<CommentBean>()
                    item.sub_comments.add(0, commentBean)
                    break
                }
            }
            adapter.notifySubCommentList()
        }
        adapter.notifyDataSetChanged()
    }

    /**
     * 更新评论点赞状态
     */
    override fun setPraiseCommentState(doPraise: Boolean, position: Int, isSubAdapter: Boolean) {
        val commentsBean = adapter.getItem(position) as CommentBean
        if (doPraise) {
            commentsBean.is_praise = true
            commentsBean.praise_count += 1
        } else {
            commentsBean.is_praise = false
            if (commentsBean.praise_count > 0) {
                commentsBean.praise_count -= 1
            }
        }
        adapter.notifyItemChanged(position)

    }

    /**
     * 设置喜欢状态
     */
    override fun setFavorite(b: Boolean, view1: ImageView, textViewLikeCount: TextView) {
        if (b) {
            shopWindowData.is_like = true
            view1.setImageResource(R.mipmap.icon_click_favorite_selected)
            shopWindowData.like_count += 1
            textViewLikeCount.text = "${shopWindowData.like_count}"
        } else {
            shopWindowData.is_like = false
            view1.setImageResource(R.mipmap.icon_click_favorite_normal)
            shopWindowData.like_count -= 1
            textViewLikeCount.text = "${shopWindowData.like_count}"
        }
    }

    override fun setNewData(comments: MutableList<CommentBean>) {
        emotionMainFragment.requestFocus()
        adapter.setNewData(comments)
    }

    override fun addData(comments: MutableList<CommentBean>) {
        adapter.addData(comments)
    }

    override fun requestNet() {
        presenter.loadData(shopWindowData.rid, false)
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun loadMoreFail() {
        adapter.loadMoreFail()
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(R.color.color_eee)
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter.itemCount
            val divider: Y_Divider?
            divider = when (itemPosition) {
                count - 1 -> {
                    Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                else -> {
                    Y_DividerBuilder()
                            .setBottomSideLine(true, color, 0.5f, 20f, 0f)
                            .create()
                }
            }

            return divider
        }
    }


    override fun onBackPressed() {
        /**
         * 判断是否拦截返回键操作
         */
        if (!emotionMainFragment.isInterceptBackPress()) {
            finish()
        }
    }
}
