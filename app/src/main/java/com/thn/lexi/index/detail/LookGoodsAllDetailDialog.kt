package com.thn.lexi.index.detail
import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.view.View
import com.flyco.dialog.widget.base.BaseDialog
import com.basemodule.tools.Util
import com.flyco.animation.SlideEnter.SlideBottomEnter
import com.flyco.animation.SlideExit.SlideBottomExit
import com.flyco.dialog.utils.CornerUtils
import com.thn.lexi.R
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

        if (TextUtils.isEmpty(data.features)) {
            view.textViewLightSpot.visibility = View.GONE
        } else {
            view.textViewLightSpot.visibility = View.VISIBLE
            view.textViewLightSpot.text = "亮点：${data.features}"
        }

        if (!TextUtils.isEmpty(data.expressTime)) view.textViewExpressTime.text = data.expressTime

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

        view.textViewCount.text = "数量：${data.total_stock}件"

        view.textViewSendAddress.text = data.delivery_country

        if (TextUtils.isEmpty(data.return_policy_title)){
            view.textViewReturnPolicy.visibility = View.GONE
        }else{
            view.textViewReturnPolicy.text = data.return_policy_title
        }

        view.textViewProductReturnPolicy.maxLines = Int.MAX_VALUE
        view.textViewProductReturnPolicy.text = data.product_return_policy
    }

    fun setExpressTime(expressTime: String) {
        view.textViewExpressTime.text = expressTime
    }
}