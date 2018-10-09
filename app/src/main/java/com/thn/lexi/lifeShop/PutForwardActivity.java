package com.thn.lexi.lifeShop;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.thn.lexi.R;
import com.thn.lexi.view.CustomHeadView;

import org.w3c.dom.Text;

/**
 * 提现页面
 */
public class PutForwardActivity extends BaseActivity implements View.OnClickListener,PutForwardContract.View {

    private String rid;
    private WaitingDialog dialog;
    private TextView tv_put_money;
    private TextView tv_put;
    private TextView tv_put_record;
    private PutForwardPresenter presenter;
    private Button button;

    @Override
    protected int getLayout() {
        return R.layout.activity_put_forward;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent=getIntent();
        rid = intent.getStringExtra("rid");
        dialog = new WaitingDialog(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,Util.getString(R.string.text_put_money));
        tv_put_money = findViewById(R.id.tv_put_money);
        tv_put = findViewById(R.id.tv_put);
        button = findViewById(R.id.button);
        LinearLayout ll_statements=findViewById(R.id.ll_statements);
        tv_put_record = findViewById(R.id.tv_put_record);
        ll_statements.setOnClickListener(this);
        presenter = new PutForwardPresenter(this);
        presenter.loadData(rid);
        presenter.loadRecentData(rid);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                break;
            case R.id.ll_statements:
                Intent intent=new Intent(this,AccountStatementActivity.class);
                intent.putExtra("rid",rid);
                startActivity(intent);
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
    public void showError(String error) {
        dialog.dismiss();
        ToastUtil.showInfo(error);
    }

    @Override
    public void setData(LifeShopCashBean bean) {
        tv_put_money.setText(bean.data.cash_price);
        tv_put.setText(bean.data.total_cash_price);
        if (0==Double.valueOf(bean.data.cash_price)){
            button.setEnabled(false);
        }else {
            button.setEnabled(true);
        }
    }

    @Override
    public void setCashRecent(PutForwardBean bean) {
        if (0!=bean.data.actual_amount) {
            tv_put_record.setText(Util.getString(R.string.text_put_money_recent)+bean.data.actual_amount);
        }
    }

    @Override
    public void setPresenter(PutForwardContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
