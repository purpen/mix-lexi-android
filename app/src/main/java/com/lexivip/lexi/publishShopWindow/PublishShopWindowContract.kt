package com.lexivip.lexi.publishShopWindow

import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.beans.ShopWindowBean

class PublishShopWindowContract {
    interface View:BaseView<PublishShopWindowPresenter>{
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun publishShopWindowSuccess(data: ShopWindowBean) {

        }
    }

    interface Presenter:BasePresenter{
        fun publishShopWindow(title: String, content: String, products: ArrayList<ProductBean>, tagList: ArrayList<String>)
    }
}