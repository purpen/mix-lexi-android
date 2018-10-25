package com.lexivip.lexi.receiveVoucher;

import android.os.Bundle;

import com.basemodule.ui.BaseFragment;
import com.lexivip.lexi.R;

public class ReceiveVoucherFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_receive_voucher;
    }
    public static ReceiveVoucherFragment newInstance(String rid){
        ReceiveVoucherFragment fragment=new ReceiveVoucherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key",rid);
        fragment.setArguments(bundle);
        return fragment;
    }
}
