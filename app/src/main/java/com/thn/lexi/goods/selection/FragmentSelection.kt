package com.thn.lexi.goods.selection

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.goods.detail.GoodsDetailActivity
import com.thn.lexi.goods.explore.ExploreBannerBean
import com.thn.lexi.view.CenterShareView
import kotlinx.android.synthetic.main.fragment_selection.*

class FragmentSelection : BaseFragment(), SelectionContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_selection
    private val presenter: SelectionPresenter by lazy { SelectionPresenter(this) }
    private var page: Int = 1
    private lateinit var adapter: GoodsAdapter
    private lateinit var adapterViewPager: ViewPagerAdapter

    companion object {
        @JvmStatic
        fun newInstance(): FragmentSelection = FragmentSelection()
    }

    override fun initView() {
        initBanner()
        adapter = GoodsAdapter(R.layout.adapter_goods_layout, activity)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.VERTICAL, resources.getDimensionPixelSize(R.dimen.dp10), resources.getColor(R.color.color_d1d1d1)))
    }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        presenter.getBanners()
        viewPager.offscreenPageLimit = 3
        viewPager.setPageTransformer(false, GallaryTransformer())
    }

    /**
     * 设置Banner数据
     */
    override fun setBannerData(banner_images: List<ExploreBannerBean.DataBean.BannerImagesBean>) {
        val list = ArrayList<String>()
        list.add("https://imgsa.baidu.com/news/q%3D100/sign=25fbbeb51c3853438acf8321a311b01f/f2deb48f8c5494eee0ce42c021f5e0fe98257e7e.jpg")
        list.add("https://imgsa.baidu.com/news/q%3D100/sign=0672257b55ee3d6d24c683cb73176d41/faf2b2119313b07e9dcf66d400d7912396dd8cff.jpg")
        list.add("https://imgsa.baidu.com/news/q%3D100/sign=8b9e26e200f3d7ca0af63b76c21dbe3c/d1a20cf431adcbef65b51fdaa0af2edda2cc9f6c.jpg")
        for (item in banner_images) {
            list.add(item.image)
        }
        adapterViewPager = ViewPagerAdapter(list)
        viewPager.adapter = adapterViewPager
    }

    override fun setPresenter(presenter: SelectionContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {
//        relativeLayout.setOnTouchListener(object :View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                return viewPager.dispatchTouchEvent(event)
//            }
//        })

        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as GoodsData.DataBean.ProductsBean
            when (view.id) {

                R.id.textView3 -> {
                    if (item.isFavorite) {
                        presenter.unfavoriteGoods(item.rid, position)
                    } else {
                        presenter.favoriteGoods(item.rid, position)
                    }
                }

                R.id.textView4 -> {
                    val popupWindow = GoodsSpecPopupWindow(activity, item, R.layout.dialog_purchase_goods, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    popupWindow.show()
                }
                R.id.textView5 -> {
                    val dialog = DialogPlus.newDialog(context)
                            .setContentHolder(ViewHolder(CenterShareView(context)))
                            .setGravity(Gravity.CENTER)
                            .create()
                    dialog.show()
                }
            }

        }


        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as GoodsData.DataBean.ProductsBean
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item.rid)
            startActivity(intent)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            loadData()
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData("", page)
        }, recyclerView)
    }

    /**
     * 设置喜欢状态
     */
    override fun setFavorite(b: Boolean, position: Int) {
        val item = adapter.getItem(position) as GoodsData.DataBean.ProductsBean
        item.isFavorite = b
        adapter.notifyDataSetChanged()
    }

    override fun loadData() {
        page = 1
        presenter.loadData("", page)
    }

    override fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
        ++page
    }

    override fun addData(products: List<GoodsData.DataBean.ProductsBean>) {
        adapter.addData(products)
        ++page
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun showLoadingView() {
        if (!swipeRefreshLayout.isRefreshing) dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun showError(string: String) {
        adapter.isLoading
        swipeRefreshLayout.isRefreshing = false
        adapter.loadMoreFail()
    }

    override fun goPage() {

    }
}