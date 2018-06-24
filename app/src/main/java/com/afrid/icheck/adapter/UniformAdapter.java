package com.afrid.icheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrid.icheck.R;
import com.afrid.icheck.bean.EmpUniform;
import com.afrid.icheck.bean.SubReceiptListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：扫描后得条目对应得Adapter
 *
 * @author yu
 * @version 1.0
 * @date 2017/6/27
 */

public class UniformAdapter extends RecyclerView.Adapter<UniformAdapter.UniformViewHolder>{

    private Context mContext;

    private List<EmpUniform>  mData;


    public UniformAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public UniformViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.rv_item_uniform ,parent , false);
        UniformViewHolder viewHolder   = new UniformViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UniformViewHolder holder, final int position) {
        holder.getTv_name().setText(""+mData.get(position).getEmpName());
        holder.getTv_num().setText(""+mData.get(position).getEmpInfo());
    }

    @Override
    public int getItemCount() {
        return mData==null ? 0 :mData.size();
    }

    public void setmData(List<EmpUniform> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public static class UniformViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;
        private TextView tv_num;

        public UniformViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
        }

        public TextView getTv_name() {
            return tv_name;
        }

        public void setTv_name(TextView tv_name) {
            this.tv_name = tv_name;
        }

        public TextView getTv_num() {
            return tv_num;
        }

        public void setTv_num(TextView tv_num) {
            this.tv_num = tv_num;
        }

    }
}
