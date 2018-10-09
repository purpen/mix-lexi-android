package com.thn.lexi.order
import com.thn.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import org.json.JSONObject
import java.io.IOException

class SelectExpressAddressPresenter(view: SelectExpressAddressContract.View) : SelectExpressAddressContract.Presenter {
    private var view: SelectExpressAddressContract.View = checkNotNull(view)

    private val dataSource: SelectExpressAddressModel by lazy { SelectExpressAddressModel() }

    /**
     * 加载数据
     */
    override fun loadData() {
        dataSource.loadData(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()


                val userAddressListBean = JsonUtil.fromJson(json, UserAddressListBean::class.java)
                if (userAddressListBean.success) {
                    view.setNewData(userAddressListBean.data)
                } else {
                    view.showError(userAddressListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取用户身份信息
     */
    override fun getUserIdentifyInfo(first_name: String, mobile: String, selectedItem: UserAddressListBean.DataBean) {
        dataSource.getUserIdentifyInfo(first_name,mobile,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val response = JSONObject(json)
                val isSuccess = response.getBoolean("success")
                val status = response.getJSONObject("status")
                val data = response.getJSONObject("data")
                if (isSuccess) {
                    view.setUserIndentityInfo(data,selectedItem)
                } else {
                    view.showError(status.getString("message"))
                }
//                val userAddressListBean = JsonUtil.fromJson(json, UserAddressListBean::class.java)
//                if (userAddressListBean.success) {
//                    view.setNewData(userAddressListBean.data)
//                } else {
//                    view.showError(userAddressListBean.status.message)
//                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

}