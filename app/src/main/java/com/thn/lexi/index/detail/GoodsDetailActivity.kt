package com.thn.lexi.index.detail

import android.content.Intent
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basemodule.ui.BaseActivity
import com.thn.lexi.GlideImageLoader
import com.thn.lexi.R
import com.thn.lexi.index.selection.HeadImageAdapter
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.header_goods_detail.*
import android.widget.TextView
import com.basemodule.tools.*
import com.thn.lexi.AppApplication
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.beans.BrandPavilionBean
import com.thn.lexi.beans.CouponBean
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.beans.UserBean
import com.thn.lexi.mine.designPavilion.DesignPavilionProductAdapter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.header_goods_detail.view.*
import kotlinx.android.synthetic.main.view_goods_description.view.*
import kotlinx.android.synthetic.main.view_goods_shop.view.*
import kotlinx.android.synthetic.main.view_similar_goods.view.*


class GoodsDetailActivity : BaseActivity(), GoodsDetailContract.View, View.OnClickListener {
    private val showTagCount: Int = 5
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private lateinit var presenter: GoodsDetailPresenter

    private var goodsId: String = ""

    override val layout: Int = R.layout.activity_goods_detail

    private var labels: MutableList<String>? = null

    private lateinit var adapter: AdapterGoodsDetail

    private lateinit var listDescription: ArrayList<AdapterGoodsDetail.MultipleItem>

    private lateinit var headerView: View

    private lateinit var couponList: ArrayList<CouponBean>

    private var storeRid: String = ""

    //商品详情数据
    private var goodsData: GoodsAllDetailBean.DataBean? = null

    //品牌馆数据
    private var brandPavilionData: BrandPavilionBean.DataBean? = null

    override fun getIntentData() {
        goodsId = intent.extras.getString(GoodsDetailActivity::class.java.simpleName)
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

        val footerView = View(this)
        footerView.setBackgroundColor(Util.getColor(android.R.color.white))
        footerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp20))
        adapter.addFooterView(footerView)
        headerView = LayoutInflater.from(this).inflate(R.layout.header_goods_detail, null)

        adapter.addHeaderView(headerView)

        adapter.setHeaderAndEmpty(true)

        headerView.banner.setImageLoader(GlideImageLoader(R.dimen.dp0))
        headerView.banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        headerView.banner.isAutoPlay(false)
        this.presenter = GoodsDetailPresenter(this)
        headerView.textViewCoupon.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_get_coupon, R.dimen.dp29, R.dimen.dp15), null, null, null)
        headerView.textViewSub.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_full_reduction, R.dimen.dp15, R.dimen.dp15), null, null, null)
    }

    override fun setPresenter(presenter: GoodsDetailContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun requestNet() {
        presenter.loadData(goodsId)

        //获取相似商品
        presenter.getSimilarGoods(goodsId)

        //获取喜欢商品用户
        presenter.getFavoriteUsers(goodsId)
    }


    /**
     * 更新喜欢状态
     */
    override fun updateFavoriteState(favorite: Boolean) {
        if (goodsData == null) return
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
    override fun setBrandPavilionData(data: BrandPavilionBean.DataBean?) {

        if (data == null) return

        brandPavilionData = data
        val imgUrls = ArrayList<String>()

        data.products?.forEach { product ->
            imgUrls.add(product.cover)
        }

        GlideUtil.loadImage(data.logo, headerView.imageViewLogo)

        headerView.textViewShopName.text = data.name

        if (data.is_followed) {
            headerView.buttonFocus.text = Util.getString(R.string.text_focused)
            headerView.buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
            headerView.buttonFocus.setBackgroundResource(R.drawable.bg_coloreff3f2_radius4)
            headerView.buttonFocus.setPadding(0,0,0,0)
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
    }

    //设置品牌馆关注状态
    override fun setBrandPavilionFocusState(favorite: Boolean) {
        if (favorite) {
            headerView.buttonFocus.text = Util.getString(R.string.text_focused)
            headerView.buttonFocus.setTextColor(Util.getColor(R.color.color_949ea6))
            headerView.buttonFocus.setBackgroundResource(R.drawable.bg_coloreff3f2_radius4)
            headerView.buttonFocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            headerView.buttonFocus.setPadding(0,0,0,0)
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
        val expressItem: ExpressInfoBean.DataBean.ItemsBean? = items[0]
        headerView.textViewExpressTime.text = "预计${expressItem?.min_days}~${expressItem?.max_days}到达"
    }



    /**
     * 设置商品信息
     */
    override fun setData(data: GoodsAllDetailBean.DataBean) {

        goodsData = data

        storeRid = data.store_rid

        //获取优惠券
        presenter.getCouponsByStoreId(data.store_rid)

        //获取商品所在品牌馆信息
        presenter.loadBrandPavilionInfo(data.store_rid)

        // 获取交货时间
        presenter.getExpressTime(data.fid, data.store_rid, goodsId)


        for (item in data.deal_content) {
            if (TextUtils.equals("text", item.type)) {
                listDescription.add(AdapterGoodsDetail.MultipleItem(item, AdapterGoodsDetail.MultipleItem.TEXT_ITEM_TYPE))
            } else if (TextUtils.equals("image", item.type)) {
                listDescription.add(AdapterGoodsDetail.MultipleItem(item, AdapterGoodsDetail.MultipleItem.IMAGE_ITEM_TYPE))
            }
        }

        //设置心愿单状态
        if (data.is_wish) {
            buttonAddWish.setCompoundDrawables(null, null, null, null)
            buttonAddWish.text = Util.getString(R.string.text_already_add)
        } else {
            buttonAddWish.compoundDrawablePadding = DimenUtil.getDimensionPixelSize(R.dimen.dp5)
            buttonAddWish.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_add_wish_order, R.dimen.dp10, R.dimen.dp10), null, null, null)
            buttonAddWish.text = Util.getString(R.string.text_wish_order)
        }

        adapter.setNewData(listDescription)

        headerView.textViewName.text = data.name

        headerView.textViewNowPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp10, R.dimen.dp12), null, null, null)
        if (data.real_sale_price == 0.0) {
            headerView.textViewOriginalPrice.visibility = View.GONE
            headerView.textViewNowPrice.text = data.real_price.toString()
        } else {
            headerView.textViewNowPrice.text = data.real_sale_price.toString()
            headerView.textViewOriginalPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            headerView.textViewOriginalPrice.text = data.real_price.toString()
        }


        if (data.is_free_postage) {
            headerView.imageViewFreeExpress.visibility = View.VISIBLE
        } else {
            headerView.imageViewFreeExpress.visibility = View.GONE
        }

        //设置banner
        val urlList = ArrayList<String>()
        for (item in data.assets) {
            urlList.add(item.view_url)
        }

        headerView.banner.setImages(urlList)
        headerView.banner.start()

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

        headerView.textViewLightSpot.text = "亮点：" + data.features

        if (data.is_custom_service) { //可定制
            headerView.textViewCharacter.visibility = View.VISIBLE
            headerView.textViewCharacter.text = "特点：" + Util.getString(R.string.text_can_custom_service)
        } else {
            headerView.textViewCharacter.visibility = View.GONE
        }

        headerView.textViewMaterial.text = "材质：${data.material_name}"

        headerView.textViewCount.text = "数量：${data.total_stock}件"

        headerView.textViewSendAddress.text = data.delivery_country

        headerView.textViewReturnPolicy.text = data.return_policy_title

        headerView.textViewProductReturnPolicy.text = data.product_return_policy
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
        val linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
        val headImageAdapter = HeadImageAdapter(R.layout.item_head_imageview)

        headImageAdapter.setNewData(urlList)
        headerView.recyclerViewHeader.layoutManager = linearLayoutManager
        headerView.recyclerViewHeader.adapter = headImageAdapter


        if (headerView.recyclerViewHeader.itemDecorationCount == 0) {
            headerView.recyclerViewHeader.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
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


    override fun installListener() {

        buttonGoOrderConfirm.setOnClickListener(this)

        buttonAddShopCart.setOnClickListener(this)

        relativeLayoutShopCart.setOnClickListener {
            //TODO 去购物车
            ToastUtil.showInfo("去购物车")
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
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {

            R.id.buttonGoOrderConfirm -> {
                //TODO 跳转确认订单
                ToastUtil.showInfo("确认订单")
            }

            R.id.buttonAddShopCart -> {
                if (goodsData == null) return
                val selectSpecificationBottomDialog = SelectSpecificationBottomDialog(this, presenter, goodsData, R.id.buttonAddShopCart)
                selectSpecificationBottomDialog.show()
            }

            R.id.imageViewShare -> {
                ToastUtil.showInfo("分享产品")
            }

            R.id.imageButton ->{ //喜欢用户列表
                val intent = Intent(applicationContext,FavoriteUserListActivity::class.java)
                intent.putExtra(FavoriteUserListActivity::class.java.simpleName,goodsId)
                startActivity(intent)
            }

            R.id.buttonFocus -> { //关注大B/品牌馆/店铺
                if (brandPavilionData == null) return
                presenter.focusBrandPavilion(goodsData!!.store_rid,!brandPavilionData!!.is_followed)
            }

            R.id.buttonLike -> {
                if (goodsData == null) return


                if (goodsData!!.is_like) {
                    presenter.favoriteGoods(goodsData!!.rid, v, false)
                } else {
                    presenter.favoriteGoods(goodsData!!.rid, v, true)
                }
            }

            R.id.buttonAddWish -> {
                if (goodsData == null) return

                if (goodsData!!.is_wish) { //取消
                    presenter.addWishOrder(goodsData!!.rid, false)
                } else {
                    presenter.addWishOrder(goodsData!!.rid, true)
                }

            }
            R.id.buttonGetDiscount -> { //获取优惠券
                val couponBottomDialog = CouponBottomDialog(this, couponList, presenter, storeRid)
                couponBottomDialog.show()
            }

            R.id.textViewSelectSpec -> { //请选择规格
                val selectSpecificationBottomDialog = SelectSpecificationBottomDialog(this, presenter, goodsData, R.id.textViewSelectSpec)
                selectSpecificationBottomDialog.show()
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
}