package com.thn.lexi.mine
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.index.bean.ProductBean
import com.thn.lexi.index.selection.DiscoverLifeBean
import com.thn.lexi.index.selection.GoodsData
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.view_head_mine_favorite.view.*

class FavoriteFragment : BaseFragment(), FavoriteContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val adapterLikeGoods: AdapterLikeGoods by lazy { AdapterLikeGoods(R.layout.adapter_pure_imageview) }

    private val adapterMineFavorite: AdapterMineFavorite by lazy { AdapterMineFavorite(R.layout.adapter_pure_imageview) }

    private val adapterLikeShowWindow: AdapterLikeShowWindow by lazy { AdapterLikeShowWindow(R.layout.adapter_show_window_like) }

    private lateinit var headerView:View

    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: FavoritePresenter

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteFragment = FavoriteFragment()
    }

    override fun initView() {
        presenter = FavoritePresenter(this)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapterMineFavorite

        headerView = LayoutInflater.from(context).inflate(R.layout.view_head_mine_favorite, null)
        adapterMineFavorite.addHeaderView(headerView)
        adapterMineFavorite.setHeaderAndEmpty(true)
        initGoodsLike()

        initShowWindowLike()
    }

    private fun initShowWindowLike() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        headerView.recyclerViewShowWindow.setHasFixedSize(true)
        headerView.recyclerViewShowWindow.layoutManager = linearLayoutManager
        headerView.recyclerViewShowWindow.adapter = adapterLikeShowWindow
        headerView.recyclerViewShowWindow.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置喜欢的橱窗数据
     */
    override fun setShowWindowData(shop_windows: List<DiscoverLifeBean.DataBean.ShopWindowsBean>) {
        var demos = ArrayList<DiscoverLifeBean.DataBean.ShopWindowsBean>()

        for (i in 0..3) {
            val windowsBean = DiscoverLifeBean.DataBean.ShopWindowsBean()
            demos.add(windowsBean)
        }

        for (item in demos) {
            item.title = "发现生活美学"
            item.description = "一个让你爱不释手的包包阿时间 和阿花代姐哈就"
            val list = ArrayList<DiscoverLifeBean.DataBean.ShopWindowsBean.ProductsBean>()
            for (i in 0..3) {
                val productsBean = DiscoverLifeBean.DataBean.ShopWindowsBean.ProductsBean()
                productsBean.cover = "http://c.hiphotos.baidu.com/image/h%3D300/sign=87d6daed02f41bd5c553eef461d881a0/f9198618367adab4b025268587d4b31c8601e47b.jpg"
                list.add(productsBean)
            }
            item.products = list
            item.avatar = "http://imgtu.5011.net/uploads/content/20170209/4934501486627131.jpg"
        }

        adapterLikeShowWindow.setNewData(demos)
//        adapterLikeShowWindow.setNewData(shop_windows)
    }

    override fun setPresenter(presenter: FavoriteContract.Presenter?) {
        setPresenter(presenter)
    }

    /**
     * 喜欢的商品
     */
    private fun initGoodsLike() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        headerView.recyclerViewGoodsLike.setHasFixedSize(true)
        headerView.recyclerViewGoodsLike.layoutManager = linearLayoutManager
        headerView.recyclerViewGoodsLike.adapter = adapterLikeGoods
        headerView.recyclerViewGoodsLike.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置猜你喜欢界面
     */
    override fun setGoodsLikeData(products: List<ProductBean>) {
        adapterLikeGoods.setNewData(products)
    }



    override fun installListener() {

    }



    override fun loadData() {
        presenter.getUserGoodsLike()

        presenter.getShowWindowLike()
//        page = 1
//        presenter.loadData("", page)
    }

    override fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {
//        adapter.setNewData(data)
//        ++page
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