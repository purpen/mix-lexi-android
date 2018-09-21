package com.thn.lexi.order;

import com.thn.lexi.beans.ProductBean;

import java.util.List;

public class FullReductionRequestBean {
    public String rid;
    public List<ProductBean> sku_items;

    public static class SKU{
        public String sku;
        public int quantity;
        public String express_id;
    }
}
