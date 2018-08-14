package com.thn.lexi
import android.content.Intent
import android.view.View
import com.basemodule.tools.Constants
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.basemodule.ui.CustomFragmentPagerAdapter
import com.thn.lexi.goods.selection.GoodsData
import com.thn.lexi.mine.*
import com.thn.lexi.user.setting.SettingActivity
import kotlinx.android.synthetic.main.fragment_main3.*
import kotlinx.android.synthetic.main.view_mine_head.*

class MainFragment3 : BaseFragment(), MineContract.View,View.OnClickListener{
    private val dialog: WaitingDialog? by lazy { WaitingDialog(activity) }
    private lateinit var presenter: MinePresenter
    private var page: Int = 1
    private lateinit var adapter0: MineFavoritesAdapter
    private lateinit var fragments: ArrayList<BaseFragment>
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

        val adapter = CustomFragmentPagerAdapter(childFragmentManager, fragments, titles.asList())
        customViewPager.adapter = adapter
        customViewPager.offscreenPageLimit = fragments.size
        customViewPager.setPagingEnabled(true)
        slidingTabLayout.setViewPager(customViewPager)
    }


    override fun initView() {
        setUpViewPager()
        this.presenter = MinePresenter(this)
        adapter0 = MineFavoritesAdapter(R.layout.adapter_goods_layout)
    }


    override fun setPresenter(presenter: MineContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun installListener() {
        imageButton0.setOnClickListener(this)
        imageButton1.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when(id){
//            R.id.imageButton0->
            R.id.imageButton1 -> startActivity(Intent(activity, SettingActivity::class.java))
        }
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

}