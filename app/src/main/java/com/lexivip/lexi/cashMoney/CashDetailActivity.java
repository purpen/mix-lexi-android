package com.lexivip.lexi.cashMoney;

import android.content.Intent;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

public class CashDetailActivity extends BaseActivity implements CashDetailContract.View{

    private TextView tv_money;
    private TextView tv_state;
    private TextView tv_put_type;
    private TextView tv_put_account;
    private TextView tv_put_time;
    private CashDetailPresenter presenter;
    private WaitingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_cash_detail;
    }

    @Override
    public void initView() {
        super.initView();
        presenter = new CashDetailPresenter(this);
        dialog = new WaitingDialog(this);
        Intent intent=getIntent();
        String rid=intent.getStringExtra("rid");
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_cash_detail);
        tv_money = findViewById(R.id.tv_money);
        tv_state = findViewById(R.id.tv_state);
        tv_put_type = findViewById(R.id.tv_put_type);
        tv_put_account = findViewById(R.id.tv_put_account);
        tv_put_time = findViewById(R.id.tv_put_time);
        presenter.loadData(rid);
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
        if (dialog!=null){
            dialog.dismiss();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void setData(CashDetailBean bean) {
        tv_money.setText(bean.data.actual_amount);
        switch (bean.data.status){//提现状态1成功2失败3审核中
            case 1:
                tv_state.setText(Util.getString(R.string.text_put_success));
                tv_state.setTextColor(Util.getColor(R.color.color_999));
                break;
            case 2:
                tv_state.setText(Util.getString(R.string.text_put_fail));
                tv_state.setTextColor(Util.getColor(R.color.color_ff6666));
                break;
            case 3:
                tv_state.setText(Util.getString(R.string.text_examine));
                tv_state.setTextColor(Util.getColor(R.color.color_fb9013));
                break;
        }
        if (1==bean.data.receive_target){
            tv_put_type.setText(Util.getString(R.string.text_wx));
        }else {
            tv_put_type.setText(Util.getString(R.string.text_alipay));
        }
        tv_put_account.setText(bean.data.user_account);
        tv_put_time.setText(DateUtil.getDateByTimestamp(bean.data.created_at,"yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public void setPresenter(CashDetailContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
