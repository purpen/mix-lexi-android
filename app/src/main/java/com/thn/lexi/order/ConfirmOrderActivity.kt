package com.thn.lexi.order

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.AppApplication
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import kotlinx.android.synthetic.main.acticity_submit_order.*


class ConfirmOrderActivity : BaseActivity(), SelectExpressAddressContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: SelectExpressAddressPresenter by lazy { SelectExpressAddressPresenter(this) }

    private val adapter: AdapterUserAddressList by lazy { AdapterUserAddressList(R.layout.adapter_user_express_address) }

    override val layout: Int = R.layout.acticity_submit_order

    private lateinit var footerView: View


    override fun getIntentData() {
        if (intent.hasExtra(ConfirmOrderActivity::class.java.simpleName)) {
//            goodsId = intent.getStringExtra(ConfirmOrderActivity::class.java.simpleName)
        }
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_submit_order)
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 1f))
        val view = View(this)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp10))
        view.setBackgroundColor(Util.getColor(android.R.color.transparent))
        adapter.addHeaderView(view)

        footerView = View.inflate(this, R.layout.footer_add_new_address, null)
        adapter.addFooterView(footerView)
    }

    override fun setPresenter(presenter: SelectExpressAddressContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {

        adapter.setOnItemClickListener { _, _, position ->
            val data = adapter.data
            for (item in data) {
                item.is_default = false
            }

            val item = adapter.getItem(position) as UserAddressListBean.DataBean
            item.is_default = true

            adapter.notifyDataSetChanged()
        }

        buttonSubmitOrder.setOnClickListener {

//            val intent = Intent(this,ConfirmOrderActivity::class.java)
//            startActivity(intent)
        }
    }


    override fun setNewData(addresses: MutableList<UserAddressListBean.DataBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(addresses)
    }



    override fun requestNet() {
        presenter.loadData()
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
