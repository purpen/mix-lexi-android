package com.thn.lexi.mine

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import kotlinx.android.synthetic.main.activity_mine_dynamic.*
import kotlinx.android.synthetic.main.view_head_mine_dynamic.view.*

/**
val icon = ContextCompat.getDrawable(AppApplication.getContext(), R.mipmap.icon_praise_active)
icon?.bounds = bounds
textViewPraise.setCompoundDrawables(icon,null,null,null)
 */
class DynamicActivity : BaseActivity(), DynamicContract.View {

    private val presenter: DynamicPresenter by lazy { DynamicPresenter(this) }

    private val adapter: AdapterDynamicAdapter by lazy { AdapterDynamicAdapter(R.layout.adapter_mine_dynamic) }

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    override val layout: Int = R.layout.activity_mine_dynamic

    private lateinit var headerView: View

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_dynamic_activity)
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        headerView = LayoutInflater.from(this).inflate(R.layout.view_head_mine_dynamic, null)
        adapter.addHeaderView(headerView)
        adapter.setHeaderAndEmpty(true)
        adapter.emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view_dynamic, null)
    }

    override fun setPresenter(presenter: DynamicContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun requestNet() {
        presenter.loadData(false)
    }


    override fun setNewData(data: DynamicBean.DataBean) {
        GlideUtil.loadImage(data.bg_cover,headerView.imageViewHeader)
        GlideUtil.loadCircleImageWidthDimen(data.user_avatar,headerView.imageViewAvatar,DimenUtil.getDimensionPixelSize(R.dimen.dp60))
        headerView.textViewName.text = data.username
    }

    override fun installListener() {
        headerView.linearLayoutPublishWindow.setOnClickListener{
            ToastUtil.showInfo("拼接橱窗")
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)


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
