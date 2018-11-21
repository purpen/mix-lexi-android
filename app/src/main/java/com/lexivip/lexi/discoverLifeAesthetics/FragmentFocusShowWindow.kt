package com.lexivip.lexi.discoverLifeAesthetics

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.text.TextUtils
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.index.lifehouse.DistributeShareDialog
import com.lexivip.lexi.publishShopWindow.PublishShopWindowActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import kotlinx.android.synthetic.main.fragment_swipe_refresh_recyclerview.*
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class FragmentFocusShowWindow : BaseFragment(), ShowWindowContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_swipe_refresh_recyclerview
    private val presenter: ShowWindowPresenter by lazy { ShowWindowPresenter(this) }
    private val adapter: AdapterRecommendShowWindow by lazy { AdapterRecommendShowWindow(R.layout.adapter_show_window) }

    companion object {
        @JvmStatic
        fun newInstance(): FragmentFocusShowWindow = FragmentFocusShowWindow()
    }

    override fun setPresenter(presenter: ShowWindowContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
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

    override fun installListener() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadFocusData(true)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreFocusData()
        }, recyclerView)


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
                    val dialog = DistributeShareDialog(activity)
                    dialog.show()
                }
                R.id.textViewFocus -> { //关注用户
                    if (UserProfileUtil.isLogin()) {
                        presenter.focusUser(showWindowBean.uid, view, showWindowBean.is_follow, position)
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

    /**
     * 设置用户关注状态
     */
    override fun setFocusState(isFollowed: Boolean, position: Int) {
        val item = adapter.getItem(position) ?: return
        item.is_follow = isFollowed
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
        for (item in data){
            if (TextUtils.equals(item.rid,message.rid)){
                item.is_follow = message.is_follow
                item.is_expert = message.is_expert
                item.is_official = message.is_official
                item.like_count = message.like_count
                item.is_like = message.is_like
                item.comment_count = message.comment_count
                adapter.notifyDataSetChanged()
                break
            }
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
        dialog.show()
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
        dialog.dismiss()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun goPage() {

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

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}