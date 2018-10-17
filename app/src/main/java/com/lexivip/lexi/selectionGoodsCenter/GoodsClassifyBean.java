package com.lexivip.lexi.selectionGoodsCenter;

import java.util.List;

public class GoodsClassifyBean {

    /**
     * data : {"categories":[{"browse_count":"分类浏览人数","cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":1,"name":"家居","pid":0,"sort_order":4,"status":1},{"browse_count":1,"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":3,"name":"创意设计","pid":0,"sort_order":3,"status":1},{"browse_count":1,"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":4,"name":"杯子","pid":0,"sort_order":2,"status":1},{"browse_count":1,"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":5,"name":"智能硬件","pid":0,"sort_order":1,"status":1}],"count":17,"next":false,"prev":false}
     * status : {"code":200,"message":"Ok all right."}
     */

    public DataBean data;
    public StatusBean status;

    public static class DataBean {
        /**
         * categories : [{"browse_count":"分类浏览人数","cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":1,"name":"家居","pid":0,"sort_order":4,"status":1},{"browse_count":1,"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":3,"name":"创意设计","pid":0,"sort_order":3,"status":1},{"browse_count":1,"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":4,"name":"杯子","pid":0,"sort_order":2,"status":1},{"browse_count":1,"cover":"http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg","description":"","id":5,"name":"智能硬件","pid":0,"sort_order":1,"status":1}]
         * count : 17
         * next : false
         * prev : false
         */

        public List<CategoriesBean> categories;

        public static class CategoriesBean {
            /**
             * browse_count : 分类浏览人数
             * cover : http://xxx/_uploads/photos/180530/72de0b9ca1ae5a3.jpg
             * description :
             * id : 1
             * name : 家居
             * pid : 0
             * sort_order : 4
             * status : 1
             */

            public String browse_count;
            public String cover;
            public String description;
            public int id;
            public String name;
            public int pid;
            public int sort_order;
            public int status;
            public boolean selected;
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
