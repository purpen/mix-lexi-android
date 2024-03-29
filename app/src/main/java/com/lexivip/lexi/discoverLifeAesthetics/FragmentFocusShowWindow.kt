package com.lexivip.lexi.discoverLifeAesthetics

import android.Manifest
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.eventBusMessge.MessageUpDown
import com.lexivip.lexi.net.WebUrl
import com.lexivip.lexi.publishShopWindow.PublishShopWindowActivity
import com.lexivip.lexi.shareUtil.ShareUtil
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.fragment_swipe_refresh_recyclerview.*
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class FragmentFocusShowWindow : BaseFragment(), ShowWindowContract.View , EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{
    override val layout: Int = R.layout.fragment_swipe_refresh_recyclerview
    private val presenter: ShowWindowPresenter by lazy { ShowWindowPresenter(this) }
    private val adapter: AdapterRecommendShowWindow by lazy { AdapterRecommendShowWindow(R.layout.adapter_show_window) }
    private var imagrUrl:String?=null
    private var title:String?=null
    private var rid:String?=null
    companion object {
        @JvmStatic
        fun newInstance(): FragmentFocusShowWindow = FragmentFocusShowWindow()
    }

    override fun setPresenter(presenter: ShowWindowContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
        loadingView.visibility = View.VISIBLE
        loadingView.setOffsetTop((ScreenUtil.getScreenHeight() - DimenUtil.dp2px(64.0)) / 2 - DimenUtil.dp2px(10.0))
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        EventBus.getDefault().register(this)
        swipeRefreshLayout.isEnabled = false
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
    }

    /**
     * 刷新数据
     */
    fun refreshData(){
        adapter.setEnableLoadMore(false)
        presenter.loadFocusData(true)
    }

    override fun installListener() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadFocusData(true)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreFocusData()
        }, recyclerView)


        //添加监听
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_SETTLING || recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) return
                if (Math.abs(dy) < 20) return
                if (dy > 0) {
                    EventBus.getDefault().post(MessageUpDown(true))
                } else {
                    EventBus.getDefault().post(MessageUpDown(false))
                }
            }
        })

        adapter.setOnItemChildClickListener { _, view, position ->
            val showWindowBean = adapter.getItem(position) ?: return@setOnItemChildClickListener
            when (view.id) {
                R.id.textViewLike -> {
                    if (UserProfileUtil.isLogin()) {
                        presenter.favoriteShowWindow(showWindowBean.rid, showWindowBean.is_like, position, view)
                    } else {
                        startActivity(Intent(activity, LoginActivity::class.java))
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
                    /*val dialog = DistributeShareDialog(activity)
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
                        startActivity(Intent(activity, LoginActivity::class.java))
                    }
                }
//                R.id.imageView30 -> {
//                    val bean = showWindowBean.products[0] ?: return@setOnItemChildClickListener
//                    PageUtil.jump2GoodsDetailActivity(bean.rid)
//                }
//                R.id.imageView31 -> {
//                    val bean = showWindowBean.products[1] ?: return@setOnItemChildClickListener
//                    PageUtil.jump2GoodsDetailActivity(bean.rid)
//                }
//                R.id.imageView32 -> {
//                    val bean = showWindowBean.products[2] ?: return@setOnItemChildClickListener
//                    PageUtil.jump2GoodsDetailActivity(bean.rid)
//                }
//                R.id.textView -> { //点+号
//                    PageUtil.jump2ShopWindowDetailActivity(showWindowBean.rid)
//                }
            }
        }

        adapter.setOnItemClickListener { _, _, position ->
            val showWindowBean = adapter.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2ShopWindowDetailActivity(showWindowBean.rid)
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_SHARE)
    private fun share() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(AppApplication.getContext(), *perms)) {
            val shareUtil = ShareUtil(activity)
            shareUtil.shareWindow(WebUrl.WINDOW, WebUrl.AUTH_WINDOW,imagrUrl,title,"",rid,rid)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo), Constants.REQUEST_CODE_SHARE, *perms)
        }
    }

    /**
     * 设置用户关注状态
     */
    override fun setFocusState(followed_status:Int, position: Int) {
        val item = adapter.getItem(position) ?: return
        item.followed_status = followed_status
        adapter.notifyItemChanged(position)
        item.PAGE_TAG = TAG
        EventBus.getDefault().post(item)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onOuterPageShopWindow(message:ShopWindowBean) {
        if (TextUtils.equals(PublishShopWindowActivity::class.java.simpleName,message.PAGE_TAG)){
            adapter.addData(0,message)
            return
        }

        //就是自己
        if (TextUtils.equals(TAG,message.PAGE_TAG)) return
        val data = adapter.data
        var isContain = false
        for (item in data){
            if (TextUtils.equals(item.rid,message.rid)){
                isContain = true
                item.followed_status = message.followed_status
                item.is_expert = message.is_expert
                item.is_official = message.is_official
                item.like_count = message.like_count
                item.is_like = message.is_like
                item.comment_count = message.comment_count
                adapter.notifyDataSetChanged()
                break
            }
        }

        if (!isContain){ //列表不包含
            adapter.addData(0,message)
            adapter.notifyDataSetChanged()
        }
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

    override fun setNewData(shopWindows: MutableList<ShopWindowBean>) {
        adapter.setNewData(shopWindows)
    }

    override fun addData(shopWindows: MutableList<ShopWindowBean>) {
        adapter.addData(shopWindows)
        adapter.setEnableLoadMore(true)
    }

    override fun loadData() {
        presenter.loadFocusData(false)
    }

    override fun showLoadingView() {
        loadingView.show()
    }

    override fun loadMoreFail() {
        adapter.loadMoreFail()
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun dismissLoadingView() {
        loadingView.dismiss()
    }

    override fun showError(string: String) {
        loadingView.showError()
    }

    override fun goPage() {

    }

    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(R.color.color_f5f7f9)
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter.itemCount
            val divider: Y_Divider?
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

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
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