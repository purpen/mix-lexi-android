package com.thn.lexi.discoverLifeAesthetics

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
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

    override fun setPresenter(presenter: ShowWindowCommentContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun getIntentData() {
        if (intent.hasExtra(ShowWindowCommentListActivity::class.java.simpleName)) {
            rid = intent.getStringExtra(ShowWindowCommentListActivity::class.java.simpleName)
        }
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, "4条评论")
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext))
    }


    override fun installListener() {

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
                    commentsBean.comment_id = "110"
                    if (commentsBean.is_praise){
                        presenter.cancelPraiseComment(commentsBean.comment_id, position, view, false)
                    }else{
                        presenter.praiseComment(commentsBean.comment_id, position, view, false)
                    }

                }
            }
        }
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
                commentsBean.praise_count += 1
            } else {
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
        swipeRefreshLayout.isRefreshing = false

        var demos = ArrayList<CommentBean>()

        for (i in 0..3) {
            val commentBean = CommentBean()
            demos.add(commentBean)
        }

        for (item in demos) {
            item.user_avatar = "http://imgtu.5011.net/uploads/content/20170209/4934501486627131.jpg"
            item.user_name = "姗姗来迟"
            item.created_at = System.currentTimeMillis() / 1000
            item.content = "哈教科书代姐啊代姐哈空间大代姐大声疾活动空 间点,环撒打算的"
            item.praise_count = 102
            val list = ArrayList<CommentBean>()
            for (i in 0..4) {
                val subItem = CommentBean()
                subItem.user_avatar = "http://c.hiphotos.baidu.com/image/h%3D300/sign=87d6daed02f41bd5c553eef461d881a0/f9198618367adab4b025268587d4b31c8601e47b.jpg"
                subItem.user_name = "${i}懵乖乖"
                subItem.created_at = System.currentTimeMillis() / 1000
                subItem.content = "${i}哈教科书代姐啊代姐哈空间大代姐大声疾"
                subItem.praise_count = 10
                list.add(subItem)
            }
            item.sub_comments = list
        }

        adapter.setNewData(demos)
//        adapter.setNewData(comments)
    }

    override fun addData(comments: MutableList<CommentBean>) {
        adapter.addData(comments)
    }

    override fun requestNet() {
        presenter.loadData(rid, false)
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
            var divider: Y_Divider? = null
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
}
