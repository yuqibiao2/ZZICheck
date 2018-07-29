package com.afrid.icheck.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afrid.icheck.R;
import com.afrid.icheck.bean.json.return_data.GetUndoReceiptReturn;
import com.afrid.icheck.global.Constant;
import com.afrid.icheck.ui.activity.ScanLinenActivity;
import com.yyyu.baselibrary.utils.MyTimeUtils;
import com.yyyu.baselibrary.utils.MyToast;

import java.util.List;

/**
 * 功能：
 *
 * @author yyyu
 * @version 1.0
 * @date 2018/7/28
 */
public class UndoReceiptAdapter extends RecyclerView.Adapter<UndoReceiptAdapter.UndoReceiptViewHolder> {

    private Context mContext;
    private List<GetUndoReceiptReturn.ResultDataBean> mData;
    private final Application application;

    public UndoReceiptAdapter(Context context , List<GetUndoReceiptReturn.ResultDataBean> data) {

        this.mContext = context;
        this.mData = data;
        application = ((Activity) mContext).getApplication();

    }

    @Override
    public UndoReceiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.rv_item_undo_receipt, parent, false);
        return new UndoReceiptViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(UndoReceiptViewHolder holder, int position) {
        StringBuffer sbContent = new StringBuffer();
        final GetUndoReceiptReturn.ResultDataBean resultDataBean = mData.get(position);
        sbContent.append("订单编号："+resultDataBean.getMainId()+"\r\n");
        final String createTime = MyTimeUtils.formatDateDT(resultDataBean.getCreateTime());
        sbContent.append("创建时间："+createTime+"\r\n");
        List<GetUndoReceiptReturn.ResultDataBean.SubReceiptListBean> subReceiptList = resultDataBean.getSubReceiptList();
        for (GetUndoReceiptReturn.ResultDataBean.SubReceiptListBean subReceiptListBean :subReceiptList){
            String linenTypeName = subReceiptListBean.getLinenTypeName();
            int linenCount = subReceiptListBean.getLinenCount();
            sbContent.append("          "+linenTypeName+":                   x "+linenCount+"\r\n");
        }
        holder.tvContent.setText(""+sbContent.toString());
        holder.btnToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.showLong(mContext , "补发："+createTime+" 时间的订单");
                Constant.checkedUndoReceipt = resultDataBean;
                ScanLinenActivity.startAction(mContext, "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static  class UndoReceiptViewHolder extends RecyclerView.ViewHolder{

        public TextView tvContent;
        public Button btnToSend;

        public UndoReceiptViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            btnToSend = (Button) itemView.findViewById(R.id.btn_to_send);
        }


    }

}
