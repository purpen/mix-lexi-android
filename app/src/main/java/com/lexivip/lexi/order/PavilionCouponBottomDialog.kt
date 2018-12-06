package com.lexivip.lexi.order

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.Util
import com.lexivip.lexi.CustomLinearLayoutManager
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.eventBusMessge.MessageUpdate
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean
import com.smart.dialog.widget.base.BottomBaseDialog
import kotlinx.android.synthetic.main.dialog_pavilion_coupon_bottom.view.*
import kotlinx.android.synthetic.main.header_coupon_bottom_dialog.view.*
import org.greenrobot.eventbus.EventBus


class PavilionCouponBottomDialog(context: Context, presenter: ConfirmOrderPresenter, couponList: ArrayList<CouponBean>, itemBean: StoreItemBean) : BottomBaseDialog<PavilionCouponBottomDialog>(context) {
    private val present: ConfirmOrderPresenter by lazy { presenter }
    private val coupons: ArrayList<CouponBean> by lazy { couponList }
    private val store: StoreItemBean by lazy { itemBean }
    private val adapterDialogCoupon: AdapterDialogPavilionCoupon by lazy { AdapterDialogPavilionCoupon(R.layout.adapter_dialog_pavilion_coupon) }
    private lateinit var view: View
    private lateinit var headerView: View

    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_pavilion_coupon_bottom, null)
        return view
    }

    override fun setUiBeforShow() {
        headerView = View.inflate(context, R.layout.header_coupon_bottom_dialog, null)
        headerView.relativeLayoutNoUseCoupon.visibility = View.VISIBLE
        headerView.textViewCouponTitle.text = Util.getString(R.string.text_get_coupon_packet)
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

        var selectedCoupon: CouponBean? = null

        //有选中优惠券
        for (coupon in coupons) {
            if (coupon.selected) {
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

            var selected = selectedCoupon?.selected
            for (item in adapterDialogCoupon.data) {
                item.selected = false
            }

            headerView.checkBox.isChecked = false
            selectedCoupon?.selected = !selected!!
            adapterDialogCoupon.notifyDataSetChanged()

            view.textViewReducePrice.visibility = View.VISIBLE
            view.textViewUseCouponNum.visibility = View.VISIBLE

            if (selectedCoupon!!.selected) {
                view.textViewReducePrice.text = "已抵扣${selectedCoupon?.amount}元"
                view.textViewUseCouponNum.text = "使用1张"
            } else {
                view.textViewReducePrice.text = "已抵扣0元"
                view.textViewUseCouponNum.text = "使用0张"
            }
        }

        //点击完成
        view.textViewConfirm.setOnClickListener {

            if (selectedCoupon == null) {
                store.notUsingCoupon = true
                store.couponPrice = 0
                EventBus.getDefault().post(MessageUpdate())
                dismiss()
                return@setOnClickListener
            }

            if (!selectedCoupon!!.selected) { //未选中取消优惠券
                store.notUsingCoupon = true
                store.couponPrice = 0
            } else {
                store.couponPrice = selectedCoupon!!.amount
                store.coupon_codes = selectedCoupon!!.code
                store.notUsingCoupon = false
            }
            EventBus.getDefault().post(MessageUpdate())
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