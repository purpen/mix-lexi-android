package com.thn.lexi.search

import android.support.v4.app.FragmentActivity
import android.view.View
import com.basemodule.tools.Util
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.thn.lexi.R
import kotlinx.android.synthetic.main.dialog_synthesise_sort_bottom.view.*

class DialogBottomSynthesiseSort(context: FragmentActivity?, presenter: SearchGoodsPresenter) : BottomBaseDialog<DialogBottomSynthesiseSort>(context) {
    private lateinit var view: View
    private val present: SearchGoodsPresenter = presenter
    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_synthesise_sort_bottom, null)

        setSelection(present.getSortType())

        return view
    }

    /**
     * 设置选中状态
     */
    private fun setSelection(sortType:String){
        resetSelectionStatus()
        when (sortType) {
            SearchGoodsPresenter.SORT_TYPE_SYNTHESISE -> {
                view.textViewSynthesis.setTextColor(Util.getColor(R.color.color_6ed7af))
                view.textViewSynthesis.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            }
            SearchGoodsPresenter.SORT_TYPE_LOW_UP -> {
                view.textViewLow2Up.setTextColor(Util.getColor(R.color.color_6ed7af))
                view.textViewLow2Up.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            }
            SearchGoodsPresenter.SORT_TYPE_UP_LOW -> {
                view.textViewUp2Low.setTextColor(Util.getColor(R.color.color_6ed7af))
                view.textViewUp2Low.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            }
        }
    }


    /**
     * 重置选中状态
     */
    private fun resetSelectionStatus(){
        view.textViewSynthesis.setTextColor(Util.getColor(R.color.color_333))
        view.textViewSynthesis.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        view.textViewLow2Up.setTextColor(Util.getColor(R.color.color_333))
        view.textViewLow2Up.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        view.textViewUp2Low.setTextColor(Util.getColor(R.color.color_333))
        view.textViewUp2Low.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    override fun setUiBeforShow() {
        view.imageViewClose.setOnClickListener { dismiss() }

        //综合排序
        view.textViewSynthesis.setOnClickListener {
            setSelection(SearchGoodsPresenter.SORT_TYPE_SYNTHESISE)
            val page = 1
            val filterCondition = present.getFilterCondition()
            val minPrice = ""
            val maxPrice = ""
            val profitType = ""
            val cids = ""
            present.loadData(page, SearchGoodsPresenter.SORT_TYPE_SYNTHESISE, profitType, filterCondition, minPrice, maxPrice,cids)
            dismiss()
        }

        //价格由低到高
        view.textViewLow2Up.setOnClickListener {
            setSelection(SearchGoodsPresenter.SORT_TYPE_LOW_UP)
            val page = 1
            val filterCondition = present.getFilterCondition()
            val minPrice = ""
            val maxPrice = ""
            val profitType = ""
            val cids = ""
            present.loadData(page, SearchGoodsPresenter.SORT_TYPE_LOW_UP, profitType, filterCondition, minPrice, maxPrice,cids)
            dismiss()
        }

        //价格由高到低
        view.textViewUp2Low.setOnClickListener {
            setSelection(SearchGoodsPresenter.SORT_TYPE_UP_LOW)
            val page = 1
            val filterCondition = present.getFilterCondition()
            val minPrice = ""
            val maxPrice = ""
            val profitType = ""
            val cids = ""
            present.loadData(page, SearchGoodsPresenter.SORT_TYPE_UP_LOW, profitType, filterCondition, minPrice, maxPrice,cids)
            dismiss()
        }
    }
}