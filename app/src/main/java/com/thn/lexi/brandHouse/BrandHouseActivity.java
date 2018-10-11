package com.thn.lexi.brandHouse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.thn.lexi.R;

public class BrandHouseActivity extends BaseActivity implements View.OnClickListener,BrandHouseContract.View {

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
    private LinearLayout ll_discount;
    private TextView tv_receive;
    private TextView tv_minus;
    private RelativeLayout rl_notice;
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
    private RecyclerView recyclerViewArticle;
    private LinearLayout ll_goods_list;
    private boolean isFollwed;

    @Override
    protected int getLayout() {
        return R.layout.activity_brand_house;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        rid = intent.getStringExtra("rid");
        dialog = new WaitingDialog(this);
        ImageView head_goback = findViewById(R.id.head_goback);
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
        ll_discount = findViewById(R.id.ll_discount);
        tv_receive = findViewById(R.id.tv_receive);
        tv_minus = findViewById(R.id.tv_minus);
        rl_notice = findViewById(R.id.rl_notice);
        rl_close = findViewById(R.id.rl_close);
        tv_close_time = findViewById(R.id.tv_close_time);
        tv_recovery = findViewById(R.id.tv_recovery);
        tv_notice0 = findViewById(R.id.tv_notice0);
        tv_notice1 = findViewById(R.id.tv_notice1);
        tv_look = findViewById(R.id.tv_look);
        linearLayoutSort = findViewById(R.id.linearLayoutSort);
        textViewSort = findViewById(R.id.textViewSort);
        imageViewSortArrow0 = findViewById(R.id.imageViewSortArrow0);
        linearLayoutFilter = findViewById(R.id.linearLayoutFilter);
        imageViewSortArrow2 = findViewById(R.id.imageViewSortArrow2);
        ll_goods_list = findViewById(R.id.ll_goods_list);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerViewArticle = findViewById(R.id.recyclerViewArticle);

        linearLayoutSort.setOnClickListener(this);
        linearLayoutFilter.setOnClickListener(this);
        tv_design.setOnClickListener(this);
        ll_follow.setOnClickListener(this);
        ll_article.setOnClickListener(this);
        ll_goods.setOnClickListener(this);
        head_goback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                finish();
                break;
            case R.id.ll_goods:

                break;
            case R.id.ll_article:

                break;
            case R.id.ll_follow:

                break;
            case R.id.tv_design:

                break;
            case R.id.linearLayoutFilter:

                break;
            case R.id.linearLayoutSort:

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
        GlideUtil.loadImageWithFading(bean.data.logo,iv_logo);
        tv_goods_num.setText(String.valueOf(bean.data.product_count));
        tv_article_num.setText(String.valueOf(bean.data.life_record_count));
        tv_name.setText(bean.data.name);
        tv_location.setText(bean.data.city+","+bean.data.delivery_province);
        tv_fans.setText(String.valueOf(bean.data.fans_count));
        tv_description.setText(bean.data.tag_line);
        isFollwed = bean.data.is_followed;
        if (isFollwed){
            ll_follow.setBackgroundResource(R.drawable.bg_radius_round_f5f7f9);
            tv_focus.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
            tv_focus.setTextColor(Util.getColor(R.color.color_949ea6));
        }
    }

    @Override
    public void setNoticeData(BrandHouseNoticeBean bean) {
        if (bean.data.is_closed){
            tv_close_time.setText(DateUtil.getDateByTimestamp(bean.data.begin_date,DateUtil.PATTERN_DOT)+"—"+DateUtil.getDateByTimestamp(bean.data.end_date,DateUtil.PATTERN_DOT));
            tv_recovery.setText(DateUtil.getDateByTimestamp(bean.data.delivery_date,DateUtil.PATTERN_DOT));

        }else{
            rl_close.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(BrandHouseContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
