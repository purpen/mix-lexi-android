package com.thn.lexi.lifeShop;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.DateUtil;
import com.thn.lexi.R;

public class AccountDetailDialog extends Dialog {
    private AccountDetailOrderBean bean;
    private ImageView imageView;
    private Context context;
    private double commission_price;

    public AccountDetailDialog(@NonNull Context context,AccountDetailOrderBean bean,double commission_price) {
        super(context);
        this.bean=bean;
        this.commission_price=commission_price;
        LayoutInflater inflater =  (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.dialog_account_detail,null);
        //initView(view);
        setContentView(view);
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM);
        //WindowManager m = getWindowManager();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;//自适应高度
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;//自适应宽度
        dialogWindow.setAttributes(lp);
        initView(view);
    }

    private void initView(View view) {
        imageView = view.findViewById(R.id.iv_finish);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterAccountDetailDialog adapterAccountDetailDialog = new AdapterAccountDetailDialog(R.layout.adapter_dialog_account_detail, bean.data.items);
        recyclerView.setAdapter(adapterAccountDetailDialog);
        View header = View.inflate(getContext(), R.layout.adapter_account_detail_two, null);
        TextView tv_income = header.findViewById(R.id.tv_income);
        TextView tv_detail = header.findViewById(R.id.tv_detail);
        TextView tv_order_code = header.findViewById(R.id.tv_order_code);
        TextView tv_time = header.findViewById(R.id.tv_time);
        tv_detail.setVisibility(View.GONE);
        tv_income.setText(String.valueOf(commission_price));
        tv_order_code.setText(bean.data.rid);
        tv_time.setText(DateUtil.getDateByTimestamp(bean.data.created_at, "yyyy-MM-dd  HH:mm:ss"));
        adapterAccountDetailDialog.addHeaderView(header);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public AccountDetailDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AccountDetailDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
