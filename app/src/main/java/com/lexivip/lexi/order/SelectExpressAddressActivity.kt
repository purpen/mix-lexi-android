package com.lexivip.lexi.order

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.DividerItemDecoration
import com.lexivip.lexi.eventBusMessge.MessageOrderSuccess
import com.lexivip.lexi.R
import com.lexivip.lexi.address.AddressActivity
import com.smart.dialog.listener.OnBtnClickL
import com.smart.dialog.widget.NormalDialog
import kotlinx.android.synthetic.main.acticity_select_express_address.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject


class SelectExpressAddressActivity : BaseActivity(), SelectExpressAddressContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private val presenter: SelectExpressAddressPresenter by lazy { SelectExpressAddressPresenter(this) }

    private val adapter: AdapterUserAddressList by lazy { AdapterUserAddressList(R.layout.adapter_user_express_address) }

    override val layout: Int = R.layout.acticity_select_express_address

    private lateinit var footerView: View

    //订单信息
    private lateinit var createOrderBean: CreateOrderBean

    override fun getIntentData() {
        if (intent.hasExtra(SelectExpressAddressActivity::class.java.simpleName)) {
            createOrderBean = intent.getParcelableExtra(SelectExpressAddressActivity::class.java.simpleName)
        }
    }

    override fun initView() {
        EventBus.getDefault().register(this)

        customHeadView.setHeadCenterTxtShow(true, R.string.title_select_express_address)
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext(), R.color.color_f5f7f9, recyclerView, 1f))
        val view = View(this)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp10))
        view.setBackgroundColor(Util.getColor(android.R.color.transparent))
        adapter.addHeaderView(view)

        footerView = View.inflate(this, R.layout.footer_add_new_address, null)
        adapter.addFooterView(footerView)
    }

    override fun setPresenter(presenter: SelectExpressAddressContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {

        footerView.setOnClickListener { //添加新地址
            val intent = Intent(this, AddressActivity::class.java)
            intent.putExtra(AddressActivity::class.java.simpleName, createOrderBean.address_rid)
            startActivityForResult(intent,Constants.REQUEST_CODE_REFRESH_ADDRESS)
        }

        //编辑地址
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) as UserAddressListBean.DataBean
            val intent = Intent(this, AddressActivity::class.java)
            intent.putExtra("isNew", false)
            intent.putExtra(AddressActivity::class.java.simpleName, item.rid)
            startActivityForResult(intent,Constants.REQUEST_CODE_EDIT_ADDRESS)
        }

        //checkBox点击
        adapter.setOnItemChildClickListener { _, _, position ->
            val data = adapter.data
            for (item in data) {
                item.is_default = false
            }

            val item = adapter.getItem(position) as UserAddressListBean.DataBean
            item.is_default = true

            adapter.notifyDataSetChanged()

            setConfirmOrderButtonState()
        }


        buttonConfirmOrder.setOnClickListener {

            var selectedItem: UserAddressListBean.DataBean? = null

            for (item in adapter.data) {
                if (item.is_default) {
                    selectedItem = item
                    break
                }
            }

            if (selectedItem == null) {
                ToastUtil.showInfo("请先选择收货地址")
                return@setOnClickListener
            }

            var isNeedIdentify = false
            //判断是否需要海关信息
            for (store in createOrderBean.store_items) {
                for (product in store.items) {
                    if (product.delivery_country_id != selectedItem.country_id){
                        isNeedIdentify = true
                        break
                    }
                }
            }

            createOrderBean.consigneeInfo = selectedItem
            //判断用户是否有上传海关信息
            if (isNeedIdentify){
                presenter.getUserIdentifyInfo(selectedItem.first_name,selectedItem.mobile,selectedItem)
            }else{ //发货地与收货地同一个国家
                jump2ConfirmOrder()
            }
        }
    }

    /**
     * //订单提交成功关闭本界面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onOrderCommitSuccess(message: MessageOrderSuccess) {
        finish()
    }

    /**
     * 去确认订单界面
     */
    private fun jump2ConfirmOrder(){

        val intent = Intent(this, ConfirmOrderActivity::class.java)
        intent.putExtra(ConfirmOrderActivity::class.java.simpleName, createOrderBean)
        startActivity(intent)
    }

    /**
     * 获取用户身份信息
     */
    override fun setUserIndentityInfo(data: JSONObject, selectedItem: UserAddressListBean.DataBean) {
        val identify = data.optString("id_card")
        if (TextUtils.isEmpty(identify)){ //未上传身份信息
            showEditAddressDialog(selectedItem.rid)
        }else{ //已上传身份信息
            jump2ConfirmOrder()
        }
    }


    private fun showEditAddressDialog(rid: String) {
        val color333 = Util.getColor(R.color.color_333)
        val color007aaf = Util.getColor(R.color.color_007aaf)
        val white = Util.getColor(android.R.color.white)
        val dialog = NormalDialog(this)
        dialog.isTitleShow(true)
                .style(NormalDialog.STYLE_TWO)
                .titleTextColor(Util.getColor(android.R.color.black))
                .titleTextSize(17f)
                .title(getString(R.string.text_tips))
                .bgColor(white)
                .cornerRadius(4f)
                .content(Util.getString(R.string.text_no_identify_info))
                .contentGravity(Gravity.CENTER)
                .contentTextColor(color333)
                .contentTextSize(13f)
                .dividerColor(Util.getColor(R.color.color_ccc))
                .btnText(Util.getString(R.string.text_cancel),Util.getString(R.string.text_go_add))
                .btnTextSize(17f, 17f)
                .btnTextColor(color007aaf, color007aaf)
                .btnPressColor(white)
                .widthScale(0.85f)
                .show()
        dialog.setOnBtnClickL(OnBtnClickL {
            dialog.dismiss()
        }, OnBtnClickL {
            val intent = Intent(this, AddressActivity::class.java)
            intent.putExtra("isForeign", true)
            intent.putExtra(AddressActivity::class.java.simpleName,rid)
            startActivityForResult(intent,Constants.REQUEST_CODE_REFRESH_ADDRESS)
            dialog.dismiss()
        })
    }


    override fun setNewData(addresses: MutableList<UserAddressListBean.DataBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(addresses)
        setConfirmOrderButtonState()
    }

    /**
     * 设置去确认订单界面按钮状态
     */
    private fun setConfirmOrderButtonState() {
        val data = adapter.data

        var hasChecked = false
        for (item in data) {
            if (item.is_default) {
                //设置默认地址rid
                createOrderBean.address_rid = item.rid
                hasChecked = true
                break
            }
        }
        buttonConfirmOrder.isEnabled = hasChecked
    }


    override fun requestNet() {
        presenter.loadData()
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun loadMoreFail() {
        adapter.loadMoreFail()
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

    override fun goPage() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == Constants.REQUEST_CODE_REFRESH_ADDRESS){ //添加成功新地址

        }
//        presenter.loadData()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
