package com.afrid.icheck.net;

import com.afrid.icheck.bean.json.BaseJsonResult;
import com.afrid.icheck.bean.json.return_data.GetEmpUniformReturn;
import com.afrid.icheck.bean.json.return_data.GetHospitalReturn;
import com.afrid.icheck.bean.json.return_data.GetOfficeReturn;
import com.afrid.icheck.bean.json.return_data.GetTagInfoListReturn;
import com.afrid.icheck.bean.json.return_data.GetWarehouseReturn;
import com.afrid.icheck.bean.json.return_data.GetWashFactoryReturn;
import com.afrid.icheck.bean.json.return_data.LoginReturn;
import com.afrid.icheck.net.api.ZhengZhouApi;
import com.yyyu.baselibrary.utils.MyLog;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 功能：网络请求API的统一管理类，和其它组件进行交互
 *
 * @author yu
 * @version 1.0
 * @date 2017/8/10
 */

public class APIMethodManager {


    private ZhengZhouApi kunmingApi;

    private APIMethodManager() {
        APIFactory apiFactory = APIFactory.getInstance();
        kunmingApi = apiFactory.createKunmingApi();
    }

    private static class SingletonHolder {
        private static final APIMethodManager INSTANCE = new APIMethodManager();
    }

    public static APIMethodManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 得到工服信息
     *
     * @param request
     * @param callback
     * @return
     */
    public Subscription getetEmpUniformList(String request, final IRequestCallback<GetEmpUniformReturn> callback){
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), request);
        Subscription subscribe = kunmingApi.getetEmpUniformList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetEmpUniformReturn>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(GetEmpUniformReturn getEmpUniformReturn) {
                        callback.onSuccess(getEmpUniformReturn);
                    }
                });

        return subscribe;
    }

    /**
     * 得到医院信息--工服
     *
     * @param callback
     * @return
     */
    public Subscription getEmpHospital(final IRequestCallback<GetHospitalReturn> callback){

        Subscription subscribe = kunmingApi.getEmpHospital()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetHospitalReturn>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(GetHospitalReturn data) {
                        callback.onSuccess(data);
                    }
                });
        return subscribe;
    }

    /**
     * 得到医院对应的科室 --工服
     *
     * @param hospitalNo
     * @param callback
     * @return
     */
    public Subscription getEmpOfficeByHospitalNo(String hospitalNo , final IRequestCallback<GetOfficeReturn> callback){
        Subscription subscribe = kunmingApi.getEmpOfficeByHospitalNo(hospitalNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetOfficeReturn>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(GetOfficeReturn getOfficeReturn) {
                        callback.onSuccess(getOfficeReturn);
                    }
                });
        return subscribe;
    }


    /**
     * 得到用户所管理的医院
     *
     * @param userId
     * @param callback
     * @return
     */
    public Subscription getHospitalByUserId(Integer userId , final IRequestCallback<GetHospitalReturn> callback){

        Subscription subscribe = kunmingApi.getHospitalByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetHospitalReturn>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(GetHospitalReturn data) {
                        callback.onSuccess(data);
                    }
                });
        return subscribe;
    }


    /**
     * 得到医院对应的科室
     *
     * @param hospitalId
     * @param callback
     * @return
     */
    public Subscription getOfficeByHospitalId(Integer hospitalId , final IRequestCallback<GetOfficeReturn> callback){
        Subscription subscribe = kunmingApi.getOfficeByHospitalId(hospitalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetOfficeReturn>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(GetOfficeReturn getOfficeReturn) {
                        callback.onSuccess(getOfficeReturn);
                    }
                });
        return subscribe;
    }

    /**
     * 保存收据
     *
     * @param request  SaveReceiptRequest对应的json
     * @param callback
     */
    public Subscription saveReceipt(String request, final IRequestCallback<BaseJsonResult<String>> callback) {
        MyLog.e("request===" + request);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), request);
        Subscription subscribe = kunmingApi.saveReceipt(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseJsonResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(BaseJsonResult<String> baseJsonResult) {
                        callback.onSuccess(baseJsonResult);
                    }
                });
        return subscribe;
    }

    /**
     * 得到标签的信息
     *
     * @param request  GetTagInfoRequest对应的json
     * @param callback
     */
    public Subscription getTagInfoList(String request, final IRequestCallback<GetTagInfoListReturn> callback) {
        MyLog.e("request===" + request);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), request);
        Subscription subscribe = kunmingApi.getTagInfoList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetTagInfoListReturn>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(GetTagInfoListReturn getTagInfoListReturn) {
                        callback.onSuccess(getTagInfoListReturn);
                    }
                });
        return subscribe;
    }

    /**
     * 得到用户对应的库房
     *
     * @param callback
     */
    public Subscription getWarehouse(Integer userId, final IRequestCallback<GetWarehouseReturn> callback) {
        MyLog.e("request===" + userId);
        Subscription subscribe = kunmingApi.getWarehouse(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetWarehouseReturn>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(GetWarehouseReturn getWarehouseReturn) {
                        callback.onSuccess(getWarehouseReturn);
                    }
                });
        return subscribe;
    }

    /**
     * 得到用户对应的洗涤厂
     *
     * @param userId
     * @param callback
     * @return
     */
    public Subscription getWashFactory(Integer userId, final IRequestCallback<GetWarehouseReturn> callback) {

        Subscription subscribe = kunmingApi.getWashFactory(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetWashFactoryReturn>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(GetWashFactoryReturn getWashFactoryReturn) {
                        callback.onSuccess(getWashFactoryReturn);
                    }
                });
        return subscribe;

    }

    /**
     * 登录
     *
     * @param request  LoginRequest对应的json
     * @param callback
     */
    public Subscription login(String request, final IRequestCallback<LoginReturn> callback) {
        MyLog.e("request===" + request);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), request);
        MyLog.e("body===" + body);
        Subscription subscribe = kunmingApi.login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginReturn>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onNext(LoginReturn loginReturn) {
                        callback.onSuccess(loginReturn);
                    }
                });
        return subscribe;
    }


}
