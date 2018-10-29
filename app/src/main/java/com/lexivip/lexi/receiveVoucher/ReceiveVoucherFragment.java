package com.lexivip.lexi.receiveVoucher;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.lexivip.lexi.R;

/**
 * 领券中心分类
 */
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

    @Override
    public void initView() {
        super.initView();
        WaitingDialog dialog=new WaitingDialog(getActivity());
        Button bt_common=getView().findViewById(R.id.bt_common);
        Button bt_single=getView().findViewById(R.id.bt_single);
        RecyclerView recyclerView=getView().findViewById(R.id.recyclerView);
        RecyclerView recyclerViewCommon=getView().findViewById(R.id.recyclerViewCommon);

    }
}
