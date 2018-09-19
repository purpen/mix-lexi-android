package com.thn.lexi.order

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.thn.lexi.*
import kotlinx.android.synthetic.main.acticity_header_recyclerview.*
import kotlinx.android.synthetic.main.footer_select_express.view.*
import kotlinx.android.synthetic.main.header_express_send_address.view.*
import org.greenrobot.eventbus.EventBus


class SelectExpressActivity : BaseActivity(), SelectExpressContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: SelectExpressPresenter by lazy { SelectExpressPresenter(this) }

    private val adapter: AdapterSelectExpress by lazy { AdapterSelectExpress(R.layout.adapter_select_express) }
    private val adapterGoods: AdapterGoodsInSelectExpress by lazy { AdapterGoodsInSelectExpress(R.layout.adapter_goods_in_select_express) }
    private lateinit var footerView: View
    override val layout: Int = R.layout.acticity_header_recyclerview

    //选择配送方式参数
    private lateinit var selectExpressRequestBean: SelectExpressRequestBean

    private var clickedExpress: ExpressInfoBean? = null

    override fun getIntentData() {
        if (intent.hasExtra(SelectExpressActivity::class.java.simpleName)) {
            selectExpressRequestBean = intent.getParcelableExtra(SelectExpressActivity::class.java.simpleName)
        }
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_select_send_express)
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isEnabled = false


        val headerViewGoods = View.inflate(this, R.layout.header_recyclerview, null) as RecyclerView
        val customLinearLayoutManager = CustomLinearLayoutManager(this)
        customLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        customLinearLayoutManager.setScrollEnabled(false)
        headerViewGoods.layoutManager = customLinearLayoutManager
        headerViewGoods.adapter = adapterGoods
        adapterGoods.setNewData(selectExpressRequestBean.items)
        headerViewGoods.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_e9e9e9, headerViewGoods, 1f))
        adapter.setHeaderView(headerViewGoods)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 1f))
        val headerView = View.inflate(this, R.layout.header_express_send_address, null)
        headerView.textViewFromAddress.text = selectExpressRequestBean.expressSendAddress
        adapter.addHeaderView(headerView)

        footerView = View.inflate(this, R.layout.footer_select_express, null)
        adapter.setFooterView(footerView)
    }

    override fun setPresenter(presenter: SelectExpressContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {

        //快递类型
        adapter.setOnItemClickListener { _, _, position ->
            val data = adapter.data
            for (item in data) {
                item.is_default = false
            }
            val item = adapter.getItem(position) as ExpressInfoBean
            clickedExpress = item
            item.is_default = true
            footerView.textViewPrice.text = "${item.freight}"
            adapter.notifyDataSetChanged()
        }
    }


    override fun setNewData(expresses: MutableList<ExpressInfoBean>) {
        for (item in expresses) {
            if (TextUtils.equals(item.express_id, selectExpressRequestBean.defaultExpress.express_id)) {
                item.is_default = true
                footerView.textViewPrice.text = "${item.freight}"
                break
            }
        }

        adapter.setNewData(expresses)
    }


    override fun requestNet() {
        presenter.loadData(selectExpressRequestBean)
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


    /**
     * 改变默认物流
     */
    private fun changeDefaultExpress() {
        val express = MessageUpdateDefaultExpress()
        if (clickedExpress != null) {
            val list = selectExpressRequestBean.productBean.express
            express.product_rid = selectExpressRequestBean.productBean.product_rid
            express.store_rid = selectExpressRequestBean.productBean.store_rid
            for (item in list) {
                if(TextUtils.equals(item.express_id, clickedExpress!!.express_id)){
                    express.express_id = item.express_id
                    break
                }
            }
            EventBus.getDefault().post(express)
        }
    }

    override fun onDestroy() {
        changeDefaultExpress()
        super.onDestroy()
    }

}
