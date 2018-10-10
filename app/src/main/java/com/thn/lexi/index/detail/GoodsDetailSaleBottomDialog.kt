package com.thn.lexi.index.detail

import android.content.Context
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.thn.lexi.JsonUtil
import com.thn.lexi.R
import kotlinx.android.synthetic.main.dialog_share_goods_bottom.view.*
import java.io.IOException

class GoodsDetailSaleBottomDialog(context: Context, presenter: GoodsDetailPresenter, goodsData: GoodsAllDetailBean.DataBean) : BottomBaseDialog<GoodsDetailSaleBottomDialog>(context) {
    private lateinit var view: View
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
                    GlideUtil.loadImageWithDimen(posterBean.data.image_url,view.imageView2,DimenUtil.dp2px(74.0),DimenUtil.dp2px(131.0))
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
        view.linearLayoutWeChatShare.setOnClickListener {
            ToastUtil.showInfo("微信分享")
        }

        view.linearLayoutSave.setOnClickListener {

        }
    }
}