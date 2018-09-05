package com.thn.lexi.index.detail
import android.content.Context
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.dialog_select_specification_bottom.view.*
import java.io.IOException
import java.util.HashMap


class SelectSpecificationBottomDialog(context: Context, presenter: GoodsDetailPresenter, goodsId: String) : BottomBaseDialog<SelectSpecificationBottomDialog>(context) {
    private val present: GoodsDetailPresenter by lazy { presenter }
    private val id: String by lazy { goodsId }
    private val items: ArrayList<GoodsAllSKUBean.DataBean.ItemsBean> by lazy { ArrayList<GoodsAllSKUBean.DataBean.ItemsBean>() }
    private val colors: ArrayList<GoodsAllSKUBean.DataBean.ColorsBean> by lazy { ArrayList<GoodsAllSKUBean.DataBean.ColorsBean>() }
    private val modes: ArrayList<GoodsAllSKUBean.DataBean.ModesBean> by lazy { ArrayList<GoodsAllSKUBean.DataBean.ModesBean>() }
    private var selectedColor: String? = null
    private var selectedSize: String? = null
    private lateinit var view: View
    private var selectedSKU: GoodsAllSKUBean.DataBean.ItemsBean? = null

    private lateinit var adapterColor: TagAdapter<GoodsAllSKUBean.DataBean.ColorsBean>
    private lateinit var adapterSize: TagAdapter<GoodsAllSKUBean.DataBean.ModesBean>

    override fun onCreateView(): View {
        view = View.inflate(context, R.layout.dialog_select_specification_bottom, null)
        present.getGoodsSKUs(id, object : IDataSource.HttpRequestCallBack {
            override fun onSuccess(json: String) {
                val goodsAllSKUBean = JsonUtil.fromJson(json, GoodsAllSKUBean::class.java)
                if (goodsAllSKUBean.success) {
                    setData(goodsAllSKUBean)
                    SPUtil.write(GoodsAllSKUBean::class.java.simpleName, json)
                } else {
                    ToastUtil.showError(goodsAllSKUBean.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                ToastUtil.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
        return view
    }


    /**
     * 设置数据
     */
    private fun setData(goodsAllSKUBean: GoodsAllSKUBean) {
        items.clear()
        colors.clear()
        modes.clear()
        items.addAll(goodsAllSKUBean.data.items)
        colors.addAll(goodsAllSKUBean.data.colors)
        modes.addAll(goodsAllSKUBean.data.modes)
        initColorListState()
        initSpecListState()

        if (colors.isEmpty()) {
            view.flowLayoutColor.visibility = View.GONE
            view.textViewColor.visibility = View.GONE
        } else {
            view.flowLayoutColor.visibility = View.VISIBLE
            view.textViewColor.visibility = View.VISIBLE
            adapterColor.notifyDataChanged()
        }

        if (modes.isEmpty()) {
            view.flowLayoutSize.visibility = View.GONE
            view.textViewSize.visibility = View.GONE
        } else {
            view.flowLayoutSize.visibility = View.VISIBLE
            view.textViewSize.visibility = View.VISIBLE
            adapterSize.notifyDataChanged()
        }

    }

    /**
     * 初始化规格列表UI
     */
    private fun initSpecListState() {
        var stockCount = 0
        for (mode in modes) { //每种规格,在items列表数量
            for (item in items) {
                if (TextUtils.equals(item.s_model, mode.name)) {
                    stockCount += item.stock_count
                }
            }
            mode.valid = stockCount != 0
        }
    }

    /**
     * 初始化颜色列表UI
     */
    private fun initColorListState() {
        var stockCount = 0
        for (color in colors) {
            for (item in items) {//每种颜色，在items列表数量
                if (TextUtils.equals(item.s_color, color.name)) {
                    stockCount += item.stock_count
                }
            }
            color.valid = stockCount != 0
        }
    }

    override fun setUiBeforShow() {
        view.textViewPrice.setCompoundDrawables(Util.getDrawableWidthDimen(R.mipmap.icon_price_unit, R.dimen.dp10, R.dimen.dp12), null, null, null)

        val marginRight = DimenUtil.getDimensionPixelSize(R.dimen.dp15)
        val layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DimenUtil.getDimensionPixelSize(R.dimen.dp24))
        layoutParams.rightMargin = marginRight

        val dp6 = DimenUtil.getDimensionPixelSize(R.dimen.dp6)

        adapterColor = object : TagAdapter<GoodsAllSKUBean.DataBean.ColorsBean>(colors) {
            override fun getView(parent: FlowLayout, position: Int, t: GoodsAllSKUBean.DataBean.ColorsBean): View {
                val item = getItem(position)
                val textView = TextView(context)
                textView.layoutParams = layoutParams
                textView.setPadding(dp6, 0, dp6, 0)
                textView.includeFontPadding = false
                textView.gravity = Gravity.CENTER
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                textView.text = item.name
                if (item.valid) {
                    if (item.selected) {
                        textView.setTextColor(Util.getColor(android.R.color.white))
                        textView.setBackgroundResource(R.drawable.bg_color5fe4b1_radius4)
                    } else {
                        textView.setTextColor(Util.getColor(R.color.color_333))
                        textView.setBackgroundResource(R.drawable.border333_bg_white)
                    }
                } else {
                    textView.setTextColor(Util.getColor(R.color.color_b2b2b2))
                    textView.setBackgroundResource(R.drawable.borderb2b2b2_bg_white)
                }

                return textView
            }
        }

        adapterSize = object : TagAdapter<GoodsAllSKUBean.DataBean.ModesBean>(modes) {
            override fun getView(parent: FlowLayout, position: Int, t: GoodsAllSKUBean.DataBean.ModesBean): View {
                val item = getItem(position)
                val textView = TextView(context)
                textView.layoutParams = layoutParams
                textView.setPadding(dp6, 0, dp6, 0)
                textView.includeFontPadding = false
                textView.gravity = Gravity.CENTER
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                textView.text = item.name
                if (item.valid) {
                    if (item.selected) {
                        textView.setTextColor(Util.getColor(android.R.color.white))
                        textView.setBackgroundResource(R.drawable.bg_color5fe4b1_radius4)
                    } else {
                        textView.setTextColor(Util.getColor(R.color.color_333))
                        textView.setBackgroundResource(R.drawable.border333_bg_white)
                    }
                } else {
                    textView.setTextColor(Util.getColor(R.color.color_b2b2b2))
                    textView.setBackgroundResource(R.drawable.borderb2b2b2_bg_white)
                }
                return textView
            }
        }

        view.flowLayoutColor.adapter = adapterColor

        view.flowLayoutSize.adapter = adapterSize


        view.flowLayoutColor.setOnTagClickListener { view, position, parent ->
            if (colors[position].valid) {
                val size = colors.size
                var colorsBean: GoodsAllSKUBean.DataBean.ColorsBean
                for (i in 0 until size) {
                    colorsBean = colors[i]
                    if (position == i) {
                        colorsBean.selected = !colorsBean.selected
                    } else {
                        colorsBean.selected = false
                    }
                }
                adapterColor.notifyDataChanged()
                selectedColor = colors[position].name
                setSkuSpecInfoByAttr()
//                更新规格列表可选状态
                updateSizeSelectableState()
            }
            true
        }


        view.flowLayoutSize.setOnTagClickListener { view, position, parent ->
            if (modes[position].valid) {
                val size = modes.size
                var modesBean: GoodsAllSKUBean.DataBean.ModesBean
                for (i in 0 until size) {
                    modesBean = modes[i]
                    if (position == i) {
                        modesBean.selected = !modesBean.selected
                    } else {
                        modesBean.selected = false
                    }
                }
                adapterSize.notifyDataChanged()

                selectedSize = modes[position].name

                setSkuSpecInfoByAttr()
//                更新颜色列表可选状态
                updateColorSelectableState()
            }
            true
        }
//        if (list.isEmpty()) {
//            view.relativeLayoutBar.visibility = View.GONE
//            view.recyclerViewCoupon.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.getDimensionPixelSize(R.dimen.dp125))

//        }
    }

    /**
     * 根据规格找出库存为0的颜色，更新颜色列表
     */
    private fun updateColorSelectableState() {
        //        判断是否有规格为选中
        var allUnselected = true
        for (mode in modes) {
            if (mode.selected) {
                allUnselected = false
                break
            }
        }
        //如果没有规格被选
        if (allUnselected) {
            resetDialogData()
            return
        }

        if (!view.flowLayoutColor.isShown) return

        val existItems = HashMap<String, GoodsAllSKUBean.DataBean.ItemsBean>()
        for (item in items) {
            if (TextUtils.equals(selectedSize, item.s_model)) {
                existItems[item.s_color] = item
            }
        }

        for (color in colors) {
            val bean = existItems[color.name]
            if (null == bean) {
                color.valid = false
            } else {
                color.valid = bean.stock_count != 0
            }
        }
        adapterColor.notifyDataChanged()
    }

    /**
     * 根据颜色找出库存为0的规格，更新规格列表
     */
    private fun updateSizeSelectableState() {
        //        判断是否有颜色被选中
        var allUnselected = true
        for (color in colors) {
            if (color.selected) {
                allUnselected = false
                break
            }
        }
        //如果没有颜色被选中
        if (allUnselected) {
            resetDialogData()
            return
        }

        if (!view.flowLayoutSize.isShown) return

        //得到选中颜色的所有规格
        val existItems = HashMap<String, GoodsAllSKUBean.DataBean.ItemsBean>()
        for (item in items) {
            if (TextUtils.equals(selectedColor, item.s_color)) {
                existItems[item.s_model] = item
            }
        }

        //        根据规格存在情况设置是否可选
        for (mode in modes) {
            val bean = existItems[mode.name]
            if (null == bean) {
                mode.valid = false
            } else {
                mode.valid = bean.stock_count != 0
            }
        }
        adapterSize.notifyDataChanged()
    }

    /**
     * 取消选择时还原数据
     */
    private fun resetDialogData() {
        selectedColor = null
        selectedSize = null
        val s = SPUtil.read(GoodsAllSKUBean::class.java.simpleName)
        val goodsAllSKUBean = JsonUtil.fromJson(s, GoodsAllSKUBean::class.java)
        setData(goodsAllSKUBean)
    }


    /**
     * 根据颜色/规格显示指定SKU
     * @return
     */
    private fun setSkuSpecInfoByAttr() {
        //        只有颜色列表
        if (view.flowLayoutColor.isShown && !view.flowLayoutSize.isShown) {

            if (TextUtils.isEmpty(selectedColor)) return

            for (item in items) {
                if (TextUtils.equals(item.s_color, selectedColor)) {
                    setGoodsInfo(item)
                    return
                }
            }
            //只有规格列表
        } else if (!view.flowLayoutColor.isShown && view.flowLayoutSize.isShown) {

            if (TextUtils.isEmpty(selectedSize)) return
            for (item in items) {
                if (TextUtils.equals(item.s_model, selectedSize)) {
                    setGoodsInfo(item)
                    return
                }
            }
            // 都有
        } else if (view.flowLayoutColor.isShown && view.flowLayoutSize.isShown) {

            if (TextUtils.isEmpty(selectedColor) || TextUtils.isEmpty(selectedSize)) return
            LogUtil.e("选中颜色=$selectedColor&&选中规格=$selectedSize")
            for (item in items) {
                if (TextUtils.equals(item.s_color, selectedColor) && TextUtils.equals(item.s_model, selectedSize)) {
                    setGoodsInfo(item)
                    return
                }
            }
        }
    }

    /**
     * 设置选中的商品信息
     * @param item
     */
    private fun setGoodsInfo(item: GoodsAllSKUBean.DataBean.ItemsBean) {
        selectedSKU = item
        view.textViewPrice.text = item.product_name

        if (item.sale_price == 0.0) {
            view.textViewPrice.text = "${item.price}"
        } else {
            view.textViewPrice.text = "${item.sale_price}"
        }
    }

}