package com.thn.lexi.goods.lifehouse
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.JsonUtil
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.goods.bean.FavoriteBean
import com.thn.lexi.goods.explore.EditorRecommendBean
import com.thn.lexi.net.NetStatusBean
import com.thn.lexi.user.completeinfo.UploadTokenBean
import org.json.JSONArray
import java.io.IOException

class LifeHousePresenter(view: LifeHouseContract.View) : LifeHouseContract.Presenter {

    private var view: LifeHouseContract.View = checkNotNull(view)

    private val dataSource: LifeHouseModel by lazy { LifeHouseModel() }

    override fun loadData(cid: String, page: Int) {
        dataSource.loadData(cid, page,object : IDataSource.HttpRequestCallBack {
            override fun onStart() {
                view.showLoadingView()
            }

            override fun onSuccess(json: String) {
                view.dismissLoadingView()
                val distributionGoodsBean = JsonUtil.fromJson(json, DistributionGoodsBean::class.java)
                if (distributionGoodsBean.success) {
                    view.setNewData(distributionGoodsBean.data.products)
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

    override fun loadMoreData(cid: String, page: Int) {
        dataSource.loadData(cid, page, object : IDataSource.HttpRequestCallBack {

            override fun onSuccess(json: String) {
                val distributionGoodsBean = JsonUtil.fromJson(json, DistributionGoodsBean::class.java)
                if (distributionGoodsBean.success) {
                    val products = distributionGoodsBean.data.products
                    if (products.isEmpty() ){
                        view.loadMoreEnd()
                    }else{
                        view.loadMoreComplete()
                    }
                    view.addData(products)
                } else {
                    view.showError(distributionGoodsBean.status.message)
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
    fun getWelcomeInWeek() {
        dataSource.getWelcomeInWeek( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val editorRecommendBean = JsonUtil.fromJson(json, EditorRecommendBean::class.java)
                if (editorRecommendBean.success) {
                    view.setWelcomeInWeekData(editorRecommendBean.data.products)
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
     * 获取生活馆浏览人数
     */
    fun getLookPeople() {
        dataSource.getLookPeople( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val lookPeopleBean = JsonUtil.fromJson(json, LookPeopleBean::class.java)
                if (lookPeopleBean.success) {
                    view.setLookPeopleData(lookPeopleBean.data.users)
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
    fun getLifeHouse() {
        dataSource.getLifeHouse( object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
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
    fun editLifeHouse(title: String, description: String){

        dataSource.editLifeHouse(title,description,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val lifeHouseBean = JsonUtil.fromJson(json, LifeHouseBean::class.java)
                if (lifeHouseBean.success) {
                    view.setEditLifeHouseData(lifeHouseBean)
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
        dataSource.deleteDistributeGoods(rid,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val netStatusBean = JsonUtil.fromJson(json, NetStatusBean::class.java)
                if (netStatusBean.success) {
                    view.deleteDistributeGoods(position)
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
            }

            override fun onSuccess(json: String) {
                viewClicked.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, FavoriteBean::class.java)
                if (favoriteBean.success) {
                    view.setFavorite(false,position)
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
            }
            override fun onSuccess(json: String) {
                viewClicked.isEnabled = true
                val favoriteBean = JsonUtil.fromJson(json, FavoriteBean::class.java)
                if (favoriteBean.success) {
                    view.setFavorite(true,position)
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
                    view.setUploadTokenData(uploadTokenBean,byteArray)
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
        if (uploadTokenBean==null) {
            ToastUtil.showInfo(R.string.text_net_error)
            return
        }

        dataSource.uploadLifeHouseLogo(byteArray,uploadTokenBean,object : IDataSource.UpLoadCallBack {
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
        if (TextUtils.isEmpty(logoId)){
            ToastUtil.showInfo(AppApplication.getContext().getString(R.string.hint_text_upload_avatar))
            return
        }

        dataSource.uploadLifeHouseLogoId(logoId,object : IDataSource.HttpRequestCallBack {
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


}