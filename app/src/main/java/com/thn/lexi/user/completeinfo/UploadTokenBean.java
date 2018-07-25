package com.thn.lexi.user.completeinfo;

public class UploadTokenBean {

    /**
     * data : {"user_id":4,"up_endpoint":"https://up.qbox.me","up_token":"AWTEpwVNmNcVjsIL-vS1hOabJ0NgIfNDzvTbDb4i:OgsNLotOvWrTLDv7_QOSREvN2Zo=:eyJzY29wZSI6ImZya2luZyIsImRlYWRsaW5lIjoxNTIxNzcxNzI1LCJjYWxsYmFja1VybCI6Imh0dHA6Ly8xMjcuMC4wLjE6OTAwMC9vcGVuL3Fpbml1L25vdGlmeSIsImNhbGxiYWNrQm9keSI6ImZpbGVwYXRoPSQoa2V5KSZmaWxlbmFtZT0kKGZuYW1lKSZmaWxlc2l6ZT0kKGZzaXplKSZtaW1lPSQobWltZVR5cGUpJnVzZXJfaWQ9JCh4OnVzZXJfaWQpJndpZHRoPSQoaW1hZ2VJbmZvLndpZHRoKSZoZWlnaHQ9JChpbWFnZUluZm8uaGVpZ2h0KSZleHQ9JChleHQpJmRpcmVjdG9yeT0kKHg6ZGlyZWN0b3J5KSIsInNhdmVLZXkiOiIkKHllYXIpJChtb24pJChkYXkpLyQoZXRhZykkKGV4dCkiLCJmc2l6ZUxpbWl0IjoyMDk3MTUyMCwicmV0dXJuVXJsIjoiIiwicmV0dXJuQm9keSI6IiJ9"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * user_id : 4
         * up_endpoint : https://up.qbox.me
         * up_token : AWTEpwVNmNcVjsIL-vS1hOabJ0NgIfNDzvTbDb4i:OgsNLotOvWrTLDv7_QOSREvN2Zo=:eyJzY29wZSI6ImZya2luZyIsImRlYWRsaW5lIjoxNTIxNzcxNzI1LCJjYWxsYmFja1VybCI6Imh0dHA6Ly8xMjcuMC4wLjE6OTAwMC9vcGVuL3Fpbml1L25vdGlmeSIsImNhbGxiYWNrQm9keSI6ImZpbGVwYXRoPSQoa2V5KSZmaWxlbmFtZT0kKGZuYW1lKSZmaWxlc2l6ZT0kKGZzaXplKSZtaW1lPSQobWltZVR5cGUpJnVzZXJfaWQ9JCh4OnVzZXJfaWQpJndpZHRoPSQoaW1hZ2VJbmZvLndpZHRoKSZoZWlnaHQ9JChpbWFnZUluZm8uaGVpZ2h0KSZleHQ9JChleHQpJmRpcmVjdG9yeT0kKHg6ZGlyZWN0b3J5KSIsInNhdmVLZXkiOiIkKHllYXIpJChtb24pJChkYXkpLyQoZXRhZykkKGV4dCkiLCJmc2l6ZUxpbWl0IjoyMDk3MTUyMCwicmV0dXJuVXJsIjoiIiwicmV0dXJuQm9keSI6IiJ9
         */

        public int user_id;
        public String up_endpoint;
        public String up_token;
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
