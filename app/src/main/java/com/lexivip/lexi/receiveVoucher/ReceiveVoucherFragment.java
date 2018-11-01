package com.lexivip.lexi.receiveVoucher;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 领券中心分类
 */
public class ReceiveVoucherFragment extends BaseFragment implements ReceiveVoucherFragmentContract.View{

    private WaitingDialog dialog;
    private List<MultipleItem> list;
    private AdapterReceiveVoucherBrand voucherBrands;
    private AdapterReceiveVoucherGoods voucherGoods;
    private ReceiveVoucherFragmentPresenter presenter;
    private int page=1;
    private String key;
    private RecyclerView recyclerView;
    private String rid;

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
        dialog = new WaitingDialog(getActivity());
        presenter = new ReceiveVoucherFragmentPresenter(this);
        Bundle bundle=getArguments();
        key = bundle.getString("key");
        Button bt_common=getView().findViewById(R.id.bt_common);
        Button bt_single=getView().findViewById(R.id.bt_single);
        recyclerView = getView().findViewById(R.id.recyclerView);
        RecyclerView recyclerViewCommon=getView().findViewById(R.id.recyclerViewCommon);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewCommon.setLayoutManager(new GridLayoutManager(getContext(),2));
        list = new ArrayList<>();
        voucherBrands = new AdapterReceiveVoucherBrand(list,getActivity());
        voucherGoods = new AdapterReceiveVoucherGoods(R.layout.adapter_voucher_goods,null);
        voucherBrands.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return voucherBrands.getData().get(position).getSpanSize();
            }
        });
        recyclerView.setAdapter(voucherBrands);
        recyclerViewCommon.setAdapter(voucherGoods);

        rid = UserProfileUtil.getUserId();
        presenter.loadBrand(key,String.valueOf(page));
        presenter.loadGoods(key, rid);

        voucherBrands.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadBrand(key,String.valueOf(page));
            }
        },recyclerView);
        voucherGoods.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadGoods(key, rid);
            }
        },recyclerViewCommon);
    }

    @Override
    public void showLoadingView() {
        dialog.show();
    }

    @Override
    public void dismissLoadingView() {
        dialog.dismiss();
    }

    @Override
    public void showError(@NonNull String error) {
        if (dialog!=null){
            dialog.dismiss();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void getBrand(VoucherBrandBean brandBean) {
        for (int i=0;i<brandBean.data.coupons.size();i++){
            if (i==0||i==9){
                list.add(new MultipleItem(brandBean.data.coupons.get(i),MultipleItem.ITEM_TYPE_SPAN1,MultipleItem.ITEM_SPAN1_SIZE));
            }else {
                list.add(new MultipleItem(brandBean.data.coupons.get(i),MultipleItem.ITEM_TYPE_SPAN2,MultipleItem.ITEM_SPAN2_SIZE));
            }
        }
        voucherBrands.setNewData(list);
    }

    @Override
    public void getGoods(VoucherGoodsBean bean) {
        voucherGoods.setNewData(bean.data.coupons);
    }

    @Override
    public void setPresenter(ReceiveVoucherFragmentContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
