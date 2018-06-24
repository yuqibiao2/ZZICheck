package com.afrid.icheck.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.afrid.icheck.MyApplication;
import com.afrid.icheck.R;
import com.afrid.icheck.adapter.OfficeAdapter;
import com.afrid.icheck.bean.json.return_data.GetHospitalReturn;
import com.afrid.icheck.bean.json.return_data.GetOfficeReturn;
import com.afrid.icheck.net.APIMethodManager;
import com.afrid.icheck.net.IRequestCallback;
import com.afrid.icheck.ui.activity.BTDeviceScanActivity;
import com.afrid.icheck.ui.activity.MyBaseActivity;
import com.afrid.icheck.ui.activity.ScanLinenActivity;
import com.afrid.swingu.utils.SwingUManager;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MyToast;
import com.yyyu.baselibrary.view.recyclerview.listener.OnRvItemClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;

/**
 * 功能：库房选择
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class MainFragment extends MyBaseFragment {

    private static final String TAG = "MainFragment";

    @BindView(R.id.rv_office)
    RecyclerView rv_office;

    @BindView(R.id.sp_hos_choice)
    Spinner sp_hos_choice;

    private APIMethodManager apiMethodManager;
    private OfficeAdapter warehouseAdapter;
    private List<GetHospitalReturn.ResultDataBean> resultData;
    private MyApplication application;
    private Subscription subscription;
    private List<GetOfficeReturn.ResultDataBean> officeList;
    private MyBaseActivity.Type type;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override
    protected void beforeInit() {
        super.beforeInit();
        application = (MyApplication) getActivity().getApplication();
        apiMethodManager = APIMethodManager.getInstance();
        type = ((MyBaseActivity) getActivity()).type;
    }

    @Override
    protected void initView() {
        rv_office.setLayoutManager(new GridLayoutManager(getContext(), 3));
        warehouseAdapter = new OfficeAdapter(getContext());
        rv_office.setAdapter(warehouseAdapter);
    }

    @Override
    protected void initListener() {

        sp_hos_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                application.setCheckHospitalId(resultData.get(i).getHospitalId());
                application.setCheckHospitalName(resultData.get(i).getHospitalName());
                showLoadDialog("加载科室信息..");
                //刷新rv数据
                GetHospitalReturn.ResultDataBean hospital = resultData.get(i);
                initOfficeDataData(hospital);
               /* apiMethodManager.getOfficeByHospitalId(hospital.getHospitalId(), new IRequestCallback<GetOfficeReturn>() {
                    @Override
                    public void onSuccess(GetOfficeReturn result) {
                        MyLog.e(TAG, "result===" + result.getMsg());
                        int resultCode = result.getResultCode();
                        officeList = result.getResultData();
                        if (resultCode == 200) {
                            warehouseAdapter.setDataBeanList(officeList);
                        } else if (resultCode == 500) {
                            MyToast.show(getContext(), result.getMsg(), Toast.LENGTH_SHORT);
                        }
                        hiddenLoadingDialog();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hiddenLoadingDialog();
                    }
                });*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rv_office.addOnItemTouchListener(new OnRvItemClickListener(rv_office) {
            @Override
            public void onItemClick(View itemView, int position) {
              /*  if (!SwingUManager.getInstance(getContext()).isConnected()) {
                    MyToast.showShort(getContext(), resourceUtils.getStr(R.string.swing_disconnect));
                    BTDeviceScanActivity.startAction(getActivity());
                    return;
                }*/
                application.setCheckOfficeId(officeList.get(position).getOfficeId());
                ScanLinenActivity.startAction(getActivity(), officeList.get(position).getOfficeName());
            }

            @Override
            public void onItemLongClick(View itemView, int position) {

            }
        });
    }

    @Override
    protected void initData() {
        showLoadDialog(resourceUtils.getStr(R.string.data_loading));
        super.initData();
        initHospitalData();
        /*subscription = apiMethodManager.getHospitalByUserId(application.getUser_id(), new IRequestCallback<GetHospitalReturn>() {
            @Override
            public void onSuccess(GetHospitalReturn result) {
                MyLog.e(TAG, "result===" + result.getMsg());
                int resultCode = result.getResultCode();
                if (resultCode == 200) {
                    resultData = result.getResultData();
                    List<String> hospitalNameList = new ArrayList<>();
                    for (GetHospitalReturn.ResultDataBean bean :resultData){
                        hospitalNameList.add(bean.getHospitalName());
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item , hospitalNameList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_hos_choice.setAdapter(adapter);
                    if (resultData.size()>0){
                        sp_hos_choice.setSelection(0);
                    }
                } else if (resultCode == 500) {
                    MyToast.show(getContext(), result.getMsg(), Toast.LENGTH_SHORT);
                }
                hiddenLoadingDialog();
            }

            @Override
            public void onFailure(Throwable throwable) {
                hiddenLoadingDialog();
                MyToast.show(getContext(), resourceUtils.getStr(R.string.net_error), Toast.LENGTH_SHORT);
                throwable.printStackTrace();
            }
        });*/
    }


    private void initHospitalData(){
        switch (type){
            case UNIFORM:{
                subscription = apiMethodManager.getEmpHospital(new IRequestCallback<GetHospitalReturn>() {
                    @Override
                    public void onSuccess(GetHospitalReturn result) {
                        MyLog.e(TAG, "result===" + result.getMsg());
                        int resultCode = result.getResultCode();
                        if (resultCode == 200) {
                            resultData = result.getResultData();
                            List<String> hospitalNameList = new ArrayList<>();
                            for (GetHospitalReturn.ResultDataBean bean :resultData){
                                hospitalNameList.add(bean.getHospitalName());
                            }
                            ArrayAdapter<String> adapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item , hospitalNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_hos_choice.setAdapter(adapter);
                            if (resultData.size()>0){
                                sp_hos_choice.setSelection(0);
                            }
                        } else if (resultCode == 500) {
                            MyToast.show(getContext(), result.getMsg(), Toast.LENGTH_SHORT);
                        }
                        hiddenLoadingDialog();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hiddenLoadingDialog();
                        MyToast.show(getContext(), resourceUtils.getStr(R.string.net_error), Toast.LENGTH_SHORT);
                        throwable.printStackTrace();
                    }
                });
                break;
            }
            case COMMON:{
                subscription = apiMethodManager.getHospitalByUserId(application.getUser_id(), new IRequestCallback<GetHospitalReturn>() {
                    @Override
                    public void onSuccess(GetHospitalReturn result) {
                        MyLog.e(TAG, "result===" + result.getMsg());
                        int resultCode = result.getResultCode();
                        if (resultCode == 200) {
                            resultData = result.getResultData();
                            List<String> hospitalNameList = new ArrayList<>();
                            for (GetHospitalReturn.ResultDataBean bean :resultData){
                                hospitalNameList.add(bean.getHospitalName());
                            }
                            ArrayAdapter<String> adapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item , hospitalNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_hos_choice.setAdapter(adapter);
                            if (resultData.size()>0){
                                sp_hos_choice.setSelection(0);
                            }
                        } else if (resultCode == 500) {
                            MyToast.show(getContext(), result.getMsg(), Toast.LENGTH_SHORT);
                        }
                        hiddenLoadingDialog();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hiddenLoadingDialog();
                        MyToast.show(getContext(), resourceUtils.getStr(R.string.net_error), Toast.LENGTH_SHORT);
                        throwable.printStackTrace();
                    }
                });
                break;
            }
        }
    }


    private void initOfficeDataData(GetHospitalReturn.ResultDataBean hospital){
        switch (type){
            case UNIFORM:{
                apiMethodManager.getEmpOfficeByHospitalNo(hospital.getHospitalNo(), new IRequestCallback<GetOfficeReturn>() {
                    @Override
                    public void onSuccess(GetOfficeReturn result) {
                        MyLog.e(TAG, "result===" + result.getMsg());
                        int resultCode = result.getResultCode();
                        officeList = result.getResultData();
                        if (resultCode == 200) {
                            warehouseAdapter.setDataBeanList(officeList);
                        } else if (resultCode == 500) {
                            MyToast.show(getContext(), result.getMsg(), Toast.LENGTH_SHORT);
                        }
                        hiddenLoadingDialog();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hiddenLoadingDialog();
                    }
                });
                break;
            }
            case COMMON:{
                apiMethodManager.getOfficeByHospitalId(hospital.getHospitalId(), new IRequestCallback<GetOfficeReturn>() {
                    @Override
                    public void onSuccess(GetOfficeReturn result) {
                        MyLog.e(TAG, "result===" + result.getMsg());
                        int resultCode = result.getResultCode();
                        officeList = result.getResultData();
                        if (resultCode == 200) {
                            warehouseAdapter.setDataBeanList(officeList);
                        } else if (resultCode == 500) {
                            MyToast.show(getContext(), result.getMsg(), Toast.LENGTH_SHORT);
                        }
                        hiddenLoadingDialog();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        hiddenLoadingDialog();
                    }
                });
                break;
            }
        }
    }



    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
