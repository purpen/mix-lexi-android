package com.lexivip.lexi.index.explore

import android.content.Intent
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.*
import com.lexivip.lexi.brandPavilion.BrandPavilionListActivity
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.brandHouse.BrandHouseActivity
import com.lexivip.lexi.eventBusMessge.MessageUpDown
import com.lexivip.lexi.index.bean.BannerImageBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.index.explore.collection.CollectionListActivity
import com.lexivip.lexi.index.explore.editorRecommend.AllEditorRecommendActivity
import com.lexivip.lexi.index.explore.editorRecommend.EditorRecommendAdapter
import com.lexivip.lexi.index.explore.goodDesign.AllGoodDesignActivity
import com.lexivip.lexi.index.explore.goodsClassify.GoodsClassifyActivity
import com.lexivip.lexi.index.explore.goodsIn100.AllGoodsIn100Activity
import com.lexivip.lexi.index.explore.newGoods.AllNewGoodsActivity
import com.lexivip.lexi.index.selection.GoodsData
import com.lexivip.lexi.user.login.UserProfileUtil
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_explore.*
import org.greenrobot.eventbus.EventBus

class FragmentExplore : BaseFragment(), ExploreContract.View {
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
    private var isFirstLoad = true

    companion object {
        @JvmStatic
        fun newInstance(): FragmentExplore = FragmentExplore()
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
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.isEnabled = false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && isFirstLoad) {
            isFirstLoad = false
            page = 1
            presenter.loadData("", page)
            presenter.getBanners()
            presenter.getGoodsClass()
            presenter.getGood100()
            presenter.getGoodDesign()
            presenter.getGoodsCollection()
            presenter.getFeatureNewGoods()
            presenter.getBrandPavilion()
            presenter.getEditorRecommend()
            if (banner != null) banner.startAutoPlay()
        } else {
            if (banner != null) banner.stopAutoPlay()
        }
        super.setUserVisibleHint(isVisibleToUser)
    }


    /**
     * 初始化百元好物
     */
    private fun initGood100() {
        adapterGood100 = EditorRecommendAdapter(R.layout.adapter_editor_recommend)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
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

        adapterGoodDesign = EditorRecommendAdapter(R.layout.adapter_editor_recommend)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
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
        adapterBrandPavilion = BrandPavilionAdapter(R.layout.adapter_brand_pavilion)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewBrand.setHasFixedSize(true)
        recyclerViewBrand.layoutManager = linearLayoutManager
        recyclerViewBrand.adapter = adapterBrandPavilion
        (recyclerViewBrand.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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
        val contentW = ScreenUtil.getScreenWidth() - DimenUtil.dp2px(30.0)
        banner.setImageLoader(GlideImageLoader(R.dimen.dp4, contentW, DimenUtil.dp2px(112.0), ImageSizeConfig.DEFAULT))
        banner.setIndicatorGravity(BannerConfig.RIGHT)
    }


    /**
     * 设置Banner数据
     */
    override fun setBannerData(banner_images: List<BannerImageBean>) {
        val list = ArrayList<String>()
        for (item in banner_images) {
            list.add(item.image)
        }
        banner.setImages(list)
        banner.start()

        banner.setOnBannerListener { position ->
            PageUtil.banner2Page(banner_images[position])
        }
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
        adapterBrandPavilion.notifyItemChanged(position)
    }


    override fun installListener() {

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (Math.abs(scrollY - oldScrollY) < 20) return@OnScrollChangeListener
            if (scrollY > oldScrollY) { //上滑
                EventBus.getDefault().post(MessageUpDown(true))
            } else {
                EventBus.getDefault().post(MessageUpDown(false))
            }
        })

        //全部编辑推荐
        textViewAllRecommend.setOnClickListener {
            startActivity(Intent(activity, AllEditorRecommendActivity::class.java))
        }

        //全部特色品牌馆
        textViewAllBrand.setOnClickListener {
            startActivity(Intent(activity, BrandPavilionListActivity::class.java))
        }

        //优质新品
        textViewAllNewGoods.setOnClickListener {
            startActivity(Intent(activity, AllNewGoodsActivity::class.java))
        }

        //全部集合
        textViewCollection.setOnClickListener {
            startActivity(Intent(activity, CollectionListActivity::class.java))
        }

        //特惠好设计
        textViewGoodDesign.setOnClickListener {
            startActivity(Intent(activity, AllGoodDesignActivity::class.java))
        }

        //百元好物
        textViewGood100.setOnClickListener {
            startActivity(Intent(activity, AllGoodsIn100Activity::class.java))
        }


        //商品分类
        adapterGoodsClass.setOnItemClickListener { _, _, position ->
            val item = adapterGoodsClass.getItem(position)
            val intent = Intent(activity, GoodsClassifyActivity::class.java)
            intent.putExtra(GoodsClassifyActivity::class.java.simpleName, item)
            startActivity(intent)
        }

        //去品牌馆详情
        adapterBrandPavilion.setOnItemClickListener { _, _, position ->
            val item = adapterBrandPavilion.getItem(position) as BrandPavilionListBean.DataBean.StoresBean
            val intent = Intent(activity, BrandHouseActivity::class.java)
            intent.putExtra("rid", item.rid)
            startActivity(intent)
        }


        adapterBrandPavilion.setOnItemChildClickListener { _, view, position ->
            val item = adapterBrandPavilion.getItem(position) as BrandPavilionListBean.DataBean.StoresBean
            when (view.id) {
                R.id.textViewFocus -> { //关注品牌馆
                    if (UserProfileUtil.isLogin()){
                        if (item.is_followed) {
                            presenter.unFocusBrandPavilion(item.rid, position)
                        } else {
                            presenter.focusBrandPavilion(item.rid, position)
                        }
                    }else{
                        PageUtil.jump2LoginActivity()
                    }
                }
            }
        }

        //好货合集
        adapterGoodsCollection.setOnItemClickListener { _, _, position ->
            val item = adapterGoodsCollection.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2CollectionDetailActivity(item.id)


        }

        adapterEditorRecommend.setOnItemClickListener { adapter, _, position ->
            jump2GoodsDetail(adapter, position)
        }


        adapterFeatureNewGoods.setOnItemClickListener { adapter, _, position ->
            jump2GoodsDetail(adapter, position)
        }

        adapterGood100.setOnItemClickListener { adapter, _, position ->
            jump2GoodsDetail(adapter, position)
        }

        adapterGoodDesign.setOnItemClickListener { adapter, _, position ->
            jump2GoodsDetail(adapter, position)
        }

    }

    private fun jump2GoodsDetail(adapter: BaseQuickAdapter<Any, BaseViewHolder>, position: Int) {
        val item = adapter.getItem(position) as ProductBean
        val intent = Intent(activity, GoodsDetailActivity::class.java)
        intent.putExtra(GoodsDetailActivity::class.java.simpleName, item)
        startActivity(intent)
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
        ToastUtil.showError(string)
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