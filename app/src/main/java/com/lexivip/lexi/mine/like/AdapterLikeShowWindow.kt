package com.lexivip.lexi.mine.like
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.basemodule.tools.DimenUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.PageUtil
import com.lexivip.lexi.R
import com.lexivip.lexi.beans.ShopWindowBean
import com.lexivip.lexi.index.selection.DiscoverLifeProductAdapter

class AdapterLikeShowWindow(layoutResId: Int) : BaseQuickAdapter<ShopWindowBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ShopWindowBean) {

        helper.setText(R.id.textView,item.title)

        if (item.product_covers.isEmpty()) return

        val list = ArrayList<String>()

        if (item.product_covers.size <= 3){
            list.addAll(item.product_covers)
        }else{
            val subList = item.product_covers.subList(0, 3)
            list.addAll(subList)
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

        adapter.setOnItemClickListener { _, _, _ ->
            PageUtil.jump2ShopWindowDetailActivity(item.rid)
        }

        adapter.setSpanSizeLookup { _, position ->
            list1[position].spanSize
        }

        if (recyclerView.itemDecorationCount>0) return
        val size = DimenUtil.getDimensionPixelSize(R.dimen.dp2)
        recyclerView.addItemDecoration(object:RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (position == 0){
                    outRect.right = size
                }

                if (position==1){
                    outRect.bottom = size
                }
            }
        })

    }
}