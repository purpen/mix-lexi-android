package com.thn.lexi.index.detail

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import com.thn.lexi.beans.CouponBean
import kotlinx.android.synthetic.main.dialog_coupon_bottom.view.*
import kotlinx.android.synthetic.main.header_coupon_bottom_dialog.view.*


class CouponBottomDialog(context: Context, coupons: List<CouponBean>, presenter: GoodsDetailPresenter) : BottomBaseDialog<CouponBottomDialog>(context) {
    private val couponsList: List<CouponBean> = coupons
    private val present: GoodsDetailPresenter = presenter
    private val adapterDialogCoupon: AdapterDialogCoupon by lazy {AdapterDialogCoupon(R.layout.adapter_dialog_coupon) }
    private lateinit var view: View
    private lateinit var headerView: View
    override fun onCreateView(): View {
//        showAnim(FlipVerticalSwingEnter())
//        dismissAnim(null)
        view = View.inflate(context, R.layout.dialog_coupon_bottom, null)

        return view
    }

    /**
     * 设置满减信息
     */
    private fun setFullReduce() {

        headerView = View.inflate(context, R.layout.header_coupon_bottom_dialog, null)
        adapterDialogCoupon.addHeaderView(headerView)

        val couponStr = StringBuilder()
        for (coupon in couponsList) {
            if (coupon.type == 3) couponStr.append("满${coupon.min_amount}减${coupon.amount}、")
        }
        if (TextUtils.isEmpty(couponStr)) {
            headerView.imageView.visibility = View.GONE
            headerView.textViewSub.visibility = View.GONE
        } else {
            headerView.imageView.visibility = View.VISIBLE
            headerView.textViewSub.visibility = View.VISIBLE
            headerView.textViewSub.text = couponStr.dropLast(1)
        }
    }

    override fun setUiBeforShow() {
        setFullReduce()
        val footerView = View(context)
        footerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DimenUtil.getDimensionPixelSize(R.dimen.dp26))
        adapterDialogCoupon.addFooterView(footerView)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        view.recyclerViewCoupon.setHasFixedSize(true)
        view.recyclerViewCoupon.layoutManager = linearLayoutManager
        view.recyclerViewCoupon.adapter = adapterDialogCoupon
        view.recyclerViewCoupon.addItemDecoration(DividerItemDecoration(context,android.R.color.transparent,view.recyclerViewCoupon,15f))
        adapterDialogCoupon.setNewData(couponsList)
        view.relativeLayoutBar.setOnClickListener { dismiss() }
    }
}