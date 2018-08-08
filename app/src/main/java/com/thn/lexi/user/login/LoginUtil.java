package com.thn.lexi.user.login;

import com.basemodule.tools.JsonUtil;
import com.basemodule.tools.SPUtil;
import com.thn.lexi.Constants;

public class LoginUtil {
    /**
     * 获取店铺Id
     * @return
     */
    public static final String storeId(){
        String read = SPUtil.read(Constants.LOGIN_BEAN);
        LoginBean loginBean = JsonUtil.fromJson(read, LoginBean.class);
        return  loginBean.data.store_rid;
    }

    /**
     * 是否是供应商
     * @return
     */
    public static final boolean isSupplier(){
        String read = SPUtil.read(Constants.LOGIN_BEAN);
        LoginBean loginBean = JsonUtil.fromJson(read, LoginBean.class);
        return  loginBean.data.is_supplier;
    }
}
