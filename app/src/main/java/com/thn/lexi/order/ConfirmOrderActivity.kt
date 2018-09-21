package com.thn.lexi.order

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.thn.lexi.*
import com.thn.lexi.beans.CouponBean
import kotlinx.android.synthetic.main.acticity_submit_order.*
import kotlinx.android.synthetic.main.footer_comfirm_order.view.*
import kotlinx.android.synthetic.main.header_submit_order.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ConfirmOrderActivity : BaseActivity(), ConfirmOrderContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: ConfirmOrderPresenter by lazy { ConfirmOrderPresenter(this) }

    private val adapter: AdapterOrderByPavilion by lazy { AdapterOrderByPavilion(R.layout.adapter_confirm_order, createOrderBean.address_rid) }

    override val layout: Int = R.layout.acticity_submit_order

    private lateinit var headerView: View

    private lateinit var footerView: View

    //用户首单优惠信息
    private var newUserDiscountBean: NewUserDiscountBean.DataBean? = null

    //订单信息
    private lateinit var createOrderBean: CreateOrderBean

    /**
     * 所有订单店铺优惠券
     */
    private val orderShopCouponMap: HashMap<String, ArrayList<CouponBean>> by lazy { HashMap<String, ArrayList<CouponBean>>() }

    //首单优惠
    private var firstOrderDiscountPrice: Double = 0.0
    //总运费
    private var expressTotalPrice: Double = 0.0

    //满减总额
    private var fullReductionTotalPrice: Double = 0.0

    //优惠券总额(店券)
    private var shopCouponTotalPrice: Int = 0

    override fun getIntentData() {
        if (intent.hasExtra(ConfirmOrderActivity::class.java.simpleName)) {
            createOrderBean = intent.getParcelableExtra(ConfirmOrderActivity::class.java.simpleName)
        }
    }

    override fun initView() {
        EventBus.getDefault().register(this)

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


    /**
     * 选完店铺优惠券，更新支付价格
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCouponSelected(messageUpdate: MessageUpdate) {
        adapter.notifyDataSetChanged()

        val stores = createOrderBean.store_items

        //清零官方优惠券
        createOrderBean.officialCouponPrice = 0
        createOrderBean.officialCouponCode = ""

        //累加优惠券总面值先重置
        shopCouponTotalPrice = 0


        for (store in stores) {
            shopCouponTotalPrice += store.couponPrice
        }

        if (shopCouponTotalPrice == 0) {
            headerView.textViewCouponPrice.text = "无"
        } else {
            headerView.textViewCouponPrice.text = "-￥$shopCouponTotalPrice"
        }

        footerView.textViewOfficialCoupon.text = "已抵扣￥${createOrderBean.officialCouponPrice}元"

        calculateUserPayTotalPrice()
    }

    /**
     * 选完官方优惠券，将店铺优惠券清空
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onOfficialCouponSelected(couponBean:CouponBean) {

        //将店铺优惠券总面值清零
        shopCouponTotalPrice = 0

        val stores = createOrderBean.store_items

        //将店铺优惠券清零
        for (store in stores) {
            store.couponPrice = 0
            store.coupon_codes = ""
        }

        adapter.notifyDataSetChanged()

        //设置为官方优惠券
        headerView.textViewCouponPrice.text = "-￥${createOrderBean.officialCouponPrice}"

        footerView.textViewOfficialCoupon.text = "已抵扣￥${createOrderBean.officialCouponPrice}元"

        calculateUserPayTotalPrice()
    }

    /**
     * 更换快递
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onChangeExpress(message: MessageUpdateDefaultExpress) {
        for (store in createOrderBean.store_items) {
            if (TextUtils.equals(message.store_rid, store.store_rid)) {
                for (product in store.items) {

                    if (TextUtils.equals(message.fid, product.fid)) {
                        product.express_id = message.express_id
                    }

                    if (TextUtils.equals(product.product_rid, message.product_rid)) {
                        for (express in product.express) {
                            express.is_default = TextUtils.equals(express.express_id, message.express_id)
                        }
                    }

                }
                break
            }
        }

        //换了快递重新计算运费
        calculateExpressExpenseForEachOrder()
        adapter.notifyDataSetChanged()
    }

    override fun installListener() {
        adapter.setOnItemChildClickListener { _, view, position ->
            val id = view.id
            when (id) {
                R.id.textViewPavilionCoupon -> {
                    val itemBean = adapter.getItem(position) as StoreItemBean
                    val list = orderShopCouponMap[itemBean.store_rid]
                    if (list == null || list.isEmpty()) {
                        ToastUtil.showInfo("当前品牌馆没有可用优惠券")
                        return@setOnItemChildClickListener
                    }
                    val pavilionCouponBottomDialog = PavilionCouponBottomDialog(this, presenter, list, itemBean)
                    pavilionCouponBottomDialog.show()
                }
            }
        }

        footerView.setOnClickListener { //领官方券总价计算
            //获取满足订单条件官方优惠券
            val sumPrice = createOrderBean.orderTotalPrice - shopCouponTotalPrice - fullReductionTotalPrice
            val officialCouponBottomDialog = OfficialCouponBottomDialog(this, presenter,sumPrice,createOrderBean)
            officialCouponBottomDialog.show()
        }

//        adapter.setOnItemClickListener { _, _, position ->
            //            val data = adapter.data
//            val item = adapter.getItem(position) as UserAddressListBean.DataBean
//            item.is_default = true
//
//            adapter.notifyDataSetChanged()
//        }

        buttonSubmitOrder.setOnClickListener {
            //TODO 提交订单
            presenter.submitOrder(createOrderBean)
        }
    }

    /**
     * 订单提交成功
     */
    override fun setSubmitOrderSuccess() {
//        清空购物车
        ToastUtil.showInfo("订单提交成功，等待支付....")
        EventBus.getDefault().post(MessageUpdate())
        finish()
        // 跳转支付界面
        // val intent = Intent(this,ConfirmOrderActivity::class.java)
//            startActivity(intent)
        LogUtil.e("订单提交成功，跳转支付界面")
    }

    /**
     * 设置新用户首单优惠信息
     */
    override fun setNewUserDiscountData(data: NewUserDiscountBean.DataBean) {
        newUserDiscountBean = data
        calculateUserPayTotalPrice()
    }



    override fun requestNet() {

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
        var arrayList: ArrayList<CouponBean>
        var jsonArrayCoupon: JSONArray
        for (item in createOrderBean.store_items) {
            arrayList = ArrayList()
            val any = data.get(item.store_rid)
            if (any is JSONArray) {
                jsonArrayCoupon = data.getJSONArray(item.store_rid)
                val length = jsonArrayCoupon.length() - 1
                for (i in 0..length) {
                    val couponBean = CouponBean()
                    val couponInfo = jsonArrayCoupon[i] as JSONObject
                    val coupon = couponInfo.optJSONObject("coupon")
                    couponBean.amount = coupon.optInt("amount")
                    couponBean.min_amount = coupon.optInt("min_amount")
                    couponBean.start_date = coupon.optLong("start_date")
                    couponBean.code = coupon.optString("code")
                    couponBean.end_date = couponInfo.optLong("end_at")
                    arrayList.add(couponBean)
                }
            }
            orderShopCouponMap[item.store_rid] = arrayList
        }
    }


    /**
     * 设置商品默认快递公司
     */
    override fun setDefaultExpressCompany(data: JSONObject) {
        var arrayList: ArrayList<ExpressInfoBean>
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
                        if (expressInfoBean.is_default) {
                            product.express_id = expressInfoBean.express_id
                        }
                        arrayList.add(expressInfoBean)
                    }

                    product.express = arrayList
                }
            }
        }

        adapter.notifyDataSetChanged()

        //设置完默认快递公司，计算邮费
        calculateExpressExpenseForEachOrder()
    }


    /**
     * 设置每个店铺运费并设置总运费
     */
    override fun setCalculateExpressExpenseForEachOrder(data: JSONObject) {
        //计算前先将邮费清零
        expressTotalPrice = 0.0
        for (store in createOrderBean.store_items) {
            val expense = data.optDouble(store.store_rid)
            store.expressExpense = expense
            //运费总计
            expressTotalPrice += expense
        }

        adapter.notifyDataSetChanged()

        if (expressTotalPrice == 0.0) {
            headerView.textViewDeliveryPrice.text = "包邮"
            headerView.textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_c2a67d))
        } else {
            headerView.textViewDeliveryPrice.text = "￥$expressTotalPrice"
            headerView.textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_9099a6))
        }

        calculateUserPayTotalPrice()
    }


    /**
     * 获取订单运费列表
     */
    private fun calculateExpressExpenseForEachOrder() {
        var requestBean = CalculateExpressExpenseRequestBean()
        //用户收货地址相同
        requestBean.address_rid = createOrderBean.address_rid

        val storeList = ArrayList<StoreItemBean>()

        for (store in createOrderBean.store_items) {
            val storeItemBean = StoreItemBean()
            storeItemBean.rid = store.store_rid
            storeItemBean.sku_items = store.items
            storeList.add(storeItemBean)
        }
        requestBean.items = storeList
        presenter.calculateExpressExpenseForEachOrder(requestBean)
    }

    /**
     * 获取默认快递公司
     */
    private fun getDefaultExpressCompany() {
        val storeList = ArrayList<FullReductionRequestBean>()
        var requestBean: FullReductionRequestBean
        for (item in createOrderBean.store_items) {
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
        var requestBean: FullReductionRequestBean
        for (item in createOrderBean.store_items) {
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
        var requestBean: FullReductionRequestBean
        for (item in createOrderBean.store_items) {
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
        //满减清零
        fullReductionTotalPrice = 0.0

        for (item in createOrderBean.store_items) {
            val any = data.get(item.store_rid)
            if (any is JSONObject) {
                val reduction = data.optJSONObject(item.store_rid)
                var amout: Double
                if (reduction.has("amount")) {
                    amout = reduction.optDouble("amount")
                } else {
                    amout = 0.0
                }

                item.fullReductionAmount = amout

                //满减总计
                fullReductionTotalPrice += amout
                var fullReductionText: String
                if (reduction.has("type_text")) {
                    fullReductionText = reduction.optString("type_text")
                } else {
                    fullReductionText = "￥0"
                }
                item.fullReductionText = fullReductionText
            }
        }

        headerView.textViewFullReducePrice.text = "-￥$fullReductionTotalPrice"
        calculateUserPayTotalPrice()
    }

    /**
     * 计算用户应支付价格和首单优惠价格
     */
    private fun calculateUserPayTotalPrice() {
        var userPayPrice = createOrderBean.orderTotalPrice + expressTotalPrice - createOrderBean.officialCouponPrice - shopCouponTotalPrice - fullReductionTotalPrice


        //满足首单优惠折扣
        if (newUserDiscountBean == null) {
            firstOrderDiscountPrice = 0.0
        } else {
            firstOrderDiscountPrice = userPayPrice * (1 - newUserDiscountBean!!.discount_ratio)
            userPayPrice *= newUserDiscountBean!!.discount_ratio
        }

        headerView.textViewFirstOrderDiscountPrice.text = "-￥$firstOrderDiscountPrice"

        headerView.textViewTotalPrice.text = "￥$userPayPrice"
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

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}

