package com.thn.lexi.order
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.CustomLinearLayoutManager
import com.thn.lexi.R

class AdapterOrderByPavilion(@LayoutRes res: Int) : BaseQuickAdapter<StoreItemBean, BaseViewHolder>(res) {
    override fun convert(helper: BaseViewHolder, item: StoreItemBean) {

        //取第一个商品以获取发货地店铺名等信息
        val productBean = item.items[0]

        helper.setText(R.id.textViewPavilionName,productBean?.store_name)

        helper.setText(R.id.textViewFromAddress,"从${productBean?.delivery_province}发货")

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewGoods)

        val linearLayoutManager = CustomLinearLayoutManager(AppApplication.getContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.setScrollEnabled(false)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = AdapterOrderGoods(R.layout.adapter_confirm_order_goods)
        recyclerView.adapter = adapter
        adapter.setNewData(item.items)
    }
}