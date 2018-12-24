package com.lexivip.lexi.index.detail

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.Util
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.CustomLinearLayoutManager
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.CouponBean
import com.lexivip.lexi.user.login.UserProfileUtil
import com.smart.dialog.widget.base.BottomBaseDialog
import kotlinx.android.synthetic.main.dialog_coupon_bottom.view.*
import kotlinx.android.synthetic.main.header_coupon_bottom_dialog.view.*
import java.io.IOException


class OfficialCouponBottomDialog(context: Context?) : BottomBaseDialog<OfficialCouponBottomDialog>(context) {
    private lateinit var couponsList: List<CouponBean>
    private var present: GoodsDetailPresenter? = null
    private lateinit var storeId: String
    private val list: ArrayList<CouponBean> by lazy { ArrayList<CouponBean>() }
    private val adapterDialogCoupon: AdapterOfficialCoupon by lazy { AdapterOfficialCoupon(R.layout.adapter_goods_detail_official_coupon) }
    private lateinit var view: View
    private lateinit var headerView: View

    constructor(context: Context, coupons: List<CouponBean>, present: GoodsDetailPresenter, storeRid: String) : this(context) {
        couponsList = coupons
        this.present = present
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
        headerView.textViewCouponTitle.text = Util.getString(R.string.text_lx_official_coupon)
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

        //领取官方券
        adapterDialogCoupon.setOnItemClickListener { _, _, position ->
            if (UserProfileUtil.isLogin()){
                val couponBean = adapterDialogCoupon.getItem(position)
                        ?: return@setOnItemClickListener
                if (present == null) return@setOnItemClickListener
                present!!.clickGetOfficialCoupon(couponBean.code, object : IDataSource.HttpRequestCallBack {
                    override fun onSuccess(json: String) {
                        couponBean.is_grant = true
                        adapterDialogCoupon.notifyDataSetChanged()
                    }

                    override fun onFailure(e: IOException) {
                    }
                })
            }else{
                PageUtil.jump2LoginActivity()
            }
        }
    }
}