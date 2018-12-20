package com.lexivip.lexi.cashMoney;

import android.view.View;
import android.widget.Button;

import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

public class AuthenticationSuccessActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.acitivity_authentication_success;
    }

    @Override
    public void initView() {
        super.initView();
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_name_authentication);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
