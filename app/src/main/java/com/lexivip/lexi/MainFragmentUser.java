package com.lexivip.lexi;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;

import com.basemodule.tools.Constants;
import com.basemodule.tools.LogUtil;
import com.basemodule.ui.BaseFragment;
import com.basemodule.ui.CustomFragmentPagerAdapter;
import com.basemodule.ui.CustomViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.lexivip.lexi.lifeShop.LifeShopFragment;
import com.lexivip.lexi.net.WebUrl;
import com.lexivip.lexi.shareUtil.ShareUtil;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.lexivip.lexi.user.setting.SettingActivity;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

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
        ImageView imageViewShare=getView().findViewById(R.id.imageViewShare);
        ImageView imageViewSetting=getView().findViewById(R.id.imageViewSetting);
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

        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //分享

                ActivityCompat.requestPermissions(getActivity(),Constants.PERMISSION_LIST_SHARE,Constants.REQUEST_CODE_SHARE);
                ShareUtil shareUtil=new ShareUtil(getActivity(),WebUrl.USER+UserProfileUtil.getUserId(),"在#乐喜#悄悄收藏了一些原创精品好物快来看看吧"
                        ,"",WebUrl.AUTH_PAGE+UserProfileUtil.getUserId());

            }
        });
        imageViewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SettingActivity.class));
            }
        });
    }

    public static BaseFragment newInstance(){
        MainFragmentUser mainFragmentUser=new MainFragmentUser();
        return mainFragmentUser;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtil.e("外层的fragment");
        for (BaseFragment fragment : fragments)
        {
            if (fragment != null)
            {
                // 这里就会调用我们Fragment中的onRequestPermissionsResult方法
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}
