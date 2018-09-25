package com.thn.lexi.order
import android.support.annotation.LayoutRes
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.lexi.R

class AdapterUserAddressList(@LayoutRes res: Int) : BaseQuickAdapter<UserAddressListBean.DataBean, BaseViewHolder>(res) {

    override fun convert(helper: BaseViewHolder, item: UserAddressListBean.DataBean) {

        val checkBox = helper.getView<CheckBox>(R.id.checkBox)

        checkBox.isChecked = item.is_default

        helper.setText(R.id.textViewName,item.first_name)
        helper.setText(R.id.textViewFullAddress,item.full_address)
        helper.setText(R.id.textViewProvinceCity,"${item.province}，${item.city}")

        val textViewZip = helper.getView<TextView>(R.id.textViewZip)

        if (TextUtils.isEmpty(item.zipcode)){
            textViewZip.visibility = View.GONE
        }else{
            textViewZip.visibility = View.VISIBLE
            textViewZip.text = "${item.zipcode}"
        }

        helper.setText(R.id.textViewPhone,item.mobile)
        helper.addOnClickListener(R.id.checkBox)
    }
}