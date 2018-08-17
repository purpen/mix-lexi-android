package com.thn.lexi.selectionGoodsCenter

import android.support.v4.app.FragmentActivity
import android.view.View
import com.basemodule.tools.Util
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.thn.lexi.R
import kotlinx.android.synthetic.main.dialog_synthesise_sort_bottom.view.*

class DialogBottomSynthesiseSort(context: FragmentActivity?, presenter: AllGoodsPresenter) : BottomBaseDialog<DialogBottomSynthesiseSort>(context) {
    private lateinit var view: View
    private val present: AllGoodsPresenter = presenter
    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_synthesise_sort_bottom, null)
        return view
    }

    override fun setUiBeforShow() {
        view.imageViewClose.setOnClickListener { dismiss() }

        view.textViewSynthesis.setOnClickListener { //综合排序
            view.textViewSynthesis.setTextColor(Util.getColor(R.color.color_6ed7af))
            view.textViewSynthesis.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            val page = 1
            val filterCondition = ""
            val minPrice = ""
            val maxPrice = ""
            val profitType = ""
            present.loadData(page, AllGoodsPresenter.SORT_TYPE_SYNTHESISE,profitType,filterCondition,minPrice,maxPrice)
            dismiss()
        }

        view.textViewLow2Up.setOnClickListener { //价格由低到高
            view.textViewLow2Up.setTextColor(Util.getColor(R.color.color_6ed7af))
            view.textViewLow2Up.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            val page = 1
            val filterCondition = ""
            val minPrice = ""
            val maxPrice = ""
            val profitType = ""
            present.loadData(page, AllGoodsPresenter.SORT_TYPE_LOW_UP,profitType,filterCondition,minPrice,maxPrice)
            dismiss()
        }

        view.textViewUp2Low.setOnClickListener { //价格由高到低
            view.textViewUp2Low.setTextColor(Util.getColor(R.color.color_6ed7af))
            view.textViewUp2Low.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            val page = 1
            val filterCondition = ""
            val minPrice = ""
            val maxPrice = ""
            val profitType = ""

            present.loadData(page, AllGoodsPresenter.SORT_TYPE_UP_LOW,profitType,filterCondition,minPrice,maxPrice)
            dismiss()
        }
    }
}