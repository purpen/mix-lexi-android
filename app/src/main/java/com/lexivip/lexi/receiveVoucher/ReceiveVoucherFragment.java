package com.lexivip.lexi.receiveVoucher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.PageUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.brandHouse.BrandHouseActivity;
import com.lexivip.lexi.user.login.UserProfileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 领券中心分类
 */
public class ReceiveVoucherFragment extends BaseFragment implements ReceiveVoucherFragmentContract.View, View.OnClickListener {

    private WaitingDialog dialog;
    private List<MultipleItem> list;
    private AdapterReceiveVoucherBrand voucherBrands;
    private AdapterReceiveVoucherGoods voucherGoods;
    private ReceiveVoucherFragmentPresenter presenter;
    private int page=1;
    private int pageSingle=1;
    private String key;
    private RecyclerView recyclerView;
    private String rid;
    private boolean isSingle;
    private Button bt_common;
    private Button bt_single;
    private LinearLayout ll_null;
    private RecyclerView recyclerViewSingle;
    private boolean isCommenNull;
    private boolean isSingleNull;
    private int goodsPosition;

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
        bt_common = getView().findViewById(R.id.bt_common);
        bt_single = getView().findViewById(R.id.bt_single);
        ll_null = getView().findViewById(R.id.ll_null);
        bt_common.setOnClickListener(this);
        bt_single.setOnClickListener(this);
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerViewSingle = getView().findViewById(R.id.recyclerViewSingle);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewSingle.setLayoutManager(new GridLayoutManager(getContext(),2));
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
        recyclerViewSingle.setAdapter(voucherGoods);

        rid = UserProfileUtil.getUserId();
        presenter.loadBrand(key,String.valueOf(page));
        presenter.loadGoods(key, rid,String.valueOf(pageSingle));
    }

    @Override
    public void installListener() {
        super.installListener();
        voucherBrands.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadBrand(key,String.valueOf(page));
            }
        },recyclerView);
        voucherGoods.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadGoods(key, rid,String.valueOf(pageSingle));
            }
        }, recyclerViewSingle);
        voucherGoods.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                goodsPosition = position;
                switch (view.getId()){
                    case R.id.button:
                        if (voucherGoods.getData().get(position).is_grant){
                            if (0!=voucherGoods.getData().get(position).surplus_count){
                                PageUtil.jump2GoodsDetailActivity(voucherGoods.getData().get(position).product_rid);
                            }
                        }else {
                            presenter.receiveVoucher(voucherGoods.getData().get(position).product_rid,voucherGoods.getData().get(position).store_rid);
                        }
                        break;
                    case R.id.ll_content:
                        PageUtil.jump2GoodsDetailActivity(voucherGoods.getData().get(position).product_rid);
                        break;
                }
            }
        });
        voucherBrands.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getActivity(),BrandHouseActivity.class);
                intent.putExtra("rid",voucherBrands.getData().get(position).getBean().store_rid);
                getActivity().startActivity(intent);
            }
        });
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
            if (i==0||i==5){
                list.add(new MultipleItem(brandBean.data.coupons.get(i),MultipleItem.ITEM_TYPE_SPAN1,MultipleItem.ITEM_SPAN1_SIZE));
            }else {
                list.add(new MultipleItem(brandBean.data.coupons.get(i),MultipleItem.ITEM_TYPE_SPAN2,MultipleItem.ITEM_SPAN2_SIZE));
            }
        }
        if (page==1) {
            if (list.size()==0){
                isCommenNull=true;
                ll_null.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }else {
                isCommenNull=false;
                ll_null.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            voucherBrands.setNewData(list);
        }else {
            voucherBrands.addData(list);
        }
        page++;
    }

    @Override
    public void getGoods(VoucherGoodsBean bean) {
        if (pageSingle==1) {
            if (bean.data.coupons.size()==0){
                isSingleNull=true;
                ll_null.setVisibility(View.VISIBLE);
                recyclerViewSingle.setVisibility(View.GONE);
            }else {
                isSingleNull=false;
                ll_null.setVisibility(View.GONE);
                recyclerViewSingle.setVisibility(View.VISIBLE);
            }
            voucherGoods.setNewData(bean.data.coupons);
        }else {
            voucherGoods.addData(bean.data.coupons);
        }
        pageSingle++;
    }

    @Override
    public void loadMoreFail() {
        if (isSingle){
            voucherGoods.loadMoreFail();
        }else {
            voucherBrands.loadMoreFail();
        }
    }

    @Override
    public void loadMoreEnd() {
        if (isSingle){
            voucherGoods.loadMoreEnd();
        }else {
            voucherBrands.loadMoreEnd();
        }
    }

    @Override
    public void loadMoreComplete() {
        if (isSingle){
            voucherGoods.loadMoreComplete();
        }else {
            voucherBrands.loadMoreComplete();
        }
    }

    @Override
    public void getReceive(boolean isReceive) {
        voucherGoods.getData().get(goodsPosition).is_grant=isReceive;
        voucherGoods.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(ReceiveVoucherFragmentContract.Presenter presenter) {
        setPresenter(presenter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_common:
                if (isSingle){
                    isSingle=false;
                    bt_common.setBackgroundResource(R.drawable.bg_color5fe4b1_stroke_solid);
                    bt_common.setTextColor(Util.getColor(R.color.color_66e6b5));
                    bt_single.setBackgroundResource(R.drawable.corner_bg_f5f7f9);
                    bt_single.setTextColor(Util.getColor(R.color.text_primary));
                    recyclerViewSingle.setVisibility(View.GONE);
                    if (isCommenNull){
                        ll_null.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else {
                        ll_null.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.bt_single:
                if (!isSingle) {
                    isSingle = true;
                    bt_common.setBackgroundResource(R.drawable.corner_bg_f5f7f9);
                    bt_common.setTextColor(Util.getColor(R.color.text_primary));
                    bt_single.setBackgroundResource(R.drawable.bg_color5fe4b1_stroke_solid);
                    bt_single.setTextColor(Util.getColor(R.color.color_66e6b5));
                    recyclerView.setVisibility(View.GONE);
                    if (isSingleNull){
                        ll_null.setVisibility(View.VISIBLE);
                        recyclerViewSingle.setVisibility(View.GONE);
                    }else {
                        if (pageSingle==1){
                            presenter.loadGoods(key, rid,String.valueOf(pageSingle));
                        }
                        ll_null.setVisibility(View.GONE);
                        recyclerViewSingle.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }
}
