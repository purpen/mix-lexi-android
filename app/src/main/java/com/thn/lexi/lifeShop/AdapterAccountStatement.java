package com.thn.lexi.lifeShop;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.basemodule.tools.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.lexi.CustomLinearLayoutManager;
import com.thn.lexi.R;

import java.util.List;

public class AdapterAccountStatement extends BaseQuickAdapter<AccountStatementBean.DataBean.StatementsBeanX,BaseViewHolder> {
    private Activity activity;
    private String rid;
    private Intent intent;

    public AdapterAccountStatement(int layoutResId, @Nullable List<AccountStatementBean.DataBean.StatementsBeanX> data, Activity activity,String rid) {
        super(layoutResId, data);
        this.activity = activity;
        intent = new Intent(activity,AccountDetailActivity.class);
        this.rid = rid;
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountStatementBean.DataBean.StatementsBeanX item) {
        helper.setText(R.id.tv_month,item.name);
        helper.setText(R.id.tv_put_money,String.valueOf(item.total_amount));
        RecyclerView recyclerView=helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager(activity));
        final AdapterAccountStatementTwo adapters=new AdapterAccountStatementTwo(R.layout.adapter_account_statement_two,item.statements);
        recyclerView.setAdapter(adapters);
        intent.putExtra("rid",rid);
        helper.addOnClickListener(R.id.recyclerView);
        adapters.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtil.e("对账单详情啊啊啊啊 啊");
                intent.putExtra("record_id",adapters.getData().get(position).record_id);
                activity.startActivity(intent);
            }
        });
    }
}
