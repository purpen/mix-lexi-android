package com.lexivip.lexi.index.lifehouse

import android.text.TextUtils
import android.view.View
import com.lexivip.lexi.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.index.bean.FavoriteBean
import com.lexivip.lexi.index.explore.ExploreBannerBean
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendBean
import com.lexivip.lexi.index.selection.HeadLineBean
import com.lexivip.lexi.net.NetStatusBean
import com.lexivip.lexi.user.completeinfo.UploadTokenBean
import org.json.JSONArray
import java.io.IOException

class LifeHousePresenter(view: LifeHouseContract.View) : LifeHouseContract.Presenter {

    private var view: LifeHouseContract.View = checkNotNull(view)

    private var page = 1

    private val dataSource: LifeHouseModel by lazy { LifeHouseModel() }

    /**
     * 获取未开通生活馆头部背景
     */
    override fun getNotSmallBHeaderImage(isRefresh: Boolean) {
        dataSource.getNotSmallBHeaderImage(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val exploreBannerBean = JsonUtil.fromJson(json, ExploreBannerBean::class.java)
                if (exploreBannerBean.success) {
                    val banner_images = exploreBannerBean.data.banner_images
                    if (banner_images == null || banner_images.isEmpty()) return
                    view.setNotSmallBHeaderImage(banner_images[0])
                } else {
                    view.showError(exploreBannerBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     *  开馆头条背景图
     */
    override fun getOpenStoreHeadLineImage(isRefresh: Boolean) {
        dataSource.getOpenStoreHeadLineImage(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val bannerImageBean = JsonUtil.fromJson(json, ExploreBannerBean::class.java)
                if (bannerImageBean.success) {
                    val banner_images = bannerImageBean.data.banner_images
                    if (banner_images == null || banner_images.isEmpty()) return
                    view.setOpenStoreHeadLineImage(banner_images[0])
                    page++
                } else {
                    view.showError(bannerImageBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 加载馆主推荐数据
     */
    override fun loadData(isRefresh: Boolean) {
        if (isRefresh) page = 1
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val distributionGoodsBean = JsonUtil.fromJson(json, DistributionGoodsBean::class.java)
                if (distributionGoodsBean.success) {
                    view.setNewData(distributionGoodsBean.data.products)
                    page++
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

    /**
     * 获取头条
     */
    override fun getHeadLine() {
        dataSource.getHeadLine(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val headLineBean = JsonUtil.fromJson(json, HeadLineBean::class.java)
                if (headLineBean.success) {
                    view.setHeadLineData(headLineBean.data.headlines)
                } else {
                    view.showError(headLineBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 加载更多分销商品
     */
    fun loadMoreOwnerGoodsData() {
        dataSource.loadData(page, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val distributionGoodsBean = JsonUtil.fromJson(json, DistributionGoodsBean::class.java)
                if (distributionGoodsBean.success) {
                    val products = distributionGoodsBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
                        page++
                    }
                } else {
                    view.loadMoreFail()
                    view.showError(distributionGoodsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 加载更多本周最受欢迎
     */
    override fun loadMoreData() {
        dataSource.getWelcomeInWeek(page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    val products = editorRecommendBean.data.products
                    if (products.isEmpty()) {
                        view.loadMoreEnd()
                    } else {
                        view.loadMoreComplete()
                        view.addData(products)
                        page++
                    }
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
     * 获取本周最受欢迎
     */
    fun getWelcomeInWeek(isRefresh: Boolean) {
        if (isRefresh) page = 1
        dataSource.getWelcomeInWeek(page, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setWelcomeInWeekData(editorRecommendBean.data.products)
                    page++
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
     * 获取生活馆浏览人数
     */
    fun getLookPeople(isRefresh: Boolean) {
        dataSource.getLookPeople(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val lookPeopleBean = JsonUtil.fromJson(json, LookPeopleBean::class.java)
                if (lookPeopleBean.success) {
                    view.setLookPeopleData(lookPeopleBean.data)
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
    fun getLifeHouse(isRefresh: Boolean) {
        dataSource.getLifeHouse(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                LogUtil.e(json)
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
    fun editLifeHouse(title: String, description: String) {

        dataSource.editLifeHouse(title, description, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.setEditLifeHouseData(title, description)
            }

            override fun onSuccess(json: String) {
                val lifeHouseBean = JsonUtil.fromJson(json, LifeHouseBean::class.java)
                if (lifeHouseBean.success) {
                    LogUtil.e("修改生活馆信息成功")
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
     * 删除分销商品
     */
    fun deleteDistributeGoods(rid: String, position: Int) {
        dataSource.deleteDistributeGoods(rid, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
//                    view.deleteDistributeGoods(position)
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 取消喜欢
     */
    fun unfavoriteGoods(rid: String, position: Int, viewClicked: View) {
        dataSource.unfavoriteGoods(rid, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                viewClicked.isEnabled = false
                view.setFavorite(false, position)
            }

            override fun onSuccess(json: String) {
                viewClicked.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, FavoriteBean::class.java)
                if (favoriteBean.success) {

                } else {
                    view.showError(favoriteBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                viewClicked.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 喜欢分享
     */
    fun favoriteGoods(rid: String, position: Int, viewClicked: View) {
        dataSource.favoriteGoods(rid, object : IDataSource.HttpRequestCallBack {

            override fun onStart() {
                viewClicked.isEnabled = false
                view.setFavorite(true, position)
            }

            override fun onSuccess(json: String) {
                viewClicked.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, FavoriteBean::class.java)
                if (favoriteBean.success) {
//                    view.setFavorite(true,position)
                } else {
                    view.showError(favoriteBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                viewClicked.isEnabled = true
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取图片上传的Token
     */
    fun getUploadToken(byteArray: ByteArray) {
        dataSource.getUploadToken(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val uploadTokenBean = JsonUtil.fromJson(json, UploadTokenBean::class.java)
                if (uploadTokenBean.success) {
                    uploadLifeHouseLogo(uploadTokenBean, byteArray)
                } else {
                    view.showInfo(uploadTokenBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 上传生活馆logo
     */
    fun uploadLifeHouseLogo(uploadTokenBean: UploadTokenBean?, byteArray: ByteArray) {
        if (uploadTokenBean == null) {
            ToastUtil.showInfo(R.string.text_net_error)
            return
        }

        dataSource.uploadLifeHouseLogo(byteArray, uploadTokenBean, object : IDataSource.UpLoadCallBack {
            override fun onComplete(ids: JSONArray) {
                LogUtil.e("uploadAvatar===上传完成，图片id=${ids[0]}")
                view.setLifeHouseLogoData(ids)
            }
        })
    }

    /**
     * 上传logoId
     */
    fun uploadLifeHouseLogoId(logoId: String) {
        if (TextUtils.isEmpty(logoId)) {
            ToastUtil.showInfo(AppApplication.getContext().getString(R.string.hint_text_upload_avatar))
            return
        }

        dataSource.uploadLifeHouseLogoId(logoId, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    LogUtil.e("品牌馆logoId上传成功")
                } else {
                    view.showError(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 获取新分销的产品
     */
    override fun getNewPublishProducts(isRefresh: Boolean) {
        dataSource.getNewPublishProducts(object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                if (!isRefresh) view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val newPublishProductsBean = JsonUtil.fromJson(json, NewPublishProductsBean::class.java)
                if (newPublishProductsBean.success) {
                    view.setNewPublishProductsData(newPublishProductsBean.data.products)
                } else {
                    view.showInfo(newPublishProductsBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 获取新品速递
     */
    fun getNewProducts(isRefresh: Boolean) {
        dataSource.getNewProducts(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setNewProductsExpressData(editorRecommendBean.data.products)
                } else {
                    view.showInfo(editorRecommendBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.dismissLoadingView()
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }

    /**
     * 改变开馆提示状态
     */
    fun changeNoticeStatus(storeId: String, close_status: Int) {
        dataSource.changeNoticeStatus(storeId,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                when(close_status){
                    0->{
                        view.setOpenStoreTipsStatus(1)
                    }
                    1->{
                        view.setOpenStoreTipsStatus(2)
                    }
                }
            }
            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    LogUtil.e("改变开馆提示成功")
                } else {
                    view.showInfo(netStatusBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                view.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


}