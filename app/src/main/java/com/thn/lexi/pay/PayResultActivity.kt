package com.thn.lexi.pay
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.Util
import com.basemodule.ui.BaseActivity
import com.thn.lexi.AppApplication
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import com.thn.lexi.order.CreateOrderBean
import com.thn.lexi.orderList.OrderListActivity
import kotlinx.android.synthetic.main.acticity_pay_result.*
import kotlinx.android.synthetic.main.header_view_pay_result.view.*

class PayResultActivity : BaseActivity() {
    private lateinit var headerView: View
    private val adapter: AdapterPayResultByPavilion by lazy { AdapterPayResultByPavilion(R.layout.adapter_pay_result) }
    override val layout: Int = R.layout.acticity_pay_result
    private var createOrderBean: CreateOrderBean? = null

    override fun getIntentData() {
        createOrderBean = intent.getParcelableExtra(TAG)
    }

    override fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        headerView = View.inflate(this, R.layout.header_view_pay_result, null)

        headerView.textViewSubtotalPrice.text = "${createOrderBean?.orderTotalPrice}"
        headerView.textViewSubtotalPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit,R.dimen.dp10,R.dimen.dp12),null,null,null)


        headerView.textViewTotalPrice.text = "${createOrderBean?.userPayTotalPrice}"
        headerView.textViewTotalPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit_ff6666,R.dimen.dp10,R.dimen.dp12),null,null,null)



        //设置收货人信息
        headerView.textViewUserInfo.text = "${createOrderBean?.consigneeInfo?.first_name}，${createOrderBean?.consigneeInfo?.full_address}，${createOrderBean?.consigneeInfo?.zipcode}"

        if (createOrderBean?.expressTotalPrice == 0.0) {
            headerView.textViewDeliveryPrice.text = "包邮"
            headerView.textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_c2a67d))
            headerView.textViewDeliveryPrice.setCompoundDrawables(null,null,null,null)
        } else {
            headerView.textViewDeliveryPrice.text = "${createOrderBean?.expressTotalPrice}"
            headerView.textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_333))
            headerView.textViewDeliveryPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit,R.dimen.dp10,R.dimen.dp11),null,null,null)
        }


        adapter.addHeaderView(headerView)


        val footerView = View(this)
        footerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DimenUtil.dp2px(30.0))
        footerView.setBackgroundColor(Util.getColor(R.color.color_f5f7f9))
        adapter.addFooterView(footerView)

        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 10f))

        adapter.setNewData(createOrderBean?.store_items)
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