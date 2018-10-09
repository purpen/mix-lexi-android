package com.thn.lexi.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.*
import com.thn.lexi.R
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.beans.ProductBean
import com.thn.lexi.index.detail.GoodsDetailActivity
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_search_goods.*


class FragmentSearchGoods : BaseFragment(), SearchGoodsContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val presenter: SearchGoodsPresenter by lazy { SearchGoodsPresenter(this) }

    private var dialogBottomFilter: DialogBottomFilter? = null

    private val list: ArrayList<AdapterSearchGoods.MultipleItem> by lazy { ArrayList<AdapterSearchGoods.MultipleItem>() }

    private val adapter: AdapterSearchGoods by lazy { AdapterSearchGoods(list) }

    private var searchString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchString = arguments?.getString(FragmentSearchGoods::class.java.simpleName)
    }

    companion object {
        @JvmStatic
        fun newInstance(searchString: String): FragmentSearchGoods {
            val fragment = FragmentSearchGoods()
            val bundle = Bundle()
            bundle.putString(FragmentSearchGoods::class.java.simpleName, searchString)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layout: Int = R.layout.fragment_search_goods

    override fun initView() {
        val gridLayoutManager = GridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        recyclerView.setPadding(DimenUtil.dp2px(15.0), 0, DimenUtil.dp2px(15.0), 0)
        val colorWhite = Util.getColor(android.R.color.white)
        recyclerView.setBackgroundColor(colorWhite)
        adapter.setSpanSizeLookup { _, position ->
            adapter.data[position].spanSize
        }
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
        val headerView = View(activity)
        headerView.setBackgroundColor(colorWhite)
        adapter.setHeaderView(headerView)
    }


    override fun installListener() {
        linearLayoutSort.setOnClickListener { _ ->
            Util.startViewRotateAnimation(imageViewSortArrow0, 0f, 180f)
            val dialog = DialogBottomSynthesiseSort(activity, presenter)
            dialog.setOnDismissListener {
                Util.startViewRotateAnimation(imageViewSortArrow0, -180f, 0f)
                when (presenter.getSortType()) {
                    SearchGoodsPresenter.SORT_TYPE_SYNTHESISE -> textViewSort.text = Util.getString(R.string.text_sort_synthesize)
                    SearchGoodsPresenter.SORT_TYPE_LOW_UP -> textViewSort.text = Util.getString(R.string.text_price_low_up)
                    SearchGoodsPresenter.SORT_TYPE_UP_LOW -> textViewSort.text = Util.getString(R.string.text_price_up_low)
                }
            }
            dialog.show()
        }

        linearLayoutFilter.setOnClickListener { _ ->
            Util.startViewRotateAnimation(imageViewSortArrow2, 0f, 180f)
            dialogBottomFilter = DialogBottomFilter(activity, presenter)
            dialogBottomFilter?.show()
            dialogBottomFilter?.setOnDismissListener {
                Util.startViewRotateAnimation(imageViewSortArrow2, -180f, 0f)
            }
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) as AdapterSearchGoods.MultipleItem
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item.product)
            startActivity(intent)
        }
    }

    /**
     * 设置符合条件商品数量
     */
    override fun setGoodsCount(count: Int) {
        if (dialogBottomFilter != null && dialogBottomFilter!!.isShowing) dialogBottomFilter?.setGoodsCount(count)
    }

    override fun loadData() {
        if (TextUtils.isEmpty(searchString)) return
        presenter.loadData(false, searchString!!)
    }

    override fun setNewData(data: List<ProductBean>) {
        adapter.setNewData(formatData(data))
    }

    /**
     * 根据角标整理数据
     */
    private fun formatData(data: List<ProductBean>): ArrayList<AdapterSearchGoods.MultipleItem> {
        val curList = ArrayList<AdapterSearchGoods.MultipleItem>()
        val size = data.size - 1
        for (i in 0..size) {
            if (i == 4 || i == 9) {
                curList.add(AdapterSearchGoods.MultipleItem(data[i], AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN2, AdapterSearchGoods.MultipleItem.ITEM_SPAN2_SIZE))
            } else {
                if (i < 4 && i % 2 == 1 || i in 5..8 && i % 2 == 0) {
                    data[i].isRight = true
                }
                curList.add(AdapterSearchGoods.MultipleItem(data[i], AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN1, AdapterSearchGoods.MultipleItem.ITEM_SPAN1_SIZE))
            }

        }
        return curList
    }

    override fun addData(products: MutableList<ProductBean>) {
        adapter.addData(formatData(products))
    }


    override fun loadMoreComplete() {
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun loadMoreFail() {
        adapter.loadMoreFail()
    }

    override fun setPresenter(presenter: SearchGoodsContract.Presenter?) {
        setPresenter(presenter)
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

    private inner class DividerItemDecoration constructor(context: Context) : Y_DividerItemDecoration(context) {
        private val color: Int = Util.getColor(android.R.color.white)
        private val height = 20f
        override fun getDivider(itemPosition: Int): Y_Divider? {
            val count = adapter.itemCount
            var divider: Y_Divider? = null
            when (itemPosition) {
                0 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, 10f, 0f, 0f)
                            .create()
                    return divider
                }
                count - 2 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, height, 0f, 0f)
                            .create()
                }

                count - 1 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(true, color, height, 0f, 0f)
                            .create()
                }
                else -> {
                    val item = adapter.getItem(itemPosition - 1) as AdapterSearchGoods.MultipleItem
                    if (item.product.isRight) {
                        divider = Y_DividerBuilder()
                                .setBottomSideLine(true, color, height, 0f, 0f)
                                .setLeftSideLine(true, color, 10f, 0f, 0f)
                                .create()
                    } else {
                        divider = Y_DividerBuilder()
                                .setBottomSideLine(true, color, height, 0f, 0f)
                                .create()
                    }
                }
            }
            return divider
        }
    }
}