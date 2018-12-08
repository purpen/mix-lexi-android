package com.lexivip.lexi.brandHouse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ScreenUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.basemodule.ui.BaseFragment;
import com.basemodule.ui.CustomViewPager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;
import com.lexivip.lexi.beans.CouponBean;
import com.lexivip.lexi.beans.ProductBean;
import com.lexivip.lexi.index.detail.CouponBottomDialog;
import com.lexivip.lexi.index.detail.ShopCouponListBean;
import com.lexivip.lexi.index.selection.OpenLifeHouseActivity;
import com.lexivip.lexi.lifeShop.MyFragmentPageAdapter;
import com.lexivip.lexi.net.WebUrl;
import com.lexivip.lexi.search.AdapterSearchGoods;
import com.lexivip.lexi.shareUtil.ShareUtil;
import com.lexivip.lexi.user.login.LoginActivity;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌馆页面
 */
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
    private BrandHousePresenter presenter;
    private List<CouponBean> couponList;
    private ImageView head_goback;
    private boolean isFollow;
    private boolean isArtcle;
    private LinearLayout ll_discount;
    private RelativeLayout rl_notice;
    private BrandHouseBean dataBean;
    private TextView head_center_tv;
    private Intent intent;
    private CustomViewPager viewPager;
    private ArrayList<BaseFragment> fragments;
    private int fansCount;
    private TextView tv_qualification;

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
        head_center_tv = findViewById(R.id.head_center_tv);

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
        tv_qualification = findViewById(R.id.tv_qualification);

        rl_close = findViewById(R.id.rl_close);
        tv_close_time = findViewById(R.id.tv_close_time);
        tv_recovery = findViewById(R.id.tv_recovery);
        tv_notice0 = findViewById(R.id.tv_notice0);
        tv_notice1 = findViewById(R.id.tv_notice1);
        tv_look = findViewById(R.id.tv_look);

        tv_receive = findViewById(R.id.tv_receive);
        tv_minus = findViewById(R.id.tv_minus);

        ll_discount = findViewById(R.id.ll_discount);
        rl_notice = findViewById(R.id.rl_notice);

        viewPager = findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
        fragments.add(BrandHouseGoodsFragment.newInstance(rid));
        fragments.add(BrandHouseArticleFragment.newInstance(rid));
        viewPager.setAdapter(new MyFragmentPageAdapter(getSupportFragmentManager(),fragments));
        viewPager.setPagingEnabled(false);

        AppBarLayout appBarLayout=findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (-300>verticalOffset) {
                    head_center_tv.setVisibility(View.VISIBLE);
                    if (dataBean.data.name!=null&&!dataBean.data.name.isEmpty()) {
                        head_center_tv.setText(dataBean.data.name);
                    }
                }else {
                    head_center_tv.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void installListener() {
        super.installListener();
        tv_receive.setOnClickListener(this);
        tv_design.setOnClickListener(this);
        ll_follow.setOnClickListener(this);
        ll_article.setOnClickListener(this);
        ll_goods.setOnClickListener(this);
        head_goback.setOnClickListener(this);
        tv_look.setOnClickListener(this);
        imageViewShare.setOnClickListener(this);
        tv_qualification.setOnClickListener(this);

        presenter.loadData(rid);
        presenter.loadNoticeData(rid);
        presenter.loadCouponsData(rid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_look:
                intent=new Intent(this,CloseHouseActivity.class);
                intent.putExtra("rid",rid);
                startActivity(intent);
                break;
            case R.id.head_goback:
                finish();
                break;
            case R.id.ll_goods:
                if (isArtcle) {
                    isArtcle = false;
                    viewPager.setCurrentItem(0);
                    tv_goods_num.setTextColor(Util.getColor(R.color.color_6ed7af));
                    tv_goods.setTextColor(Util.getColor(R.color.color_6ed7af));
                    tv_article_num.setTextColor(Util.getColor(R.color.color_949ea6));
                    tv_article.setTextColor(Util.getColor(R.color.color_949ea6));
                }
                break;
            case R.id.ll_article:
                if (!isArtcle) {
                    isArtcle = true;
                    viewPager.setCurrentItem(1);
                    tv_goods_num.setTextColor(Util.getColor(R.color.color_949ea6));
                    tv_goods.setTextColor(Util.getColor(R.color.color_949ea6));
                    tv_article_num.setTextColor(Util.getColor(R.color.color_6ed7af));
                    tv_article.setTextColor(Util.getColor(R.color.color_6ed7af));
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
                intent = new Intent(this,AboutBrandHouseActivity.class);
                intent.putExtra("data", dataBean);
                intent.putExtra("rid",rid);
                startActivity(intent);
                break;
            case R.id.tv_receive:
                if (UserProfileUtil.isLogin()) {
                    CouponBottomDialog couponBottomDialog = new CouponBottomDialog(this, couponList, presenter, rid);
                    couponBottomDialog.show();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.imageViewShare:
                ShareUtil shareUtil=new ShareUtil(this,WebUrl.LIFE+rid,dataBean.data.name,dataBean.data.tag_line,WebUrl.AUTH_BRAND+rid,dataBean.data.logo);
                break;
            case R.id.tv_qualification:
                Intent intent1=new Intent(this,OpenLifeHouseActivity.class);
                intent1.putExtra("url",WebUrl.QUALIFICAIONS+rid);
                intent1.putExtra("title",R.string.text_qualification);
                startActivity(intent1);
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
        if (dialog!=null) {
            dialog.dismiss();
        }
        showError(error);
    }

    @Override
    public void setData(BrandHouseBean bean) {
        dataBean = bean;
        GlideUtil.loadImageWithRadius(bean.data.logo, iv_logo, DimenUtil.dp2px(4.0));
        tv_goods_num.setText(String.valueOf(bean.data.product_count));
        tv_article_num.setText(String.valueOf(bean.data.life_record_count));
        tv_name.setText(bean.data.name);
        tv_location.setText(bean.data.delivery_country+"."+bean.data.delivery_province);
        tv_fans.setText(String.valueOf(bean.data.fans_count));
        fansCount = bean.data.fans_count;
        tv_description.setText(bean.data.tag_line);
        isFollow = bean.data.is_followed;
        if (isFollow) {
            ll_follow.setBackgroundResource(R.drawable.bg_radius_round_f5f7f9);
            tv_focus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tv_focus.setTextColor(Util.getColor(R.color.color_949ea6));
            tv_focus.setText(Util.getString(R.string.text_focused));
        }
        if (bean.data.has_qualification){
            tv_qualification.setVisibility(View.VISIBLE);
        }else {
            tv_qualification.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNoticeData(BrandHouseNoticeBean bean) {
        if (bean.data.is_closed) {
            rl_notice.setVisibility(View.VISIBLE);
            rl_close.setVisibility(View.VISIBLE);
            tv_close_time.setText(DateUtil.getDateByTimestamp(Long.valueOf(bean.data.begin_date), DateUtil.PATTERN_DOT) + "—" + DateUtil.getDateByTimestamp(Long.valueOf(bean.data.end_date), DateUtil.PATTERN_DOT));
            if (Long.valueOf(bean.data.delivery_date)!=0) {
                tv_recovery.setText(DateUtil.getDateByTimestamp(Long.valueOf(bean.data.delivery_date), DateUtil.PATTERN_DOT));
            }

            int textWidth=ScreenUtil.getScreenWidth()-DimenUtil.getDimensionPixelSize(R.dimen.dp60);
            int sumWidth = 0;
            int sumText=0;
            for (int index=0;index<bean.data.announcement.length();index++){
                char c=bean.data.announcement.charAt(index);
                float charWidth=getCharWidth(tv_notice0,c);
                sumWidth += charWidth;
                if (sumWidth>textWidth){
                    sumText=index;
                    break;
                }
            }
            tv_notice0.setText(bean.data.announcement);
            String string=bean.data.announcement.substring(sumText);
            tv_notice1.setText(string);
            LogUtil.e("第二句："+tv_notice1.getText().toString());
            tv_notice1.post(new Runnable() {
                @Override
                public void run() {
                    Layout l = tv_notice1.getLayout();
                    if (l != null) {
                        int lines = l.getLineCount();
                        if (lines > 0) {
                            if (l.getEllipsisCount(lines - 1) > 0) {
                                LogUtil.e("显示更多");
                                tv_look.setVisibility(View.VISIBLE);
                            }else {
                                LogUtil.e("么有显示更多");
                                tv_look.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        LogUtil.e("获取不到TextView");
                    }
                }
            });

        } else {
            rl_close.setVisibility(View.GONE);
            if (bean.data.announcement.isEmpty()) {
                rl_notice.setVisibility(View.GONE);
            }else {
                rl_notice.setVisibility(View.VISIBLE);

                int textWidth=ScreenUtil.getScreenWidth()-DimenUtil.getDimensionPixelSize(R.dimen.dp60);
                int sumWidth = 0;
                int sumText=0;
                for (int index=0;index<bean.data.announcement.length();index++){
                    char c=bean.data.announcement.charAt(index);
                    float charWidth=getCharWidth(tv_notice0,c);
                    sumWidth += charWidth;
                    if (sumWidth>textWidth){
                        sumText=index;
                        break;
                    }
                }
                tv_notice0.setText(bean.data.announcement);
                String string=bean.data.announcement.substring(sumText);
                tv_notice1.setText(string);
                LogUtil.e("第二句："+tv_notice1.getText().toString());
                tv_notice1.post(new Runnable() {
                    @Override
                    public void run() {
                        Layout l = tv_notice1.getLayout();
                        if (l != null) {
                            int lines = l.getLineCount();
                            if (lines > 0) {
                                if (l.getEllipsisCount(lines - 1) > 0) {
                                    LogUtil.e("显示更多");
                                    tv_look.setVisibility(View.VISIBLE);
                                }else {
                                    LogUtil.e("么有显示更多");
                                    tv_look.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            LogUtil.e("获取不到TextView");
                        }
                    }
                });
            }
        }

    }

    // 计算每一个字符的宽度
    public float getCharWidth(TextView textView, char c) {
        textView.setText(String.valueOf(c));
        textView.measure(0, 0);
        return textView.getMeasuredWidth();
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
    public void setIsFollow(BrandHouseFollowBean bean) {
        isFollow = !isFollow;
        if (isFollow) {
            tv_fans.setText(String.valueOf(fansCount+1));
            ll_follow.setBackgroundResource(R.drawable.bg_radius_round_f5f7f9);
            tv_focus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tv_focus.setTextColor(Util.getColor(R.color.color_949ea6));
            tv_focus.setText(Util.getString(R.string.text_focused));
        } else {
            tv_fans.setText(fansCount);
            ll_follow.setBackgroundResource(R.drawable.switch_noselect);
            tv_focus.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_focus_pavilion, 0, 0, 0);
            tv_focus.setTextColor(Util.getColor(android.R.color.white));
            tv_focus.setText(Util.getString(R.string.text_focus));
        }
    }

    @Override
    public void setPresenter(BrandHouseContract.Presenter presenter) {
        setPresenter(presenter);
    }

}
