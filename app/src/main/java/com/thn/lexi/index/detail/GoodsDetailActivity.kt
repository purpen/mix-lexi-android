package com.thn.lexi.index.detail

import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.AppCompatDrawableManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.GlideImageLoader
import com.thn.lexi.R
import com.thn.lexi.index.selection.HeadImageAdapter
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.view_goods_detail_head.*
import android.widget.TextView
import com.basemodule.tools.*
import com.thn.lexi.AppApplication
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.beans.BrandPavilionBean
import com.thn.lexi.mine.designPavilion.DesignPavilionProductAdapter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.view_goods_description.*
import kotlinx.android.synthetic.main.view_goods_shop.*


class GoodsDetailActivity : BaseActivity(), GoodsDetailContract.View, View.OnClickListener {
    private val showTagCount: Int = 5
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }

    private lateinit var presenter: GoodsDetailPresenter


    private var goodsId: String = ""

    override val layout: Int = R.layout.activity_goods_detail

    private var labels: MutableList<String>? = null

    override fun getIntentData() {
        goodsId = intent.extras.getString(GoodsDetailActivity::class.java.simpleName)
    }

    override fun initView() {
        banner.setImageLoader(GlideImageLoader(R.dimen.dp0))
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        setUpViewPager()
        this.presenter = GoodsDetailPresenter(this)
        textViewCoupon.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_get_coupon,R.dimen.dp29,R.dimen.dp15),null,null,null)
        textViewSub.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_full_reduction,R.dimen.dp15,R.dimen.dp15),null,null,null)
    }

    override fun setPresenter(presenter: GoodsDetailContract.Presenter?) {
        setPresenter(presenter)
    }

    private fun setUpViewPager() {
        var fragments = ArrayList<BaseFragment>()
        fragments.add(SimilarGoodsFragment.newInstance())

        val titles = resources.getStringArray(R.array.strings_goods_detail_titles)
        val adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.asList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(false)
        slidingTabLayout.setViewPager(customViewPager)
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }

    override fun requestNet() {
        presenter.loadData(goodsId)
    }


    //设置品牌馆信息
    override fun setBrandPavilionData(data: BrandPavilionBean.DataBean?) {
        val imgUrls = ArrayList<String>()

        data?.products?.forEach { product ->
            imgUrls.add(product.cover)
        }

        GlideUtil.loadImage(data?.logo, imageViewLogo)

        textViewShopName.text = data?.name

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewShopGoods.setHasFixedSize(true)
        recyclerViewShopGoods.layoutManager = linearLayoutManager

        val designPavilionProductAdapter = DesignPavilionProductAdapter(R.layout.adapter_pure_imageview)
        recyclerViewShopGoods.adapter = designPavilionProductAdapter
        recyclerViewShopGoods.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
        designPavilionProductAdapter.setNewData(imgUrls)
    }

    override fun setExpressData(expressInfoBean: ExpressInfoBean?) {

        var expressItem: ExpressInfoBean.DataBean.ItemsBean? = null
        run loop@{
            expressInfoBean?.data?.items?.forEach {
                if (it.is_default) { //退出遍历
                    expressItem = it
                    return@loop
                }
            }
        }

        textViewExpressTime.text = "预计${expressItem?.min_days}~${expressItem?.max_days}到达"
    }

    /**
     * 设置商品信息
     */
    override fun setData(data: GoodsAllDetailBean.DataBean) {

        //获取商品所在品牌馆信息
        presenter.loadBrandPavilionInfo(data.store_rid)

        // 获取交货时间
        presenter.getExpressTime(data.fid, data.store_rid, goodsId)

        textViewName.text = data.name

        textViewNowPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit,R.dimen.dp10,R.dimen.dp12),null ,null,null)
        if (data.real_sale_price == 0.0) {
            textViewOriginalPrice.visibility = View.GONE
            textViewNowPrice.text = data.real_price.toString()
        } else {
            textViewNowPrice.text = data.real_sale_price.toString()
            textViewOriginalPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOriginalPrice.text = data.real_price.toString()
        }


        if (data.is_free_postage) {
            imageViewFreeExpress.visibility = View.VISIBLE
        } else {
            imageViewFreeExpress.visibility = View.GONE
        }

        //设置banner
        val urlList = ArrayList<String>()
        for (item in data.assets) {
            urlList.add(item.view_url)
        }

        banner.setImages(urlList)
        banner.start()

        labels = ArrayList()
        for (label in data.labels) {
            labels?.add(label.name)
        }


        //设置标签
        setTags(labels, false)


        if (data.like_count > 0) {
            buttonLike.setBackgroundResource(R.drawable.bg_round_color5fe4b1)
            buttonLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_like_white, 0, 0, 0)
            buttonLike.setTextColor(Util.getColor(android.R.color.white))
            buttonLike.text = "+${data.like_count}"
        } else {
            buttonLike.setBackgroundResource(R.drawable.border_round_ededed)
            buttonLike.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_click_favorite_normal, 0, 0, 0)
            buttonLike.setTextColor(Util.getColor(R.color.color_949ea6))
            buttonLike.text = Util.getString(R.string.text_like)
        }

        //设置关注人头像
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val headImageAdapter = HeadImageAdapter(R.layout.item_head_imageview)
        headImageAdapter.setNewData(urlList)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = headImageAdapter

        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) > 0) {
                        outRect.left = -DimenUtil.getDimensionPixelSize(R.dimen.dp5)
                    }
                }
            })
        }


        textViewLightSpot.text = "亮点：" + data.features

        if (data.is_custom_service) { //可定制
            textViewCharacter.visibility = View.VISIBLE
            textViewCharacter.text = "特点：" + Util.getString(R.string.text_can_custom_service)
        } else {
            textViewCharacter.visibility = View.GONE
        }

        textViewMaterial.text = "材质：${data.material_name}"

        textViewCount.text = "数量：${data.total_stock}件"

        textViewSendAddress.text = data.delivery_country

        textViewReturnPolicy.text = data.return_policy_title

        textViewProductReturnPolicy.text = data.product_return_policy
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
                    textViewShowAllTag.visibility = View.VISIBLE
                    subLabels = labels.subList(0, showTagCount)
                    textViewShowAllTag.text = "+${size - showTagCount}"
                } else {
                    subLabels = labels
                }
            }

            linearLayoutTags.visibility = View.VISIBLE
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

            tagFlowLayout.adapter = adapter
        }
    }


    override fun installListener() {
        imageViewBack.setOnClickListener {
            finish()
        }

        textViewShowAllTag.setOnClickListener {
            //展开所有标签
            setTags(labels, true)
        }

        imageViewShare.setOnClickListener(this)

        buttonLike.setOnClickListener(this)

        buttonAddWish.setOnClickListener(this)

        buttonGetDiscount.setOnClickListener(this)

        textViewSelectSpec.setOnClickListener(this)

        textViewConsult.setOnClickListener {
            ToastUtil.showInfo("咨询")
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.imageViewShare -> {
                ToastUtil.showInfo("分享产品")
            }

            R.id.buttonLike -> {
                ToastUtil.showInfo("喜欢")
            }

            R.id.buttonAddWish -> {
                ToastUtil.showInfo("添加心愿单")
            }
            R.id.buttonGetDiscount -> {
                ToastUtil.showInfo("领取")
            }
            R.id.textViewSelectSpec -> {
                ToastUtil.showInfo("选择规格")
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