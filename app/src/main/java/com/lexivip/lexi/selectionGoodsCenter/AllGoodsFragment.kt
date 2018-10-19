package com.lexivip.lexi.selectionGoodsCenter

import android.content.Intent
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseFragment
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import kotlinx.android.synthetic.main.fragment_all_goods.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AllGoodsFragment : BaseFragment(), AllGoodsContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    override val layout: Int = R.layout.fragment_all_goods

    private val dialogBottomFilter: DialogBottomFilter by lazy { DialogBottomFilter(activity, presenter) }

    private val presenter: AllGoodsPresenter by lazy { AllGoodsPresenter(this) }

    private var firstLoadData: Boolean = true
    private var goodsCount=0
    private val adapter: AdapterAllGoods by lazy { AdapterAllGoods(R.layout.adapter_all_goods) }

    companion object {
        @JvmStatic
        fun newInstance(): AllGoodsFragment = AllGoodsFragment()
    }

    override fun initView() {
        EventBus.getDefault().register(this)
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

    override fun setNewData(products: MutableList<ProductBean>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.setNewData(products)
        adapter.setEnableLoadMore(true)
    }

    override fun addData(products: List<ProductBean>) {
        adapter.addData(products)
    }

    override fun setGoodsCount(count: Int) {
        goodsCount = count
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
            dialogBottomFilter.setGoodsCount(goodsCount)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

        //商品点击
        adapter.setOnItemClickListener { _, _, position ->
            val productsBean = adapter.getItem(position)
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName,productsBean)
            startActivity(intent)
        }

        adapter.setOnItemChildClickListener { _, view, position ->
            val productsBean = adapter.getItem(position) as ProductBean
            when (view.id) {
                R.id.textView4 -> ToastUtil.showInfo("卖")
                R.id.textView5 -> {
                    val intent = Intent(activity,PutAwayActivity::class.java)
                    intent.putExtra(PutAwayActivity::class.java.simpleName,productsBean)
                    startActivity(intent)
                }
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

    //上架成功刷新adapter
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun putAwaySuccess(productBean: ProductBean) {
        for (item in adapter.data){
            if (TextUtils.equals(item.rid,productBean.rid)){
                item.have_distributed = true
                adapter.notifyDataSetChanged()
                break
            }
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}