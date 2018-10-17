package com.lexivip.lexi.user.setting.userData;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.user.login.UserProfileBean;
import com.lexivip.lexi.view.CustomHeadView;

/**
 * 编辑个人资料
 */
public class EditUserDataActivity extends BaseActivity implements EditUserDataContract.View{

    private ImageView iv_logo;
    private TextView tv_time;
    private ImageView iv_change_logo;
    private Button button;
    private EditText et_name;
    private EditText et_slogan;
    private EditText et_email;
    private EditText et_position;
    private TextView tv_date;
    private TextView tv_sex;
    private WaitingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_user_data;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,Util.getString(R.string.text_edit_user_data));
        iv_logo = findViewById(R.id.iv_logo);
        tv_time = findViewById(R.id.tv_time);
        iv_change_logo = findViewById(R.id.iv_change_logo);
        button = findViewById(R.id.button);
        et_name = findViewById(R.id.et_name);
        et_slogan = findViewById(R.id.et_slogan);
        et_email = findViewById(R.id.et_email);
        et_position = findViewById(R.id.et_position);
        tv_date = findViewById(R.id.tv_date);
        tv_sex = findViewById(R.id.tv_sex);
    }

    @Override
    public void showLoadingView() {
        dialog.show();
    }

    @Override
    public void dismissLoadingView() {
        dialog.dismiss();
    }

    @Override
    public void showError(String error) {
        dialog.dismiss();
        ToastUtil.showError(error);
    }

    @Override
    public void getData(UserProfileBean bean) {

    }

    @Override
    public void getUpdate() {
        finish();
    }

    @Override
    public void setPresenter(EditUserDataContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
