package com.thn.lexi.order

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.Util
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.thn.lexi.CustomLinearLayoutManager
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import com.thn.lexi.beans.CouponBean
import kotlinx.android.synthetic.main.dialog_coupon_bottom.view.*
import kotlinx.android.synthetic.main.header_coupon_bottom_dialog.view.*


class PavilionCouponBottomDialog(context: Context, presenter: ConfirmOrderPresenter, couponList: ArrayList<CouponBean>) : BottomBaseDialog<PavilionCouponBottomDialog>(context) {
    private val present: ConfirmOrderPresenter by lazy { presenter }
    private val coupons: ArrayList<CouponBean> by lazy { couponList }
    private val adapterDialogCoupon: AdapterDialogPavilionCoupon by lazy { AdapterDialogPavilionCoupon(R.layout.adapter_dialog_pavilion_coupon) }
    private lateinit var view: View
    private lateinit var headerView: View

    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_pavilion_coupon_bottom, null)
        return view
    }

    override fun setUiBeforShow() {
        headerView = View.inflate(context, R.layout.header_coupon_bottom_dialog, null)
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

        view.relativeLayoutBar.setOnClickListener { dismiss() }

        adapterDialogCoupon.setOnItemClickListener { adapter, _, position ->
            //            val couponBean = adapter.getItem(position) as CouponBean
//            present.clickGetCoupon(storeId, couponBean.code, object : IDataSource.HttpRequestCallBack {
//                override fun onSuccess(json: String) {
//                    couponBean.status = 1
//                    adapter.notifyDataSetChanged()
//                }
//
//                override fun onFailure(e: IOException) {
//                }
//            })
        }
    }
}