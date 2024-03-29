package com.lexivip.lexi.index.selection

import android.content.Intent
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
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
import com.lexivip.lexi.dialog.CouponDialog
import com.lexivip.lexi.dialog.CouponFinishDialog
import com.lexivip.lexi.eventBusMessge.MessageUpDown
import com.lexivip.lexi.index.bean.BannerImageBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.index.discover.ComposerStoryActivity
import com.lexivip.lexi.index.selection.freePostage.AllFreePostageActivity
import com.lexivip.lexi.index.selection.goodsSelection.AllGoodsSelectionActivity
import com.lexivip.lexi.receiveVoucher.ReceiveVoucherActivity
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.lexivip.lexi.view.autoScrollViewpager.RecyclerViewPagerAdapter
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_selection.*
import kotlinx.android.synthetic.main.view_notice_item_view.view.*
import org.greenrobot.eventbus.EventBus


class FragmentSelection : BaseFragment(), SelectionContract.View, View.OnClickListener {
    override val layout: Int = R.layout.fragment_selection
    private val presenter: SelectionPresenter by lazy { SelectionPresenter(this) }

    private lateinit var adapterTodayRecommend: TodayRecommendAdapter

    private lateinit var adapterDiscoverLife: DiscoverLifeAdapter

    private lateinit var adapterGoodSelection: GoodSelectionAdapter

    private lateinit var adapterZCManifest: ZCManifestAdapter
    private var views: ArrayList<View> = ArrayList()
    private var banners: ArrayList<String> = ArrayList()

    private val bannerWidth: Int by lazy { ScreenUtil.getScreenWidth() * 300 / 375 }
    private var couponDialog: CouponDialog? = null

    companion object {
        @JvmStatic
        fun newInstance(): FragmentSelection = FragmentSelection()
    }

    override fun initView() {
        loadingView.setOffsetTop(DimenUtil.dp2px(103.0))
        initBanner(false)
        initNotice()
        initRecommend(false)
        initHotRecommend(false)
        initHotRecommendBanner(false)
        initDiscoverLife(false)
        initGoodSelection(false)
        initZCManifest(false)
        if (UserProfileUtil.isLogin()) {
            presenter.getReceive()
        } else {
            setIsReceive(0)
        }
        textViewCouponCenter.setCompoundDrawables(null, Util.getDrawableWidthPxDimen(R.mipmap.icon_coupon_center, DimenUtil.dp2px(26.0), DimenUtil.dp2px(26.0)), null, null)
        textViewExemptionMail.setCompoundDrawables(null, Util.getDrawableWidthPxDimen(R.mipmap.icon_exemption_mail, DimenUtil.dp2px(26.0), DimenUtil.dp2px(26.0)), null, null)
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
     * 是否领取优惠券
     */
    override fun setIsReceive(is_grant: Int) {
        LogUtil.e("是否领取：" + is_grant)
        if (is_grant == 0) {
            couponDialog = CouponDialog(context, object : CouponDialog.CouponInterface {
                override fun getReceive(isReceive: Boolean) {
                    if (isReceive) {
                        val finishDialog: CouponFinishDialog = CouponFinishDialog(context)
                        finishDialog.show()
                    }
                }
            })
            couponDialog!!.show()
        } else {
            LogUtil.e("不显示")
            if (couponDialog != null) {
                LogUtil.e("dialog是否显示")
                couponDialog!!.dismiss()
            }
        }
    }

    /**
     * 初始化种草清单
     */
    private fun initZCManifest(isRefresh: Boolean) {
        presenter.getZCManifest()
        if (isRefresh) return
        adapterZCManifest = ZCManifestAdapter(R.layout.adapter_zc_manifest)
        val gridLayoutManager = CustomGridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.setScrollEnabled(false)
        recyclerViewZCManifest.layoutManager = gridLayoutManager
        recyclerViewZCManifest.adapter = adapterZCManifest
        recyclerViewZCManifest.isNestedScrollingEnabled = false
        recyclerViewZCManifest.setHasFixedSize(true)
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
    private fun initGoodSelection(isRefresh: Boolean) {
        presenter.getGoodSelection()
        if (isRefresh) return
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
    private fun initDiscoverLife(isRefresh: Boolean) {
        presenter.getDiscoverLife()
        if (isRefresh) return
        adapterDiscoverLife = DiscoverLifeAdapter(R.layout.adapter_discover_life)
        val linearLayoutManager = CustomLinearLayoutManager(AppApplication.getContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.setScrollEnabled(false)
        recyclerViewDiscoverLife.isNestedScrollingEnabled = false
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
    private fun initHotRecommendBanner(isRefresh: Boolean) {
        presenter.getHotRecommendBanner()
        if (isRefresh) return
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
        hotBanner.setOnBannerListener { position ->
            PageUtil.banner2Page(banner_images[position])
        }
        hotBanner.start()
    }

    /**
     * 人气推荐
     */
    private fun initHotRecommend(isRefresh: Boolean) {
        presenter.getHotRecommend(isRefresh)
        if (isRefresh) return
        val manager = CustomGridLayoutManager(AppApplication.getContext(), 6)
        manager.setScrollEnabled(false)
        recyclerViewHotRecommend.isNestedScrollingEnabled = false
        recyclerViewHotRecommend.layoutManager = manager
        recyclerViewHotRecommend.isNestedScrollingEnabled = false
        recyclerViewHotRecommend.setHasFixedSize(true)
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

        linearLayoutIndicator.removeAllViews()
        imageViews.clear()

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
    private fun initRecommend(isRefresh: Boolean) {
        presenter.getTodayRecommend(isRefresh)
        if (isRefresh) return
        adapterTodayRecommend = TodayRecommendAdapter(R.layout.adapter_today_recommend)
        val linearLayoutManager = CustomLinearLayoutManager(AppApplication.getContext())
        linearLayoutManager.setScrollEnabled(false)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewRecommend.setHasFixedSize(true)
        recyclerViewRecommend.isNestedScrollingEnabled = false
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
            presenter.getHeadLine()
            linearLayoutOpenLifeHouseGuide.visibility = View.VISIBLE
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
    private fun initBanner(isRefresh: Boolean) {
        presenter.getBanners()
        if (isRefresh) return
        val height = ScreenUtil.getScreenWidth() * 200 / 375 + DimenUtil.dp2px(35.0)
        viewPager.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
        viewPager.setPadding(0, DimenUtil.dp2px(15.0), 0, DimenUtil.dp2px(20.0))
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
        val adapterOnePageThreeView = OnePageTwoViewAdapter(activity, viewPager, banner_images, true)
        viewPager.adapter = adapterOnePageThreeView
        viewPager.offscreenPageLimit = banner_images.size
        viewPager.startAutoScroll()
        viewPager.setAutoScrollDurationFactor(4.0)
        viewPager.pageMargin = -bannerWidth / 8
        viewPager.setPageTransformer(true, OnePageTwoViewTransformer())
    }


    override fun setPresenter(presenter: SelectionContract.Presenter?) {
        setPresenter(presenter)
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

        refreshLayout.setRefreshHeader(CustomRefreshHeader(AppApplication.getContext()))
        refreshLayout.setEnableOverScrollDrag(false)
        refreshLayout.isEnableLoadMore = false
        refreshLayout.setOnRefreshListener {
            initBanner(true)
            initNotice()
            initRecommend(true)
            initHotRecommend(true)
            initHotRecommendBanner(true)
            initDiscoverLife(true)
            initGoodSelection(true)
            initZCManifest(true)
            refreshLayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
        }

        refreshLayout.setScrollBoundaryDecider(object : ScrollBoundaryDecider {
            override fun canRefresh(content: View?): Boolean {
                if (nestedScrollView.scrollY > 0) return false
                return true
            }

            override fun canLoadMore(content: View?): Boolean {
                return false
            }
        })

        buttonOpenShop.setOnClickListener(this)
        textViewCustomMade.setOnClickListener(this)
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
                    PageUtil.jump2OpenLifeHouseActivity("https://h5.lexivip.com/shop/guide", R.string.title_open_life_house)
                } else {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
            }
            R.id.textViewMoreZCManifest -> { //全部种草清单
                val intent = Intent(activity, ComposerStoryActivity::class.java)
                intent.putExtra(ComposerStoryActivity::class.java.simpleName, R.mipmap.icon_image_seeding)
                startActivity(intent)
            }
            R.id.textViewCustomMade -> startActivity(Intent(activity, AllCustomMadeActivity::class.java))
            R.id.textViewCouponCenter -> startActivity(Intent(activity, ReceiveVoucherActivity::class.java))
            R.id.textViewExemptionMail -> startActivity(Intent(activity, AllFreePostageActivity::class.java))
//            R.id.textViewMoreDiscoverLife -> startActivity(Intent(context, DiscoverLifeAestheticsActivity::class.java))

        }
    }


    override fun loadData() {

    }


    override fun showLoadingView() {
        loadingView.show()
    }

    override fun dismissLoadingView() {
        loadingView.dismiss()
    }

    override fun showError(string: String) {
        LogUtil.e(string)
//        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

    override fun onStart() {
        viewPager.startAutoScroll()
        super.onStart()
        hotBanner.startAutoPlay()
    }

    override fun onStop() {
        viewPager.stopAutoScroll()
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