package com.lexivip.lexi.pay
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lexivip.lexi.AppApplication
import com.lexivip.lexi.CustomLinearLayoutManager
import com.lexivip.lexi.R
import com.lexivip.lexi.order.SortBy
import com.lexivip.lexi.order.StoreItemBean
import java.util.*

class AdapterPayResultByPavilion(@LayoutRes res: Int) : BaseQuickAdapter<StoreItemBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: StoreItemBean) {

        //取第一个商品以获取发货地店铺名等信息
        val productBean = item.items[0]

        helper.setText(R.id.textViewPavilionName, productBean?.store_name)
        helper.setText(R.id.textViewFreight, "${item.expressExpense}")

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewGoods)

        val linearLayoutManager = CustomLinearLayoutManager(AppApplication.getContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.setScrollEnabled(false)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = AdapterPayResultGoods(R.layout.adapter_pay_result_goods)
        recyclerView.adapter = adapter

        //根据物流模板选出添加选择物流item,map的覆盖效果
        val hashMap = HashMap<String, String>()
        for (product in item.items) {
            hashMap[product.fid] = product.rid
            product.map = hashMap
        }


        if (hashMap.size > 1) {//两种及2种以上物流摸板执行排序
            Collections.sort(item.items, SortBy())
        }

        adapter.setNewData(item.items)

    }
}