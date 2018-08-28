package com.thn.lexi.mine
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.R
import com.thn.lexi.index.selection.DiscoverLifeBean
import com.thn.lexi.index.selection.DiscoverLifeProductAdapter

class AdapterLikeShowWindow(layoutResId: Int) : BaseQuickAdapter<DiscoverLifeBean.DataBean.ShopWindowsBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: DiscoverLifeBean.DataBean.ShopWindowsBean) {

        helper.setText(R.id.textView,item.description)

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

        adapter.setOnItemClickListener { adapter, view, position ->
            val multipleItem = adapter.getItem(position) as DiscoverLifeProductAdapter.MultipleItem
            ToastUtil.showInfo("商品详情"+position+multipleItem.str)
        }

        adapter.setSpanSizeLookup { _, position ->
            list1[position].spanSize
        }

        if (recyclerView.itemDecorationCount>0) return
        val size = DimenUtil.getDimensionPixelSize(R.dimen.dp2)
        recyclerView.addItemDecoration(object:RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
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