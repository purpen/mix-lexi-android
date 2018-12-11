package com.basemodule.tools;

import android.Manifest;

import org.jetbrains.annotations.Nullable;

import java.security.PublicKey;

/**
 * Created by lilin on 2017/6/16.
 */

public class Constants {

    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_IMAGE="user_image";
    public static final String USER_NAME="user_name";
    public static final String PAGE_SIZE= "10";

    public static final String PAGE_SIZE20= "20";

    public static final String WX_ID="wx456e2f0cb22db269";
    public static final String WX_KEY ="8eddb55d39cbfdb9fee1afa93a495db1";
    public static final String QQ_ID="1106125719";
    public static final String QQ_KEY="Vx7fjdJy7i1As15N";
    public static final String SINA_ID="146542115";
    public static final String SINA_KEY="3d2cff91e7e95529e97aa6a2320c940e";

    public static final String AUTHAPPID="wx60ed17bfd850985d";
    public static final String HOMEPATH="pages/index/index";
    public static final String PRODUCTPATH="pages/product/product";
    public static final String WINDOWPATH="pages/windowDetail/windowDetail";

    public static final String MI_ID="2882303761517900308";
    public static final String MI_KEY="5391790024308";
    public static final String UMENG_ID="5bc5c5f1b465f5c5a000007d";
    public static final String UMENG_PUSH="1325f645c927d2d3b6d627a5c0bb5c2e";

    //从获取相册图片
    public static final int REQUEST_CODE_PICK_IMAGE = 0x000011;

    //拍照获取图片
    public static final int REQUEST_CODE_CAPTURE_CAMERA = 0x000010;

    //保存图片到本地的所需的权限
    public static final int REQUEST_CODE_SAVE_IMAGE = 0x000100;
    //分享所需权限
    public static final int REQUEST_CODE_SHARE=0x000110;
    public static final int REQUEST_CODE_SHARE_GOODS=0x000111;

    //添加新地址
    public static final int REQUEST_CODE_REFRESH_ADDRESS = 0x0000012;
    //分享权限
    public static final String[] PERMISSION_LIST_SHARE = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,
            Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.WRITE_APN_SETTINGS};
    //public static final int REQUEST_CODE_SHARE=0x0001000;


    public static final int SUCCESS = 200;


    /**
     * 生活馆等级提示
     */
    @Nullable
    public static final String TIPS_LIFE_HOUSE_GRADE_CLOSE = "TIPS_LIFE_HOUSE_GRADE_CLOSE" ;

    /**
     * 用户资料
     */
    @Nullable
    public static final String USER_PROFILE = "USER_PROFILE";

    @Nullable
    public static final String SEARCH_HISTORY = "SEARCH_HISTORY";

    public static final long GUIDE_INTERVAL = 1500;
    @Nullable
    public static final String GUIDE_TAG = "GUIDE_TAG";

    /**
     * 橱窗标签历史
     */
    @Nullable
    public static final String ADD_TAG_HISTORY = "ADD_TAG_HISTORY";

}
