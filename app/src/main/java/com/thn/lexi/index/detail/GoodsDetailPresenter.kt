package com.thn.lexi.index.detail

import com.basemodule.tools.JsonUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.beans.BrandPavilionBean
import com.thn.lexi.net.NetStatusBean
import java.io.IOException

class GoodsDetailPresenter(view: GoodsDetailContract.View) : GoodsDetailContract.Presenter {

    private var view: GoodsDetailContract.View = checkNotNull(view)

    private val dataSource: GoodsDetailModel by lazy { GoodsDetailModel() }

    override fun loadData(goodsId: String) {
        dataSource.loadData(goodsId, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val goodsData = JsonUtil.fromJson(json, GoodsAllDetailBean::class.java)
                if (goodsData.success) {
                    if (goodsData.data != null) view.setData(goodsData.data)
                } else {
                    view.showError(goodsData.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

//    fun loadGoodsInfo(goodsId: String) {
//        dataSource.loadGoodsInfo(goodsId, object : IDataSource.HttpRequestCallBack {
//            override fun onSuccess(json: String) {
//                val goodsInfoBean = JsonUtil.fromJson(json, GoodsInfoBean::class.java)
//                if (goodsInfoBean.success) {
//                    if (goodsInfoBean.data != null) view.setGoodsInfo(goodsInfoBean.data)
//                } else {
//                    view.showError(goodsInfoBean.status.message)
//                }
//            }
//
//            override fun onFailure(e: IOException) {
//                view.dismissLoadingView()
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
//            }
//        })
//    }

    /**
     * 请求商品所属品牌馆数据
     */
    override fun loadBrandPavilionInfo(store_rid: String) {
        dataSource.loadBrandPavilionInfo(store_rid, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val brandPavilionBean = JsonUtil.fromJson(json, BrandPavilionBean::class.java)
                if (brandPavilionBean.success) {
                    if (brandPavilionBean.data != null) view.setBrandPavilionData(brandPavilionBean.data)
                } else {
                    view.showError(brandPavilionBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 获取交货时间
     */
    override fun getExpressTime(rid: String, store_rid: String, goodsId: String) {
        dataSource.getExpressTime(rid, store_rid, goodsId, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val expressInfoBean = JsonUtil.fromJson(json, ExpressInfoBean::class.java)
                if (expressInfoBean.success) {
                    if (expressInfoBean.data != null) view.setExpressData(expressInfoBean)
                } else {
                    view.showError(expressInfoBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取相似商品
     */
    override fun getSimilarGoods(goodsId: String) {

//        dataSource.getSimilarGoods(goodsId, object : IDataSource.HttpRequestCallBack {
//            override fun onSuccess(json: String) {
//                val brandPavilionBean = JsonUtil.fromJson(json, BrandPavilionBean::class.java)
//                if (brandPavilionBean.success) {
//                    if (brandPavilionBean.data != null) view.setSimilarGoodsData(brandPavilionBean.data)
//                } else {
//                    view.showError(brandPavilionBean.status.message)
//                }
//            }
//
//            override fun onFailure(e: IOException) {
//                view.dismissLoadingView()
//                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
//            }
//        })
    }


    /**
     * 获取商品所在店铺优惠券列表
     */
    override fun getCouponsByStoreId(store_rid: String) {
        dataSource.getCouponsByStoreId(store_rid, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val shopCouponListBean = JsonUtil.fromJson(json, ShopCouponListBean::class.java)
                if (shopCouponListBean.success) {
                    if (shopCouponListBean.data != null) view.setCouponData(shopCouponListBean.data.coupons)
                } else {
                    view.showError(shopCouponListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 点击领取优惠券
     */
    fun clickGetCoupon(storeId: String, code: String, callBack: IDataSource.HttpRequestCallBack) {
        dataSource.clickGetCoupon(storeId, code, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val getCouponBean = JsonUtil.fromJson(json, GetCouponBean::class.java)
                if (getCouponBean.success) {
                    callBack.onSuccess(json)
                    ToastUtil.showInfo(R.string.text_get_coupon_success)
                } else {
                    view.showError(getCouponBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                callBack.onFailure(e)
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取商品所有SKU
     */
    override fun getGoodsSKUs(id: String, callBack: IDataSource.HttpRequestCallBack) {
        dataSource.getGoodsSKUs(id, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                callBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                callBack.onFailure(e)
            }
        })
    }

    /**
     * 添加心愿单
     */
    override fun addWishOrder(goodsId: String, isAddWish: Boolean) {
        dataSource.addWishOrder(goodsId, isAddWish, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    view.setAddWishOrderStatus(isAddWish)
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