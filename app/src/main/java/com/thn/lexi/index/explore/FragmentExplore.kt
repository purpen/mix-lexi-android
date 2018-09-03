package com.thn.lexi.index.explore
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.GlideImageLoader
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.brandPavilion.BrandPavilionListActivity
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.detail.GoodsDetailActivity
import com.thn.lexi.index.selection.GoodsData
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_explore.*

class FragmentExplore:BaseFragment(),ExploreContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    private val presenter: ExplorePresenter by lazy { ExplorePresenter(this) }
    override val layout: Int = R.layout.fragment_explore
    private var page: Int = 1
    private lateinit var adapterGoodsClass: GoodsClassAdapter
    private lateinit var adapterEditorRecommend: EditorRecommendAdapter
    private lateinit var adapterBrandPavilion: BrandPavilionAdapter
    private lateinit var adapterFeatureNewGoods: EditorRecommendAdapter
    private lateinit var adapterGoodsCollection: CollectionGoodsAdapter
    private lateinit var adapterGoodDesign: EditorRecommendAdapter
    private lateinit var adapterGood100: EditorRecommendAdapter

    companion object {
        @JvmStatic fun newInstance(): FragmentExplore = FragmentExplore()
    }

    override fun initView() {
        initBanner()
        initGoodsClass()
        initEditorRecommend()
        initBrandPavilion()
        initFeatureNewGoods()
        initGoodsCollection()
        initGoodDesign()
        initGood100()
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.isEnabled = false
    }

    /**
     * 初始化百元好物
     */
    private fun initGood100() {
        presenter.getGood100()
        adapterGood100 = EditorRecommendAdapter(R.layout.adapter_editor_recommend)
        val linearLayoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewGood100.setHasFixedSize(true)
        recyclerViewGood100.layoutManager = linearLayoutManager
        recyclerViewGood100.adapter = adapterGood100
        recyclerViewGood100.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))

    }

    override fun setGood100Data(products: List<ProductBean>) {
        adapterGood100.setNewData(products)
    }

    /**
     * 初始化特惠好设计
     */
    private fun initGoodDesign() {
        presenter.getGoodDesign()
        adapterGoodDesign = EditorRecommendAdapter(R.layout.adapter_editor_recommend)
        val linearLayoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewGoodDesign.setHasFixedSize(true)
        recyclerViewGoodDesign.layoutManager = linearLayoutManager
        recyclerViewGoodDesign.adapter = adapterGoodDesign
        recyclerViewGoodDesign.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    override fun setGoodDesignData(products: List<ProductBean>) {
        adapterGoodDesign.setNewData(products)
    }

    /**
     * 初始化商品集合
     */
    private fun initGoodsCollection() {
        presenter.getGoodsCollection()
        adapterGoodsCollection = CollectionGoodsAdapter(R.layout.adapter_goods_collection)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewCollection.setHasFixedSize(true)
        recyclerViewCollection.layoutManager = linearLayoutManager
        recyclerViewCollection.adapter = adapterGoodsCollection
        recyclerViewCollection.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置集合数据
     */
    override fun setGoodsCollectionData(collections: List<GoodsCollectionBean.DataBean.CollectionsBean>) {
        adapterGoodsCollection.setNewData(collections)
    }

    /**
     * 初始化优质新品
     */
    private fun initFeatureNewGoods() {
        presenter.getFeatureNewGoods()
        adapterFeatureNewGoods = EditorRecommendAdapter(R.layout.adapter_editor_recommend)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewNewGoods.setHasFixedSize(true)
        recyclerViewNewGoods.layoutManager = linearLayoutManager
        recyclerViewNewGoods.adapter = adapterFeatureNewGoods
        recyclerViewNewGoods.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))

    }

    /**
     * 设置优质新品数据
     * 与编辑推荐bean和adapter共用
     */
    override fun setFeatureNewGoodsData(products: List<ProductBean>) {
        adapterFeatureNewGoods.setNewData(products)
    }


    /**
     * 初始化品牌馆
     */
    private fun initBrandPavilion() {
        presenter.getBrandPavilion()
        adapterBrandPavilion = BrandPavilionAdapter(R.layout.adapter_brand_pavilion)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewBrand.setHasFixedSize(true)
        recyclerViewBrand.layoutManager = linearLayoutManager
        recyclerViewBrand.adapter = adapterBrandPavilion
        recyclerViewBrand.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置品牌馆数据
     */
    override fun setBrandPavilionData(stores: List<BrandPavilionListBean.DataBean.StoresBean>) {
        adapterBrandPavilion.setNewData(stores)
    }

    /**
     * 初始化编辑推荐
     */
    private fun initEditorRecommend() {
        presenter.getEditorRecommend()
        adapterEditorRecommend = EditorRecommendAdapter(R.layout.adapter_editor_recommend)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewRecommend.setHasFixedSize(true)
        recyclerViewRecommend.layoutManager = linearLayoutManager
        recyclerViewRecommend.adapter = adapterEditorRecommend
        recyclerViewRecommend.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置编辑推荐数据
     */
    override fun setEditorRecommendData(products: List<ProductBean>) {
        adapterEditorRecommend.setNewData(products)
    }

    /**
     * 初始化商品分类
     */
    private fun initGoodsClass() {
        presenter.getGoodsClass()
        adapterGoodsClass = GoodsClassAdapter(R.layout.adapter_goods_class)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView0.setHasFixedSize(true)
        recyclerView0.layoutManager = linearLayoutManager
        recyclerView0.adapter = adapterGoodsClass
        recyclerView0.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp30), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置商品分类数据
     */
    override fun setGoodsClassData(categories: List<GoodsClassBean.DataBean.CategoriesBean>) {
        adapterGoodsClass.setNewData(categories)
    }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        presenter.getBanners()
        banner.setImageLoader(GlideImageLoader(R.dimen.dp4))
        banner.setIndicatorGravity(BannerConfig.RIGHT)

    }

    /**
     * 设置Banner数据
     */
    override fun setBannerData(banner_images: List<ExploreBannerBean.DataBean.BannerImagesBean>) {
        val list = ArrayList<String>()
        for (item in banner_images){
            list.add(item.image)
        }
        banner.setImages(list)
        banner.start()
    }

    override fun setPresenter(presenter: ExploreContract.Presenter?) {
        setPresenter(presenter)
    }

    /**
     * 设置是否馆主品牌馆
     */
    override fun setBrandPavilionFocusStateData(b: Boolean, position: Int) {
        val item = adapterBrandPavilion.getItem(position) as BrandPavilionListBean.DataBean.StoresBean
        item.is_followed = b
        adapterBrandPavilion.notifyDataSetChanged()
    }


    override fun installListener() {

        textViewAllBrand.setOnClickListener {
            startActivity(Intent(activity,BrandPavilionListActivity::class.java))
        }

        banner.setOnBannerListener {
            position ->ToastUtil.showInfo("你点击了$position")
        }

        adapterBrandPavilion.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as BrandPavilionListBean.DataBean.StoresBean
            when(view.id){
                R.id.imageViewShop->ToastUtil.showInfo("去店铺")

//                R.id.imageViewGoods0,R.id.imageViewGoods1,R.id.imageViewGoods2->{
//                    //TODO 去商品详情
//                    startActivity(Intent(activity,GoodsDetailActivity::class.java))
//                }

                R.id.buttonFocus ->{
                    if (item.is_followed) {
                        presenter.unFocusBrandPavilion(item.rid,position)
                    } else {
                        presenter.focusBrandPavilion(item.rid,position)
                    }
                }
            }

        }

        adapterEditorRecommend.setOnItemClickListener { adapter, _, position ->
            jump2GoodsDetail(adapter, position)
        }


        adapterFeatureNewGoods.setOnItemClickListener { adapter, _, position ->
            jump2GoodsDetail(adapter,position)
        }

        adapterGood100.setOnItemClickListener { adapter, _, position ->
            jump2GoodsDetail(adapter,position)
        }

        adapterGoodDesign.setOnItemClickListener { adapter, _, position ->
            jump2GoodsDetail(adapter,position)
        }

//        swipeRefreshLayout.setOnRefreshListener {
//            swipeRefreshLayout.isRefreshing = true
//            adapter.setEnableLoadMore(false)
//            loadData()
//        }

//        adapter.setOnLoadMoreListener({
//            presenter.loadMoreData("", page)
//        }, recyclerView)
    }

    private fun jump2GoodsDetail(adapter: BaseQuickAdapter<Any, BaseViewHolder>, position: Int) {
        val item = adapter.getItem(position) as ProductBean
        val intent = Intent(activity, GoodsDetailActivity::class.java)
        intent.putExtra(GoodsDetailActivity::class.java.simpleName, item.rid)
        startActivity(intent)
    }


    override fun loadData() {
        page = 1
        presenter.loadData("", page)
    }

    override fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {
        swipeRefreshLayout.isRefreshing = false
//        adapter.setNewData(data)
//        adapter.setEnableLoadMore(true)
        ++page
    }

    override fun addData(products: List<GoodsData.DataBean.ProductsBean>) {
//        adapter.addData(products)
        ++page
    }

    override fun loadMoreComplete() {
//        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
//        adapter.loadMoreEnd()
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
//        adapter.isLoading
//        swipeRefreshLayout.isRefreshing = false
//        adapter.loadMoreFail()
    }
    override fun goPage() {

    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }
}