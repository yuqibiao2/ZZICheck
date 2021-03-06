package com.afrid.icheck;

import android.app.Application;

import com.afrid.icheck.bean.json.return_data.GetUndoReceiptReturn;

import java.util.List;

/**
 * 功能：自定义Application
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class MyApplication extends Application{

    private int user_id;
    private String user_name;
    private List<String> readerIdList;
    private String currentReaderId;
    private int checkWarehouseId;
    private int checkOfficeId;
    private String checkOfficeName;
    private int checkHospitalId;
    private String checkHospitalName;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<String> getReaderIdList() {
        return readerIdList;
    }

    public void setReaderIdList(List<String> readerIdList) {
        this.readerIdList = readerIdList;
    }

    public String getCurrentReaderId() {
        return currentReaderId;
    }

    public void setCurrentReaderId(String currentReaderId) {
        this.currentReaderId = currentReaderId;
    }

    public int getCheckWarehouseId() {
        return checkWarehouseId;
    }

    public void setCheckWarehouseId(int checkWarehouseId) {
        this.checkWarehouseId = checkWarehouseId;
    }

    public int getCheckOfficeId() {
        return checkOfficeId;
    }

    public void setCheckOfficeId(int checkOfficeId) {
        this.checkOfficeId = checkOfficeId;
    }

    public String getCheckOfficeName() {
        return checkOfficeName;
    }

    public void setCheckOfficeName(String checkOfficeName) {
        this.checkOfficeName = checkOfficeName;
    }

    public int getCheckHospitalId() {
        return checkHospitalId;
    }

    public void setCheckHospitalId(int checkHospitalId) {
        this.checkHospitalId = checkHospitalId;
    }

    public String getCheckHospitalName() {
        return checkHospitalName;
    }

    public void setCheckHospitalName(String checkHospitalName) {
        this.checkHospitalName = checkHospitalName;
    }
}
