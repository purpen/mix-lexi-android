package com.lexivip.lexi.orderList;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

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
    private LogisticsPresenter presenter = new LogisticsPresenter(this);
    private String express_name;

    @Override
    protected int getLayout() {
        return R.layout.activity_logistics;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        logistic_code = intent.getStringExtra("logistic_code");
        LogUtil.e("logistic_code：" + logistic_code);
        String kdn_company_code = intent.getStringExtra("kdn_company_code");
        LogUtil.e("kdn_company_code：" + kdn_company_code);
        String order_rid = intent.getStringExtra("order_rid");
        String express_name= intent.getStringExtra("express_name");
        LogUtil.e("order_rid：" + order_rid);
        dialog = new WaitingDialog(this);

        CustomHeadView customHeadView = findViewById(R.id.customHeadView);
        customHeadView.setBackgroundColor(Util.getColor(R.color.color_2d343a));
        customHeadView.setLeftImageButton(R.mipmap.icon_return_white);
        customHeadView.setHeadCenterTxtShow(true, R.string.order_logistics, R.color.color_white);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        recyclerView = findViewById(R.id.recyclerView);
        ll_null = findViewById(R.id.ll_null);
        tv_logistics_code = findViewById(R.id.tv_logistics_code);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_code = findViewById(R.id.tv_code);
        tv_freight_type = findViewById(R.id.tv_freight_type);
        tv_freight_type.setText(express_name);
        tv_state = findViewById(R.id.tv_state);

        presenter.getData(logistic_code, kdn_company_code, order_rid);
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
        switch (bean.getState()) {
            case "2":
                tv_state.setText("运送中");
                break;
            case "3":
                tv_state.setText("已签收");
                break;
            case "4":
                tv_state.setText("问题件");
                break;
            default:
                tv_state.setText("配送中");
                break;
        }
        Collections.reverse(bean.getTraces());
        AdapterLogistics adapterLogistics = new AdapterLogistics(R.layout.item_logistics, bean.getTraces(), bean.getTraces().size(), this);
        recyclerView.setAdapter(adapterLogistics);
    }

    @Override
    public void setPresenter(LogisticsContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
