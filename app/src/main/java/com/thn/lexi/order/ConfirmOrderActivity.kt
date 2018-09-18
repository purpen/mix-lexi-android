package com.thn.lexi.order

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.thn.lexi.AppApplication
import com.thn.lexi.DividerItemDecoration
import com.thn.lexi.R
import com.thn.lexi.beans.CouponBean
import com.thn.lexi.beans.ProductBean
import kotlinx.android.synthetic.main.acticity_submit_order.*
import kotlinx.android.synthetic.main.header_submit_order.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ConfirmOrderActivity : BaseActivity(), ConfirmOrderContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: ConfirmOrderPresenter by lazy { ConfirmOrderPresenter(this) }

    private val adapter: AdapterOrderByPavilion by lazy { AdapterOrderByPavilion(R.layout.adapter_confirm_order) }

    override val layout: Int = R.layout.acticity_submit_order

    private lateinit var headerView: View

    private lateinit var footerView: View

    //订单信息
    private lateinit var createOrderBean: CreateOrderBean

    /**
     * 所有订单优惠券
     */
    private val orderCouponMap:HashMap<String,ArrayList<CouponBean>> by lazy { HashMap<String,ArrayList<CouponBean>>() }

    //首单优惠
    private var firstOrderDiscount:Double = 0.0
    //邮费
    private var expressTotalPrice:Double = 0.0

    //满减总额
    private var fullReductionTotalPrice:Double = 0.0

    //优惠券总额(店铺和乐喜券)
    private var couponTotalPrice:Double = 0.0

    override fun getIntentData() {
        if (intent.hasExtra(ConfirmOrderActivity::class.java.simpleName)) {
            createOrderBean = intent.getParcelableExtra(ConfirmOrderActivity::class.java.simpleName)
        }
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_submit_order)
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        headerView = View.inflate(this, R.layout.header_submit_order, null)
        adapter.addHeaderView(headerView)

        footerView = View.inflate(this, R.layout.footer_comfirm_order, null)
        adapter.addFooterView(footerView)
        adapter.setHeaderFooterEmpty(true, true)

        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 10f))

        setOrderData()
    }


    /**
     * 设置订单列表数据
     */
    private fun setOrderData() {
        headerView.textViewName.text = createOrderBean.consigneeInfo.full_name
        headerView.textViewPhone.text = createOrderBean.consigneeInfo.mobile
        headerView.textViewAddress.text = createOrderBean.consigneeInfo.full_address
        headerView.textViewZip.text = createOrderBean.consigneeInfo.zipcode

        headerView.textViewSubtotalPrice.text = "${createOrderBean.orderTotalPrice}"

        adapter.setNewData(createOrderBean.store_items)
    }

    override fun setPresenter(presenter: ConfirmOrderContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {
        adapter.setOnItemChildClickListener { _, view, position ->
            val id = view.id
            when(id){
                R.id.textViewPavilionCoupon ->{
                    val itemBean = adapter.getItem(position) as StoreItemBean
                    val list = orderCouponMap[itemBean.store_rid]
                    if (list==null || list.isEmpty()) {
                        ToastUtil.showInfo("当前品牌馆没有可用优惠券")
                        return@setOnItemChildClickListener
                    }
                    val pavilionCouponBottomDialog = PavilionCouponBottomDialog(this, presenter,list)
                    pavilionCouponBottomDialog.show()
                }
            }
        }

        adapter.setOnItemClickListener { _, _, position ->
            //            val data = adapter.data
//            val item = adapter.getItem(position) as UserAddressListBean.DataBean
//            item.is_default = true
//
//            adapter.notifyDataSetChanged()
        }

        buttonSubmitOrder.setOnClickListener {
            //提交订单
            presenter.submitOrder(createOrderBean)
//            val intent = Intent(this,ConfirmOrderActivity::class.java)
//            startActivity(intent)
        }
    }

    /**
     * 订单提交成功
     */
    override fun setSubmitOrderSuccess() {
        // 跳转支付界面
        ToastUtil.showInfo("订单提交成功，跳转支付界面")
    }

    /**
     * 设置新用户首单优惠信息
     */
    override fun setNewUserDiscountData(data: NewUserDiscountBean.DataBean) {
        if (data.is_new_user){
            val newUserPrice = createOrderBean.orderTotalPrice *(1- data.discount_ratio)
            headerView.textViewFirstOrderDiscountPrice.text = "￥ $newUserPrice"
        }else{
            headerView.textViewFirstOrderDiscountPrice.text = "无"
        }
    }

    override fun setNewData(addresses: MutableList<UserAddressListBean.DataBean>) {
//        swipeRefreshLayout.isRefreshing = false
//        adapter.setNewData(addresses)
    }


    override fun requestNet() {
//        presenter.loadData()
        //获取新用户首单信息
        presenter.getNewUserFirstOrderDiscounts()

        getDefaultExpressCompany()

        getPerOrderFullReduction()

        getPavilionCouponByOrder()
    }



    /**
     * 设置每个订单店铺优惠券
     */
    override fun setPavilionCouponByOrderData(data: JSONObject) {
        var arrayList:ArrayList<CouponBean>
        var jsonArrayCoupon:JSONArray
        for (item in createOrderBean.store_items){
            arrayList = ArrayList()
            val any = data.get(item.store_rid)
            if (any is JSONArray) {
                jsonArrayCoupon = data.getJSONArray(item.store_rid)
                val length = jsonArrayCoupon.length()-1
                for (i in 0..length){
                    val couponBean = CouponBean()
                    val couponObj = jsonArrayCoupon[i] as JSONObject
                    couponBean.amount = couponObj.optJSONObject("coupon").optInt("amount")
                    couponBean.min_amount = couponObj.optJSONObject("coupon").optInt("min_amount")
                    couponBean.start_date = couponObj.optJSONObject("coupon").optLong("start_date")
                    couponBean.end_date = couponObj.optLong("end_at")
                    arrayList.add(couponBean)
                }
            }
            orderCouponMap[item.store_rid] = arrayList
        }

    }


    /**
     * 设置商品默认快递公司
     */
    override fun setDefaultExpressCompany(data: JSONObject) {
        var arrayList:ArrayList<ExpressInfoBean>
        val store_items = createOrderBean.store_items
        for (item in store_items) {
            val any = data.get(item.store_rid)
            if (any is JSONObject) {
                val storeJSONObject = data.optJSONObject(item.store_rid)
                var skuJSONObject: JSONObject
                var expressJsonArray: JSONArray
                for (product in item.items) {
                    arrayList = ArrayList()
                    skuJSONObject = storeJSONObject.optJSONObject(product.rid)
                    expressJsonArray = skuJSONObject.optJSONArray("express")
                    val length = expressJsonArray.length() - 1
                    for (i in 0..length) {
                        val expressInfoBean = ExpressInfoBean()
                        val expressObj = expressJsonArray[i] as JSONObject
                        expressInfoBean.express_code = expressObj.optString("express_code")
                        expressInfoBean.express_id = expressObj.optString("express_id")
                        expressInfoBean.express_name = expressObj.optString("express_name")
                        expressInfoBean.max_days = expressObj.optInt("max_days")
                        expressInfoBean.min_days = expressObj.optInt("min_days")
                        expressInfoBean.is_default = expressObj.optBoolean("is_default")
                        arrayList.add(expressInfoBean)
                    }

                    product.express = arrayList
                }
            }
        }

        adapter.notifyDataSetChanged()
    }

    /**
     * 获取默认快递公司
     */
    private fun getDefaultExpressCompany() {
        val storeList = ArrayList<FullReductionRequestBean>()
        var requestBean:FullReductionRequestBean
        for (item in createOrderBean.store_items){
            requestBean = FullReductionRequestBean()
            requestBean.rid = item.store_rid
            requestBean.sku_items = item.items
            storeList.add(requestBean)
        }

        presenter.getDefaultExpressCompany(storeList)
    }

    /**
     * 获取订单店铺优惠券
     */

    private fun getPavilionCouponByOrder() {
        val storeList = ArrayList<FullReductionRequestBean>()
        var requestBean:FullReductionRequestBean
        for (item in createOrderBean.store_items){
            requestBean = FullReductionRequestBean()
            requestBean.rid = item.store_rid
            requestBean.sku_items = item.items
            storeList.add(requestBean)
        }

        presenter.getPavilionCouponByOrder(storeList)
    }


    /**
     * 获取每个订单(每店)满减信息
     */
    private fun getPerOrderFullReduction() {
        val storeList = ArrayList<FullReductionRequestBean>()
        var requestBean:FullReductionRequestBean
        for (item in createOrderBean.store_items){
            requestBean = FullReductionRequestBean()
            requestBean.rid = item.store_rid
            requestBean.sku_items = item.items
            storeList.add(requestBean)
        }

        presenter.getPerOrderFullReduction(storeList)
    }

    /**
     * 设置所有订单满减总和数据
     */
    override fun setPerOrderFullReductionData(data: JSONObject) {

        var sumFullReduction=0.0

        for (item in createOrderBean.store_items){
            val any = data.get(item.store_rid)
            if (any is JSONObject){
                val reduction = data.optJSONObject(item.store_rid)
                var amout:Double
                if (reduction.has("amount")){
                    amout = reduction.optDouble("amount")
                }else{
                    amout = 0.0
                }

                item.fullReductionAmount = amout
                var fullReductionText:String
                if (reduction.has("type_text")){
                    fullReductionText = reduction.optString("type_text")
                }else{
                    fullReductionText = "无"
                }
                item.fullReductionText = fullReductionText
                sumFullReduction+= amout
            }
        }


        headerView.textViewFullReducePrice.text = "￥${sumFullReduction}"
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun loadMoreFail() {
        adapter.loadMoreFail()
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

}

