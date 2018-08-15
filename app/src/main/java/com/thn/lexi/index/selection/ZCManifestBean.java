package com.thn.lexi.index.selection;

import java.util.List;

public class ZCManifestBean {

    /**
     * data : {"count":2,"life_records":[{"audit_status":2,"content":"正文...","cover":"http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","cover_id":2,"created_at":1533353214,"description":"摘要...","published_at":1533353944,"refuse_reason":null,"rid":5,"status":2,"title":"她手绘出来的童话馆的小公主居然偷偷跑出来了","type":2},{"audit_status":2,"content":"新正文...","cover":"http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","cover_id":2,"created_at":1533353214,"description":"新摘要...","published_at":1533353214,"refuse_reason":null,"rid":4,"status":2,"title":"她手绘出来的童话馆的小公主居然偷偷跑出来了","type":2}],"next":false,"prev":false}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 2
         * life_records : [{"audit_status":2,"content":"正文...","cover":"http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","cover_id":2,"created_at":1533353214,"description":"摘要...","published_at":1533353944,"refuse_reason":null,"rid":5,"status":2,"title":"她手绘出来的童话馆的小公主居然偷偷跑出来了","type":2},{"audit_status":2,"content":"新正文...","cover":"http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg","cover_id":2,"created_at":1533353214,"description":"新摘要...","published_at":1533353214,"refuse_reason":null,"rid":4,"status":2,"title":"她手绘出来的童话馆的小公主居然偷偷跑出来了","type":2}]
         * next : false
         * prev : false
         */

        public int count;
        public boolean next;
        public boolean prev;
        public List<LifeRecordsBean> life_records;

        public static class LifeRecordsBean {
            /**
             * audit_status : 2
             * content : 正文...
             * cover : http://127.0.0.1:9000/_uploads/photos/180707/77409c8ab9b0abf.jpg
             * cover_id : 2
             * created_at : 1533353214
             * description : 摘要...
             * published_at : 1533353944
             * refuse_reason : null
             * rid : 5
             * status : 2
             * title : 她手绘出来的童话馆的小公主居然偷偷跑出来了
             * type : 2
             */

            public int audit_status;
            public String content;
            public String cover;
            public int cover_id;
            public int created_at;
            public String description;
            public int published_at;
            public Object refuse_reason;
            public int rid;
            public int status;
            public String title;
            public int type;
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
