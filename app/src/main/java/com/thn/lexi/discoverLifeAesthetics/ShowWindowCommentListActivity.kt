package com.thn.lexi.discoverLifeAesthetics
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import kotlinx.android.synthetic.main.activity_show_window_comment_list.*


class ShowWindowCommentListActivity : BaseActivity(),ShowWindowCommentContract.View {

    override val layout: Int = R.layout.activity_show_window_comment_list

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: ShowWindowCommentPresenter by lazy { ShowWindowCommentPresenter(this) }

    private val adapter:ShowWindowCommentListAdapter by lazy { ShowWindowCommentListAdapter(R.layout.adapter_comment_list) }

    private lateinit var rid:String

    override fun setPresenter(presenter: ShowWindowCommentContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun getIntentData() {
        if(intent.hasExtra(ShowWindowCommentListActivity::class.java.simpleName)){
            rid = intent.getStringExtra(ShowWindowCommentListActivity::class.java.simpleName)
        }
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true,"4条评论")
        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
//        recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.VERTICAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }


    override fun installListener() {

    }

    override fun setNewData(comments: MutableList<ShowWindowCommentListBean.DataBean.CommentsBean>) {
        swipeRefreshLayout.isRefreshing = false

        var demos = ArrayList<ShowWindowCommentListBean.DataBean.CommentsBean>()

        for (i in 0..3) {
            val commentBean = ShowWindowCommentListBean.DataBean.CommentsBean()
            demos.add(commentBean)
        }

        for (item in demos) {
            item.user_avatar = "http://imgtu.5011.net/uploads/content/20170209/4934501486627131.jpg"
            item.user_name = "姗姗来迟"
            item.created_at = System.currentTimeMillis()/1000
            item.content = "哈教科书代姐啊代姐哈空间大代姐大声疾活动空 间点,环撒打算的"
            item.praise_count = 102
            val list = ArrayList<ShowWindowCommentListBean.DataBean.CommentsBean.SubCommentsBean>()
            for (i in 0..4) {
                val subItem = ShowWindowCommentListBean.DataBean.CommentsBean.SubCommentsBean()
                subItem.user_avatar = "http://c.hiphotos.baidu.com/image/h%3D300/sign=87d6daed02f41bd5c553eef461d881a0/f9198618367adab4b025268587d4b31c8601e47b.jpg"
                subItem.user_name = "${i}懵乖乖"
                subItem.created_at = System.currentTimeMillis()
                subItem.content = "${i}哈教科书代姐啊代姐哈空间大代姐大声疾"
                item.praise_count = 100
                list.add(subItem)
            }
            item.sub_comments = list
        }

        adapter.setNewData(demos)
//        adapter.setNewData(comments)
    }

    override fun addData(comments: MutableList<ShowWindowCommentListBean.DataBean.CommentsBean>) {
        adapter.addData(comments)
    }

    override fun requestNet() {
        presenter.loadData(rid,false)
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
}
