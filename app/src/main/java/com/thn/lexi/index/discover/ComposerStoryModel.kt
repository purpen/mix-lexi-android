package com.thn.lexi.index.discover

import com.basemodule.ui.IDataSource
import com.thn.lexi.R
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException

class ComposerStoryModel : IDataSource {

    fun loadData(page: Int, imageId: Int, httpRequestCallBack: IDataSource.HttpRequestCallBack) {
        val params = ClientParamsAPI.getDefaultParams()
        params["per_page"] = "10"
        params["page"] = "$page"
        val url: String
        when (imageId) {
            R.mipmap.icon_image_composer -> {//创作人
                url = URL.COMPOSER_STORY_LIST
            }
            R.mipmap.icon_image_seeding -> { //种草清单
                url = URL.SEEDING_MANIFEST_LIST
            }
            R.mipmap.icon_image_life_record -> { //生活志
                url = URL.LIFE_RECORDS_LIST
            }
            R.mipmap.icon_image_hand_make -> { //手作教学
                url = URL.HAND_MADE_LIST
            }
            else ->{
                url = URL.COMPOSER_STORY_LIST
            }
        }

        HttpRequest.sendRequest(HttpRequest.GET, url, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                httpRequestCallBack.onStart()
            }

            override fun onSuccess(json: String) {
                httpRequestCallBack.onSuccess(json)
            }

            override fun onFailure(e: IOException) {
                httpRequestCallBack.onFailure(e)
            }
        })
    }

}