package com.thn.lexi.mine
import com.basemodule.tools.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.index.selection.GoodsData
import java.io.IOException

class MinePresenter(view: MineContract.View) : MineContract.Presenter {

    private var view:MineContract.View = checkNotNull(view)

    private val dataSource: MineModel by lazy { MineModel() }

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
                        bean.cover = "http://pic.58pic.com/58pic/17/00/96/20g58PIC6nk_1024.jpg"
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
                        bean.cover = "http://pic.58pic.com/58pic/17/00/96/20g58PIC6nk_1024.jpg"
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