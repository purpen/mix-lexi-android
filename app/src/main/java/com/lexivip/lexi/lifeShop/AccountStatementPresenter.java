package com.lexivip.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AccountStatementPresenter implements AccountStatementContract.Presenter{
    private AccountStatementModel model=new AccountStatementModel();
    private AccountStatementContract.View view;

    public AccountStatementPresenter(AccountStatementContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(String rid, String page) {
        model.loadData(rid, page, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("dui账单："+json);
                view.dismissLoadingView();
                AccountStatementBean bean=JsonUtil.fromJson(json,AccountStatementBean.class);
                if (bean.success){
                    if (bean.data.statements.isEmpty()){
                        view.loadMoreEnd();
                    }else {
                        view.loadMoreComplete();
                        view.setData(bean);
                    }
                }else{
                    view.showError(Util.getString(R.string.text_net_error));
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
