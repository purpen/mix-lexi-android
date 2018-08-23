package com.thn.lexi.selectionGoodsCenter

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.thn.lexi.R
import kotlinx.android.synthetic.main.fragment_all_goods.*

class AllGoodsFragment : BaseFragment(), AllGoodsContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    override val layout: Int = R.layout.fragment_all_goods

    private val dialogBottomFilter: DialogBottomFilter by lazy { DialogBottomFilter(activity, presenter) }

    private val presenter: AllGoodsPresenter by lazy { AllGoodsPresenter(this) }

    private var firstLoadData: Boolean = true

    private val adapter: AdapterAllGoods by lazy { AdapterAllGoods(R.layout.adapter_all_goods) }

    companion object {
        @JvmStatic
        fun newInstance(): AllGoodsFragment = AllGoodsFragment()
    }

    override fun initView() {
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        val headerView = View(context)
        headerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp10))
        headerView.setBackgroundColor(Util.getColor(R.color.color_f6f5f5))
        adapter.setHeaderView(headerView)
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) return
                if ((position - 1) % 2 == 0) {
                    outRect.right = DimenUtil.getDimensionPixelSize(R.dimen.dp5)
                    outRect.left = 0
                } else {
                    outRect.right = 0
                    outRect.left = DimenUtil.getDimensionPixelSize(R.dimen.dp5)
                }
                outRect.bottom = DimenUtil.getDimensionPixelSize(R.dimen.dp20)
            }
        })
    }

    override fun setPresenter(presenter: AllGoodsContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun loadData() {
        firstLoadData = false
        presenter.loadData(false)
    }

    override fun setNewData(products: MutableList<HotGoodsBean.DataBean.ProductsBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(products)
        adapter.setEnableLoadMore(true)
    }

    override fun addData(products: List<HotGoodsBean.DataBean.ProductsBean>) {
        adapter.addData(products)
    }

    override fun setGoodsCount(count: Int) {
        if (dialogBottomFilter.isShowing) dialogBottomFilter.setGoodsCount(count)
    }


    override fun installListener() {

        linearLayoutSort.setOnClickListener { _ ->
            Util.startViewRotateAnimation(imageViewSortArrow0, 0f, 180f)
            val dialog = DialogBottomSynthesiseSort(activity, presenter)
            dialog.setOnDismissListener {
                Util.startViewRotateAnimation(imageViewSortArrow0, -180f, 0f)
                when (presenter.getSortType()) {
                    AllGoodsPresenter.SORT_TYPE_SYNTHESISE -> textViewSort.text = Util.getString(R.string.text_sort_synthesize)
                    AllGoodsPresenter.SORT_TYPE_LOW_UP -> textViewSort.text = Util.getString(R.string.text_price_low_up)
                    AllGoodsPresenter.SORT_TYPE_UP_LOW -> textViewSort.text = Util.getString(R.string.text_price_up_low)
                }
            }
            dialog.show()
        }

        linearLayoutProfit.setOnClickListener { _ ->
            Util.startViewRotateAnimation(imageViewSortArrow1, 0f, 180f)
            val dialog = DialogBottomProfit(activity, presenter)
            dialog.setOnDismissListener {
                Util.startViewRotateAnimation(imageViewSortArrow1, -180f, 0f)
                when (presenter.getSortType()) {
                    AllGoodsPresenter.PROFIT_TYPE_DEFAULT -> textViewProfit.text = Util.getString(R.string.text_no_limit)
                    AllGoodsPresenter.SORT_TYPE_LOW_UP -> textViewProfit.text = Util.getString(R.string.text_price_low_up)
                    AllGoodsPresenter.SORT_TYPE_UP_LOW -> textViewProfit.text = Util.getString(R.string.text_price_up_low)
                }
            }
            dialog.show()
        }


        linearLayoutFilter.setOnClickListener { _ ->
            Util.startViewRotateAnimation(imageViewSortArrow2, 0f, 180f)
            dialogBottomFilter.show()
            dialogBottomFilter.setOnDismissListener {
                Util.startViewRotateAnimation(imageViewSortArrow2, -180f, 0f)
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

        adapter.setOnItemChildClickListener { adapter, view, position ->
            val productsBean = adapter.getItem(position) as HotGoodsBean.DataBean.ProductsBean
            when (view.id) {
                R.id.textView4 -> ToastUtil.showInfo("卖")
                R.id.textView5 -> ToastUtil.showInfo("上架")
//                R.id.linearLayoutLoadMore -> presenter.loadMoreData(page)
            }
        }
    }



    override fun showLoadingView() {
        if (firstLoadData) dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
        swipeRefreshLayout.isRefreshing = false
        adapter.loadMoreFail()
    }

    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun goPage() {

    }
}