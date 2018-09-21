package com.thn.lexi

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.detail.AddShopCartBean
import com.thn.lexi.order.*
import com.thn.lexi.shopCart.*
import kotlinx.android.synthetic.main.header_shop_cart_goods.view.*
import kotlinx.android.synthetic.main.fragment_main1.*
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
    private var items: MutableList<ShopCartBean.DataBean.ItemsBean>? = null

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
        EventBus.getDefault().register(this)
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setColorSchemeColors(color6e)
        swipeRefreshLayout.isRefreshing = false

        customHeadView.head_goback.visibility = View.GONE
        customHeadView.setRightTxt(Util.getString(R.string.text_edit), color6e)
        customHeadView.setHeadCenterTxtShow(true, R.string.title_shopcart)
        adapterWish.setHeaderAndEmpty(true)
        iniShopCart()

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapterWish

        adapterWish.addHeaderView(View.inflate(activity, R.layout.header_shop_cart_wish_order, null))
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

        //编辑状态购物车
        val linearLayoutManagerEdit = LinearLayoutManager(activity)
        linearLayoutManagerEdit.orientation = LinearLayoutManager.VERTICAL
        recyclerViewEditShopCart.setHasFixedSize(true)
        recyclerViewEditShopCart.layoutManager = linearLayoutManagerEdit
        recyclerViewEditShopCart.adapter = adapterOrder
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
    override fun setAddWishOrderStatus() {
        presenter.loadData(true)
    }

    /**
     * 商品SKU移除购物车成功
     */
    override fun removeShopCartSuccess() {
        val data = adapterOrder.data

        val iterator = data.iterator()

        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.isChecked) iterator.remove()
        }

        adapterOrder.notifyDataSetChanged()
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


    override fun installListener() {

        buttonSettleAccount.setOnClickListener {
            val data = adapterOrder.data

            if (data.isEmpty()) {
                ToastUtil.showInfo("您的购物车还没有商品")
                return@setOnClickListener
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
                return@setOnClickListener
            }

            for (item in data) {
                if (item.product.stock_quantity == 0) {
                    canSubmit = false
                    break
                }
            }

            if (!canSubmit) {
                ToastUtil.showInfo("存在已售罄商品")
                return@setOnClickListener
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



        buttonDelete.setOnClickListener {
            //从购物车移除
            val data = adapterOrder.data
            val list = ArrayList<String>()
            for (item in data) {
                if (item.isChecked) {
                    list.add(item.product.rid)
                }
            }

            if (list.isEmpty()) {
                ToastUtil.showInfo(Util.getString(R.string.text_plesse_select_goods))
                return@setOnClickListener
            }

            presenter.removeProductFromShopCart(list)
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


        // 心愿单添加购物车
        adapterWish.setOnItemChildClickListener { adapter, view, position ->
            val productBean = adapter.getItem(position) as ProductBean
            val addShopCartBottomDialog = AddShopCartBottomDialog(activity!!, presenter, productBean)
            addShopCartBottomDialog.show()
        }

        customHeadView.headRightTV.setOnClickListener {
            if (swipeRefreshLayout.isShown) { //显示编辑状态
                swipeRefreshLayout.visibility = View.GONE
                recyclerViewEditShopCart.visibility = View.VISIBLE
                customHeadView.setRightTxt(Util.getString(R.string.text_complete), color6e)
                textViewTotal.visibility = View.GONE
                textViewTotalPrice.visibility = View.GONE
                buttonSettleAccount.visibility = View.GONE
                buttonDelete.visibility = View.VISIBLE
                buttonAddWish.visibility = View.VISIBLE
            } else {
                swipeRefreshLayout.visibility = View.VISIBLE
                recyclerViewEditShopCart.visibility = View.GONE
                customHeadView.setRightTxt(Util.getString(R.string.text_edit), color6e)
                buttonDelete.visibility = View.GONE
                buttonAddWish.visibility = View.GONE
                textViewTotal.visibility = View.VISIBLE
                textViewTotalPrice.visibility = View.VISIBLE
                buttonSettleAccount.visibility = View.VISIBLE
            }
            for (item in adapterOrder.data) {
                item.isEdit = !item.isEdit
            }

            adapterOrder.notifyDataSetChanged()
        }

        adapterWish.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

    }

    override fun setNewData(products: List<ProductBean>) {
        adapterWish.setNewData(products)
    }

    override fun addData(products: List<ProductBean>) {
        adapterWish.addData(products)
        adapterWish.setEnableLoadMore(true)
    }


    override fun loadData() {
//        加载心愿单
        presenter.loadData(false)

//        获取购物车商品
        presenter.getShopCartGoods()
    }

    /**
     * 设置购物车商品数据
     */
    override fun setShopCartGoodsData(data: ShopCartBean.DataBean) {
        if (data.item_count == 0) return//添加购物车为空的 占位图
        textViewNum.text = "${data.item_count}件礼品"
        items = data.items

        adapterOrder.setNewData(data.items)

        setShopCartTotalPrice()
    }

    /**
     * 设置购物车总价
     */
    private fun setShopCartTotalPrice() {
        var totalPrice = 0.0
        val items = adapterOrder.data
        for (item in items) {
            val product = item.product
            if (product.sale_price == 0.0) {
                totalPrice += product.price * item.quantity
            } else {
                totalPrice += product.sale_price * item.quantity
            }
        }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCountChange(message: MessageUpdate) {
        val data = adapterOrder.data
        var count = 0
        for (item in data) {
            count += item.quantity
        }
        textViewNum.text = "${count}件礼品"
        setShopCartTotalPrice()
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}