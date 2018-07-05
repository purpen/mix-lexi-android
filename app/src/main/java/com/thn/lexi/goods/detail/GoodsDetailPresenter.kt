package com.thn.lexi.goods.detail
import com.basemodule.tools.LogUtil
import com.basemodule.ui.IDataSource
import java.io.IOException

class GoodsDetailPresenter(view: GoodsDetailContract.View):GoodsDetailContract.Presenter {

    private var view:GoodsDetailContract.View = checkNotNull(view)

    private val dataSource:GoodsDetailModel by lazy {GoodsDetailModel()}

    override fun loadData(goodsId: String) {
        dataSource.loadData(goodsId,object:IDataSource.HttpRequestCallBack{
            override fun onStart() {
                super.onStart()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
            }

            override fun onFailure(e: IOException) {

            }
        })
    }
}