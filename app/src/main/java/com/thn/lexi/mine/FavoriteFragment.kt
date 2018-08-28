package com.thn.lexi.mine
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.Constants
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.index.explore.EditorRecommendBean
import com.thn.lexi.index.selection.GoodsAdapter
import com.thn.lexi.index.selection.GoodsData
import kotlinx.android.synthetic.main.fragment_mine_favorite.*

class FavoriteFragment : BaseFragment(), FavoriteContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    private val adapterLikeGoods: AdapterLikeGoods by lazy { AdapterLikeGoods(R.layout.adapter_pure_imageview) }

    override val layout: Int = R.layout.fragment_mine_favorite
    private lateinit var presenter: FavoritePresenter
    private var page: Int = 1
    private lateinit var adapter: AdapterLikeGoods

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteFragment = FavoriteFragment()
    }

    override fun initView() {
        presenter = FavoritePresenter(this)

        initGuessLike()
    }

    override fun setPresenter(presenter: FavoriteContract.Presenter?) {
        setPresenter(presenter)
    }

    /**
     * 初始化猜你喜欢
     */
    private fun initGuessLike() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewGoodsLike.setHasFixedSize(true)
        recyclerViewGoodsLike.layoutManager = linearLayoutManager
        recyclerViewGoodsLike.adapter = adapterLikeGoods
        recyclerViewGoodsLike.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(), LinearLayoutManager.HORIZONTAL, resources.getDimensionPixelSize(R.dimen.dp10), Util.getColor(android.R.color.transparent)))
    }

    /**
     * 设置猜你喜欢界面
     */
//    override fun setGoodsLikeData(products: List<EditorRecommendBean.DataBean.ProductsBean>) {
//        adapterLikeGoods.setNewData(products)
//    }



    override fun installListener() {

    }



    override fun loadData() {
        presenter.getUserGoodsLike()
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