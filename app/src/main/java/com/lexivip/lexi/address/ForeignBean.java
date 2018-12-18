package com.lexivip.lexi.address;

public class ForeignBean {
    /**
     * data : {"id":3,"id_card":"18081827109465","id_card_back":{"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"},"id_card_front":{"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"},"mobile":"18888888888","user_name":"小杨"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * id : 3
         * id_card : 18081827109465
         * id_card_back : {"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"}
         * id_card_front : {"created_at":1,"filename":"1","filepath":"180523/8f51855eedae984.jpg","id":1,"type":1,"view_url":"http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg"}
         * mobile : 18888888888
         * user_name : 小杨
         */

        public int id;
        public String id_card;
        public IdCardBackBean id_card_back;
        public IdCardBackBean id_card_front;
        public String mobile;
        public String user_name;

        public static class IdCardBackBean {
            /**
             * created_at : 1
             * filename : 1
             * filepath : 180523/8f51855eedae984.jpg
             * id : 1
             * type : 1
             * view_url : http://0.0.0.0:9000/_uploads/photos/180523/8f51855eedae984.jpg
             */

            public int created_at;
            public String filename;
            public String filepath;
            public int id;
            public int type;
            public String view_url;
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
