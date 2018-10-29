package com.lexivip.lexi.receiveVoucher;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 领券中心推荐
 */
public class ReceiveVoucherRecommendFragment extends BaseFragment implements ReceiveVoucherRecommendContract.View{

    private WaitingDialog dialog;
    private List<AdapterReceiveVoucherBrand.MultipleItem> list;
    private AdapterReceiveVoucherBrand voucherBrand;
    private AdapterReceiveVoucherGoods voucherGoods;
    private AdapterReceiveVoucherOfficial voucherOfficial;
    private ReceiveVoucherRecommendPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_receive_voucher_recommend;
    }
    public static ReceiveVoucherRecommendFragment newInstance(){
        ReceiveVoucherRecommendFragment fragment=new ReceiveVoucherRecommendFragment();
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(getActivity());
        presenter = new ReceiveVoucherRecommendPresenter(this);
        RecyclerView recyclerViewOfficial=getView().findViewById(R.id.recyclerViewOfficial);
        RecyclerView recyclerViewBrand=getView().findViewById(R.id.recyclerViewBrand);
        ImageView imageView=getView().findViewById(R.id.imageView);
        RecyclerView recyclerViewGoods=getView().findViewById(R.id.recyclerViewGoods);
        list = new ArrayList<>();
        voucherBrand = new AdapterReceiveVoucherBrand(list,getActivity());
        voucherGoods = new AdapterReceiveVoucherGoods(R.layout.adapter_voucher_goods,null);
        voucherOfficial = new AdapterReceiveVoucherOfficial(R.layout.adapter_voucher_official,null);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewOfficial.setLayoutManager(manager);
        recyclerViewOfficial.setAdapter(voucherOfficial);
        recyclerViewGoods.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewGoods.setAdapter(voucherGoods);
        recyclerViewBrand.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewBrand.setAdapter(voucherBrand);
        voucherBrand.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return voucherBrand.getData().get(position).getItemType();
            }
        });
        //presenter.loadBrand();
        //presenter.loadGoods();
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
    public void getImage() {

    }

    @Override
    public void getOfficial(VoucherOfficialBean bean) {

    }

    @Override
    public void getBrand(VoucherBrandBean brandBean) {

    }

    @Override
    public void getGoods(VoucherGoodsBean bean) {

    }

    @Override
    public void setPresenter(ReceiveVoucherRecommendContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
