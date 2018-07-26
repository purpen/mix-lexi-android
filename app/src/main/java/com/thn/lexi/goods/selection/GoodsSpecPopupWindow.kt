package com.thn.lexi.goods.selection
import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.basemodule.tools.*
import com.basemodule.ui.IDataSource
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.goods.bean.SKUListData
import com.thn.lexi.net.ClientParamsAPI
import com.thn.lexi.net.HttpRequest
import com.thn.lexi.net.URL
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

class GoodsSpecPopupWindow(activity: FragmentActivity?, item: GoodsData.DataBean.ProductsBean, @LayoutRes res: Int, width: Int, height: Int) : PopupWindow(width, height) {
    private var activity: FragmentActivity? = null
    private var view: View = LayoutInflater.from(activity).inflate(res, null)
    private lateinit var colorAdapter: ColorAdapter
    private lateinit var specificationAdapter: SpecificationAdapter
    private lateinit var items: ArrayList<SKUListData.DataBean.ItemsBean>
    private lateinit var colors: ArrayList<SKUListData.DataBean.ColorsBean>
    private lateinit var modes: ArrayList<SKUListData.DataBean.ModesBean>
    private var dataBean: SKUListData.DataBean.ItemsBean? = null
    private lateinit var colorRecyclerView: RecyclerView

    private lateinit var specificRecyclerView: RecyclerView

    /**
     * 选中的颜色
     */
    private var selectedColorTv: TextView? = null
    /**
     * 选中的规格
     */
    private var selectedSpecTv: TextView? = null

    init {
        contentView = view
        this.activity = activity
        isOutsideTouchable = true
        isTouchable = true
        isFocusable = true
        animationStyle = R.style.popupwindow_style
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        setOnDismissListener {
            selectedColorTv = null
            selectedSpecTv = null
            SPUtil.clear(SKUListData::class.java.name)
            val window = activity?.window
            val attrs = window?.attributes
            attrs?.alpha = 1f
            window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window?.attributes = attrs
        }
        initDialogContent()
        installListener()
        loadData(item)
    }


    private fun installListener() {
        colorAdapter.setOnItemClickListener { adapter, view, position ->
            var colorsBean: SKUListData.DataBean.ColorsBean? = null
            for (i in colors.indices) {
                colorsBean = colors[i]
                if (position == i) {
                    colorsBean.selected = !colorsBean.selected
                } else {
                    colorsBean.selected = false
                }
            }
            colorAdapter.notifyDataSetChanged()
            selectedColorTv = view as TextView
            setSkuSpecInfoByAttr()
            // 更新规格列表可选状态
            setSpecSelectableState()
        }


        specificationAdapter.setOnItemClickListener { adapter, view, position ->
            var modesBean: SKUListData.DataBean.ModesBean
            for (i in modes.indices) {
                modesBean = modes[i]
                if (position == i) {
                    modesBean.selected = !modesBean.selected
                } else {
                    modesBean.selected = false
                }
            }
            specificationAdapter.notifyDataSetChanged()
            selectedSpecTv = view as TextView
            setSkuSpecInfoByAttr()
            //                更新颜色列表可选状态
            setColorSelectableState()
        }

        var dialogConfirmBtn = view.findViewById<View>(R.id.dialog_confirm_btn)

        dialogConfirmBtn.setOnClickListener {
            if (colors.size > 0 && selectedColorTv == null) {
                ToastUtil.showInfo("请选择颜色分类")
                return@setOnClickListener
            }

            if (modes.size > 0 && selectedSpecTv == null) {
                ToastUtil.showInfo("请选择规格")
                return@setOnClickListener
            }

            dismiss()
            val intent = Intent()
            intent.putExtra(SKUListData::class.java!!.simpleName, dataBean)
            //TODO 跳转购物车界面
        }

    }

    /**
     * 根据规格找出库存为0的颜色，更新颜色列表
     */
    private fun setColorSelectableState() {
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

        if (!colorRecyclerView.isShown) return

        val specTvText = selectedSpecTv?.text.toString()
        val existItems = HashMap<String, SKUListData.DataBean.ItemsBean>()
        for (item in items) {
            if (TextUtils.equals(specTvText, item.s_model)) {
                existItems[item.s_color] = item
            }
        }

        for (color in colors) {
            val bean = existItems[color.name]
            if (null == bean) {
                color.valid = false
            } else {
                color.valid = bean.stock_count !== 0
            }
        }
        colorAdapter.notifyDataSetChanged()
    }


    /**
     * 根据颜色/规格显示指定SKU
     * @return
     */
    private fun setSkuSpecInfoByAttr() {
        //        只显示颜色列表
        if (colorRecyclerView.isShown && !specificRecyclerView.isShown) {
            if (selectedColorTv == null) return
            val colorTvText = selectedColorTv?.text
            if (TextUtils.isEmpty(colorTvText)) return
            for (item in items) {
                if (TextUtils.equals(item.s_color, colorTvText)) {
                    setSkuInfo(item)
                    return
                }
            }
            //            只显示规格列表
        } else if (!colorRecyclerView.isShown && specificRecyclerView.isShown) {
            if (selectedSpecTv == null) return
            val specTvText = selectedSpecTv?.text
            if (TextUtils.isEmpty(specTvText)) return
            for (item in items) {
                if (TextUtils.equals(item.s_model, specTvText)) {
                    setSkuInfo(item)
                    return
                }
            }
            //            颜色规格列表都显示
        } else if (colorRecyclerView.isShown && specificRecyclerView.isShown) {
            if (selectedColorTv == null || selectedSpecTv == null) return
            val colorTvText = selectedColorTv?.text
            val specTvText = selectedSpecTv?.text
            if (TextUtils.isEmpty(colorTvText) || TextUtils.isEmpty(specTvText)) return
            LogUtil.e("选中颜色=$colorTvText&&选中规格=$specTvText")
            for (item in items) {
                if (TextUtils.equals(item.s_color, colorTvText) && TextUtils.equals(item.s_model, specTvText)) {
                    LogUtil.e(item.cover)
                    setSkuInfo(item)
                    return
                }
            }
        }


    }

    /**
     * 根据颜色找出库存为0的规格，更新规格列表
     */
    private fun setSpecSelectableState() {
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

        if (!specificRecyclerView.isShown) return


        val colorTvText = selectedColorTv?.text.toString()
        //       得到选中颜色的所有规格
        val existItems = HashMap<String, SKUListData.DataBean.ItemsBean>()
        for (item in items) {
            if (TextUtils.equals(colorTvText, item.s_color)) {
                existItems[item.s_model] = item
            }
        }

        //        根据规格存在情况设置是否可选
        for (mode in modes) {
            val bean = existItems[mode.name]
            if (null == bean) {
                mode.valid = false
            } else {
                mode.valid = bean.stock_count !== 0
            }
        }
        specificationAdapter.notifyDataSetChanged()
    }

    /**
     * 初始化对话框控件
     */
    private fun initDialogContent() {
        items = ArrayList()
        colors = ArrayList()
        modes = ArrayList()
        specificRecyclerView = view.findViewById(R.id.specificRecyclerView)
        colorRecyclerView = view.findViewById(R.id.colorRecyclerView)


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


    }

    private fun loadData(item: GoodsData.DataBean.ProductsBean) {
        val params = ClientParamsAPI.getSKUListParams(item.rid)
        HttpRequest.sendRequest(HttpRequest.GET, URL.SKU_LIST, params, object : IDataSource.HttpRequestCallBack {
            override fun onStart() {

            }

            override fun onSuccess(json: String) {
                LogUtil.e(json)
                val skuListData = JsonUtil.fromJson(json, SKUListData::class.java)
                if (skuListData.success) {
                    setData(skuListData)
                    SPUtil.write(SKUListData::class.java.name, json)
                } else {
                    ToastUtil.showError(skuListData.status.message)
                }
            }

            override fun onFailure(e: IOException) {
                ToastUtil.showError(AppApplication.getContext().getString(R.string.text_net_error))
            }
        })
    }


    /**
     * 设置数据
     */
    fun setData(skuListData: SKUListData) {
        items.clear()
        colors.clear()
        modes.clear()
        items.addAll(skuListData.data.items)
        colors.addAll(skuListData.data.colors)
        modes.addAll(skuListData.data.modes)
        initColorListState()
        initSpecListState()
        val tvColorSpec = view.findViewById<View>(R.id.tvColorSpec)
        val tvSpecification = view.findViewById<View>(R.id.tvSpecification)

        if (colors.size == 0) {
            colorRecyclerView.visibility = View.GONE
            tvColorSpec.visibility = View.GONE
        } else {
            colorRecyclerView.visibility = View.VISIBLE
            tvColorSpec.visibility = View.VISIBLE
            colorAdapter.setNewData(colors)
        }

        if (modes.size == 0) {
            specificRecyclerView.visibility = View.GONE
            tvSpecification.visibility = View.GONE
        } else {
            specificRecyclerView.visibility = View.VISIBLE
            tvSpecification.visibility = View.VISIBLE
            specificationAdapter.setNewData(modes)
        }


        for (item in items) {
            if (item.stock_count > 0) {
                dataBean = item
                setSkuInfo(item)
                return
            } else {
                dataBean = null
            }
        }

        if (dataBean == null && !items.isEmpty()) {
            dataBean = items[0]
            setSkuInfo(dataBean)
        }
    }

    /**
     * 设置Sku的信息
     *
     * @param dataBean
     */
    private fun setSkuInfo(dataBean: SKUListData.DataBean.ItemsBean?) {
        this.dataBean = dataBean
        GlideUtil.loadImageWithFading(dataBean?.cover, view.findViewById(R.id.imageView))
        view.findViewById<TextView>(R.id.dialog_cart_price).text = "￥" + dataBean?.sale_price
        view.findViewById<TextView>(R.id.dialog_cart_producttitle).text = dataBean?.product_name
        view.findViewById<TextView>(R.id.dialog_cart_skusnumber).text = "库存：" + dataBean?.stock_count
    }


    /**
     * 还原数据
     */
    private fun resetDialogData() {
        selectedColorTv = null
        selectedSpecTv = null
        val s = SPUtil.read(SKUListData::class.java.name)
        val skuListData = JsonUtil.fromJson(s, SKUListData::class.java)
        setData(skuListData)
    }

    /**
     * 初始化颜色列表
     */
    private fun initColorListState() {
        var stockCount: Long = 0
        for (color in colors) {
            for (item in items) {
                if (TextUtils.equals(item.s_color, color.name)) {
                    stockCount += item.stock_count
                }
            }
            color.valid = stockCount != 0L
        }

    }

    /**
     * 初始化规格列表
     */
    private fun initSpecListState() {
        var stockCount = 0
        for (mode in modes) {
            for (item in items) {
                if (TextUtils.equals(item.s_model, mode.name)) {
                    stockCount += item.stock_count
                }
            }
            mode.valid = stockCount != 0
        }
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