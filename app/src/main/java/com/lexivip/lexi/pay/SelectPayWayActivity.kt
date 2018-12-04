package com.lexivip.lexi.pay

import android.os.Handler
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.R
import com.lexivip.lexi.dialog.InquiryDialog
import com.lexivip.lexi.order.CreateOrderBean
import com.lexivip.lexi.payUtil.PayUtil
import kotlinx.android.synthetic.main.activity_select_pay_way.*

class SelectPayWayActivity : BaseActivity(), SelectPayWayContract.View {
    override val layout: Int = R.layout.activity_select_pay_way
    private val presenter: SelectPayWayPresenter by lazy { SelectPayWayPresenter(this) }
    private lateinit var createOrderBean: CreateOrderBean
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
        const val ANT_PAY: Int = 4
    }

    override fun initView() {

        customHeadView.setHeadCenterTxtShow(true, R.string.title_select_pay_way)

        textViewSubtotalPrice.text = "${createOrderBean.orderTotalPrice}"
        textViewSubtotalPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp11, R.dimen.dp14), null, null, null)

        if (createOrderBean.expressTotalPrice == 0.0) {
            textViewDeliveryPrice.text = "包邮"
            textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_c2a67d))
            textViewDeliveryPrice.setCompoundDrawables(null, null, null, null)
        } else {
            textViewDeliveryPrice.text = "${createOrderBean.expressTotalPrice}"
            textViewDeliveryPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp6, R.dimen.dp8), null, null, null)
            textViewDeliveryPrice.setTextColor(Util.getColor(R.color.color_333))
        }

        textViewFirstOrderDiscountPrice.text = "-￥${createOrderBean.firstOrderDiscountPrice}"

        textViewFullReducePrice.text = "-￥${createOrderBean.fullReductionTotalPrice}"

        if (createOrderBean.notUsingOfficialCoupon) {
            textViewCouponPrice.text = "-￥${createOrderBean.shopCouponTotalPrice}"
        } else {
            textViewCouponPrice.text = "-￥${createOrderBean.officialCouponPrice}"
        }

        textViewTotalPrice.text = "${createOrderBean.userPayTotalPrice}"

        createOrderBean.payWay = WECHAT_PAY
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
            createOrderBean.payWay = WECHAT_PAY
        }

        relativeLayoutAliPay.setOnClickListener {
            resetSelectState()
            checkBoxAli.isChecked = true
            createOrderBean.payWay = ALI_PAY
        }

        relativeLayoutAntPay.setOnClickListener {
            resetSelectState()
            checkBoxAnt.isChecked = true
            createOrderBean.payWay = ANT_PAY
        }

        //点击开启支付窗口
        buttonPayNow.setOnClickListener {
            /*val intent = Intent(this, PayResultActivity::class.java)
            intent.putExtra(PayResultActivity::class.java.simpleName, createOrderBean)
            startActivity(intent)
            finish()*/
            LogUtil.e("订单编号：" + createOrderBean.order_rid)
            val payUtil = PayUtil(dialog, createOrderBean.order_rid, createOrderBean.payWay, 0)
        }

        customHeadView.setGoBackListener { showConfirmDialog() }
    }
    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }


    override fun setPresenter(presenter: SelectPayWayContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun savePayWaySuccess() {
        LogUtil.e("已保存支付方式")
    }


    private fun showConfirmDialog() {
        InquiryDialog(this, Util.getString(R.string.text_confirm_exit_payway_page), Util.getString(R.string.text_order_will_cancel_in_ten_minute), Util.getString(R.string.text_confirm_exit), Util.getString(R.string.text_continue_pay), InquiryDialog.InquiryInterface { isCheck ->
            if (isCheck) {
                presenter.savePayWay(createOrderBean.order_rid, "${createOrderBean.payWay}")
                Handler().postDelayed({ finish() }, 300)
            }
        }).show()
    }

    override fun onBackPressed() {
        showConfirmDialog()
    }

}