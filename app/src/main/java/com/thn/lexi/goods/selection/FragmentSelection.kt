package com.thn.lexi.goods.selection

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Gravity
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
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.view.View
import com.basemodule.tools.ToastUtil
import com.thn.lexi.GlideImageLoader
import com.thn.lexi.goods.explore.EditorRecommendBean
import com.youth.banner.BannerConfig


class FragmentSelection : BaseFragment(), SelectionContract.View, View.OnClickListener {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_selection
    private val presenter: SelectionPresenter by lazy { SelectionPresenter(this) }
    private var page: Int = 1
    private lateinit var adapter: GoodsAdapter

    private lateinit var adapterTodayRecommend: TodayRecommendAdapter

    private lateinit var adapterSelectionBanner: AdapterSelectionBanner

    private lateinit var adapterDiscoverLife: DiscoverLifeAdapter

    private lateinit var adapterGoodSelection: GoodSelectionAdapter

    private lateinit var adapterZCManifest: ZCManifestAdapter


    companion object {
        @JvmStatic
        fun newInstance(): FragmentSelection = FragmentSelection()
    }

    override fun initView() {
        initBanner()
        initNotice()
        initRecommend()
        initHotRecommend()
        initHotRecommendBanner()
        initDiscoverLife()
        initGoodSelection()
        initZCManifest()
        adapter = GoodsAdapter(R.layout.adapter_goods_layout, activity)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
    }

    /**
     * 初始化种草清单
     */
    private fun initZCManifest() {
        presenter.getZCManifest()
        adapterZCManifest = ZCManifestAdapter(R.layout.adapter_zc_manifest)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerViewZCManifest.setHasFixedSize(true)
        recyclerViewZCManifest.layoutManager = staggeredGridLayoutManager
        recyclerViewZCManifest.adapter = adapterZCManifest
        recyclerViewZCManifest.addItemDecoration(GridSpaceDecoration(resources.getDimensionPixelSize(R.dimen.dp10),resources.getDimensionPixelSize(R.dimen.dp20)))
    }

    override fun setZCManifestData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {
        adapterZCManifest.setNewData(products)
    }

    /**
     * 乐喜优选
     */
    private fun initGoodSelection() {
        presenter.getGoodSelection()
        adapterGoodSelection = GoodSelectionAdapter(R.layout.adapter_editor_recommend)
        val gridLayoutManager = GridLayoutManager(activity,2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewGoodSelection.setHasFixedSize(true)
        recyclerViewGoodSelection.layoutManager = gridLayoutManager
        recyclerViewGoodSelection.adapter = adapterGoodSelection
        recyclerViewGoodSelection.addItemDecoration(GridSpaceDecoration(resources.getDimensionPixelSize(R.dimen.dp10),resources.getDimensionPixelSize(R.dimen.dp20)))
    }

    /**
     * 设置优选数据
     */
    override fun setGoodSelectionData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {
        adapterGoodSelection.setNewData(products)
    }


    /**
     * 发现生活美学
     */
    private fun initDiscoverLife() {
        presenter.getDiscoverLife()
        adapterDiscoverLife = DiscoverLifeAdapter(R.layout.adapter_discover_life)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewDiscoverLife.setHasFixedSize(true)
        recyclerViewDiscoverLife.layoutManager = linearLayoutManager
        recyclerViewDiscoverLife.adapter = adapterDiscoverLife
//        recyclerViewDiscoverLife.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), resources.getColor(android.R.color.transparent)))
    }

    /**
     * 设置发现生活美学数据
     */
    override fun setDiscoverLifeData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {
        adapterDiscoverLife.setNewData(products)
    }

    /**
     * 初始化人气推荐banner
     */
    private fun initHotRecommendBanner() {
        presenter.getHotRecommendBanner()
        hotBanner.setImageLoader(GlideImageLoader())
        hotBanner.setBannerStyle(BannerConfig.NOT_INDICATOR)
    }

    override fun setHotRecommendBannerData(banner_images: List<String>) {
        val list = ArrayList<String>()
        list.add("https://imgsa.baidu.com/news/q%3D100/sign=25fbbeb51c3853438acf8321a311b01f/f2deb48f8c5494eee0ce42c021f5e0fe98257e7e.jpg")
        list.add("https://imgsa.baidu.com/news/q%3D100/sign=0672257b55ee3d6d24c683cb73176d41/faf2b2119313b07e9dcf66d400d7912396dd8cff.jpg")
        list.add("https://imgsa.baidu.com/news/q%3D100/sign=8b9e26e200f3d7ca0af63b76c21dbe3c/d1a20cf431adcbef65b51fdaa0af2edda2cc9f6c.jpg")
//        for (item in banner_images){
//            list.add(item.image)
//        }
        hotBanner.setImages(list)
        hotBanner.start()
    }

    /**
     * 人气推荐
     */
    private fun initHotRecommend() {
        presenter.getHotRecommend()
        val manager = GridLayoutManager(activity,6)
        recyclerViewHotRecommend.layoutManager = manager

    }

    /**
     * 设置人气推荐数据
     */
    override fun setHotRecommendData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {
        val list = ArrayList<PeopleRecommendAdapter.MultipleItem>()
        for (i in products.indices){
            if (i==0 ||i==1){//占3列宽
                list.add(PeopleRecommendAdapter.MultipleItem(products[i],PeopleRecommendAdapter.MultipleItem.ITEM_TYPE_SPAN2,PeopleRecommendAdapter.MultipleItem.ITEM_SPAN3_SIZE))
            }else{//占两列
                list.add(PeopleRecommendAdapter.MultipleItem(products[i],PeopleRecommendAdapter.MultipleItem.ITEM_TYPE_SPAN3,PeopleRecommendAdapter.MultipleItem.ITEM_SPAN2_SIZE))
            }
        }

        val multipleItemAdapter = PeopleRecommendAdapter(list)

        multipleItemAdapter.setSpanSizeLookup { gridLayoutManager, position ->
            list[position].spanSize
        }
        recyclerViewHotRecommend.adapter = multipleItemAdapter
        recyclerViewHotRecommend.addItemDecoration(HotPeopleGridSpaceDecoration(resources.getDimensionPixelSize(R.dimen.dp10)))

    }


    /**
     *  今日推荐
     */
    private fun initRecommend() {
        presenter.getTodayRecommend()
        adapterTodayRecommend = TodayRecommendAdapter(R.layout.adapter_today_recommend)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewRecommend.setHasFixedSize(true)
        recyclerViewRecommend.layoutManager = linearLayoutManager
        recyclerViewRecommend.adapter = adapterTodayRecommend
        recyclerViewRecommend.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), resources.getColor(android.R.color.transparent)))
    }

    override fun setTodayRecommendData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {
        adapterTodayRecommend.setNewData(products)
    }

    /**
     * 初始化通知
     */
    private fun initNotice() {
        val openInfo = SpannableString("设计师risky秒前开了自己的设计馆")
        openInfo.setSpan(ForegroundColorSpan(resources.getColor(R.color.color_6ed7af)), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textViewOpenInfo.text = openInfo

        val orderInfo = SpannableString("伟峰的设计馆1小时售出200单")
        orderInfo.setSpan(ForegroundColorSpan(resources.getColor(R.color.color_f4b329)), 9, orderInfo.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textViewOrderInfo.text = orderInfo
    }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        presenter.getBanners()
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewBanner.layoutManager = manager
        adapterSelectionBanner = AdapterSelectionBanner(R.layout.adapter_selection_banner)
        recyclerViewBanner.adapter = adapterSelectionBanner
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
        adapterSelectionBanner.setNewData(list)
        val mCardScaleHelper = CardScaleHelper()
        mCardScaleHelper.currentItemPos = 0
        mCardScaleHelper.attachToRecyclerView(recyclerViewBanner)
    }

    override fun setPresenter(presenter: SelectionContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {

        textViewGuessPic.setOnClickListener(this)
        textViewCouponCenter.setOnClickListener(this)
        textViewExemptionMail.setOnClickListener(this)

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

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.textViewGuessPic -> ToastUtil.showInfo("猜图")
            R.id.textViewCouponCenter -> ToastUtil.showInfo("领券中心")
            R.id.textViewExemptionMail -> ToastUtil.showInfo("包邮专区")
        }
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
        swipeRefreshLayout.isRefreshing = false
        adapter.loadMoreFail()
    }

    override fun goPage() {

    }

    override fun onStart() {
        super.onStart()
        hotBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        hotBanner.stopAutoPlay()
    }
}