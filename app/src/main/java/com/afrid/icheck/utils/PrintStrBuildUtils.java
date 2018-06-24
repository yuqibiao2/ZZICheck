package com.afrid.icheck.utils;

import android.content.Context;
import android.content.res.Resources;

import com.afrid.icheck.R;
import com.afrid.icheck.bean.SubReceiptListBean;
import com.afrid.icheck.bean.Uniform;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * 功能：创建打印内容
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/13
 */

public class PrintStrBuildUtils {

    /**
     * 生成收据
     *
     * @return
     */
    public static String buildReceipt2(Context context , boolean isRepeat,
                                       String username,
                                       String hospitalName ,
                                       String warehouse,
                                       String officeName,
                                       String receiptId
            , HashMap<String, List<Uniform>> conMap) {

        Resources resources = context.getResources();

        StringBuilder sb = new StringBuilder();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");
        DATE_FORMAT.setTimeZone(TIME_ZONE);
        String date = DATE_FORMAT.format(Calendar.getInstance(TIME_ZONE).getTime());
        sb.append("\n");
        if (!isRepeat){
            sb.append(resources.getString(R.string.receipt_tip)+"\n");
        }else{
            sb.append(resources.getString(R.string.receipt_tip_repeat)+"\n");
        }
        sb.append("\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_id)+receiptId+"\n");
        sb.append("" + date+ "\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_user) + username+ "\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_hospital) + hospitalName + "\n");
        sb.append("仓库：" + warehouse + "\n");
        sb.append("本科室：" + officeName + "\n");
        sb.append("\n");

        for (String empName : conMap.keySet()) {
            if (empName.contains("(")){
                continue;
            }
            List<Uniform> uniforms = conMap.get(empName);
            for (Uniform uniform : uniforms){
                sb.append(empName);
                String tagTypeName =uniform.getName();
                Integer num = uniform.getNum();
                for (int i = 0; i <8-tagTypeName.length() ; i++) {
                    sb.append("　");
                }
                sb.append("---"+tagTypeName+"　x" + num+"\n");
                sb.append("\n");
            }
        }

        sb.append("------------非本科室-----------\n"+"\n");

        for (String empName : conMap.keySet()) {
            if (!empName.contains("(")){
                continue;
            }
            List<Uniform> uniforms = conMap.get(empName);
            for (Uniform uniform : uniforms){
                sb.append(empName);
                String tagTypeName =uniform.getName();
                Integer num = uniform.getNum();
                /*for (int i = 0; i <8-tagTypeName.length() ; i++) {
                    sb.append("　");
                }*/
                sb.append("---"+tagTypeName+"　x" + num+"\n");
                sb.append("\n");
            }
        }

      /*  for (SubReceiptListBean bean : subReceiptListBeanList) {
            String tagTypeName = bean.getTagTypeName();
            sb.append(tagTypeName);
            for (int i = 0; i <8-tagTypeName.length() ; i++) {
                sb.append("　");
            }
            sb.append("----------x" + bean.getTagNum()+"\n");
            sb.append("\n");
        }*/
        sb.append("\n");
        int total = 0;
        for (String empName : conMap.keySet()) {
            List<Uniform> uniforms = conMap.get(empName);
            for (Uniform uniform : uniforms){
                Integer num = uniform.getNum();
                total+=num;
            }
        }
        sb.append("合计：------------x "+total+"\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_customer_sign)+"\n");
        sb.append("\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_arfid));
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }

    /**
     * 生成收据
     *
     * @return
     */
    public static String buildReceipt(Context context , boolean isRepeat, String username, String officeName , String warehouse, String receiptId
            , List<SubReceiptListBean> subReceiptListBeanList) {

        Resources resources = context.getResources();

        StringBuilder sb = new StringBuilder();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");
        DATE_FORMAT.setTimeZone(TIME_ZONE);
        String date = DATE_FORMAT.format(Calendar.getInstance(TIME_ZONE).getTime());
        sb.append("\n");
        if (!isRepeat){
            sb.append(resources.getString(R.string.receipt_tip)+"\n");
        }else{
            sb.append(resources.getString(R.string.receipt_tip_repeat)+"\n");
        }
        sb.append("\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_id)+receiptId+"\n");
        sb.append("" + date+ "\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_user) + username+ "\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_office) + officeName + "\n");
        sb.append(resources.getString(R.string.receipt_warehouse) + warehouse + "\n");
        sb.append("\n");

        for (SubReceiptListBean bean : subReceiptListBeanList) {
            String tagTypeName = bean.getTagTypeName();
            sb.append(tagTypeName);
            for (int i = 0; i <8-tagTypeName.length() ; i++) {
                sb.append("　");
            }
            sb.append("----------x" + bean.getTagNum()+"\n");
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_customer_sign)+"\n");
        sb.append("\n");
        sb.append("\n");
        sb.append(resources.getString(R.string.receipt_arfid));
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }


}
