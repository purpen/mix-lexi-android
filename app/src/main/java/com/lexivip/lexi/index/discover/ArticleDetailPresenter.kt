package com.lexivip.lexi.index.discover

import android.view.View
import com.basemodule.tools.LogUtil
import com.lexivip.lexi.JsonUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.discoverLifeAesthetics.UserFocusState
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendBean
import com.lexivip.lexi.net.NetStatusBean
import org.json.JSONObject
import java.io.IOException

class ArticleDetailPresenter(view: ArticleDetailContract.View) : ArticleDetailContract.Presenter {

    private var view: ArticleDetailContract.View = checkNotNull(view)

    private val dataSource: ArticleDetailModel by lazy { ArticleDetailModel() }

    private var rid: String = ""

    override fun loadData(isRefresh: Boolean, rid: String) {
        this.rid = rid
        dataSource.loadData(rid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                view.dismissLoadingView()
                val response = JSONObject(json)
                val isSuccess = response.optBoolean("success")
                val status = response.optJSONObject("status")
                val data = response.optJSONObject("data")
                if (isSuccess) {
                    view.setData(data)
                } else {
                    view.showError(status.getString("message"))
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 关注用户
     */
    override fun focusUser(uid: String, v: View, isFollow: Boolean) {
        dataSource.focusUser(uid, isFollow, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                v.isEnabled = false
            }

            override fun onSuccess(json: String) {
                v.isEnabled = true
                val userFocusState = JsonUtil.fromJson(json, UserFocusState::class.java)
                if (userFocusState.success) {
                    view.setFocusState(userFocusState.data.followed_status)
                } else {
                    view.showError(userFocusState.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                v.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载相关故事
     */
    override fun getRelateStories(rid: String) {
        dataSource.getRelateStories(rid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val storyListBean = JsonUtil.fromJson(json, StoryListBean::class.java)
                if (storyListBean.success) {
                    view.setRelateStoriesData(storyListBean.data.life_records)
                } else {
                    view.showError(storyListBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取推荐商品
     */
    override fun getRecommendProducts(rid: String) {
        dataSource.getRecommendProducts(rid, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setRecommendProductsData(editorRecommendBean.data.products)
                } else {
                    view.showError(editorRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 关注/取消品牌馆
     */
    override fun focusBrandPavilion(store_rid: String, isFavorite: Boolean, v: View, isHeaderPavilion: Boolean) {
        dataSource.focusBrandPavilion(store_rid, isFavorite, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                v.isEnabled = false
                if (isHeaderPavilion) {
                    view.setHeadPavilionFocusState(isFavorite)
                } else {
                    view.setBrandPavilionFocusState(isFavorite)
                }
            }

            override fun onSuccess(json: String) {
                v.isEnabled = true
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
//                    view.setBrandPavilionFocusState(isFavorite)
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                v.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }
}