package com.thn.lexi.pay

import android.content.Intent
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.ui.BaseActivity
import com.thn.lexi.R
import com.thn.lexi.order.CreateOrderBean
import kotlinx.android.synthetic.main.activity_select_pay_way.*

class SelectPayWayActivity : BaseActivity() {
    override val layout: Int = R.layout.activity_select_pay_way

    private var createOrderBean: CreateOrderBean? = null
    override fun getIntentData() {
        createOrderBean = intent.getParcelableExtra(TAG)
    }

    override fun initView() {

        customHeadView.setHeadCenterTxtShow(true, R.string.title_select_pay_way)

        textViewSubtotalPrice.text = "${createOrderBean?.orderTotalPrice}"
        textViewSubtotalPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit,R.dimen.dp11,R.dimen.dp14),null,null,null)

        if (createOrderBean?.expressTotalPrice == 0.0) {
            textViewTextDeliveryPrice.text = "包邮"
            textViewTextDeliveryPrice.setTextColor(Util.getColor(R.color.color_c2a67d))
            textViewTextDeliveryPrice.setCompoundDrawables(null,null,null,null)
        } else {
            textViewTextDeliveryPrice.text = "${createOrderBean?.expressTotalPrice}"
            textViewTextDeliveryPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit,R.dimen.dp6,R.dimen.dp8),null,null,null)
            textViewTextDeliveryPrice.setTextColor(Util.getColor(R.color.color_333))
        }

        textViewTotalPrice.text = "${createOrderBean?.userPayTotalPrice}"
    }

    /**
     * 重置选中状态
     */
    private fun resetSelectState(){
        checkBoxWechat.isChecked = false
        checkBoxAli.isChecked = false
        checkBoxAnt.isChecked = false
    }

    /**
     * 检查是否选择过支付方式
     */
    private fun hasSelectPayWay():Boolean{
        var hasSelect = false
        if (checkBoxWechat.isChecked) hasSelect = true

        if (checkBoxAli.isChecked) hasSelect = true

        if (checkBoxAnt.isChecked) hasSelect = true

        return hasSelect
    }

    override fun installListener() {

        relativeLayoutWeChatPay.setOnClickListener {
            resetSelectState()
            checkBoxWechat.isChecked = true
        }

        relativeLayoutAliPay.setOnClickListener {
            resetSelectState()
            checkBoxAli.isChecked = true
        }

        relativeLayoutAntPay.setOnClickListener {
            resetSelectState()
            checkBoxAnt.isChecked = true
        }

        //点击开启支付窗口
        buttonPayNow.setOnClickListener {
            if (hasSelectPayWay()){
                //TODO 支付成功跳转支付结果页
                val intent = Intent(this,PayResultActivity::class.java)
                intent.putExtra(PayResultActivity::class.java.simpleName,createOrderBean)
                startActivity(intent)
                finish()
            }else{
                ToastUtil.showInfo(getString(R.string.hint_please_select_payway))
            }
        }
    }
}