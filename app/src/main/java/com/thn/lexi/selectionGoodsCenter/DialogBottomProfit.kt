package com.thn.lexi.selectionGoodsCenter

import android.support.v4.app.FragmentActivity
import android.view.View
import com.basemodule.tools.Util
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.thn.lexi.R
import kotlinx.android.synthetic.main.dialog_profit_sort_bottom.view.*

class DialogBottomProfit(context: FragmentActivity?, presenter: AllGoodsPresenter) : BottomBaseDialog<DialogBottomProfit>(context) {
    private lateinit var view: View
    private val present: AllGoodsPresenter = presenter
    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_profit_sort_bottom, null)
        setSelection(present.getProfitType())
        return view
    }

    /**
     * 设置选中状态
     */
    private fun setSelection(sortType:String){
        resetSelectionStatus()
        when (sortType) {
            AllGoodsPresenter.PROFIT_TYPE_DEFAULT -> {
                view.textViewNoLimit.setTextColor(Util.getColor(R.color.color_6ed7af))
                view.textViewNoLimit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            }
            AllGoodsPresenter.PROFIT_TYPE_LOW_UP -> {
                view.textViewLow2Up.setTextColor(Util.getColor(R.color.color_6ed7af))
                view.textViewLow2Up.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            }
            AllGoodsPresenter.PROFIT_TYPE_UP_LOW -> {
                view.textViewUp2Low.setTextColor(Util.getColor(R.color.color_6ed7af))
                view.textViewUp2Low.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            }
        }
    }


    /**
     * 重置选中状态
     */
    private fun resetSelectionStatus(){
        view.textViewNoLimit.setTextColor(Util.getColor(R.color.color_333))
        view.textViewNoLimit.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        view.textViewLow2Up.setTextColor(Util.getColor(R.color.color_333))
        view.textViewLow2Up.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        view.textViewUp2Low.setTextColor(Util.getColor(R.color.color_333))
        view.textViewUp2Low.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    override fun setUiBeforShow() {
        view.imageViewClose.setOnClickListener { dismiss() }

        view.textViewNoLimit.setOnClickListener {//利润不限
            setSelection(AllGoodsPresenter.PROFIT_TYPE_DEFAULT)
            val page = 1
            val filterCondition = ""
            val minPrice = ""
            val maxPrice = ""
            val sortType = ""
            val cids =""
            present.loadData(page, sortType, AllGoodsPresenter.PROFIT_TYPE_DEFAULT, filterCondition, minPrice, maxPrice,cids)
            dismiss()
        }

        view.textViewLow2Up.setOnClickListener { //利润由低到高
            setSelection(AllGoodsPresenter.PROFIT_TYPE_LOW_UP)
            val page = 1
            val filterCondition = ""
            val minPrice = ""
            val maxPrice = ""
            val sortType =""
            val cids =""
            present.loadData(page, sortType, AllGoodsPresenter.PROFIT_TYPE_LOW_UP, filterCondition, minPrice, maxPrice,cids)
            dismiss()
        }

        view.textViewUp2Low.setOnClickListener { //利润由高到低
            setSelection(AllGoodsPresenter.PROFIT_TYPE_UP_LOW)
            view.textViewUp2Low.setTextColor(Util.getColor(R.color.color_6ed7af))
            view.textViewUp2Low.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_radio_checked, 0)
            val page = 1
            val filterCondition = ""
            val minPrice = ""
            val maxPrice = ""
            val sortType = ""
            val cids =""
            present.loadData(page, sortType, AllGoodsPresenter.PROFIT_TYPE_UP_LOW, filterCondition, minPrice, maxPrice,cids)
            dismiss()
        }
    }
}