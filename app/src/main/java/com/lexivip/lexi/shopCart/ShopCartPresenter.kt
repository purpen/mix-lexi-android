package com.lexivip.lexi.shopCart

import com.basemodule.tools.ToastUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.detail.AddShopCartBean
import com.lexivip.lexi.net.NetStatusBean
import java.io.IOException

class ShopCartPresenter(view: ShopCartContract.View) : ShopCartContract.Presenter {

    private var view: ShopCartContract.View = checkNotNull(view)

    private val dataSource: ShopCartModel by lazy { ShopCartModel() }
    private var page: Int = 1

    /**
     * 加载心愿单
     */
    override fun loadData(isRefresh: Boolean) {

        if (isRefresh) page = 1

        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
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
    override fun getShopCartGoods(isRefresh: Boolean) {
        dataSource.getShopCartGoods(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val shopCartBean = JsonUtil.fromJson(json, ShopCartBean::class.java)
                if (shopCartBean.success) {
                    if (shopCartBean.data != null) view.setShopCartGoodsData(shopCartBean.data)
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
     * 获取商品所有SKU
     */
    override fun getGoodsSKUs(rid: String, callBack: IDataSource.HttpRequestCallBack) {
        dataSource.getGoodsSKUs(rid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                callBack.onStart()
            }

            override fun onSuccess(json: String) {
                callBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callBack.onFailure(e)
            }
        })
    }


    /**
     * 添加购物车
     */
    override fun addShopCart(rid: String, quantity: Int) {
        dataSource.addShopCart(rid, quantity, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val addShopCartBean = JsonUtil.fromJson(json, AddShopCartBean::class.java)
                if (addShopCartBean.success) {
                    if (addShopCartBean.data.cart != null) view.setAddShopCartSuccess(addShopCartBean.data.cart)
                } else {
                    view.showError(addShopCartBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 心愿单
     */
    override fun addWishOrder(list: ArrayList<String>) {
        dataSource.addWishOrder(list, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    ToastUtil.showSuccess(AppApplication.getContext().getString(R.string.text_already_add_shop_cart))
                    view.setAddWishOrderStatus(list)
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 从购物车移除
     */
    override fun removeProductFromShopCart(list: ArrayList<String>) {
        dataSource.removeProductFromShopCart(list, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val removeShopCartBean = JsonUtil.fromJson(json, RemoveShopCartBean::class.java)
                if (removeShopCartBean.success) {
                    if (removeShopCartBean.data != null) view.removeShopCartSuccess()
                } else {
                    view.showError(removeShopCartBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 更新SKU
     */
    override fun updateReselectSKU(newSKU: String, oldSKU: String, quantity: Int) {
        dataSource.updateReselectSKU(newSKU, oldSKU, quantity, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    view.updateShopCart()
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}