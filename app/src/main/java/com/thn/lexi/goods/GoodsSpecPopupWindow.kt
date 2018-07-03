package com.thn.lexi.goods

import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.basemodule.tools.SPUtil
import com.thn.lexi.R
import com.thn.lexi.goods.bean.SKUListData

class GoodsSpecPopupWindow(activity: FragmentActivity?, @LayoutRes res: Int, width: Int, height: Int) : PopupWindow(width, height) {
    private var activity: FragmentActivity? = null
    private var view: View = LayoutInflater.from(activity).inflate(res, null)
    private lateinit var colorAdapter: ColorAdapter
    private lateinit var specificationAdapter: SpecificationAdapter

    init {
        contentView = view
        this.activity = activity
        isOutsideTouchable = true
        isTouchable = true
        isFocusable = true
        animationStyle = R.style.popupwindow_style
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        setOnDismissListener {
            //            selectedColorTv = null
//            selectedSpecTv = null
            SPUtil.clear(SKUListData::class.java.name)
            val window = activity?.window
            val attrs = window?.attributes
            attrs?.alpha = 1f
            window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window?.attributes = attrs
        }
        initDialogContent()
    }

    /**
     * 初始化对话框控件
     */
    private fun initDialogContent() {
        val specificRecyclerView = view.findViewById<RecyclerView>(R.id.specificRecyclerView)
        val colorRecyclerView = view.findViewById<RecyclerView>(R.id.colorRecyclerView)
        var dialogConfirmBtn = view.findViewById<View>(R.id.dialog_confirm_btn)

        val linearLayoutManager = LinearLayoutManager(activity)
        val modesLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        modesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        specificRecyclerView.setHasFixedSize(true)
        specificRecyclerView.layoutManager = modesLayoutManager

        colorRecyclerView.setHasFixedSize(true)
        colorRecyclerView.layoutManager = linearLayoutManager

        colorAdapter = ColorAdapter(R.layout.adapter_sku_layout)
        colorRecyclerView.adapter = colorAdapter

        specificationAdapter = SpecificationAdapter(R.layout.adapter_sku_layout)
        specificRecyclerView.adapter = specificationAdapter

//        colorAdapter.setOnItemClickListener(object : OnRecyclerViewItemClickListener() {
//            fun onClick(view: View, i: Int) {
//                val size = colors.size
//                var colorsBean: SKUListData.DataBean.ColorsBean? = null
//                for (j in 0 until size) {
//                    colorsBean = colors.get(j)
//                    if (j == i) {
//                        colorsBean!!.selected = !colorsBean!!.selected
//                    } else {
//                        colorsBean!!.selected = false
//                    }
//                }
//                colorAdapter.notifyDataSetChanged()
//                selectedColorTv = view as TextView
//                setSkuSpecInfoByAttr()
//                //                更新规格列表可选状态
//                setSpecSelectableState()
//            }
//        })

//        specificationAdapter.setOnItemClickListener(object : OnRecyclerViewItemClickListener() {
//            fun onClick(view: View, i: Int) {
//                val size = modes.size
//                var modesBean: SKUListData.DataBean.ModesBean
//                for (j in 0 until size) {
//                    modesBean = modes.get(j)
//                    if (j == i) {
//                        modesBean.selected = !modesBean.selected
//                    } else {
//                        modesBean.selected = false
//                    }
//                }
//                specificationAdapter.notifyDataSetChanged()
//                selectedSpecTv = view as TextView
//                setSkuSpecInfoByAttr()
//                //                更新颜色列表可选状态
//                setColorSelectableState()
//            }
//        })


        dialogConfirmBtn.setOnClickListener(View.OnClickListener {
            //            if (colors.size > 0 && selectedColorTv == null) {
//                ToastUtil.showInfo("请选择颜色分类")
//                return
//            }
//
//            if (modes.size > 0 && selectedSpecTv == null) {
//                ToastUtil.showInfo("请选择规格")
//                return@OnClickListener
//            }
//
//            dataBean.buyNum = holder.counterView.getNum()
//            popupWindow.dismiss()
//            val intent = Intent()
//            intent.putExtra(SKUListData::class.java!!.getSimpleName(), dataBean)
//            setResult(RESULT_OK, intent)
//            finish()
        })
    }

    /**
     * 设置数据
     */
    fun setData() {
//        colorAdapter.setNewData()
//        specificationAdapter.setNewData()
    }

    fun show() {
        val window = activity?.window
        val attrs = window?.attributes
        attrs?.alpha = 0.4f
        window?.attributes = attrs
        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)//这行代码可以使window后的所有东西边暗淡
        setBackgroundDrawable(ContextCompat.getDrawable(view.context, android.R.color.white))
        showAtLocation(window?.decorView, Gravity.BOTTOM, 0, 0)
    }

}