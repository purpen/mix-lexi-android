package com.thn.lexi.index.detail

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.AppApplication
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import com.thn.lexi.beans.UserBean
import kotlinx.android.synthetic.main.acticity_header_recyclerview.*


class FavoriteUserListActivity : BaseActivity(), FavoriteUserListContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: FavoriteUserListPresenter by lazy { FavoriteUserListPresenter(this) }

    private val adapter: AdapterFavoriteUserList by lazy { AdapterFavoriteUserList(R.layout.adapter_favorite_user_list) }

    override val layout: Int = R.layout.acticity_header_recyclerview

    private var goodsId: String? = null

    override fun getIntentData() {
        if (intent.hasExtra(FavoriteUserListActivity::class.java.simpleName)) {
            goodsId = intent.getStringExtra(FavoriteUserListActivity::class.java.simpleName)
        }
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_favorite_goods_person)
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 1f))
        val view = View(this)
        adapter.addHeaderView(view)
    }

    override fun setPresenter(presenter: FavoriteUserListContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {
        swipeRefreshLayout.setOnRefreshListener {
            if (TextUtils.isEmpty(goodsId)) return@setOnRefreshListener
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(goodsId!!, true)
        }

        adapter.setOnLoadMoreListener({
            if (TextUtils.isEmpty(goodsId)) return@setOnLoadMoreListener
            presenter.loadMoreData(goodsId!!)
        }, recyclerView)


        adapter.setOnItemChildClickListener { adapter, v, position ->
            val usersBean = adapter.getItem(position) as UserBean

            presenter.focusUser(usersBean.uid, v, usersBean.followed_status,position)
        }

        adapter.setOnItemClickListener { adapter, view, position ->
//            ToastUtil.showInfo("跳转用户")
//            val showWindowBean = adapter.getItem(position) as ShowWindowBean.DataBean.ShopWindowsBean
//            val intent = Intent(context, ShowWindowDetailActivity::class.java)
//            intent.putExtra(ShowWindowDetailActivity::class.java.simpleName, showWindowBean)
//            startActivity(intent)
        }
    }

    /**
     * 设置用户关注状态
     */
    override fun setFocusState(followed_status: Int, position: Int) {
        val usersBean = adapter.getItem(position) as UserBean
        usersBean.followed_status = followed_status
        adapter.notifyDataSetChanged()
    }

    override fun setNewData(users: MutableList<UserBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(users)
    }

    override fun addData(users: MutableList<UserBean>) {
        adapter.addData(users)
        adapter.setEnableLoadMore(true)
    }

    override fun requestNet() {
        if (TextUtils.isEmpty(goodsId)) return

        presenter.loadData(goodsId!!, false)
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

}
