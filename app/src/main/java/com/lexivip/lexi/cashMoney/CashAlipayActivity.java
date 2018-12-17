package com.lexivip.lexi.cashMoney;

import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

public class CashAlipayActivity extends BaseActivity implements CashAlipayConract.View{

    private CashAlipayPresenter presenter;
    private String amount;
    private int type;

    @Override
    protected int getLayout() {
        return R.layout.activity_cash_alipay;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent=getIntent();
        amount = intent.getStringExtra("amount");
        type = intent.getIntExtra("type",1);
        presenter = new CashAlipayPresenter(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        Button button=findViewById(R.id.button);
        final EditText et_name=findViewById(R.id.et_name);
        final EditText et_account=findViewById(R.id.et_account);
        TextView tv_money=findViewById(R.id.tv_money);
        tv_money.setText(amount+"元");

        SpannableStringBuilder builder=new SpannableStringBuilder(Util.getString(R.string.text_need_one));
        builder.setSpan(new ForegroundColorSpan(Util.getColor(R.color.color_ff6666)),6,11,Spanned.SPAN_COMPOSING);
        SpannableStringBuilder builder1=new SpannableStringBuilder(Util.getString(R.string.text_need_ones));
        builder1.setSpan(new ForegroundColorSpan(Util.getColor(R.color.color_ff6666)),6,10,Spanned.SPAN_COMPOSING);
        TextView textView = findViewById(R.id.textView);
        if (Double.valueOf(amount)==1){
            textView.setText(builder);
        }else {
            textView.setText(builder1);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type==1){
                    presenter.loadCash("2",null,et_account.getText().toString(),et_name.getText().toString(), Integer.valueOf(amount));
                }else {
                    presenter.cashFriend(et_account.getText().toString(),et_name.getText().toString());
                }
            }
        });
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void dismissLoadingView() {

    }

    @Override
    public void showError(String error) {
        ToastUtil.showError(error);
    }

    @Override
    public void setCashResult(CashMoneyBean bean) {
        Intent intent=new Intent(this,CashTimeActivity.class);
        intent.putExtra("data",bean);
        startActivity(intent);
    }

    @Override
    public void setPresenter(CashAlipayConract.Presenter presenter) {
        setPresenter(presenter);
    }
}
