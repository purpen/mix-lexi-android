package com.basemodule.ui

import android.graphics.Bitmap
import java.io.IOException

interface IDataSource {
    interface HttpRequestCallBack {

        fun onStart(){}

        fun onSuccess(json: String)

        fun onSuccess(json: Bitmap){}

        fun onFailure(e: IOException)
    }

    fun loadData(cid: String, page: Int, callBack: IDataSource.HttpRequestCallBack)
}