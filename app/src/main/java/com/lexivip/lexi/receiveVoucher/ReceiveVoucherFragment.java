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
import com.lexivip.lexi.brandHouse.BrandHouseGoodsFragment;
import com.lexivip.lexi.search.AdapterSearchGoods;
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
        //voucherGoods = new AdapterReceiveVoucherGoods(R.layout.adapter_voucher_goods,null);
        voucherBrands.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return voucherBrands.getData().get(position).getSpanSize();
            }
        });
        recyclerView.setAdapter(voucherBrands);
        recyclerView.addItemDecoration(new DividerItemDecoration(AppApplication.getContext()));
        recyclerViewCommon.setAdapter(voucherGoods);

        presenter.loadBrand(key,String.valueOf(page));
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
                LogUtil.e("一个"+i);
                list.add(new MultipleItem(brandBean.data.coupons.get(i),MultipleItem.ITEM_TYPE_SPAN1,MultipleItem.ITEM_SPAN1_SIZE));
                LogUtil.e("第几个："+i+"几个:"+list.get(i).getItemType());
            }else {
                LogUtil.e("两个"+i);
                list.add(new MultipleItem(brandBean.data.coupons.get(i),MultipleItem.ITEM_TYPE_SPAN2,MultipleItem.ITEM_SPAN2_SIZE));
                LogUtil.e("第几个："+i+"几个:"+list.get(i).getItemType());
            }
            //list.add(new AdapterReceiveVoucherBrand.MultipleItem(2,brandBean.data.coupons.get(i)));
        }
        voucherBrands.setNewData(list);
        /*voucherBrands.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return voucherBrands.getData().get(position).getSpanSize();
            }
        });*/
    }

    @Override
    public void getGoods(VoucherGoodsBean bean) {

    }

    @Override
    public void setPresenter(ReceiveVoucherFragmentContract.Presenter presenter) {
        setPresenter(presenter);
    }

    private class DividerItemDecoration extends Y_DividerItemDecoration {
        private int color = Util.getColor(android.R.color.white);

        public DividerItemDecoration(Context context) {
            super(context);
        }

        @Override
        public Y_Divider getDivider(int itemPosition) {
            LogUtil.e("position：" + itemPosition);
            Y_Divider divider;

            int count = voucherBrands.getItemCount();
            LogUtil.e("总行数：" + count);
            MultipleItem item = voucherBrands.getItem(itemPosition);
            if (item == null) {
                divider = new Y_DividerBuilder()
                        .create();
                return divider;
            } else {
                divider = new Y_DividerBuilder()
                        .setTopSideLine(true, color, 20f, 0f, 0f)
                        .setLeftSideLine(true, color, 5f, 0f, 0f)
                        .create();
                return divider;
            }
        }
    }
}
