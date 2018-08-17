package com.thn.lexi.selectionGoodsCenter

import android.support.v4.app.FragmentActivity
import android.view.View
import com.basemodule.tools.LogUtil
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.thn.lexi.R
import kotlinx.android.synthetic.main.dialog_filter_sort_bottom.view.*

class DialogBottomFilter(context: FragmentActivity?, presenter: AllGoodsPresenter) : BottomBaseDialog<DialogBottomFilter>(context) {
    private lateinit var view: View
    private val present: AllGoodsPresenter = presenter
    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_filter_sort_bottom, null)
//        setSelection(present.getProfitType())
        val list = listOf("￥0", "￥150", "￥300", "￥400", "￥500", "￥800", "不限")
        view.rangeSeekBarView.setData(list) { leftPostion, rightPostion ->
            LogUtil.e("list[leftPostion]="+list[leftPostion]+";list[rightPostion]="+list[rightPostion])
        }
        return view
    }

    /**
     * 设置选中状态
     */
    private fun setSelection(sortType:String){

    }


    /**
     * 重置选中状态
     */
    private fun resetSelectionStatus(){

    }

    override fun setUiBeforShow() {
        view.imageViewClose.setOnClickListener { dismiss() }

        view.button.setOnClickListener {//利润不限
            setSelection(AllGoodsPresenter.PROFIT_TYPE_DEFAULT)
            val page = 1
            val filterCondition = ""
            val minPrice = ""
            val maxPrice = ""
            val sortType = ""
            present.loadData(page, sortType, AllGoodsPresenter.PROFIT_TYPE_DEFAULT, filterCondition, minPrice, maxPrice)
            dismiss()
        }

    }
}