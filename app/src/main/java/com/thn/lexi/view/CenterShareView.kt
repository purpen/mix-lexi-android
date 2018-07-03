package com.thn.lexi.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R
import kotlinx.android.synthetic.main.view_share_goods.view.*

class CenterShareView : LinearLayout, View.OnClickListener {
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.view_share_goods, this)
        initRecyclerView()
        installListener()
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val list = ArrayList<ImageItem>()
        val strings = resources.getStringArray(R.array.strings_dialog_share)
        val imgIds = listOf(R.mipmap.icon_dialog_moment, R.mipmap.icon_dialog_wechat, R.mipmap.icon_dialog_sina, R.mipmap.icon_dialog_qq, R.mipmap.icon_dialog_zone)
        for (i in strings.indices) {
            list.add(ImageItem(imgIds[i],strings[i]))
        }
        val adapter = ShareAdapter(R.layout.adapter_item_share)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        adapter.setNewData(list)

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                if (parent.getChildAdapterPosition(view)!=0) outRect.left = resources.getDimensionPixelSize(R.dimen.dp12)
            }
        })
    }

    private fun installListener() {
        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val button: Button? = v as? Button
        val id = button?.id
        when (id) {
            R.id.button -> {
                val service = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val text = editText.text.toString().trim()
                service.primaryClip = ClipData.newPlainText(text, text)
            }
        }
    }


    data class ImageItem(var imgId: Int, var text: String)

    class ShareAdapter(layoutResId: Int) : BaseQuickAdapter<ImageItem, BaseViewHolder>(layoutResId) {
        override fun convert(helper: BaseViewHolder, item: ImageItem) {
            helper.getView<ImageView>(R.id.imageView).setImageResource(item.imgId)
            helper.setText(R.id.textView, item.text)
        }
    }
}