package com.lexivip.lexi.publishShopWindow
import com.basemodule.tools.Util
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import java.io.IOException

class SelectShopWindowGoodsImagePresenter(view: SelectShopWindowGoodsImageContract.View) : SelectShopWindowGoodsImageContract.Presenter {
    private var view: SelectShopWindowGoodsImageContract.View = checkNotNull(view)
    private val dataSource: SelectShopWindowGoodsImageModel by lazy { SelectShopWindowGoodsImageModel() }
    /**
     * 加载选中的商品图
     */
    override fun loadGoodsImageById(rid: String) {
        dataSource.loadGoodsImageById(rid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val selectGoodsImageBean = JsonUtil.fromJson(json, SelectGoodsImageBean::class.java)
                if (selectGoodsImageBean.success) {
                    view.setNewData(selectGoodsImageBean.data.images)
                } else {
                    view.showError(selectGoodsImageBean.status.message)
                }

            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(Util.getString(R.string.text_net_error))
            }
        })
    }
}