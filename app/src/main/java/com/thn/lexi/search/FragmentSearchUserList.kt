package com.thn.lexi.search
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import com.thn.lexi.beans.UserBean
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class FragmentSearchUserList : BaseFragment(), SearchUserListContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val presenter: SearchUserListPresenter by lazy { SearchUserListPresenter(this) }

    private val adapter: AdapterSearchUserList by lazy { AdapterSearchUserList(R.layout.adapter_search_user_list) }

    override val layout: Int = R.layout.fragment_recyclerview

    companion object {
        fun newInstance(): FragmentSearchUserList {
            return FragmentSearchUserList()
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
            presenter.loadMoreData("好")
        }, recyclerView)


        adapter.setOnItemChildClickListener { adapter, v, position ->
            val usersBean = adapter.getItem(position) as UserBean

            presenter.focusUser(usersBean.uid, v, usersBean.follow_status,position)
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
        usersBean.follow_status = followed_status
        adapter.notifyDataSetChanged()
    }

    override fun loadData() {
        presenter.loadData("好", false)
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
