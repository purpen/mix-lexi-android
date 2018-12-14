package com.lexivip.lexi.user.login;

import android.text.TextUtils;

import com.basemodule.tools.Constants;
import com.basemodule.tools.LogUtil;
import com.google.gson.Gson;
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
     * 是否绑定微信
     */
    public static boolean isBindWX(){
        String read = SPUtil.read(Constants.USER_PROFILE);
        if (TextUtils.isEmpty(read)) return false;
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        return  userProfileBean.data.profile.is_bind_wx;
    }

    /**
     * 修改绑定状态
     */
    public static void setBindWX(boolean is_bind_wx){
        String read = SPUtil.read(Constants.USER_PROFILE);
        if (TextUtils.isEmpty(read)) return ;
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        userProfileBean.data.profile.is_bind_wx=is_bind_wx;
        Gson gson=new Gson();
        String json=gson.toJson(userProfileBean);
        LogUtil.e("转换之后的数据："+json);
        SPUtil.write(Constants.USER_PROFILE,json);
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

    /**
     * 返回用户数据
     */
    public static UserProfileBean getUserData(){
        String read = SPUtil.read(Constants.USER_PROFILE);
        if (TextUtils.isEmpty(read)) return null;
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        return userProfileBean;
    }
}
