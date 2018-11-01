package com.lexivip.lexi.receiveVoucher;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.basemodule.ui.BaseFragment;
import com.basemodule.ui.CustomViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.lexivip.lexi.R;
import com.lexivip.lexi.orderList.OrderPagerAdapter;
import com.lexivip.lexi.view.CustomHeadView;

import java.util.ArrayList;
import java.util.List;

/**
 * 领券中心
 */
public class ReceiveVoucherActivity extends BaseActivity implements ReceiveVoucherContract.View{

    private WaitingDialog dialog;
    private ReceiveVoucherPresenter presenter;
    private SlidingTabLayout slidingTabLayout;
    private CustomViewPager viewPager;
    private ArrayList<BaseFragment> fragments;
    private List<String> titles;
    private LinearLayout ll_voucher;

    @Override
    protected int getLayout() {
        return R.layout.activity_receive_voucher;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(this);
        presenter = new ReceiveVoucherPresenter(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_coupon_center);
        slidingTabLayout = findViewById(R.id.slidingTabLayout);
        viewPager = findViewById(R.id.viewPager);
        ll_voucher = findViewById(R.id.ll_voucher);
        presenter.loadClass();
    }

    @Override
    public void installListener() {
        super.installListener();
        ll_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
    public void showError(@NonNull String error) {
        if (dialog!=null)
            dialog.dismiss();
        ToastUtil.showError(error);
    }

    @Override
    public void getCategories(VoucherCategoriesBean bean) {
        fragments=new ArrayList<>();
        titles = new ArrayList<>();
        fragments.add(ReceiveVoucherRecommendFragment.newInstance());
        titles.add("推荐");
        for (int i=0;i<bean.data.categories.size();i++){
            fragments.add(ReceiveVoucherFragment.newInstance(String.valueOf(bean.data.categories.get(i).id)));
            titles.add(bean.data.categories.get(i).name);
            LogUtil.e(bean.data.categories.get(i).name);
        }
        OrderPagerAdapter adapter=new OrderPagerAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(true);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public void getNotice(VoucherNoticeBean bean) {

    }

    @Override
    public void setPresenter(ReceiveVoucherContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
