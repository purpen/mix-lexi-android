package com.thn.lexi.index.detail

import android.content.Context
import android.view.View
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ToastUtil
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.thn.lexi.R
import kotlinx.android.synthetic.main.dialog_share_goods_bottom.view.*

class GoodsDetailSaleBottomDialog(context: Context, presenter: GoodsDetailPresenter, goodsData: GoodsAllDetailBean.DataBean) : BottomBaseDialog<GoodsDetailSaleBottomDialog>(context) {
    private lateinit var view: View
    private val present: GoodsDetailPresenter by lazy { presenter }
    private val product: GoodsAllDetailBean.DataBean by lazy { goodsData }
    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_share_goods_bottom, null)
        GlideUtil.loadImageWithDimen(product.assets[0].view_url,view.imageView1,DimenUtil.dp2px(145.0),DimenUtil.dp2px(124.0))
        view.textViewPrice.text = "${product.commission_price}"
        return view
    }

    override fun setUiBeforShow() {
        view.textViewCancel.setOnClickListener {
            dismiss()
        }
        view.linearLayoutWeChatShare.setOnClickListener {
            ToastUtil.showInfo("微信分享")
        }

        view.linearLayoutSave.setOnClickListener {
            ToastUtil.showInfo("微信分享")
        }
    }
}