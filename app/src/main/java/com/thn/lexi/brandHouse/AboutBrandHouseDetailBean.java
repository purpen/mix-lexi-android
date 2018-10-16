package com.thn.lexi.brandHouse;

import java.util.List;

public class AboutBrandHouseDetailBean {
    /**
     * data : {"content":"<p>窗前明月光jhhhhhhhhhhhhhhhhhhfaklshklshdkfhjkalhskdhfks<img src='http://kg.erp.taihuoniao.com/static/img/default-logo-180x180.png'>思考方式减肥反复反复反复反复反反复复思考顶顶顶顶顶的大街上的开发可阿克苏到就发开了速度发货快啦速度发货阿克苏到恢复卡了速度发货卡死了地方好阿克苏到恢复克拉三东方喀什到恢复卡萨丁发哈里开始地方阿克苏到恢复卡号的分离卡水电费阿克苏到恢复克拉三东方和阿克苏到恢复快啦收到话费啊速度快发货可历史的恢复啊上课得恢复卡了速度发货阿克苏地方哈来看速度发货阿克苏到恢复卡了速度发货啊上课点复活快啦速度发货<\/p>","split_content":[{"content":"窗前明月光jhhhhhhhhhhhhhhhhhhfaklshklshdkfhjkalhskdhfks","rid":"7624190","type":"text"},{"content":"http://kg.erp.taihuoniao.com/static/img/default-logo-180x180.png","rid":"4279138","type":"image"},{"content":"思考方式减肥反复反复反复反复反反复复思考顶顶顶顶顶的大街上的开发可阿克苏到就发开了速度发货快啦速度发货阿克苏到恢复卡了速度发货卡死了地方好阿克苏到恢复克拉三东方喀什到恢复卡萨丁发哈里开始地方阿克苏到恢复卡号的分离卡水电费阿克苏到恢复克拉三东方和阿克苏到恢复快啦收到话费啊速度快发货可历史的恢复啊上课得恢复卡了速度发货阿克苏地方哈来看速度发货阿克苏到恢复卡了速度发货啊上课点复活快啦速度发货","rid":"3501248","type":"text"}],"store_rid":"97958360","summary":"窗前明月光jhhhhhhhhhhhhhhhhhhfaklshklshdkfhjkalhskdhfks思考方式减肥反复反复反复反复反反复复思考顶顶顶顶顶的大街上的开发可阿克苏到就发开了速度发货快啦速度发货阿克苏到恢复卡了速度发货卡死了地方好阿克苏到恢复克拉三东方喀什到恢复卡萨丁发哈","updated_at":1532671360}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * content : <p>窗前明月光jhhhhhhhhhhhhhhhhhhfaklshklshdkfhjkalhskdhfks<img src='http://kg.erp.taihuoniao.com/static/img/default-logo-180x180.png'>思考方式减肥反复反复反复反复反反复复思考顶顶顶顶顶的大街上的开发可阿克苏到就发开了速度发货快啦速度发货阿克苏到恢复卡了速度发货卡死了地方好阿克苏到恢复克拉三东方喀什到恢复卡萨丁发哈里开始地方阿克苏到恢复卡号的分离卡水电费阿克苏到恢复克拉三东方和阿克苏到恢复快啦收到话费啊速度快发货可历史的恢复啊上课得恢复卡了速度发货阿克苏地方哈来看速度发货阿克苏到恢复卡了速度发货啊上课点复活快啦速度发货</p>
         * split_content : [{"content":"窗前明月光jhhhhhhhhhhhhhhhhhhfaklshklshdkfhjkalhskdhfks","rid":"7624190","type":"text"},{"content":"http://kg.erp.taihuoniao.com/static/img/default-logo-180x180.png","rid":"4279138","type":"image"},{"content":"思考方式减肥反复反复反复反复反反复复思考顶顶顶顶顶的大街上的开发可阿克苏到就发开了速度发货快啦速度发货阿克苏到恢复卡了速度发货卡死了地方好阿克苏到恢复克拉三东方喀什到恢复卡萨丁发哈里开始地方阿克苏到恢复卡号的分离卡水电费阿克苏到恢复克拉三东方和阿克苏到恢复快啦收到话费啊速度快发货可历史的恢复啊上课得恢复卡了速度发货阿克苏地方哈来看速度发货阿克苏到恢复卡了速度发货啊上课点复活快啦速度发货","rid":"3501248","type":"text"}]
         * store_rid : 97958360
         * summary : 窗前明月光jhhhhhhhhhhhhhhhhhhfaklshklshdkfhjkalhskdhfks思考方式减肥反复反复反复反复反反复复思考顶顶顶顶顶的大街上的开发可阿克苏到就发开了速度发货快啦速度发货阿克苏到恢复卡了速度发货卡死了地方好阿克苏到恢复克拉三东方喀什到恢复卡萨丁发哈
         * updated_at : 1532671360
         */

        public String content;
        public String store_rid;
        public String summary;
        public int updated_at;
        public List<SplitContentBean> split_content;

        public static class SplitContentBean {
            /**
             * content : 窗前明月光jhhhhhhhhhhhhhhhhhhfaklshklshdkfhjkalhskdhfks
             * rid : 7624190
             * type : text
             */

            public String content;
            public String rid;
            public String type;
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
