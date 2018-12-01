package com.lexivip.lexi.user.login;

import android.text.TextUtils;

import com.basemodule.tools.Constants;
import com.lexivip.lexi.JsonUtil;
import com.basemodule.tools.SPUtil;

public class UserProfileUtil {
    /**
     * 获取店铺Id
     * @return
     */
    public static final String storeId(){
        String read = SPUtil.read(Constants.USER_PROFILE);
        if (TextUtils.isEmpty(read)) return "";
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        return  userProfileBean.data.store_rid;
    }

    /**
     * 判断是否是小B
     * @return
     */
    public static boolean isSmallB() {
        String read = SPUtil.read(Constants.USER_PROFILE);
        if (TextUtils.isEmpty(read)) return false;
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        return  userProfileBean.data.is_small_b;
    }

    /**
     * 判断是否是大B
     * @return
     */
    public static boolean isBigB() {
        String read = SPUtil.read(Constants.USER_PROFILE);
        if (TextUtils.isEmpty(read)) return false;
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        return  userProfileBean.data.is_supplier;
    }


    /**
     * 判断用户是否登录
     * @return
     */
    public static boolean isLogin(){
        String read = SPUtil.read(Constants.USER_PROFILE);
        if (TextUtils.isEmpty(read)) return false;
        return true;
    }

    /**
     * 获取userId
     * @return
     */
    public static String getUserId() {
        String read = SPUtil.read(Constants.USER_PROFILE);
        if (TextUtils.isEmpty(read)) return "";
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        return userProfileBean.data.profile.uid;
    }

    /**
     * 返回用户名
     * @return
     */
    public static String getUserName() {
        String read = SPUtil.read(Constants.USER_PROFILE);
        if (TextUtils.isEmpty(read)) return "";
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        return userProfileBean.data.profile.username;
    }
}
