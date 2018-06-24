package com.afrid.icheck.bean.json;

import java.util.List;

/**
 * 功能：根据标签id得到标签信息的请求
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class GetTagInfoRequest {


    private TagInfoRequestBean requestData;

    public TagInfoRequestBean getRequestData() {
        return requestData;
    }

    public void setRequestData(TagInfoRequestBean requestData) {
        this.requestData = requestData;
    }

    public static class TagInfoRequestBean {

        private String warehouseId;//仓库id（查询对应仓库标签信息用，可为空）
        private List<String> tagList;

        public TagInfoRequestBean() {

        }

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

        public List<String> getTagList() {
            return tagList;
        }

        public void setTagList(List<String> tagList) {
            this.tagList = tagList;
        }
    }
}
