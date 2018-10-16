package com.thn.lexi.user.setting.userData;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.Util;
import com.basemodule.ui.BaseActivity;
import com.thn.lexi.R;
import com.thn.lexi.view.CustomHeadView;

/**
 * 编辑个人资料
 */
public class EditUserDataActivity extends BaseActivity {

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

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_user_data;
    }

    @Override
    public void initView() {
        super.initView();
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
}
