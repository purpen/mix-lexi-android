package com.lexivip.lexi.cashMoney;

import android.content.Intent;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

public class CashTimeActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_cash_time;
    }

    @Override
    public void initView() {
        super.initView();
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_put_money);
        Intent intent=getIntent();
        CashMoneyBean bean=intent.getParcelableExtra("data");
        TextView tv_time=findViewById(R.id.tv_time);
        tv_time.setText(DateUtil.getDateByTimestamp(bean.data.created_at,"yyyy-MM-dd HH:mm:ss"));
        TextView tv_money=findViewById(R.id.tv_money);
        tv_money.setText(bean.data.actual_amount);
        TextView tv_type=findViewById(R.id.tv_type);
        if (1==bean.data.receive_target) {
            tv_type.setText(Util.getString(R.string.text_wx));
        }else {
            tv_type.setText(Util.getString(R.string.text_alipay));
        }
        TextView tv_account=findViewById(R.id.tv_account);
        tv_account.setText(bean.data.user_account);
    }
}
