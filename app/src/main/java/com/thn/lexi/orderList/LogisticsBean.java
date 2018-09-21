package com.thn.lexi.orderList;

import java.util.List;

public class LogisticsBean {
    /**
     * data : {"EBusinessID":"1302778","LogisticCode":"1199965242414","ShipperCode":"EMS","State":"3","Success":true,"Traces":[{"AcceptStation":"天津市邮政速递物流公司武清区分公司崔黄口揽投部已收件（揽投员姓名：李静,联系电话:）","AcceptTime":"2018-05-21 07:46:00"},{"AcceptStation":"已离开天津市邮政速递物流公司武清区分公司崔黄口揽投部，发往天津处理中心","AcceptTime":"2018-05-21 08:39:17"},{"AcceptStation":"离开天津市 发往北京市","AcceptTime":"2018-05-21 20:35:42"},{"AcceptStation":"到达  中国邮政速递物流股份有限公司北京市国货航航空邮件处 处理中心","AcceptTime":"2018-05-21 23:15:00"},{"AcceptStation":"离开中国邮政速递物流股份有限公司北京市国货航航空邮件处 发往北京邮政速递安定门区域分公司北苑营投部","AcceptTime":"2018-05-22 05:15:05"},{"AcceptStation":"北京邮政速递安定门区域分公司北苑营投部安排投递，预计23:59:00前投递（投递员姓名：韩方朋;联系电话：18519363520）","AcceptTime":"2018-05-22 07:54:02"},{"AcceptStation":"投递并签收，签收人：他人收 超市","AcceptTime":"2018-05-22 12:49:50"}]}
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
         * EBusinessID : 1302778
         * LogisticCode : 1199965242414
         * ShipperCode : EMS
         * State : 3
         * Success : true
         * Traces : [{"AcceptStation":"天津市邮政速递物流公司武清区分公司崔黄口揽投部已收件（揽投员姓名：李静,联系电话:）","AcceptTime":"2018-05-21 07:46:00"},{"AcceptStation":"已离开天津市邮政速递物流公司武清区分公司崔黄口揽投部，发往天津处理中心","AcceptTime":"2018-05-21 08:39:17"},{"AcceptStation":"离开天津市 发往北京市","AcceptTime":"2018-05-21 20:35:42"},{"AcceptStation":"到达  中国邮政速递物流股份有限公司北京市国货航航空邮件处 处理中心","AcceptTime":"2018-05-21 23:15:00"},{"AcceptStation":"离开中国邮政速递物流股份有限公司北京市国货航航空邮件处 发往北京邮政速递安定门区域分公司北苑营投部","AcceptTime":"2018-05-22 05:15:05"},{"AcceptStation":"北京邮政速递安定门区域分公司北苑营投部安排投递，预计23:59:00前投递（投递员姓名：韩方朋;联系电话：18519363520）","AcceptTime":"2018-05-22 07:54:02"},{"AcceptStation":"投递并签收，签收人：他人收 超市","AcceptTime":"2018-05-22 12:49:50"}]
         */

        private String EBusinessID;
        private String LogisticCode;
        private String ShipperCode;
        private String State;
        private boolean Success;
        private List<TracesBean> Traces;

        public String getEBusinessID() {
            return EBusinessID;
        }

        public void setEBusinessID(String EBusinessID) {
            this.EBusinessID = EBusinessID;
        }

        public String getLogisticCode() {
            return LogisticCode;
        }

        public void setLogisticCode(String LogisticCode) {
            this.LogisticCode = LogisticCode;
        }

        public String getShipperCode() {
            return ShipperCode;
        }

        public void setShipperCode(String ShipperCode) {
            this.ShipperCode = ShipperCode;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public boolean isSuccess() {
            return Success;
        }

        public void setSuccess(boolean Success) {
            this.Success = Success;
        }

        public List<TracesBean> getTraces() {
            return Traces;
        }

        public void setTraces(List<TracesBean> Traces) {
            this.Traces = Traces;
        }

        public static class TracesBean {
            /**
             * AcceptStation : 天津市邮政速递物流公司武清区分公司崔黄口揽投部已收件（揽投员姓名：李静,联系电话:）
             * AcceptTime : 2018-05-21 07:46:00
             */

            private String AcceptStation;
            private String AcceptTime;

            public String getAcceptStation() {
                return AcceptStation;
            }

            public void setAcceptStation(String AcceptStation) {
                this.AcceptStation = AcceptStation;
            }

            public String getAcceptTime() {
                return AcceptTime;
            }

            public void setAcceptTime(String AcceptTime) {
                this.AcceptTime = AcceptTime;
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
