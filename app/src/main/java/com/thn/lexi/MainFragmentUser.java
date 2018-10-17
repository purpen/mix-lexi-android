package com.thn.lexi;

import android.support.annotation.NonNull;
import android.view.View;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseFragment;
import com.basemodule.ui.CustomFragmentPagerAdapter;
import com.basemodule.ui.CustomViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.thn.lexi.lifeShop.LifeShopFragment;
import com.thn.lexi.mine.MineContract;
import com.thn.lexi.mine.MinePresenter;
import com.thn.lexi.mine.UserCenterBean;
import com.thn.lexi.user.login.UserProfileUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 个人中心
 */
public class MainFragmentUser extends BaseFragment {

    private ArrayList<BaseFragment> fragments;
    private List<String> listTitle;
    private CustomViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_user;
    }

    @Override
    public void initView() {
        super.initView();
        slidingTabLayout = getView().findViewById(R.id.slidingTabLayout);
        viewPager = getView().findViewById(R.id.customViewPager);
        fragments = new ArrayList<>();
        String[] titles=getResources().getStringArray(R.array.strings_main_user_titles);
        listTitle = Arrays.asList(titles);
        fragments.add(MainFragment3.Companion.newInstance());
        if (UserProfileUtil.isLogin()){
            if (UserProfileUtil.isSmallB()) {
                fragments.add(LifeShopFragment.newInstance());
            }else{
                slidingTabLayout.setVisibility(View.GONE);
            }
        }else{
            slidingTabLayout.setVisibility(View.GONE);
        }
        CustomFragmentPagerAdapter adapter=new CustomFragmentPagerAdapter(getChildFragmentManager(),fragments,listTitle);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    public static BaseFragment newInstance(){
        MainFragmentUser mainFragmentUser=new MainFragmentUser();
        return mainFragmentUser;
    }
}
