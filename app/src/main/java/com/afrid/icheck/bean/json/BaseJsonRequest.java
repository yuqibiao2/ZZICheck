package com.afrid.icheck.bean.json;

/**
 * 功能：
 *
 * @author yu
 * @date 2017/8/9.
 */
public class BaseJsonRequest<T> {

    private T requestData;

    public BaseJsonRequest() {

    }

    public T getRequestData() {
        return requestData;
    }

    public void setRequestData(T requestData) {
        this.requestData = requestData;
    }
}
