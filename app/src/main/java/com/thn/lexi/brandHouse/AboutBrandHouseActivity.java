package com.thn.lexi.brandHouse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.thn.lexi.R;
import com.thn.lexi.view.CustomHeadView;

public class AboutBrandHouseActivity extends BaseActivity implements AboutBrandHouseContract.View{

    private WaitingDialog dialog;
    private TextView tv_user_name;
    private TextView tv_status;
    private ImageView iv_logo;
    private ImageView imageView;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_description;
    private TextView tv_summary;
    private RecyclerView recyclerView;
    private LinearLayout ll_null;
    private BrandHouseBean houseBean;

    @Override
    protected int getLayout() {
        return R.layout.activity_about_brand_house;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent=getIntent();
        houseBean = intent.getParcelableExtra("data");
        dialog = new WaitingDialog(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,Util.getString(R.string.text_design));
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_status = findViewById(R.id.tv_status);
        iv_logo = findViewById(R.id.iv_logo);
        imageView = findViewById(R.id.imageView);
        tv_name = findViewById(R.id.tv_name);
        tv_time = findViewById(R.id.tv_time);
        tv_description = findViewById(R.id.tv_description);
        tv_summary = findViewById(R.id.tv_summary);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ll_null = findViewById(R.id.ll_null);
    }

    @Override
    public void installListener() {
        super.installListener();
        GlideUtil.loadImageAsBitmap(houseBean.data.logo,imageView);
        tv_name.setText(houseBean.data.name);
        tv_time.setText("开馆时间："+DateUtil.getDateByTimestamp(houseBean.data.created_at,DateUtil.PATTERN_DOT));
        tv_description.setText(houseBean.data.delivery_province + "." + houseBean.data.city);
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
        ToastUtil.showError(error);
    }

    @Override
    public void setOwnerData(AboutBrandHouseOwnerBean bean) {
        GlideUtil.loadCircleImageWidthDimen(bean.data.user_avatar,iv_logo,DimenUtil.getDimensionPixelSize(R.dimen.dp60));
        tv_user_name.setText(bean.data.username);
        switch (bean.data.user_identity){
            case 1:
                tv_status.setText("独立设计师");
                break;
            case 2:
                tv_status.setText("艺术家");
                break;
            case 3:
                tv_status.setText("手做人");
                break;
            case 4:
                tv_status.setText("原创设计达人");
                break;
            case 11:
                tv_status.setText("原创商户");
                break;
        }
    }

    @Override
    public void setDetailData(AboutBrandHouseDetailBean bean) {
        tv_summary.setText(bean.data.summary);
        if (bean.data.split_content.isEmpty()){
            ll_null.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            ll_null.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            AdapterBrandHouseAbout adapterBrandHouseAbout=new AdapterBrandHouseAbout(R.layout.adapter_brand_house_about,bean.data.split_content);
            recyclerView.setAdapter(adapterBrandHouseAbout);
        }
    }

    @Override
    public void setPresenter(AboutBrandHouseContract.presenter presenter) {
        setPresenter(presenter);
    }
}
