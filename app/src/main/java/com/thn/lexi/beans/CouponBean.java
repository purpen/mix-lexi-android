package com.thn.lexi.beans;

import java.util.List;
import com.thn.lexi.order.StoreItemBean;

public class CouponBean {
    /**
     * amount : 10
     * code : UHAOSIWFUVZ
     * count : 100
     * created_at : 1531742069
     * days : 7
     * min_amount : 99
     * products : []
     * reach_amount : 0
     * status : 1
     * type : 1
     * type_text : 全店通用
     * end_date : 1538582400
     * start_date : 1531670400
     */

    public int amount;
    public String code;
    //coupon所在店铺
    public StoreItemBean storeItemBean;
    public int count;
    public int created_at;
    public int days;
    public int min_amount;
    public int reach_amount;
    public int status;
    public int type;
    public String type_text;
    public long end_date;
    public long start_date;
    public long expired_at;
    public long start_at;
    public List<ProductBean> products;
    public boolean selected;
    public boolean is_expired;
    public boolean is_used;
}
