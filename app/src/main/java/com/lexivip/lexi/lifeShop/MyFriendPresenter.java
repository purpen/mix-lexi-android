package com.lexivip.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.tools.AppManager;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MyFriendPresenter implements MyFriendContract.Presenter {
    private MyFriendContract.View view;
    private MyFriendModel myFriendModel=new MyFriendModel();
    private WaitingDialog dialog=new WaitingDialog(AppManager.getAppManager().currentActivity());

    public MyFriendPresenter(MyFriendContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int page) {
        myFriendModel.loadData(page, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                MyFriendBean bean=JsonUtil.fromJson(json,MyFriendBean.class);
                if (bean.success){
                    if (bean.data.friends.isEmpty()){
                        view.loadMoreEnd();
                    }else {
                        view.loadMoreComplete();
                        view.dismissLoadingView();
                        view.setData(bean);
                    }
                    dialog.dismiss();
                }else {
                    dialog.dismiss();
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                dialog.dismiss();
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
