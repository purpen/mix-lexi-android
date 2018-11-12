package com.lexivip.lexi.index.detail

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.basemodule.tools.DimenUtil
import com.basemodule.ui.IDataSource
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.lexivip.lexi.CustomLinearLayoutManager
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean
import com.lexivip.lexi.brandHouse.BrandHousePresenter
import kotlinx.android.synthetic.main.dialog_coupon_bottom.view.*
import kotlinx.android.synthetic.main.header_coupon_bottom_dialog.view.*
import java.io.IOException


class CouponBottomDialog(context: Context?) : BottomBaseDialog<CouponBottomDialog>(context) {
    private lateinit var couponsList: List<CouponBean>
    private var present: GoodsDetailPresenter? = null
    private var presenter: BrandHousePresenter? = null
    private lateinit var storeId: String
    private val list: ArrayList<CouponBean> by lazy { ArrayList<CouponBean>() }
    private val adapterDialogCoupon: AdapterDialogCoupon by lazy { AdapterDialogCoupon(R.layout.adapter_dialog_coupon) }
    private lateinit var view: View
    private lateinit var headerView: View

    constructor(context: Context, coupons: List<CouponBean>, present: GoodsDetailPresenter, storeRid: String) : this(context) {
        couponsList = coupons
        this.present = present
        storeId = storeRid
    }

    constructor(context: Context, coupons: List<CouponBean>, presenter: BrandHousePresenter, storeRid: String) : this(context) {
        couponsList = coupons
        this.presenter = presenter
        storeId = storeRid
    }

    override fun onCreateView(): View {
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
            if (coupon.type == 3) {
                couponStr.append("满${coupon.min_amount}减${coupon.amount}、")
            } else {
                list.add(coupon)
            }
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

        val linearLayoutManager = CustomLinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        if (list.isEmpty()) {
            view.relativeLayoutBar.visibility = View.GONE
            view.recyclerViewCoupon.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp125))
            adapterDialogCoupon.emptyView = View(context)
            adapterDialogCoupon.setHeaderFooterEmpty(true, true)
            headerView.textViewCouponTitle.visibility = View.GONE
            linearLayoutManager.setScrollEnabled(false)
        } else {
            val footerView = View(context)
            footerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp26))
            adapterDialogCoupon.addFooterView(footerView)

        }


        view.recyclerViewCoupon.setHasFixedSize(true)
        view.recyclerViewCoupon.layoutManager = linearLayoutManager
        view.recyclerViewCoupon.adapter = adapterDialogCoupon
        view.recyclerViewCoupon.addItemDecoration(DividerItemDecoration(context, android.R.color.transparent, view.recyclerViewCoupon, 15f))



        adapterDialogCoupon.setNewData(list)

        view.relativeLayoutBar.setOnClickListener { dismiss() }

        adapterDialogCoupon.setOnItemClickListener { adapter, _, position ->
            val couponBean = adapter.getItem(position) as CouponBean
            if (0==couponBean.status) {
                if (present != null) {
                    present!!.clickGetCoupon(storeId, couponBean.code, object : IDataSource.HttpRequestCallBack {
                        override fun onSuccess(json: String) {
                            couponBean.status = 1
                            adapter.notifyDataSetChanged()
                        }

                        override fun onFailure(e: IOException) {
                        }
                    })
                } else if (presenter != null) {
                    presenter!!.clickGetCoupon(storeId, couponBean.code, object : IDataSource.HttpRequestCallBack {
                        override fun onFailure(e: IOException) {

                        }

                        override fun onSuccess(json: String) {
                            couponBean.status = 1
                            adapter.notifyDataSetChanged()
                        }
                    })
                }
            }
        }
    }
}