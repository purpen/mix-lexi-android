package com.lexivip.lexi.pay

import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.R
import com.lexivip.lexi.order.CreateOrderBean
import com.lexivip.lexi.payUtil.PayUtil
import kotlinx.android.synthetic.main.activity_select_pay_way.*

class SelectPayWayActivity : BaseActivity() {
    override val layout: Int = R.layout.activity_select_pay_way

    private var createOrderBean: CreateOrderBean? = null
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    override fun getIntentData() {
        createOrderBean = intent.getParcelableExtra(TAG)
    }

    companion object {
        //微信支付
        const val WECHAT_PAY: Int = 1
        //支付宝
        const val ALI_PAY: Int = 2
        //蚂蚁支付
        const val ANT_PAY: Int = 3
    }

    override fun initView() {

        customHeadView.setHeadCenterTxtShow(true, R.string.title_select_pay_way)

        textViewSubtotalPrice.text = "${createOrderBean?.orderTotalPrice}"
        textViewSubtotalPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp11, R.dimen.dp14), null, null, null)

        if (createOrderBean?.expressTotalPrice == 0.0) {
            textViewTextDeliveryPrice.text = "包邮"
            textViewTextDeliveryPrice.setTextColor(Util.getColor(R.color.color_c2a67d))
            textViewTextDeliveryPrice.setCompoundDrawables(null, null, null, null)
        } else {
            textViewTextDeliveryPrice.text = "${createOrderBean?.expressTotalPrice}"
            textViewTextDeliveryPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp6, R.dimen.dp8), null, null, null)
            textViewTextDeliveryPrice.setTextColor(Util.getColor(R.color.color_333))
        }

        textViewTotalPrice.text = "${createOrderBean?.userPayTotalPrice}"

        createOrderBean?.payWay = WECHAT_PAY
    }

    /**
     * 重置选中状态
     */
    private fun resetSelectState() {
        checkBoxWechat.isChecked = false
        checkBoxAli.isChecked = false
        checkBoxAnt.isChecked = false
    }


    override fun installListener() {

        relativeLayoutWeChatPay.setOnClickListener {
            resetSelectState()
            checkBoxWechat.isChecked = true
            createOrderBean?.payWay = WECHAT_PAY
        }

        relativeLayoutAliPay.setOnClickListener {
            resetSelectState()
            checkBoxAli.isChecked = true
            createOrderBean?.payWay = ALI_PAY
        }

        relativeLayoutAntPay.setOnClickListener {
            resetSelectState()
            checkBoxAnt.isChecked = true
            createOrderBean?.payWay = ANT_PAY
        }

        //点击开启支付窗口
        buttonPayNow.setOnClickListener {
            //TODO 支付成功跳转支付结果页
            /*val intent = Intent(this, PayResultActivity::class.java)
            intent.putExtra(PayResultActivity::class.java.simpleName, createOrderBean)
            startActivity(intent)
            finish()*/
            val payUtil = PayUtil(dialog, createOrderBean!!.consigneeInfo.rid, createOrderBean!!.payWay,0)

        }
    }
}