package com.lexivip.lexi.beans;

import java.util.List;

public class BrandPavilionBean {
    /**
     * bgcover : https://kg.erp.taihuoniao.com/20180716/2634FlH_a8eIHd-bUpHs72zlgnt3I8Rz.jpg
     * categories : [[220,"科技"],[261,"玩具&娱乐"],[376,"好感衣装"]]
     * city : 朝阳区
     * country : 中国
     * created_at : 1531722293
     * delivery_city : 朝阳区
     * delivery_country : 中国
     * delivery_province : 北京
     * fans_count : 0
     * is_followed : false
     * life_record_count : 2
     * logo : https://kg.erp.taihuoniao.com/20180716/2623FopwvnJY8ORXMTqU42vPisc4V0_e.jpg
     * name : 798艺术区
     * product_count : 3
     * province : 北京
     * rid : 94952308
     * tag_line : 您的满意，是对我们服务的最大肯定，我们将用心做的更好！
     * town :
     */

    public String bgcover;
    public String city;
    public String country;
    public int created_at;
    public String delivery_city;
    public String delivery_country;
    public String delivery_province;
    public int fans_count;
    public boolean is_followed;
    public int life_record_count;
    public String logo;
    public String name;
    public int product_count;
    public String province;
    public String rid;
    public String tag_line;
    public String town;
    public List<List<String>> categories;
    public List<ProductBean> products;
}
