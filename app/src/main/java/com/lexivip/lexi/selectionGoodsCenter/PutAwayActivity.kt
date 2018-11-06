package com.lexivip.lexi.selectionGoodsCenter

import android.graphics.Paint
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import kotlinx.android.synthetic.main.acticity_putaway.*
import org.greenrobot.eventbus.EventBus

class PutAwayActivity : BaseActivity(), PutAwayActivityContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    private lateinit var goods: ProductBean

    private val presenter: PutAwayActivityPresenter by lazy { PutAwayActivityPresenter(this) }

    override val layout: Int = R.layout.acticity_putaway

    override fun getIntentData() {
        goods = intent.getParcelableExtra(TAG)
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.title_putaway_life_house)
        setGoodsData()
    }

    private fun setGoodsData() {
        textView0.text = goods.name
        if (goods.real_sale_price == 0.0) { //折扣价为0,显示真实价格
            textView1.text = "￥${goods.real_price}"
        } else { //折扣价不为0显示折扣价格和带划线的真实价格
            textView1.text = "￥${goods.real_sale_price}"
            textView2.visibility = View.VISIBLE
            textView2.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textView2.text = "￥${goods.real_price}"
        }

        textView3.text = "喜欢 +${goods.like_count}"

        textViewEarn.text = "赚 ￥${goods.commission_price}"

        if (goods.is_sold_out) {
            imageViewStatus.visibility = View.VISIBLE
        } else {
            imageViewStatus.visibility = View.GONE
        }

        if (goods.is_free_postage) {
            imageViewFreeExpress.visibility = View.VISIBLE
        } else {
            imageViewFreeExpress.visibility = View.VISIBLE
        }

        GlideUtil.loadImageWithRadius(goods.cover, imageViewGoods, DimenUtil.getDimensionPixelSize(R.dimen.dp4))
    }

    override fun installListener() {
        buttonConfirmOrder.setOnClickListener {
//            if (editText.text.trim().isEmpty() || editText.text.trim().length < 10) {
//                ToastUtil.showInfo("请填写不少10个字推荐语")
//                return@setOnClickListener
//            }
            val content = editText.text.trim().toString()

            presenter.putAwayGoods(goods.rid, content)
        }
    }

    override fun setPresenter(presenter: PutAwayActivityContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun goPage() { //提交成功关闭上架商品页
        EventBus.getDefault().post(goods)
        finish()
    }
}