package com.lexivip.lexi.mine.enshrine.recentLook

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.Constants
import com.lexivip.lexi.JsonUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.lexivip.lexi.selectionGoodsCenter.AdapterGoodsClassify
import com.lexivip.lexi.selectionGoodsCenter.GoodsClassifyBean
import kotlinx.android.synthetic.main.dialog_filter_sort_bottom.view.*
import java.io.IOException

class DialogBottomFilter(context: FragmentActivity?, presenter: AllRecentLookGoodsPresenter) : BottomBaseDialog<DialogBottomFilter>(context) {
    private lateinit var view: View
    private val present: AllRecentLookGoodsPresenter = presenter
    private val adapter: AdapterGoodsClassify by lazy { AdapterGoodsClassify(R.layout.adapter_text_border) }
    private val adapterRecommend: AdapterGoodsClassify by lazy { AdapterGoodsClassify(R.layout.adapter_text_border) }
    private var minPrice: String = ""

    private var maxPrice: String = ""


    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_filter_sort_bottom, null)
        view.relativeLayoutRecommend.visibility = View.VISIBLE
        setRangeSeekBar()
        return view
    }

    private fun setRangeSeekBar(): View {
        val list = listOf("￥0", "￥150", "￥300", "￥400", "￥500", "￥800", "不限")
        view.rangeSeekBarView.setData(list) { leftPostion, rightPostion ->

            val page = 1

            if (leftPostion == list.size - 1) {
                minPrice = ""
            } else {
                minPrice = list[leftPostion].substring(1)
            }

            if (rightPostion == list.size - 1) {
                maxPrice = ""
            } else {
                maxPrice = list[rightPostion].substring(1)
            }
            //            LogUtil.e("minPrice==" + minPrice + ";maxPrice==" + maxPrice)
            val sortType = ""
            val cids = getSelectedItem()
            present.loadData(page, sortType, minPrice, maxPrice, cids, "", "", "", "")
        }

        return view
    }

    override fun onViewCreated(inflate: View?) {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        view.recyclerView.setHasFixedSize(false)
        view.recyclerView.layoutManager = manager
        view.recyclerView.adapter = adapter
        present.getGoodsClassify(object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val goodsClassifyBean = JsonUtil.fromJson(json, GoodsClassifyBean::class.java)
                if (goodsClassifyBean.status.code == Constants.SUCCESS) {
                    val categories = goodsClassifyBean.data.categories
                    adapter.setNewData(categories)
                } else {
                    ToastUtil.showError(goodsClassifyBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                ToastUtil.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        view.recyclerViewRecommend.setHasFixedSize(false)
        view.recyclerViewRecommend.layoutManager = linearLayoutManager
        view.recyclerViewRecommend.adapter = adapterRecommend

        val list = ArrayList<GoodsClassifyBean.DataBean.CategoriesBean>()
        val categoriesBean0 = GoodsClassifyBean.DataBean.CategoriesBean()
        categoriesBean0.name = "包邮"
        list.add(categoriesBean0)

        val categoriesBean1 = GoodsClassifyBean.DataBean.CategoriesBean()
        categoriesBean1.name = "特惠"
        list.add(categoriesBean1)

        val categoriesBean2 = GoodsClassifyBean.DataBean.CategoriesBean()
        categoriesBean2.name = "可订制"
        list.add(categoriesBean2)

        adapterRecommend.setNewData(list)

        installListener()
    }


    private fun installListener() {
        //分类点击
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) as GoodsClassifyBean.DataBean.CategoriesBean
            item.selected = !item.selected
            adapter.notifyItemChanged(position)
            val page = 1
            val cids = getSelectedItem()
            val sortType = ""
            val categoriesBean0 = adapterRecommend.getItem(0) as GoodsClassifyBean.DataBean.CategoriesBean
            val isFreePostage = if(categoriesBean0.selected) "1" else "0"

            val categoriesBean1 = adapterRecommend.getItem(1) as GoodsClassifyBean.DataBean.CategoriesBean
            val isPreferential = if(categoriesBean1.selected) "1" else "0"

            val categoriesBean2 = adapterRecommend.getItem(2) as GoodsClassifyBean.DataBean.CategoriesBean
            val isCustomMade = if(categoriesBean2.selected) "1" else "0"
            present.loadData(page, sortType, minPrice, maxPrice, cids, isFreePostage, isPreferential, isCustomMade, "")
        }

        //  推荐点击
        adapterRecommend.setOnItemClickListener { _, _, position ->
            val item = adapterRecommend.getItem(position) as GoodsClassifyBean.DataBean.CategoriesBean
            item.selected = !item.selected
            adapterRecommend.notifyItemChanged(position)
            val page = 1
            val cids = getSelectedItem()
            val sortType = ""
            val categoriesBean0 = adapterRecommend.getItem(0) as GoodsClassifyBean.DataBean.CategoriesBean
            val isFreePostage = if(categoriesBean0.selected) "1" else "0"

            val categoriesBean1 = adapterRecommend.getItem(1) as GoodsClassifyBean.DataBean.CategoriesBean
            val isPreferential = if(categoriesBean1.selected) "1" else "0"

            val categoriesBean2 = adapterRecommend.getItem(2) as GoodsClassifyBean.DataBean.CategoriesBean
            val isCustomMade = if(categoriesBean2.selected) "1" else "0"
            present.loadData(page, sortType, minPrice, maxPrice, cids, isFreePostage, isPreferential, isCustomMade, "")
        }
    }



    /**
     * 获取选中的分类
     */
    private fun getSelectedItem(): String {
        var string = ""
        val data = adapter.data
        val size = data.size
        if (size == 0) return string

        for (i in data.indices) {
            if (data[i].selected) {
                string += "${data[i].id},"
            }
        }
        if (TextUtils.isEmpty(string)) return string
        return string.removeSuffix(",")
    }


    /**
     * 设置选中状态
     */
    private fun setSelection(sortType: String) {

    }

    /**
     * 重置选中状态
     */
    private fun resetSelectionStatus() {
        val data = adapter.data
        for (item in data) {
            item.selected = false
        }
        adapter.notifyDataSetChanged()
    }

    override fun setUiBeforShow() {

        view.textViewReset.setOnClickListener {
            setRangeSeekBar()
            resetSelectionStatus()
            setGoodsCount(0)
        }

        view.imageViewClose.setOnClickListener { dismiss() }

        view.button.setOnClickListener {
            dismiss()
        }

    }

    /**
     * 设置商品数量
     */
    fun setGoodsCount(count: Int) {
        view.button.text = "查看商品(${count}件)"
    }
}