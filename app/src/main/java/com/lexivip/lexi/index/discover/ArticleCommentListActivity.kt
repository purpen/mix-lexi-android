package com.lexivip.lexi.index.discover

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.discoverLifeAesthetics.IOnSendCommentListener
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowCommentListAdapter
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowCommentListBean
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.view.emotionkeyboardview.fragment.EmotionMainFragment
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.activity_article_comment_list.*


class ArticleCommentListActivity : BaseActivity(), ArticleDetailContract.View {

    override val layout: Int = R.layout.activity_article_comment_list

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: ArticleDetailPresenter by lazy { ArticleDetailPresenter(this) }

    private val adapter: ArticleCommentListAdapter by lazy { ArticleCommentListAdapter(R.layout.adapter_comment_list, presenter) }

    private lateinit var articleId: String
    private lateinit var emotionMainFragment: EmotionMainFragment

    private var commentCount = 0

    //父级评论id
    private var pid: String = "0"

    //回复哪条评论
    private var replyId: String = "0"

    override fun setPresenter(presenter: ArticleDetailContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun getIntentData() {
        if (intent.hasExtra(TAG)) {
            articleId = intent.getStringExtra(TAG)
        }
    }

    override fun initView() {
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext))
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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

//        bundle.putBoolean(EmotionMainFragment.IS_LIKE, shopWindowData.is_like)
//        bundle.putInt(EmotionMainFragment.LIKE_COUNTS, shopWindowData.like_count)

        emotionMainFragment = EmotionMainFragment.newInstance(bundle)
        emotionMainFragment.bindToContentView(swipeRefreshLayout)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutEmotion, emotionMainFragment)
        transaction.addToBackStack(null)
        //提交修改
        transaction.commit()
    }

    override fun installListener() {

        //点击输入框获取焦点
        textViewInput.setOnClickListener {
            emotionMainFragment.requestFocus()
        }

        val intArray = IntArray(2)
        textViewInput.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            textViewInput.getLocationOnScreen(intArray)
            if (intArray[1] > ScreenUtil.getScreenHeight() * 2 / 3) { //键盘被关闭
                if (emotionMainFragment.isUserInputEmpty()) {
                    relativeLayoutBar.visibility = View.VISIBLE
                }
            } else { //键盘打开
                relativeLayoutBar.visibility = View.INVISIBLE
                emotionMainFragment.requestFocus()

            }
        }


        emotionMainFragment.setOnSendCommentListener(object : IOnSendCommentListener {
            override fun onSend(sendButton: Button, editText: EditText) {
                if (UserProfileUtil.isLogin()) {
                    val content = editText.text.trim().toString()
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.showInfo("请先输入评论")
                        return
                    }
                    presenter.submitComment(articleId, pid,replyId,content, sendButton)
                    editText.text.clear()
                    emotionMainFragment.hideKeyBoard()
                } else {
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                }
            }
        })

        //点击输入框外部关闭键盘
        adapter.setOnItemClickListener { _, _, _ ->
            if (emotionMainFragment.isUserInputEmpty()) {
                pid = "0"
                replyId ="0"
                emotionMainFragment.setEditTextHint(getString(R.string.text_add_comment))
            }
            emotionMainFragment.hideKeyBoard()
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true, articleId)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreArticleComments()
        }, recyclerView)


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

        /**
         * 子评论点击时
         */
        adapter.setOnSubCommentClickListener(object : ShowWindowCommentListAdapter.OnSubCommentClickListener {
            override fun onClick(commentBean: CommentBean) {
                showKeyboardAndReplyWho(commentBean)
            }
        })
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
        pid = "0"
        replyId = "0"
        emotionMainFragment.resetInputBarState()
    }


    /**
     * 当评论提交成功
     */
    override fun noticeCommentSuccess(commentBean: CommentBean) {
        commentCount++
        customHeadView.setHeadCenterTxtShow(true, "${commentCount}条评论")
        resetInputBarState()
        if (TextUtils.equals(commentBean.pid, "0")) { //评论橱窗
            adapter.addData(0, commentBean)
        } else {//子评论,添加到评论列表最后
            val list = adapter.data
            for (item in list) {
                if (TextUtils.equals(item.comment_id, commentBean.pid)) { //子评论数+1
                    item.sub_comment_count += 1
                    if (item.sub_comments == null) item.sub_comments = ArrayList<CommentBean>()
                    item.sub_comments.add(0,commentBean)
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
    override fun setPraiseCommentState(b: Boolean, position: Int, subAdapter: Boolean) {
        val commentsBean = adapter.getItem(position) as CommentBean
        if (b) {
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

    override fun setCommentListData(data: ShowWindowCommentListBean.DataBean) {
        commentCount = data.count
        customHeadView.setHeadCenterTxtShow(true, "${commentCount}条评论")
        emotionMainFragment.requestFocus()
        adapter.setNewData(data.comments)
    }


    override fun addData(comments: List<CommentBean>) {
        adapter.addData(comments)
    }

    override fun requestNet() {
        presenter.getArticleComments(articleId, false)
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
