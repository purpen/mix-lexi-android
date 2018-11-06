package com.basemodule.tools;

import org.jetbrains.annotations.Nullable;

/**
 * Created by lilin on 2017/6/16.
 */

public class Constants {

    public static final String AUTHORIZATION = "Authorization";
    public static final String PAGE_SIZE= "10";

    public static final String PAGE_SIZE20= "20";

    public static final String WX_ID="wx456e2f0cb22db269";

    //从获取相册图片
    public static final int REQUEST_CODE_PICK_IMAGE = 0x000011;

    //拍照获取图片
    public static final int REQUEST_CODE_CAPTURE_CAMERA = 0x000010;

    //保存图片到本地的所需的权限
    public static final int REQUEST_CODE_SAVE_IMAGE = 0x000100;

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
}
