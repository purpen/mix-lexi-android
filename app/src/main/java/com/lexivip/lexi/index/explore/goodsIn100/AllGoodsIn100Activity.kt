package com.lexivip.lexi.index.explore.goodsIn100

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.basemodule.tools.*
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.ImageSizeConfig
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import com.lexivip.lexi.index.detail.GoodsDetailActivity
import com.lexivip.lexi.index.lifehouse.LookPeopleBean
import com.lexivip.lexi.index.selection.HeadImageAdapter
import com.lexivip.lexi.search.AdapterSearchGoods
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.acticity_all_editor_recommend.*
import kotlinx.android.synthetic.main.header_all_editor_recommend.view.*


class AllGoodsIn100Activity : BaseActivity(), AllGoodIn100Contract.View {
    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    private var goodsCount = 0
    private val presenter: AllGoodsIn100Presenter by lazy { AllGoodsIn100Presenter(this) }
    private val list: ArrayList<AdapterSearchGoods.MultipleItem> by lazy { ArrayList<AdapterSearchGoods.MultipleItem>() }
    private val adapter: AdapterSearchGoods by lazy { AdapterSearchGoods(list) }
    private var dialogBottomSynthesiseSort: DialogBottomSynthesiseSort? = null
    private var dialogBottomFilter: DialogBottomFilter? = null
    override val layout: Int = R.layout.acticity_all_editor_recommend

    private lateinit var headerView: View

    override fun setPresenter(presenter: AllGoodIn100Contract.Presenter?) {
        setPresenter(presenter)
    }

    override fun initView() {
        linearLayout.visibility = View.VISIBLE
        swipeRefreshLayout.setColorSchemeColors(Util.getColor(R.color.color_6ed7af))
        customHeadView.setHeadCenterTxtShow(true, R.string.text_explore_good100)
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

        initHeaderView()
    }

    /**
     * 初始化头布局
     */
    private fun initHeaderView() {
        presenter.getLookPeople()
        headerView = View.inflate(this, R.layout.header_all_editor_recommend, null)
        GlideUtil.loadImageWithDimenAndRadius(R.mipmap.icon_bg_header_goods_in100, headerView.imageViewBg, 0, ScreenUtil.getScreenWidth(), DimenUtil.dp2px(153.0), ImageSizeConfig.DEFAULT)
        headerView.textViewHeadTitle.text = Util.getString(R.string.text_explore_good100)
        headerView.textViewHeadTitle.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_good_in100_head_title, 0, 0, 0)
        adapter.setHeaderView(headerView)
    }

    /**
     * 设置看过的用户信息
     */
    override fun setLookPeopleData(data: LookPeopleBean.DataBean) {
        if (data.count < 999) {
            headerView.textViewHeaders.text = "${data.count}"
        } else {
            headerView.textViewHeaders.textViewHeaders.text = "+999"
        }

        val urlList = ArrayList<String>()
        for (item in data.users) {
            urlList.add(item.avatar)
        }

        //反转头像
        urlList.reverse()

        val recyclerView = headerView.recyclerViewHeader

        //反转布局
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        val headImageAdapter = HeadImageAdapter(R.layout.item_head_imageview)
        recyclerView.adapter = headImageAdapter
        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                private val dp5 = DimenUtil.dp2px(5.0)
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    if (parent.getChildAdapterPosition(view) >= 0 && parent.getChildAdapterPosition(view) != urlList.size - 1) {
                        outRect.left = -dp5
                    }
                }
            })
        }

        headImageAdapter.setNewData(urlList)
    }

    override fun setGoodsCount(count: Int) {
        goodsCount = count
        if (dialogBottomFilter != null && dialogBottomFilter!!.isShowing) dialogBottomFilter!!.setGoodsCount(count)
    }

    override fun installListener() {
        linearLayoutSort.setOnClickListener {
            Util.startViewRotateAnimation(imageViewSortArrow0, 0f, 180f)
            if (dialogBottomSynthesiseSort == null) dialogBottomSynthesiseSort = DialogBottomSynthesiseSort(this, presenter)
            dialogBottomSynthesiseSort?.setOnDismissListener {
                Util.startViewRotateAnimation(imageViewSortArrow0, -180f, 0f)
                when (presenter.getSortType()) {
                    AllGoodsIn100Presenter.SORT_TYPE_SYNTHESISE -> textViewSort.text = Util.getString(R.string.text_sort_synthesize)
                    AllGoodsIn100Presenter.SORT_TYPE_LOW_UP -> textViewSort.text = Util.getString(R.string.text_price_low_up)
                    AllGoodsIn100Presenter.SORT_TYPE_UP_LOW -> textViewSort.text = Util.getString(R.string.text_price_up_low)
                }
            }
            dialogBottomSynthesiseSort?.show()
        }

        linearLayoutFilter.setOnClickListener {
            Util.startViewRotateAnimation(imageViewSortArrow2, 0f, 180f)
            if (dialogBottomFilter == null) dialogBottomFilter = DialogBottomFilter(this, presenter)
            dialogBottomFilter?.show()
            dialogBottomFilter?.setOnDismissListener {
                Util.startViewRotateAnimation(imageViewSortArrow2, -180f, 0f)
            }
            dialogBottomFilter?.setGoodsCount(goodsCount)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.setEnableLoadMore(false)
            presenter.loadData(true)
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
        presenter.loadData(false)
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
