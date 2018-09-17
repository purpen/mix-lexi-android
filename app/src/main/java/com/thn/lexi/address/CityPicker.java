package com.thn.lexi.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.basemodule.tools.LogUtil;
import com.thn.lexi.R;

/**
 * 城市Picker
 *
 * @author zd
 */
public class CityPicker extends LinearLayout {
    private static final int REFRESH_PROVINCE = 1;
    private static final int REFRESH_CITY = 2;
    private static final int REFRESH_COUNTRY = 3;
    /**
     * 滑动控件
     */
    private ScrollerNumberPicker provincePicker;
    private ScrollerNumberPicker cityPicker;
    private ScrollerNumberPicker counyPicker;
    /**
     * 选择监听
     */
    private OnSelectingListener onSelectingListener;
    /**
     * 刷新界面
     */
    private static final int REFRESH_VIEW = 0;
    /**
     * 临时日期
     */
    private int tempProvinceIndex = 0;
    private int temCityIndex = 0;
    private int tempCounyIndex = 0;
    private Context context;
    private String city_string;
    private ArrayList<String> pList = new ArrayList<>();
    private ArrayList<String> cList = new ArrayList<>();
    private ArrayList<String> aList = new ArrayList<>();
    private ArrayList<CityBean.CityNameBean> pdList = new ArrayList<>();
    private ArrayList<CityBean.CityNameBean> cdList = new ArrayList<>();
    private ArrayList<CityBean.CityNameBean> adList = new ArrayList<>();
    Map<String, ArrayList<CityBean.CityNameBean>> map;
    private int pId;
    private int cId;
    private int aId;

    public CityPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CityPicker(Context context) {
        super(context);
        this.context = context;
    }

    // 获取城市信息
    private void getaddressinfo() {
        LayoutInflater.from(getContext()).inflate(R.layout.city_picker, CityPicker.this);
        // 获取控件引用
        provincePicker = (ScrollerNumberPicker) findViewById(R.id.province);
        cityPicker = (ScrollerNumberPicker) findViewById(R.id.city);
        counyPicker = (ScrollerNumberPicker) findViewById(R.id.couny);

    }

    public void setData(HashMap<String, ArrayList<CityBean.CityNameBean>> map) {
        this.map = map;
        pdList = map.get("k_1_0");
        for (int i = 0; i < pdList.size(); i++) {
            pList.add(pdList.get(i).getName());
        }
        pId = pdList.get(0).getOid();
        setTwoData();
        setThreeData();
        setaddressinfo();
    }

    private void setTwoData() {
        cdList = map.get("k_2_" + pId);
        LogUtil.e("市区的长度："+cdList.size());
        if (cdList!=null) {
            cList.clear();
            for (int k = 0; k < cdList.size(); k++) {
                cList.add(cdList.get(k).getName());
            }
            cId = cdList.get(0).getOid();
        }
    }

    private void setThreeData() {
        adList = map.get("k_3_" + cId);
        if (adList!=null) {
            aList.clear();
            for (int k = 0; k < adList.size(); k++) {
                aList.add(adList.get(k).getName());
            }
        }
        aId=adList.get(0).getOid();
    }

    // 设置城市信息
    private void setaddressinfo() {
        provincePicker.setData(pList);
        cityPicker.setData(cList);
        counyPicker.setData(aList);
        //设置省市区的默认位置
        provincePicker.setDefault(0);
        cityPicker.setDefault(0);
        counyPicker.setDefault(0);
        provincePicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                LogUtil.e("省份滑动停止");
                cityPicker.setEnable(true);
                counyPicker.setEnable(true);
                if (text.equals("") || text == null)
                    return;
                if (tempProvinceIndex != id) {
                    String selectDay = cityPicker.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                    String selectMonth = counyPicker.getSelectedText();
                    if (selectMonth == null || selectMonth.equals(""))
                        return;
                    // 城市数组
                    tempProvinceIndex = id;
                    pId = pdList.get(id).getOid();
                    mHandler.sendEmptyMessage(REFRESH_PROVINCE);
                }
                tempProvinceIndex = id;

            }

            @Override
            public void selecting(int id, String text) {
                cityPicker.setEnable(false);
                counyPicker.setEnable(false);
            }
        });
        cityPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                LogUtil.e("市区滑动停止");
                if (TextUtils.isEmpty(text))
                    return;
                LogUtil.e(text);
                counyPicker.setEnable(true);
                provincePicker.setEnable(true);

                String selectDay = provincePicker.getSelectedText();
                if (TextUtils.isEmpty(selectDay))
                    return;
                String selectMonth = counyPicker.getSelectedText();
                if (TextUtils.isEmpty(selectMonth))
                    return;
                temCityIndex = id;
                cId = cdList.get(id).getOid();

                mHandler.sendEmptyMessage(REFRESH_CITY);
            }

            @Override
            public void selecting(int id, String text) {
                counyPicker.setEnable(false);
                provincePicker.setEnable(false);
            }
        });
        counyPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                cityPicker.setEnable(true);
                provincePicker.setEnable(true);
                LogUtil.e("countryId:" + id + text);
                if (text.equals("") || text == null)
                    return;
                if (tempCounyIndex != id) {
                    String selectDay = provincePicker.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                    String selectMonth = cityPicker.getSelectedText();
                    if (selectMonth == null || selectMonth.equals(""))
                        return;
                    // 城市数组
                    tempCounyIndex = id;
                    LogUtil.e("ID："+id+"    长度："+adList.size());
                    aId = adList.get(id).getOid();
                    mHandler.sendEmptyMessage(REFRESH_COUNTRY);
                }
                tempCounyIndex = id;
            }

            @Override
            public void selecting(int id, String text) {
                cityPicker.setEnable(false);
                provincePicker.setEnable(false);
            }
        });

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getaddressinfo();
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_VIEW:
                    if (onSelectingListener != null)
                        onSelectingListener.selected(true);
                    break;
                //省份滑动停止
                case REFRESH_PROVINCE:
                    setTwoData();
                    setThreeData();
                    cityPicker.setData(cList);
                    cityPicker.setDefault(0);
                    counyPicker.setData(aList);
                    counyPicker.setDefault(0);
                    int lastDay = Integer.valueOf(provincePicker.getListSize());
                    if (tempProvinceIndex > lastDay) {
                        provincePicker.setDefault(lastDay - 1);
                    }
                    mHandler.sendEmptyMessage(REFRESH_VIEW);
                    break;
                //城市的滑动停止
                case REFRESH_CITY:
                    setThreeData();
                    counyPicker.setData(aList);
                    counyPicker.setDefault(0);
                    if (temCityIndex > Integer.valueOf(cityPicker.getListSize())) {
                        cityPicker.setDefault(Integer.valueOf(cityPicker.getListSize()) - 1);
                    }
                    mHandler.sendEmptyMessage(REFRESH_VIEW);
                    break;
                //区镇的滑动停止
                case REFRESH_COUNTRY:
                    if (tempCounyIndex > Integer.valueOf(counyPicker.getListSize())) {
                        counyPicker.setDefault(Integer.valueOf(counyPicker.getListSize()) - 1);
                    }
                    mHandler.sendEmptyMessage(REFRESH_VIEW);
                    break;

            }
        }

    };

    public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
        this.onSelectingListener = onSelectingListener;
    }

    public int getProvinceId() {
        return pId;
    }

    public int getCityId() {
        return cId;
    }

    public int getAreaId() {
        return aId;
    }

    public String getCity_string() {
        city_string = provincePicker.getSelectedText()
                + cityPicker.getSelectedText() + counyPicker.getSelectedText();
        return city_string;
    }

    public interface OnSelectingListener {

        public void selected(boolean selected);
    }
}
