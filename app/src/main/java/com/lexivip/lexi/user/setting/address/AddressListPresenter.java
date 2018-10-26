package com.lexivip.lexi.user.setting.address;

import android.graphics.Bitmap;

import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.order.UserAddressListBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AddressListPresenter implements AddressListContract.Presenter {
    private AddressListModel model=new AddressListModel();
    private AddressListContract.View view;

    public AddressListPresenter(AddressListContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        model.loadData(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                UserAddressListBean bean=JsonUtil.fromJson(json,UserAddressListBean.class);
                if (bean.success){
                    view.dismissLoadingView();
                    view.getData(bean);
                }else {
                    view.dismissLoadingView();
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
