package com.lexivip.lexi.index.explore.goodsClassify

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.index.explore.GoodsClassBean
import com.lexivip.lexi.search.AdapterSearchGoods
import com.lexivip.lexi.search.SearchActivity
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.activity_goods_classsify.*
import kotlinx.android.synthetic.main.header_classify_goods.*


class GoodsClassifyActivity : BaseActivity(), GoodsClassifyContract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    private var goodsCount = 0
    private val presenter: GoodsClassifyPresenter by lazy { GoodsClassifyPresenter(this) }
    private val list: ArrayList<AdapterSearchGoods.MultipleItem> by lazy { ArrayList<AdapterSearchGoods.MultipleItem>() }
    private val adapter: AdapterSearchGoods by lazy { AdapterSearchGoods(list) }

    private var dialogBottomFilter: DialogBottomFilter? = null
    private var dialogBottomSynthesiseSort: DialogBottomSynthesiseSort? = null
    override val layout: Int = R.layout.activity_goods_classsify

    private lateinit var categoriesBean: GoodsClassBean.DataBean.CategoriesBean

    override fun setPresenter(presenter: GoodsClassifyContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun getIntentData() {
        categoriesBean = intent.getParcelableExtra(TAG)
    }

    override fun initView() {
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        customHeadView.setHeadCenterTxtShow(true, categoriesBean.name)
        customHeadView.setHeadShopShow(true)
        val gridLayoutManager = GridLayoutManager(AppApplication.getContext(), 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        val colorWhite = Util.getColor(android.R.color.white)
        recyclerView.setBackgroundColor(colorWhite)
        adapter.setSpanSizeLookup { _, position ->
            adapter.data[position].spanSize
        }
        recyclerView.addItemDecoration(DividerItemDecoration(AppApplication.getContext()))
        textViewNewProductFilter.isSelected = false
        val headerView = View(this)
        headerView.setPadding(0, DimenUtil.dp2px(10.0), 0, 0)
        adapter.addHeaderView(View(this))
    }


    override fun setGoodsCount(count: Int) {
        goodsCount = count
        if (dialogBottomFilter != null && dialogBottomFilter!!.isShowing) dialogBottomFilter!!.setGoodsCount(count)
    }

    override fun installListener() {

        imageButton.setOnClickListener { //返回顶部
            recyclerView.smoothScrollToPosition(0)
        }

        //点击搜索
        customHeadView.headRightShop.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        //新品
        textViewNewProductFilter.setOnClickListener {
            val sort_newest: String
            if (textViewNewProductFilter.isSelected) {
                sort_newest = "0"
                textViewNewProductFilter.isSelected = false
                textViewNewProductFilter.setTextColor(Util.getColor(R.color.color_555))
            } else {
                sort_newest = "1"
                textViewNewProductFilter.isSelected = true
                textViewNewProductFilter.setTextColor(Util.getColor(R.color.color_6ed7af))

            }
            val page = 1
            val sortType = presenter.getSortType()
            val cids = presenter.getCids()
            val minPrice = presenter.getMinPrice()
            val maxPrice = presenter.getMaxPrice()
            val id = presenter.getClassifyId()
            val is_free_postage = presenter.isFreePostage()
            val is_preferential = presenter.isPreferential()
            val is_custom_made = presenter.isCustomMade()
            presenter.loadData(page, sortType, minPrice, maxPrice, cids, is_free_postage, is_preferential, is_custom_made, sort_newest, id)

        }

        //排序
        linearLayoutSort.setOnClickListener {
            Util.startViewRotateAnimation(imageViewSortArrow0, 0f, 180f)
            if (dialogBottomSynthesiseSort == null) dialogBottomSynthesiseSort = DialogBottomSynthesiseSort(this, presenter)
            dialogBottomSynthesiseSort?.setOnDismissListener {
                Util.startViewRotateAnimation(imageViewSortArrow0, -180f, 0f)
                when (presenter.getSortType()) {
                    GoodsClassifyPresenter.SORT_TYPE_SYNTHESISE -> textViewSort.text = Util.getString(R.string.text_sort_synthesize)
                    GoodsClassifyPresenter.SORT_TYPE_LOW_UP -> textViewSort.text = Util.getString(R.string.text_price_low_up)
                    GoodsClassifyPresenter.SORT_TYPE_UP_LOW -> textViewSort.text = Util.getString(R.string.text_price_up_low)
                }
            }
            dialogBottomSynthesiseSort?.show()
        }

        linearLayoutFilter.setOnClickListener {
            Util.startViewRotateAnimation(imageViewSortArrow2, 0f, 180f)
            if (dialogBottomFilter == null) dialogBottomFilter = DialogBottomFilter(this, presenter, categoriesBean.id)
            dialogBottomFilter?.show()
            dialogBottomFilter?.setOnDismissListener {
                Util.startViewRotateAnimation(imageViewSortArrow2, -180f, 0f)
            }
            dialogBottomFilter?.setGoodsCount(goodsCount)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true, categoriesBean.id)
        }

        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) as AdapterSearchGoods.MultipleItem
            val intent = Intent(this, GoodsDetailActivity::class.java)
            intent.putExtra(GoodsDetailActivity::class.java.simpleName, item.product)
            startActivity(intent)
        }

    }

    override fun requestNet() {
        presenter.loadData(false, categoriesBean.id)
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

    override fun setNewData(data: List<ProductBean>) {
        adapter.setNewData(formatData(data))
        swipeRefreshLayout.isRefreshing = false
    }

    override fun addData(products: MutableList<ProductBean>) {
        adapter.addData(formatData(products))
        adapter.setEnableLoadMore(true)
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
                            .setLeftSideLine(true, color, 15f, 0f, 0f)
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
                                .setLeftSideLine(true, color, 15f, 0f, 0f)
                                .create()
                    }
                }
            }
            return divider
        }
    }

}
