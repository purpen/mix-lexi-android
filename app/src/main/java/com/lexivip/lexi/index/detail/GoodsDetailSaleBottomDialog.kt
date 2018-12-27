package com.lexivip.lexi.index.detail
import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.album.ImageUtils
import com.lexivip.lexi.net.WebUrl
import com.smart.dialog.widget.base.BottomBaseDialog
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMMin
import kotlinx.android.synthetic.main.dialog_share_goods_bottom.view.*
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference

class GoodsDetailSaleBottomDialog(activity: Activity, presenter: GoodsDetailPresenter, goodsData: GoodsAllDetailBean.DataBean) : BottomBaseDialog<GoodsDetailSaleBottomDialog>(activity) {
    private lateinit var view: View
    private var posterUrl: String? = null
    private var curActivity = activity
    private val present: GoodsDetailPresenter by lazy { presenter }
    private val product: GoodsAllDetailBean.DataBean by lazy { goodsData }
    override fun onCreateView(): View {
        view = View.inflate(curActivity, R.layout.dialog_share_goods_bottom, null)
        GlideUtil.loadImageWithDimen(product.assets[0].view_url, view.imageView1, DimenUtil.dp2px(145.0), DimenUtil.dp2px(124.0), ImageSizeConfig.SIZE_P30X2)
        view.textViewPrice.text = "${product.commission_price}"
        loadData()
        return view
    }

    /**
     * 加载海报
     */
    private fun loadData() {
        val goodsId = product.rid
        val scene = "${product.rid}-${product.store_rid}"
        present.loadPoster(goodsId, scene, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val posterBean = JsonUtil.fromJson(json, PosterBean::class.java)
                if (posterBean.success) {
                    posterUrl = posterBean.data.image_url
                    if (TextUtils.isEmpty(posterUrl)) return
                    GlideUtil.loadImageWithDimen(posterUrl,view.imageView2,DimenUtil.dp2px(74.0),DimenUtil.dp2px(131.0),ImageSizeConfig.SIZE_P30X2)
                } else {
                    ToastUtil.showError(posterBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                ToastUtil.showError(Util.getString(R.string.text_net_error))
            }
        })
    }


    override fun setUiBeforShow() {
        view.textViewCancel.setOnClickListener {
            GlideUtil.clear(view.imageView1)
            GlideUtil.clear(view.imageView2)
            dismiss()
        }
        view.textViewWechatShare.setOnClickListener {
            val image = UMImage(curActivity, product.assets.get(0).view_url)
            val umMin = UMMin(WebUrl.GOODS + product.rid)//兼容低版本的网页链接
            // 小程序消息封面图片
            umMin.setThumb(image)
            // 小程序消息title
            umMin.title = product.name
            // 小程序消息描述
            umMin.description = ""
            //小程序页面路径
            umMin.path = WebUrl.AUTH_GOODS
            // 小程序原始id,在微信平台查询
            umMin.userName = Constants.AUTHAPPID
            ShareAction(curActivity)
                    .withMedia(umMin)
                    .setPlatform(SHARE_MEDIA.WEIXIN)
                    .share()
            //ToastUtil.showInfo("微信分享")
            if (TextUtils.isEmpty(posterUrl)) {
                return@setOnClickListener
            }else {
                val images = UMImage(context, posterUrl)
                ShareAction(context)
                        .withMedia(images)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .share()
                //ToastUtil.showInfo("微信分享")
            }
        }

        view.textViewSavePoster.setOnClickListener {
            //保存海报到相册
            if (TextUtils.isEmpty(posterUrl)) return@setOnClickListener
            //保存相册
            GetImageCacheAsyncTask(view.linearLayoutSave).execute(posterUrl)
        }
    }

    internal class GetImageCacheAsyncTask(v: View) : AsyncTask<String, Void, File>() {
        private val reference: WeakReference<View> by lazy { WeakReference<View>(v) }
        private val view: View? by lazy { reference.get() }

        override fun onPreExecute() {
            view?.isEnabled = false
        }

        override fun doInBackground(vararg params: String): File? {
            val imgUrl = params[0]
            try {
                return GlideUtil.downLoadOriginalImage(imgUrl, view?.context)
            } catch (ex: Exception) {
                return null
            }

        }

        override fun onPostExecute(result: File?) {
            view?.isEnabled = true
            if (result == null) return
            //此path就是对应文件的缓存路径
            val path = result.path
            LogUtil.e("path===========", path)
            val bmp = BitmapFactory.decodeFile(path)
            ImageUtils.saveImageToGallery(bmp)
        }
    }
}