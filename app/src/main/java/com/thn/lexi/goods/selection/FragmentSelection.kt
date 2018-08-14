package com.thn.lexi.goods.selection

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.goods.detail.GoodsDetailActivity
import com.thn.lexi.goods.explore.ExploreBannerBean
import kotlinx.android.synthetic.main.fragment_selection.*
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
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
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
    }

    /**
     * 初始化种草清单
     */
    private fun initZCManifest() {
        presenter.getZCManifest()
        adapterZCManifest = ZCManifestAdapter(R.layout.adapter_zc_manifest)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerViewZCManifest.layoutManager = staggeredGridLayoutManager
        recyclerViewZCManifest.adapter = adapterZCManifest
        recyclerViewZCManifest.addItemDecoration(GridSpaceDecoration(resources.getDimensionPixelSize(R.dimen.dp10),resources.getDimensionPixelSize(R.dimen.dp20)))
    }

    /**
     * 设置种草清单数据
     */
    override fun setZCManifestData(products: List<ZCManifestBean.DataBean.LifeRecordsBean>) {
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
        recyclerViewDiscoverLife.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置发现生活美学数据
     */
    override fun setDiscoverLifeData(windows: List<DiscoverLifeBean.DataBean.ShopWindowsBean>) {

         var demos = ArrayList<DiscoverLifeBean.DataBean.ShopWindowsBean>()

        for (i in 0..3){
            val windowsBean = DiscoverLifeBean.DataBean.ShopWindowsBean()
            demos.add(windowsBean)
        }

        for (item in demos){
            item.title = "发现生活美学"
            item.description = "生活美学好"
            val list = ArrayList<DiscoverLifeBean.DataBean.ShopWindowsBean.ProductsBean>()
            for (i in 0..3){
                val productsBean = DiscoverLifeBean.DataBean.ShopWindowsBean.ProductsBean()
                productsBean.cover = "http://c.hiphotos.baidu.com/image/h%3D300/sign=87d6daed02f41bd5c553eef461d881a0/f9198618367adab4b025268587d4b31c8601e47b.jpg"
                list.add(productsBean)
            }
            item.products=list
            item.avatar = "http://imgtu.5011.net/uploads/content/20170209/4934501486627131.jpg"
        }

        adapterDiscoverLife.setNewData(demos)
//        adapterDiscoverLife.setNewData(windows)
    }

    /**
     * 初始化人气推荐banner
     */
    private fun initHotRecommendBanner() {
        presenter.getHotRecommendBanner()
        hotBanner.setImageLoader(GlideImageLoader())
        hotBanner.setBannerStyle(BannerConfig.NOT_INDICATOR)
    }

    override fun setHotRecommendBannerData(banner_images: List<SelectionHotRecommendBannerBean.DataBean.BannerImagesBean>) {
        val list = ArrayList<String>()
        for (item in banner_images){
            list.add(item.image)
        }
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
        recyclerViewRecommend.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    override fun setTodayRecommendData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {
        adapterTodayRecommend.setNewData(products)
    }

    /**
     * 初始化头条
     */
    private fun initNotice() {
        presenter.getHeadLine()
    }

    /**
     * 设置头条数据
     */
    override fun setHeadLineData(data: MutableList<HeadLineBean.DataBean.HeadlinesBean>) {
        val contentPavilion = data[0].line_text
        val pavilionTime = data[0].time

        val contentOrders = data[1].line_text
        val orderCount= data[1].time

//        if (!TextUtils.isEmpty(name1)){
//            val openInfo = SpannableString("设计师${contentPavilion}10秒前开了自己的设计馆")
//            val start = 3
//            val end = start + name1.length
//            openInfo.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_6ed7af)),start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            textViewOpenInfo.text = openInfo
//        }
//
//        if (!TextUtils.isEmpty(name2) && !TextUtils.isEmpty(orderCount)){
//            val orderInfo = SpannableString("${name2}设计馆1小时售出${orderCount}单")
//            val start = name2.length + 8
//            val end = start + orderCount.length
//            orderInfo.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_f4b329)),start , end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            textViewOrderInfo.text = orderInfo
//        }

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
//                R.id.textView4 -> {
//                    val popupWindow = GoodsSpecPopupWindow(activity, item, R.layout.dialog_purchase_goods, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                    popupWindow.show()
//                }
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
        if (!swipeRefreshLayout.isRefreshing) dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
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