package com.thn.lexi.orderList;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.thn.lexi.R;
import com.thn.lexi.view.CustomHeadView;

import java.util.Collection;
import java.util.Collections;

public class LogisticsActivity extends BaseActivity implements LogisticsContract.View {

    private ClipboardManager clipboardManager;
    private RecyclerView recyclerView;
    private LinearLayout ll_null;
    private TextView tv_logistics_code;
    private String logistic_code;
    private TextView tv_code;
    private TextView tv_freight_type;
    private TextView tv_state;
    private WaitingDialog dialog;
    private LogisticsPresenter presenter=new LogisticsPresenter(this);

    @Override
    protected int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent=getIntent();
        logistic_code = intent.getStringExtra("logistic_code");
        String kdn_company_code=intent.getStringExtra("kdn_company_code");
        dialog = new WaitingDialog(this);

        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setBackgroundColor(Util.getColor(R.color.color_2d343a));
        customHeadView.setLeftImageButton(R.mipmap.icon_return_white);
        customHeadView.setHeadCenterTxtShow(true,R.string.order_logistics,R.color.color_white);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        recyclerView = findViewById(R.id.recyclerView);
        ll_null = findViewById(R.id.ll_null);
        tv_logistics_code = findViewById(R.id.tv_logistics_code);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_code = findViewById(R.id.tv_code);
        tv_freight_type = findViewById(R.id.tv_freight_type);
        tv_state = findViewById(R.id.tv_state);

        presenter.getData(logistic_code,kdn_company_code);
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
    public void showError(String error) {
        ToastUtil.showInfo(error);
    }

    @Override
    public void shoeNull() {
        ll_null.setVisibility(View.VISIBLE);
        tv_logistics_code.setText(logistic_code);
        recyclerView.setVisibility(View.GONE);
        tv_logistics_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clipboardManager.setText(logistic_code);
                ToastUtil.showInfo("运单号已复制成功");
                return true;
            }
        });
    }

    @Override
    public void showList(LogisticsBean.DataBean bean) {
        ll_null.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tv_code.setText(bean.getLogisticCode());
        tv_freight_type.setText(bean.getShipperCode());
        switch (bean.getState()){
            case "2":
                tv_state.setText("运送中");
                break;
            case "3":
                tv_state.setText("已签收");
                break;
            case "4":
                tv_state.setText("问题件");
                break;
        }
        Collections.reverse(bean.getTraces());
        AdapterLogistics adapterLogistics=new AdapterLogistics(R.layout.item_logistics,bean.getTraces(),bean.getTraces().size(),this);
        recyclerView.setAdapter(adapterLogistics);
    }

    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
