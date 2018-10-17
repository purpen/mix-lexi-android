package com.lexivip.lexi.order

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import org.json.JSONObject

class SelectExpressAddressContract {
    interface View : BaseView<Presenter> {
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)

        fun goPage()

        fun setNewData(addresses: MutableList<UserAddressListBean.DataBean>) {

        }

        fun loadMoreEnd() {

        }

        fun loadMoreComplete() {

        }

        fun setFavorite(b: Boolean, position: Int) {

        }

        fun loadMoreFail() {

        }

        fun setUserIndentityInfo(data: JSONObject, selectedItem: UserAddressListBean.DataBean) {

        }

    }

    interface Presenter : BasePresenter {
        fun loadData()
        fun getUserIdentifyInfo(first_name: String, mobile: String, selectedItem: UserAddressListBean.DataBean)
    }
}