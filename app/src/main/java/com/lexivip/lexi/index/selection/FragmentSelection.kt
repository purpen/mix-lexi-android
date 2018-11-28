package com.lexivip.lexi.index.selection

import android.content.Intent
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.basemodule.tools.*
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.*
import com.lexivip.lexi.beans.LifeWillBean
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.discoverLifeAesthetics.DiscoverLifeAestheticsActivity
import com.lexivip.lexi.index.bean.BannerImageBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.index.discover.ComposerStoryActivity
import com.lexivip.lexi.index.selection.freePostage.AllFreePostageActivity
import com.lexivip.lexi.index.selection.goodsSelection.AllGoodsSelectionActivity
import com.lexivip.lexi.receiveVoucher.ReceiveVoucherActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.view.autoScrollViewpager.RecyclerViewPagerAdapter
import com.lexivip.lexi.view.autoScrollViewpager.ScrollableView
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_selection.*
import kotlinx.android.synthetic.main.view_notice_item_view.view.*


class FragmentSelection : BaseFragment(), SelectionContract.View, View.OnClickListener {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_selection
    private val presenter: SelectionPresenter by lazy { SelectionPresenter(this) }

    private lateinit var adapterTodayRecommend: TodayRecommendAdapter

    private lateinit var adapterSelectionBanner: AdapterSelectionBanner

    private lateinit var adapterDiscoverLife: DiscoverLifeAdapter

    private lateinit var adapterGoodSelection: GoodSelectionAdapter

    private lateinit var adapterZCManifest: ZCManifestAdapter
    private var views: ArrayList<View> = ArrayList()
    private var banners: ArrayList<String> = ArrayList()
    private var adapterOnePageThreeView: OnePageThreeViewAdapter? = null

    private val bannerWidth: Int by lazy { ScreenUtil.getScreenWidth() * 300 / 375 }

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
        swipeRefreshLayout.isEnabled = false
//        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
//        swipeRefreshLayout.isRefreshing = false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (upMarqueeView != null) {
                upMarqueeView.startFlipping()
            }
        } else {
            if (upMarqueeView != null) {
                upMarqueeView.stopFlipping()
            }
        }
    }

    /**
     * 初始化种草清单
     */
    private fun initZCManifest() {
        presenter.getZCManifest()
        adapterZCManifest = ZCManifestAdapter(R.layout.adapter_zc_manifest)
        val gridLayoutManager = CustomGridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.setScrollEnabled(false)
        recyclerViewZCManifest.layoutManager = gridLayoutManager
        recyclerViewZCManifest.adapter = adapterZCManifest
        recyclerViewZCManifest.addItemDecoration(GridSpacingItemDecoration(2, DimenUtil.dp2px(10.0), DimenUtil.dp2px(20.0), false))
    }

    /**
     * 设置种草清单数据
     */
    override fun setZCManifestData(products: List<LifeWillBean>) {
        adapterZCManifest.setNewData(products)
        adapterZCManifest.notifyDataSetChanged()
    }

    /**
     * 乐喜优选
     */
    private fun initGoodSelection() {
        presenter.getGoodSelection()
        adapterGoodSelection = GoodSelectionAdapter(R.layout.adapter_editor_recommend)
        val gridLayoutManager = CustomGridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.setScrollEnabled(false)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerViewGoodSelection.setHasFixedSize(true)
        recyclerViewGoodSelection.layoutManager = gridLayoutManager
        recyclerViewGoodSelection.adapter = adapterGoodSelection
        recyclerViewGoodSelection.addItemDecoration(GridSpacingItemDecoration(2, DimenUtil.dp2px(10.0), DimenUtil.dp2px(20.0), false))
    }

    /**
     * 设置优选数据
     */
    override fun setGoodSelectionData(products: List<ProductBean>) {
        adapterGoodSelection.setNewData(products)
        adapterGoodSelection.notifyDataSetChanged()
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
    override fun setDiscoverLifeData(windows: List<ShopWindowBean>) {
        adapterDiscoverLife.setNewData(windows)
    }

    /**
     * 初始化人气推荐banner
     */
    private fun initHotRecommendBanner() {
        presenter.getHotRecommendBanner()
        val contentW = ScreenUtil.getScreenWidth() - DimenUtil.dp2px(30.0)
        hotBanner.setImageLoader(GlideImageLoader(R.dimen.dp4, contentW, DimenUtil.dp2px(135.0), ImageSizeConfig.DEFAULT))
        hotBanner.setIndicatorGravity(BannerConfig.RIGHT)
    }

    /**
     * 设置推荐数据
     */
    override fun setHotRecommendBannerData(banner_images: List<BannerImageBean>) {
        val list = ArrayList<String>()
        for (item in banner_images) {
            list.add(item.image)
        }
        hotBanner.setImages(list)
        hotBanner.start()
        hotBanner.setOnBannerListener { position ->
            PageUtil.banner2Page(banner_images[position])
        }
    }

    /**
     * 人气推荐
     */
    private fun initHotRecommend() {
        presenter.getHotRecommend()
        val manager = CustomGridLayoutManager(AppApplication.getContext(), 6)
        manager.setScrollEnabled(false)
        recyclerViewHotRecommend.layoutManager = manager

    }

    /**
     * 设置人气推荐数据
     */
    override fun setHotRecommendData(products: List<ProductBean>) {
        var pageSize = 0
        val size = products.size
        if (size % 5 == 0) {
            pageSize = size / 5
        } else {
            pageSize = size / 5 + 1
        }

        val sizeSpan2 by lazy { (ScreenUtil.getScreenWidth() - DimenUtil.dp2px(40.0)) / 2 }
        val span2Height by lazy { sizeSpan2 * 128 / 160 }
        val sizeSpan1 by lazy { (ScreenUtil.getScreenWidth() - DimenUtil.dp2px(48.0)) / 3 }
        scrollableView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, span2Height + sizeSpan1 + DimenUtil.dp2px(120.0))
        scrollableView.setAdapter(RecyclerViewPagerAdapter<ProductBean>(activity, products).setInfiniteLoop(true), pageSize)


        val llp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        llp.gravity = Gravity.CENTER_HORIZONTAL
        val vlp = ViewGroup.LayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        llp.setMargins(DimenUtil.dp2px(5.0), 0, 0, DimenUtil.dp2px(5.0))
        var imageView: ImageView
        for (i in 0 until pageSize) {
            imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.layoutParams = vlp
            if (i == currentItem) {
                imageView.setImageResource(R.drawable.bg_auto_scroll_viewpager_shape_oval_sel)
            } else {
                imageView.setImageResource(R.drawable.bg_auto_scroll_viewpager_shape_oval_unsel)
            }
            imageViews.add(imageView)
            linearLayoutIndicator.addView(imageView, llp)
        }

        scrollableView.addOnPageChangeListener(CustomOnPageChangeListener(pageSize))


        val list = ArrayList<PeopleRecommendAdapter.MultipleItem>()
        for (i in products.indices) {
            if (i == 0 || i == 1) {//占3列宽
                list.add(PeopleRecommendAdapter.MultipleItem(products[i], PeopleRecommendAdapter.MultipleItem.ITEM_TYPE_SPAN2, PeopleRecommendAdapter.MultipleItem.ITEM_SPAN3_SIZE))
            } else {//占两列
                list.add(PeopleRecommendAdapter.MultipleItem(products[i], PeopleRecommendAdapter.MultipleItem.ITEM_TYPE_SPAN3, PeopleRecommendAdapter.MultipleItem.ITEM_SPAN2_SIZE))
            }
        }

        val adapterPeopleRecommend = PeopleRecommendAdapter(list)

        adapterPeopleRecommend.setSpanSizeLookup { _, position ->
            list[position].spanSize
        }
        recyclerViewHotRecommend.adapter = adapterPeopleRecommend
        recyclerViewHotRecommend.addItemDecoration(HotPeopleGridSpaceDecoration(resources.getDimensionPixelSize(R.dimen.dp10)))

        adapterPeopleRecommend.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as PeopleRecommendAdapter.MultipleItem
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item.product)
            startActivity(intent)
        }

        adapterPeopleRecommend.notifyDataSetChanged()
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

    override fun setTodayRecommendData(products: MutableList<TodayRecommendBean.DataBean.DailyRecommendsBean>) {
        adapterTodayRecommend.setNewData(products)
    }

    /**
     * 初始化头条
     */
    private fun initNotice() {
        if (UserProfileUtil.isSmallB() || UserProfileUtil.isBigB()) {
            linearLayoutOpenLifeHouseGuide.visibility = View.GONE
        } else {
            linearLayoutOpenLifeHouseGuide.visibility = View.VISIBLE
            presenter.getHeadLine()
        }
    }


    /**
     * 设置头条数据
     */
    override fun setHeadLineData(data: MutableList<HeadLineBean.DataBean.HeadlinesBean>) {

        var size = data.size

        var i = 0
        while (i < size) {
            //设置滚动的单个布局
            val noticeView = View.inflate(activity, R.layout.view_notice_item_view, null)
            setNoticeTextViewData(data[i], noticeView.tv1)
            if (size > i + 1) {
                setNoticeTextViewData(data[i + 1], noticeView.tv2)
            } else {
                noticeView.tv2.visibility = View.GONE
            }

            views.add(noticeView)
            i += 2
        }
        upMarqueeView.setViews(views)
    }

    /**
     * 设置通知
     */
    private fun setNoticeTextViewData(bean: HeadLineBean.DataBean.HeadlinesBean, textView: TextView) {
        if (bean.username == null) bean.username = ""
        when (bean.event) {
            1 -> { //开通生活馆 人名蓝色
                val content = "${bean.username} ${bean.time}${bean.time_info}开通了自己的生活馆"
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_6ed7af)), 0, bean.username.toString().length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                textView.text = string
            }

            2 -> {//售出3单成为正式馆主
                textView.text = "${bean.username} 售出${bean.quantity}单成为正式馆主"
            }
            3 -> {//刚刚售出1单
                val content = "「${bean.username}」的生活馆 ${bean.time}${bean.time_info} 售出${bean.quantity}单"
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_f5a43c)), content.indexOf("售"), content.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                textView.text = string
            }
            4 -> {//售出单数
                val content = "「${bean.username}」的生活馆 ${bean.time}${bean.time_info} 售出${bean.quantity}单"
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Util.getColor(R.color.color_f5a43c)), content.indexOf("售"), content.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                textView.text = string
            }
        }
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

//        linearLayoutViewPager.clipChildren = false
        val width = ScreenUtil.getScreenWidth() * 320 / 375
        val height = width * 200 / 320 + DimenUtil.dp2px(35.0)
        viewPager.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
        viewPager.setPadding(0,DimenUtil.dp2px(15.0),0,DimenUtil.dp2px(20.0))
//        viewPager.clipChildren = false
//        viewPager.adapter = adapterOnePageThreeView
//        viewPager.setPageTransformer(true, OnePageThreeViewTransformer())

    }

    /**
     * 设置Banner数据
     */
    override fun setBannerData(banner_images: List<BannerImageBean>) {
        val list = ArrayList<String>()
        for (item in banner_images) {
            list.add(item.image)
        }
        banners.clear()
        banners.addAll(list)
//        viewPager.offscreenPageLimit = list.size
        val adapterOnePageThreeView = OnePageThreeViewAdapter(activity,viewPager, banner_images)
        adapterOnePageThreeView.setAutoScroll()
//        viewPager.adapter = adapterOnePageThreeView
        viewPager.pageMargin = -bannerWidth / 8 + DimenUtil.dp2px(10.0)
        viewPager.setPageTransformer(true, OnePageThreeViewTransformer())

//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageSelected(position: Int) {
//                for (i in 0 until adapterOnePageThreeView.count) {
//                    val view = viewPager.findViewWithTag<View>(i)
//                    if (i == position) {
//                        view.setPadding(DimenUtil.dp2px(15.0), 0, 0, 0)
//                    } else {
//                        view.setPadding(0, 0, 0, 0)
//                    }
//                }
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//
//            }
//        })


        adapterSelectionBanner.setNewData(list)
        val mCardScaleHelper = CardScaleHelper()
        mCardScaleHelper.currentItemPos = 0
        mCardScaleHelper.attachToRecyclerView(recyclerViewBanner)
        adapterSelectionBanner.notifyDataSetChanged()
        //banner点击
        adapterSelectionBanner.setOnItemClickListener { _, _, position ->
            PageUtil.banner2Page(banner_images[position])
        }

    }


    override fun setPresenter(presenter: SelectionContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {
        buttonOpenShop.setOnClickListener(this)
        textViewGuessPic.setOnClickListener(this)
        textViewCouponCenter.setOnClickListener(this)
        textViewExemptionMail.setOnClickListener(this)
        textViewMoreDiscoverLife.setOnClickListener(this)
        textViewMoreZCManifest.setOnClickListener(this)

        adapterDiscoverLife.setOnItemClickListener { _, _, position ->
            val item = adapterDiscoverLife.getItem(position) ?: return@setOnItemClickListener
            PageUtil.jump2ShopWindowDetailActivity(item.rid)
        }

        adapterGoodSelection.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as ProductBean
            val intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item)
            startActivity(intent)
        }

        adapterZCManifest.setOnItemClickListener { _, _, position ->
            val item = adapterZCManifest.getItem(position) ?: return@setOnItemClickListener
            item.channel_name = getString(R.string.text_zc_manifest)
            PageUtil.jump2ArticleDetailActivity(item)
        }

        //今日推荐
        adapterTodayRecommend.setOnItemClickListener { _, _, position ->
            val item = adapterTodayRecommend.getItem(position) ?: return@setOnItemClickListener
            LogUtil.e("${item.target_type}")
            when (item.target_type) {
                1, 2 -> { //1=生活志文章, 2=种草清单 3=主题
                    val bean = LifeWillBean()
                    bean.rid = item.recommend_id
                    bean.channel_name = item.recommend_label
                    PageUtil.jump2ArticleDetailActivity(bean)
                }
                3 -> { //集合详情
                    PageUtil.jump2CollectionDetailActivity(item.recommend_id)
                }
                4 -> {//商品详情
                    PageUtil.jump2GoodsDetailActivity(item.recommend_id)
                }
            }

        }

        //查看全部优选
        textViewMoreGoodSelection.setOnClickListener {
            startActivity(Intent(activity, AllGoodsSelectionActivity::class.java))
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonOpenShop -> { //开馆指引 https://h5.lexivip.com/shop/guide
                if (UserProfileUtil.isLogin()) {
                    startActivity(Intent(activity, OpenLifeHouseActivity::class.java))
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
            }
            R.id.textViewMoreZCManifest -> { //全部种草清单
                val intent = Intent(activity, ComposerStoryActivity::class.java)
                intent.putExtra(ComposerStoryActivity::class.java.simpleName, R.mipmap.icon_image_seeding)
                startActivity(intent)
            }
            R.id.textViewGuessPic -> ToastUtil.showInfo("猜图")
            R.id.textViewCouponCenter -> startActivity(Intent(activity, ReceiveVoucherActivity::class.java))
            R.id.textViewExemptionMail -> startActivity(Intent(activity, AllFreePostageActivity::class.java))
            R.id.textViewMoreDiscoverLife -> startActivity(Intent(context, DiscoverLifeAestheticsActivity::class.java))

        }
    }


    override fun loadData() {

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
        hotBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        hotBanner.stopAutoPlay()
    }

    private var currentItem = 0
    private val imageViews: ArrayList<ImageView> by lazy { ArrayList<ImageView>() }

    private inner class CustomOnPageChangeListener(size: Int) : ViewPager.OnPageChangeListener {
        private val size = size
        override fun onPageSelected(position: Int) {
            var pos = position
            pos %= size
            currentItem = pos
            setCurFocus(pos)
        }

        private fun setCurFocus(position: Int) {
            if (imageViews.size == 0) return
            for (i in 0 until size) {
                if (i == position) {
                    imageViews[i].setImageResource(R.drawable.bg_auto_scroll_viewpager_shape_oval_sel)
                } else {
                    imageViews[i].setImageResource(R.drawable.bg_auto_scroll_viewpager_shape_oval_unsel)
                }
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageScrollStateChanged(arg0: Int) {}
    }
}