package com.lexivip.lexi.selectionGoodsCenter

import android.Manifest
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.basemodule.tools.Constants
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.shareUtil.ShareUtil
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class FragmentFirstPublish:BaseFragment(),FirstPublishContract.View, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: FirstPublishPresenter
    private var price:String?=null
    private var goodsId:String?=null
    private var storeId:String?=null

    private lateinit var adapter: AdapterHotGoods
    companion object {
        @JvmStatic
        fun newInstance(): FragmentFirstPublish = FragmentFirstPublish()
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        presenter = FirstPublishPresenter(this)
        adapter = AdapterHotGoods(R.layout.adapter_hot_goods)

        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun setPresenter(presenter: FirstPublishContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun loadData() {
        presenter.loadData()
    }

    override fun setNewData(products: MutableList<ProductBean>) {
        adapter.setNewData(products)
    }

    override fun addData(products: List<ProductBean>) {
        adapter.addData(products)
        adapter.notifyDataSetChanged()
    }

    override fun installListener() {
        adapter.setOnItemChildClickListener { _, view, position ->
            val productsBean = adapter.getItem(position) as ProductBean
            when (view.id) {
                R.id.textView4 -> {
                    goodsId=adapter.data[position].rid
                    price=adapter.data[position].commission_price
                    storeId=adapter.data[position].store_rid
                    share()
                }
                R.id.textView5 -> {
                    val intent = Intent(activity,PutAwayActivity::class.java)
                    intent.putExtra(PutAwayActivity::class.java.simpleName,productsBean)
                    startActivity(intent)
                }
            }
        }

        adapter.setOnItemClickListener { _, _, position ->
            val productsBean = adapter.getItem(position)
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName,productsBean)
            startActivity(intent)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE)
    private fun share() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), *perms)) {
            val shareUtil: ShareUtil = ShareUtil(activity)
            shareUtil.shareGoods(WebUrl.AUTH_GOODS, goodsId, goodsId+"-"+ storeId,price,4)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, *perms)
        }
    }

    override fun showLoadingView() {

    }

    override fun dismissLoadingView() {

    }

    override fun showError(string: String) {

    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun goPage() {

    }

    //上架成功刷新adapter
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun putAwaySuccess(productBean: ProductBean) {
        for (item in adapter.data){
            if (TextUtils.equals(item.rid,productBean.rid)){
                item.have_distributed = true
                adapter.notifyDataSetChanged()
                break
            }
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onRationaleAccepted(requestCode: Int) {

    }
}