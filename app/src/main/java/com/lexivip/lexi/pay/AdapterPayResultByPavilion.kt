package com.lexivip.lexi.pay
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.CustomLinearLayoutManager
import com.lexivip.lexi.R

class AdapterPayResultByPavilion(@LayoutRes res: Int) : BaseQuickAdapter<PayResultBean.DataBean.OrdersBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: PayResultBean.DataBean.OrdersBean) {
        helper.setText(R.id.textViewPavilionName, item.store.store_name)
        helper.setText(R.id.textViewFreight, "${item.freight}")

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewGoods)

        val linearLayoutManager = CustomLinearLayoutManager(AppApplication.getContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.setScrollEnabled(false)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = AdapterPayResultGoods(R.layout.adapter_pay_result_goods)
        recyclerView.adapter = adapter
        adapter.setNewData(item.items)
    }
}