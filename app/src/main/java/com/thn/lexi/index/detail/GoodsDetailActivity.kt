package com.thn.lexi.index.detail

import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.GlideImageLoader
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.selection.HeadImageAdapter
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.view_goods_detail_head.*

class GoodsDetailActivity : BaseActivity(),GoodsDetailContract.View,View.OnClickListener{

    private val dialog:WaitingDialog by lazy { WaitingDialog(this) }

    private lateinit var presenter:GoodsDetailPresenter

    private var goodsId:String = ""

    override val layout: Int = R.layout.activity_goods_detail

    override fun getIntentData() {
        goodsId = intent.extras.getString(GoodsDetailActivity::class.java.simpleName)
    }

    override fun initView() {
        banner.setImageLoader(GlideImageLoader(R.dimen.dp0))
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR)

        setUpViewPager()
        this.presenter = GoodsDetailPresenter(this)
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
        presenter.loadGoodsInfo(goodsId)
    }


    override fun setData(data: GoodsAllDetailBean.DataBean) {
        val urlList = ArrayList<String>()
        for (item in data.assets){
            urlList.add(item.view_url)
        }

        banner.setImages(urlList)
        banner.start()

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
                        outRect.left = -parent.context.resources.getDimensionPixelSize(R.dimen.dp5)
                    }
                }
            })
        }

//        val content:String? = data.content
//        textViewGoodsDesc.text = Html.fromHtml(content)

        //商店商品
//        recyclerViewShopGoods.adapter
    }

    override fun setGoodsInfo(data: ProductBean) {
        textViewName.text = data.name
        if (data.real_sale_price==0.0){
            textViewOriginalPrice.visibility =View.GONE
            textViewNowPrice.text = data.real_price.toString()
        }else{
            textViewNowPrice.text = data.real_sale_price.toString()
            textViewOriginalPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            textViewOriginalPrice.text = data.real_price.toString()
        }

    }

    override fun installListener() {
        imageViewBack.setOnClickListener{
            finish()
        }

        imageViewShare.setOnClickListener(this)

        buttonLike.setOnClickListener(this)

        buttonAddWish.setOnClickListener(this)

        buttonGetDiscount.setOnClickListener(this)

        textViewSelectSpec.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when(id){
            R.id.imageViewShare ->{
                ToastUtil.showInfo("分享产品")
            }

            R.id.buttonLike ->{
                ToastUtil.showInfo("喜欢")
            }

            R.id.buttonAddWish ->{
                ToastUtil.showInfo("添加心愿单")
            }
            R.id.buttonGetDiscount ->{
                ToastUtil.showInfo("领取")
            }
            R.id.textViewSelectSpec ->{
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