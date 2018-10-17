package com.lexivip.lexi.order

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.ui.IDataSource
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.lexivip.lexi.CustomLinearLayoutManager
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean
import com.lexivip.lexi.index.detail.ShopCouponListBean
import kotlinx.android.synthetic.main.dialog_pavilion_coupon_bottom.view.*
import kotlinx.android.synthetic.main.header_coupon_bottom_dialog.view.*
import org.greenrobot.eventbus.EventBus
import java.io.IOException


class OfficialCouponBottomDialog(context: Context, presenter: ConfirmOrderPresenter, sumPrice: Double, createOrderBean: CreateOrderBean) : BottomBaseDialog<OfficialCouponBottomDialog>(context) {
    private val present: ConfirmOrderPresenter by lazy { presenter }
    private val orderBean: CreateOrderBean by lazy { createOrderBean }
    private val price: Double by lazy { sumPrice }
    private val adapterDialogCoupon: AdapterDialogPavilionCoupon by lazy { AdapterDialogPavilionCoupon(R.layout.adapter_dialog_official_coupon) }
    private lateinit var view: View
    private lateinit var headerView: View

    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_pavilion_coupon_bottom, null)
        present.getOfficialCoupons(price,object:IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.progressBar.visibility = View.VISIBLE
            }

            override fun onSuccess(json: String) {
                view.progressBar.visibility = View.GONE
                val shopCouponListBean = JsonUtil.fromJson(json, ShopCouponListBean::class.java)
                if (shopCouponListBean.success) {
                    adapterDialogCoupon.setNewData(shopCouponListBean.data.coupons)
                } else {
                    ToastUtil.showError(shopCouponListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.progressBar.visibility = View.GONE
            }
        })
        return view
    }


    override fun setUiBeforShow() {
        headerView = View.inflate(context, R.layout.header_coupon_bottom_dialog, null)
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


        var selectedCoupon: CouponBean? =null

        //有选中优惠券
        for (coupon in adapterDialogCoupon.data) {
            if (coupon.selected) {
                selectedCoupon =coupon
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

            selectedCoupon!!.selected = !selected

            adapterDialogCoupon.notifyDataSetChanged()

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
                dismiss()
                return@setOnClickListener
            }

            if (!selectedCoupon!!.selected) { //取消选中优惠券
                orderBean.officialCouponPrice = 0
                orderBean.officialCouponCode = ""
            } else {
                orderBean.officialCouponPrice = selectedCoupon!!.amount
                orderBean.officialCouponCode = selectedCoupon!!.code
            }
            EventBus.getDefault().post(selectedCoupon)
            dismiss()
        }
    }
}