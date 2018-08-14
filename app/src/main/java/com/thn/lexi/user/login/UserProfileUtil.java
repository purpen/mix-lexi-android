package com.thn.lexi.user.login;

import com.basemodule.tools.Constants;
import com.basemodule.tools.JsonUtil;
import com.basemodule.tools.SPUtil;

public class UserProfileUtil {
    /**
     * 获取店铺Id
     * @return
     */
    public static final String storeId(){
        String read = SPUtil.read(Constants.USER_PROFILE);
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        return  userProfileBean.data.store_rid;
    }

    /**
     * 判断是否是小B
     * @return
     */
    public static boolean isSmallB() {
        String read = SPUtil.read(Constants.USER_PROFILE);
        UserProfileBean userProfileBean = JsonUtil.fromJson(read, UserProfileBean.class);
        return  userProfileBean.data.is_small_b;
    }
}
