package com.thn.lexi.discoverLifeAesthetics

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import kotlinx.android.synthetic.main.fragment_swipe_refresh_recyclerview.*
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration


class FragmentRecommendShowWindow : BaseFragment(), ShowWindowContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_swipe_refresh_recyclerview
    private val presenter: ShowWindowPresenter by lazy { ShowWindowPresenter(this) }
    private val adapter: AdapterRecommendShowWindow by lazy { AdapterRecommendShowWindow(R.layout.adapter_show_window) }
    companion object {
        @JvmStatic
        fun newInstance(): FragmentRecommendShowWindow = FragmentRecommendShowWindow()
    }

    override fun setPresenter(presenter: ShowWindowContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
//        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
    }

    override fun installListener() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

        adapter.setOnItemChildClickListener { adapter, view, position ->
            //            val productsBean = adapter.getItem(position) as HotGoodsBean.DataBean.ProductsBean
            when (view.id) {
                R.id.textView4 -> ToastUtil.showInfo("卖")
                R.id.textView5 -> ToastUtil.showInfo("上架")
//                R.id.linearLayoutLoadMore -> presenter.loadMoreData(page)
            }
        }
    }

    override fun setNewData(shopWindows: MutableList<ShowWindowBean.DataBean.ShopWindowsBean>) {
        swipeRefreshLayout.isRefreshing = false
        var demos = ArrayList<ShowWindowBean.DataBean.ShopWindowsBean>()

        for (i in 0..3) {
            val windowsBean = ShowWindowBean.DataBean.ShopWindowsBean()
            demos.add(windowsBean)
        }

        for (item in demos) {
            item.title = "标题发现生活美学"
            item.description = "生活美学好哈哈哈哈丰厚的回访电话是否会对生活美学好哈哈哈哈丰厚的回访电话是否会对生活美学好哈哈哈哈丰厚的回访电话是否会对生活美学好哈哈哈哈丰厚的回访电话是否会对生活美学好哈哈哈哈丰厚的回访电话是否会对"
            val list = ArrayList<ShowWindowBean.DataBean.ShopWindowsBean.ProductsBean>()
            for (i in 0..3) {
                val productsBean = ShowWindowBean.DataBean.ShopWindowsBean.ProductsBean()
                productsBean.cover = "http://c.hiphotos.baidu.com/image/h%3D300/sign=87d6daed02f41bd5c553eef461d881a0/f9198618367adab4b025268587d4b31c8601e47b.jpg"
                list.add(productsBean)
            }
            item.products = list
            item.user_avatar = "http://imgtu.5011.net/uploads/content/20170209/4934501486627131.jpg"
        }

        adapter.setNewData(demos)

//        adapter.setNewData(shopWindows)
    }

    override fun addData(shopWindows: MutableList<ShowWindowBean.DataBean.ShopWindowsBean>) {
        adapter.addData(shopWindows)
        adapter.setEnableLoadMore(true)
    }

    override fun loadData() {
        presenter.loadData(false)
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

    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color:Int = Util.getColor(R.color.color_f5f7f9)
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
}