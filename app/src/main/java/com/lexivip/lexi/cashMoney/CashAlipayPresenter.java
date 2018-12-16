package com.lexivip.lexi.cashMoney;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CashAlipayPresenter implements CashAlipayConract.Presenter{
    private CashAlipayConract.View view;
    private CashAlipayModel model=new CashAlipayModel();

    public CashAlipayPresenter(CashAlipayConract.View view) {
        this.view = view;
    }

    @Override
    public void loadCash(String cash_type, String open_id, String ali_account, String ali_name, int amount) {
        model.loadCash(cash_type, open_id, ali_account, ali_name, amount, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("提现返回："+json);
                CashMoneyBean bean=JsonUtil.fromJson(json,CashMoneyBean.class);
                if (bean.success){
                    view.dismissLoadingView();
                    view.setCashResult(bean);
                }else {
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void cashFriend(String ali_account, String ali_name) {
        model.cashMoney(ali_account, ali_name, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("提现返回："+json);
                CashMoneyBean bean=JsonUtil.fromJson(json,CashMoneyBean.class);
                if (bean.success){
                    view.dismissLoadingView();
                    view.setCashResult(bean);
                }else {
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
