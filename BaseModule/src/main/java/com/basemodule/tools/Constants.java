package com.basemodule.tools;

import org.jetbrains.annotations.Nullable;

/**
 * Created by lilin on 2017/6/16.
 */

public class Constants {

    public static final String AUTHORIZATION = "Authorization";
    public static final String PAGE_SIZE= "10";

    //从获取相册图片
    public static final int REQUEST_CODE_PICK_IMAGE = 0x000011;

    //拍照获取图片
    public static final int REQUEST_CODE_CAPTURE_CAMERA = 0x000010;

    public static final int SUCCESS = 200;


    /**
     * 登录信息
     */
    @Nullable
    public static final String LOGIN_BEAN = "LOGIN_BEAN";

    /**
     * 生活馆等级提示
     */
    @Nullable
    public static final String TIPS_LIFE_HOUSE_GRADE_CLOSE = "TIPS_LIFE_HOUSE_GRADE_CLOSE" ;
}
