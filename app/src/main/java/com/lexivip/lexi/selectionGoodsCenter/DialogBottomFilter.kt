package com.lexivip.lexi.selectionGoodsCenter

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.basemodule.tools.Constants
import com.lexivip.lexi.JsonUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.ui.IDataSource
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.R
import com.smart.dialog.widget.base.BottomBaseDialog
import kotlinx.android.synthetic.main.dialog_filter_sort_bottom.view.*
import java.io.IOException

class DialogBottomFilter(context: FragmentActivity?, presenter: AllGoodsPresenter) : BottomBaseDialog<DialogBottomFilter>(context) {
    private lateinit var view: View
    private val present: AllGoodsPresenter = presenter
    private val adapter: AdapterGoodsClassify by lazy { AdapterGoodsClassify(R.layout.adapter_text_border) }

    private var minPrice: String = ""

    private var maxPrice: String = ""

    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_filter_sort_bottom, null)
        setRangeSeekBar()
        return view
    }

    private fun setRangeSeekBar(): View {
        val list = listOf("¥0", "¥150", "¥300", "¥400", "¥500", "¥800", "不限")
        view.rangeSeekBarView.setData(list) { leftPostion, rightPostion ->
            //            LogUtil.e("list[leftPostion]=" + list[leftPostion] + ";list[rightPostion]=" + list[rightPostion])

            val page = 1
            val filterCondition = present.getFilterCondition()

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
            present.loadData(page, sortType, AllGoodsPresenter.PROFIT_TYPE_DEFAULT, filterCondition, minPrice, maxPrice, cids)
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

        installListener()
    }

    private fun installListener() {

        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as GoodsClassifyBean.DataBean.CategoriesBean
            item.selected = !item.selected
            adapter.notifyItemChanged(position)


            val page = 1
            val filterCondition = present.getFilterCondition()
            val cids = getSelectedItem()
            val sortType = ""
            present.loadData(page, sortType, AllGoodsPresenter.PROFIT_TYPE_DEFAULT, filterCondition, minPrice, maxPrice,cids)
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