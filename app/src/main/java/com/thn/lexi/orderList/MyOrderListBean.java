package com.thn.lexi.orderList;

import java.util.List;

public class MyOrderListBean {
    private DataBean data;
    private StatusBean status;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {

        private int count;
        private String next;
        private Object prev;
        private List<OrdersBean> orders;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public Object getPrev() {
            return prev;
        }

        public void setPrev(Object prev) {
            this.prev = prev;
        }

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public static class OrdersBean {
            /**
             * blessing_utterance : null
             * buyer_address : 北京太火鸟
             * buyer_area :
             * buyer_city : 新乡市
             * buyer_country : 中国
             * buyer_name : 张飞
             * buyer_phone : 13777777777
             * buyer_province : 河南
             * buyer_remark :
             * buyer_tel :
             * buyer_town :
             * buyer_zipcode : 10010
             * coupon_amount : 0.0
             * created_at : 1537262352
             * current_time : 1537327124
             * customer_order_id : null
             * discount_amount : 10.0
             * distributed : false
             * first_discount : 0.0
             * freight : 20.0
             * is_many_express : false
             * items : [{"bgcover":"https://kg.erp.taihuoniao.com/20180831/2914Fmxc2aINCXzpVSBnlySJNwri4bn_.jpg","city":"门头沟","commission_price":323.2,"commission_rate":10,"country":"中国","cover":"https://kg.erp.taihuoniao.com/20180716/0648FgSixpS2fT87IU3kKh7QKmMez5Bq.jpg","cover_id":2355,"deal_price":3232,"delivery_city":"朝阳区","delivery_country":"中国","delivery_country_id":1,"delivery_province":"北京","distribution_type":2,"express":7,"express_at":0,"express_code":"JD","express_name":"京东物流","express_no":null,"fans_count":6,"freight":0,"freight_name":"大陆地区","mode":"黄色 330ML","order_sku_commission_price":0,"order_sku_commission_rate":0,"price":3232,"product_name":"赵高尚777","product_rid":"8564927308","province":"北京","quantity":1,"rid":"8601734985","s_color":"黄色","s_model":"330ML","s_weight":0.2,"sale_price":0,"stock_count":994,"stock_quantity":994,"store_logo":"https://kg.erp.taihuoniao.com/20180726/3621Fg896_2PBRG2DP7T06lFUZqnZX_3.jpg","store_name":"乐喜小馆","store_rid":"91984703","tag_line":"以多种工艺，设计属于你的结婚饰品阿迪经典卡带设计","town":""}]
             * official_order_id : null
             * order_total_commission_price : 0
             * outside_target_id : D18091889347602
             * pay_amount : 3242.0
             * payed_at : 0
             * payment_sn : null
             * reach_minus : 10.0
             * received_at : 0
             * refund_amount : 0.0
             * remark : null
             * rid : D18091889347602
             * ship_mode : 1
             * signed_at : 0
             * status : -1
             * store : {"store_logo":"https://kg.erp.taihuoniao.com/20180726/3621Fg896_2PBRG2DP7T06lFUZqnZX_3.jpg","store_name":"乐喜小馆","store_rid":"91984703"}
             * total_amount : 3232.0
             * total_quantity : 1
             * user_order_status : 6
             * outside_target_id  : D18091793085621
             * total_amount  : 10.0
             * buyer_name  : 张飞
             * official_order_id  : null
             */

            private Object blessing_utterance;
            private String buyer_address;
            private String buyer_area;
            private String buyer_city;
            private String buyer_country;
            private String buyer_name;
            private String buyer_phone;
            private String buyer_province;
            private String buyer_remark;
            private String buyer_tel;
            private String buyer_town;
            private String buyer_zipcode;
            private double coupon_amount;
            private int created_at;
            private int current_time;
            private Object customer_order_id;
            private double discount_amount;
            private boolean distributed;
            private double first_discount;
            private double freight;
            private boolean is_many_express;
            private Object official_order_id;
            private int order_total_commission_price;
            private String outside_target_id;
            private double pay_amount;
            private int payed_at;
            private Object payment_sn;
            private double reach_minus;
            private int received_at;
            private double refund_amount;
            private Object remark;
            private String rid;
            private int ship_mode;
            private int signed_at;
            private int status;
            private StoreBean store;
            private double total_amount;
            private int total_quantity;
            private int user_order_status;
            private List<ItemsBean> items;

            public Object getBlessing_utterance() {
                return blessing_utterance;
            }

            public void setBlessing_utterance(Object blessing_utterance) {
                this.blessing_utterance = blessing_utterance;
            }

            public String getBuyer_address() {
                return buyer_address;
            }

            public void setBuyer_address(String buyer_address) {
                this.buyer_address = buyer_address;
            }

            public String getBuyer_area() {
                return buyer_area;
            }

            public void setBuyer_area(String buyer_area) {
                this.buyer_area = buyer_area;
            }

            public String getBuyer_city() {
                return buyer_city;
            }

            public void setBuyer_city(String buyer_city) {
                this.buyer_city = buyer_city;
            }

            public String getBuyer_country() {
                return buyer_country;
            }

            public void setBuyer_country(String buyer_country) {
                this.buyer_country = buyer_country;
            }

            public String getBuyer_phone() {
                return buyer_phone;
            }

            public void setBuyer_phone(String buyer_phone) {
                this.buyer_phone = buyer_phone;
            }

            public String getBuyer_province() {
                return buyer_province;
            }

            public void setBuyer_province(String buyer_province) {
                this.buyer_province = buyer_province;
            }

            public String getBuyer_remark() {
                return buyer_remark;
            }

            public void setBuyer_remark(String buyer_remark) {
                this.buyer_remark = buyer_remark;
            }

            public String getBuyer_tel() {
                return buyer_tel;
            }

            public void setBuyer_tel(String buyer_tel) {
                this.buyer_tel = buyer_tel;
            }

            public String getBuyer_town() {
                return buyer_town;
            }

            public void setBuyer_town(String buyer_town) {
                this.buyer_town = buyer_town;
            }

            public String getBuyer_zipcode() {
                return buyer_zipcode;
            }

            public void setBuyer_zipcode(String buyer_zipcode) {
                this.buyer_zipcode = buyer_zipcode;
            }

            public double getCoupon_amount() {
                return coupon_amount;
            }

            public void setCoupon_amount(double coupon_amount) {
                this.coupon_amount = coupon_amount;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public int getCurrent_time() {
                return current_time;
            }

            public void setCurrent_time(int current_time) {
                this.current_time = current_time;
            }

            public Object getCustomer_order_id() {
                return customer_order_id;
            }

            public void setCustomer_order_id(Object customer_order_id) {
                this.customer_order_id = customer_order_id;
            }

            public double getDiscount_amount() {
                return discount_amount;
            }

            public void setDiscount_amount(double discount_amount) {
                this.discount_amount = discount_amount;
            }

            public boolean isDistributed() {
                return distributed;
            }

            public void setDistributed(boolean distributed) {
                this.distributed = distributed;
            }

            public double getFirst_discount() {
                return first_discount;
            }

            public void setFirst_discount(double first_discount) {
                this.first_discount = first_discount;
            }

            public double getFreight() {
                return freight;
            }

            public void setFreight(double freight) {
                this.freight = freight;
            }

            public boolean isIs_many_express() {
                return is_many_express;
            }

            public void setIs_many_express(boolean is_many_express) {
                this.is_many_express = is_many_express;
            }

            public int getOrder_total_commission_price() {
                return order_total_commission_price;
            }

            public void setOrder_total_commission_price(int order_total_commission_price) {
                this.order_total_commission_price = order_total_commission_price;
            }

            public double getPay_amount() {
                return pay_amount;
            }

            public void setPay_amount(double pay_amount) {
                this.pay_amount = pay_amount;
            }

            public int getPayed_at() {
                return payed_at;
            }

            public void setPayed_at(int payed_at) {
                this.payed_at = payed_at;
            }

            public Object getPayment_sn() {
                return payment_sn;
            }

            public void setPayment_sn(Object payment_sn) {
                this.payment_sn = payment_sn;
            }

            public double getReach_minus() {
                return reach_minus;
            }

            public void setReach_minus(double reach_minus) {
                this.reach_minus = reach_minus;
            }

            public int getReceived_at() {
                return received_at;
            }

            public void setReceived_at(int received_at) {
                this.received_at = received_at;
            }

            public double getRefund_amount() {
                return refund_amount;
            }

            public void setRefund_amount(double refund_amount) {
                this.refund_amount = refund_amount;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public int getShip_mode() {
                return ship_mode;
            }

            public void setShip_mode(int ship_mode) {
                this.ship_mode = ship_mode;
            }

            public int getSigned_at() {
                return signed_at;
            }

            public void setSigned_at(int signed_at) {
                this.signed_at = signed_at;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public StoreBean getStore() {
                return store;
            }

            public void setStore(StoreBean store) {
                this.store = store;
            }

            public int getTotal_quantity() {
                return total_quantity;
            }

            public void setTotal_quantity(int total_quantity) {
                this.total_quantity = total_quantity;
            }

            public int getUser_order_status() {
                return user_order_status;
            }

            public void setUser_order_status(int user_order_status) {
                this.user_order_status = user_order_status;
            }

            public String getOutside_target_id() {
                return outside_target_id;
            }

            public void setOutside_target_id(String outside_target_id) {
                this.outside_target_id = outside_target_id;
            }

            public double getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(double total_amount) {
                this.total_amount = total_amount;
            }

            public String getBuyer_name() {
                return buyer_name;
            }

            public void setBuyer_name(String buyer_name) {
                this.buyer_name = buyer_name;
            }

            public Object getOfficial_order_id() {
                return official_order_id;
            }

            public void setOfficial_order_id(Object official_order_id) {
                this.official_order_id = official_order_id;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class StoreBean {
                /**
                 * store_logo : https://kg.erp.taihuoniao.com/20180726/3621Fg896_2PBRG2DP7T06lFUZqnZX_3.jpg
                 * store_name : 乐喜小馆
                 * store_rid : 91984703
                 */

                private String store_logo;
                private String store_name;
                private String store_rid;

                public String getStore_logo() {
                    return store_logo;
                }

                public void setStore_logo(String store_logo) {
                    this.store_logo = store_logo;
                }

                public String getStore_name() {
                    return store_name;
                }

                public void setStore_name(String store_name) {
                    this.store_name = store_name;
                }

                public String getStore_rid() {
                    return store_rid;
                }

                public void setStore_rid(String store_rid) {
                    this.store_rid = store_rid;
                }
            }

            public static class ItemsBean {
                /**
                 * bgcover : https://kg.erp.taihuoniao.com/20180831/2914Fmxc2aINCXzpVSBnlySJNwri4bn_.jpg
                 * city : 门头沟
                 * commission_price : 323.2
                 * commission_rate : 10.0
                 * country : 中国
                 * cover : https://kg.erp.taihuoniao.com/20180716/0648FgSixpS2fT87IU3kKh7QKmMez5Bq.jpg
                 * cover_id : 2355
                 * deal_price : 3232.0
                 * delivery_city : 朝阳区
                 * delivery_country : 中国
                 * delivery_country_id : 1
                 * delivery_province : 北京
                 * distribution_type : 2
                 * express : 7
                 * express_at : 0
                 * express_code : JD
                 * express_name : 京东物流
                 * express_no : null
                 * fans_count : 6
                 * freight : 0.0
                 * freight_name : 大陆地区
                 * mode : 黄色 330ML
                 * order_sku_commission_price : 0
                 * order_sku_commission_rate : 0
                 * price : 3232.0
                 * product_name : 赵高尚777
                 * product_rid : 8564927308
                 * province : 北京
                 * quantity : 1
                 * rid : 8601734985
                 * s_color : 黄色
                 * s_model : 330ML
                 * s_weight : 0.2
                 * sale_price : 0.0
                 * stock_count : 994
                 * stock_quantity : 994
                 * store_logo : https://kg.erp.taihuoniao.com/20180726/3621Fg896_2PBRG2DP7T06lFUZqnZX_3.jpg
                 * store_name : 乐喜小馆
                 * store_rid : 91984703
                 * tag_line : 以多种工艺，设计属于你的结婚饰品阿迪经典卡带设计
                 * town :
                 */

                private String bgcover;
                private String city;
                private double commission_price;
                private double commission_rate;
                private String country;
                private String cover;
                private int cover_id;
                private double deal_price;
                private String delivery_city;
                private String delivery_country;
                private int delivery_country_id;
                private String delivery_province;
                private int distribution_type;
                private int express;
                private int express_at;
                private String express_code;
                private String express_name;
                private Object express_no;
                private int fans_count;
                private double freight;
                private String freight_name;
                private String mode;
                private int order_sku_commission_price;
                private int order_sku_commission_rate;
                private double price;
                private String product_name;
                private String product_rid;
                private String province;
                private int quantity;
                private String rid;
                private String s_color;
                private String s_model;
                private double s_weight;
                private double sale_price;
                private int stock_count;
                private int stock_quantity;
                private String store_logo;
                private String store_name;
                private String store_rid;
                private String tag_line;
                private String town;
                public boolean isShow;

                public String getBgcover() {
                    return bgcover;
                }

                public void setBgcover(String bgcover) {
                    this.bgcover = bgcover;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public double getCommission_price() {
                    return commission_price;
                }

                public void setCommission_price(double commission_price) {
                    this.commission_price = commission_price;
                }

                public double getCommission_rate() {
                    return commission_rate;
                }

                public void setCommission_rate(double commission_rate) {
                    this.commission_rate = commission_rate;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getCover() {
                    return cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public int getCover_id() {
                    return cover_id;
                }

                public void setCover_id(int cover_id) {
                    this.cover_id = cover_id;
                }

                public double getDeal_price() {
                    return deal_price;
                }

                public void setDeal_price(double deal_price) {
                    this.deal_price = deal_price;
                }

                public String getDelivery_city() {
                    return delivery_city;
                }

                public void setDelivery_city(String delivery_city) {
                    this.delivery_city = delivery_city;
                }

                public String getDelivery_country() {
                    return delivery_country;
                }

                public void setDelivery_country(String delivery_country) {
                    this.delivery_country = delivery_country;
                }

                public int getDelivery_country_id() {
                    return delivery_country_id;
                }

                public void setDelivery_country_id(int delivery_country_id) {
                    this.delivery_country_id = delivery_country_id;
                }

                public String getDelivery_province() {
                    return delivery_province;
                }

                public void setDelivery_province(String delivery_province) {
                    this.delivery_province = delivery_province;
                }

                public int getDistribution_type() {
                    return distribution_type;
                }

                public void setDistribution_type(int distribution_type) {
                    this.distribution_type = distribution_type;
                }

                public int getExpress() {
                    return express;
                }

                public void setExpress(int express) {
                    this.express = express;
                }

                public int getExpress_at() {
                    return express_at;
                }

                public void setExpress_at(int express_at) {
                    this.express_at = express_at;
                }

                public String getExpress_code() {
                    return express_code;
                }

                public void setExpress_code(String express_code) {
                    this.express_code = express_code;
                }

                public String getExpress_name() {
                    return express_name;
                }

                public void setExpress_name(String express_name) {
                    this.express_name = express_name;
                }

                public Object getExpress_no() {
                    return express_no;
                }

                public void setExpress_no(Object express_no) {
                    this.express_no = express_no;
                }

                public int getFans_count() {
                    return fans_count;
                }

                public void setFans_count(int fans_count) {
                    this.fans_count = fans_count;
                }

                public double getFreight() {
                    return freight;
                }

                public void setFreight(double freight) {
                    this.freight = freight;
                }

                public String getFreight_name() {
                    return freight_name;
                }

                public void setFreight_name(String freight_name) {
                    this.freight_name = freight_name;
                }

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }

                public int getOrder_sku_commission_price() {
                    return order_sku_commission_price;
                }

                public void setOrder_sku_commission_price(int order_sku_commission_price) {
                    this.order_sku_commission_price = order_sku_commission_price;
                }

                public int getOrder_sku_commission_rate() {
                    return order_sku_commission_rate;
                }

                public void setOrder_sku_commission_rate(int order_sku_commission_rate) {
                    this.order_sku_commission_rate = order_sku_commission_rate;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public String getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(String product_name) {
                    this.product_name = product_name;
                }

                public String getProduct_rid() {
                    return product_rid;
                }

                public void setProduct_rid(String product_rid) {
                    this.product_rid = product_rid;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public String getRid() {
                    return rid;
                }

                public void setRid(String rid) {
                    this.rid = rid;
                }

                public String getS_color() {
                    return s_color;
                }

                public void setS_color(String s_color) {
                    this.s_color = s_color;
                }

                public String getS_model() {
                    return s_model;
                }

                public void setS_model(String s_model) {
                    this.s_model = s_model;
                }

                public double getS_weight() {
                    return s_weight;
                }

                public void setS_weight(double s_weight) {
                    this.s_weight = s_weight;
                }

                public double getSale_price() {
                    return sale_price;
                }

                public void setSale_price(double sale_price) {
                    this.sale_price = sale_price;
                }

                public int getStock_count() {
                    return stock_count;
                }

                public void setStock_count(int stock_count) {
                    this.stock_count = stock_count;
                }

                public int getStock_quantity() {
                    return stock_quantity;
                }

                public void setStock_quantity(int stock_quantity) {
                    this.stock_quantity = stock_quantity;
                }

                public String getStore_logo() {
                    return store_logo;
                }

                public void setStore_logo(String store_logo) {
                    this.store_logo = store_logo;
                }

                public String getStore_name() {
                    return store_name;
                }

                public void setStore_name(String store_name) {
                    this.store_name = store_name;
                }

                public String getStore_rid() {
                    return store_rid;
                }

                public void setStore_rid(String store_rid) {
                    this.store_rid = store_rid;
                }

                public String getTag_line() {
                    return tag_line;
                }

                public void setTag_line(String tag_line) {
                    this.tag_line = tag_line;
                }

                public String getTown() {
                    return town;
                }

                public void setTown(String town) {
                    this.town = town;
                }
            }
        }
    }

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
