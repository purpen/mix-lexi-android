package com.basemodule.ui

import android.graphics.Bitmap
import org.json.JSONArray
import java.io.IOException

interface IDataSource {
    interface HttpRequestCallBack {

        fun onStart(){}

        fun onSuccess(json: String)

        fun onSuccess(json: Bitmap){}

        fun onFailure(e: IOException)
    }

    interface UpLoadCallBack {
        fun onComplete(ids: JSONArray)
    }
}