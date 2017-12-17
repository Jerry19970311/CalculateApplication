package cn.edu.bistu.cs.se.calculateapplication;

/**
 * Created by XZL on 2017/11/26.
 */

public class Money {
    /**
     * result : {"rate":"6.5997","scur":"USD","update":"2017-11-26 04:44:02","tcur":"CNY","ratenm":"美元/人民币","status":"ALREADY"}
     * success : 1
     */
    private ResultEntity result;
    private String success;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ResultEntity getResult() {
        return result;
    }

    public String getSuccess() {
        return success;
    }

    public class ResultEntity {
        /**
         * rate : 6.5997
         * scur : USD
         * update : 2017-11-26 04:44:02
         * tcur : CNY
         * ratenm : 美元/人民币
         * status : ALREADY
         */
        private String rate;
        private String scur;
        private String update;
        private String tcur;
        private String ratenm;
        private String status;

        public void setRate(String rate) {
            this.rate = rate;
        }

        public void setScur(String scur) {
            this.scur = scur;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public void setTcur(String tcur) {
            this.tcur = tcur;
        }

        public void setRatenm(String ratenm) {
            this.ratenm = ratenm;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRate() {
            return rate;
        }

        public String getScur() {
            return scur;
        }

        public String getUpdate() {
            return update;
        }

        public String getTcur() {
            return tcur;
        }

        public String getRatenm() {
            return ratenm;
        }

        public String getStatus() {
            return status;
        }
    }
}
