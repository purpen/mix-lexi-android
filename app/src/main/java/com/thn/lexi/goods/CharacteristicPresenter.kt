package com.thn.lexi.goods
import com.basemodule.tools.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import java.io.IOException

class CharacteristicPresenter(view: CharacteristicContract.View) : CharacteristicContract.Presenter {

    private var view:CharacteristicContract.View = checkNotNull(view)

    private val dataSource: CharacteristicModel by lazy { CharacteristicModel() }

    override fun loadData(cid: String, page: Int) {
        dataSource.loadData(cid, page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                view.dismissLoadingView()
                val goodsData = JsonUtil.fromJson(json, GoodsData::class.java)
                goodsData.success =true
                if (goodsData.success) {
                    val list = ArrayList<GoodsData.DataBean.ProductsBean>()
                    for (i in 1..10){
                        val bean = GoodsData.DataBean.ProductsBean()
                        bean.sale_price = 10.0
                        bean.cover = "https://kg.erp.taihuoniao.com/20180226/Fi-R-nar6b1TdBOKZAnhl-FvT_qc.jpg"
                        bean.name = "呼啸山庄"
                        list.add(bean)
                    }

                    view.setNewData(list)
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

    override fun loadMoreData(cid: String, page: Int) {
        dataSource.loadData(cid, page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                val goodsData = JsonUtil.fromJson(json, GoodsData::class.java)

                goodsData.success =true

                if (goodsData.success) {

                    val list = ArrayList<GoodsData.DataBean.ProductsBean>()
                    for (i in 1..10){
                        val bean = GoodsData.DataBean.ProductsBean()
                        bean.sale_price = 10.0
                        bean.cover = "https://kg.erp.taihuoniao.com/20180226/Fi-R-nar6b1TdBOKZAnhl-FvT_qc.jpg"
                        bean.name = "呼啸山庄"
                        list.add(bean)
                    }

                    view.addData(list)
//                    view.addData(goodsData.data.products)
                } else {
                    view.showError(goodsData.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}