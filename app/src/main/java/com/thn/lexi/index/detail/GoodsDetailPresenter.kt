package com.thn.lexi.index.detail
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.beans.BrandPavilionBean
import com.thn.lexi.index.explore.BrandPavilionListBean
import java.io.IOException

class GoodsDetailPresenter(view: GoodsDetailContract.View):GoodsDetailContract.Presenter {

    private var view:GoodsDetailContract.View = checkNotNull(view)

    private val dataSource:GoodsDetailModel by lazy {GoodsDetailModel()}

    override fun loadData(goodsId: String) {
        dataSource.loadData(goodsId,object:IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val goodsData = JsonUtil.fromJson(json, GoodsAllDetailBean::class.java)
                if (goodsData.success) {
                    if (goodsData.data!=null) view.setData(goodsData.data)
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

    fun loadGoodsInfo(goodsId: String) {
        dataSource.loadGoodsInfo(goodsId,object :IDataSource.HttpRequestCallBack{
            override fun onSuccess(json: String) {
                val goodsInfoBean = JsonUtil.fromJson(json, GoodsInfoBean::class.java)
                if (goodsInfoBean.success) {
                    if (goodsInfoBean.data!=null) view.setGoodsInfo(goodsInfoBean.data)
                } else {
                    view.showError(goodsInfoBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 请求商品所属品牌馆数据
     */
    override fun loadBrandPavilionInfo(store_rid: String) {
        dataSource.loadBrandPavilionInfo(store_rid,object :IDataSource.HttpRequestCallBack{
            override fun onSuccess(json: String) {
                val brandPavilionBean = JsonUtil.fromJson(json, BrandPavilionBean::class.java)
                if (brandPavilionBean.success) {
                    if (brandPavilionBean.data!=null) view.setBrandPavilionData(brandPavilionBean.data)
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
}