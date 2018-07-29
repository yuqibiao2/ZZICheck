package com.afrid.icheck.bean.json;

import com.afrid.icheck.bean.SubReceiptListBean;

import java.util.List;

/**
 * 功能：保存收据请求数据
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class SaveReceiptRequest {


    private RequestDataBean requestData;

    public RequestDataBean getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataBean requestData) {
        this.requestData = requestData;
    }

    public static class RequestDataBean {
        private String readerId;
        private int userId;
        private int senderWarehouseId;//医院Id
        private int receiptWarehouseId;//仓库Id
        private String officeId;//科室ID
        private Integer orderType;//1：收脏 2：发净 3：盘点
        private Integer washType;//1：布草 2：工服
        private String receiveMainId;//发净时才设置
        private int linenNum;
        private List<SubReceiptListBean> subReceiptList;

        public String getReceiveMainId() {
            return receiveMainId;
        }

        public void setReceiveMainId(String receiveMainId) {
            this.receiveMainId = receiveMainId;
        }

        public String getReaderId() {
            return readerId;
        }

        public void setReaderId(String readerId) {
            this.readerId = readerId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getSenderWarehouseId() {
            return senderWarehouseId;
        }

        public void setSenderWarehouseId(int senderWarehouseId) {
            this.senderWarehouseId = senderWarehouseId;
        }

        public int getReceiptWarehouseId() {
            return receiptWarehouseId;
        }

        public void setReceiptWarehouseId(int receiptWarehouseId) {
            this.receiptWarehouseId = receiptWarehouseId;
        }

        public int getLinenNum() {
            return linenNum;
        }

        public void setLinenNum(int linenNum) {
            this.linenNum = linenNum;
        }

        public List<SubReceiptListBean> getSubReceiptList() {
            return subReceiptList;
        }

        public void setSubReceiptList(List<SubReceiptListBean> subReceiptList) {
            this.subReceiptList = subReceiptList;
        }

        public String getOfficeId() {
            return officeId;
        }

        public void setOfficeId(String officeId) {
            this.officeId = officeId;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public void setOrderType(Integer orderType) {
            this.orderType = orderType;
        }

        public Integer getWashType() {
            return washType;
        }

        public void setWashType(Integer washType) {
            this.washType = washType;
        }
    }

}
