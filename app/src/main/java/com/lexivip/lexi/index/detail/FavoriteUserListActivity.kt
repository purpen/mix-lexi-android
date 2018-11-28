package com.lexivip.lexi.index.detail

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.UserBean
import com.lexivip.lexi.user.OtherUserCenterActivity
import kotlinx.android.synthetic.main.acticity_header_recyclerview.*


class FavoriteUserListActivity : BaseActivity(), FavoriteUserListContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: FavoriteUserListPresenter by lazy { FavoriteUserListPresenter(this) }

    private val adapter: AdapterFavoriteUserList by lazy { AdapterFavoriteUserList(R.layout.adapter_favorite_user_list) }

    override val layout: Int = R.layout.acticity_header_recyclerview

    private var goodsId: String? = null
    private var uid: String? = null
    private var type:Int=0//0：商品喜欢的列表，1：个人中心关注列表，2：个人中心粉丝列表，3：别人的关注列表，4：别人的粉丝列表
    private var title:String?=null

    override fun getIntentData() {
        title=intent.getStringExtra("title")
        type=intent.getIntExtra("type",0)
        if (intent.hasExtra(FavoriteUserListActivity::class.java.simpleName)) {
            goodsId = intent.getStringExtra(FavoriteUserListActivity::class.java.simpleName)
        }else{
            uid=intent.getStringExtra("uid")
        }
    }

    override fun initView() {
        if(intent.hasExtra(FavoriteUserListActivity::class.java.simpleName)) {
            customHeadView.setHeadCenterTxtShow(true, R.string.title_favorite_goods_person)
        }else {
            customHeadView.setHeadCenterTxtShow(true, title)
        }
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
            //presenter.loadData(goodsId!!, true)
            if(type==0) {
                presenter.loadData(goodsId!!, true)
            }else if (type==1||type==2){
                presenter.loadUserData(true,type)
            }else{
                presenter.loadOtherData(uid!!,true,type)
            }
        }

        adapter.setOnLoadMoreListener({
            if(type==0) {
                if (TextUtils.isEmpty(goodsId)) return@setOnLoadMoreListener
                presenter.loadMoreData(goodsId!!)
            }else if (type==1||type==2){
                presenter.loadMoreUserData(type)
            }else{
                if (TextUtils.isEmpty(uid)) return@setOnLoadMoreListener
                presenter.loadMoreOtherData(uid!!,type)
            }
        }, recyclerView)


        adapter.setOnItemChildClickListener { adapter, v, position ->
            val usersBean = adapter.getItem(position) as UserBean

            presenter.focusUser(usersBean.uid, v, usersBean.followed_status,position)
        }

        adapter.setOnItemClickListener { _, _, position ->
            val usersBean = adapter.getItem(position)?:return@setOnItemClickListener
            val intent = Intent(this, OtherUserCenterActivity::class.java)
            intent.putExtra(OtherUserCenterActivity::class.java.simpleName,usersBean.uid)
            startActivity(intent)
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
        LogUtil.e("当前的type："+type)
        if(type==0) {
            LogUtil.e("第一个")
            if (TextUtils.isEmpty(goodsId)) return
            presenter.loadData(goodsId!!, false)
        }else if (type==1||type==2){
            LogUtil.e("第二个")
            presenter.loadUserData(false,type)
        }else{
            LogUtil.e("第三个")
            if (TextUtils.isEmpty(uid)) return
            presenter.loadOtherData(uid!!,false,type)
        }
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
