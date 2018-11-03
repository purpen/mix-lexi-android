package com.lexivip.lexi;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;

import com.basemodule.tools.LogUtil;
import com.basemodule.ui.BaseFragment;
import com.basemodule.ui.CustomFragmentPagerAdapter;
import com.basemodule.ui.CustomViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.lexivip.lexi.lifeShop.LifeShopFragment;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.lexivip.lexi.user.setting.SettingActivity;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

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
                UMConfigure.setLogEnabled(true);
                //分享
                String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.WRITE_APN_SETTINGS};
                ActivityCompat.requestPermissions(getActivity(),mPermissionList,123);
                new ShareAction(getActivity()).withText("hello")
                        .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(shareListener)
                        .open();
            }
        });
        imageViewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SettingActivity.class));
            }
        });
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtil.e("成功了");
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            LogUtil.e("失败:"+t.toString());
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            LogUtil.e("取消了");
        }
    };

    public static BaseFragment newInstance(){
        MainFragmentUser mainFragmentUser=new MainFragmentUser();
        return mainFragmentUser;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
