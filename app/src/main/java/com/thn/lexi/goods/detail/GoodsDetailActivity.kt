package com.thn.lexi.goods.detail

import android.graphics.Color
import com.basemodule.tools.ScreenUtil
import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.R
import com.thn.lexi.mine.FavoriteFragment
import com.thn.lexi.mine.WishOrderFragment
import com.thn.lexi.view.autoScrollViewpager.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.view_goods_detail_head.*

class GoodsDetailActivity : BaseActivity(),GoodsDetailContract.View {

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
        fragments.add(FavoriteFragment.newInstance())
        fragments.add(WishOrderFragment.newInstance())

        val titles = resources.getStringArray(R.array.strings_mine_titles)
        val adapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments, titles)
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
    }

    override fun setGoodsInfo(data: GoodsInfoBean.DataBean) {
        textView0.text = data.name
        textView1.text = data.commission_price.toString()
        textView2.text = "+ ${data.like_count}人"
    }

    override fun installListener() {

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