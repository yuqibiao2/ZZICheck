package com.afrid.icheck.bean.json.return_data;

import java.util.List;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2018/2/6
 */

public class GetEmpUniformReturn {

    private int resultCode;
    private Object msg;
    private List<ResultDataBean> resultData;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<ResultDataBean> getResultData() {
        return resultData;
    }

    public void setResultData(List<ResultDataBean> resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {

        private String empNo;
        private String empName;
        private String tagId;
        private String uniformTypeName;
        private Integer officeId;
        private String officeName;

        public String getEmpNo() {
            return empNo;
        }

        public void setEmpNo(String empNo) {
            this.empNo = empNo;
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getUniformTypeName() {
            return uniformTypeName;
        }

        public void setUniformTypeName(String uniformTypeName) {
            this.uniformTypeName = uniformTypeName;
        }

        public Integer getOfficeId() {
            return officeId;
        }

        public void setOfficeId(Integer officeId) {
            this.officeId = officeId;
        }

        public String getOfficeName() {
            return officeName;
        }

        public void setOfficeName(String officeName) {
            this.officeName = officeName;
        }
    }
}
