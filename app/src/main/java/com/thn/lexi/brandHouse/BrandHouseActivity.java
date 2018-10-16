package com.thn.lexi.brandHouse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.thn.lexi.AppApplication;
import com.thn.lexi.R;
import com.thn.lexi.beans.CouponBean;
import com.thn.lexi.beans.ProductBean;
import com.thn.lexi.index.detail.CouponBottomDialog;
import com.thn.lexi.index.detail.ShopCouponListBean;
import com.thn.lexi.search.AdapterSearchGoods;
import com.thn.lexi.user.login.LoginActivity;
import com.thn.lexi.user.login.UserProfileUtil;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BrandHouseActivity extends BaseActivity implements View.OnClickListener, BrandHouseContract.View {

    private WaitingDialog dialog;
    private String rid;
    private ImageView imageViewShare;
    private ImageView iv_logo;
    private LinearLayout ll_goods;
    private TextView tv_goods_num;
    private TextView tv_goods;
    private LinearLayout ll_article;
    private TextView tv_article_num;
    private TextView tv_article;
    private TextView tv_name;
    private TextView tv_location;
    private TextView tv_fans;
    private TextView tv_description;
    private TextView tv_design;
    private LinearLayout ll_follow;
    private TextView tv_focus;
    private TextView tv_receive;
    private TextView tv_minus;
    private RelativeLayout rl_close;
    private TextView tv_close_time;
    private TextView tv_recovery;
    private TextView tv_notice0;
    private TextView tv_notice1;
    private TextView tv_look;
    private LinearLayout linearLayoutSort;
    private TextView textViewSort;
    private ImageView imageViewSortArrow0;
    private LinearLayout linearLayoutFilter;
    private ImageView imageViewSortArrow2;
    private LinearLayout ll_goods_list;
    private BrandHousePresenter presenter;
    private List<CouponBean> couponList;
    private int count;
    private DialogBottomFilter dialogBottomFilter;
    private ArrayList<AdapterSearchGoods.MultipleItem> adaperList = new ArrayList<>();
    private AdapterBrandHouseGoods adapterBranHouseGoods;
    private ImageView head_goback;
    private boolean isFollow;
    private boolean isArtcle;
    private AdapterBrandHouseArticle adapterBrandHouseArticle;
    private int articlePage = 1;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewArticle;
    private LinearLayout ll_discount;
    private RelativeLayout rl_notice;
    private BrandHouseBean dataBean;

    @Override
    protected int getLayout() {
        return R.layout.activity_brand_house;
    }

    @Override
    public void initView() {
        super.initView();
        presenter = new BrandHousePresenter(this);
        Intent intent = getIntent();
        rid = intent.getStringExtra("rid");
        dialog = new WaitingDialog(this);
        head_goback = findViewById(R.id.head_goback);
        imageViewShare = findViewById(R.id.imageViewShare);

        iv_logo = findViewById(R.id.iv_logo);
        ll_goods = findViewById(R.id.ll_goods);
        tv_goods_num = findViewById(R.id.tv_goods_num);
        tv_goods = findViewById(R.id.tv_goods);
        ll_article = findViewById(R.id.ll_article);
        tv_article_num = findViewById(R.id.tv_article_num);
        tv_article = findViewById(R.id.tv_article);
        tv_name = findViewById(R.id.tv_name);
        tv_location = findViewById(R.id.tv_location);
        tv_fans = findViewById(R.id.tv_fans);
        tv_description = findViewById(R.id.tv_description);
        tv_design = findViewById(R.id.tv_design);
        ll_follow = findViewById(R.id.ll_follow);
        tv_focus = findViewById(R.id.tv_focus);

        rl_close = findViewById(R.id.rl_close);
        tv_close_time = findViewById(R.id.tv_close_time);
        tv_recovery = findViewById(R.id.tv_recovery);
        tv_notice0 = findViewById(R.id.tv_notice0);
        tv_notice1 = findViewById(R.id.tv_notice1);
        tv_look = findViewById(R.id.tv_look);

        tv_receive = findViewById(R.id.tv_receive);
        tv_minus = findViewById(R.id.tv_minus);

        linearLayoutSort = findViewById(R.id.linearLayoutSort);
        textViewSort = findViewById(R.id.textViewSort);
        imageViewSortArrow0 = findViewById(R.id.imageViewSortArrow0);
        linearLayoutFilter = findViewById(R.id.linearLayoutFilter);
        imageViewSortArrow2 = findViewById(R.id.imageViewSortArrow2);
        ll_goods_list = findViewById(R.id.ll_goods_list);

        ll_discount = findViewById(R.id.ll_discount);
        rl_notice = findViewById(R.id.rl_notice);

        recyclerViewArticle = findViewById(R.id.recyclerViewArticle);
        recyclerViewArticle.setLayoutManager(new GridLayoutManager(this,2));

        recyclerView = findViewById(R.id.recyclerView);
        adapterBrandHouseArticle = new AdapterBrandHouseArticle(R.layout.adapter_brand_article, null);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapterBranHouseGoods = new AdapterBrandHouseGoods(adaperList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterBranHouseGoods);
        recyclerViewArticle.setAdapter(adapterBrandHouseArticle);

    }

    @Override
    public void installListener() {
        super.installListener();
        tv_receive.setOnClickListener(this);
        linearLayoutSort.setOnClickListener(this);
        linearLayoutFilter.setOnClickListener(this);
        tv_design.setOnClickListener(this);
        ll_follow.setOnClickListener(this);
        ll_article.setOnClickListener(this);
        ll_goods.setOnClickListener(this);
        head_goback.setOnClickListener(this);

        presenter.loadData(rid);
        presenter.loadNoticeData(rid);
        presenter.loadCouponsData(rid);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                finish();
                break;
            case R.id.ll_goods:
                if (isArtcle) {
                    isArtcle = false;
                    articlePage = 1;
                    ll_goods_list.setVisibility(View.VISIBLE);
                    presenter.loadGoodsData(rid, 1, "", "", "", "", "");
                    tv_goods_num.setTextColor(Util.getColor(R.color.color_6ed7af));
                    tv_goods.setTextColor(Util.getColor(R.color.color_6ed7af));
                    tv_article_num.setTextColor(Util.getColor(R.color.color_949ea6));
                    tv_article.setTextColor(Util.getColor(R.color.color_949ea6));
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerViewArticle.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_article:
                if (!isArtcle) {
                    isArtcle = true;
                    ll_goods_list.setVisibility(View.GONE);
                    presenter.loadArticle(rid, String.valueOf(articlePage));
                    tv_goods_num.setTextColor(Util.getColor(R.color.color_949ea6));
                    tv_goods.setTextColor(Util.getColor(R.color.color_949ea6));
                    tv_article_num.setTextColor(Util.getColor(R.color.color_6ed7af));
                    tv_article.setTextColor(Util.getColor(R.color.color_6ed7af));
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewArticle.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_follow:
                if (isFollow) {
                    presenter.unFollowStore(rid);
                } else {
                    presenter.followStore(rid);
                }
                break;
            case R.id.tv_design:
                Intent intent=new Intent(this,AboutBrandHouseActivity.class);
                intent.putExtra("data", dataBean);
                startActivity(intent);
                break;
            case R.id.linearLayoutFilter:
                Util.startViewRotateAnimation(imageViewSortArrow2, 0f, 180f);
                dialogBottomFilter = new DialogBottomFilter(this, presenter, rid);
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
                DialogBottomSynthesiseSort dialogBottomSynthesiseSort = new DialogBottomSynthesiseSort(this, presenter, rid);
                dialogBottomSynthesiseSort.show();
                dialogBottomSynthesiseSort.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Util.startViewRotateAnimation(imageViewSortArrow0, -180f, 0f);
                    }
                });
                break;
            case R.id.tv_receive:
                if (UserProfileUtil.isLogin()) {
                    CouponBottomDialog couponBottomDialog = new CouponBottomDialog(this, couponList, presenter, rid);
                    couponBottomDialog.show();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
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
        dialog.dismiss();
        showError(error);
    }

    @Override
    public void setData(BrandHouseBean bean) {
        dataBean = bean;
        GlideUtil.loadImageWithRadius(bean.data.logo, iv_logo, DimenUtil.dp2px(4.0));
        tv_goods_num.setText(String.valueOf(bean.data.product_count));
        tv_article_num.setText(String.valueOf(bean.data.life_record_count));
        tv_name.setText(bean.data.name);
        tv_location.setText(bean.data.delivery_province + "." + bean.data.city);
        tv_fans.setText(String.valueOf(bean.data.fans_count));
        tv_description.setText(bean.data.tag_line);
        isFollow = bean.data.is_followed;
        if (isFollow) {
            ll_follow.setBackgroundResource(R.drawable.bg_radius_round_f5f7f9);
            tv_focus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tv_focus.setTextColor(Util.getColor(R.color.color_949ea6));
            tv_focus.setText(Util.getString(R.string.text_focused));
        }
    }

    @Override
    public void setNoticeData(BrandHouseNoticeBean bean) {
        if (bean.data.is_closed) {
            rl_notice.setVisibility(View.VISIBLE);
            rl_close.setVisibility(View.VISIBLE);
            tv_close_time.setText(DateUtil.getDateByTimestamp(Long.valueOf(bean.data.begin_date), DateUtil.PATTERN_DOT) + "—" + DateUtil.getDateByTimestamp(Long.valueOf(bean.data.end_date), DateUtil.PATTERN_DOT));
            tv_recovery.setText(DateUtil.getDateByTimestamp(Long.valueOf(bean.data.delivery_date), DateUtil.PATTERN_DOT));
            tv_notice0.setText(bean.data.announcement);

        } else {
            rl_close.setVisibility(View.GONE);
            if (bean.data.announcement.isEmpty()) {
                rl_notice.setVisibility(View.GONE);
            }else {
                tv_notice0.setText(bean.data.announcement);
                rl_notice.setVisibility(View.VISIBLE);
            }
        }
        LogUtil.e("数据啊：" + tv_notice0.getText().toString());
    }

    @Override
    public void setCouponsData(ShopCouponListBean bean) {
        if (bean.data.coupons.isEmpty()) {
            ll_discount.setVisibility(View.GONE);
        } else {
            ll_discount.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bean.data.coupons.size(); i++) {
                if (bean.data.coupons.get(i).type == 3)
                    stringBuilder.append("满" + bean.data.coupons.get(i).min_amount + "减" + bean.data.coupons.get(i).amount + "、");
            }
            if (TextUtils.isEmpty(stringBuilder)) {
                tv_minus.setVisibility(View.GONE);
            } else {
                tv_minus.setText(stringBuilder);
            }
        }
        couponList = bean.data.coupons;
    }

    @Override
    public void setGoodsData(int count) {
        this.count = count;
        if (dialogBottomFilter != null && dialogBottomFilter.isShowing()) {
            dialogBottomFilter.setGoodsCount(count);
        }
    }

    @Override
    public void loadMoreFail() {
        if (isArtcle) {
            adapterBrandHouseArticle.loadMoreFail();
        } else {
            adapterBranHouseGoods.loadMoreFail();
        }
    }

    @Override
    public void loadMoreEnd() {
        if (isArtcle) {
            adapterBrandHouseArticle.loadMoreEnd();
        } else {
            adapterBranHouseGoods.loadMoreEnd();
        }
    }

    @Override
    public void loadMoreComplete() {
        if (isArtcle) {
            adapterBrandHouseArticle.loadMoreComplete();
        } else {
            adapterBranHouseGoods.loadMoreComplete();
        }
    }

    @Override
    public void addData(List<ProductBean> data) {
        adapterBranHouseGoods.addData(formatData(data));
    }

    @Override
    public void setNewData(List<ProductBean> data) {
        adapterBranHouseGoods.setNewData(formatData(data));
    }

    @Override
    public void setIsFollow(BrandHouseFollowBean bean) {
        tv_fans.setText(String.valueOf(bean.data.fans_count));
        isFollow = !isFollow;
        if (isFollow) {
            ll_follow.setBackgroundResource(R.drawable.bg_radius_round_f5f7f9);
            tv_focus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tv_focus.setTextColor(Util.getColor(R.color.color_949ea6));
            tv_focus.setText(Util.getString(R.string.text_focused));
        } else {
            ll_follow.setBackgroundResource(R.drawable.switch_noselect);
            tv_focus.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_focus_pavilion, 0, 0, 0);
            tv_focus.setTextColor(Util.getColor(android.R.color.white));
            tv_focus.setText(Util.getString(R.string.text_focus));
        }
    }

    @Override
    public void setArticle(BrandHouseArticelBean bean) {
        if (articlePage == 1) {
            adapterBrandHouseArticle.setNewData(bean.data.life_records);
        } else {
            adapterBrandHouseArticle.addData(bean.data.life_records);
        }
        if (bean.data.count == adapterBrandHouseArticle.getData().size()) {
            loadMoreEnd();
        } else {
            loadMoreComplete();
        }
    }

    @Override
    public void setPresenter(BrandHouseContract.Presenter presenter) {
        setPresenter(presenter);
    }

    private ArrayList<AdapterSearchGoods.MultipleItem> formatData(List<ProductBean> list) {
        ArrayList<AdapterSearchGoods.MultipleItem> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 4 || i == 9) {
                arrayList.add(new AdapterSearchGoods.MultipleItem(list.get(i), AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN2, AdapterSearchGoods.MultipleItem.ITEM_SPAN2_SIZE));
            } else {
                if ((1 < 4 && i % 2 == 1) || (4 < i && i < 8 && i % 2 == 0)) {
                    LogUtil.e("第几个：" + i);
                    list.get(i).isRight = true;
                }
                arrayList.add(new AdapterSearchGoods.MultipleItem(list.get(i), AdapterSearchGoods.MultipleItem.ITEM_TYPE_SPAN1, AdapterSearchGoods.MultipleItem.ITEM_SPAN1_SIZE));
            }
        }
        return arrayList;
    }

    private class DividerItemDecoration extends Y_DividerItemDecoration {
        private int color = Util.getColor(android.R.color.white);
        public DividerItemDecoration(Context context) {
            super(context);
        }

        @Override
        public Y_Divider getDivider(int itemPosition) {
            LogUtil.e("position："+itemPosition);
            Y_Divider divider;

            int count = adapterBranHouseGoods.getItemCount();
            LogUtil.e("总行数："+count);
            if (itemPosition==count-1){
                divider = new Y_DividerBuilder()
                        .create();
                return divider;
            }else {
                AdapterSearchGoods.MultipleItem item = adapterBranHouseGoods.getItem(itemPosition);
                if (item==null){
                    divider = new Y_DividerBuilder()
                            .create();
                    return divider;
                }else {
                    if (item.getProduct().isRight) {
                        LogUtil.e("是否是右边：" + item.getProduct().isRight);
                        LogUtil.e("itemPosition：" + itemPosition);
                        divider = new Y_DividerBuilder()
                                .setBottomSideLine(true, color, 20f, 0f, 0f)
                                .setLeftSideLine(true, color, 5f, 0f, 0f)
                                .create();
                        return divider;
                    } else {
                        divider = new Y_DividerBuilder()
                                .setBottomSideLine(true, color, 20f, 0f, 0f)
                                .setLeftSideLine(true, color, 15f, 0f, 0f)
                                .create();
                        return divider;
                    }
                }
            }
        }
    }
}
