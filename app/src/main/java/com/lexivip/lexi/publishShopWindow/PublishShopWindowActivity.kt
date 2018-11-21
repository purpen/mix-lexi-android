package com.lexivip.lexi.publishShopWindow

import android.content.Intent
import android.graphics.*
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ProductBean
import kotlinx.android.synthetic.main.acticity_publish_shop_window.*
import kotlinx.android.synthetic.main.view_show_window_image3.view.*
import kotlinx.android.synthetic.main.view_show_window_image5.view.*
import kotlinx.android.synthetic.main.view_show_window_image7.view.*
import android.graphics.Bitmap
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.basemodule.tools.*
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.eventBusMessge.MessageAddGoodsImages
import com.lexivip.lexi.eventBusMessge.MessageShopWindowTag
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class PublishShopWindowActivity : BaseActivity(), PublishShopWindowContract.View {

    companion object {
        const val THREE = 3
        const val FIVE = 5
        const val SEVEN = 7
    }

    private val dialog: WaitingDialog by lazy { WaitingDialog(this) }
    private val presenter: PublishShopWindowPresenter by lazy { PublishShopWindowPresenter(this) }
    private val maxImageCount: Int by lazy { SEVEN }
    private lateinit var productsMap: SparseArray<ProductBean>
    private lateinit var adapterTags: TagAdapter<String>
    private val tagList by lazy { ArrayList<String>(3) }
    private val placeHolderBitmap: Bitmap by lazy { getPlaceHolderImage() }
    override val layout: Int = R.layout.acticity_publish_shop_window
    private var publishCount: Int = THREE
    private lateinit var view: View
    override fun initView() {

        EventBus.getDefault().register(this)

        productsMap = SparseArray()
        for (i in 0..maxImageCount) {
            productsMap.put(i, null)
        }
        customHeadView.setHeadCenterTxtShow(true, R.string.title_select_goods)
        customHeadView.buttonRight.visibility = View.VISIBLE
        customHeadView.buttonRight.setTextColor(Util.getColor(R.color.color_949ea6))
        switchImageMode(THREE)
        initTagsView()

    }

    private fun initTagsView() {
        val dp6 = DimenUtil.dp2px(6.0)
        val dp10 = DimenUtil.dp2px(10.0)
        val dp27 = DimenUtil.dp2px(27.0)
        val layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp27)
        layoutParams.rightMargin = dp10
        layoutParams.bottomMargin = dp10

        adapterTags = object : TagAdapter<String>(tagList) {
            private val color333 = Util.getColor(R.color.color_333)
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                val linearLayout = LinearLayout(applicationContext)
                linearLayout.orientation = LinearLayout.HORIZONTAL
                val textView = TextView(applicationContext)
                textView.setCompoundDrawables(null, null, Util.getDrawableWidthPxDimen(R.mipmap.icon_clear_search_box, DimenUtil.dp2px(12.0)), null)
                textView.compoundDrawablePadding = DimenUtil.dp2px(10.0)
                textView.gravity = Gravity.CENTER_VERTICAL
                val imageView = ImageView(applicationContext)
                imageView.setImageResource(R.mipmap.icon_clear_search_box)
                textView.layoutParams = layoutParams
                textView.setPadding(dp10, dp6, dp10, dp6)
                textView.includeFontPadding = false
                textView.gravity = Gravity.CENTER
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                textView.maxLines = 1
                textView.maxEms = 12
                textView.ellipsize = TextUtils.TruncateAt.END
                textView.text = "# $t"
                textView.setTextColor(color333)
                textView.setBackgroundResource(R.drawable.bg_radius_round_f5f7f9)
                return textView
            }
        }
        tagFlowLayout.adapter = adapterTags

        //点击删除标签
        tagFlowLayout.setOnTagClickListener { _, position, _ ->
            val item = adapterTags.getItem(position)
            if (TextUtils.isEmpty(item)) return@setOnTagClickListener false
            tagList.remove(item)
            adapterTags.notifyDataChanged()
            true
        }
    }

    /**
     * 绘制占位图
     */
    private fun getPlaceHolderImage(): Bitmap {
        val width = ScreenUtil.getScreenWidth() * 3 / 5
        val createBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.RGB_565)
        val canvas = Canvas(createBitmap)
        val paint = Paint()
        paint.isFilterBitmap = true
        val pfd = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas.drawFilter = pfd
        canvas.drawColor(Util.getColor(R.color.color_f5f7f9))
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_add_product)
        val dp30 = DimenUtil.dp2px(30.0)
        val h = bitmap.height
        val w = bitmap.width

        val scaleWidth = dp30 / w.toFloat()
        val scaleHeight = dp30 / h.toFloat()
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        val newBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, false)
        bitmap?.recycle()
        val left = (createBitmap.width - newBitmap.width) * 0.5f
        val top = (createBitmap.height - newBitmap.height) * 0.5f
        canvas.drawBitmap(newBitmap, left, top, paint)
        return createBitmap
    }


    /**
     * 返回待发布商品列表
     */
    private fun getPublishProducts(): ArrayList<ProductBean> {
        val products = ArrayList<ProductBean>()
        for (i in 0 until publishCount) {
            val bean = productsMap[i] ?: continue
            products.add(bean)
        }
        return products
    }

    /**
     * 发布橱窗成功
     */
    override fun publishShopWindowSuccess(data: ShopWindowBean) {
        data.PAGE_TAG = TAG
        EventBus.getDefault().post(data)
        ToastUtil.showSuccess("发布成功")
        finish()
    }

    override fun installListener() {
        editTextTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                setPublishButtonState()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        editTextContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                setPublishButtonState()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        customHeadView.buttonRight.setOnClickListener {
            val title = editTextTitle.text.trim().toString()
            val content = editTextContent.text.trim().toString()
            val products = getPublishProducts()
            presenter.publishShopWindow(title, content, products, tagList)
        }

        button3Image.setOnClickListener {
            publishCount = THREE
            switchImageMode(THREE)
            resetButtonState()
            button3Image.isEnabled = false
            button3Image.setTextColor(Util.getColor(android.R.color.white))
            setPublishButtonState()
        }

        button5Image.setOnClickListener {
            publishCount = FIVE
            switchImageMode(FIVE)
            resetButtonState()
            button5Image.isEnabled = false
            button5Image.setTextColor(Util.getColor(android.R.color.white))
            setPublishButtonState()
        }

        button7Image.setOnClickListener {
            publishCount = SEVEN
            switchImageMode(SEVEN)
            resetButtonState()
            button7Image.isEnabled = false
            button7Image.setTextColor(Util.getColor(android.R.color.white))
            setPublishButtonState()
        }

        buttonAddTag.setOnClickListener {
            if (tagList.size == 3) {
                ToastUtil.showInfo("最多添加3个标签")
                return@setOnClickListener
            }
            startActivity(Intent(this, ShopWindowTagsActivity::class.java))
        }
    }

    /**
     * 设置发布按钮状态
     */
    private fun setPublishButtonState() {
        if (publishEnable()) {
            customHeadView.buttonRight.setTextColor(Util.getColor(android.R.color.white))
            customHeadView.buttonRight.isEnabled = true
        } else {
            customHeadView.buttonRight.setTextColor(Util.getColor(R.color.color_949ea6))
            customHeadView.buttonRight.isEnabled = false
        }
    }

    private fun resetButtonState() {
        val color333 = Util.getColor(R.color.color_333)
        button3Image.isEnabled = true
        button3Image.setTextColor(color333)
        button5Image.isEnabled = true
        button5Image.setTextColor(color333)
        button7Image.isEnabled = true
        button7Image.setTextColor(color333)
    }


    /**
     * 设置橱窗产品图片
     */
    private fun switchImageMode(count: Int) {
        linearLayoutBox.removeAllViews()
        val screenW = ScreenUtil.getScreenWidth()
        val dp2: Int by lazy { DimenUtil.dp2px(2.0) }
        val dp30: Int by lazy { DimenUtil.dp2px(30.0) }
        when (count) {
            3 -> {
                val dp110: Int by lazy { screenW * 110 / 375 }
                val dp220: Int by lazy { dp110 * 2 + dp2 }
                val dp108: Int by lazy { screenW - dp30 - dp220 - dp2 }
                val layoutParams30: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp220, dp220) }
                val layoutParams31: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp108, dp110) }
                val layoutParams32: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp108, dp110) }
                view = View.inflate(this, R.layout.view_show_window_image3, null)
                linearLayoutBox.addView(view)
                if (productsMap[0] == null) {
                    view.imageView30.scaleType = ImageView.ScaleType.CENTER
                    view.imageView30.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView30.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[0].cover, view.imageView30)
                }

                if (productsMap[1] == null) {
                    view.imageView31.scaleType = ImageView.ScaleType.CENTER
                    view.imageView31.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView31.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[1].cover, view.imageView31)
                }

                if (productsMap[2] == null) {
                    view.imageView32.scaleType = ImageView.ScaleType.CENTER
                    view.imageView32.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView32.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[2].cover, view.imageView32)
                }

                view.imageView30.layoutParams = layoutParams30
                view.imageView31.layoutParams = layoutParams31
                layoutParams31.addRule(RelativeLayout.END_OF, R.id.imageView30)
                layoutParams31.marginStart = dp2
                layoutParams32.addRule(RelativeLayout.BELOW, R.id.imageView31)
                layoutParams32.addRule(RelativeLayout.ALIGN_LEFT, R.id.imageView31)
                layoutParams32.topMargin = dp2
                view.relativeLayoutImage32.layoutParams = layoutParams32
                view.imageView30.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(0, productsMap)
                }

                view.imageView31.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(1, productsMap)
                }
                view.imageView32.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(2, productsMap)
                }
            }

            5 -> {
                val dp125: Int by lazy { screenW * 125 / 375 }
                val dp203: Int by lazy { screenW - dp30 - dp125 - dp2 }
                val dp101: Int by lazy { (dp203 - dp2) / 2 }
                val dp140: Int by lazy { screenW * 140 / 375 }
                val dp188: Int by lazy { screenW - dp140 - dp30 - dp2 }
                val dp141: Int by lazy { dp188 * 141 / 188 }
                val layoutParams50: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp203, dp203) }
                val layoutParams51: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp125, dp101) }
                val layoutParams52: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp125, dp101) }
                val layoutParams53: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp188, dp141) }
                val layoutParams54: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp140, dp141) }
                view = View.inflate(this, R.layout.view_show_window_image5, null)
                view.imageView50.layoutParams = layoutParams50
                view.imageView51.layoutParams = layoutParams51
                layoutParams51.addRule(RelativeLayout.END_OF, R.id.imageView50)
                layoutParams51.marginStart = dp2
                layoutParams52.addRule(RelativeLayout.BELOW, R.id.imageView51)
                layoutParams52.addRule(RelativeLayout.ALIGN_LEFT, R.id.imageView51)
                layoutParams52.topMargin = dp2
                view.imageView52.layoutParams = layoutParams52

                layoutParams53.addRule(RelativeLayout.BELOW, R.id.imageView50)
                layoutParams53.topMargin = dp2
                view.imageView53.layoutParams = layoutParams53

                layoutParams54.addRule(RelativeLayout.ALIGN_TOP, R.id.imageView53)
                layoutParams54.addRule(RelativeLayout.END_OF, R.id.imageView53)
                layoutParams54.leftMargin = dp2
                view.imageView54.layoutParams = layoutParams54
                linearLayoutBox.addView(view)
                if (productsMap[0] == null) {
                    view.imageView50.scaleType = ImageView.ScaleType.CENTER
                    view.imageView50.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView50.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[0].cover, view.imageView50)
                }

                if (productsMap[1] == null) {
                    view.imageView51.scaleType = ImageView.ScaleType.CENTER
                    view.imageView51.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView51.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[1].cover, view.imageView51)
                }

                if (productsMap[2] == null) {
                    view.imageView52.scaleType = ImageView.ScaleType.CENTER
                    view.imageView52.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView52.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[2].cover, view.imageView52)
                }

                if (productsMap[3] == null) {
                    view.imageView53.scaleType = ImageView.ScaleType.CENTER
                    view.imageView53.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView53.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[3].cover, view.imageView53)
                }

                if (productsMap[4] == null) {
                    view.imageView54.scaleType = ImageView.ScaleType.CENTER
                    view.imageView54.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView54.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[4].cover, view.imageView54)
                }

                view.imageView50.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(0, productsMap)
                }
                view.imageView51.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(1, productsMap)
                }

                view.imageView52.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(2, productsMap)
                }
                view.imageView53.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(3, productsMap)
                }

                view.imageView54.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(4, productsMap)
                }
            }

            7 -> {
                val dp68: Int by lazy { screenW * 68 / 375 }
                val dp190: Int by lazy { screenW - dp68 * 2 - dp30 - dp2 * 2 }
                val dp138: Int by lazy { dp68 * 2 + dp2 }
                val dp120: Int by lazy { dp190 - dp68 - dp2 }
                val dp110: Int by lazy { (screenW - dp30 - dp2 * 2) / 3 }
                val layoutParamsImageView70: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp68, dp68) }
                val layoutParamsImageView71: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp68, dp68) }
                val layoutParamsImageView72: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp190, dp190) }
                val layoutParamsImageView73: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp138, dp120) }
                val layoutParamsImageView74: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp110, dp110) }
                val layoutParamsImageView75: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp110, dp110) }
                val layoutParamsImageView76: RelativeLayout.LayoutParams by lazy { RelativeLayout.LayoutParams(dp110, dp110) }
                view = View.inflate(this, R.layout.view_show_window_image7, null)
                view.imageView70.layoutParams = layoutParamsImageView70

                layoutParamsImageView71.leftMargin = dp2
                layoutParamsImageView71.addRule(RelativeLayout.END_OF, R.id.imageView70)
                view.imageView71.layoutParams = layoutParamsImageView71
                layoutParamsImageView72.addRule(RelativeLayout.END_OF, R.id.imageView71)
                layoutParamsImageView72.leftMargin = dp2
                view.imageView72.layoutParams = layoutParamsImageView72

                layoutParamsImageView73.addRule(RelativeLayout.BELOW, R.id.imageView70)
                layoutParamsImageView73.topMargin = dp2
                view.imageView73.layoutParams = layoutParamsImageView73

                layoutParamsImageView74.addRule(RelativeLayout.BELOW, R.id.imageView73)
                layoutParamsImageView74.topMargin = dp2
                view.imageView74.layoutParams = layoutParamsImageView74

                layoutParamsImageView75.addRule(RelativeLayout.ALIGN_TOP, R.id.imageView74)
                layoutParamsImageView75.addRule(RelativeLayout.END_OF, R.id.imageView74)
                layoutParamsImageView75.leftMargin = dp2
                view.imageView75.layoutParams = layoutParamsImageView75

                layoutParamsImageView76.addRule(RelativeLayout.ALIGN_TOP, R.id.imageView74)
                layoutParamsImageView76.addRule(RelativeLayout.END_OF, R.id.imageView75)
                layoutParamsImageView76.leftMargin = dp2
                view.imageView76.layoutParams = layoutParamsImageView76
                linearLayoutBox.addView(view)


                if (productsMap[0] == null) {
                    view.imageView70.scaleType = ImageView.ScaleType.CENTER
                    view.imageView70.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView70.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[0].cover, view.imageView70)
                }

                if (productsMap[1] == null) {
                    view.imageView71.scaleType = ImageView.ScaleType.CENTER
                    view.imageView71.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView71.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[1].cover, view.imageView71)
                }

                if (productsMap[2] == null) {
                    view.imageView72.scaleType = ImageView.ScaleType.CENTER
                    view.imageView72.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView72.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[2].cover, view.imageView72)
                }

                if (productsMap[3] == null) {
                    view.imageView73.scaleType = ImageView.ScaleType.CENTER
                    view.imageView73.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView73.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[3].cover, view.imageView73)
                }

                if (productsMap[4] == null) {
                    view.imageView74.scaleType = ImageView.ScaleType.CENTER
                    view.imageView74.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView74.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[4].cover, view.imageView74)
                }

                if (productsMap[5] == null) {
                    view.imageView75.scaleType = ImageView.ScaleType.CENTER
                    view.imageView75.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView75.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[5].cover, view.imageView75)
                }

                if (productsMap[6] == null) {
                    view.imageView76.scaleType = ImageView.ScaleType.CENTER
                    view.imageView76.setImageBitmap(placeHolderBitmap)
                } else {
                    view.imageView76.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[6].cover, view.imageView76)
                }

                view.imageView70.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(0, productsMap)
                }
                view.imageView71.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(1, productsMap)
                }
                view.imageView72.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(2, productsMap)
                }
                view.imageView73.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(3, productsMap)
                }
                view.imageView74.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(4, productsMap)
                }
                view.imageView75.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(5, productsMap)
                }
                view.imageView76.setOnClickListener {
                    PageUtil.jump2SelectGoodsImageActivity(6, productsMap)
                }
            }
        }
    }

    /**
     * 当有产品被选中
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onImageSelected(message: MessageAddGoodsImages) {
        val bean = ProductBean()
        bean.rid = message.rid
        bean.cover = message.url
        bean.cover_id = message.id
        productsMap.put(message.position, bean)
        setImageForGridView(publishCount)

        setPublishButtonState()
    }

    /**
     * 能否发布
     */
    private fun publishEnable(): Boolean {
        val title = editTextTitle.text.trim().toString()
        val content = editTextContent.text.trim().toString()
        val products = getPublishProducts()
        if (TextUtils.isEmpty(title)) {
            customHeadView.buttonRight.isEnabled = false
            return false
        }
        if (TextUtils.isEmpty(content)) {
            customHeadView.buttonRight.isEnabled = false
            return false
        }
        if (products.isEmpty() || products.size < publishCount) {
            customHeadView.buttonRight.isEnabled = false
            return false
        }
        return true
    }

    private fun setImageForGridView(count: Int) {
        when (count) {
            3 -> {
                if (productsMap[0] != null) {
                    view.imageView30.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[0].cover, view.imageView30)
                }

                if (productsMap[1] != null) {
                    view.imageView31.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[1].cover, view.imageView31)
                }

                if (productsMap[2] != null) {
                    view.imageView32.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[2].cover, view.imageView32)
                }
            }

            5 -> {
                if (productsMap[0] != null) {
                    view.imageView50.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[0].cover, view.imageView50)
                }

                if (productsMap[1] != null) {
                    view.imageView51.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[1].cover, view.imageView51)
                }

                if (productsMap[2] != null) {
                    view.imageView52.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[2].cover, view.imageView52)
                }

                if (productsMap[3] != null) {
                    view.imageView53.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[3].cover, view.imageView53)
                }

                if (productsMap[4] != null) {
                    view.imageView54.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[4].cover, view.imageView54)
                }
            }
            7 -> {
                if (productsMap[0] != null) {
                    view.imageView70.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[0].cover, view.imageView70)
                }

                if (productsMap[1] != null) {
                    view.imageView71.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[1].cover, view.imageView71)
                }

                if (productsMap[2] != null) {
                    view.imageView72.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[2].cover, view.imageView72)
                }

                if (productsMap[3] != null) {
                    view.imageView73.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[3].cover, view.imageView73)
                }

                if (productsMap[4] != null) {
                    view.imageView74.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[4].cover, view.imageView74)
                }

                if (productsMap[5] != null) {
                    view.imageView75.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[5].cover, view.imageView75)
                }

                if (productsMap[6] != null) {
                    view.imageView76.scaleType = ImageView.ScaleType.FIT_XY
                    GlideUtil.loadImageWithFading(productsMap[6].cover, view.imageView76)
                }
            }
        }
    }

    /**
     * 当有标签被选中
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTagSelected(message: MessageShopWindowTag) {
        if (tagList.contains(message.tag)) return
        tagList.add(message.tag)
        adapterTags.notifyDataChanged()
    }

    override fun showLoadingView() {
        dialog.show()
    }

    override fun dismissLoadingView() {
        dialog.dismiss()
    }

    override fun showError(string: String) {
        ToastUtil.showError(string)
    }

    override fun onDestroy() {
        placeHolderBitmap.recycle()
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun setPresenter(presenter: PublishShopWindowPresenter?) {
        setPresenter(presenter)
    }
}