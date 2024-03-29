package com.lexivip.lexi.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.UserBean
import com.lexivip.lexi.user.OtherUserCenterActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class FragmentSearchUserList : BaseFragment(), SearchUserListContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val presenter: SearchUserListPresenter by lazy { SearchUserListPresenter(this) }

    private val adapter: AdapterSearchUserList by lazy { AdapterSearchUserList(R.layout.adapter_search_user_list) }

    override val layout: Int = R.layout.fragment_recyclerview

    private var searchString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchString = arguments?.getString(FragmentSearchUserList::class.java.simpleName)
    }

    companion object {
        @JvmStatic
        fun newInstance(searchString: String): FragmentSearchUserList {
            val fragment = FragmentSearchUserList()
            val bundle = Bundle()
            bundle.putString(FragmentSearchUserList::class.java.simpleName, searchString)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 1f))
    }

    override fun setPresenter(presenter: SearchUserListContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)


        adapter.setOnItemChildClickListener { adapter, v, position ->
            if (UserProfileUtil.isLogin()) {
                val usersBean = adapter.getItem(position) as UserBean
                presenter.focusUser(usersBean.uid, v, usersBean.follow_status, position)
            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2OtherUserCenterActivity(item.uid)
        }
    }

    /**
     * 设置用户关注状态
     */
    override fun setFocusState(followed_status: Int, position: Int) {
        val usersBean = adapter.getItem(position) as UserBean
        usersBean.follow_status = followed_status
        adapter.notifyDataSetChanged()
    }

    override fun loadData() {
        if (TextUtils.isEmpty(searchString)) return
        presenter.loadData(searchString!!, false)
    }

    override fun setNewData(users: MutableList<UserBean>) {
        adapter.setNewData(users)
    }

    override fun addData(users: MutableList<UserBean>) {
        adapter.addData(users)
        adapter.setEnableLoadMore(true)
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
