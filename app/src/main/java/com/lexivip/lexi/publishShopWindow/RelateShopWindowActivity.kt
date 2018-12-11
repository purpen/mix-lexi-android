package com.lexivip.lexi.publishShopWindow

import android.Manifest
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import com.basemodule.tools.Constants
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.discoverLifeAesthetics.AdapterRecommendShowWindow
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowContract
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowDetailBean
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowPresenter
import com.lexivip.lexi.index.lifehouse.DistributeShareDialog
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.shareUtil.ShareUtil
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.acticity_header_recyclerview.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class RelateShopWindowActivity:BaseActivity(), ShowWindowContract.View , EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    private val presenter: ShowWindowPresenter by lazy { ShowWindowPresenter(this) }
    private val adapter: AdapterRecommendShowWindow by lazy { AdapterRecommendShowWindow(R.layout.adapter_show_window) }
    override val layout: Int = R.layout.acticity_header_recyclerview
    private lateinit var tag:String
    private var imagrUrl:String?=null
    private var title:String?=null
    private var rid:String?=null

    override fun getIntentData() {
        tag= intent.getStringExtra(TAG)
    }

    override fun initView() {
        customHeadView.setHeadCenterTxtShow(true,R.string.title_relate_shop_window)
        swipeRefreshLayout.isEnabled = false
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
    }


    override fun requestNet() {
        presenter.loadRelateShopWIndow(tag)
    }

    override fun setNewData(shopWindows: MutableList<ShopWindowBean>) {
        adapter.setNewData(shopWindows)
    }

    override fun addData(shopWindows: MutableList<ShopWindowBean>) {
        adapter.addData(shopWindows)
    }

    override fun installListener() {
        adapter.setOnLoadMoreListener({
            presenter.loadMoreRelateShopWindow(tag)
        }, recyclerView)

        adapter.setOnItemClickListener { _, _, position ->
            val showWindowBean = adapter.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2ShopWindowDetailActivity(showWindowBean.rid)
        }

        adapter.setOnItemChildClickListener { _, view, position ->
            val showWindowBean = adapter.getItem(position) ?: return@setOnItemChildClickListener
            when (view.id) {
                R.id.textViewLike -> {
                    if (UserProfileUtil.isLogin()) {
                        presenter.favoriteShowWindow(showWindowBean.rid, showWindowBean.is_like, position, view)
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }

                R.id.textViewComment -> { //跳转评论列表
                    val dataBean = ShowWindowDetailBean.DataBean()
                    dataBean.rid = showWindowBean.rid
                    dataBean.is_like = showWindowBean.is_like
                    dataBean.like_count = showWindowBean.like_count
                    dataBean.comment_count = showWindowBean.comment_count
                    PageUtil.jump2ShopWindowCommentListActivity(dataBean)
                }

                R.id.textViewShare -> {
                    /*val dialog = DistributeShareDialog(this)
                    dialog.show()*/
                    rid=adapter.data[position].rid
                    title=adapter.data[position].title
                    imagrUrl=adapter.data[position].products[0].cover
                    share()
                }
                R.id.textViewFocus -> { //关注用户
                    if (UserProfileUtil.isLogin()) {
                        presenter.focusUser(showWindowBean.uid, view, showWindowBean.followed_status, position)
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
            }
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE)
    private fun share() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            val shareUtil = ShareUtil(this)
            shareUtil.shareWindow(WebUrl.WINDOW,WebUrl.AUTH_WINDOW,imagrUrl,title,"",rid,rid)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, *perms)
        }
    }

    /**
     * 设置用户关注状态
     */
    override fun setFocusState(followed_status: Int, position: Int) {
        val item = adapter.getItem(position) ?: return
        item.followed_status = followed_status
        adapter.notifyItemChanged(position)
        item.PAGE_TAG = TAG
        EventBus.getDefault().post(item)
    }

    /**
     * 更新喜欢状态
     */
    override fun setFavorite(b: Boolean, position: Int) {
        val item = adapter.getItem(position)?:return
        item.is_like = b
        if (b) {
            item.like_count++
        } else {
            item.like_count--
        }
        adapter.notifyItemChanged(position)
        item.PAGE_TAG = TAG
        EventBus.getDefault().post(item)
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
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
    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

    override fun setPresenter(presenter: ShowWindowContract.Presenter?) {
        setPresenter(presenter)
    }

    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(R.color.color_f5f7f9)
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter.itemCount
            var divider: Y_Divider? = null
            when (itemPosition) {
                count - 2 -> {

                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                count - 1 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                else -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                }
            }

            return divider
        }
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