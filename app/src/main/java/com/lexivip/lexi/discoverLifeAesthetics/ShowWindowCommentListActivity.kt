package com.lexivip.lexi.discoverLifeAesthetics
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.LogUtil

import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CommentBean
import com.lexivip.lexi.view.emotionkeyboardview.EmotionKeyboard
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

    private lateinit var rid: String
    private var count: Int =0
    private lateinit var emotionMainFragment:EmotionMainFragment;



    //表情面板
    private lateinit var mEmotionKeyboard: EmotionKeyboard


    override fun setPresenter(presenter: ShowWindowCommentContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun getIntentData() {
        if (intent.hasExtra(ShowWindowCommentListActivity::class.java.simpleName)) {
            rid = intent.getStringExtra(ShowWindowCommentListActivity::class.java.simpleName)
        }
        if (intent.hasExtra(ShowWindowCommentListActivity::class.java.name)) {
            count = intent.getIntExtra(ShowWindowCommentListActivity::class.java.name,0)
            LogUtil.e("count="+count)
        }

    }

    override fun initView() {
        swipeRefreshLayout.isEnabled = false
        customHeadView.setHeadCenterTxtShow(true, "${count}条评论")
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

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(rid, true)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData(rid)
        }, recyclerView)


        adapter.setOnItemChildClickListener { adapter, view, position ->

            val commentsBean = adapter.getItem(position) as CommentBean

            when (view.id) {
                R.id.textViewReply -> {
                    //弹出键盘，输出回复
                }

                R.id.textViewPraise -> {
                    presenter.praiseComment(commentsBean.comment_id,commentsBean.is_praise,position,view,false)
                }
            }
        }

//        editText.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//            override fun afterTextChanged(s: Editable) { //动态设置底部栏高度
//                val lineCount = editText.lineCount
//                var height = DimenUtil.getDimensionPixelSize(R.dimen.dp50) + editText.lineHeight * (lineCount - 1)
//                val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height)
//                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
//                relativeLayoutBar.layoutParams = layoutParams
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//        })
    }

    /**
     * 更新评论点赞状态
     */
    override fun setPraiseCommentState(doPraise: Boolean, position: Int, isSubAdapter: Boolean) {

        if (isSubAdapter) {
            adapter.setPraiseCommentState(doPraise, position)
        } else {
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

    }

    override fun addSubCommentsData(position: Int,comments: MutableList<CommentBean>) {
        adapter.addSubCommentsData(position,comments)
    }


    override fun setNewData(comments: MutableList<CommentBean>) {
        adapter.setNewData(comments)
    }

    override fun addData(comments: MutableList<CommentBean>) {
        adapter.addData(comments)
    }

    override fun requestNet() {
        presenter.loadData(rid, false)
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
                            .setBottomSideLine(true, color, 1f, 20f, 0f)
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
