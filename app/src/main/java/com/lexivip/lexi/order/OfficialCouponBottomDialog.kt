package com.lexivip.lexi.order

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.Util
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.lexivip.lexi.CustomLinearLayoutManager
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean
import kotlinx.android.synthetic.main.dialog_pavilion_coupon_bottom.view.*
import kotlinx.android.synthetic.main.header_coupon_bottom_dialog.view.*
import org.greenrobot.eventbus.EventBus


class OfficialCouponBottomDialog(context: Context,createOrderBean: CreateOrderBean, officialCoupons: List<CouponBean>) : BottomBaseDialog<OfficialCouponBottomDialog>(context) {
    private val orderBean: CreateOrderBean by lazy { createOrderBean }
    private val coupons: List<CouponBean> by lazy { officialCoupons }
    private val adapterDialogCoupon: AdapterDialogPavilionCoupon by lazy { AdapterDialogPavilionCoupon(R.layout.adapter_dialog_official_coupon) }
    private lateinit var view: View
    private lateinit var headerView: View

    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_pavilion_coupon_bottom, null)
        return view
    }


    override fun setUiBeforShow() {
        headerView = View.inflate(context, R.layout.header_coupon_bottom_dialog, null)
        headerView.relativeLayoutNoUseCoupon.visibility = View.VISIBLE
        headerView.textViewCouponTitle.text = Util.getString(R.string.text_official_coupon)
        adapterDialogCoupon.addHeaderView(headerView)

        val linearLayoutManager = CustomLinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL


        val footerView = View(context)
        footerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp26))
        adapterDialogCoupon.addFooterView(footerView)


        view.recyclerViewCoupon.setHasFixedSize(true)
        view.recyclerViewCoupon.layoutManager = linearLayoutManager
        view.recyclerViewCoupon.adapter = adapterDialogCoupon
        view.recyclerViewCoupon.addItemDecoration(DividerItemDecoration(context, android.R.color.transparent, view.recyclerViewCoupon, 15f))
        adapterDialogCoupon.setNewData(coupons)

        var selectedCoupon: CouponBean? =null

        //有选中优惠券
        for (coupon in adapterDialogCoupon.data) {
            if (coupon.selected) {
                LogUtil.e("fasdfsafsdfdsdsfsdfsdfsdfsd")
                selectedCoupon =coupon
                view.textViewReducePrice.visibility = View.VISIBLE
                view.textViewUseCouponNum.visibility = View.VISIBLE
                view.textViewReducePrice.text = "已抵扣${coupon.amount}元"
                view.textViewUseCouponNum.text = "使用1张"
                break
            }
        }

        adapterDialogCoupon.setOnItemClickListener { _, _, position ->
            selectedCoupon = adapterDialogCoupon.getItem(position) as CouponBean

            var selected = selectedCoupon!!.selected

            for (item in adapterDialogCoupon.data) {
                item.selected = false
            }

            headerView.checkBox.isChecked = false

            selectedCoupon!!.selected = !selected

            adapterDialogCoupon.notifyDataSetChanged()

            view.textViewReducePrice.visibility = View.VISIBLE
            view.textViewUseCouponNum.visibility = View.VISIBLE

            if (selectedCoupon!!.selected) {
                view.textViewReducePrice.text = "已抵扣${selectedCoupon!!.amount}元"
                view.textViewUseCouponNum.text = "使用1张"
            } else {
                view.textViewReducePrice.text = "已抵扣0元"
                view.textViewUseCouponNum.text = "使用0张"
            }
        }

        //点击完成
        view.textViewConfirm.setOnClickListener {

            if (selectedCoupon == null) {
                orderBean.officialCouponPrice = 0
                orderBean.officialCouponCode = ""
                orderBean.notUsingOfficialCoupon = true
                EventBus.getDefault().post(CouponBean())
                dismiss()
                return@setOnClickListener
            }

            if (!selectedCoupon!!.selected) { //取消选中优惠券
                orderBean.officialCouponPrice = 0
                orderBean.officialCouponCode = ""
                orderBean.notUsingOfficialCoupon = true
            } else {
                orderBean.officialCouponPrice = selectedCoupon!!.amount
                orderBean.officialCouponCode = selectedCoupon!!.code
                orderBean.notUsingOfficialCoupon = false
            }
            EventBus.getDefault().post(selectedCoupon)
            dismiss()
        }

        //不使用优惠券
        headerView.relativeLayoutNoUseCoupon.setOnClickListener {
            for (item in adapterDialogCoupon.data) {
                item.selected = false
            }
            adapterDialogCoupon.notifyDataSetChanged()
            headerView.checkBox.isChecked = true
            view.textViewReducePrice.visibility = View.GONE
            view.textViewUseCouponNum.visibility = View.GONE
            selectedCoupon = null
        }
    }
}