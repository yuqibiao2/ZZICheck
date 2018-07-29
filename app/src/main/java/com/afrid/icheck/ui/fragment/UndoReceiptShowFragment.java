package com.afrid.icheck.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afrid.icheck.R;
import com.afrid.icheck.adapter.UndoReceiptAdapter;
import com.afrid.icheck.bean.json.GetUndoReceiptRequest;
import com.afrid.icheck.bean.json.return_data.GetUndoReceiptReturn;
import com.afrid.icheck.net.APIMethodManager;
import com.afrid.icheck.net.IRequestCallback;
import com.afrid.icheck.ui.activity.MyBaseActivity;
import com.yyyu.baselibrary.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能：未发净订单显示
 *
 * @author yyyu
 * @version 1.0
 * @date 2018/7/28
 */
public class UndoReceiptShowFragment extends MyBaseFragment {

    @BindView(R.id.rv_undo_receipt)
    RecyclerView rvUndoReceipt;
    @BindView(R.id.tv_empty_tip)
    TextView tvEmptyTip;

    private List<GetUndoReceiptReturn.ResultDataBean> mData = new ArrayList<>();
    private APIMethodManager apiMethodManager;
    private GetUndoReceiptRequest undoReceiptRequest;
    private GetUndoReceiptRequest.RequestDataBean requestDataBean;
    private UndoReceiptAdapter undoReceiptAdapter;

    @Override
    public void beforeInit() {
        super.beforeInit();
        apiMethodManager = APIMethodManager.getInstance();
        undoReceiptRequest = new GetUndoReceiptRequest();
        requestDataBean = new GetUndoReceiptRequest.RequestDataBean();
        //requestDataBean.setLimitDate("180628");//该时间之前
        if (getArguments() != null) {
            String limitDate = getArguments().getString("limitDate");
            String afterDate = getArguments().getString("afterDate");
            requestDataBean.setLimitDate(limitDate);//该时间之前
            requestDataBean.setAfterDate(afterDate);
        }
        MyBaseActivity activity = (MyBaseActivity)getActivity();
        switch (activity.type){
            case LINEN:
                requestDataBean.setWashType(1);//布草
                break;
            case UNIFORM:
                requestDataBean.setWashType(2);//工服
                break;
        }
        undoReceiptRequest.setRequestData(requestDataBean);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_undo_receipt_show;
    }

    @Override
    protected void initView() {
        rvUndoReceipt.setLayoutManager(new LinearLayoutManager(getActivity()));
        undoReceiptAdapter = new UndoReceiptAdapter(getActivity() , mData);
        rvUndoReceipt.setAdapter(undoReceiptAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onResume() {
        super.onResume();
        showLoadDialog("数据加载中...");
        apiMethodManager.getUndoReceipt(undoReceiptRequest, new IRequestCallback<GetUndoReceiptReturn>() {
            @Override
            public void onSuccess(GetUndoReceiptReturn result) {
                int resultCode = result.getResultCode();
                if (resultCode==200){
                    List<GetUndoReceiptReturn.ResultDataBean> resultData = result.getResultData();
                    if (resultData.size()==0){
                        tvEmptyTip.setVisibility(View.VISIBLE);
                    }
                    mData.clear();
                    mData.addAll(resultData);
                    undoReceiptAdapter.notifyDataSetChanged();
                }else{
                    MyToast.showLong(getContext() , "异常："+result.getMsg());
                }
                hiddenLoadingDialog();
            }

            @Override
            public void onFailure(Throwable throwable) {
                hiddenLoadingDialog();
            }

        });
    }

    public static UndoReceiptShowFragment newInstance(String limitDate , String afterDate) {
        UndoReceiptShowFragment fragment = new UndoReceiptShowFragment();
        Bundle args = new Bundle();
        args.putString("limitDate", limitDate);
        args.putString("afterDate", afterDate);
        fragment.setArguments(args);
        return fragment;
    }

}
