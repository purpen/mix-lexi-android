package com.thn.lexi.index.detail

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.basemodule.tools.ScreenUtil
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.R
import com.thn.lexi.index.selection.HeadImageAdapter
import com.thn.lexi.mine.enshrine.WishOrderFragment
import com.thn.lexi.view.autoScrollViewpager.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.view_goods_detail_head.*

class GoodsDetailActivity : BaseActivity(),GoodsDetailContract.View,View.OnClickListener{

    private lateinit var presenter:GoodsDetailPresenter

    private var goodsId:String = ""

    override val layout: Int = R.layout.activity_goods_detail

    override fun getIntentData() {
        goodsId = intent.extras.getString(GoodsDetailActivity::class.java.simpleName)
    }

    override fun initView() {
        setUpViewPager()
        this.presenter = GoodsDetailPresenter(this)
    }

    override fun setPresenter(presenter: GoodsDetailContract.Presenter?) {
        setPresenter(presenter)
    }

    private fun setUpViewPager() {
        var fragments = ArrayList<BaseFragment>()
        fragments.add(SimilarGoodsFragment.newInstance())
        fragments.add(WishOrderFragment.newInstance())

        val titles = resources.getStringArray(R.array.strings_goods_detail_titles)
        val adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles.asList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(true)
        slidingTabLayout.setViewPager(customViewPager)
    }

    override fun onStart() {
        super.onStart()
        scrollableView?.start()
    }

    override fun onStop() {
        super.onStop()
        scrollableView?.stop()
    }

    override fun requestNet() {
        presenter.loadData(goodsId)
        presenter.loadGoodsInfo(goodsId)
    }


    override fun setData(data: GoodsDetailBean.DataBean) {
        var urlList = ArrayList<String>()
        for (item in data.images){
            urlList.add(item.view_url)
        }
        scrollableView.setAdapter(ViewPagerAdapter<String>(this,urlList,ScreenUtil.getScreenWidth(),resources.getDimensionPixelSize(R.dimen.dp375)).setInfiniteLoop(true))
        scrollableView.setAutoScrollDurationFactor(8.0)
        scrollableView.showIndicators()
        scrollableView.start()

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

        //商店商品
//        recyclerViewShopGoods.adapter
    }

    override fun setGoodsInfo(data: GoodsInfoBean.DataBean) {
        textView0.text = data.name
        textView1.text = data.commission_price.toString()
        textView2.text = "+ ${data.like_count}人"
    }

    override fun installListener() {
        imageButton1.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when(id){
            R.id.imageButton1-> finish()
        }
    }

    override fun showLoadingView() {

    }

    override fun dismissLoadingView() {

    }

    override fun showError(string: String) {

    }

    override fun goPage() {

    }
}