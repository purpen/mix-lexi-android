package com.lexivip.lexi

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.index.detail.AddShopCartBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.order.*
import com.lexivip.lexi.shopCart.*
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.header_shop_cart_goods.view.*
import kotlinx.android.synthetic.main.fragment_main1.*
import kotlinx.android.synthetic.main.header_empty_shop_cart.view.*
import kotlinx.android.synthetic.main.view_custom_headview.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 购物车
 */
class MainFragment1 : BaseFragment(), ShopCartContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val presenter: ShopCartPresenter by lazy { ShopCartPresenter(this) }

    private val color6e: Int by lazy { Util.getColor(R.color.color_6ed7af) }

    private val adapterWish: AdapterShopCartWishGoods by lazy { AdapterShopCartWishGoods(R.layout.adapter_shop_cart_wish_goods) }

    private val adapterOrder: AdapterShopCartGoods by lazy { AdapterShopCartGoods(R.layout.adapter_shop_cart_goods) }

    private val adapterEditShopCartGoods: AdapterEditShopCartGoods by lazy { AdapterEditShopCartGoods(R.layout.adapter_shop_cart_goods) }

    //判断当前fragment是否初始化
    private var isFragmentInitiate = false

    private var headerViewWishOrder: View? = null

    companion object {
        fun newInstance(): MainFragment1 {
            return MainFragment1()
        }
    }

    override val layout: Int = R.layout.fragment_main1


    override fun setPresenter(presenter: ShopCartContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {

        isFragmentInitiate = true

        EventBus.getDefault().register(this)
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setColorSchemeColors(color6e)
        swipeRefreshLayout.isRefreshing = false

        customHeadView.head_goback.visibility = View.GONE
        customHeadView.setRightTxt(Util.getString(R.string.text_edit), color6e)
        customHeadView.headRightTV.visibility = View.GONE
        customHeadView.setHeadCenterTxtShow(true, R.string.title_shopcart)
        adapterWish.setHeaderAndEmpty(true)
        iniShopCart()

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapterWish

        textViewTotalPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp11, R.dimen.dp14), null, null, null)
    }

    /**
     * 初始化购物车View
     */
    private fun iniShopCart() {
        val headerView = LayoutInflater.from(context).inflate(R.layout.header_shop_cart_goods, null)
        adapterWish.setHeaderView(headerView)
        val recyclerViewGoods = headerView.recyclerViewGoods
        val linearLayoutManager = CustomLinearLayoutManager(AppApplication.getContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.setScrollEnabled(false)
        recyclerViewGoods.layoutManager = linearLayoutManager
        recyclerViewGoods.setHasFixedSize(true)
        recyclerViewGoods.adapter = adapterOrder
        val shopCartEmpty = View.inflate(activity, R.layout.header_empty_shop_cart, null)
        shopCartEmpty.textViewLookAround.setOnClickListener {
            //跳转首页
            EventBus.getDefault().post(MessageChangePage(MainFragment0::class.java.simpleName))
        }
        adapterOrder.emptyView = shopCartEmpty
        adapterOrder.setHeaderAndEmpty(true)

        //编辑状态购物车
        val editShopCartEmpty = View.inflate(activity, R.layout.header_empty_shop_cart, null)
        val linearLayoutManagerEdit = LinearLayoutManager(activity)
        linearLayoutManagerEdit.orientation = LinearLayoutManager.VERTICAL
        recyclerViewEditShopCart.setHasFixedSize(true)
        recyclerViewEditShopCart.layoutManager = linearLayoutManagerEdit
        recyclerViewEditShopCart.adapter = adapterEditShopCartGoods
        adapterEditShopCartGoods.emptyView = editShopCartEmpty
        adapterEditShopCartGoods.setHeaderAndEmpty(true)
    }

    /**
     * 心愿单添加购物车成功
     */
    override fun setAddShopCartSuccess(cartBean: AddShopCartBean.DataBean.CartBean) {
        presenter.getShopCartGoods()
    }

    /**
     *  放入心愿单
     */
    override fun setAddWishOrderStatus(list: ArrayList<String>) {
        //从产品列表删除加入心愿单产品
        val iterator = adapterEditShopCartGoods.data.iterator()
        while (iterator.hasNext()) {
            val itemsBean = iterator.next()
            if (list.contains(itemsBean.product.product_rid)) iterator.remove()
        }

        if (adapterEditShopCartGoods.data.isEmpty()) {
            linearLayoutNum.visibility = View.GONE
        }

        adapterEditShopCartGoods.notifyDataSetChanged()
        adapterOrder.notifyDataSetChanged()
        setShopCartTotalPrice()
        presenter.loadData(true)
    }

    /**
     * 商品SKU移除购物车成功
     */
    override fun removeShopCartSuccess() { //移除已选中数据
        val data = adapterOrder.data

        val iterator = data.iterator()

        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.isChecked) iterator.remove()
        }
        adapterEditShopCartGoods.notifyDataSetChanged()
        adapterOrder.notifyDataSetChanged()

        //移除后如果购物车无数据隐藏数量
        if (data.isEmpty()) linearLayoutNum.visibility = View.GONE
    }


    /**
     * 获取店铺Id
     */
    private fun getStoreIds(): HashSet<String> {
        val data = adapterOrder.data
        val hashSet = HashSet<String>()
        for (item in data) {
            hashSet.add(item.product.store_rid)
        }
        return hashSet
    }


    /**
     * 重新选择SKU后更新购物车
     */
    override fun updateShopCart() {
        presenter.getShopCartGoods()
    }

    override fun installListener() {

        buttonSettleAccount.setOnClickListener {
            //点击结算
            clickedSettleAccount()
        }


        buttonDelete.setOnClickListener {
            //从购物车移除商品
            showDeleteDialog()
        }

        buttonAddWish.setOnClickListener {
            //加入心愿单
            val data = adapterOrder.data
            val list = ArrayList<String>()
            for (item in data) {
                if (item.isChecked) {
                    list.add(item.product.product_rid)
                }
            }

            if (list.isEmpty()) {
                ToastUtil.showInfo(Util.getString(R.string.text_plesse_select_goods))
                return@setOnClickListener
            }
            presenter.addWishOrder(list)
        }


        adapterOrder.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.textViewReselectSpec -> { //重新选择规格
                    val itemBean = adapter.getItem(position) as ShopCartBean.DataBean.ItemsBean
                    val addShopCartBottomDialog = ShopCartReselectSKUBottomDialog(activity!!, presenter, itemBean.product)
                    addShopCartBottomDialog.show()
                }
            }
        }

        //商品点击进入详情
        adapterOrder.setOnItemClickListener { adapter, view, position ->
            val itemBean = adapter.getItem(position) as ShopCartBean.DataBean.ItemsBean
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            itemBean.product.rid = itemBean.product.product_rid
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, itemBean.product)
            startActivity(intent)
        }

        // 心愿单点击进入详情
        adapterWish.setOnItemClickListener { adapter, view, position ->
            val productBean = adapter.getItem(position) as ProductBean
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, productBean)
            startActivity(intent)
        }

        // 心愿单添加购物车
        adapterWish.setOnItemChildClickListener { adapter, view, position ->
            val productBean = adapter.getItem(position) as ProductBean
            val addShopCartBottomDialog = AddShopCartBottomDialog(activity!!, presenter, productBean)
            addShopCartBottomDialog.show()
        }

        customHeadView.headRightTV.setOnClickListener {

            val data = adapterEditShopCartGoods.data

            if (!data.isEmpty()) {
                for (item in data) {
                    item.isEdit = !item.isEdit
                }
            }

            if (swipeRefreshLayout.isShown) { //编辑状态
                swipeRefreshLayout.visibility = View.GONE
                recyclerViewEditShopCart.visibility = View.VISIBLE
                customHeadView.setRightTxt(Util.getString(R.string.text_complete), color6e)
                textViewTotal.visibility = View.GONE
                textViewTotalPrice.visibility = View.GONE
                buttonSettleAccount.visibility = View.GONE
                buttonDelete.visibility = View.VISIBLE
                buttonAddWish.visibility = View.VISIBLE
            } else { //点击完成
                swipeRefreshLayout.visibility = View.VISIBLE
                recyclerViewEditShopCart.visibility = View.GONE
                customHeadView.setRightTxt(Util.getString(R.string.text_edit), color6e)
                buttonDelete.visibility = View.GONE
                buttonAddWish.visibility = View.GONE
                textViewTotal.visibility = View.VISIBLE
                textViewTotalPrice.visibility = View.VISIBLE
                buttonSettleAccount.visibility = View.VISIBLE
                if (adapterOrder.data.size==0) { //如果购物车为空 隐藏编辑/结算/礼品数量
                    customHeadView.headRightTV.visibility = View.GONE
                    relativeLayoutBottom.visibility = View.GONE
                    linearLayoutNum.visibility = View.GONE
                }
            }

        }

        adapterWish.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

    }

    /**
     * 点击结算
     */
    private fun clickedSettleAccount() {
        val data = adapterOrder.data

        if (data.isEmpty()) {
            ToastUtil.showInfo("您的购物车还没有商品")
            return
        }

        var canSubmit = true

        for (item in data) {
            if (item.product.status != 1) {
                canSubmit = false
                break
            }
        }

        if (!canSubmit) {
            ToastUtil.showInfo("请先移除已下架商品")
            return
        }

        for (item in data) {
            if (item.product.stock_quantity == 0) {
                canSubmit = false
                break
            }
        }

        if (!canSubmit) {
            ToastUtil.showInfo("存在已售罄商品")
            return
        }

        val createOrderBean = CreateOrderBean()

        val storeIds = getStoreIds()

        //店铺列表
        val storeList = ArrayList<StoreItemBean>()

        //当前店铺
        var storeItemBean: StoreItemBean

        //商品列表
        var goodsList: ArrayList<ProductBean>

        //商品
        var goods: ProductBean

        for (storeId in storeIds) {

            //创建店铺
            storeItemBean = StoreItemBean()

            storeItemBean.store_rid = storeId

            //创建商品列表
            goodsList = ArrayList()

            //添加店铺
            storeList.add(storeItemBean)

            for (item in data) {
                if (TextUtils.equals(storeId, item.product.store_rid)) {
                    goods = item.product
                    goods.quantity = item.quantity
                    goods.sku = item.product.rid
                    goodsList.add(goods)

                    if (item.product.is_distributed) { //分销1，不是分销0
                        storeItemBean.is_distribute = "1"
                    } else {
                        storeItemBean.is_distribute = "0"
                    }
                }

            }
            //添加商品列表
            storeItemBean.items = goodsList
        }

        // 添加有所店铺
        createOrderBean.store_items = storeList

        createOrderBean.orderTotalPrice = textViewTotalPrice.text.toString().toDouble()

        //结算
        val intent = Intent(activity, SelectExpressAddressActivity::class.java)
        intent.putExtra(SelectExpressAddressActivity::class.java.simpleName, createOrderBean)
        startActivity(intent)
    }

    /**
     * 显示确认删除对话框
     */
    private fun showDeleteDialog() {
        val data = adapterOrder.data
        val list = ArrayList<String>()
        for (item in data) {
            if (item.isChecked) {
                list.add(item.product.rid)
            }
        }

        if (list.isEmpty()) {
            ToastUtil.showInfo(Util.getString(R.string.text_plesse_select_goods))
            return
        }


        val color333 = Util.getColor(R.color.color_333)
        val white = Util.getColor(android.R.color.white)
        val dialog = NormalDialog(activity)
        dialog.isTitleShow(false)
                .bgColor(white)
                .cornerRadius(4f)
                .content(Util.getString(R.string.text_remove_selected_goods))
                .contentGravity(Gravity.CENTER)
                .contentTextColor(color333)
                .contentTextSize(16f)
                .dividerColor(Util.getColor(R.color.color_ccc))
                .btnText(Util.getString(R.string.text_qd), Util.getString(R.string.text_cancel))
                .btnTextSize(15f, 15f)
                .btnTextColor(color333, Util.getColor(R.color.color_6ed7af))
                .btnPressColor(white)
                .widthScale(0.85f)
                .show()
        dialog.setOnBtnClickL(OnBtnClickL {
            presenter.removeProductFromShopCart(list)
            dialog.dismiss()
        }, OnBtnClickL {
            dialog.dismiss()
        })
    }

    override fun setNewData(products: MutableList<ProductBean>) {
        if (products.isEmpty()) {
            adapterWish.removeHeaderView(headerViewWishOrder)
        } else {
            adapterWish.removeHeaderView(headerViewWishOrder)
            headerViewWishOrder = View.inflate(activity, R.layout.header_shop_cart_wish_order, null)
            adapterWish.addHeaderView(headerViewWishOrder)
        }
        adapterWish.setNewData(products)
    }


    override fun addData(products: List<ProductBean>) {
        adapterWish.addData(products)
        adapterWish.setEnableLoadMore(true)
    }


    override fun loadData() {
        if (UserProfileUtil.isLogin()) {
            //        加载心愿单
            presenter.loadData(false)

//        获取购物车商品
            presenter.getShopCartGoods()
        }
    }

    /**
     * 设置购物车商品数据
     */
    override fun setShopCartGoodsData(data: ShopCartBean.DataBean) {
        if (data.items.isEmpty()) {
            linearLayoutNum.visibility = View.GONE
            relativeLayoutBottom.visibility = View.GONE
        } else {
            linearLayoutNum.visibility = View.VISIBLE
            relativeLayoutBottom.visibility = View.VISIBLE
            customHeadView.headRightTV.visibility = View.VISIBLE
        }
        adapterOrder.setNewData(data.items)
        adapterEditShopCartGoods.setNewData(data.items)
        setShopCartTotalPrice()
    }

    /**
     * 设置购物车总价和商品数量
     */
    private fun setShopCartTotalPrice() {
        var totalPrice = 0.0
        val items = adapterOrder.data
        var count = 0
        for (item in items) {
            count += item.quantity
            val product = item.product
            if (product.sale_price == 0.0) {
                totalPrice += product.price * item.quantity
            } else {
                totalPrice += product.sale_price * item.quantity
            }
        }
        textViewNum.text = "${count}件礼品"
        textViewTotalPrice.text = "$totalPrice"
    }

    override fun loadMoreComplete() {
        adapterWish.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapterWish.loadMoreEnd()
    }

    override fun loadMoreFail() {
        adapterWish.loadMoreFail()
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

    //订单提交成功清空购物车
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onOrderSubmitSuccess(message: MessageOrderSuccess) {
        presenter.getShopCartGoods()
        adapterOrder.data.clear()
        adapterOrder.notifyDataSetChanged()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onJumpShopCart(message: String) {
        presenter.getShopCartGoods()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCountChange(message: MessageUpdate) {
        setShopCartTotalPrice()
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}