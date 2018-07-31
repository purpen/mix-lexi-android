package com.thn.lexi.user.completeinfo;

public class UploadTokenBean {

    /**
     * data : {"directory_id":1,"up_endpoint":"https://up.qbox.me","up_token":"AWTEpwVNmNcVjsIL-vS1hOabJ0NgIfNDzvTbDb4i:9ePxNPobY29xyuOOrBBpPTXuNyE=:eyJzY29wZSI6ImZya2luZyIsImRlYWRsaW5lIjoxNTMzMDIxNjQwLCJjYWxsYmFja1VybCI6Imh0dHBzOi8vd3gudGFpaHVvbmlhby5jb20vb3Blbi9xaW5pdS9ub3RpZnkiLCJjYWxsYmFja0JvZHkiOiJmaWxlcGF0aD0kKGtleSkmZmlsZW5hbWU9JChmbmFtZSkmZmlsZXNpemU9JChmc2l6ZSkmbWltZT0kKG1pbWVUeXBlKSZ1c2VyX2lkPSQoeDp1c2VyX2lkKSZ3aWR0aD0kKGltYWdlSW5mby53aWR0aCkmaGVpZ2h0PSQoaW1hZ2VJbmZvLmhlaWdodCkmZXh0PSQoZXh0KSZkaXJlY3Rvcnk9JCh4OmRpcmVjdG9yeSkmZGlyZWN0b3J5X2lkPSQoeDpkaXJlY3RvcnlfaWQpIiwic2F2ZUtleSI6IiQoeWVhcikkKG1vbikkKGRheSkvJChtaW4pJChzZWMpJChldGFnKSQoZXh0KSIsImZzaXplTGltaXQiOjIwOTcxNTIwLCJyZXR1cm5VcmwiOiIiLCJyZXR1cm5Cb2R5IjoiIn0=","user_id":7}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * directory_id : 1
         * up_endpoint : https://up.qbox.me
         * up_token : AWTEpwVNmNcVjsIL-vS1hOabJ0NgIfNDzvTbDb4i:9ePxNPobY29xyuOOrBBpPTXuNyE=:eyJzY29wZSI6ImZya2luZyIsImRlYWRsaW5lIjoxNTMzMDIxNjQwLCJjYWxsYmFja1VybCI6Imh0dHBzOi8vd3gudGFpaHVvbmlhby5jb20vb3Blbi9xaW5pdS9ub3RpZnkiLCJjYWxsYmFja0JvZHkiOiJmaWxlcGF0aD0kKGtleSkmZmlsZW5hbWU9JChmbmFtZSkmZmlsZXNpemU9JChmc2l6ZSkmbWltZT0kKG1pbWVUeXBlKSZ1c2VyX2lkPSQoeDp1c2VyX2lkKSZ3aWR0aD0kKGltYWdlSW5mby53aWR0aCkmaGVpZ2h0PSQoaW1hZ2VJbmZvLmhlaWdodCkmZXh0PSQoZXh0KSZkaXJlY3Rvcnk9JCh4OmRpcmVjdG9yeSkmZGlyZWN0b3J5X2lkPSQoeDpkaXJlY3RvcnlfaWQpIiwic2F2ZUtleSI6IiQoeWVhcikkKG1vbikkKGRheSkvJChtaW4pJChzZWMpJChldGFnKSQoZXh0KSIsImZzaXplTGltaXQiOjIwOTcxNTIwLCJyZXR1cm5VcmwiOiIiLCJyZXR1cm5Cb2R5IjoiIn0=
         * user_id : 7
         */

        public String directory_id;
        public String up_endpoint;
        public String up_token;
        public String user_id;
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
