package com.afrid.icheck.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.afrid.icheck.MyApplication;
import com.afrid.icheck.R;
import com.afrid.icheck.adapter.OfficeAdapter;
import com.afrid.icheck.adapter.WarehouseAdapter;
import com.afrid.icheck.bean.json.return_data.GetWarehouseReturn;
import com.afrid.icheck.net.APIMethodManager;
import com.afrid.icheck.net.IRequestCallback;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MyToast;
import com.yyyu.baselibrary.view.recyclerview.listener.OnRvItemClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/20
 */

public class WashFactoryChoiceActivity extends  MyBaseActivity{

    private static final String TAG = "WashFactoryChoiceActivi";
    public static final int REQUEST_WASH_FACTORY = 101;

    @BindView(R.id.rv_wash_factory)
    RecyclerView rv_wash_factory;

    private APIMethodManager apiMethodManager;
    private WarehouseAdapter warehouseAdapter;
    private List<GetWarehouseReturn.ResultDataBean> resultData;
    private MyApplication application;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_wash_factory_choice;
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        apiMethodManager = APIMethodManager.getInstance();
        application = (MyApplication) getApplication();
    }

    @Override
    protected void initView() {
        rv_wash_factory.setLayoutManager(new GridLayoutManager(this ,1));
        warehouseAdapter = new WarehouseAdapter(this);
        rv_wash_factory.setAdapter(warehouseAdapter);
    }

    @Override
    protected void initListener() {
        rv_wash_factory.addOnItemTouchListener(new OnRvItemClickListener(rv_wash_factory) {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent();
                intent.putExtra("washFactoryId" , resultData.get(position).getWarehouseId());
                intent.putExtra("washFactoryName" , resultData.get(position).getWarehouseName());
                setResult(RESULT_OK , intent);
                finish();
            }

            @Override
            public void onItemLongClick(View itemView, int position) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showLoadDialog(resourceUtils.getStr(R.string.data_loading));
        apiMethodManager.getWarehouse(application.getUser_id(), new IRequestCallback<GetWarehouseReturn>() {
            @Override
            public void onSuccess(GetWarehouseReturn result) {
                MyLog.e(TAG, "result===" + result.getMsg());
                int resultCode = result.getResultCode();
                if (resultCode == 200) {
                    resultData = result.getResultData();
                    warehouseAdapter.setDataBeanList(resultData);
                } else if (resultCode == 500) {
                    MyToast.show(WashFactoryChoiceActivity.this, result.getMsg(), Toast.LENGTH_SHORT);
                }
                hiddenLoadingDialog();
            }

            @Override
            public void onFailure(Throwable throwable) {
                MyToast.showShort(WashFactoryChoiceActivity.this , resourceUtils.getStr(R.string.net_error));
                hiddenLoadingDialog();
            }
        });
    }

    public static void startActionForResult(Activity activity){
        Intent intent = new Intent(activity , WashFactoryChoiceActivity.class);
        activity.startActivityForResult(intent , REQUEST_WASH_FACTORY);
    }

}
