package com.afrid.icheck.bean.json;

/**
 * 功能：
 *
 * @author yyyu
 * @version 1.0
 * @date 2018/7/28
 */
public class GetUndoReceiptRequest {

    private RequestDataBean requestData;

    public RequestDataBean getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataBean requestData) {
        this.requestData = requestData;
    }

    public static class RequestDataBean {
        private String afterDate;
        private String limitDate;
        private int washType;

        public String getAfterDate() {
            return afterDate;
        }

        public void setAfterDate(String afterDate) {
            this.afterDate = afterDate;
        }

        public String getLimitDate() {
            return limitDate;
        }

        public void setLimitDate(String limitDate) {
            this.limitDate = limitDate;
        }

        public int getWashType() {
            return washType;
        }

        public void setWashType(int washType) {
            this.washType = washType;
        }
    }
}
