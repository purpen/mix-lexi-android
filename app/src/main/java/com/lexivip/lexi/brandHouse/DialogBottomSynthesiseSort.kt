package com.lexivip.lexi.brandHouse

import android.app.Activity
import android.view.View
import com.basemodule.tools.Util
import com.lexivip.lexi.R
import com.lexivip.lexi.search.SearchGoodsPresenter
import com.smart.dialog.widget.base.BottomBaseDialog
import kotlinx.android.synthetic.main.dialog_synthesise_sort_bottom.view.*

class DialogBottomSynthesiseSort(context: Activity, presenter: BrandHouseGoodsPresenter,rid: String) : BottomBaseDialog<DialogBottomSynthesiseSort>(context) {
    private lateinit var view: View
    private val present: BrandHouseGoodsPresenter = presenter
    private val rid:String=rid
    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_synthesise_sort_bottom, null)

        setSelection(present.sortType)

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
            val minPrice = present.minePrice
            val maxPrice = present.maxPrice
            val sortType = SearchGoodsPresenter.SORT_TYPE_SYNTHESISE
            val cids = present.cids
            present.loadGoodsData(rid,page,cids,minPrice,maxPrice,sortType,"")
            dismiss()
        }

        //价格由低到高
        view.textViewLow2Up.setOnClickListener {
            setSelection(SearchGoodsPresenter.SORT_TYPE_LOW_UP)
            val page = 1
            val minPrice = present.minePrice
            val maxPrice = present.maxPrice
            val sortType = SearchGoodsPresenter.SORT_TYPE_LOW_UP
            val cids = present.cids
            present.loadGoodsData(rid,page,cids,minPrice,maxPrice,sortType,"")
            dismiss()
        }

        //价格由高到低
        view.textViewUp2Low.setOnClickListener {
            setSelection(SearchGoodsPresenter.SORT_TYPE_UP_LOW)
            val page = 1
            val minPrice = present.minePrice
            val maxPrice = present.maxPrice
            val sortType = SearchGoodsPresenter.SORT_TYPE_UP_LOW
            val cids = present.cids
            present.loadGoodsData(rid,page,cids,minPrice,maxPrice,sortType,"")
            dismiss()
        }
    }
}