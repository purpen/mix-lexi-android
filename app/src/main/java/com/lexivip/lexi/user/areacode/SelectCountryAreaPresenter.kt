package com.lexivip.lexi.user.areacode

import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import java.io.IOException

class SelectCountryAreaPresenter(view:SelectCountryAreaContract.View) :SelectCountryAreaContract.Presenter {
    companion object {
        //开通的地区
        const val status: String = "1"
    }

    private var view: SelectCountryAreaContract.View = checkNotNull(view)

    private val dataSource: SelectCountryAreaModel by lazy { SelectCountryAreaModel() }

    fun loadData(page: Int) {
        dataSource.loadData(page, status,object :IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val countryAreaCodeBean = JsonUtil.fromJson(json, CountryAreaCodeBean::class.java)
                if (countryAreaCodeBean.success){
                    view.setNewData(countryAreaCodeBean.data.area_codes)
                }else{
                    view.showError(countryAreaCodeBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    fun loadMoreData(page: Int) {
        dataSource.loadData(page, status,object :IDataSource.HttpRequestCallBack{
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val countryAreaCodeBean = JsonUtil.fromJson(json, CountryAreaCodeBean::class.java)
                if (countryAreaCodeBean.success){
                    val area_codes = countryAreaCodeBean.data.area_codes
                    if (area_codes.isEmpty() ){
                        view.loadMoreEnd()
                    }else{
                        view.loadMoreComplete()
                    }
                    view.addData(area_codes)
                }else{
                    view.showError(countryAreaCodeBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}