package com.lexivip.lexi.index.shopWindow

import android.content.Context
import android.content.Intent
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.discoverLifeAesthetics.*
import com.lexivip.lexi.eventBusMessge.MessageUpDown
import com.lexivip.lexi.publishShopWindow.PublishShopWindowActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_shop_window_demo.*
import org.greenrobot.eventbus.EventBus

class FragmentShopWindowDemo : BaseFragment(), ShowWindowContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    private val presenter: ShowWindowPresenter by lazy { ShowWindowPresenter(this) }
    private val adapter: AdapterRecommendShowWindow by lazy { AdapterRecommendShowWindow(R.layout.adapter_show_window) }
    override val layout: Int = R.layout.fragment_shop_window_demo

    override fun setPresenter(presenter: ShowWindowContract.Presenter?) {
        setPresenter(presenter)
    }
    private val fragment0: BaseFragment by lazy { FragmentFocusShowWindow.newInstance() }
    private val fragment1: BaseFragment by lazy { FragmentRecommendShowWindow.newInstance() }

    private val fragments: ArrayList<BaseFragment> by lazy { ArrayList<BaseFragment>() }

    override fun initView() {
        GlideUtil.loadImageWithDimenAndRadius(R.mipmap.icon_bg_header_shop_window, imageViewBg, 0, ScreenUtil.getScreenWidth(), DimenUtil.dp2px(255.0), ImageSizeConfig.DEFAULT)
        val linearLayoutManager = object : LinearLayoutManager(context) {
            //解决RecyclerView嵌套RecyclerView滑动卡顿的问题
            //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
            override fun canScrollVertically() = false
        }
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        (recyclerViewFocus.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerViewFocus.layoutManager = linearLayoutManager
        recyclerViewFocus.isNestedScrollingEnabled = false
        recyclerViewFocus.setHasFixedSize(true)
        recyclerViewFocus.adapter = adapter
        recyclerViewFocus.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
        recyclerViewFocus.isFocusable = false
    }

    companion object {
        @JvmStatic
        fun newInstance(): FragmentShopWindowDemo = FragmentShopWindowDemo()
    }


    override fun installListener() {

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (Math.abs(scrollY - oldScrollY) < 20) return@OnScrollChangeListener
            if (scrollY > oldScrollY) { //上滑
                EventBus.getDefault().post(MessageUpDown(true))
            } else {
                EventBus.getDefault().post(MessageUpDown(false))
            }

            LogUtil.e("scrollY===$scrollY")
            LogUtil.e("v.getChildAt(0).measuredHeight===${v.getChildAt(0).measuredHeight}")
            LogUtil.e("v.measuredHeight===${v.measuredHeight}")
            if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)) {
                presenter.loadMoreFocusData()
            }
        })


//        adapter.setOnLoadMoreListener({
//            presenter.loadMoreFocusData()
//        }, recyclerViewFocus)

        linearLayoutPublishWindow.setOnClickListener {
            //跳转拼接橱窗
            if (UserProfileUtil.isLogin()) {
                startActivity(Intent(activity, PublishShopWindowActivity::class.java))
            }else{
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }


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