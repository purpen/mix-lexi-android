package com.thn.lexi.goods.detail

import com.basemodule.ui.BaseActivity
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.R
import com.thn.lexi.mine.FavoriteFragment
import com.thn.lexi.mine.WishOrderFragment
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.view_goods_shop.*

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


    override fun requestNet() {
        presenter.loadData(goodsId)
    }



    override fun installListener() {
        super.installListener()
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