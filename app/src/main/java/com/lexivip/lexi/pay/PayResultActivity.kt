package com.lexivip.lexi.pay

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.orderList.OrderListActivity
import kotlinx.android.synthetic.main.acticity_pay_result.*
import kotlinx.android.synthetic.main.header_view_pay_result.view.*

class PayResultActivity : BaseActivity(), PayResultContract.View {
    private lateinit var headerView: View
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    private val adapter: AdapterPayResultByPavilion by lazy { AdapterPayResultByPavilion(R.layout.adapter_pay_result) }
    private val presenter: PayResultPresenter by lazy { PayResultPresenter(this) }
    override val layout: Int = R.layout.acticity_pay_result
//    private var createOrderBean: CreateOrderBean? = null


    private lateinit var orderNum: String
    private var payWay: Int = -1
    override fun getIntentData() {
        orderNum = intent.getStringExtra(TAG)
        payWay = intent.getIntExtra(PayResultActivity::class.java.name, -1)
    }

    override fun setPresenter(presenter: PayResultContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        headerView = View.inflate(this, R.layout.header_view_pay_result, null)

        adapter.addHeaderView(headerView)


        val footerView = View(this)
        footerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.dp2px(30.0))
        footerView.setBackgroundColor(Util.getColor(R.color.color_f5f7f9))
        adapter.addFooterView(footerView)

        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 10f))


    }

    override fun requestNet() {
        presenter.loadData(orderNum)
    }

    /**
     * 设置订单数据
     */
    override fun setPayResultData(data: PayResultBean.DataBean) {

        adapter.setNewData(data.orders)

        headerView.textViewSubtotalPrice.text = "${data.total_amount}"
        headerView.textViewSubtotalPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp10, R.dimen.dp12), null, null, null)


        headerView.textViewTotalPrice.text = "${data.user_pay_amount}"
        headerView.textViewTotalPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit_ff6666, R.dimen.dp10, R.dimen.dp12), null, null, null)


        //设置收货人信息
        headerView.textViewUserInfo.text = "${data.buyer_name}，${data.buyer_province}${data.buyer_city}${data.buyer_town}${data.buyer_address}，${data.buyer_zipcode}"


        when (payWay) {
            1 -> {
                headerView.textViewPayName.text = getString(R.string.text_pay_wechat)
                headerView.imageViewPayWay.setImageResource(R.mipmap.icon_wechat_pay)
            }
            2 -> {
                headerView.textViewPayName.text = getString(R.string.text_pay_ali)
                headerView.imageViewPayWay.setImageResource(R.mipmap.icon_alipay)
            }
            3 -> {
                headerView.textViewPayName.text = getString(R.string.text_pay_ant)
                headerView.imageViewPayWay.setImageResource(R.mipmap.icon_ant_pay)
            }
        }

        if (data.freight == 0.0) {
            headerView.textViewDeliveryPrice.text = "包邮"
            headerView.textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_c2a67d))
            headerView.textViewDeliveryPrice.setCompoundDrawables(null, null, null, null)
        } else {
            headerView.textViewDeliveryPrice.text = "${data.freight}"
            headerView.textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_333))
            headerView.textViewDeliveryPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp10, R.dimen.dp11), null, null, null)
        }
    }


    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(error: String) {
        ToastUtil.showError(error)
    }

    override fun installListener() {

        imageViewClose.setOnClickListener {
            finish()
        }
        //查看我的订单
        headerView.textViewLookAllOrder.setOnClickListener {
            startActivity(Intent(this, OrderListActivity::class.java))
        }
    }


}