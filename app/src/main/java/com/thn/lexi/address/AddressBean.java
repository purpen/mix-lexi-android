package com.thn.lexi.address;

public class AddressBean {

    /**
     * data : {"area":null,"area_id":null,"city":"北京","city_id":1,"country_id":1,"first_name":"田","full_address":"北京北京大街","full_name":"田帅","is_default":false,"is_from_wx":false,"last_name":null,"mobile":"13278989898","phone":null,"province":"北京","province_id":1,"rid":"5254096781","street_address":"大街","street_address_two":null,"town":null,"town_id":0,"zipcode":null}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

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
        /**
         * area : null
         * area_id : null
         * city : 北京
         * city_id : 1
         * country_id : 1
         * first_name : 田
         * full_address : 北京北京大街
         * full_name : 田帅
         * is_default : false
         * is_from_wx : false
         * last_name : null
         * mobile : 13278989898
         * phone : null
         * province : 北京
         * province_id : 1
         * rid : 5254096781
         * street_address : 大街
         * street_address_two : null
         * town : null
         * town_id : 0
         * zipcode : null
         */

        private Object area;
        private Object area_id;
        private String city;
        private int city_id;
        private int country_id;
        private String first_name;
        private String full_address;
        private String full_name;
        private boolean is_default;
        private boolean is_from_wx;
        private Object last_name;
        private String mobile;
        private Object phone;
        private String province;
        private int province_id;
        private String rid;
        private String street_address;
        private Object street_address_two;
        private Object town;
        private int town_id;
        private Object zipcode;
        private String country_name;

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public Object getArea_id() {
            return area_id;
        }

        public void setArea_id(Object area_id) {
            this.area_id = area_id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getFull_address() {
            return full_address;
        }

        public void setFull_address(String full_address) {
            this.full_address = full_address;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public boolean isIs_default() {
            return is_default;
        }

        public void setIs_default(boolean is_default) {
            this.is_default = is_default;
        }

        public boolean isIs_from_wx() {
            return is_from_wx;
        }

        public void setIs_from_wx(boolean is_from_wx) {
            this.is_from_wx = is_from_wx;
        }

        public Object getLast_name() {
            return last_name;
        }

        public void setLast_name(Object last_name) {
            this.last_name = last_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getProvince_id() {
            return province_id;
        }

        public void setProvince_id(int province_id) {
            this.province_id = province_id;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getStreet_address() {
            return street_address;
        }

        public void setStreet_address(String street_address) {
            this.street_address = street_address;
        }

        public Object getStreet_address_two() {
            return street_address_two;
        }

        public void setStreet_address_two(Object street_address_two) {
            this.street_address_two = street_address_two;
        }

        public Object getTown() {
            return town;
        }

        public void setTown(Object town) {
            this.town = town;
        }

        public int getTown_id() {
            return town_id;
        }

        public void setTown_id(int town_id) {
            this.town_id = town_id;
        }

        public Object getZipcode() {
            return zipcode;
        }

        public void setZipcode(Object zipcode) {
            this.zipcode = zipcode;
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