package com.lexivip.lexi.receiveVoucher;

import com.basemodule.ui.BaseFragment;
import com.lexivip.lexi.R;

public class ReceiveVoucherRecommendFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_receive_voucher_recommend;
    }
    public static ReceiveVoucherRecommendFragment newInstance(){
        ReceiveVoucherRecommendFragment fragment=new ReceiveVoucherRecommendFragment();
        return fragment;
    }
}
