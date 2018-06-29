package com.afrid.icheck.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afid.utils.ZKCManager;
import com.afrid.icheck.MyApplication;
import com.afrid.icheck.R;
import com.afrid.icheck.adapter.LinenAdapter;
import com.afrid.icheck.adapter.UniformAdapter;
import com.afrid.icheck.bean.EmpUniform;
import com.afrid.icheck.bean.SubReceiptListBean;
import com.afrid.icheck.bean.Uniform;
import com.afrid.icheck.bean.json.BaseJsonRequest;
import com.afrid.icheck.bean.json.BaseJsonResult;
import com.afrid.icheck.bean.json.GetTagInfoRequest;
import com.afrid.icheck.bean.json.SaveReceiptRequest;
import com.afrid.icheck.bean.json.return_data.GetEmpUniformReturn;
import com.afrid.icheck.bean.json.return_data.GetTagInfoListReturn;
import com.afrid.icheck.net.APIMethodManager;
import com.afrid.icheck.net.IRequestCallback;
import com.afrid.icheck.utils.PrintStrBuildUtils;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;

/**
 * 功能：收据生成界面
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/13
 */

public class ReceiptActivity extends MyBaseActivity {

    private static final String TAG = "ReceiptActivity";
    public static final int RECEIPT_REQUEST_CODE = 1001;
    public static final int RECEIPT_RESULT_CODE = 1002;

    @BindView(R.id.tv_useless_tag)
    TextView tv_useless_tag;
    @BindView(R.id.tb_receipt)
    Toolbar tb_receipt;
    @BindView(R.id.rv_office)
    RecyclerView rv_office;
    @BindView(R.id.btn_submit)
    Button btn_submit;


    private APIMethodManager apiMethodManager;
    private LinenAdapter adapter;
    private String getTagInfoRequest;
    private MyApplication application;
    private GetTagInfoListReturn.ResultDataBean resultData;
    private ZKCManager zkcManager;
    private String officeName;
    private Subscription tagInfoListSubscription;
    private Subscription saveReceiptSubscription;
    private Integer hospitalId;
    private Integer officeId;
    private UniformAdapter uniformAdapter;
    private HashMap<String, List<Uniform>> conMap;
    private String checkHospitalName;



    @Override
    public void beforeInit() {
        super.beforeInit();
        zkcManager = ZKCManager.getInstance(this);
        zkcManager.bindService();
        apiMethodManager = APIMethodManager.getInstance();
        application = (MyApplication) getApplication();
    }

    @Override
    public int getLayoutId() {
        getTagInfoRequest = getIntent().getStringExtra("getTagInfoRequest");
        officeName = getIntent().getStringExtra("officeName");
        hospitalId = ((MyApplication) getApplication()).getCheckHospitalId();
        checkHospitalName = ((MyApplication) getApplication()).getCheckHospitalName();
        officeId = ((MyApplication) getApplication()).getCheckOfficeId();
        return R.layout.activity_receipt;
    }

    @Override
    protected void initView() {
        //setSupportActionBar(tb_receipt);
       /* getSupportActionBar().setTitle(R.string.receipt_tb_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/
        rv_office.setLayoutManager(new LinearLayoutManager(this));
        switch (type) {
            case COMMON:
                adapter = new LinenAdapter(this);
                rv_office.setAdapter(adapter);
                break;

            case UNIFORM:
                uniformAdapter = new UniformAdapter(this);
                rv_office.setAdapter(uniformAdapter);
                break;
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        super.initData();
        showLoadDialog(resourceUtils.getStr(R.string.data_loading));
        switch (type) {
            case COMMON:
                tagInfoListSubscription = apiMethodManager.getTagInfoList(getTagInfoRequest, new IRequestCallback<GetTagInfoListReturn>() {
                    @Override
                    public void onSuccess(GetTagInfoListReturn result) {
                        int resultCode = result.getResultCode();
                        if (resultCode == 200) {
                            resultData = result.getResultData();
                            adapter.setmData(result.getResultData().getMIxTagLinenList());
                            tv_useless_tag.setText("" + result.getResultData().getUselessTag().getTagNum());
                        } else if (resultCode == 500) {

                        }
                        hiddenLoadingDialog();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        MyLog.e(TAG, "异常==" + throwable.getMessage());
                    }
                });
                break;
            case UNIFORM:
                GetTagInfoRequest infoRequest = mGson.fromJson(this.getTagInfoRequest, GetTagInfoRequest.class);
                List<String> tagList = infoRequest.getRequestData().getTagList();
                final BaseJsonRequest<List<String>> request = new BaseJsonRequest<>();
                request.setRequestData(tagList);
                tagInfoListSubscription = apiMethodManager.getTagInfoList(getTagInfoRequest, new IRequestCallback<GetTagInfoListReturn>() {
                    @Override
                    public void onSuccess(GetTagInfoListReturn result) {
                        int resultCode = result.getResultCode();
                        if (resultCode == 200) {
                            resultData = result.getResultData();
                            tv_useless_tag.setText("" + result.getResultData().getUselessTag().getTagNum());
                            getEmpUniform(request);
                        } else if (resultCode == 500) {

                        }
                        //hiddenLoadingDialog();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        MyLog.e(TAG, "异常==" + throwable.getMessage());
                    }
                });
                break;
        }

    }

    private void getEmpUniform(BaseJsonRequest<List<String>> request) {
        apiMethodManager.getetEmpUniformList(mGson.toJson(request), new IRequestCallback<GetEmpUniformReturn>() {
            @Override
            public void onSuccess(GetEmpUniformReturn result) {
                int resultCode = result.getResultCode();
                if (resultCode == 200) {
                    List<GetEmpUniformReturn.ResultDataBean> resultData = result.getResultData();

                    conMap = new HashMap<>();

                    for (GetEmpUniformReturn.ResultDataBean bean : resultData) {
                        String empName;
                        int officeId1 = bean.getOfficeId();
                        int officeId2 = officeId;
                        if (officeId1!=officeId2){
                            String officeName = bean.getOfficeName();
                            empName = bean.getEmpName()+"("+officeName+")";
                            bean.setEmpName(empName);
                        }else{
                            empName = bean.getEmpName();
                        }
                        String empNo = bean.getEmpNo();
                        String tagId = bean.getTagId();
                        String uniformTypeName = bean.getUniformTypeName();
                        if (conMap.containsKey(empName)) {//已经存在
                            List<Uniform> uniforms = conMap.get(empName);
                            List<Uniform> newUniformList = new ArrayList<Uniform>();

                            Uniform uniform = existEmpName(uniforms, uniformTypeName);
                            if(uniform!=null){//相同的员工名存在相同的名称
                                Integer num = uniform.getNum();
                                uniform.setNum(++num);
                            }else{
                                Uniform uniform2 = new Uniform();
                                uniform2.setName(uniformTypeName);
                                uniform2.setNum(1);
                                uniforms.add(uniform2);
                            }
                            uniforms.addAll(newUniformList);
                        } else {
                            List<Uniform> infoList = new ArrayList();
                            Uniform uniform = new Uniform();
                            uniform.setName(uniformTypeName);
                            uniform.setNum(1);
                            infoList.add(uniform);
                            conMap.put(empName, infoList);
                        }

                    }

                    List<EmpUniform> empUniformList = new ArrayList<EmpUniform>();
                    for (String empName : conMap.keySet()) {
                        EmpUniform empUniform = new EmpUniform();
                        List<Uniform> uniforms = conMap.get(empName);
                        empUniform.setEmpName(empName);
                        StringBuilder sbInfo = new StringBuilder();
                        for (Uniform uniform : uniforms) {
                            sbInfo.append(uniform.getName());
                            for (int i=10 ; i>uniform.getName().length() ; i--){
                                sbInfo.append("　");
                            }
                            sbInfo.append("x " + uniform.getNum());
                            sbInfo.append("\r\n");
                        }
                        empUniform.setEmpInfo(sbInfo.toString());
                        empUniformList.add(empUniform);
                    }
                    uniformAdapter.setmData(empUniformList);
                } else if (resultCode == 500) {

                }
                hiddenLoadingDialog();
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                MyLog.e(TAG, "异常==" + throwable.getMessage());
            }
        });
    }

    public Uniform existEmpName(List<Uniform> uniforms , String  uniformTypeName){
        for (Uniform uniform : uniforms) {
            if (uniformTypeName.equals(uniform.getName())) {//相同的员工名存在相同的名称
                return uniform;
            }
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //---TODO stop操作
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void back(View view) {
        finish();
    }

    /**
     * 保存收据
     *
     * @param view
     */
    public void saveReceipt(View view) {
        btn_submit.setEnabled(false);
        WashFactoryChoiceActivity.startActionForResult(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WashFactoryChoiceActivity.REQUEST_WASH_FACTORY && resultCode == RESULT_OK) {
            int washFactoryId = data.getIntExtra("washFactoryId", 0);
            final String washFactoryName = data.getStringExtra("washFactoryName");
            showLoadDialog("订单提交中.....");
            SaveReceiptRequest saveReceiptRequest = new SaveReceiptRequest();
            SaveReceiptRequest.RequestDataBean requestDataBean = new SaveReceiptRequest.RequestDataBean();
            requestDataBean.setUserId(application.getUser_id());
            requestDataBean.setReaderId(application.getCurrentReaderId());
            requestDataBean.setLinenNum(resultData.getVaildTagNum());//设置有效标签总数
            requestDataBean.setReceiptWarehouseId(hospitalId);//医院ID
            requestDataBean.setSenderWarehouseId(washFactoryId);//库房ID
            requestDataBean.setOfficeId(officeId + "");//科室ID
            requestDataBean.setOrderType(1);//收脏
            switch (type){
                case COMMON:
                    requestDataBean.setWashType(1);
                    break;
                case UNIFORM:
                    requestDataBean.setWashType(2);
                    break;
            }
            requestDataBean.setSubReceiptList(resultData.getMIxTagLinenList());
            saveReceiptRequest.setRequestData(requestDataBean);

            String request = mGson.toJson(saveReceiptRequest);
            saveReceiptSubscription = apiMethodManager.saveReceipt(request, new IRequestCallback<BaseJsonResult<String>>() {
                @Override
                public void onSuccess(BaseJsonResult<String> result) {
                    int resultCode = result.getResultCode();
                    String msg = result.getMsg();
                    if (resultCode == 200) {
                        final String mainId = result.getResultData();
                        AlertDialog alertDialog = new AlertDialog.Builder(ReceiptActivity.this)
                                .setCancelable(false)
                                .setTitle(resourceUtils.getStr(R.string.receipt_alter_title) + "\r\n")
                                .setMessage(resourceUtils.getStr(R.string.receipt_alter_msg))
                                .setNegativeButton(resourceUtils.getStr(R.string.receipt_alter_nev), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        setResult(RECEIPT_RESULT_CODE);
                                        finish();
                                    }
                                })
                                .setPositiveButton(resourceUtils.getStr(R.string.receipt_alter_pos), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (type){
                                            case COMMON:{
                                                String printStr = PrintStrBuildUtils.buildReceipt(ReceiptActivity.this, true, application.getUser_name(),
                                                        officeName, washFactoryName, mainId, ReceiptActivity.this.resultData.getMIxTagLinenList());
                                                zkcManager.getPrintManager().printText(printStr);
                                                break;
                                            }
                                            case UNIFORM:{
                                                String printStr = PrintStrBuildUtils.buildReceipt2(ReceiptActivity.this, true, application.getUser_name(),
                                                        checkHospitalName, washFactoryName, officeName,mainId, conMap);
                                                zkcManager.getPrintManager().printText(printStr);
                                                break;
                                            }
                                        }
                                    }
                                })
                                .create();
                        alertDialog.show();
                        switch (type){
                            case COMMON:{
                                String printStr = PrintStrBuildUtils.buildReceipt(ReceiptActivity.this, false, application.getUser_name(),
                                        officeName, washFactoryName, mainId, ReceiptActivity.this.resultData.getMIxTagLinenList());
                                zkcManager.getPrintManager().printText(printStr);
                                break;
                            }
                            case UNIFORM:{
                                String printStr = PrintStrBuildUtils.buildReceipt2(ReceiptActivity.this, false, application.getUser_name(),
                                        checkHospitalName, washFactoryName, officeName,mainId, conMap);
                                zkcManager.getPrintManager().printText(printStr);
                                break;
                            }
                        }
                    } else {
                        btn_submit.setEnabled(true);
                        MyToast.showShort(ReceiptActivity.this, resourceUtils.getStr(R.string.receipt_submit_failed));
                    }
                    hiddenLoadingDialog();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    hiddenLoadingDialog();
                    MyLog.e(TAG, throwable.getMessage());
                    btn_submit.setEnabled(true);
                    MyToast.showShort(ReceiptActivity.this, resourceUtils.getStr(R.string.net_error));
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        zkcManager.unbindService();
        if (tagInfoListSubscription != null && !tagInfoListSubscription.isUnsubscribed()) {
            tagInfoListSubscription.unsubscribe();
        }
        if (saveReceiptSubscription != null && !saveReceiptSubscription.isUnsubscribed()) {
            saveReceiptSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    /**
     * 跳转界面
     *
     * @param activity
     * @param getTagInfoRequest GetTagInfoRequest转化的json
     */
    public static void startAction(Activity activity, String getTagInfoRequest, String warehouseName) {
        Intent intent = new Intent(activity, ReceiptActivity.class);
        intent.putExtra("getTagInfoRequest", getTagInfoRequest);
        intent.putExtra("warehouseName", warehouseName);
        activity.startActivity(intent);
    }

    /**
     * startActionForResult
     *
     * @param activity
     * @param getTagInfoRequest
     * @param officeName
     */
    public static void startActionForResult(Activity activity, String getTagInfoRequest, String officeName) {
        Intent intent = new Intent(activity, ReceiptActivity.class);
        intent.putExtra("getTagInfoRequest", getTagInfoRequest);
        intent.putExtra("officeName", officeName);
        activity.startActivityForResult(intent, RECEIPT_REQUEST_CODE);
    }

}

