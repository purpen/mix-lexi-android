package com.lexivip.lexi.mine.like.likeShopWindow

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.text.TextUtils
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean
import kotlinx.android.synthetic.main.activity_like_shop_window.*

class LikeShopWindowActivity : BaseActivity(), LikeShopWindowContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override val layout: Int = R.layout.activity_like_shop_window
    private val presenter: LikeShopWindowPresenter by lazy { LikeShopWindowPresenter(this) }
    private val adapter: AdapterLikeShowWindow by lazy { AdapterLikeShowWindow(R.layout.adapter_like_shop_window) }

    private var uid: String? = null

    override fun getIntentData() {
        uid = intent.getStringExtra(TAG)
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.text_shop_window_like)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 10f))
    }

    override fun requestNet() {
        if (TextUtils.isEmpty(uid)) {
            presenter.loadData(false)
        } else {
            presenter.loadData(uid!!, false)

        }
    }

    override fun installListener() {
        adapter.setOnLoadMoreListener({
            if (TextUtils.isEmpty(uid)) {

                presenter.loadMoreData()
            } else {
                presenter.loadMoreData(uid!!)
            }
        }, recyclerView)

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2ShopWindowDetailActivity(item.rid)
        }
    }

    override fun setNewData(data: MutableList<ShopWindowBean>) {
        adapter.setNewData(data)
    }

    override fun addData(windows: MutableList<ShopWindowBean>) {
        adapter.addData(windows)
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

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun setPresenter(presenter: LikeShopWindowContract.Presenter?) {
        setPresenter(presenter)
    }
}