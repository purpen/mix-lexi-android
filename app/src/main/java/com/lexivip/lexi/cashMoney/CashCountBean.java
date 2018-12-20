package com.lexivip.lexi.cashMoney;

public class CashCountBean {
    /**
     * data : {"cash_count":1,"id_card":{"code":"ID9172683504","id_card":"123124213123","id_card_back":{"id":38801,"view_url":"图片链接"},"id_card_front":{"id":38800,"view_url":"图片链接"},"user_name":"哈哈"}}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * cash_count : 1
         * id_card : {"code":"ID9172683504","id_card":"123124213123","id_card_back":{"id":38801,"view_url":"图片链接"},"id_card_front":{"id":38800,"view_url":"图片链接"},"user_name":"哈哈"}
         */

        public int cash_count;
        public IdCardBean id_card;

        public static class IdCardBean {
            /**
             * code : ID9172683504
             * id_card : 123124213123
             * id_card_back : {"id":38801,"view_url":"图片链接"}
             * id_card_front : {"id":38800,"view_url":"图片链接"}
             * user_name : 哈哈
             */

            public String code;
            public String id_card;
            public IdCardBackBean id_card_back;
            public IdCardBackBean id_card_front;
            public String user_name;

            public static class IdCardBackBean {
                /**
                 * id : 38801
                 * view_url : 图片链接
                 */

                public int id;
                public String view_url;
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
