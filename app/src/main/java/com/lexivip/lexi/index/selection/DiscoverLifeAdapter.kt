package com.lexivip.lexi.index.selection
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.GlideUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean

class DiscoverLifeAdapter(layoutResId: Int) : BaseQuickAdapter<ShopWindowBean, BaseViewHolder>(layoutResId) {
    private val dp25 by lazy { DimenUtil.dp2px(25.0) }
    private val dp2 by lazy { DimenUtil.dp2px(2.0) }
    override fun convert(helper: BaseViewHolder, item: ShopWindowBean) {

        val imageViewAvatar = helper.getView<ImageView>(R.id.imageViewAvatar)
        GlideUtil.loadCircleImageWidthDimen(item.user_avatar,imageViewAvatar,dp25)

        helper.setText(R.id.textViewName,item.user_name)
        helper.setText(R.id.textViewTitle1,item.title)
        helper.setText(R.id.textViewTitle2,item.description)

        if (item.products.isEmpty()) return

        val list = ArrayList<String>()

        if (item.products.size <= 3){
            for (product in item.products){
                list.add(product.cover)
            }
        }else{
            val subList = item.products.subList(0, 3)
            for (product in subList){
                list.add(product.cover)
            }
        }

        val list1 = ArrayList<DiscoverLifeProductAdapter.MultipleItem>()

        for (i in list.indices){
            if (i==0){//占2列宽
                list1.add(DiscoverLifeProductAdapter.MultipleItem(list[i],DiscoverLifeProductAdapter.MultipleItem.ITEM_TYPE_SPAN2,DiscoverLifeProductAdapter.MultipleItem.ITEM_SPAN2_SIZE))
            }else{//占1列
                list1.add(DiscoverLifeProductAdapter.MultipleItem(list[i],DiscoverLifeProductAdapter.MultipleItem.ITEM_TYPE_SPAN1,DiscoverLifeProductAdapter.MultipleItem.ITEM_SPAN1_SIZE))
            }
        }

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
        val adapter = DiscoverLifeProductAdapter(list1)
        val gridLayoutManager = GridLayoutManager(AppApplication.getContext(),2)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { _, _, _ ->//跳转橱窗详情
            PageUtil.jump2ShopWindowDetailActivity(item.rid)
        }

        adapter.setSpanSizeLookup { _, position ->
            list1[position].spanSize
        }

        if (recyclerView.itemDecorationCount>0) return

        recyclerView.addItemDecoration(object:RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (position == 0){
                    outRect.right = dp2
                }

                if (position==1){
                    outRect.bottom = dp2
                }
            }
        })

    }
}