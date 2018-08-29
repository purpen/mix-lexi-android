package com.thn.lexi.mine;

import com.thn.lexi.index.bean.ProductBean;

import java.util.List;

public class DesignPavilionBean {
    /**
     * area :
     * area_id : 0
     * areacode : null
     * begin_date :
     * bgcover : http://kg.erp.taihuoniao.com
     * bgcover_id : 0
     * browse_number : 0
     * categories : []
     * city :
     * city_id :
     * country : 中国
     * country_id : 1
     * created_at : 1532759838
     * delivery_city :
     * delivery_city_id :
     * delivery_country : 中国
     * delivery_country_id : 1
     * delivery_date :
     * delivery_province :
     * delivery_province_id : 0
     * description : null
     * detail :
     * distribution_type : 0
     * end_date :
     * fans_count : 0
     * followed_status : 1
     * is_closed : false
     * logo : http://kg.erp.taihuoniao.com/static/img/default-logo.png
     * logo_id : 0
     * mobile : null
     * name : 京东
     * pattern : 1
     * phone : null
     * products_count : 0
     * province :
     * province_id : 0
     * rid : 95492837
     * status : 1
     * tag_line : null
     * type : 1
     * store_products_counts : 0
     */

    public String area;
    public int area_id;
    public String begin_date;
    public String bgcover;
    public int bgcover_id;
    public int browse_number;
    public String city;
    public String city_id;
    public String country;
    public String country_id;
    public int created_at;
    public String delivery_city;
    public String delivery_city_id;
    public String delivery_country;
    public int delivery_country_id;
    public String delivery_date;
    public String delivery_province;
    public int delivery_province_id;
    public Object description;
    public String detail;
    public int distribution_type;
    public String end_date;
    public String fans_count;
    public int followed_status;
    public boolean is_closed;
    public String logo;
    public String logo_id;
    public String name;
    public int pattern;
    public String products_count;
    public String province;
    public String province_id;
    public List<ProductBean> products;
    public String rid;
    public int status;
    public int type;
    public String store_products_counts;
    public List<?> categories;
}
