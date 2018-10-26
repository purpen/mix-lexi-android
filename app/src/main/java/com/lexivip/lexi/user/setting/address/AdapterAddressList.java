package com.lexivip.lexi.user.setting.address;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;
import com.lexivip.lexi.order.UserAddressListBean;

import java.util.List;

public class AdapterAddressList extends BaseQuickAdapter<UserAddressListBean.DataBean,BaseViewHolder> {
    public AdapterAddressList(int layoutResId, @Nullable List<UserAddressListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserAddressListBean.DataBean item) {
        helper.setText(R.id.textViewName,item.first_name);
        helper.setText(R.id.textViewFullAddress,item.full_address);
        helper.setText(R.id.textViewProvinceCity,item.province+"，"+item.city);
        if (TextUtils.isEmpty(item.zipcode)){
            helper.setGone(R.id.textViewZip,false);
        }else {
            helper.setVisible(R.id.textViewZip,true);
            helper.setText(R.id.textViewZip,item.zipcode);
        }
        helper.setText(R.id.textViewPhone,item.mobile);
    }
}
