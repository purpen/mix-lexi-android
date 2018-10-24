package com.lexivip.lexi.index.selection;

import java.util.List;

public class TodayRecommendBean {

    /**
     * data : {"count":8,"daily_recommends":[{"cover":"https://s3.lexivip.com/20180929/0143FgtHGledaawKX6WBYhygXpWb0Q4d.jpg","recommend_description":"与Rachel的对话，才知道珠宝另一种存在的意义的，他们可以是商品，但也不是商品。","recommend_id":29,"recommend_label":"创作人专访","recommend_title":"一份幸福，常驻于心","target_type":1},{"cover":"https://s3.lexivip.com/20180928/2049FnkHxM70KaSfH0eln67MZ5seihtm.jpg","recommend_description":"通过用\u201ckurinuki\u201d粘土雕刻技术创造的最新的手工陶瓷系列","recommend_id":25,"recommend_label":"生活记事","recommend_title":"穿越光影交织的梦境","target_type":1},{"cover":"https://s3.lexivip.com/20180928/3253Fhf17x-FFYGRDMKTv55xJHwzcnun.jpg","recommend_description":"让你重新爱上过期的香港\u2013文字篇","recommend_id":20,"recommend_label":"生活记事","recommend_title":"#旧城中环","target_type":1},{"cover":"https://s3.lexivip.com/20180928/0715Fk3XyolSadXrLCWYGv7PH59sQ8T_.png","recommend_description":"成为首饰设计师这半年里，我所制作的所有作品","recommend_id":23,"recommend_label":"生活记事","recommend_title":"Sarea.H成长故事","target_type":1},{"cover":"https://s3.lexivip.com/20180928/4510FpQ_UsLwrgdS-HIz4vL2h3xv73aG.jpg","recommend_description":"旧城中环，在快城市里慢走，发现香港迷人的老东西","recommend_id":21,"recommend_label":"生活记事","recommend_title":"发现香港","target_type":1},{"cover":"https://s3.lexivip.com/20180928/2859FgVm23yv3q5ooV7E3yMo30DgD6z8.jpg","recommend_description":"文具小物的归属，除了五道口，还有乐喜社区","recommend_id":26,"recommend_label":"生活记事","recommend_title":"让你尖叫的礼物","target_type":1},{"cover":"https://s3.lexivip.com/20180928/1308FqpTzDWfm2V91Hln1iJYYuO_vVxP.jpg","recommend_description":"用顶级皮革让生活更简单，皮件礼物陪你渡过便捷的日程","recommend_id":24,"recommend_label":"生活记事","recommend_title":"皮革制礼物","target_type":1},{"cover":"https://s3.lexivip.com/20180928/5307Fh9QC-X7EWssAMlojF0f6ibkirAw.jpg","recommend_description":"每颗用心手作的缤纷毛球，让毛球有了温度","recommend_id":22,"recommend_label":"生活记事","recommend_title":"毛球不只是毛球","target_type":1}],"next":false,"prev":false,"title":"今日推荐"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 8
         * daily_recommends : [{"cover":"https://s3.lexivip.com/20180929/0143FgtHGledaawKX6WBYhygXpWb0Q4d.jpg","recommend_description":"与Rachel的对话，才知道珠宝另一种存在的意义的，他们可以是商品，但也不是商品。","recommend_id":29,"recommend_label":"创作人专访","recommend_title":"一份幸福，常驻于心","target_type":1},{"cover":"https://s3.lexivip.com/20180928/2049FnkHxM70KaSfH0eln67MZ5seihtm.jpg","recommend_description":"通过用\u201ckurinuki\u201d粘土雕刻技术创造的最新的手工陶瓷系列","recommend_id":25,"recommend_label":"生活记事","recommend_title":"穿越光影交织的梦境","target_type":1},{"cover":"https://s3.lexivip.com/20180928/3253Fhf17x-FFYGRDMKTv55xJHwzcnun.jpg","recommend_description":"让你重新爱上过期的香港\u2013文字篇","recommend_id":20,"recommend_label":"生活记事","recommend_title":"#旧城中环","target_type":1},{"cover":"https://s3.lexivip.com/20180928/0715Fk3XyolSadXrLCWYGv7PH59sQ8T_.png","recommend_description":"成为首饰设计师这半年里，我所制作的所有作品","recommend_id":23,"recommend_label":"生活记事","recommend_title":"Sarea.H成长故事","target_type":1},{"cover":"https://s3.lexivip.com/20180928/4510FpQ_UsLwrgdS-HIz4vL2h3xv73aG.jpg","recommend_description":"旧城中环，在快城市里慢走，发现香港迷人的老东西","recommend_id":21,"recommend_label":"生活记事","recommend_title":"发现香港","target_type":1},{"cover":"https://s3.lexivip.com/20180928/2859FgVm23yv3q5ooV7E3yMo30DgD6z8.jpg","recommend_description":"文具小物的归属，除了五道口，还有乐喜社区","recommend_id":26,"recommend_label":"生活记事","recommend_title":"让你尖叫的礼物","target_type":1},{"cover":"https://s3.lexivip.com/20180928/1308FqpTzDWfm2V91Hln1iJYYuO_vVxP.jpg","recommend_description":"用顶级皮革让生活更简单，皮件礼物陪你渡过便捷的日程","recommend_id":24,"recommend_label":"生活记事","recommend_title":"皮革制礼物","target_type":1},{"cover":"https://s3.lexivip.com/20180928/5307Fh9QC-X7EWssAMlojF0f6ibkirAw.jpg","recommend_description":"每颗用心手作的缤纷毛球，让毛球有了温度","recommend_id":22,"recommend_label":"生活记事","recommend_title":"毛球不只是毛球","target_type":1}]
         * next : false
         * prev : false
         * title : 今日推荐
         */

        public int count;
        public String title;
        public List<DailyRecommendsBean> daily_recommends;

        public static class DailyRecommendsBean {
            /**
             * cover : https://s3.lexivip.com/20180929/0143FgtHGledaawKX6WBYhygXpWb0Q4d.jpg
             * recommend_description : 与Rachel的对话，才知道珠宝另一种存在的意义的，他们可以是商品，但也不是商品。
             * recommend_id : 29
             * recommend_label : 创作人专访
             * recommend_title : 一份幸福，常驻于心
             * target_type : 1  1=生活志文章, 2=种草清单 3=主题"
             */

            public String cover;
            public String recommend_description;
            public String recommend_id;
            public String recommend_label;
            public String recommend_title;
            public int target_type;
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
