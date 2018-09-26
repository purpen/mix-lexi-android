package com.thn.lexi.search

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.tools.WaitingDialog
import com.thn.lexi.R
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.beans.ProductBean
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration


class FragmentSearchGoods : BaseFragment(), SearchGoodsContract.View {

    private val dialog: WaitingDialog by lazy { WaitingDialog(activity) }

    private val presenter: SearchGoodsPresenter by lazy { SearchGoodsPresenter(this) }

    private val list: ArrayList<AdapterSearchGoods.MultipleItem> by lazy { ArrayList<AdapterSearchGoods.MultipleItem>() }

    private val adapter: AdapterSearchGoods by lazy { AdapterSearchGoods(list) }


    companion object {
        fun newInstance(): FragmentSearchGoods {
            return FragmentSearchGoods()
        }
    }

    override val layout: Int = R.layout.fragment_recyclerview

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
        headerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DimenUtil.dp2px(10.0))
        adapter.setHeaderView(headerView)
    }


    override fun installListener() {
        adapter.setOnLoadMoreListener({
            presenter.loadMoreData()
        }, recyclerView)

        adapter.setOnItemClickListener { adapter, view, position ->
            //            ToastUtil.showInfo("跳转用户")
//            val showWindowBean = adapter.getItem(position) as ShowWindowBean.DataBean.ShopWindowsBean
//            val intent = Intent(context, ShowWindowDetailActivity::class.java)
//            intent.putExtra(ShowWindowDetailActivity::class.java.simpleName, showWindowBean)
//            startActivity(intent)
        }
    }

    override fun loadData() {
        presenter.loadData(false, "好")
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
        super.loadMoreFail()
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
                0 ->{
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, 0f, 0f, 0f)
                            .create()
                }
                count - 2 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, height, 0f, 0f)
                            .create()
                }

                count - 1 -> {
                    divider = Y_DividerBuilder()
                            .setBottomSideLine(false, color, height, 0f, 0f)
                            .create()
                }
                else -> {
                    val item = adapter.getItem(itemPosition-1) as AdapterSearchGoods.MultipleItem
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