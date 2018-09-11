package com.thn.lexi.shopCart
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class ShopCartPresenter(view: ShopCartContract.View) : ShopCartContract.Presenter {

    private var view: ShopCartContract.View = checkNotNull(view)

    private val dataSource: ShopCartModel by lazy { ShopCartModel() }
    private var page: Int = 1

    /**
     * 加载心愿单
     */
    override fun loadData() {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val wishOrderListBean = JsonUtil.fromJson(json, WishOrderListBean::class.java)
                if (wishOrderListBean.success) {
                    view.setNewData(wishOrderListBean.data.products)
                    ++page
                } else {
                    view.showError(wishOrderListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 加载更多心愿单
     */
    override fun loadMoreData() {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val wishOrderListBean = JsonUtil.fromJson(json, WishOrderListBean::class.java)
                if (wishOrderListBean.success) {
                    val products = wishOrderListBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
                        ++page
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(wishOrderListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 获取购物车商品
     */
    override fun getShopCartGoods() {
        dataSource.getShopCartGoods(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val shopCartBean = JsonUtil.fromJson(json, ShopCartBean::class.java)
                if (shopCartBean.success) {
                    if (shopCartBean.data!=null) view.setShopCartGoodsData(shopCartBean.data)
                } else {
                    view.showError(shopCartBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }



    /**
     * 添加购物车
     */
//    override fun addShopCart(rid: String, quantity: Int) {
//        dataSource.addShopCart(rid,quantity,object : IDataSource.HttpRequestCallBack {
//            override fun onSuccess(json: String) {
//                val addShopCartBean = JsonUtil.fromJson(json, AddShopCartBean::class.java)
//                if (addShopCartBean.success) {
//                    view.setAddShopCartSuccess()
//                } else {
//                    view.showError(addShopCartBean.status.message)
//                }
//            }
//
//            override fun onFailure(e: IOException) {
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
//            }
//        })
//    }

    /**
     *  获取购物车商品数量
     */
//    override fun getShopCartProductsNum() {
//
//        dataSource.getShopCartProductsNum(object : IDataSource.HttpRequestCallBack {
//            override fun onSuccess(json: String) {
//                val shopCartProductNumBean = JsonUtil.fromJson(json, ShopCartProductNumBean::class.java)
//                if (shopCartProductNumBean.success) {
//                    LogUtil.e(json)
//                    view.setShopCartNum(shopCartProductNumBean.data.item_count)
//                } else {
//                    view.showError(shopCartProductNumBean.status.message)
//                }
//            }
//
//            override fun onFailure(e: IOException) {
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
//            }
//        })
//    }
}