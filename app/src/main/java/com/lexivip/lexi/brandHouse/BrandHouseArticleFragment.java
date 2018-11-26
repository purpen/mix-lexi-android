package com.lexivip.lexi.brandHouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.PageUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.index.discover.ArticleDetailActivity;
//TODO 文章待测试
public class BrandHouseArticleFragment extends BaseFragment implements BrandHouseArticleContract.View{

    private WaitingDialog dialog;
    private AdapterBrandHouseArticle adapterBrandHouseArticle;
    private BrandHouseArticlePresenter presenter;
    private int page=1;
    private LinearLayout ll_null;
    private RecyclerView recyclerView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_brand_article;
    }

    public static BrandHouseArticleFragment newInstance(String rid){
        BrandHouseArticleFragment fragment=new BrandHouseArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("rid", rid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle bundle=getArguments();
        final String rid=bundle.getString("rid");
        dialog = new WaitingDialog(getActivity());
        presenter = new BrandHouseArticlePresenter(this);
        ll_null = getView().findViewById(R.id.ll_null);
        recyclerView = getView().findViewById(R.id.recyclerViewArticle);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapterBrandHouseArticle = new AdapterBrandHouseArticle(R.layout.adapter_brand_article, null);
        recyclerView.setAdapter(adapterBrandHouseArticle);
        presenter.loadArticle(rid,String.valueOf(page));
        adapterBrandHouseArticle.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.loadArticle(rid,String.valueOf(page));
            }
        }, recyclerView);
        adapterBrandHouseArticle.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                /*Intent intent=new Intent(getActivity(),ArticleDetailActivity.class);
                intent.putExtra(ArticleDetailActivity.class.getSimpleName(),adapterBrandHouseArticle.getData().get(position).rid);
                intent.putExtra(ArticleDetailActivity.class.getName(),adapterBrandHouseArticle.getData().get(position).channel_name);
                startActivity(intent);*/
                PageUtil.jump2ArticleDetailActivity(adapterBrandHouseArticle.getData().get(position));
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
    public void loadMoreFail() {
        adapterBrandHouseArticle.loadMoreFail();
    }

    @Override
    public void loadMoreEnd() {
        adapterBrandHouseArticle.loadMoreEnd();
    }

    @Override
    public void loadMoreComplete() {
        adapterBrandHouseArticle.loadMoreComplete();
    }

    @Override
    public void setArticle(BrandHouseArticelBean bean) {
        adapterBrandHouseArticle.setNewData(bean.data.life_records);
        page++;
    }

    @Override
    public void addArticle(BrandHouseArticelBean bean) {
        adapterBrandHouseArticle.addData(bean.data.life_records);
        page++;
    }

    @Override
    public void showNull() {
        recyclerView.setVisibility(View.GONE);
        ll_null.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(BrandHouseArticleContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
