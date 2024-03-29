package com.lexivip.lexi.brandHouse

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.Constants
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.JsonUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.selectionGoodsCenter.AdapterGoodsClassify
import com.lexivip.lexi.selectionGoodsCenter.GoodsClassifyBean
import com.smart.dialog.widget.base.BottomBaseDialog
import kotlinx.android.synthetic.main.dialog_filter_sort_bottom.view.*
import java.io.IOException

class DialogBottomFilter(context: Activity, presenter: BrandHouseGoodsPresenter, rid:String) : BottomBaseDialog<DialogBottomFilter>(context) {
    private lateinit var view: View
    private val present: BrandHouseGoodsPresenter = presenter
    private val rid:String=rid
    private val adapter: AdapterGoodsClassify by lazy { AdapterGoodsClassify(R.layout.adapter_text_border) }

    private var minPrice: String = ""

    private var maxPrice: String = ""

    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_filter_sort_bottom, null)
        setRangeSeekBar()
        return view
    }

    private fun setRangeSeekBar(): View {
        val list = Util.getStringArray(R.array.strings_goods_range_price).asList()
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
            val cids = getSelectedItem()
            val sortType = present.sortType
            present.loadGoodsData(rid,page,cids,minPrice,maxPrice,sortType,"")
        }

        return view
    }

    override fun onViewCreated(inflate: View?) {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        view.recyclerView.setHasFixedSize(false)
        view.recyclerView.layoutManager = manager
        view.recyclerView.adapter = adapter
        present.loadGoodsClassify(rid,object : IDataSource.HttpRequestCallBack {
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

        installListener()
    }

    private fun installListener() {
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) as GoodsClassifyBean.DataBean.CategoriesBean
            item.selected = !item.selected
            adapter.notifyItemChanged(position)
            val page = 1
            val cids = getSelectedItem()
            val sortType = present.sortType
            present.loadGoodsData(rid,page,cids,minPrice,maxPrice,sortType,"")
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
        for (item in data){
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