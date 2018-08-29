package com.thn.lexi.mine
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_favorite_shop.*

class FavoriteShopFragment : BaseFragment(), FavoriteDesignContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_recyclerview
    private lateinit var presenter: FavoriteDesignPresenter
    private val adapter: AdapterDesignPavilion by lazy { AdapterDesignPavilion(R.layout.adapter_design_pavilion) }

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteShopFragment = FavoriteShopFragment()
    }

    override fun initView() {
        presenter = FavoriteDesignPresenter(this)
//        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
//        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        val view = View(activity)
        adapter.addHeaderView(view)
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
    }

    override fun setPresenter(presenter: FavoriteDesignContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {

//        swipeRefreshLayout.setOnRefreshListener {
//            adapter.setEnableLoadMore(false)
//            loadData()
//        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        },recyclerView)
    }



    override fun loadData() {
        presenter.loadData()
    }



    override fun setNewData(data: MutableList<DesignPavilionBean>) {
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
    }


    override fun addData(products: MutableList<DesignPavilionBean>) {
        adapter.addData(products)
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }


    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
//        swipeRefreshLayout.isRefreshing = false
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

    internal inner class DividerItemDecoration(context: Context) : Y_DividerItemDecoration(context) {
        private val color:Int = Util.getColor(R.color.color_f5f7f9)
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter.itemCount
            var divider: Y_Divider? = null
            when (itemPosition) {
                count - 2 -> {

                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                count - 1 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }

                else -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, 11f, 0f, 0f)
                            .create()
                }
            }

            return divider
        }
    }

}