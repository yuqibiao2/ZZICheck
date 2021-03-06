
package com.afrid.icheck.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afrid.icheck.MyApplication;
import com.afrid.icheck.R;
import com.afrid.icheck.bean.json.GetTagInfoRequest;
import com.afrid.swingu.utils.SwingUManager;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MyToast;
import com.yyyu.baselibrary.view.WhewView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;

/**
 * 布草扫描界面
 */
public class ScanLinenActivity extends MyBaseActivity {

    private static final String TAG = "ScanLinenActivity";

    @BindView(R.id.tv_warehouse)
    TextView tv_warehouse;
    @BindView(R.id.tb_scan)
    Toolbar tb_scan;
    @BindView(R.id.wv_scan)
    WhewView wvScan;

    //存储标签id的set
    private HashSet<String> tagIdSet = new HashSet<>();
    //存储标签id的list
    private List<String> tagIdList;
    private SwingUManager swingUManager;
    private String officeName;
    private MyApplication application;

    @Override
    public void beforeInit() {
        super.beforeInit();
        swingUManager = SwingUManager.getInstance(this);
        application = (MyApplication) getApplication().getApplicationContext();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_linen;
    }

    @Override
    protected void initView() {
        /*setSupportActionBar(tb_scan);
        getSupportActionBar().setTitle(resourceUtils.getStr(R.string.scan_tb_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

        Intent intent = getIntent();
        officeName = application.getCheckOfficeName();//intent.getStringExtra("officeName");
        tv_warehouse.setText(officeName);
    }

    @Override
    protected void initListener() {
        swingUManager.setOnReadResultListener(new SwingUManager.OnReadResultListener() {
            @Override
            public void onRead(String tagId) {
                boolean isAdd = tagIdSet.add(tagId.substring(4));
                if (isAdd) {
                    MyLog.e(TAG, tagId.substring(4) + "   size" + tagIdSet.size());
                }
            }
        });
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

    /**
     * 开始扫描
     */
    public void startScan(View view) {
        tagIdSet.clear();
        //---判断设备是否为用户绑定的
     /*   if (!application.getReaderIdList().contains(application.getCurrentReaderId())) {
            MyToast.showShort(this, resourceUtils.getStr(R.string.swing_connect_yourself));
            BTDeviceScanActivity.startAction(this);
            return;
        }*/

        if (!SwingUManager.getInstance(this).isConnected()) {
            MyToast.showShort(this, resourceUtils.getStr(R.string.swing_disconnect));
            BTDeviceScanActivity.startAction(this);
            return;
        }
        wvScan.start();
        swingUManager.resetReader();
        swingUManager.startReader();
    }

    /**
     * 结束扫描
     */
    public void stopScan(View view) {

        //TODO 删除
/*
     tagIdSet.add("E280117020001295464A0912");
        tagIdSet.add("E2806890200000001F135630");
        tagIdSet.add("E2806890200000001F11AE5A");
        tagIdSet.add("E2806890200000001F1257AE");
        tagIdSet.add("E2806890200000001F11C00D");
        tagIdSet.add("E2806810200000040D0174F7");
        tagIdSet.add("E2801170200002F046560912");
*/

        wvScan.stop();
        swingUManager.stopReader();
        tagIdList = new ArrayList<>(tagIdSet);
        GetTagInfoRequest request = new GetTagInfoRequest();
        GetTagInfoRequest.TagInfoRequestBean requestBean = new GetTagInfoRequest.TagInfoRequestBean();
        requestBean.setTagList(tagIdList);
        request.setRequestData(requestBean);
        ReceiptActivity.startActionForResult(this, mGson.toJson(request), officeName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ReceiptActivity.RECEIPT_REQUEST_CODE
                && resultCode == ReceiptActivity.RECEIPT_RESULT_CODE) {
            //正常的提交receipt后清空数据
            tagIdSet.clear();
            tagIdList.clear();
        }
    }

    public void back(View view){
        finish();
    }

    /**
     * 页面跳转
     *
     * @param context
     * @param officeName 科室
     */
    public static void startAction(Context context, String officeName) {
        Intent intent = new Intent(context, ScanLinenActivity.class);
        intent.putExtra("officeName", officeName);
        context.startActivity(intent);
    }

}
