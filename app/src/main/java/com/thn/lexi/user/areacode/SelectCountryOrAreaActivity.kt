package com.thn.lexi.user.areacode

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.AppApplication
import com.thn.lexi.Constants
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import kotlinx.android.synthetic.main.acticity_select_country_area.*
import kotlinx.android.synthetic.main.view_custom_headview.view.*

class SelectCountryOrAreaActivity : BaseActivity(),SelectCountryAreaContract.View {
    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }
    private var page: Int = 1
    private lateinit var adapter: AreaCodeAdapter
    override val layout: Int = R.layout.acticity_select_country_area
    private lateinit var presenter: SelectCountryAreaPresenter
    override fun initView() {
        adapter = AreaCodeAdapter(R.layout.adapter_area_code)
        presenter = SelectCountryAreaPresenter(this)
        customHeadView.head_goback.visibility = View.GONE
        customHeadView.setHeadCenterTxtShow(true, R.string.title_select_country_area)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.VERTICAL, resources.getDimensionPixelSize(R.dimen.dp1), resources.getColor(R.color.color_d1d1d1)))
    }

    override fun installListener() {
        imageViewClose.setOnClickListener {
            finish()
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            requestNet()
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData(page)
        }, recyclerView)


        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as CountryAreaCodeBean.DataBean.AreaCodesBean
            val intent = Intent()
            intent.putExtra(SelectCountryOrAreaActivity::class.java.simpleName,item)
            setResult(RESULT_OK,intent)
            finish()
        }
    }

    override fun requestNet() {
        page=1
        presenter.loadData(page)
    }


    override fun setNewData(data: List<CountryAreaCodeBean.DataBean.AreaCodesBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
        ++page
    }


    override fun addData(area_codes: List<CountryAreaCodeBean.DataBean.AreaCodesBean>) {
        adapter.addData(area_codes)
        ++page
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun setPresenter(presenter: SelectCountryAreaContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun showLoadingView() {
        if (!swipeRefreshLayout.isRefreshing) dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun showError(string: String) {
        swipeRefreshLayout.isRefreshing = false
        adapter.loadMoreFail()
    }


}