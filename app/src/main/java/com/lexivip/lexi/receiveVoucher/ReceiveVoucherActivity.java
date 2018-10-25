package com.lexivip.lexi.receiveVoucher;

import com.basemodule.ui.BaseActivity;
import com.basemodule.ui.CustomViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

public class ReceiveVoucherActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_receive_voucher;
    }

    @Override
    public void initView() {
        super.initView();
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_coupon_center);
        SlidingTabLayout slidingTabLayout=findViewById(R.id.slidingTabLayout);
        CustomViewPager viewPager=findViewById(R.id.viewPager);
    }
}
