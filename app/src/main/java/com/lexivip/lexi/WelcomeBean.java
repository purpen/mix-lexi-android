package com.lexivip.lexi;

public class WelcomeBean {

    /**
     * data : {"big":"http://kg.erp.taihuoniao.com/20180910/4607FlR2IbV390NUSHE6DXFKKWmghvB-.png","micro":"http://kg.erp.taihuoniao.com/20180910/4609FutzCIzakzbarEw14acvnD6Z-elf.png","middle":"http://kg.erp.taihuoniao.com/20180910/4609FgDkp-m8ibSFOMrP3Nj6qClgsavr.png","small":"http://kg.erp.taihuoniao.com/20180910/4609Fgtti95byW2Rbr_lbI9sW2w5Gspw.png"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * big : http://kg.erp.taihuoniao.com/20180910/4607FlR2IbV390NUSHE6DXFKKWmghvB-.png
         * micro : http://kg.erp.taihuoniao.com/20180910/4609FutzCIzakzbarEw14acvnD6Z-elf.png
         * middle : http://kg.erp.taihuoniao.com/20180910/4609FgDkp-m8ibSFOMrP3Nj6qClgsavr.png
         * small : http://kg.erp.taihuoniao.com/20180910/4609Fgtti95byW2Rbr_lbI9sW2w5Gspw.png
         */

        public String big;
        public String micro;
        public String middle;
        public String small;
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
