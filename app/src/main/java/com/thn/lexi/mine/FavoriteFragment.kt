package com.thn.lexi.mine
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.basemodule.tools.Constants
import com.basemodule.tools.LogUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.MainFragment3
import com.thn.lexi.R
import com.thn.lexi.RecyclerViewDivider
import com.thn.lexi.goods.CharacteristicContract
import com.thn.lexi.goods.CharacteristicPresenter
import com.thn.lexi.goods.GoodsAdapter
import com.thn.lexi.goods.GoodsData
import kotlinx.android.synthetic.main.fragment_mine_favorite.*

class FavoriteFragment : BaseFragment(), CharacteristicContract.View {
    private lateinit var headView: View
    private var isSetScroll:Boolean?=null
    private val dialog: WaitingDialog? by lazy { WaitingDialog(activity) }
    override val layout: Int = R.layout.fragment_mine_favorite
    private lateinit var presenter: CharacteristicPresenter
    private var page: Int = 1
    private lateinit var adapter: GoodsAdapter

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteFragment = FavoriteFragment()
    }

    override fun initView() {
        presenter = CharacteristicPresenter(this)
        adapter = GoodsAdapter(R.layout.layout_goods_adapter)

        headView = View(activity)
        headView.background = ColorDrawable(Color.TRANSPARENT)
        headView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,resources.getDimensionPixelSize(R.dimen.dp278))
//        adapter.addHeaderView(headView)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_6ed7af))
        swipeRefreshLayout.isRefreshing = false
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecyclerViewDivider(AppApplication.getContext(),LinearLayoutManager.VERTICAL,resources.getDimensionPixelSize(R.dimen.dp10),resources.getColor(R.color.color_d1d1d1)))
    }

    override fun setPresenter(presenter: CharacteristicContract.Presenter?) {
        setPresenter(presenter)
    }


    override fun installListener() {

        recyclerView.addOnScrollListener(object : OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
//                if (parentFragment is MainFragment3) (parentFragment as MainFragment3).onScrollStateChanged(recyclerView,newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

//                if (parentFragment is MainFragment3) (parentFragment as MainFragment3).onScrolled(recyclerView, dx, dy)
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            adapter.setEnableLoadMore(false)
            loadData()
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData("",page)
        },recyclerView)
    }



    override fun loadData() {
        page = 1
        presenter.loadData("", page)
    }

    override fun setNewData(data: List<GoodsData.DataBean.ProductsBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(data)
        adapter.setEnableLoadMore(true)
        showEndView()
        ++page
    }

    override fun addData(products: List<GoodsData.DataBean.ProductsBean>) {
        adapter.addData(products)
        ++page
        showEndView()
    }

    private fun showEndView() {
        if (adapter.data.size < Integer.valueOf(Constants.PAGE_SIZE)) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter.loadMoreEnd(false)

        } else {
            adapter.loadMoreComplete()
        }
    }

    override fun showLoadingView() {
        if (!swipeRefreshLayout.isRefreshing) dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun showError(string: String) {
        swipeRefreshLayout.isRefreshing = false
        ToastUtil.showError(string)
    }

    override fun goPage() {

    }

    fun setScrollHeight(distance: Float, isSetScroll: Boolean) {
//        val deltaPadding = resources.getDimensionPixelSize(R.dimen.dp278) - distance
//        headView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,deltaPadding.toInt())
    }
}