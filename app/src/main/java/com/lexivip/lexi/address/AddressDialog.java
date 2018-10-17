package com.lexivip.lexi.address;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.lexivip.lexi.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/30.
 */

public class AddressDialog extends Dialog {
    private final Context mContext;
    private DialogCallback mDialogCallback;
    HashMap<String, ArrayList<CityBean.CityNameBean>> map;
    public AddressDialog(Context context,HashMap<String, ArrayList<CityBean.CityNameBean>> map) {
        super(context, R.style.MyDialog);
        LayoutInflater inflater =  (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        this.mContext=context;
        this.map=map;
        View view=inflater.inflate(R.layout.dialog_city_picker,null);
        initView(view);
        setContentView(view);
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.height=800;
    }
    public void setDialogCallback(DialogCallback mDialogCallback){
        this.mDialogCallback=mDialogCallback;
    }
    private void initView(View view) {
        final CityPicker picker= (CityPicker) view.findViewById(R.id.area_city_picker);
        picker.setData(map);
        TextView txt_cancel = (TextView) view.findViewById(R.id.sex_cancle);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView txt_ok = (TextView) view.findViewById(R.id.sex_ok);
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogCallback.callBack(picker.getCity_string(),picker.getProvinceId(),picker.getCityId(),picker.getAreaId());
                dismiss();
            }
        });
    }
    public  interface DialogCallback{
        public void callBack(String addressName,int pId,int cId,int aId);
    }
}
