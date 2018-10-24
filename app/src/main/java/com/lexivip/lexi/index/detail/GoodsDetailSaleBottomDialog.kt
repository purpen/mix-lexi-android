package com.lexivip.lexi.index.detail
import android.content.Context
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.album.ImageUtils
import kotlinx.android.synthetic.main.dialog_share_goods_bottom.view.*
import java.io.File
import java.io.IOException

class GoodsDetailSaleBottomDialog(context: Context, presenter: GoodsDetailPresenter, goodsData: GoodsAllDetailBean.DataBean) : BottomBaseDialog<GoodsDetailSaleBottomDialog>(context) {
    private lateinit var view: View
    private var posterUrl:String? = null
    private val present: GoodsDetailPresenter by lazy { presenter }
    private val product: GoodsAllDetailBean.DataBean by lazy { goodsData }
    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_share_goods_bottom, null)
        GlideUtil.loadImageWithDimen(product.assets[0].view_url,view.imageView1,DimenUtil.dp2px(145.0),DimenUtil.dp2px(124.0))
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
        present.loadPoster(goodsId,scene,object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val posterBean = JsonUtil.fromJson(json, PosterBean::class.java)
                if (posterBean.success) {
                    posterUrl = posterBean.data.image_url
                    if (TextUtils.isEmpty(posterUrl)) return
                    GlideUtil.loadImageWithDimen(posterUrl,view.imageView2,DimenUtil.dp2px(74.0),DimenUtil.dp2px(131.0))
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
            dismiss()
        }
        view.textViewWechatShare.setOnClickListener {
            ToastUtil.showInfo("微信分享")
        }

        view.textViewSavePoster.setOnClickListener { //保存海报到相册
            if (TextUtils.isEmpty(posterUrl)) return@setOnClickListener
            //保存相册
            GetImageCacheAsyncTask( view.linearLayoutSave).execute(posterUrl)
        }
    }

    internal class GetImageCacheAsyncTask(val v: View) : AsyncTask<String, Void, File>() {

        override fun onPreExecute() {
            v.isEnabled = false
        }

        override fun doInBackground(vararg params: String): File? {
            val imgUrl = params[0]
            try {
                return GlideUtil.downLoadOriginalImage(imgUrl)
            } catch (ex: Exception) {
                return null
            }

        }

        override fun onPostExecute(result: File?) {
            v.isEnabled = true
            if (result == null) return
            //此path就是对应文件的缓存路径
            val path = result.path
            LogUtil.e("path===========", path)
            val bmp = BitmapFactory.decodeFile(path)
            ImageUtils.saveImageToGallery(bmp)
        }
    }
}