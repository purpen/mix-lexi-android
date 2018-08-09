package com.thn.lexi.net;

import android.text.TextUtils;
import android.util.Base64;

import com.basemodule.tools.Constants;
import com.basemodule.tools.SPUtil;
import com.thn.lexi.user.login.LoginUtil;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ClientParamsAPI {
    public static final String app_key = "zXIPN0ftRj6dlrKFOZpH";
    public static final String app_secret = "4d8ebaf52b76603a158b67f525a1b9e5f80677da";

    /**
     * 获取登录后的authorization
     * @param token
     * @return
     */
    public static String getAuthorization(String token) {
        String str;
        if (TextUtils.isEmpty(token)) return "";
        str = token + ":" + token;
        str = "Basic  " + Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
        return str.trim();
    }

    /**
     * 获取登录前的token
     * @param phone
     * @param password
     * @return
     */
    public static String getAuthorization(String phone,String password) {
        String str;
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) return "";
        str = phone + ":" + password;
        str = "Basic  " + Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
        return str.trim();
    }

    private static HashMap<String, String> generateCommonParams() {
        HashMap<String, String> params = new HashMap<>();
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = generateRandomString();
//        String app_key = SPUtil.read(Constants.APP_KEY);
        params.put("app_key", app_key);
        params.put("timestamp", timeStamp);
        params.put("nonce_str", nonceStr);
        Map treeMap = new TreeMap<String, String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return s.compareTo(t1);
                    }
                }
        );
        treeMap.put("app_key", app_key);
        treeMap.put("timestamp", timeStamp);
        treeMap.put("nonce_str", nonceStr);
        params.put("sign", generateSign(treeMap));
        return params;
    }

    /**
     * nonce_str
     * 获得随机字符串
     *
     * @return
     */
    private static String generateRandomString() {
        String nonce_str = "";
        String randomStr = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
        for (int i = 0; i < 16; i++) {
            nonce_str += randomStr.charAt((int) Math.floor(Math.random() * randomStr.length()));
        }
        return nonce_str;
    }

    private static String generateSign(Map map) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = (String) map.get(key);
            sb.append(key + "=" + value + "&");
        }
        sb.deleteCharAt(sb.lastIndexOf("&"));
//        sb.append(SPUtil.read(Constants.APP_SECRET));
        sb.append(app_secret);
        return new String(Hex.encodeHex(DigestUtils.sha1(sb.toString())));
    }

    /**
     * 获取注册参数
     *
     * @param areaCode
     * @param phone
     * @param userPsw
     * @param confirmPassword
     * @return
     */
    public static HashMap<String, String> getRegisterParams(String areaCode, String phone, String userPsw, String confirmPassword) {
        HashMap<String, String> params = generateCommonParams();
        params.put("areacode", areaCode);
        params.put("email", phone);
        params.put("password", userPsw);
        params.put("affirm_password", confirmPassword);
        return params;
    }

    /**
     * 获取验证码参数
     *
     * @param areaCode
     * @param phone
     * @return
     */
    public static HashMap<String, String> getCheckCodeRequestParams(String areaCode, String phone) {
        HashMap<String, String> params = new HashMap<>();
        params.put("area_code", areaCode);
        params.put("mobile", phone);
        return params;
    }

    /**
     * 获得运费
     *
     * @return
     */
    public static HashMap<String, String> getFreightParams() {
        return generateCommonParams();
    }

    /**
     * 获取地址列表
     *
     * @return
     */
    public static HashMap<String, String> getAddressListParams() {
        return generateCommonParams();
    }

    /**
     * 获取默认地址
     *
     * @return
     */
    public static HashMap<String, String> getDefaultAddressParams() {
        return generateCommonParams();
    }

    /**
     * 删除收货地址
     *
     * @return
     */
    public static HashMap<String, String> deleteAddressParams() {
        return generateCommonParams();
    }

    /**
     * 获取省
     */
    public static HashMap<String, String> getProvinceParams() {
        return generateCommonParams();
    }

    /**
     * 接口仅需默认参数
     *
     * @return
     */
    public static HashMap<String, String> getDefaultParams() {
        return generateCommonParams();
    }

    /**
     * @param consigneeName
     * @param phone
     * @param provinceId
     * @param cityId
     * @param countyId
     * @param townId
     * @param addressDetail
     * @param zipCode
     * @param checked
     * @return
     */
    public static HashMap<String, String> getCommitAddressParams(String consigneeName, String phone, String provinceId, String cityId, String countyId, String townId, String addressDetail, String zipCode, boolean checked) {
        HashMap<String, String> params = generateCommonParams();
        params.put("first_name", consigneeName);
        params.put("mobile", phone);
        params.put("province_id", provinceId);
        params.put("city_id", cityId);
        params.put("town_id", countyId);
        params.put("area_id", townId);
        params.put("street_address", addressDetail);
        params.put("zipcode", zipCode);
        params.put("is_default", String.valueOf(checked));
        return params;
    }

    /**
     * 获取订单列表
     *
     * @param status 订单状态
     * @param page
     * @return
     */
    public static HashMap<String, String> getOrderList(String status, int page) {
        HashMap<String, String> params = generateCommonParams();
        params.put("status", status);
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 获取订单详情
     */
    public static HashMap<String, String> getOrderDetailParams() {
        return generateCommonParams();
    }


    /**
     * 获取SKU列表
     *
     * @param rid
     * @return
     */
    public static HashMap<String, String> getSKUListParams(String rid) {
        HashMap<String, String> params = generateCommonParams();
        params.put("rid", rid);
        return params;
    }

    /**
     * 添加购物车
     *
     * @return
     */
    public static HashMap<String, String> getShopCartParams(String rid, int quantity) {
        HashMap<String, String> params = generateCommonParams();
        params.put("rid", rid);
        params.put("quantity", String.valueOf(quantity));
        return params;
    }


    /**
     * 搜索历史
     *
     * @param page
     * @return
     */
    public static HashMap<String, String> searchHistoryParams(int page) {
        HashMap<String, String> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 搜索结果页
     *
     * @return
     */
    public static HashMap<String, String> searchResultParam(int page, String qk) {
        HashMap<String, String> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        params.put("qk", qk);
        return params;
    }

    /**
     * 商户appkey和appSecret
     *
     * @param storeId
     * @return
     */
    public static HashMap<String, String> appKeyAndSecretParams(String storeId) {
        HashMap<String, String> params = generateCommonParams();
        params.put("store_rid", storeId);
        return params;
    }


    @Nullable
    public static HashMap<String, String> getTokenParams(@NotNull String phone, @NotNull String password) {
        HashMap<String, String> params = generateCommonParams();
        params.put("email", phone);
        params.put("password", password);
        return params;
    }

    @Nullable
    public static HashMap<String, String> getLoginParams(@NotNull String phone, @NotNull String password) {
        HashMap<String, String> params = generateCommonParams();
        params.put("email", phone);
        params.put("password", password);
        return params;
    }

    @Nullable
    public static HashMap<String, String> getGoodListParams(@NotNull String cid, int page, String status) {
        HashMap<String, String> params = generateCommonParams();
        params.put("cid", cid);
        params.put("page", "" + page);
        params.put("per_page", Constants.PAGE_SIZE);
        params.put("status", status);
        return params;
    }

    /**
     * 找回密码
     *
     * @param password
     * @param confirmPassword
     * @return
     */
    @Nullable
    public static HashMap<String, String> getUpdatePasswordParams(@NotNull String phone, @NotNull String password, @NotNull String confirmPassword) {
        HashMap<String, String> params = generateCommonParams();
        params.put("email", phone);
        params.put("password", password);
        params.put("affirm_password", confirmPassword);
        return params;
    }

    /**
     * 设置界面获取用户信息
     *
     * @param userId
     * @return
     */
    @Nullable
    public static HashMap<String, String> getSettingParams(@NotNull String userId) {
        HashMap<String, String> params = generateCommonParams();
        params.put("userId", userId);
        return params;
    }

    public static HashMap<String, String> getSimilarParams(int page) {
        HashMap<String, String> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", String.valueOf(Constants.PAGE_SIZE));
        return params;
    }

    /**
     * 获取消息列表
     *
     * @param page
     * @param userId
     * @return
     */
    @Nullable
    public static HashMap<String, String> getMessageParams(int page, @NotNull String userId) {
        HashMap<String, String> params = generateCommonParams();
        params.put("user_id", userId);
        params.put("page", String.valueOf(page));
        params.put("per_page", String.valueOf(Constants.PAGE_SIZE));
        return params;
    }

    /**
     * 喜欢某商品
     *
     * @param rid
     * @return
     */
    @Nullable
    public static HashMap<String, String> getFavoriteGoodsParams(@NotNull String rid) {
        HashMap<String, String> params = generateCommonParams();
        params.put("rid", rid);
        return params;
    }

    /**
     * 验证动态码
     *
     * @param phone
     * @param checkCode
     * @return
     */
    @Nullable
    public static HashMap<String, String> verifyCheckCodeParams(@NotNull String areaCode, @NotNull String phone, @NotNull String checkCode) {
        HashMap<String, String> params = generateCommonParams();
        params.put("areacode", areaCode);
        params.put("email", phone);
        params.put("verify_code", checkCode);
        return params;
    }


    /**
     * 获取手机号地区编码
     *
     * @param page
     * @param status
     * @return
     */
    @Nullable
    public static HashMap<String, String> getAreaCodes(int page, @NotNull String status) {
        HashMap<String, String> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", String.valueOf(Constants.PAGE_SIZE));
        params.put("status", status);
        return params;
    }

    /**
     * 关注店铺
     *
     * @param rid
     * @return
     */
    @Nullable
    public static HashMap<String, String> getFocusBrandPavilionParams(@NotNull String rid) {
        HashMap<String, String> params = generateCommonParams();
        params.put("rid", rid);
        return params;
    }

    /**
     * 店铺取消关注
     *
     * @param rid
     * @return
     */
    @Nullable
    public static HashMap<String, String> getUnfocusBrandPavilionParams(@NotNull String rid) {
        HashMap<String, String> params = generateCommonParams();
        params.put("rid", rid);
        return params;
    }

    /**
     * 完善用户资料
     *
     * @param avatar_id
     * @param name
     * @param birth
     * @param gender
     * @return
     */
    @Nullable
    public static HashMap<String, String> getUploadUserInfoParams(@NotNull String avatar_id, @NotNull String name, @NotNull String birth, @NotNull String gender) {
        HashMap<String, String> params = generateCommonParams();
        params.put("avatar_id", avatar_id);
        params.put("username", name);
        params.put("date", birth);
        params.put("gender", gender);
        return params;
    }

    /**
     * 使用动态码登录
     *
     * @param areaCode
     * @param phone
     * @param checkCode
     * @return
     */
    @Nullable
    public static HashMap<String, String> getCheckCodeLoginRequestParams(@NotNull String areaCode, @NotNull String phone, @NotNull String checkCode) {
        HashMap<String, String> params = generateCommonParams();
        params.put("areacode", areaCode);
        params.put("email", phone);
        params.put("verify_code", checkCode);
        return params;
    }

    /**
     * 获取人气推荐
     * @return
     */
    @Nullable
    public static HashMap<String, String> getHotRecommendParams() {
        HashMap<String, String> params = generateCommonParams();
        params.put("per_page", "5");
        return params;
    }

    /**
     * 优选
     * @return
     */
    @Nullable
    public static HashMap<String, String> getGoodSelectionParams() {
        HashMap<String, String> params = generateCommonParams();
        params.put("per_page", "4");
        return params;
    }

    /**
     * 获取生活馆信息
     * @return
     */
    @Nullable
    public static HashMap<String, String> getLifeStoreParams() {
        HashMap<String, String> params = generateCommonParams();
        params.put("rid", LoginUtil.storeId());
        return params;
    }

    @Nullable
    public static HashMap<String, String> getEditLifeStoreParams(@NotNull String title, @NotNull String description) {
        HashMap<String, String> params = generateCommonParams();
        params.put("rid", LoginUtil.storeId());
        params.put("name", title);
        params.put("description", description);
        return params;
    }
}
