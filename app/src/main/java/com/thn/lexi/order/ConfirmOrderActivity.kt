package com.thn.lexi.order

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.thn.lexi.AppApplication
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import kotlinx.android.synthetic.main.acticity_submit_order.*
import kotlinx.android.synthetic.main.header_submit_order.view.*


class ConfirmOrderActivity : BaseActivity(), SelectExpressAddressContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: SelectExpressAddressPresenter by lazy { SelectExpressAddressPresenter(this) }

    private val adapter: AdapterOrderByPavilion by lazy { AdapterOrderByPavilion(R.layout.adapter_confirm_order) }

    override val layout: Int = R.layout.acticity_submit_order

    private lateinit var headerView: View

    private lateinit var footerView: View

    //订单信息
    private lateinit var createOrderBean: CreateOrderBean

    override fun getIntentData() {
        if (intent.hasExtra(ConfirmOrderActivity::class.java.simpleName)) {
            createOrderBean = intent.getParcelableExtra(ConfirmOrderActivity::class.java.simpleName)
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

        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 10f))

        adapter.setHeaderFooterEmpty(true,true)

        headerView = View.inflate(this, R.layout.header_submit_order, null)

        adapter.addHeaderView(headerView)

        footerView = View.inflate(this, R.layout.footer_comfirm_order, null)
        adapter.addFooterView(footerView)

        setOrderData()
    }


    /**
     * 设置订单列表数据
     */
    private fun setOrderData() {
        headerView.textViewName.text = createOrderBean.consigneeInfo.full_name
        headerView.textViewPhone.text = createOrderBean.consigneeInfo.mobile
        headerView.textViewAddress.text = createOrderBean.consigneeInfo.full_address
        headerView.textViewZip.text = createOrderBean.consigneeInfo.zipcode

        headerView.textViewSubtotalPrice.text = "${createOrderBean.orderTotalPrice}"

        adapter.setNewData(createOrderBean.store_items)
    }

    override fun setPresenter(presenter: SelectExpressAddressContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {

        adapter.setOnItemClickListener { _, _, position ->
//            val data = adapter.data
//            val item = adapter.getItem(position) as UserAddressListBean.DataBean
//            item.is_default = true
//
//            adapter.notifyDataSetChanged()
        }

        buttonSubmitOrder.setOnClickListener { //提交订单
            ToastUtil.showInfo("提交订单")
//            val intent = Intent(this,ConfirmOrderActivity::class.java)
//            startActivity(intent)
        }
    }


    override fun setNewData(addresses: MutableList<UserAddressListBean.DataBean>) {
//        swipeRefreshLayout.isRefreshing = false
//        adapter.setNewData(addresses)
    }



    override fun requestNet() {
//        presenter.loadData()
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
