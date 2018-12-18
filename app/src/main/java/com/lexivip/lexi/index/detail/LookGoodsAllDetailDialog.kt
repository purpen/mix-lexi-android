package com.lexivip.lexi.index.detail

import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.Util
import com.lexivip.lexi.R
import com.smart.animation.SlideEnter.SlideBottomEnter
import com.smart.animation.SlideExit.SlideBottomExit
import com.smart.dialog.utils.CornerUtils
import com.smart.dialog.widget.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_look_goods_all_detail.view.*
import kotlinx.android.synthetic.main.view_goods_description.view.*


class LookGoodsAllDetailDialog(context: FragmentActivity?, goodsData: GoodsAllDetailBean.DataBean) : BaseDialog<LookGoodsAllDetailDialog>(context) {
    private val data by lazy { goodsData }
    private lateinit var view: View
    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_look_goods_all_detail, null)
        showAnim(SlideBottomEnter())
        dismissAnim(SlideBottomExit())
        view.buttonLookAll.visibility = View.GONE
        view.background = CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), 0f)

        return view
    }

    override fun setUiBeforShow() {
        view.imageViewClose.setOnClickListener { dismiss() }
        view.textViewLightSpot.maxLines = Int.MAX_VALUE
        view.textViewMaterial.maxLines = Int.MAX_VALUE
        view.textViewCharacter.maxLines = Int.MAX_VALUE
        if (TextUtils.isEmpty(data.features)) {
            view.textViewLightSpot.visibility = View.GONE
        } else {
            view.textViewLightSpot.visibility = View.VISIBLE
            view.textViewLightSpot.text = "亮点：${data.features}"
        }

        if (!TextUtils.isEmpty(data.expressTime)) {
            view.textViewExpress.visibility = View.VISIBLE
            view.textViewExpressTime.visibility = View.VISIBLE
            view.textViewExpressTime.text = data.expressTime
        }

        if (data.is_custom_service) { //可定制
            view.textViewCharacter.visibility = View.VISIBLE
            view.textViewCharacter.text = "特点：" + Util.getString(R.string.text_can_custom_service)
        } else {
            view.textViewCharacter.visibility = View.GONE
        }

        if (TextUtils.isEmpty(data.material_name)) {
            view.textViewMaterial.visibility = View.GONE
        } else {
            view.textViewMaterial.visibility = View.VISIBLE
            view.textViewMaterial.text = "材质：${data.material_name}"
        }

        if (data.total_stock <= 10) {
            view.textViewCount.visibility = View.VISIBLE
            view.textViewCount.text = "数量：${data.total_stock}件"
        }

        if (data.delivery_country.contains("香港") || data.delivery_country.contains("澳门") || data.delivery_country.contains("台湾")) {
            view.textViewSendAddress.text = data.delivery_country + data.delivery_province
        } else {
            view.textViewSendAddress.text = data.delivery_country + data.delivery_province + data.delivery_city
        }

        if (TextUtils.isEmpty(data.return_policy_title)) {
            view.textViewReturnPolicy.visibility = View.GONE
        } else {
            view.textViewReturnPolicy.text = data.return_policy_title
        }

        view.textViewProductReturnPolicy.maxLines = Int.MAX_VALUE
        view.textViewProductReturnPolicy.text = data.product_return_policy
    }

    fun setExpressTime(expressTime: String?) {
        if (!expressTime.isNullOrEmpty()) {
            view.textViewExpress.visibility = View.VISIBLE
            view.textViewExpressTime.visibility = View.VISIBLE
            view.textViewExpressTime.text = expressTime
        }

    }
}