package com.lexivip.lexi.index.explore;

import com.lexivip.lexi.beans.ProductBean;

import java.util.List;

public class BrandPavilionListBean {

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {


        public int count;
        public String title;
        public List<StoresBean> stores;

        public static class StoresBean {

            public String area;
            public int area_id;
            public String areacode;
            public String begin_date;
            public String bgcover;
            public int bgcover_id;
            public int browse_number;
            public String city;
            public int city_id;
            public String country;
            public int country_id;
            public int created_at;
            public String delivery_city;
            public int delivery_city_id;
            public String delivery_country;
            public int delivery_country_id;
            public String delivery_date;
            public String delivery_province;
            public int delivery_province_id;
//            public DetailBean detail;
            public int distribution_type;
            public String end_date;
            public int fans_count;
            public boolean is_closed;
            public boolean is_followed;
            public String logo;
            public int logo_id;
            public String mobile;
            public String name;
            public int pattern;
            public String phone;
            public String province;
            public int province_id;
            public String rid;
            public int status;
            public List<ProductBean> products;
            public String store_products_counts;
            public String tag_line;
            public int type;
            public List<List<String>> categories;
            public List<String> products_cover;

            public static class DetailBean {
                /**
                 * content : 只为精致生活
                 * id : 2
                 * split_content : []
                 * store_rid : 95206897
                 * updated_at : 1531806565
                 */

                public String content;
                public int id;
                public String store_rid;
                public String updated_at;
            }
        }
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
