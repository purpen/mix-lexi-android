package com.thn.lexi
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.thn.lexi.beans.ProductBean
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

        iniShopCart()

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapterWish

        adapterWish.addHeaderView(View.inflate(activity,R.layout.header_shop_cart_wish_order,null))
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
    }


    override fun installListener() {

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
        presenter.loadData()

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
        if (items == null) return
        var count: Int = 0
        for (item in items!!) {
            count += item.quantity
        }
        textViewNum.text = "${count}件礼品"
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}