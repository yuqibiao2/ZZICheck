package com.afrid.icheck.bean.json.return_data;

import java.util.List;

/**
 * 功能：
 *
 * @author yyyu
 * @version 1.0
 * @date 2018/7/28
 */
public class GetUndoReceiptReturn {

    private int resultCode;
    private String msg;
    private List<ResultDataBean> resultData;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultDataBean> getResultData() {
        return resultData;
    }

    public void setResultData(List<ResultDataBean> resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {
        private String mainId;
        private long createTime;
        private List<SubReceiptListBean> subReceiptList;

        public String getMainId() {
            return mainId;
        }

        public void setMainId(String mainId) {
            this.mainId = mainId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public List<SubReceiptListBean> getSubReceiptList() {
            return subReceiptList;
        }

        public void setSubReceiptList(List<SubReceiptListBean> subReceiptList) {
            this.subReceiptList = subReceiptList;
        }

        public static class SubReceiptListBean {
            /**
             * linenId : 8
             * linenTypeName : 护士长袖上衣
             * linenCount : 2
             */

            private int linenId;
            private String linenTypeName;
            private int linenCount;

            public int getLinenId() {
                return linenId;
            }

            public void setLinenId(int linenId) {
                this.linenId = linenId;
            }

            public String getLinenTypeName() {
                return linenTypeName;
            }

            public void setLinenTypeName(String linenTypeName) {
                this.linenTypeName = linenTypeName;
            }

            public int getLinenCount() {
                return linenCount;
            }

            public void setLinenCount(int linenCount) {
                this.linenCount = linenCount;
            }
        }
    }
}
