package com.lexivip.lexi.publishShopWindow
import android.support.annotation.NonNull
import com.basemodule.ui.BasePresenter
import com.basemodule.ui.BaseView

class SelectShopWindowGoodsImageContract {
    interface View:BaseView<Presenter>{
        fun showLoadingView()
        fun dismissLoadingView()
        fun showError(@NonNull string: String)
        fun setNewData(images: List<SelectGoodsImageBean.DataBean.ImagesBean>) {
        }
    }

    interface Presenter:BasePresenter{
        fun loadGoodsImageById(rid: String)
    }
}