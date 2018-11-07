package com.lexivip.lexi.index.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import android.net.http.SslError
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.index.selection.HeadImageAdapter
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.header_goods_detail.*
import android.widget.TextView
import com.basemodule.tools.*
import com.lexivip.lexi.*
import com.lexivip.lexi.beans.*
import com.lexivip.lexi.brandHouse.BrandHouseActivity
import com.lexivip.lexi.mine.designPavilion.DesignPavilionProductAdapter
import com.lexivip.lexi.user.login.LoginActivity
import com.lexivip.lexi.user.login.UserProfileUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.header_goods_detail.view.*
import kotlinx.android.synthetic.main.view_goods_description.view.*
import kotlinx.android.synthetic.main.view_goods_shop.view.*
import kotlinx.android.synthetic.main.view_similar_goods.view.*
import org.greenrobot.eventbus.EventBus


class GoodsDetailActivity : BaseActivity(), GoodsDetailContract.View, View.OnClickListener {
    private val showTagCount: Int = 5
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private lateinit var presenter: GoodsDetailPresenter

    private lateinit var product: ProductBean

    override val layout: Int = R.layout.activity_goods_detail

    private var labels: MutableList<String>? = null

    private lateinit var adapter: AdapterGoodsDetail

    private lateinit var listDescription: ArrayList<AdapterGoodsDetail.MultipleItem>

    private lateinit var headerView: View

    private lateinit var webView: WebView

    private lateinit var couponList: ArrayList<CouponBean>

    private lateinit var productId: String

    private var lookGoodsAllDetailDialog: LookGoodsAllDetailDialog? = null

    //商品详情数据
    private var goodsData: GoodsAllDetailBean.DataBean? = null

    //品牌馆数据
    private var brandPavilionData: BrandPavilionBean? = null

    override fun getIntentData() {
        product = intent.extras.getParcelable(TAG)
        productId = product.rid
    }

    override fun initView() {

        listDescription = ArrayList()

        couponList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        adapter = AdapterGoodsDetail(listDescription)
        recyclerView.adapter = adapter
        adapter.setNewData(listDescription)
        val footerView = View(this)
        footerView.setBackgroundColor(Util.getColor(android.R.color.white))
        footerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp20))
        adapter.addFooterView(footerView)
        headerView = LayoutInflater.from(this).inflate(R.layout.header_goods_detail, null)

        adapter.addHeaderView(headerView)

        adapter.setHeaderFooterEmpty(true, true)

        headerView.banner.setImageLoader(GlideImageLoader(R.dimen.dp0, ScreenUtil.getScreenWidth(), DimenUtil.dp2px(336.0)))
        headerView.banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
        headerView.banner.isAutoPlay(false)
        this.presenter = GoodsDetailPresenter(this)
        headerView.textViewCoupon.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_get_coupon, R.dimen.dp29, R.dimen.dp15), null, null, null)
        headerView.textViewSub.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_full_reduction, R.dimen.dp15, R.dimen.dp15), null, null, null)
        initFooter()
    }

    private fun initFooter() {
        webView = WebView(this)
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        webView.isHorizontalScrollBarEnabled = false
        webView.isVerticalScrollBarEnabled = false
        adapter.addFooterView(webView)
        val settings = webView.settings;
        settings.javaScriptEnabled = false
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        webView.webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
                super.onReceivedSslError(view, handler, error);
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;
        } else {
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        }
    }

    override fun setPresenter(presenter: GoodsDetailContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun requestNet() {

        presenter.loadData(productId)

        //获取相似商品
        presenter.getSimilarGoods(productId)

        //获取喜欢商品用户
        presenter.getFavoriteUsers(productId)

        //获取购物车商品数量
        if (UserProfileUtil.isLogin()) presenter.getShopCartProductsNum()

        //获取商品SKU
        presenter.getGoodsSKUs(productId)
    }

    /**
     * 设置购物车商品数量
     */
    override fun setShopCartNum(item_count: Int) {
        if (item_count > 0) {
            textViewProductCount.visibility = View.VISIBLE
        } else {
            textViewProductCount.visibility = View.GONE
        }

        textViewProductCount.text = "$item_count"
    }

    /**
     *  添加购物车成功
     */
    override fun setAddShopCartSuccess() {
        var i = Integer.parseInt(textViewProductCount.text.toString())
        i++
        setShopCartNum(i)
    }

    /**
     * 更新喜欢状态
     */
    override fun updateFavoriteState(favorite: Boolean) {
        if (goodsData == null) return
        presenter.getFavoriteUsers(productId)
        goodsData?.is_like = favorite
        if (favorite) {
            goodsData!!.like_count++
            headerView.buttonLike.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            headerView.buttonLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_like_white, 0, 0, 0)
            headerView.buttonLike.setTextColor(Util.getColor(android.R.color.white))
            headerView.buttonLike.text = "+${goodsData!!.like_count}"
        } else {
            goodsData!!.like_count--
            headerView.buttonLike.setBackgroundResource(R.drawable.border_round_ededed)
            headerView.buttonLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_click_favorite_normal, 0, 0, 0)
            headerView.buttonLike.setTextColor(Util.getColor(R.color.color_949ea6))
            if (goodsData!!.like_count > 0) {
                headerView.buttonLike.text = "+${goodsData!!.like_count}"
            } else {
                headerView.buttonLike.text = Util.getString(R.string.text_like)
            }
        }
    }

    /**
     * 设置添加心愿单状态
     */
    override fun setAddWishOrderStatus(b: Boolean) {
        goodsData?.is_wish = b
        if (b) {
            buttonAddWish.setCompoundDrawables(null, null, null, null)
            buttonAddWish.setPadding(DimenUtil.getDimensionPixelSize(R.dimen.dp4), 0, 0, 0)
            buttonAddWish.text = Util.getString(R.string.text_already_add)
        } else {
            buttonAddWish.compoundDrawablePadding = DimenUtil.getDimensionPixelSize(R.dimen.dp5)
            buttonAddWish.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_add_wish_order, R.dimen.dp10, R.dimen.dp10), null, null, null)
            buttonAddWish.text = Util.getString(R.string.text_wish_order)
        }
    }


    //设置品牌馆信息
    override fun setBrandPavilionData(data: BrandPavilionBean) {
        brandPavilionData = data
        val imgUrls = ArrayList<String>()

        data.products?.forEach { product ->
            imgUrls.add(product.cover)
        }

        GlideUtil.loadImageWithDimenAndRadius(data.logo, headerView.imageViewLogo, 0, DimenUtil.dp2px(45.0))

        headerView.textViewShopName.text = data.name

        if (data.is_followed) {
            headerView.buttonFocus.text = Util.getString(R.string.text_focused)
            headerView.buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
            headerView.buttonFocus.setBackgroundResource(R.drawable.bg_coloreff3f2_radius4)
            headerView.buttonFocus.setPadding(0, 0, 0, 0)
            headerView.buttonFocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        } else {
            headerView.buttonFocus.text = Util.getString(R.string.text_focus)
            headerView.buttonFocus.setTextColor(Util.getColor(android.R.color.white))
            headerView.buttonFocus.setBackgroundResource(R.drawable.corner_bg_6ed7af)
            headerView.buttonFocus.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_focus_pavilion, R.dimen.dp13, R.dimen.dp12), null, null, null)
        }


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        headerView.recyclerViewShopGoods.setHasFixedSize(true)
        headerView.recyclerViewShopGoods.layoutManager = linearLayoutManager

        val designPavilionProductAdapter = DesignPavilionProductAdapter(R.layout.adapter_pure_imageview)
        headerView.recyclerViewShopGoods.adapter = designPavilionProductAdapter
        headerView.recyclerViewShopGoods.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
        designPavilionProductAdapter.setNewData(imgUrls)

        //跳转品牌馆商品详情
        designPavilionProductAdapter.setOnItemClickListener { _, _, position ->
            val productBean = data.products[position]
            val intent = Intent(this, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, productBean)
            startActivity(intent)
        }
    }

    //设置品牌馆关注状态
    override fun setBrandPavilionFocusState(favorite: Boolean) {
        if (favorite) {
            headerView.buttonFocus.text = Util.getString(R.string.text_focused)
            headerView.buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
            headerView.buttonFocus.setBackgroundResource(R.drawable.bg_coloreff3f2_radius4)
            headerView.buttonFocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            headerView.buttonFocus.setPadding(0, 0, 0, 0)
        } else {
            headerView.buttonFocus.text = Util.getString(R.string.text_focus)
            headerView.buttonFocus.setTextColor(Util.getColor(android.R.color.white))
            headerView.buttonFocus.setBackgroundResource(R.drawable.corner_bg_6ed7af)
            headerView.buttonFocus.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_focus_pavilion, R.dimen.dp13, R.dimen.dp12), null, null, null)
        }

        brandPavilionData?.is_followed = favorite
    }

    /**
     * 设置相似商品列表
     */
    override fun setSimilarGoodsData(data: MutableList<ProductBean>) {
        if (data.isEmpty()) {
            headerView.linearLayoutSimilar.visibility = View.GONE
            return
        }

        val imgUrls = ArrayList<String>()
        for (item in data) {
            imgUrls.add(item.cover)
        }
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        headerView.recyclerViewSimilar.setHasFixedSize(true)
        headerView.recyclerViewSimilar.layoutManager = linearLayoutManager
        //和品牌馆共用一个adapter
        val designPavilionProductAdapter = DesignPavilionProductAdapter(R.layout.adapter_pure_imageview)
        headerView.recyclerViewSimilar.adapter = designPavilionProductAdapter
        headerView.recyclerViewSimilar.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
        designPavilionProductAdapter.setNewData(imgUrls)

        //跳转相似商品详情
        designPavilionProductAdapter.setOnItemClickListener { _, view, position ->
            val productBean = data[position]
            val intent = Intent(this, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, productBean)
            startActivity(intent)
        }
    }

    /**
     * 设置优惠券数据
     */
    override fun setCouponData(coupons: List<CouponBean>) {
        if (coupons.isEmpty()) {
            headerView.relativeLayoutCoupon.visibility = View.GONE
        } else {
            headerView.relativeLayoutCoupon.visibility = View.VISIBLE
            val couponStr = StringBuilder()
            for (coupon in coupons) {
                if (coupon.type == 3) couponStr.append("满${coupon.min_amount}减${coupon.amount}、")
            }
            if (TextUtils.isEmpty(couponStr)) {
                headerView.textViewSub.visibility = View.GONE
            } else {
                headerView.textViewSub.text = couponStr.dropLast(1)
            }
        }
        couponList.addAll(coupons)
    }

    /**
     * 设置运费模板，快递时间 默认选第一条
     */
    override fun setExpressData(expressInfoBean: ExpressInfoBean?) {
        val items = expressInfoBean?.data?.items
        if (items == null || items.isEmpty()) {
            headerView.textViewExpressTime.visibility = View.GONE
            headerView.textViewExpress.visibility = View.GONE
            return
        }
        val expressItem: ExpressInfoBean.DataBean.ItemsBean = items[0] ?: return

        val expressTime: String?
        if (expressItem.max_days == 0) {
            expressTime = goodsData?.py_intro
        } else {
            expressTime = "预计${expressItem.min_days}~${expressItem.max_days}到达"
        }

        if (!TextUtils.isEmpty(expressTime)){
            headerView.textViewExpress.visibility = View.VISIBLE
            headerView.textViewExpressTime.visibility = View.VISIBLE
            headerView.textViewExpressTime.text = expressTime
            goodsData?.expressTime = expressTime
        }
        lookGoodsAllDetailDialog?.setExpressTime(expressTime)
    }


    /**
     * 设置商品信息
     */
    override fun setData(data: GoodsAllDetailBean.DataBean) {

        goodsData = data

        if (TextUtils.isEmpty(data.store_rid)) {
            LogUtil.e("店铺store_id不存在goodsId=$productId")
            return
        }

        webView.loadData(Util.createPageByHtmlBodyContent(data.content), "text/html;charset=utf-8", "utf-8");

        // 获取交货时间
        presenter.getExpressTime(data.fid, data.store_rid, productId)

        //获取优惠券
        presenter.getCouponsByStoreId(data.store_rid)

        //获取商品所在品牌馆信息
        presenter.loadBrandPavilionInfo(data.store_rid)



        if (data.is_distributed) { //分销商品
            buttonPurchase.visibility = View.VISIBLE
            buttonSaleDistribution.visibility = View.VISIBLE
        } else {  //非分销商品
            buttonAddShopCart.visibility = View.VISIBLE
            if (data.is_custom_made) { //支持接单订制
                headerView.textViewCustomDesc.visibility = View.VISIBLE
                buttonOrderMake.visibility = View.VISIBLE
            } else {  //不支持接单订制
                headerView.textViewCustomDesc.visibility = View.GONE
                buttonGoOrderConfirm.visibility = View.VISIBLE
            }
        }

//        for (item in data.deal_content) {
//            if (TextUtils.equals("text", item.type)) {
//                listDescription.add(AdapterGoodsDetail.MultipleItem(item, AdapterGoodsDetail.MultipleItem.TEXT_ITEM_TYPE))
//            } else if (TextUtils.equals("image", item.type)) {
//                listDescription.add(AdapterGoodsDetail.MultipleItem(item, AdapterGoodsDetail.MultipleItem.IMAGE_ITEM_TYPE))
//            }
//        }

        //设置心愿单状态
        if (data.is_wish) {
            buttonAddWish.setCompoundDrawables(null, null, null, null)
            buttonAddWish.text = Util.getString(R.string.text_already_add)
        } else {
            buttonAddWish.compoundDrawablePadding = DimenUtil.getDimensionPixelSize(R.dimen.dp5)
            buttonAddWish.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_add_wish_order, R.dimen.dp10, R.dimen.dp10), null, null, null)
            buttonAddWish.text = Util.getString(R.string.text_wish_order)
        }



        headerView.textViewName.text = data.name

        headerView.textViewNowPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp10, R.dimen.dp12), null, null, null)
        if (data.min_sale_price == 0.0) {
            headerView.textViewOriginalPrice.visibility = View.GONE
            headerView.textViewNowPrice.text = data.min_price.toString()
        } else {
            headerView.textViewOriginalPrice.visibility = View.VISIBLE
            headerView.textViewNowPrice.text = data.min_sale_price.toString()
            headerView.textViewOriginalPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            headerView.textViewOriginalPrice.text = data.min_price.toString()
        }


        if (data.is_free_postage) {
            headerView.imageViewFreeExpress.visibility = View.VISIBLE
        } else {
            headerView.imageViewFreeExpress.visibility = View.GONE
        }

        //设置banner
        val urlList = ArrayList<String>()
        val titleList = ArrayList<String>()
        for (item in data.assets) {
            urlList.add(item.view_url)
            titleList.add("")
        }

        headerView.banner.update(urlList, titleList)
        headerView.banner.start()
        headerView.banner.setOnBannerListener { position ->
            if (goodsData == null || skuData == null) return@setOnBannerListener
            val intent = Intent(this, GoodsImageViewActivity::class.java)
            goodsData!!.clickPosition = position
            goodsData!!.allSKUData = skuData
            intent.putExtra(GoodsImageViewActivity::class.java.simpleName, goodsData)
            startActivity(intent)
        }

        labels = ArrayList()
        for (label in data.labels) {
            labels?.add(label.name)
        }


        //设置标签
        setTags(labels, false)


        //设置初始喜欢状态
        if (data.is_like) {
            headerView.buttonLike.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            headerView.buttonLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_like_white, 0, 0, 0)
            headerView.buttonLike.setTextColor(Util.getColor(android.R.color.white))
            headerView.buttonLike.text = "+${data.like_count}"
        } else {
            headerView.buttonLike.setBackgroundResource(R.drawable.border_round_ededed)
            headerView.buttonLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_click_favorite_normal, 0, 0, 0)
            headerView.buttonLike.setTextColor(Util.getColor(R.color.color_949ea6))
            if (data.like_count > 0) {
                headerView.buttonLike.text = "+${data.like_count}"
            } else {
                headerView.buttonLike.text = Util.getString(R.string.text_like)
            }
        }

        if (TextUtils.isEmpty(data.features)) {
            headerView.textViewLightSpot.visibility = View.GONE
        } else {
            headerView.textViewLightSpot.visibility = View.VISIBLE
            headerView.textViewLightSpot.text = "亮点：${data.features}"
        }


        if (data.is_custom_service) { //可定制
            headerView.textViewCharacter.visibility = View.VISIBLE
            headerView.textViewCharacter.text = "特点：" + Util.getString(R.string.text_can_custom_service)
        } else {
            headerView.textViewCharacter.visibility = View.GONE
        }

        if (TextUtils.isEmpty(data.material_name)) {
            headerView.textViewMaterial.visibility = View.GONE
        } else {
            headerView.textViewMaterial.visibility = View.VISIBLE
            headerView.textViewMaterial.text = "材质：${data.material_name}"
        }

        headerView.textViewCount.text = "数量：${data.total_stock}件"

        headerView.textViewSendAddress.text = data.delivery_country

        if (TextUtils.isEmpty(data.return_policy_title)) {
            headerView.textViewReturnPolicy.visibility = View.GONE
        } else {
            headerView.textViewReturnPolicy.text = data.return_policy_title
        }


        headerView.textViewProductReturnPolicy.text = data.product_return_policy

        textViewEarn.text = "赚￥${goodsData?.commission_price}"
    }

    /**
     * 设置喜欢商品的用户数据
     */
    override fun setFavoriteUsersData(product_like_users: List<UserBean>) {

        if (product_like_users.isEmpty()) {
            relativeLayoutFavoriteGoodsUser.visibility = View.GONE
            return
        }

        val urlList = ArrayList<String>()

        for (item in product_like_users) urlList.add(item.avatar)

        //反转头像
        urlList.reverse()

        //设置关注人头像
        headerView.recyclerViewHeader.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        val headImageAdapter = HeadImageAdapter(R.layout.item_head_imageview)

        headImageAdapter.setNewData(urlList)
        headerView.recyclerViewHeader.layoutManager = linearLayoutManager
        headerView.recyclerViewHeader.adapter = headImageAdapter


        if (headerView.recyclerViewHeader.itemDecorationCount == 0) {
            headerView.recyclerViewHeader.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) >= 0 && parent.getChildAdapterPosition(view) != urlList.size - 1) {
                        outRect.left = -DimenUtil.getDimensionPixelSize(R.dimen.dp5)
                    }
                }
            })
        }

    }

    /**
     * 设置商品标签
     */
    private fun setTags(labels: List<String>?, isShowAll: Boolean) {
        if (labels == null || labels.isEmpty()) {
            linearLayoutTags.visibility = View.GONE
        } else {
            val size = labels.size
            val subLabels: List<String>
            if (isShowAll) {
                subLabels = labels
                textViewShowAllTag.visibility = View.GONE
            } else {
                if (size > showTagCount) { //默认最多显示5个tag，少于5全部显示
                    headerView.textViewShowAllTag.visibility = View.VISIBLE
                    subLabels = labels.subList(0, showTagCount)
                    headerView.textViewShowAllTag.text = "+${size - showTagCount}"
                } else {
                    subLabels = labels
                }
            }

            headerView.linearLayoutTags.visibility = View.VISIBLE
            val color777 = Util.getColor(R.color.color_777)
            val colorf5a43c = Util.getColor(R.color.color_f5a43c)
            val padding = DimenUtil.getDimensionPixelSize(R.dimen.dp3)
            val layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.rightMargin = padding

            val adapter = object : TagAdapter<String>(subLabels) {
                override fun getView(parent: FlowLayout, position: Int, s: String): View {
                    val tv = TextView(AppApplication.getContext())
                    if (position == 0) {
                        tv.setTextColor(colorf5a43c)
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bg_oval_f5a43c, 0)
                    } else if (position == size - 1) {
                        tv.setTextColor(color777)
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    } else {
                        tv.setTextColor(color777)
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bg_oval_b2b2b2, 0)
                    }

                    tv.compoundDrawablePadding = padding
                    tv.layoutParams = layoutParams
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
                    tv.text = s
                    return tv
                }
            }

            headerView.tagFlowLayout.adapter = adapter
        }
    }

    private var skuData: GoodsAllSKUBean? = null

    /**
     * 设置SKU数据
     */
    override fun setSKUData(goodsAllSKUBean: GoodsAllSKUBean) {
        skuData = goodsAllSKUBean
    }


    override fun installListener() {

        headerView.linearLayoutPavilion.setOnClickListener {
            if (goodsData == null) return@setOnClickListener
            val intent = Intent(this, BrandHouseActivity::class.java)
            intent.putExtra("rid", goodsData!!.store_rid)
            startActivity(intent)
        }
        //查看全部
        headerView.buttonLookAll.setOnClickListener(this)

        //立即购买
        buttonGoOrderConfirm.setOnClickListener(this)

        buttonSaleDistribution.setOnClickListener(this)

        //点击接单订制按钮
        buttonOrderMake.setOnClickListener(this)

        //添加购物车
        buttonAddShopCart.setOnClickListener(this)

        relativeLayoutShopCart.setOnClickListener {
            //跳转购物车
            EventBus.getDefault().post(MainFragment1::class.java.simpleName)
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra(MainActivity::class.java.simpleName, MainFragment1::class.java.simpleName)
            startActivity(intent)
        }

        imageViewBack.setOnClickListener { finish() }

        imageViewShare.setOnClickListener(this)

        headerView.textViewShowAllTag.setOnClickListener {
            //展开所有标签
            setTags(labels, true)
        }

        headerView.buttonLike.setOnClickListener(this)

        headerView.buttonAddWish.setOnClickListener(this)

        headerView.buttonGetDiscount.setOnClickListener(this)

        headerView.textViewSelectSpec.setOnClickListener(this)

        headerView.imageButton.setOnClickListener(this)

        headerView.buttonFocus.setOnClickListener(this)

        headerView.textViewConsult.setOnClickListener {
            ToastUtil.showInfo("咨询")
        }

        //分销商品->点击购买
        buttonPurchase.setOnClickListener(this)


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var dySum = 0
            var dp336 = DimenUtil.dp2px(336.0)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                dySum += dy
                if (dySum < dp336) {
                    textViewTitle.text = ""
                    imageViewBack.setImageResource(R.mipmap.icon_return_white)
                    imageViewShare.setImageResource(R.mipmap.icon_share_white)
                    relativeLayoutHeader.setBackgroundResource(R.drawable.bg_gradient_color000)
                } else if (dySum > dp336) {
                    textViewTitle.text = goodsData?.name
                    imageViewBack.setImageResource(R.mipmap.icon_nav_back)
                    imageViewShare.setImageResource(R.mipmap.icon_click_share)
                    relativeLayoutHeader.setBackgroundColor(Util.getColor(android.R.color.white))
                }
            }
        })
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.buttonLookAll -> { //查看全部详情
                if (goodsData == null) return
                lookGoodsAllDetailDialog = LookGoodsAllDetailDialog(this, goodsData!!)
                lookGoodsAllDetailDialog?.show()
                lookGoodsAllDetailDialog?.setExpressTime(headerView.textViewExpressTime.text.toString())
            }

            R.id.buttonGoOrderConfirm -> {//点击购买
                if (UserProfileUtil.isLogin()) {
                    if (goodsData == null || skuData == null) return
                    val selectSpecificationBottomDialog = SelectSpecificationBottomDialog(this, presenter, goodsData!!, R.id.buttonGoOrderConfirm, skuData!!)
                    selectSpecificationBottomDialog.show()
                } else {//跳转登录
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            R.id.buttonSaleDistribution -> { //卖
                if (goodsData == null) return
                val dialog = GoodsDetailSaleBottomDialog(this, presenter, goodsData!!)
                dialog.show()
            }
            R.id.buttonOrderMake -> { //接单订制
                if (UserProfileUtil.isLogin()) {
                    if (goodsData == null || skuData == null) return
                    val selectSpecificationBottomDialog = SelectSpecificationBottomDialog(this, presenter, goodsData!!, R.id.buttonOrderMake, skuData!!)
                    selectSpecificationBottomDialog.show()
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }

            R.id.buttonAddShopCart -> {
                if (UserProfileUtil.isLogin()) {
                    if (goodsData == null || skuData == null) return
                    val selectSpecificationBottomDialog = SelectSpecificationBottomDialog(this, presenter, goodsData!!, R.id.buttonAddShopCart, skuData!!)
                    selectSpecificationBottomDialog.show()
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }

            R.id.imageViewShare -> {
                ToastUtil.showInfo("分享产品")
            }

            R.id.imageButton -> { //喜欢用户列表
                val intent = Intent(applicationContext, FavoriteUserListActivity::class.java)
                intent.putExtra(FavoriteUserListActivity::class.java.simpleName, productId)
                startActivity(intent)
            }

            R.id.buttonFocus -> { //关注大B/品牌馆/店铺
                if (UserProfileUtil.isLogin()) {
                    if (brandPavilionData == null || goodsData == null) return
                    presenter.focusBrandPavilion(goodsData!!.store_rid, !brandPavilionData!!.is_followed, v)
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }

            R.id.buttonLike -> {
                if (UserProfileUtil.isLogin()) {
                    if (goodsData == null) return

                    if (goodsData!!.is_like) {
                        presenter.favoriteGoods(goodsData!!.rid, v, false)
                    } else {
                        presenter.favoriteGoods(goodsData!!.rid, v, true)
                    }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }

            R.id.buttonAddWish -> {
                if (UserProfileUtil.isLogin()) {
                    if (goodsData == null) return

                    if (goodsData!!.is_wish) { //取消
                        presenter.addWishOrder(goodsData!!.rid, false)
                    } else {
                        presenter.addWishOrder(goodsData!!.rid, true)
                    }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            R.id.buttonGetDiscount -> { //获取优惠券
                if (UserProfileUtil.isLogin()) {
                    if (brandPavilionData == null) return
                    val couponBottomDialog = CouponBottomDialog(this, couponList, presenter, brandPavilionData!!.rid)
                    couponBottomDialog.show()
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }

            R.id.buttonPurchase, R.id.textViewSelectSpec -> { //请选择规格
                if (UserProfileUtil.isLogin()) {
                    if (skuData == null || goodsData == null) return
                    val selectSpecificationBottomDialog = SelectSpecificationBottomDialog(this, presenter, goodsData!!, R.id.textViewSelectSpec, skuData!!)
                    selectSpecificationBottomDialog.show()
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
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

    override fun onDestroy() {
        val parent = webView.parent as ViewGroup
        parent.removeView(webView)
        webView.removeView(webView)
        webView.destroy()

        super.onDestroy()
    }
}