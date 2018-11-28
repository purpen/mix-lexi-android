package com.lexivip.lexi.receiveVoucher;

import android.content.Intent;
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
import com.lexivip.lexi.MainActivity;
import com.lexivip.lexi.PageUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.brandHouse.BrandHouseActivity;
import com.lexivip.lexi.user.login.LoginActivity;
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
    private int officialPosition;
    private int goodsPosition;
    private RecyclerView recyclerViewGoods;
    private View handerGoods;

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
        View handerBrand=View.inflate(getContext(),R.layout.fragment_receive_voucher_recommend_header_brand,null);
        handerGoods = View.inflate(getContext(),R.layout.fragment_receive_voucher_recommend_header_goods,null);
        View handerImage=View.inflate(getContext(),R.layout.fragment_receive_voucher_recommend_header_image,null);
        RecyclerView recyclerViewOfficial=hander.findViewById(R.id.recyclerViewOfficial);
        RecyclerView recyclerViewBrand=handerBrand.findViewById(R.id.recyclerViewBrand);
        ImageView imageView=handerImage.findViewById(R.id.imageView);
        recyclerViewGoods = getView().findViewById(R.id.recyclerViewGoods);
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
        //voucherGoods.addHeaderView(handerImage);
        voucherGoods.addHeaderView(handerBrand);

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
        presenter.loadGoods("0",UserProfileUtil.getUserId(),String.valueOf(page));
        presenter.loadOfficial();
        //presenter.loadImage();
    }

    @Override
    public void installListener() {
        super.installListener();
        voucherGoods.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadGoods("0",UserProfileUtil.getUserId(),String.valueOf(page));
            }
        },recyclerViewGoods);
        voucherOfficial.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                officialPosition = position;
                if (UserProfileUtil.isLogin()) {
                    if (voucherOfficial.getData().get(position).is_grant){
                            if (0!=voucherOfficial.getData().get(position).surplus_count){
                                startActivity(new Intent(getActivity(),MainActivity.class));
                            }
                    }else {
                        presenter.receiveOfficial(voucherOfficial.getData().get(position).code);
                    }
                }else {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            }
        });
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
        voucherBrand.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getActivity(),BrandHouseActivity.class);
                intent.putExtra("rid",voucherBrand.getData().get(position).getBean().store_rid);
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
        }
        voucherBrand.setNewData(list);
    }

    @Override
    public void getGoods(VoucherGoodsBean bean) {
        if(bean.data.coupons.size()!=0){
            voucherGoods.addHeaderView(handerGoods);
        }
        if (page==1) {
            voucherGoods.setNewData(bean.data.coupons);
        }else {
            voucherGoods.addData(bean.data.coupons);
        }
        page++;
    }

    @Override
    public void getReceive(boolean isReceive) {
        voucherGoods.getData().get(goodsPosition).is_grant=isReceive;
        voucherGoods.notifyDataSetChanged();
    }

    @Override
    public void getReceiveOfficial(boolean is_grant) {
        voucherOfficial.getData().get(officialPosition).is_grant=is_grant;
        LogUtil.e("是否："+voucherOfficial.getData().get(officialPosition).is_grant);
        voucherOfficial.notifyDataSetChanged();
    }

    @Override
    public void loadMoreFail() {
        voucherGoods.loadMoreFail();
    }

    @Override
    public void loadMoreEnd() {
        voucherGoods.loadMoreEnd();
    }

    @Override
    public void loadMoreComplete() {
        voucherGoods.loadMoreComplete();
    }

    @Override
    public void setPresenter(ReceiveVoucherRecommendContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
