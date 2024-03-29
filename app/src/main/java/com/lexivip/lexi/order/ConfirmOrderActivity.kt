package com.lexivip.lexi.order

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.*
import com.lexivip.lexi.beans.CouponBean
import com.lexivip.lexi.eventBusMessge.MessageOrderSuccess
import com.lexivip.lexi.eventBusMessge.MessageUpdate
import com.lexivip.lexi.eventBusMessge.MessageUpdateDefaultExpress
import com.lexivip.lexi.pay.SelectPayWayActivity
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

    //官方券列表
    private var officialCoupons: List<CouponBean>? = null

    //官方券最大面额
    private var maxOfficialCouponPrice: Int = 0
    private var maxOfficialCouponCode: String = ""

    //店铺券最大面额总和
    private var sumMaxShopCouponPrice: Int = 0

    private var isOfficialCouponLoaded = false

    private var isShopCouponLoaded = false

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

        if (!TextUtils.isEmpty(createOrderBean.consigneeInfo.zipcode)) {
            headerView.textViewZip.visibility = View.VISIBLE
            headerView.textViewZip.text = createOrderBean.consigneeInfo.zipcode
        }

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
        createOrderBean.officialCouponCanSelected = false
        createOrderBean.officialCouponPrice = 0
        createOrderBean.officialCouponCode = ""

        //累加优惠券总面值先重置
        createOrderBean.shopCouponTotalPrice = 0


        for (store in stores) {
            store.couponCanSelected = true
            if (store.notUsingCoupon) continue
            createOrderBean.shopCouponTotalPrice += store.couponPrice
        }

        if (createOrderBean.shopCouponTotalPrice == 0) { //说明店铺券都未使用
            createOrderBean.officialCouponCanSelected = true
            headerView.textViewCouponPrice.text = "未使用"
        } else {
            headerView.textViewCouponPrice.text = "-¥${createOrderBean.shopCouponTotalPrice}"
        }
        //选择店铺券则不用官方券
        createOrderBean.notUsingOfficialCoupon = true
        if (officialCoupons == null || officialCoupons!!.isEmpty()) {
            setOfficialCouponText(0)
        } else {
            setOfficialCouponText(officialCoupons!!.size)
        }
        calculateUserPayTotalPrice(true)
    }

    /**
     * 选完官方优惠券，将店铺优惠券清空
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onOfficialCouponSelected(couponBean: CouponBean) {

        //将店铺优惠券总面值清零
        createOrderBean.shopCouponTotalPrice = 0
        createOrderBean.officialCouponCanSelected = true
        val stores = createOrderBean.store_items


        for (store in stores) {
            store.notUsingCoupon = true
            store.coupon_codes = ""
        }

        if (couponBean.amount == 0) { //总额为0，则为选择不使用官方券，让店铺券可选
            for (store in stores) {
                store.couponCanSelected = true
                store.notUsingCoupon = true
                store.coupon_codes = ""
            }
            headerView.textViewCouponPrice.text = "未使用"
            if (officialCoupons == null || officialCoupons!!.isEmpty()) return
            footerView.textViewOfficialCoupon.text = "${officialCoupons!!.size}张优惠券可用"
            footerView.textViewOfficialCoupon.setTextColor(Util.getColor(R.color.color_ff6666))
        } else { //让店铺券不可选
            for (store in stores) {
                store.couponCanSelected = false
                store.notUsingCoupon = true
                store.coupon_codes = ""
            }
            //设置为官方优惠券
            headerView.textViewCouponPrice.text = "-¥${createOrderBean.officialCouponPrice}"
            footerView.textViewOfficialCoupon.text = "已抵¥${createOrderBean.officialCouponPrice}"

            footerView.textViewOfficialCoupon.setTextColor(Util.getColor(R.color.color_ff6666))
        }

        //刷新店铺订单列表
        adapter.notifyDataSetChanged()
        calculateUserPayTotalPrice(false)
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
                        ToastUtil.showInfo("无可用优惠券")
                        return@setOnItemChildClickListener
                    }
                    val pavilionCouponBottomDialog = PavilionCouponBottomDialog(this, presenter, list, itemBean)
                    pavilionCouponBottomDialog.show()
                }
            }
        }


        footerView.setOnClickListener {
            //显示领取官方券对话框
            if (officialCoupons == null) return@setOnClickListener
            val officialCouponBottomDialog = OfficialCouponBottomDialog(this, createOrderBean, officialCoupons!!)
            officialCouponBottomDialog.show()
        }


        buttonSubmitOrder.setOnClickListener {

            //提交订单
            presenter.submitOrder(createOrderBean)
        }
    }

    /**
     * 订单提交成功
     */
    override fun setSubmitOrderSuccess(order_id: String) {
//        清空购物车
        EventBus.getDefault().post(MessageOrderSuccess())
        createOrderBean.order_rid = order_id
//         跳转支付界面
        val intent = Intent(this, SelectPayWayActivity::class.java)
        intent.putExtra(SelectPayWayActivity::class.java.simpleName, createOrderBean)
        startActivity(intent)
        finish()
    }

    /**
     * 设置新用户首单优惠信息
     */
    override fun setNewUserDiscountData(data: NewUserDiscountBean.DataBean) {
        newUserDiscountBean = data
        calculateUserPayTotalPrice(true)
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
        sumMaxShopCouponPrice = 0
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
            item.coupons = arrayList
            orderShopCouponMap[item.store_rid] = arrayList
            if (arrayList.isEmpty()) {
                item.couponPrice = 0
            } else {
                item.couponPrice = arrayList[0].amount
            }
            sumMaxShopCouponPrice += item.couponPrice
        }
        adapter.notifyDataSetChanged()
        setAllCouponSelectedState()
        isShopCouponLoaded = true
    }

    /**
     * 进入设置默认选中的优惠券
     */
    private fun setAllCouponSelectedState() {
        if (isShopCouponLoaded && isOfficialCouponLoaded) return
        if (sumMaxShopCouponPrice == 0 && maxOfficialCouponPrice == 0) return
//        LogUtil.e("sumMaxShopCouponPrice===$sumMaxShopCouponPrice;;;maxOfficialCouponPrice===$maxOfficialCouponPrice")

        val data = adapter.data
        if (sumMaxShopCouponPrice >= maxOfficialCouponPrice) { //使用店铺优惠券
            createOrderBean.notUsingOfficialCoupon = true
            for (item in data) { //默认使用优惠券
                item.notUsingCoupon = false
                if (item.coupons.isEmpty()) continue
                item.coupons[0].selected = true
            }

            createOrderBean.officialCouponCanSelected = false
            footerView.isEnabled = false
            footerView.textViewOfficialCoupon.setTextColor(Util.getColor(R.color.color_999))
            if (officialCoupons == null || officialCoupons!!.isEmpty()) {
                footerView.textViewOfficialCoupon.text = "无可用优惠券"
            } else {
                footerView.textViewOfficialCoupon.text = "不可使用"
            }
            createOrderBean.shopCouponTotalPrice = sumMaxShopCouponPrice
            createOrderBean.officialCouponPrice = 0
            createOrderBean.officialCouponCode = ""
            headerView.textViewCouponPrice.text = "-¥${createOrderBean.shopCouponTotalPrice}"
        } else { //使用乐喜优惠券
            createOrderBean.officialCouponCanSelected = true
            createOrderBean.notUsingOfficialCoupon = false
            footerView.isEnabled = true
            for (item in data) { //默认使用优惠券
                item.notUsingCoupon = true
                item.couponCanSelected = false
            }
            createOrderBean.shopCouponTotalPrice = 0
            createOrderBean.officialCouponPrice = maxOfficialCouponPrice
            createOrderBean.officialCouponCode = maxOfficialCouponCode
            footerView.textViewOfficialCoupon.text = "已抵¥${createOrderBean.officialCouponPrice}"
            footerView.textViewOfficialCoupon.setTextColor(Util.getColor(R.color.color_ff6666))
            headerView.textViewCouponPrice.text = "-¥${createOrderBean.officialCouponPrice}"
        }
        adapter.notifyDataSetChanged()
        calculateUserPayTotalPrice(false)
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
        createOrderBean.expressTotalPrice = 0.0
        for (store in createOrderBean.store_items) {
            val expense = data.optDouble(store.store_rid)
            store.expressExpense = expense
            //运费总计
            createOrderBean.expressTotalPrice += expense
        }

        adapter.notifyDataSetChanged()

        if (createOrderBean.expressTotalPrice == 0.0) {
            headerView.textViewDeliveryPrice.text = "包邮"
            headerView.textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_c2a67d))
        } else {
            headerView.textViewDeliveryPrice.text = "¥${createOrderBean.expressTotalPrice}"
            headerView.textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_9099a6))
        }
        calculateUserPayTotalPrice(true)
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
        createOrderBean.fullReductionTotalPrice = 0.0

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
                createOrderBean.fullReductionTotalPrice += amout
                var fullReductionText: String
                if (reduction.has("type_text")) {
                    fullReductionText = reduction.optString("type_text")
                } else {
                    fullReductionText = "¥0"
                }
                item.fullReductionText = fullReductionText
            }
        }

        headerView.textViewFullReducePrice.text = "-¥${createOrderBean.fullReductionTotalPrice}"

        calculateUserPayTotalPrice(true)
    }

    /**
     * 计算用户应支付价格和首单优惠价格
     */
    private fun calculateUserPayTotalPrice(needRequestOfficial: Boolean) {
        var userPayPrice = createOrderBean.orderTotalPrice + createOrderBean.expressTotalPrice - createOrderBean.officialCouponPrice - createOrderBean.shopCouponTotalPrice - createOrderBean.fullReductionTotalPrice

        //满足首单优惠折扣
        if (newUserDiscountBean == null) {
            createOrderBean.firstOrderDiscountPrice = 0.0
        } else {
            createOrderBean.firstOrderDiscountPrice = userPayPrice * (1 - newUserDiscountBean!!.discount_ratio)
            userPayPrice *= newUserDiscountBean!!.discount_ratio
        }

        headerView.textViewFirstOrderDiscountPrice.text = "-¥${createOrderBean.firstOrderDiscountPrice}"

        //设置用户应付总金额
        createOrderBean.userPayTotalPrice = userPayPrice

        headerView.textViewTotalPrice.text = "$userPayPrice"

        if (needRequestOfficial) {
            getOfficialCoupons()
        }
    }

    /**
     * 获取官方优惠券
     */
    private fun getOfficialCoupons() {
        //价格变化则获取获取官方可用优惠
        val sumPrice = createOrderBean.orderTotalPrice - createOrderBean.shopCouponTotalPrice - createOrderBean.fullReductionTotalPrice
        val list = ArrayList<String>()
        for (storeItem in createOrderBean.store_items) {
            for (product in storeItem.items) {
                list.add(product.rid)
            }
        }
        presenter.getOfficialCoupons(sumPrice, list)
    }

    /**
     * 设置官方优惠券信息
     */
    override fun setOfficialCouponData(coupons: List<CouponBean>) {
        //第一张为最大面额
        if (coupons.isEmpty()) {// 设置官方优惠券可用数量
            maxOfficialCouponPrice = 0
        } else {
            coupons[0].selected = true
            maxOfficialCouponPrice = coupons[0].amount
            maxOfficialCouponCode = coupons[0].code
        }
        officialCoupons = coupons
        setAllCouponSelectedState()
        setOfficialCouponText(coupons.size)
        isOfficialCouponLoaded = true
    }


    /**
     * 设置官方券显示文字
     */
    private fun setOfficialCouponText(size: Int) {
        if (size > 0) {
            if (createOrderBean.notUsingOfficialCoupon) { //优惠券领取界面选择不使用官方券
                if (createOrderBean.officialCouponCanSelected) { //只选乐喜券
                    footerView.isEnabled = true
                    footerView.textViewOfficialCoupon.text = "${size}张优惠券可用"
                    footerView.textViewOfficialCoupon.setTextColor(Util.getColor(R.color.color_ff6666))
                } else {
                    footerView.isEnabled = false
                    footerView.textViewOfficialCoupon.text = "不可使用"
                    footerView.textViewOfficialCoupon.setTextColor(Util.getColor(R.color.color_999))
                }
            } else {
                footerView.isEnabled = true
                footerView.textViewOfficialCoupon.text = "已抵¥${createOrderBean.officialCouponPrice}"
                footerView.textViewOfficialCoupon.setTextColor(Util.getColor(R.color.color_ff6666))
            }
        } else {
            footerView.isEnabled = false
            footerView.textViewOfficialCoupon.text = "无可用优惠券"
            footerView.textViewOfficialCoupon.setTextColor(Util.getColor(R.color.color_999))
        }
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

    private var canBackPress = true


    override fun setOnBackPressUnable() {
        canBackPress = false
    }

    //点击提交订单不允许返回
    override fun onBackPressed() {
        if (canBackPress) super.onBackPressed()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}

