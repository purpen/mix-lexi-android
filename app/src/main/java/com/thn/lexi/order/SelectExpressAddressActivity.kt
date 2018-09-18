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
import com.thn.lexi.address.AddressActivity
import com.thn.lexi.orderList.OrderListActivity
import kotlinx.android.synthetic.main.acticity_select_express_address.*


class SelectExpressAddressActivity : BaseActivity(), SelectExpressAddressContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: SelectExpressAddressPresenter by lazy { SelectExpressAddressPresenter(this) }

    private val adapter: AdapterUserAddressList by lazy { AdapterUserAddressList(R.layout.adapter_user_express_address) }

    override val layout: Int = R.layout.acticity_select_express_address

    private lateinit var footerView: View

    //订单信息
    private lateinit var createOrderBean: CreateOrderBean

    override fun getIntentData() {
        if (intent.hasExtra(SelectExpressAddressActivity::class.java.simpleName)) {
            createOrderBean = intent.getParcelableExtra(SelectExpressAddressActivity::class.java.simpleName)
        }
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_select_express_address)
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

        footerView.setOnClickListener {
            val intent =Intent(this,OrderListActivity::class.java)
            intent.putExtra(OrderListActivity::class.java.simpleName,createOrderBean.address_rid)
            startActivity(intent)
        }

        adapter.setOnItemClickListener { _, _, position ->
            val data = adapter.data
            for (item in data) {
                item.is_default = false
            }

            val item = adapter.getItem(position) as UserAddressListBean.DataBean
            item.is_default = true

            adapter.notifyDataSetChanged()

            setConfirmOrderButtonState()
        }

        buttonConfirmOrder.setOnClickListener {

            var selectedItem: UserAddressListBean.DataBean? =null

            for (item in adapter.data){
                if (item.is_default){
                    selectedItem = item
                    break
                }
            }

            if (selectedItem==null) {
                ToastUtil.showInfo("请先选择收货地址")
                return@setOnClickListener
            }

            createOrderBean.consigneeInfo = selectedItem

            val intent = Intent(this,ConfirmOrderActivity::class.java)
            intent.putExtra(ConfirmOrderActivity::class.java.simpleName,createOrderBean)
            startActivity(intent)
        }
    }


    override fun setNewData(addresses: MutableList<UserAddressListBean.DataBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(addresses)
        setConfirmOrderButtonState()
    }

    /**
     * 设置去确认订单界面按钮状态
     */
    private fun setConfirmOrderButtonState() {
        val data = adapter.data

        var hasChecked = false
        for (item in data) {
            if (item.is_default) {
                //设置默认地址rid
                createOrderBean.address_rid = item.rid
                hasChecked = true
                break
            }
        }
        buttonConfirmOrder.isEnabled = hasChecked
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
