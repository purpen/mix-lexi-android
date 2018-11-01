package com.lexivip.lexi.test;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.basemodule.tools.LogUtil;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.R;
import com.lexivip.lexi.beans.ProductBean;

import java.util.ArrayList;

public class TextActivity extends BaseActivity {

    private TextAdapter textAdapter;
    private ArrayList<Multiple> list;
    private ArrayList<TestAdapter.MultipleItem> testList;
    private TestAdapter testAdapter;

    @Override
    protected int getLayout() {
        return R.layout.aaa;
    }

    @Override
    public void initView() {
        super.initView();
        list = new ArrayList<>();
        testList=new ArrayList<>();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        GridLayoutManager manager=new GridLayoutManager(this,2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        textAdapter = new TextAdapter(list);
        textAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                LogUtil.e(position+"第几个啊："+textAdapter.getData().get(position).getSpanSize());
                return textAdapter.getData().get(position).getSpanSize();
            }
        });


        testAdapter = new TestAdapter(testList);

        recyclerView.setAdapter(testAdapter);
        testAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                LogUtil.e(position+"第几个啊："+testAdapter.getData().get(position).getSpanSize());
                return textAdapter.getData().get(position).getSpanSize();
            }
        });
        loadData();
        loadTest();
    }

    private void loadTest() {
        for (int i=0;i<30;i++){
            ProductBean bean=new ProductBean();
            bean.name="测试啊啊啊"+i;
            if (i%3==0){
                testList.add(new TestAdapter.MultipleItem(bean,TestAdapter.MultipleItem.ITEM_TYPE_SPAN2, TestAdapter.MultipleItem.ITEM_SPAN2_SIZE));
            }else {
                testList.add(new TestAdapter.MultipleItem(bean,TestAdapter.MultipleItem.ITEM_TYPE_SPAN2, TestAdapter.MultipleItem.ITEM_SPAN2_SIZE));
                //list.add(new Multiple(Multiple.IMG,Multiple.IMG_TEXT_SPAN_SIZE_MIN,s));
            }
        }
        testAdapter.setNewData(testList);
    }

    private void loadData() {
        for (int i=0;i<30;i++){
            /*VoucherBrandBean.DataBean.CouponsBean be=new VoucherBrandBean.DataBean.CouponsBean();
            be.store_name="测试啊啊啊"+i;*/
            String s="测试啊啊啊"+i;
            if (i%3==0){
                list.add(new Multiple(Multiple.IMG,Multiple.IMG_TEXT_SPAN_SIZE_MIN,s));
            }else {
                list.add(new Multiple(Multiple.TEXT,Multiple.IMG_SPAN_SIZE,s));
            }
        }
        textAdapter.setNewData(list);
    }
}
