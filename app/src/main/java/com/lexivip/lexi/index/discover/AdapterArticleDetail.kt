package com.lexivip.lexi.index.discover

import android.graphics.Paint
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.basemodule.tools.ScreenUtil
import com.basemodule.tools.Util
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import org.json.JSONObject

class AdapterArticleDetail(list: List<MultipleItem>,channelName:String) : BaseMultiItemQuickAdapter<AdapterArticleDetail.MultipleItem, BaseViewHolder>(list) {
    private val channelName:String by lazy { channelName }
    private val dp15:Int by lazy { DimenUtil.dp2px(15.0) }
    private val dp10:Int by lazy { DimenUtil.dp2px(10.0) }
    private val dp100:Int by lazy { DimenUtil.dp2px(100.0) }
    private val dp246:Int by lazy { DimenUtil.dp2px(246.0) }
    private val dp4:Int by lazy { DimenUtil.dp2px(4.0) }
    private val screenW:Int by lazy { ScreenUtil.getScreenWidth() }
    init {
        addItemType(MultipleItem.TEXT_ITEM_TYPE, R.layout.adapter_goods_detail_text)
        addItemType(MultipleItem.IMAGE_ITEM_TYPE, R.layout.adapter_article_detail_image)
        addItemType(MultipleItem.LARGE_PRODUCT_ITEM_TYPE, R.layout.adapter_item_large_product)
        addItemType(MultipleItem.SMALL_PRODUCT_ITEM_TYPE, R.layout.adapter_item_small_product)
    }

    class MultipleItem(var content: JSONObject, private var itemType: Int) : MultiItemEntity {

        override fun getItemType(): Int {
            return itemType
        }

        companion object {
            const val TEXT_ITEM_TYPE = 0x0010
            const val IMAGE_ITEM_TYPE = 0x0011
            const val LARGE_PRODUCT_ITEM_TYPE = 0x0012
            const val SMALL_PRODUCT_ITEM_TYPE = 0x0013
        }
    }


    override fun convert(helper: BaseViewHolder, item: AdapterArticleDetail.MultipleItem) {

        when (item.itemType) {
            MultipleItem.TEXT_ITEM_TYPE -> {
                val content = item.content.optString("content")
                val textView = helper.getView<TextView>(R.id.textView)
                textView.text = Html.fromHtml(content)
            }

            MultipleItem.IMAGE_ITEM_TYPE -> {
                val content = item.content.optString("content")
                val width = item.content.optInt("width")
                val height = item.content.optInt("height")
                val imageViewH = screenW*height/width
                val imageView = helper.getView<ImageView>(R.id.imageView)
                val layoutParams = ViewGroup.MarginLayoutParams(screenW, imageViewH)
                layoutParams.bottomMargin = dp10
                imageView.layoutParams = layoutParams
                if (TextUtils.equals(channelName,Util.getString(R.string.text_seeding_note))){ //是种草清单
                    imageView.setPadding(dp15,0,dp15,0)
                }else{
                    imageView.setPadding(0,0,0,0)
                }
                GlideUtil.loadImageWithDimenAndRadius(content, imageView,0,screenW,imageViewH,R.drawable.bg_image_place_holder)
            }

            MultipleItem.LARGE_PRODUCT_ITEM_TYPE -> {
                val content = item.content.optJSONObject("content")
                val name = content.optString("name")
                val cover = content.optString("cover")
                val min_sale_price = content.optDouble("min_sale_price")
                val min_price = content.optDouble("min_price")
                val is_sold_out = content.optBoolean("is_sold_out")
                val is_free_postage = content.optBoolean("is_free_postage")
                val rid = content.optString("rid")
                val imageView = helper.getView<ImageView>(R.id.imageView)
                GlideUtil.loadImageWithDimenAndRadius(cover, imageView,dp4,screenW,dp246,R.drawable.bg_image_place_holder)
                helper.itemView.setOnClickListener {
                    PageUtil.jump2GoodsDetailActivity(rid)
                }
                helper.setText(R.id.textViewName, name)

                val imageViewFreeExpress = helper.getView<ImageView>(R.id.imageViewFreeExpress)
                if (is_free_postage) {
                    imageViewFreeExpress.visibility = View.VISIBLE
                } else {
                    imageViewFreeExpress.visibility = View.GONE
                }

                val imageViewStatus = helper.getView<ImageView>(R.id.imageViewStatus)
                if (is_sold_out) {
                    imageViewStatus.visibility = View.VISIBLE
                } else {
                    imageViewStatus.visibility = View.GONE
                }

                val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)

                if (min_sale_price == 0.0) { //折扣价为0,显示真实价格
                    helper.setText(R.id.textViewPrice, "￥${min_price}")
                    textViewOldPrice.visibility = View.GONE
                } else { //折扣价不为0显示折扣价格和带划线的真实价格
                    helper.setText(R.id.textViewPrice, "￥${min_sale_price}")
                    textViewOldPrice.visibility = View.VISIBLE
                    textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
                    textViewOldPrice.text = "￥" + min_price
                }

                val textViewJump = helper.getView<TextView>(R.id.textViewJump)

                textViewJump.setOnClickListener { PageUtil.jump2GoodsDetailActivity(rid) }
            }

            MultipleItem.SMALL_PRODUCT_ITEM_TYPE -> {
                val content = item.content.optJSONObject("content")
                val name = content.optString("name")
                val cover = content.optString("cover")
                val min_sale_price = content.optDouble("min_sale_price")
                val min_price = content.optDouble("min_price")
                val like_count = content.optInt("like_count")
                val is_sold_out = content.optBoolean("is_sold_out")
                val is_free_postage = content.optBoolean("is_free_postage")
                val rid = content.optString("rid")

                val imageView = helper.getView<ImageView>(R.id.imageView)
                GlideUtil.loadImageWithDimenAndRadius(cover, imageView,dp4,dp100,dp100)

                helper.itemView.setOnClickListener {
                    PageUtil.jump2GoodsDetailActivity(rid)
                }
                helper.setText(R.id.textViewName, name)

                val imageViewFreeExpress = helper.getView<ImageView>(R.id.imageViewFreeExpress)
                if (is_free_postage) {
                    imageViewFreeExpress.visibility = View.VISIBLE
                } else {
                    imageViewFreeExpress.visibility = View.GONE
                }

                val imageViewStatus = helper.getView<ImageView>(R.id.imageViewStatus)
                if (is_sold_out) {
                    imageViewStatus.visibility = View.VISIBLE
                } else {
                    imageViewStatus.visibility = View.GONE
                }

                val textViewOldPrice = helper.getView<TextView>(R.id.textViewOldPrice)

                if (min_sale_price == 0.0) { //折扣价为0,显示真实价格
                    helper.setText(R.id.textViewPrice, "￥${min_price}")
                    textViewOldPrice.visibility = View.GONE
                } else { //折扣价不为0显示折扣价格和带划线的真实价格
                    helper.setText(R.id.textViewPrice, "￥${min_sale_price}")
                    textViewOldPrice.visibility = View.VISIBLE
                    textViewOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
                    textViewOldPrice.text = "￥" + min_price
                }

                helper.setText(R.id.textViewLikeNum, "喜欢 +$like_count")

                val textViewJump = helper.getView<TextView>(R.id.textViewJump)

                textViewJump.setOnClickListener { PageUtil.jump2GoodsDetailActivity(rid) }
            }
        }
    }
}