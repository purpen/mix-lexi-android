package com.lexivip.lexi.brandHouse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;
import com.lexivip.lexi.beans.ProductBean;
import com.lexivip.lexi.index.detail.GoodsDetailActivity;
import com.lexivip.lexi.search.AdapterSearchGoods;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BrandHouseGoodsFragment extends BaseFragment implements BrandHouseGoodsContract.View, View.OnClickListener {
    private LinearLayout linearLayoutSort;
    private TextView textViewSort;
    private ImageView imageViewSortArrow0;
    private LinearLayout linearLayoutFilter;
    private ImageView imageViewSortArrow2;
    private ArrayList<AdapterSearchGoods.MultipleItem> adaperList = new ArrayList<>();
    private AdapterBrandHouseGoods adapterBranHouseGoods;
    private WaitingDialog dialog;
    private BrandHouseGoodsPresenter presenter;
    private String rid;
    private DialogBottomFilter dialogBottomFilter;
    private int count;
    private RecyclerView recyclerView;
    private LinearLayout ll_goods_list;
    private View footer;

    @Override
    protected int getLayout() {
        return R.layout.fragment_brand_goods;
    }

    public static BrandHouseGoodsFragment newInstance(String rid) {
        BrandHouseGoodsFragment fragment = new BrandHouseGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("rid", rid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(getActivity());
        Bundle bundle = getArguments();
        rid = bundle.getString("rid");
        presenter = new BrandHouseGoodsPresenter(this);
        linearLayoutSort = getView().findViewById(R.id.linearLayoutSort);
        textViewSort = getView().findViewById(R.id.textViewSort);
        imageViewSortArrow0 = getView().findViewById(R.id.imageViewSortArrow0);
        linearLayoutFilter = getView().findViewById(R.id.linearLayoutFilter);
        imageViewSortArrow2 = getView().findViewById(R.id.imageViewSortArrow2);
        ll_goods_list = getView().findViewById(R.id.ll_goods_list);
        recyclerView = getView().findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapterBranHouseGoods = new AdapterBrandHouseGoods(adaperList);
        recyclerView.setAdapter(adapterBranHouseGoods);
        footer = View.inflate(getContext(),R.layout.footer_brand_goods,null);
        presenter.loadGoodsData(rid, 0, "", "", "", "", "");
        adapterBranHouseGoods.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return adapterBranHouseGoods.getData().get(position).getSpanSize();
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(AppApplication.getContext()));
        adapterBranHouseGoods.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadGoodsData(rid, 0, "", "", "", "", "");
            }
        }, recyclerView);

        linearLayoutFilter.setOnClickListener(this);
        linearLayoutSort.setOnClickListener(this);

        adapterBranHouseGoods.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getActivity(),GoodsDetailActivity.class);
                intent.putExtra(GoodsDetailActivity.class.getSimpleName(),adapterBranHouseGoods.getData().get(position).getProduct());
                startActivity(intent);
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
        if (dialog != null) {
            dialog.dismiss();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void loadMoreFail() {
        adapterBranHouseGoods.loadMoreFail();
    }

    @Override
    public void loadMoreEnd() {
        adapterBranHouseGoods.loadMoreEnd();
    }

    @Override
    public void loadMoreComplete() {
        adapterBranHouseGoods.loadMoreComplete();
    }

    @Override
    public void setGoodsData(int count) {
        this.count = count;
        if (dialogBottomFilter != null && dialogBottomFilter.isShowing()) {
            dialogBottomFilter.setGoodsCount(count);
        }
    }

    @Override
    public void addData(List<ProductBean> data) {
        adapterBranHouseGoods.addData(formatData(data));
    }

    @Override
    public void setNewData(List<ProductBean> data) {
        //ll_null.setVisibility(View.GONE);
        //recyclerView.setVisibility(View.VISIBLE);
        adapterBranHouseGoods.removeFooterView(footer);
        adapterBranHouseGoods.setNewData(formatData(data));
    }

    @Override
    public void showNull() {
        //ll_null.setVisibility(View.VISIBLE);
        //recyclerView.setVisibility(View.GONE);
        adapterBranHouseGoods.setNewData(null);
        adapterBranHouseGoods.addFooterView(footer);
    }

    @Override
    public void setPresenter(BrandHouseGoodsContract.Presenter presenter) {
        setPresenter(presenter);
    }

    private ArrayList<AdapterSearchGoods.MultipleItem> formatData(List<ProductBean> list) {
        ArrayList<AdapterSearchGoods.MultipleItem> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 4 || i == 9) {
                arrayList.add(new AdapterSearchGoods.MultipleItem(list.get(i), AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN2, AdapterSearchGoods.MultipleItem.ITEM_SPAN2_SIZE));
            } else {
                if ((i < 4 && i % 2 == 1) || (4 < i && i < 9 && i % 2 == 0)) {
                    LogUtil.e("第几个：" + i);
                    list.get(i).isRight = true;
                }
                arrayList.add(new AdapterSearchGoods.MultipleItem(list.get(i), AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN1, AdapterSearchGoods.MultipleItem.ITEM_SPAN1_SIZE));
            }
        }
        return arrayList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayoutFilter:
                Util.startViewRotateAnimation(imageViewSortArrow2, 0f, 180f);
                dialogBottomFilter = new DialogBottomFilter(getActivity(), presenter, rid);
                dialogBottomFilter.show();
                dialogBottomFilter.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Util.startViewRotateAnimation(imageViewSortArrow2, -180f, 0f);
                        switch (presenter.getSortType()) {
                            case "1":
                                textViewSort.setText(Util.getString(R.string.text_sort_synthesize));
                                break;
                            case "2":
                                textViewSort.setText(Util.getString(R.string.text_price_low_up));
                                break;
                            case "3":
                                textViewSort.setText(Util.getString(R.string.text_price_up_low));
                                break;
                        }
                    }
                });
                dialogBottomFilter.setGoodsCount(count);
                break;
            case R.id.linearLayoutSort:
                Util.startViewRotateAnimation(imageViewSortArrow0, 0f, 180f);
                DialogBottomSynthesiseSort dialogBottomSynthesiseSort = new DialogBottomSynthesiseSort(getActivity(), presenter, rid);
                dialogBottomSynthesiseSort.show();
                dialogBottomSynthesiseSort.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Util.startViewRotateAnimation(imageViewSortArrow0, -180f, 0f);
                    }
                });
                break;
        }
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

            int count = adapterBranHouseGoods.getItemCount();
            LogUtil.e("总行数：" + count);
            AdapterSearchGoods.MultipleItem item = adapterBranHouseGoods.getItem(itemPosition);
            if (item == null) {
                divider = new Y_DividerBuilder()
                        .create();
                return divider;
            } else {
                if (item.getProduct().isRight) {
                    LogUtil.e("是否是右边：" + item.getProduct().isRight+itemPosition);
                    divider = new Y_DividerBuilder()
                            .setTopSideLine(true, color, 20f, 0f, 0f)
                            .setLeftSideLine(true, color, 5f, 0f, 0f)
                            .create();
                    return divider;
                } else {
                    LogUtil.e("左边：" + itemPosition);
                    divider = new Y_DividerBuilder()
                            .setTopSideLine(true, color, 20f, 0f, 0f)
                            .setLeftSideLine(true, color, 15f, 0f, 0f)
                            .setRightSideLine(true, color, 15f, 0f, 0f)
                            .create();
                    return divider;
                }
            }
        }
    }
}
