package com.thn.lexi.goods.lifehouse
import com.basemodule.tools.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.goods.explore.EditorRecommendBean
import java.io.IOException

class LifeHousePresenter(view: LifeHouseContract.View) : LifeHouseContract.Presenter {

    private var view: LifeHouseContract.View = checkNotNull(view)

    private val dataSource: LifeHouseModel by lazy { LifeHouseModel() }

    override fun loadData(cid: String, page: Int) {
        dataSource.loadData(cid, page,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val distributionGoodsBean = JsonUtil.fromJson(json, DistributionGoodsBean::class.java)
                if (distributionGoodsBean.success) {
                    view.setNewData(distributionGoodsBean.data.products)
                } else {
                    view.showError(distributionGoodsBean.status.message)
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
                val distributionGoodsBean = JsonUtil.fromJson(json, DistributionGoodsBean::class.java)
                if (distributionGoodsBean.success) {
                    val products = distributionGoodsBean.data.products
                    if (products.isEmpty() ){
                        view.loadMoreEnd()
                    }else{
                        view.loadMoreComplete()
                    }
                    view.addData(products)
                } else {
                    view.showError(distributionGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取本周最受欢迎
     */
    fun getWelcomeInWeek() {
        dataSource.getWelcomeInWeek( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setWelcomeInWeekData(editorRecommendBean.data.products)
                } else {
                    view.showError(editorRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取生活馆浏览人数
     */
    fun getLookPeople() {
        dataSource.getLookPeople( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val lookPeopleBean = JsonUtil.fromJson(json, LookPeopleBean::class.java)
                if (lookPeopleBean.success) {
                    view.setLookPeopleData(lookPeopleBean.data.users)
                } else {
                    view.showError(lookPeopleBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取小b生活馆信息
     */
    fun getLifeHouse() {
        dataSource.getLifeHouse( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val lifeHouseBean = JsonUtil.fromJson(json, LifeHouseBean::class.java)
                if (lifeHouseBean.success) {
                    view.setLifeHouseData(lifeHouseBean.data)
                } else {
                    view.showError(lifeHouseBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 编辑生活馆
     */
    fun editLifeHouse(title: String, description: String){

        dataSource.editLifeHouse(title,description,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, LifeHouseBean::class.java)
                if (netStatusBean.success) {
                    val bean = LifeHouseBean.DataBean()
                    bean.name = title
                    bean.description = description
                    view.setLifeHouseData(bean)
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