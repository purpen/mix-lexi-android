package com.lexivip.lexi;

import java.util.Map;

public class MiPushTestBean {
    /**
     * display_type : notification
     * extra : {"jdj":"hshhs","tey":"jfj","jf":"jfjf"}
     * msg_id : uaoye9y154451311112000
     * body : {"after_open":"go_app","ticker":"","text":"离线推送测试","title":"shenme"}
     * random_min : 0
     */

    public String display_type;
    public Map<String,String> extra;
    public String msg_id;
    public BodyBean body;
    public int random_min;

    public static class BodyBean {
        /**
         * after_open : go_app
         * ticker :
         * text : 离线推送测试
         * title : shenme
         */

        public String after_open;
        public String ticker;
        public String text;
        public String title;
    }
}
