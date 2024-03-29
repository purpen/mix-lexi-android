package com.lexivip.lexi.net;

import android.text.TextUtils;
import android.util.Base64;

import com.basemodule.tools.Constants;
import com.lexivip.lexi.address.AddressBean;
import com.lexivip.lexi.beans.ProductBean;
import com.lexivip.lexi.order.CreateOrderBean;
import com.lexivip.lexi.order.FullReductionRequestBean;
import com.lexivip.lexi.orderList.EvaluateBean;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.lexivip.lexi.order.CalculateExpressExpenseRequestBean;
import com.lexivip.lexi.order.SelectExpressRequestBean;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ClientParamsAPI {
    //测试
//    public static final String app_key = "zXIPN0ftRj6dlrKFOZpH";
//    public static final String app_secret = "4d8ebaf52b76603a158b67f525a1b9e5f80677da";
//    正式
    public static final String app_key = "PmVOkj4Un6dfKCqQryMR";
    public static final String app_secret = "e238bf64d77e5be7284686aaacd0232e7248254a";
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

    private static HashMap<String, Object> generateCommonParams() {
        HashMap<String,Object> params = new HashMap<>();
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
    public static HashMap<String, Object> getRegisterParams(String areaCode, String phone, String userPsw, String confirmPassword) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getCheckCodeRequestParams(String areaCode, String phone) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("area_code", areaCode);
        params.put("mobile", phone);
        return params;
    }

    /**
     * 获得运费
     *
     * @return
     */
    public static HashMap<String, Object> getFreightParams() {
        return generateCommonParams();
    }

    /**
     * 获取地址列表
     *
     * @return
     */
    public static HashMap<String, Object> getAddressListParams() {
        return generateCommonParams();
    }

    /**
     * 获取默认地址
     *
     * @return
     */
    public static HashMap<String, Object> getDefaultAddressParams() {
        return generateCommonParams();
    }

    /**
     * 删除收货地址
     *
     * @return
     */
    public static HashMap<String, Object> deleteAddressParams() {
        return generateCommonParams();
    }

    /**
     * 获取省
     */
    public static HashMap<String, Object> getProvinceParams() {
        return generateCommonParams();
    }

    /**
     * 接口仅需默认参数
     *
     * @return
     */
    public static HashMap<String, Object> getDefaultParams() {
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
    public static HashMap<String, Object> getCommitAddressParams(String consigneeName, String phone, String provinceId, String cityId, String countyId, String townId, String addressDetail, String zipCode, boolean checked) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getOrderList(String status, int page) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("status", status);
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 获取订单详情
     */
    public static HashMap<String, Object> getOrderDetailParams() {
        return generateCommonParams();
    }


    /**
     * 获取SKU列表
     *
     * @param rid
     * @return
     */
    public static HashMap<String, Object> getSKUListParams(String rid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid", rid);
        return params;
    }

    /**
     * 添加购物车
     *
     * @return
     */
    public static HashMap<String, Object> getShopCartParams(String rid, int quantity) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> searchHistoryParams(int page) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 搜索结果页
     *
     * @return
     */
    public static HashMap<String, Object> searchResultParam(int page, String qk) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> appKeyAndSecretParams(String storeId) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("store_rid", storeId);
        return params;
    }


    @Nullable
    public static HashMap<String, Object> getTokenParams(@NotNull String phone, @NotNull String password) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("email", phone);
        params.put("password", password);
        return params;
    }

    @Nullable
    public static HashMap<String, Object> getLoginParams(@NotNull String phone, @NotNull String password) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("email", phone);
        params.put("password", password);
        return params;
    }

    /**
     * 获取商品列表/分销商品列表
     * @param cid
     * @param page
     * @param status
     * @param user_record
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getGoodListParams(@NotNull String cid, int page, String status, String user_record) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("cid", cid);
        params.put("page", "" + page);
        params.put("user_record",user_record);
        params.put("per_page", Constants.PAGE_SIZE20);
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
    public static HashMap<String, Object> getUpdatePasswordParams(@NotNull String phone, @NotNull String password, @NotNull String confirmPassword) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getSettingParams(@NotNull String userId) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("userId", userId);
        return params;
    }

    public static HashMap<String, Object> getSimilarParams(int page) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getMessageParams(int page, @NotNull String userId) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getFavoriteGoodsParams(@NotNull String rid) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> verifyCheckCodeParams(@NotNull String areaCode, @NotNull String phone, @NotNull String checkCode) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getAreaCodes(int page, @NotNull String status) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getFocusBrandPavilionParams(@NotNull String rid) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getUnfocusBrandPavilionParams(@NotNull String rid) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getUploadUserInfoParams(@NotNull String avatar_id, @NotNull String name, @NotNull String birth, @NotNull String gender) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getCheckCodeLoginRequestParams(@NotNull String areaCode, @NotNull String phone, @NotNull String checkCode) {
        HashMap<String, Object> params = generateCommonParams();
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
    public static HashMap<String, Object> getHotRecommendParams() {
        HashMap<String, Object> params = generateCommonParams();
        params.put("per_page", "15");
        return params;
    }

    /**
     * 优选
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getGoodSelectionParams() {
        HashMap<String, Object> params = generateCommonParams();
        params.put("per_page", "4");
        return params;
    }

    /**
     * 获取生活馆信息
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getLifeStoreParams() {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid", UserProfileUtil.storeId());
        return params;
    }

    /**
     * 编辑生活馆
     * @param title
     * @param description
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getEditLifeStoreParams(@NotNull String title, @NotNull String description) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid", UserProfileUtil.storeId());
        params.put("name", title);
        params.put("description", description);
        return params;
    }

    /**
     * 删除分销商品
     * @param rid
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getDeleteDistributeGoodsParams(@NotNull String rid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid",rid);
        params.put("sid",UserProfileUtil.storeId());
        return params;
    }

    /**
     * 上传店铺logoid
     * @param logoId
     * @return
     */
    @Nullable
    public static HashMap<String,Object> getUploadLifeHouseLogoIdParams(@NotNull String logoId) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid", UserProfileUtil.storeId());
        params.put("logo_id",logoId);
        return params;
    }


    /**
     * 选品中心热门单品
     * @param page
     * @return
     */
    @Nullable
    public static HashMap<String,Object> getHotGoodsParams(int page) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        params.put("sid", UserProfileUtil.storeId());
        return params;
    }

    /**
     * 选品中心全部商品
     * @param page
     * @param sortType
     * @param profitType
     * @param filterCondition
     * @param minePrice
     * @param maxPrice
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getAllGoodsParams(int page, @NotNull String sortType, @NotNull String profitType, @NotNull String filterCondition,@NotNull String status, @NotNull String minePrice, @NotNull String maxPrice,String cids) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        params.put("sort_type", sortType);
        params.put("profit_type", profitType);
        params.put("status", status);
        params.put("qk", filterCondition);
        params.put("cids", cids);
        params.put("min_price", minePrice);
        params.put("max_price", maxPrice);
        return params;
    }

    /**
     * 获取橱窗数据
     * @param page
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getShowWindowParams(int page) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 喜欢橱窗
     * @param rid
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getFavoriteShowWindowParams(@Nullable String rid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid",rid);
        return params;
    }

    /**
     * 关注用户
     * @param uid
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getFocusUserParams(@NotNull String uid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("uid",uid);
        return params;
    }

    /**
     * 获取橱窗详情
     * @param rid
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getShowWindowDetailParams(@NotNull String rid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid",rid);
        return params;
    }

    /**
     * 提交橱窗评论
     * @param rid
     * @param pid
     * @param content
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getSendCommentParams(@NotNull String rid, @NotNull String pid, @NotNull String content) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid",rid);
        params.put("pid",pid);
        params.put("content",content);
        return params;
    }

    /**
     * 点赞评论
     * @param comment_id
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getPraiseCommentParams(@NotNull String comment_id) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("comment_id",comment_id);
        return params;
    }

    /**
     * 加载更多子评论
     * @param comment_id
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getMoreSubCommentsParams(int page,@NotNull String comment_id) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("pid",comment_id);
        params.put("per_page", "5");
        params.put("page", String.valueOf(page));
        return params;
    }

    /**
     * 获取相关橱窗
     * @param rid
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getRelateShowWindowParams(@NotNull String rid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid",rid);
        return  params;
    }

    /**
     * 猜你喜欢
     * @param rid
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getGuessLikeParams(@NotNull String rid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid",rid);
        return  params;
    }

    /**
     * 获取关注的品牌馆
     * @param page
     * @return
     */
    @NotNull
    public static HashMap<String, Object> getFocusedDesignPavilionParams(int page) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("per_page", Constants.PAGE_SIZE);
        params.put("page", String.valueOf(page));
        return params;
    }


    /**
     * 获取用户生活馆动态列表
     * @param page
     * @return
     */
    @NotNull
    public static HashMap<String, Object> getUserDynamicParams(int page) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("per_page", Constants.PAGE_SIZE);
        params.put("page", String.valueOf(page));
        return params;
    }


//    @Nullable
//    public static HashMap<String, Object> getProductsByStoreId(int page,@NotNull String store_rid) {
//        HashMap<String, Object> params = generateCommonParams();
//        params.put("per_page", Constants.PAGE_SIZE);
//        params.put("page", String.valueOf(page));
//        params.put("sid", store_rid);
//        return params;
//    }

    /**
     * 加载品牌馆信息
     * @param store_rid
     * @return
     */
    @Nullable
    public static HashMap<String, Object> loadBrandPavilionInfoParams(@NotNull String store_rid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid", store_rid);
        return params;
    }

    /**
     * 获取到货时间
     * @param goodsId
     * @param store_rid
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getExpressTimeParams(@NotNull String goodsId, @NotNull String store_rid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("store_rid", store_rid);
        params.put("product_rid", goodsId);
        return params;
    }

    /**
     * 获取相似商品
     * @param goodsId
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getSimilarGoodsParams(@NotNull String goodsId) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid", goodsId);
        return params;
    }

    /**
     * 获取商家优惠券列表
     * @param store_rid
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getCouponsByStoreIdParams(@NotNull String store_rid) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("store_rid", store_rid);
        return params;
    }

    /**
     * 点击领取优惠券
     * @param storeId
     * @param code
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getClickCouponsParams(@NotNull String storeId, @NotNull String code) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("store_rid", storeId);
        params.put("rid", code);
        return params;
    }

    /**
     * 获取商品所有SKU
     * @param id
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getGoodsSKUsParams(@NotNull String id) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid", id);
        return params;
    }

    /**
     * 心愿单参数
     * @param list
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getGoodsIdParams(@NotNull ArrayList<String> list) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rids", list);
        return params;
    }

    /**
     * 商品详情
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getGoodsDetailData() {
        HashMap<String, Object> params = generateCommonParams();
        params.put("user_record","1");
        return params;
    }

    /**
     * 获取喜欢商品的用户 最多7条
     * @param goodsId
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getFavoriteUsers(@NotNull String goodsId,String pageSize) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid",goodsId);
        params.put("per_page",pageSize);
        return params;
    }

    /**
     * 获取喜欢商品的用户
     * @param goodsId
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getFavoriteUsers(@NotNull String goodsId,int page) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid",goodsId);
        params.put("per_page",Constants.PAGE_SIZE);
        params.put("page",String.valueOf(page));
        return params;
    }

    /**
     * 加入购物车
     * @param rid
     * @param quantity
     * @return
     */
    @Nullable
    public static HashMap<String, Object> addShopCartParams(@NotNull String rid, int quantity) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("rid",rid);
        params.put("quantity",String.valueOf(quantity));
        return params;
    }

    /**
     * 购物车心愿单
     * @param page
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getWishOrderParams(int page) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("page",String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }


    /**
     *
     * @param storeList
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getPerOrderFullReductionParams(@NotNull ArrayList<FullReductionRequestBean> storeList) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("items",storeList);
        return params;
    }

    /**
     * 提交订单
     * @param createOrderBean
     * @return
     */
    @NotNull
    public static HashMap<String, Object> getSubmitOrderParams(@NotNull CreateOrderBean createOrderBean) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("address_rid",createOrderBean.address_rid);
        params.put("from_client",createOrderBean.from_client);
        params.put("bonus_code",createOrderBean.officialCouponCode);
        params.put("store_items",createOrderBean.store_items);
        return params;
    }

    /**
     * 根据商品运费模板获取快递列表
     * @param selectExpressRequestBean
     * @return
     */
    @Nullable
    public static HashMap<String,Object> getExpressListByExpressModel(@NotNull SelectExpressRequestBean selectExpressRequestBean) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("address_rid",selectExpressRequestBean.address_rid);
        params.put("fid",selectExpressRequestBean.fid);
        params.put("items",selectExpressRequestBean.items);
        return params;
    }
    /**
     * 获取订单运费列表
     * @return
     * @param requestBean
     */
    @Nullable
    public static HashMap<String, Object> calculateExpressExpenseForEachOrderParams(CalculateExpressExpenseRequestBean requestBean) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("address_rid", requestBean.address_rid);
        params.put("items", requestBean.items);
        return params;
    }
    /**
     * 获取官方优惠券
     * @param price
     * @param list
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getOfficialCouponsParams(double price, ArrayList<String> list) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("amount", String.valueOf(price));
        params.put("sku", list);
        return params;
    }

    /**
     * 更新地址
     * @param bean
     * @param is_overseas
     * @param id_card
     * @param id_card_front
     * @param id_card_back
     * @return
     */
    @NotNull
    public static HashMap<String,Object> getAddressParams(AddressBean.DataBean bean,String rid,boolean is_overseas,String id_card,String id_card_front,String id_card_back){
        HashMap<String,Object> params=generateCommonParams();
        params.put("rid",rid);
        params.put("first_name",bean.getFirst_name());
        params.put("phone",bean.getPhone());
        params.put("mobile",bean.getMobile());
        params.put("country_id",bean.getCountry_id());
        params.put("province_id",bean.getProvince_id());
        params.put("city_id",bean.getCity_id());
        params.put("town_id",bean.getTown_id());
        params.put("area_id",bean.getArea_id());
        params.put("street_address",bean.getStreet_address());
        params.put("street_address_two",bean.getStreet_address_two());
        params.put("zipcode",bean.getZipcode());
        params.put("is_default",bean.isIs_default());
        params.put("is_overseas",is_overseas);//是否保存海关地址
        params.put("id_card",id_card);//身份号
        params.put("id_card_front",id_card_front);//正面身份证照片
        params.put("id_card_back",id_card_back);//反面身份证照片
     return params;
    }

    /**
     * 获取订单的id
     * @param rid
     * @return
     */
    public static HashMap<String,Object> getOrderParams(String rid){
        HashMap<String,Object> params=generateCommonParams();
        params.put("rid",rid);
        return params;
    }

    /**
     * 获取物流信息
     * @param logistic_code
     * @param kdn_company_code
     * @return
     */
    public static HashMap<String,Object> getLogistics(String logistic_code, String kdn_company_code,String order_rid){
        HashMap<String,Object> params=generateCommonParams();
        params.put("logistic_code",logistic_code);
        params.put("kdn_company_code",kdn_company_code);
        params.put("order_rid",order_rid);
        return params;
    }

    /**
     * 更新购物车SKU
     * @param newSKU
     * @param oldSKU
     * @param quantity
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getUpdateReselectSKUParams(@NotNull String newSKU, @NotNull String oldSKU, int quantity) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("new_sku", newSKU);
        params.put("old_sku",oldSKU);
        params.put("new_quantity",String.valueOf(quantity));
        return params;
    }

    /**
     * 获取用户海关信息
     * @param first_name
     * @param mobile
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getUserIdentifyParams(@NotNull String first_name, @NotNull String mobile) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("user_name", first_name);
        params.put("mobile",mobile);
        return params;
    }

    /**
     * 获取品牌馆搜索参数
     * @param page
     * @param searchString
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getSearchBrandPavilionParams(int page, @NotNull String searchString) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        params.put("qk",searchString);
        return params;
    }

    /**
     * 搜索用户
     * @param searchString
     * @param page
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getSearchUsers(@NotNull String searchString, int page) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        params.put("qk",searchString);
        return params;
    }

    /**
     * 获取全部省市区
     * @param country
     * @return
     */
    public static HashMap<String,Object> getCountry(@NotNull String country){
        HashMap<String,Object> params=generateCommonParams();
        params.put("country_id",country);
        return  params;
    }

    /**
     * 通用获取rid
     * @param rid
     * @return
     */
    public static HashMap<String,Object> getRidParams(@NotNull String rid){
        HashMap<String,Object> params=generateCommonParams();
        params.put("rid",rid);
        return  params;
    }

    public static HashMap<String,Object> getStoreRidParams(String rid){
        HashMap<String,Object> params=generateCommonParams();
        params.put("store_rid",rid);
        return  params;
    }

    /**
     * 上传商品评价
     * @param order_rid
     * @param items
     * @return
     */
    public static HashMap<String,Object> getEvaluateParams(String order_rid, ArrayList<EvaluateBean> items){
        HashMap<String,Object> params=generateCommonParams();
        params.put("order_rid",order_rid);
        params.put("items",items);
        return params;
    }

    /**
     * 获取模糊匹配列表
     * @param keyWord
     * @return
     */
    @NotNull
    public static HashMap<String,Object> getFuzzyWordParams(@NotNull String keyWord) {
        HashMap<String,Object> params=generateCommonParams();
        params.put("qk",keyWord);
        return params;
    }

    /**
     * 全部编辑推荐
     * @param page
     * @param sortType
     * @param minePrice
     * @param maxPrice
     * @param cids
     * @param is_free_postage
     * @param is_preferential
     * @param is_custom_made
     * @return
     */
    @NotNull
    public static HashMap<String,Object> getAllEditorRecommendParams(int page, @NotNull String sortType, @NotNull String minePrice, @NotNull String maxPrice, @NotNull String cids, @NotNull String is_free_postage, @NotNull String is_preferential, @NotNull String is_custom_made,@NotNull String sort_newest) {
        HashMap<String,Object> params=generateCommonParams();
        params.put("page",String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        params.put("view_more",String.valueOf(1));
        params.put("min_price", minePrice);
        params.put("max_price", maxPrice);
        params.put("cids", cids);
        params.put("sort_type", sortType);
        params.put("is_free_postage", is_free_postage);
        params.put("is_preferential", is_preferential);
        params.put("is_custom_made", is_custom_made);
        params.put("sort_newest", sort_newest);
        return params;
    }

    /**
     * 分类列表
     * @param page
     * @param sortType
     * @param minePrice
     * @param maxPrice
     * @param cids
     * @param is_free_postage
     * @param is_preferential
     * @param is_custom_made
     * @param sort_newest
     * @param id
     * @return
     */
    @Nullable
    public static HashMap<String,Object> getGoodsClassifyParams(int page, @NotNull String sortType, @NotNull String minePrice, @NotNull String maxPrice, @NotNull String cids, @NotNull String is_free_postage, @NotNull String is_preferential, @NotNull String is_custom_made, @NotNull String sort_newest, @NotNull String id) {
        HashMap<String,Object> params=generateCommonParams();
        params.put("page",String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        params.put("view_more",String.valueOf(1));
        params.put("min_price", minePrice);
        params.put("max_price", maxPrice);
        params.put("cids", cids);
        params.put("sort_type", sortType);
        params.put("is_free_postage", is_free_postage);
        params.put("is_preferential", is_preferential);
        params.put("is_custom_made", is_custom_made);
        params.put("sort_newest", sort_newest);
        params.put("id", id);
        return params;
    }

    /**
     * 生活馆交易记录
     * @param date_range
     * @param rid
     * @param status
     * @param page
     * @return
     */
    public static HashMap<String,Object> getTransactionRecordParams(String date_range, String rid, String status, String page){
        HashMap<String,Object> params=generateCommonParams();
        params.put("date_range",date_range);
        params.put("store_rid",rid);
        params.put("status",status);
        params.put("page",page);
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 生活馆订单列表
     * @param store_rid
     * @param status
     * @param page
     * @return
     */
    public static HashMap<String,Object> getTransactionOrderParams(String store_rid, String status, String page){
        HashMap<String,Object> params=generateCommonParams();
        params.put("store_rid",store_rid);
        params.put("status",status);
        params.put("page",page);
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 对账单列表
     * @param rid
     * @param page
     * @return
     */
    public static HashMap<String,Object> getAccountStatementParams(String rid,String page){
        HashMap<String,Object> params=generateCommonParams();
        params.put("store_rid",rid);
        params.put("page",page);
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 对账单详情
     * @param rid
     * @param record_id
     * @return
     */
    public static HashMap<String,Object> getAccountDetailParams(String rid, String record_id){
        HashMap<String,Object> params=generateCommonParams();
        params.put("store_rid",rid);
        params.put("record_id",record_id);
        return params;
    }

    /**
     *对账单收益详情
     * @param rid
     * @param orderId
     * @return
     */
    public static HashMap<String,Object> getAccountDetailOrderParams(String rid,String orderId){
        HashMap<String,Object> params=generateCommonParams();
        params.put("rid",orderId);
        params.put("store_rid",rid);
        return params;
    }

    /**
     * 商品详情海报
     * @param auth_app_id
     * @param path
     * @param type
     * @param scene
     * @param goodId
     * @return
     */
    @Nullable
    public static HashMap<String,Object> getLoadPosterParams(@NotNull String auth_app_id, @NotNull String path, @NotNull String type, @NotNull String scene, String goodId) {
        HashMap<String,Object> params=generateCommonParams();
        params.put("auth_app_id", auth_app_id);
        params.put("path", path);
        params.put("type", type);
        params.put("rid", goodId);
        params.put("scene", scene);
        return params;
    }

    /**
     * 上架商品
     * @param rid
     * @param content
     * @return
     */
    @Nullable
    public static HashMap<String,Object> getPutAwayGoodsParams(@NotNull String rid, @NotNull String content) {
        HashMap<String,Object> params=generateCommonParams();
        params.put("rid", rid);
        params.put("sid", UserProfileUtil.storeId());
        params.put("stick_text", content);
        return params;
    }

    /**
     * 获取品牌馆商品
     * @param rid
     * @param page
     * @param cid
     * @param min_price
     * @param max_price
     * @param sort_type
     * @param sort_newest
     * @return
     */
    public static HashMap<String,Object> getBrandHouseGoodsParams(String rid, String page, String cid, String min_price, String max_price, String sort_type, String sort_newest){
        HashMap<String,Object> params=generateCommonParams();
        params.put("page", page);
        params.put("sid", rid);
        params.put("cid", cid);
        params.put("status", "1");
        params.put("min_price", min_price);
        params.put("max_price", max_price);
        params.put("sort_type", sort_type);
        params.put("sort_newest", sort_newest);
        return params;
    }

    /**
     *获取sid
     * @param sid
     * @return
     */
    public static HashMap<String,Object> getBrandHouseGoodsClassifyParams(String sid){
        HashMap<String,Object> params=generateCommonParams();
        params.put("sid",sid);
        return params;
    }

    /**
     * 获取品牌馆文章
     * @param rid
     * @param page
     * @return
     */
    public static HashMap<String,Object> getBrandHouseArticelParams(String rid,String page){
        HashMap<String,Object> params=generateCommonParams();
        params.put("page",page);
        params.put("sid",rid);
        return params;
    }

    /**
     * 个人资料修改
     * @param username
     * @param avatar_id
     * @param about_me
     * @param gender
     * @param area_id
     * @param province_id
     * @param city_id
     * @param mail
     * @param date
     * @return
     */
    public static HashMap<String,Object> getUserParams(String username,String avatar_id,String about_me,String gender,
                                                       String area_id,String province_id,String city_id,String mail,String date){
        HashMap<String,Object> params=generateCommonParams();
        params.put("username",username);
        params.put("avatar_id",avatar_id);
        params.put("about_me",about_me);
        params.put("gender",gender);
        params.put("area_id",area_id);
        params.put("province_id",province_id);
        params.put("city_id",city_id);
        params.put("mail",mail);
        params.put("date",date);
        return params;
    }

    /**
     * 橱窗评论
     * @param rid
     * @param page
     * @param sort_type
     * @return
     */
    @Nullable
    public static HashMap<String,Object> getShowWindowCommentParams(@NotNull String rid, int page, @NotNull String sort_type) {
        HashMap<String,Object> params=generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        params.put("rid", rid);
        params.put("sort_type", sort_type);
        return params;
    }

    /**
     * 提交评论
     * @param rid
     * @param replyId
     * @param pid
     * @param content
     * @return
     */
    @Nullable
    public static HashMap<String,Object> getSubmitCommentsParams(@NotNull String rid,@NotNull String pid,@NotNull String replyId,@NotNull String content) {
        HashMap<String,Object> params=generateCommonParams();
        params.put("rid", rid);
        params.put("pid", pid);
        params.put("reply_id", replyId);
        params.put("content", content);
        return params;
    }

    /**
     * 同享券推荐页
     * @param store_category
     * @param page
     * @return
     */
    public static HashMap<String,Object> getVoucherRecommendParams(String store_category, String page){
        HashMap<String,Object> params=generateCommonParams();
        params.put("store_category",store_category);
        params.put("page",page);
        params.put("per_page","50");
        return params;
    }
    public static HashMap<String,Object> getVoucherBrandParams(String store_category){
        HashMap<String,Object> params=generateCommonParams();
        params.put("store_category",store_category);
        params.put("per_page","50");
        return params;
    }
    /**
     * 同享券分类页
     * @param store_category
     * @param page
     * @return
     */
    public static HashMap<String,Object> getVoucherParams(String store_category, String page){
        HashMap<String,Object> params=generateCommonParams();
        params.put("store_category",store_category);
        params.put("page",page);
        return params;
    }

    /**
     * 商品券
     * @param store_category
     * @param open_id
     * @return
     */
    public static HashMap<String,Object> getVoucherGoodsParams(String store_category,String open_id,String page){
        HashMap<String,Object> params=generateCommonParams();
        params.put("store_category",store_category);
        params.put("rid",open_id);
        params.put("page",page);
        return params;
    }

    /**
     * 领取官方优惠
     * @param rid
     * @return
     */
    public static HashMap<String,Object> getReceiveVoucherOfficialParams(String rid){
        HashMap<String,Object> params=generateCommonParams();
        params.put("rid",rid);
        return params;
    }

    /**
     * 优惠券头条动态
     * @return
     */
    public static HashMap<String,Object> getVoucherNoticeParams(){
        HashMap<String,Object> params=generateCommonParams();
        params.put("count","50");
        return params;
    }

    /**
     * 微信支付
     * @param rid
     * @param pay_type
     * @return
     */
    public static HashMap<String,Object> getWXPayParams(String rid, int pay_type){
        HashMap<String,Object> params=generateCommonParams();
        params.put("rid",rid);
        params.put("pay_type",pay_type);
        return params;
    }

    /**
     * 包邮专区
     * @param page
     * @param sortType
     * @param minePrice
     * @param maxPrice
     * @param cids
     * @param sort_newest
     * @return
     */
    @NotNull
    public static HashMap<String, Object> getAllFreePostageParams(int page, @NotNull String sortType, @NotNull String minePrice, @NotNull String maxPrice, @NotNull String cids, @NotNull String sort_newest) {
        HashMap<String,Object> params=generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("sort_type", sortType);
        params.put("min_price", minePrice);
        params.put("max_price", maxPrice);
        params.put("sort_newest", sort_newest);
        params.put("cids", cids);
        return params;
    }

    /**
     * 发布橱窗
     * @param title
     * @param content
     * @param products
     * @param tagList
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getPublishShopWindowParams(@NotNull String title, @NotNull String content, @NotNull ArrayList<ProductBean> products, @NotNull ArrayList<String> tagList) {
        HashMap<String,Object> params=generateCommonParams();
        params.put("title", title);
        params.put("description", content);
        params.put("product_items", products);
        params.put("keywords", tagList);
        return params;
    }

    /**
     * 生成分享海报图
     * @param auth_app_id
     * @param path
     * @param type
     * @param rid
     * @param scene
     * @return
     */
    public static HashMap<String,Object> getShareImage(String auth_app_id,String path,int type,String rid,String scene){
        HashMap<String,Object> params=generateCommonParams();
        params.put("auth_app_id", auth_app_id);
        params.put("path", path);
        params.put("type", type);
        params.put("rid", rid);
        params.put("scene", scene);
        return params;
    }

    /**
     * 分享橱窗海报
     * @param auth_app_id
     * @param path
     * @param rid
     * @param scene
     * @return
     */
    public static HashMap<String,Object> getShareWindow(String auth_app_id,String path,String rid,String scene){
        HashMap<String,Object> params=generateCommonParams();
        params.put("auth_app_id", auth_app_id);
        params.put("path", path);
        params.put("rid", rid);
        params.put("scene", scene);
        return params;
    }

    /**
     * 邀请好友开馆海报
     * @param auth_app_id
     * @param path
     * @param scene
     * @return
     */
    public static HashMap<String,Object> getShareInvitation(String auth_app_id,String path,String scene){
        HashMap<String,Object> params=generateCommonParams();
        params.put("auth_app_id", auth_app_id);
        params.put("path", path);
        params.put("scene", scene);
        return params;
    }
    /**
     * 获取别人个人中心的粉丝或关注
     * @param uid
     * @param page
     * @return
     */
    @Nullable
    public static HashMap<String,Object> getOhterUsers(@NotNull String uid,int page){
        HashMap<String, Object> params = generateCommonParams();
        params.put("uid",uid);
        params.put("per_page",Constants.PAGE_SIZE);
        params.put("page",String.valueOf(page));
        return params;
    }

    /**
     * 获取自己的粉丝或关注
     * @param page
     * @return
     */
    public static HashMap<String,Object> getUserFollow(@NotNull int page){
        HashMap<String, Object> params = generateCommonParams();
        params.put("per_page",Constants.PAGE_SIZE);
        params.put("page",String.valueOf(page));
        return params;
    }

    /**
     * 绑定微信&微信登录
     * @param
     * @return
     */
    public static HashMap<String,Object> getBindWX(@NotNull Map<String,String> map){
        HashMap<String, Object> params = generateCommonParams();
        params.put("openid",map.get("unionid"));
        params.put("nick_name",map.get("name"));
        params.put("avatar_url",map.get("iconurl"));
        params.put("gender",map.get("gender"));
        params.put("country",map.get("country"));
        params.put("province",map.get("province"));
        params.put("city",map.get("city"));
        params.put("app_id",Constants.WX_ID);
        return params;
    }

    /**
     * 微信绑定手机号
     * @return
     */
    public static HashMap<String,Object> getBindPhoneCode(@NotNull String openid,@NotNull String areaCode, @NotNull String phone, @NotNull String checkCode) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("openid",openid);
        params.put("areacode", areaCode);
        params.put("email", phone);
        params.put("verify_code", checkCode);
        return params;
    }

    /**
     * 申请生活馆参数
     * @param name
     * @param job
     * @param countryCode
     * @param phone
     * @param checkCode
     * @return
     */
    @NotNull
    public static HashMap<String,Object> getApplyForLifeHouseParams(@NotNull String name, @NotNull String job, @NotNull String countryCode, @NotNull String phone, @NotNull String checkCode) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("name",name);
        params.put("profession", job);
        params.put("areacode", countryCode);
        params.put("mobile", phone);
        params.put("verify_code", checkCode);
        return params;
    }

    /**
     * 35元分享提现
     * @param cash_type
     * @param open_id
     * @param ali_account
     * @param ali_name
     * @param amount
     * @return
     */
    public static HashMap<String,Object> getInvitationCash(String cash_type,String open_id,String ali_account,String ali_name,int amount){
        HashMap<String, Object> params = generateCommonParams();
        params.put("cash_type",cash_type);
        params.put("open_id", open_id);
        params.put("ali_account", ali_account);
        params.put("ali_name", ali_name);
        params.put("amount", amount);
        return params;
    }

    /**
     * 获取新品速递列表
     * @param page
     * @param sortType
     * @param minePrice
     * @param maxPrice
     * @param cids
     * @param is_free_postage
     * @param is_preferential
     * @param is_custom_made
     * @param sort_newest
     * @return
     */
    @Nullable
    public static HashMap<String, Object> getNewProductExpressParams(int page, @NotNull String sortType, @NotNull String minePrice, @NotNull String maxPrice, @NotNull String cids, @NotNull String is_free_postage, @NotNull String is_preferential, @NotNull String is_custom_made, @NotNull String sort_newest) {
        HashMap<String, Object> params = generateCommonParams();
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        params.put("view_more", "1");
        params.put("cids", cids);
        params.put("sort_type", sortType);
        params.put("min_price", minePrice);
        params.put("max_price", maxPrice);
        params.put("is_free_postage", is_free_postage);
        params.put("is_preferential", is_preferential);
        params.put("is_custom_made", is_custom_made);
        params.put("sort_newest", sort_newest);
        return params;
    }

    public static HashMap<String, Object> getUidParams(){
        HashMap<String, Object> params = generateCommonParams();
        params.put("uid", UserProfileUtil.getUserId());
        return params;
    }

    /**
     * 邀请好友提现
     */
    public static HashMap<String,Object> getFriendCash(String cash_type,String open_id,String ali_account,String ali_name){
        HashMap<String, Object> params = generateCommonParams();
        params.put("cash_type",cash_type);
        params.put("open_id", open_id);
        params.put("ali_account", ali_account);
        params.put("ali_name", ali_name);
        return params;
    }

    /**
     * 提现实名认证
     */
    public static HashMap<String,Object> getAuthentication(String name, String id_card, int id_card_front, int id_card_back){
        HashMap<String, Object> params = generateCommonParams();
        params.put("name",name);
        params.put("id_card", id_card);
        params.put("id_card_front", id_card_front);
        params.put("id_card_back", id_card_back);
        return params;
    }

    /**
     * 分类官方推荐券
     */
    public static HashMap<String,Object> getOffici(String category_id){
        HashMap<String, Object> params = generateCommonParams();
        params.put("category_id",category_id);
        return params;
    }
}
