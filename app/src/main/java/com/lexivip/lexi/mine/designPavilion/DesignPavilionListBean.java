package com.lexivip.lexi.mine.designPavilion;

import java.util.List;

public class DesignPavilionListBean {

    /**
     * data : {"count":2,"next":false,"prev":false,"stores":[{"area":"","area_id":0,"areacode":null,"begin_date":"","bgcover":"http://kg.erp.taihuoniao.com","bgcover_id":0,"browse_number":0,"categories":[],"city":"","city_id":"","country":"中国","country_id":1,"created_at":1532759838,"delivery_city":"","delivery_city_id":"","delivery_country":"中国","delivery_country_id":1,"delivery_date":"","delivery_province":"","delivery_province_id":0,"description":null,"detail":"","distribution_type":0,"end_date":"","fans_count":0,"followed_status":1,"is_closed":false,"logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","logo_id":0,"mobile":null,"name":"京东","pattern":1,"phone":null,"products_count":0,"province":"","province_id":0,"rid":"95492837","status":1,"tag_line":null,"type":1,"store_products_counts":0},{"area":"","area_id":0,"areacode":null,"begin_date":"","bgcover":"http://kg.erp.taihuoniao.com","bgcover_id":0,"browse_number":0,"categories":[],"city":"","city_id":"","country":"中国","country_id":1,"created_at":1532760796,"delivery_city":"","delivery_city_id":"","delivery_country":"中国","delivery_country_id":1,"delivery_date":"","delivery_province":"","delivery_province_id":0,"description":null,"detail":"","distribution_type":0,"end_date":"","fans_count":0,"followed_status":1,"is_closed":false,"logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","logo_id":0,"mobile":null,"name":"拼多多","pattern":1,"phone":null,"products_count":0,"province":"","province_id":0,"rid":"93921078","status":1,"tag_line":null,"store_products_counts":0,"type":1}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 2
         * next : false
         * prev : false
         * stores : [{"area":"","area_id":0,"areacode":null,"begin_date":"","bgcover":"http://kg.erp.taihuoniao.com","bgcover_id":0,"browse_number":0,"categories":[],"city":"","city_id":"","country":"中国","country_id":1,"created_at":1532759838,"delivery_city":"","delivery_city_id":"","delivery_country":"中国","delivery_country_id":1,"delivery_date":"","delivery_province":"","delivery_province_id":0,"description":null,"detail":"","distribution_type":0,"end_date":"","fans_count":0,"followed_status":1,"is_closed":false,"logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","logo_id":0,"mobile":null,"name":"京东","pattern":1,"phone":null,"products_count":0,"province":"","province_id":0,"rid":"95492837","status":1,"tag_line":null,"type":1},{"area":"","area_id":0,"areacode":null,"begin_date":"","bgcover":"http://kg.erp.taihuoniao.com","bgcover_id":0,"browse_number":0,"categories":[],"city":"","city_id":"","country":"中国","country_id":1,"created_at":1532760796,"delivery_city":"","delivery_city_id":"","delivery_country":"中国","delivery_country_id":1,"delivery_date":"","delivery_province":"","delivery_province_id":0,"description":null,"detail":"","distribution_type":0,"end_date":"","fans_count":0,"followed_status":1,"is_closed":false,"logo":"http://kg.erp.taihuoniao.com/static/img/default-logo.png","logo_id":0,"mobile":null,"name":"拼多多","pattern":1,"phone":null,"products_count":0,"province":"","province_id":0,"rid":"93921078","status":1,"tag_line":null,"store_products_counts":0,"type":1}]
         */

        public int count;
        public List<DesignPavilionBean> stores;

    }

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
