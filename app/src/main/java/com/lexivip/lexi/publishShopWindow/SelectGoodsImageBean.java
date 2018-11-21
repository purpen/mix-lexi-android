package com.lexivip.lexi.publishShopWindow;
import java.util.List;
public class SelectGoodsImageBean {

    /**
     * data : {"images":[{"created_at":1539998863,"filename":"WechatIMG23.jpeg","filepath":"20181020/2742Ft9q7Nug7jf9V-ambe5DXhGNNHyF.jpeg","id":30874,"type":1,"view_url":"http://127.0.0.1:9000/_uploads/photos/20181020/2742Ft9q7Nug7jf9V-ambe5DXhGNNHyF.jpeg"},{"created_at":1539998854,"filename":"WechatIMG22.jpeg","filepath":"20181020/2732FiBBLWdziXYravTQQMsH9rfn8O8e.jpeg","id":30873,"type":1,"view_url":"http://127.0.0.1:9000/_uploads/photos/20181020/2732FiBBLWdziXYravTQQMsH9rfn8O8e.jpeg"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<ImagesBean> images;

        public static class ImagesBean {
            /**
             * created_at : 1539998863
             * filename : WechatIMG23.jpeg
             * filepath : 20181020/2742Ft9q7Nug7jf9V-ambe5DXhGNNHyF.jpeg
             * id : 30874
             * type : 1
             * view_url : http://127.0.0.1:9000/_uploads/photos/20181020/2742Ft9q7Nug7jf9V-ambe5DXhGNNHyF.jpeg
             */

            public int created_at;
            public String filename;
            public String filepath;
            public String id;
            public int type;
            public String view_url;
            public boolean selected;
            public String store_rid;
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
