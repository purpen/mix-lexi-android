package com.lexivip.lexi.address;

import java.util.ArrayList;
import java.util.HashMap;

public class CityBean{

    /**
     * data : {"k_1_None":[{"area_scope":null,"name":"北京","oid":1,"pid":null,"sort_by":1,"status":true},{"area_scope":null,"name":"山东","oid":3,"pid":null,"sort_by":1,"status":true}],"k_2_3":[{"area_scope":null,"name":"济南","oid":5,"pid":3,"sort_by":null,"status":true}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    private HashMap<String,ArrayList<CityNameBean>> data;
    private StatusBean status;
    private boolean success;

    public HashMap<String,ArrayList<CityNameBean>> getData() {
        return data;
    }

    public void setData(HashMap<String,ArrayList<CityNameBean>> data) {
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

    public  class CityNameBean {
            /**
             * area_scope : null
             * name : 北京
             * oid : 1
             * pid : null
             * sort_by : 1
             * status : true
             */

            private Object area_scope;
            private String name;
            private int oid;
            private Object pid;
            private int sort_by;
            private boolean status;

            public Object getArea_scope() {
                return area_scope;
            }

            public void setArea_scope(Object area_scope) {
                this.area_scope = area_scope;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOid() {
                return oid;
            }

            public void setOid(int oid) {
                this.oid = oid;
            }

            public Object getPid() {
                return pid;
            }

            public void setPid(Object pid) {
                this.pid = pid;
            }

            public int getSort_by() {
                return sort_by;
            }

            public void setSort_by(int sort_by) {
                this.sort_by = sort_by;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }
        }

    public class StatusBean {
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