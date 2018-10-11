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
public class MainFragmentUser extends BaseFragment implements MineContract.View {

    private ArrayList<BaseFragment> fragments;
    private List<String> listTitle;
    private CustomViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private WaitingDialog dialog;
    private MinePresenter presenter;

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
        dialog = new WaitingDialog(getActivity());
        presenter=new MinePresenter(this);
        presenter.loadData();
    }

    public static BaseFragment newInstance(){
        MainFragmentUser mainFragmentUser=new MainFragmentUser();
        return mainFragmentUser;
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
    public void showError(@NonNull @NotNull String string) {
        ToastUtil.showError(string);
    }

    @Override
    public void goPage() {

    }

    @Override
    public void setPresenter(MineContract.Presenter presenter) {
        setPresenter(presenter);
    }

    @Override
    public void setUserData(@NotNull UserCenterBean.DataBean data) {
        fragments.add(MainFragment3.Companion.newInstance(data));
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
}
