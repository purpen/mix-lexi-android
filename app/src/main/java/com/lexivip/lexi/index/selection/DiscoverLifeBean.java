package com.lexivip.lexi.index.selection;

import com.lexivip.lexi.beans.ShopWindowBean;

import java.util.List;

public class DiscoverLifeBean {

    /**
     * data : {"count":10,"next":false,"prev":false,"shop_windows":[{"product_count":7,"product_covers":["https://s3.lexivip.com/20180928/4028FuSj0co4DStvz6SJg5V-kXfo1v58.jpg","https://s3.lexivip.com/lxServer/1535684097908.jpg","https://s3.lexivip.com/lxServer/1532272856699.jpg","https://s3.lexivip.com/lxServer/1530673736058.JPG","https://s3.lexivip.com/20181019/4925FvIVWYC1btlXpNz3oJIsN-hB7QeB.jpg","https://s3.lexivip.com/20181027/4952FlmuB4zswu4snyjyFzuYh_V6AwZU.jpg","https://s3.lexivip.com/20181025/1136FhWMserdwggdu4tvscoi4Dz5rVVe.jpeg"],"rid":126,"title":"粉红色的小确幸"},{"product_count":3,"product_covers":["https://s3.lexivip.com/20181031/3554FhgsrDcvnKGCo5YAiqk_TPNRIweO.JPG","https://s3.lexivip.com/20181031/0824FkVjxZHX-Nym_cGF1W317qqeQruP.JPG","https://s3.lexivip.com/20181028/3707FnvREyRQgOhGx9N-4rptVNuutq4n.jpg"],"rid":122,"title":"蝶恋花"},{"product_count":3,"product_covers":["https://s3.lexivip.com/lxServer/1533623486006.jpg","https://s3.lexivip.com/lxServer/1527077572212.jpg","https://s3.lexivip.com/lxServer/1535355952995.jpg"],"rid":76,"title":"小玩意儿装饰趣生活～喜欢小玩意的看过来"},{"product_count":5,"product_covers":["https://s3.lexivip.com/20181020/2555FhCTaCFgJMEI7jQoQML7iN8jeTEo.jpg","https://s3.lexivip.com/20181025/5451FmzGdLivQmJz1pyPBvGLbu5l18dD.jpg","https://s3.lexivip.com/20181013/0506FoBpwSI03VsxCgrQ-nSxqy2V_YEn.jpg","https://s3.lexivip.com/20181010/3845FvzCREnhJ1jXIZ3-6qwQrvGzkZ-n.jpg","https://s3.lexivip.com/lxServer/1516252145515.jpg"],"rid":74,"title":"冬季温暖的那些小物件"},{"product_count":3,"product_covers":["https://s3.lexivip.com/lxServer/1533628089801.jpg","https://s3.lexivip.com/20181015/0958FtWXm5gFQgsqrmrqrs30rnTNpgsE.jpg","https://s3.lexivip.com/lxServer/1532491254761.png"],"rid":80,"title":"小生活、小森系 品味生活好物"},{"product_count":5,"product_covers":["https://s3.lexivip.com/20180926/0414Frg8bZiK6PL7IsTzQjVi7cInA-42.jpg","https://s3.lexivip.com/20180926/1839FiKvsaotvd1Chn3_9D30oxhqJO6L.jpg","https://s3.lexivip.com/20181007/2354FmOc7lN3EEsEfj7V0AoKEUIbLqm9.png","https://s3.lexivip.com/20181006/2716Fgp5FZ-unMo2DP6F9rx7AQoPNfmz.jpg","https://s3.lexivip.com/lxServer/1533705205806.jpg"],"rid":40,"title":"承包了你所有的风格"},{"product_count":3,"product_covers":["https://s3.lexivip.com/20181025/3107FvqhnZZJmL229z0FZn5cFCnSb5Mq.jpg","https://s3.lexivip.com/20181021/3836FoGDpmOeqjU7YaSFFJ98IcULPMRG.jpg","https://s3.lexivip.com/20181020/2500Ft_7cvEXFn8y1BLshLorOhJSQ8mG.jpeg"],"rid":72,"title":"棒棒哒"},{"product_count":5,"product_covers":["https://s3.lexivip.com/lxServer/1528353539958.jpg","https://s3.lexivip.com/20181020/0330FkJLIzTWTwRWEHN0ce_J6Osvgg82.jpeg","https://s3.lexivip.com/lxServer/1510197685546.jpg","https://s3.lexivip.com/20181007/3101FlifGvpqMjwO3CO5119iwOxBHKvx.jpg","https://s3.lexivip.com/20180925/1659Fr1_0Elzh6WV7DIk6sTth6sJ5F6v.jpg"],"rid":43,"title":"男生好物推荐"},{"product_count":7,"product_covers":["https://s3.lexivip.com/lxServer/1534426568621.jpg","https://s3.lexivip.com/20181010/3857FoBz0e8P4pJcs5j3itCHPeXU3xuW.png","https://s3.lexivip.com/lxServer/1525760040943.jpg","https://s3.lexivip.com/lxServer/1534427299556.jpg","https://s3.lexivip.com/lxServer/1524642737649.jpg","https://s3.lexivip.com/lxServer/1533623669223.jpg","https://s3.lexivip.com/20180928/3601FlnVCL1rhvrEIJ6eVaEjj0gWlsf2.jpg"],"rid":52,"title":"粉红少女心炸裂单品合集，冬天也要有颗粉红色少女心！"},{"product_count":3,"product_covers":["https://s3.lexivip.com/20180928/0739FnJkkGi2Vs5YsB7VrJ89_Av0fWVs.jpg","https://s3.lexivip.com/lxServer/1533705211229.jpg","https://s3.lexivip.com/20181007/5508Foj6Di-gfa-d2xGVIIsZHMMFwVw4.jpg"],"rid":35,"title":"年轻就要放肆"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * count : 10
         * next : false
         * prev : false
         * shop_windows : [{"product_count":7,"product_covers":["https://s3.lexivip.com/20180928/4028FuSj0co4DStvz6SJg5V-kXfo1v58.jpg","https://s3.lexivip.com/lxServer/1535684097908.jpg","https://s3.lexivip.com/lxServer/1532272856699.jpg","https://s3.lexivip.com/lxServer/1530673736058.JPG","https://s3.lexivip.com/20181019/4925FvIVWYC1btlXpNz3oJIsN-hB7QeB.jpg","https://s3.lexivip.com/20181027/4952FlmuB4zswu4snyjyFzuYh_V6AwZU.jpg","https://s3.lexivip.com/20181025/1136FhWMserdwggdu4tvscoi4Dz5rVVe.jpeg"],"rid":126,"title":"粉红色的小确幸"},{"product_count":3,"product_covers":["https://s3.lexivip.com/20181031/3554FhgsrDcvnKGCo5YAiqk_TPNRIweO.JPG","https://s3.lexivip.com/20181031/0824FkVjxZHX-Nym_cGF1W317qqeQruP.JPG","https://s3.lexivip.com/20181028/3707FnvREyRQgOhGx9N-4rptVNuutq4n.jpg"],"rid":122,"title":"蝶恋花"},{"product_count":3,"product_covers":["https://s3.lexivip.com/lxServer/1533623486006.jpg","https://s3.lexivip.com/lxServer/1527077572212.jpg","https://s3.lexivip.com/lxServer/1535355952995.jpg"],"rid":76,"title":"小玩意儿装饰趣生活～喜欢小玩意的看过来"},{"product_count":5,"product_covers":["https://s3.lexivip.com/20181020/2555FhCTaCFgJMEI7jQoQML7iN8jeTEo.jpg","https://s3.lexivip.com/20181025/5451FmzGdLivQmJz1pyPBvGLbu5l18dD.jpg","https://s3.lexivip.com/20181013/0506FoBpwSI03VsxCgrQ-nSxqy2V_YEn.jpg","https://s3.lexivip.com/20181010/3845FvzCREnhJ1jXIZ3-6qwQrvGzkZ-n.jpg","https://s3.lexivip.com/lxServer/1516252145515.jpg"],"rid":74,"title":"冬季温暖的那些小物件"},{"product_count":3,"product_covers":["https://s3.lexivip.com/lxServer/1533628089801.jpg","https://s3.lexivip.com/20181015/0958FtWXm5gFQgsqrmrqrs30rnTNpgsE.jpg","https://s3.lexivip.com/lxServer/1532491254761.png"],"rid":80,"title":"小生活、小森系 品味生活好物"},{"product_count":5,"product_covers":["https://s3.lexivip.com/20180926/0414Frg8bZiK6PL7IsTzQjVi7cInA-42.jpg","https://s3.lexivip.com/20180926/1839FiKvsaotvd1Chn3_9D30oxhqJO6L.jpg","https://s3.lexivip.com/20181007/2354FmOc7lN3EEsEfj7V0AoKEUIbLqm9.png","https://s3.lexivip.com/20181006/2716Fgp5FZ-unMo2DP6F9rx7AQoPNfmz.jpg","https://s3.lexivip.com/lxServer/1533705205806.jpg"],"rid":40,"title":"承包了你所有的风格"},{"product_count":3,"product_covers":["https://s3.lexivip.com/20181025/3107FvqhnZZJmL229z0FZn5cFCnSb5Mq.jpg","https://s3.lexivip.com/20181021/3836FoGDpmOeqjU7YaSFFJ98IcULPMRG.jpg","https://s3.lexivip.com/20181020/2500Ft_7cvEXFn8y1BLshLorOhJSQ8mG.jpeg"],"rid":72,"title":"棒棒哒"},{"product_count":5,"product_covers":["https://s3.lexivip.com/lxServer/1528353539958.jpg","https://s3.lexivip.com/20181020/0330FkJLIzTWTwRWEHN0ce_J6Osvgg82.jpeg","https://s3.lexivip.com/lxServer/1510197685546.jpg","https://s3.lexivip.com/20181007/3101FlifGvpqMjwO3CO5119iwOxBHKvx.jpg","https://s3.lexivip.com/20180925/1659Fr1_0Elzh6WV7DIk6sTth6sJ5F6v.jpg"],"rid":43,"title":"男生好物推荐"},{"product_count":7,"product_covers":["https://s3.lexivip.com/lxServer/1534426568621.jpg","https://s3.lexivip.com/20181010/3857FoBz0e8P4pJcs5j3itCHPeXU3xuW.png","https://s3.lexivip.com/lxServer/1525760040943.jpg","https://s3.lexivip.com/lxServer/1534427299556.jpg","https://s3.lexivip.com/lxServer/1524642737649.jpg","https://s3.lexivip.com/lxServer/1533623669223.jpg","https://s3.lexivip.com/20180928/3601FlnVCL1rhvrEIJ6eVaEjj0gWlsf2.jpg"],"rid":52,"title":"粉红少女心炸裂单品合集，冬天也要有颗粉红色少女心！"},{"product_count":3,"product_covers":["https://s3.lexivip.com/20180928/0739FnJkkGi2Vs5YsB7VrJ89_Av0fWVs.jpg","https://s3.lexivip.com/lxServer/1533705211229.jpg","https://s3.lexivip.com/20181007/5508Foj6Di-gfa-d2xGVIIsZHMMFwVw4.jpg"],"rid":35,"title":"年轻就要放肆"}]
         */

        public int count;
        public boolean next;
        public boolean prev;
        public List<ShopWindowBean> shop_windows;
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
