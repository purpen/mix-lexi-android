package com.thn.lexi.search

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.thn.lexi.R
import com.basemodule.ui.BaseFragment
import com.thn.lexi.AppApplication
import com.thn.lexi.beans.ProductBean
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class FragmentSearchGoods :BaseFragment(),SearchGoodsContract.View{

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
        val gridLayoutManager = GridLayoutManager(AppApplication.getContext(),2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter

        adapter.setSpanSizeLookup { _, position ->
            adapter.data[position].spanSize
        }
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
        presenter.loadData(false,"好")
    }

    override fun setNewData(data: List<ProductBean>) {
        adapter.setNewData(formatData(data))
    }

    /**
     * 根据角标整理数据
     */
    private fun formatData(data: List<ProductBean>):ArrayList<AdapterSearchGoods.MultipleItem> {
        val curList = ArrayList<AdapterSearchGoods.MultipleItem>()
        val size = data.size -1
        for (i in 0..size) {
            if (i==4 || i==9) {
                curList.add(AdapterSearchGoods.MultipleItem(data[i], AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN2,AdapterSearchGoods.MultipleItem.ITEM_SPAN2_SIZE))
            } else{
                curList.add(AdapterSearchGoods.MultipleItem(data[i], AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN1,AdapterSearchGoods.MultipleItem.ITEM_SPAN1_SIZE))
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
}