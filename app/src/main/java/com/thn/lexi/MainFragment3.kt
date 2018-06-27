package com.thn.lexi

import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import com.basemodule.tools.Constants
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.goods.GoodsData
import com.thn.lexi.mine.*
import kotlinx.android.synthetic.main.fragment_main3.*
import kotlinx.android.synthetic.main.view_mine_head.*

class MainFragment3 : BaseFragment(), MineContract.View, ViewPager.OnPageChangeListener {
    private val dialog: WaitingDialog? by lazy { WaitingDialog(activity) }
    private lateinit var presenter: MinePresenter
    private var page: Int = 1
    private lateinit var adapter0: MineFavoritesAdapter
    private lateinit var fragments: ArrayList<BaseFragment>
    private var distance:Float = 0f
    companion object {
        fun newInstance(): MainFragment3 {
            return MainFragment3()
        }
    }

    override val layout: Int = R.layout.fragment_main3


    private fun setUpViewPager() {
        fragments = ArrayList()
        fragments.add(FavoriteFragment.newInstance())
        fragments.add(WishOrderFragment.newInstance())
        fragments.add(FavoriteShopFragment.newInstance())
        fragments.add(ActivitiesFragment.newInstance())

        val titles = resources.getStringArray(R.array.strings_mine_titles)
        val adapter = CustomFragmentPagerAdapter(childFragmentManager, fragments, titles)
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(true)
        slidingTabLayout.setViewPager(customViewPager)
    }


    override fun initView() {
        setUpViewPager()
        this.presenter = MinePresenter(this)
        adapter0 = MineFavoritesAdapter(R.layout.layout_goods_adapter)
//
//        val headView = LayoutInflater.from(activity).inflate(R.layout.view_mine_head, null)
//        adapter0.addHeaderView(headView)
//        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_6ed7af))
//        swipeRefreshLayout.isRefreshing = false
//        val linearLayoutManager = LinearLayoutManager(activity)
//        recyclerView0.setHasFixedSize(true)
//        recyclerView0.layoutManager = linearLayoutManager
//        recyclerView0.adapter = adapter0
//        recyclerView0.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.VERTICAL, resources.getDimensionPixelSize(R.dimen.dp10), resources.getColor(R.color.color_d1d1d1)))
    }


    override fun setPresenter(presenter: MineContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {
        customViewPager.addOnPageChangeListener(this)
    }

    override fun loadData() {
        page = 1
        presenter.loadData("", page)
    }

    override fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {
        adapter0.setNewData(data)
        adapter0.setEnableLoadMore(true)
        showEndView()
        ++page
    }

    override fun addData(products: List<GoodsData.DataBean.ProductsBean>) {
        adapter0.addData(products)
        ++page
        showEndView()
    }

    private fun showEndView() {
        if (adapter0.data.size < Integer.valueOf(Constants.PAGE_SIZE)) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter0.loadMoreEnd(false)

        } else {
            adapter0.loadMoreComplete()
        }
    }


    override fun showLoadingView() {
        dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

    override fun onPageSelected(position: Int) {
//        val fragment = fragments[position]
//        if (fragment is FavoriteFragment) fragment.setScrollHeight(distance,true)
//        if (fragment is WishOrderFragment) fragment.setScrollHeight(distance,true)
//        if (fragment is FavoriteShopFragment)fragment.setScrollHeight(distance)
//        if (fragment is ActivitiesFragment)fragment.setScrollHeight(distance)

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }


    override fun onPageScrollStateChanged(state: Int) {

    }

    fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {

    }

    private var sumY: Float = 0f
    fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

        LogUtil.e("==========$dy==========")
        sumY += dy

        distance = Math.min(sumY, resources.getDimensionPixelSize(R.dimen.dp238).toFloat())
        //238dp距离屏幕顶部距离
        relativeLayout.translationY = -distance

        for (fragment in fragments){
            if (fragment is FavoriteFragment) fragment.setScrollHeight(distance,true)
            if (fragment is WishOrderFragment) fragment.setScrollHeight(distance,true)
            if (fragment is FavoriteShopFragment) fragment.setScrollHeight(distance,true)
            if (fragment is ActivitiesFragment) fragment.setScrollHeight(distance,true)
        }
    }

}