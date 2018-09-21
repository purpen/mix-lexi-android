package com.thn.lexi.order

import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.AppApplication
import com.thn.lexi.CustomLinearLayoutManager
import com.thn.lexi.R
import com.thn.lexi.beans.ProductBean
import java.util.*
import kotlin.collections.ArrayList

class AdapterOrderByPavilion(@LayoutRes res: Int, address_rid: String) : BaseQuickAdapter<StoreItemBean, BaseViewHolder>(res) {

    private val addressId:String by lazy { address_rid }
    override fun convert(helper: BaseViewHolder, item: StoreItemBean) {

        //取第一个商品以获取发货地店铺名等信息
        val productBean = item.items[0]

        helper.setText(R.id.textViewPavilionName, productBean?.store_name)

        val textViewPavilionName = helper.getView<TextView>(R.id.textViewFromAddress)

        textViewPavilionName.text = "从${productBean?.delivery_country}${productBean?.delivery_province}发货"

        if (TextUtils.isEmpty(item.fullReductionText)) {
            helper.setText(R.id.textViewPromotion, "无")
        } else {
            helper.setText(R.id.textViewPromotion, item.fullReductionText)
        }

        if (item.expressExpense==0.0){
            helper.setText(R.id.textViewFreight,"包邮")
        }else{
            helper.setText(R.id.textViewFreight,"${item.expressExpense}")
        }

        val textViewPavilionCoupon = helper.getView<TextView>(R.id.textViewPavilionCoupon)

        if (item.couponPrice==0){
            textViewPavilionCoupon.text = "选择优惠券"
        }else{
            helper.setText(R.id.textViewPavilionCoupon,"已抵扣￥${item.couponPrice}")
        }

        helper.addOnClickListener(R.id.textViewPavilionCoupon)

        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerViewGoods)

        val linearLayoutManager = CustomLinearLayoutManager(AppApplication.getContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.setScrollEnabled(false)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = AdapterOrderGoods(R.layout.adapter_confirm_order_goods)
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


        adapter.setOnItemChildClickListener { _, view, position ->
            val bean = adapter.getItem(position) as ProductBean

            //设置默认快递
            var defaultExpress:ExpressInfoBean? = null
            for (express in bean.express){
                if (express.is_default){
                    defaultExpress = express
                    break
                }
            }

            when(view.id){
                R.id.relativeLayoutGoodsItemExpress ->{
                    val selectExpressRequestBean = SelectExpressRequestBean()
                    selectExpressRequestBean.productBean = bean
                    selectExpressRequestBean.defaultExpress = defaultExpress
                    selectExpressRequestBean.expressSendAddress = "从${productBean?.delivery_country}${productBean?.delivery_province}发货"
                    selectExpressRequestBean.address_rid = addressId
                    selectExpressRequestBean.fid = bean.fid

                    val items = ArrayList<ProductBean>()
                    for (product in item.items) {
                        if (TextUtils.equals(product.fid,bean.fid)){
                            product.sku = product.rid
                            items.add(product)
                        }
                    }
                    selectExpressRequestBean.items = items
                    val intent = Intent(mContext,SelectExpressActivity::class.java)
                    intent.putExtra(SelectExpressActivity::class.java.simpleName,selectExpressRequestBean)
                    mContext.startActivity(intent)
                }
            }
        }
    }
}