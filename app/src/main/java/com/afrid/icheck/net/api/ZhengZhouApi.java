package com.afrid.icheck.net.api;

import com.afrid.icheck.bean.json.BaseJsonResult;
import com.afrid.icheck.bean.json.return_data.GetEmpUniformReturn;
import com.afrid.icheck.bean.json.return_data.GetHospitalReturn;
import com.afrid.icheck.bean.json.return_data.GetOfficeReturn;
import com.afrid.icheck.bean.json.return_data.GetTagInfoListReturn;
import com.afrid.icheck.bean.json.return_data.GetWarehouseReturn;
import com.afrid.icheck.bean.json.return_data.GetWashFactoryReturn;
import com.afrid.icheck.bean.json.return_data.LoginReturn;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 功能：昆明项目网络请求
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public interface ZhengZhouApi {

    @GET("empUniform/v1/getEmpHospital")
    Observable<GetHospitalReturn> getEmpHospital();

    @GET("empUniform/v1/getEmpOfficeByHosNo/{hospitalNo}")
    Observable<GetOfficeReturn> getEmpOfficeByHospitalNo(@Path("hospitalNo") String hospitalNo);

    @POST("empUniform/v1/getEmpUniformList")
    Observable<GetEmpUniformReturn> getetEmpUniformList(@Body RequestBody requestBody);

    @GET("warehouse/wrapper/v1/getHospitalByUserId/userId/{userId}")
    Observable<GetHospitalReturn> getHospitalByUserId(@Path("userId") Integer userId);

    @GET("office/v1/getOfficeByOfficeSuper/{hospitalId}")
    Observable<GetOfficeReturn> getOfficeByHospitalId(@Path("hospitalId") Integer hospitalId);

    @POST("receipt/wrapper/v1/saveReceipt")
    Observable<BaseJsonResult<String>> saveReceipt(@Body RequestBody requestBody);

    @POST("tag/v1/getTagInfoList")
    Observable<GetTagInfoListReturn> getTagInfoList(@Body RequestBody requestBody);

    @GET("warehouse/v1/getWarehouse/userId/{userId}")
    Observable<GetWarehouseReturn> getWarehouse(@Path("userId") Integer userId);

    @GET("warehouse/v1/getWashFactory/userId/{userId}")
    Observable<GetWashFactoryReturn> getWashFactory(@Path("userId") Integer userId);

    @POST("user/v1/checkUser")
    Observable<LoginReturn> login(@Body RequestBody requestBody);

}
