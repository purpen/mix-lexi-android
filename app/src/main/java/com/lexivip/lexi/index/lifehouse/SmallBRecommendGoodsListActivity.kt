package com.lexivip.lexi.index.lifehouse

import android.Manifest
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.view.Gravity
import com.basemodule.tools.Constants
import com.basemodule.tools.Util
import com.basemodule.ui.BaseActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.shareUtil.ShareUtil
import com.smart.dialog.listener.OnBtnClickL
import com.smart.dialog.widget.NormalDialog
import kotlinx.android.synthetic.main.acticity_header_recyclerview.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class SmallBRecommendGoodsListActivity : BaseActivity(), LifeHouseContract.View , EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    private val presenter: LifeHousePresenter by lazy { LifeHousePresenter(this) }

    private val adapter: LifeHouseAdapter by lazy { LifeHouseAdapter(R.layout.adapter_curator_recommend) }

    override val layout: Int = R.layout.acticity_header_recyclerview
    private lateinit var logo:String
    private var positions: Int?=0

    override fun getIntentData() {
        logo = intent.getStringExtra(TAG)
    }

    override fun initView() {
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        customHeadView.setHeadCenterTxtShow(true, R.string.title_owner_recommend)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun requestNet() {
        presenter.loadData(false)
    }

    override fun setNewData(data: List<ProductBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(data)
        adapter.setBrandLogo(logo)
    }

    override fun addData(products: List<ProductBean>) {
        adapter.addData(products)
    }

    /**
     * 设置是否喜欢
     */
    override fun setFavorite(b: Boolean, position: Int) {
        val item = adapter.getItem(position) as ProductBean
        if (b) {
            item.like_count += 1
        } else {
            item.like_count -= 1
        }
        item.is_like = b
        adapter.notifyItemChanged(position)
    }


    override fun installListener() {

        swipeRefreshLayout.setOnRefreshListener {
            adapter.setEnableLoadMore(false)
            presenter.loadData(true)
        }

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2GoodsDetailActivity(item.rid)
        }


        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, viewClicked, position ->
            val productsBean = adapter.getItem(position) ?:return@OnItemChildClickListener
            when (viewClicked.id) {
                R.id.imageViewDelete -> {
                    showDeleteDialog(productsBean.rid, position)
                }

                R.id.textView4 -> {
                    if (productsBean.is_like) {
                        presenter.unfavoriteGoods(productsBean.rid, position, viewClicked)
                    } else {
                        presenter.favoriteGoods(productsBean.rid, position, viewClicked)
                    }
                }
                R.id.textView5 -> {
                    val dialog = DistributeShareDialog(this)
                    dialog.show()
                }
            }
        }

        //加载更多生活馆分销商品
        adapter.setOnLoadMoreListener({
            presenter.loadMoreOwnerGoodsData()
        }, recyclerView)

    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE)
    private fun share() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            val shareUtil = ShareUtil(this)
            shareUtil.shareGoods(WebUrl.GOODS, WebUrl.AUTH_GOODS, adapter.data.get(positions!!).cover,
                    adapter.data.get(positions!!).name, "", adapter.data.get(positions!!).rid,
                    adapter.data.get(positions!!).rid + "-" + adapter.data.get(positions!!).store_rid, 4)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, *perms)
        }
    }

    /**
     * 分享商品
     */
    /*@AfterPermissionGranted(Constants.REQUEST_CODE_SHARE_GOODS)
    private fun shareGoods(productsBean: ProductBean) {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), *perms)) {
            val shareUtil: ShareUtil = ShareUtil(this)
            shareUtil.shareGoods(WebUrl.AUTH_GOODS, productsBean.rid, productsBean.rid + "-" + productsBean.store_rid, "${productsBean.commission_price}", 4)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE_GOODS, *perms)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }*/

    /**
     * 显示删除对话框
     */
    private fun showDeleteDialog(rid: String, position: Int) {
        val color333 = Util.getColor(R.color.color_333)
        val white = Util.getColor(android.R.color.white)
        val dialog = NormalDialog(this)
        dialog.isTitleShow(false)
                .bgColor(white)
                .cornerRadius(4f)
                .content(Util.getString(R.string.text_unshelve_confirm))
                .contentGravity(Gravity.CENTER)
                .contentTextColor(color333)
                .contentTextSize(14f)
                .dividerColor(Util.getColor(R.color.color_ccc))
                .btnText(Util.getString(R.string.text_cancel), Util.getString(R.string.text_qd))
                .btnTextSize(18f, 18f)
                .setRightBtnBgColor(Util.getColor(R.color.color_6ed7af))
                .btnTextColor(color333, white)
                .btnPressColor(white)
                .widthScale(0.85f)
                .show()
        dialog.setOnBtnClickL(OnBtnClickL {
            dialog.dismiss()
        }, OnBtnClickL {
            presenter.deleteDistributeGoods(rid, position)
            //删除分销商品
            adapter.remove(position)
            dialog.dismiss()
        })
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

    }

    override fun dismissLoadingView() {

    }

    override fun showError(string: String) {

    }

    override fun goPage() {

    }

    override fun setPresenter(presenter: LifeHouseContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
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