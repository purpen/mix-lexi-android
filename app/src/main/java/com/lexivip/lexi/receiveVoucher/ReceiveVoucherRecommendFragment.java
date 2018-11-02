package com.lexivip.lexi.receiveVoucher;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.R;
import com.lexivip.lexi.user.login.UserProfileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 领券中心推荐
 */
public class ReceiveVoucherRecommendFragment extends BaseFragment implements ReceiveVoucherRecommendContract.View{

    private WaitingDialog dialog;
    private List<MultipleItem> list;
    private AdapterReceiveVoucherBrand voucherBrand;
    private AdapterReceiveVoucherGoods voucherGoods;
    private AdapterReceiveVoucherOfficial voucherOfficial;
    private ReceiveVoucherRecommendPresenter presenter;
    private int page=1;
    private View hander;

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
        hander = View.inflate(getContext(),R.layout.fragment_receive_voucher_recommend_header,null);
        RecyclerView recyclerViewOfficial=hander.findViewById(R.id.recyclerViewOfficial);
        RecyclerView recyclerViewBrand=hander.findViewById(R.id.recyclerViewBrand);
        ImageView imageView=hander.findViewById(R.id.imageView);
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
        voucherGoods.addHeaderView(hander);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewBrand.setLayoutManager(gridLayoutManager);
        voucherBrand.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return voucherBrand.getData().get(position).getSpanSize();
            }
        });
        recyclerViewBrand.setAdapter(voucherBrand);
        presenter.loadBrand("0",String.valueOf(page));
        presenter.loadGoods("0",UserProfileUtil.storeId());
        presenter.loadOfficial();
        presenter.loadImage();
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
        voucherOfficial.setNewData(bean.data.official_coupons);
    }

    @Override
    public void getBrand(VoucherBrandBean brandBean) {
        for (int i=0;i<brandBean.data.coupons.size();i++){
            if (i%3==0){
                list.add(new MultipleItem(brandBean.data.coupons.get(i),MultipleItem.ITEM_TYPE_SPAN1,MultipleItem.ITEM_SPAN1_SIZE));
            }else {
                list.add(new MultipleItem(brandBean.data.coupons.get(i),MultipleItem.ITEM_TYPE_SPAN2,MultipleItem.ITEM_SPAN2_SIZE));
            }
            //list.add(new AdapterReceiveVoucherBrand.MultipleItem(2,brandBean.data.coupons.get(i)));
        }
        voucherBrand.setNewData(list);
    }

    @Override
    public void getGoods(VoucherGoodsBean bean) {
        voucherGoods.setNewData(bean.data.coupons);
    }

    @Override
    public void setPresenter(ReceiveVoucherRecommendContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
