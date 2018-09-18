package com.thn.lexi.order
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.index.detail.FavoriteGoodsUsersBean
import java.io.IOException

class SelectExpressPresenter(view: SelectExpressContract.View) : SelectExpressContract.Presenter {
    private var view: SelectExpressContract.View = checkNotNull(view)

    private val dataSource: SelectExpressModel by lazy { SelectExpressModel() }

    /**
     * 加载数据
     */
    override fun loadData(expressModelId:String) {

        dataSource.loadData(expressModelId,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
               view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()


                val favoriteGoodsUsersBean = JsonUtil.fromJson(json, FavoriteGoodsUsersBean::class.java)
                if (favoriteGoodsUsersBean.success) {
                    view.setNewData(favoriteGoodsUsersBean.data.product_like_users)
                } else {
                    view.showError(favoriteGoodsUsersBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}