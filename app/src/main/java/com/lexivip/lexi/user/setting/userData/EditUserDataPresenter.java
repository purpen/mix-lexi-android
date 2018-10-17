package com.lexivip.lexi.user.setting.userData;

import android.graphics.Bitmap;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.user.login.UserProfileBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class EditUserDataPresenter implements EditUserDataContract.Presenter{
    private EditUserDataModel model=new EditUserDataModel();
    private EditUserDataContract.View view;

    public EditUserDataPresenter(EditUserDataContract.View view) {
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
                view.dismissLoadingView();
                UserProfileBean bean=JsonUtil.fromJson(json,UserProfileBean.class);
                if (bean.success){
                    view.getData(bean);
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
    public void updateData(String username, String avatar_id, String about_me, String gender, String area_id, String province_id, String city_id, String mail, String date) {
        model.updateData(username, avatar_id, about_me, gender, area_id, province_id, city_id, mail, date, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                UserProfileBean bean=JsonUtil.fromJson(json,UserProfileBean.class);
                if (bean.success){
                    ToastUtil.showError("保存成功！");
                    view.getUpdate();
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
