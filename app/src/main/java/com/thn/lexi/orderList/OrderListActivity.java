package com.thn.lexi.orderList;

import android.util.Log;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.ui.BaseActivity;
import com.basemodule.ui.BaseFragment;
import com.basemodule.ui.CustomFragmentPagerAdapter;
import com.basemodule.ui.CustomViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.thn.lexi.R;
import com.thn.lexi.view.CustomHeadView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 我的订单
 */
public class OrderListActivity extends BaseActivity {

    private ArrayList<BaseFragment> fragments=new ArrayList<>();
    @Override
    protected int getLayout() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initView() {
        super.initView();

        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        SlidingTabLayout slidingTabLayout=findViewById(R.id.slidingTabLayout);
        CustomViewPager customViewPager=findViewById(R.id.customViewPager);
        customHeadView.setHeadCenterTxtShow(true,R.string.title_order_list);
        String[] title=getResources().getStringArray(R.array.strings_order_list_titles);
        List<String> listTitle= Arrays.asList(title);

        fragments.add(OrderListFragment.newInstance(0));
        fragments.add(OrderListFragment.newInstance(1));
        fragments.add(OrderListFragment.newInstance(2));
        fragments.add(OrderListFragment.newInstance(3));
        fragments.add(OrderListFragment.newInstance(4));
        CustomFragmentPagerAdapter adapter=new CustomFragmentPagerAdapter(this.getSupportFragmentManager(),fragments,listTitle);
        customViewPager.setAdapter(adapter);
        //customViewPager.setOffscreenPageLimit(fragments.size());
        customViewPager.setPagingEnabled(true);
        slidingTabLayout.setViewPager(customViewPager);
    }
}
