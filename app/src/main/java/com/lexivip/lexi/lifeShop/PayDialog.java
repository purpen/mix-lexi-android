package com.lexivip.lexi.lifeShop;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.basemodule.tools.DateUtil;
import com.contrarywind.timer.MessageHandler;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.lexivip.lexi.R;

import java.util.Date;

//TODO 支付dialog待完成
public class PayDialog extends BottomBaseDialog<PayDialog> {

    private View view;
    private Context context;
    private PayBean bean;
    private CountDownTimer countDownTimer;

    public PayDialog(Context context,PayBean bean) {
        super(context);
        this.context=context;
        this.bean=bean;
    }

    @Override
    public View onCreateView() {
        view = View.inflate(getContext(), R.layout.dialog_order_pay,null);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        final Button button=view.findViewById(R.id.button);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        AdapterPay adapterPay=new AdapterPay(R.layout.adapter_order_pay,bean.data.order_list);
        recyclerView.setAdapter(adapterPay);
        long time=600-(bean.data.current_at-bean.data.created_at);
        countDownTimer = new CountDownTimer(time*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText("付款 "+DateUtil.getDateByTimestamp(millisUntilFinished/1000,"mm:ss"));
            }

            @Override
            public void onFinish() {
                button.setText("付款 00:00");
            }
        };
        //TODO 付款待完成
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        countDownTimer.onFinish();
    }
}
