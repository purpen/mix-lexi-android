package com.lexivip.lexi.test;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;

import java.util.List;

public class TextAdapter extends BaseMultiItemQuickAdapter<Multiple,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TextAdapter(List<Multiple> data) {
        super(data);
        /*addItemType(MultipleItem.ITEM_TYPE_SPAN1, R.layout.aaatext);
        addItemType(MultipleItem.ITEM_TYPE_SPAN2,R.layout.aaatext);*/
        addItemType(Multiple.TEXT, R.layout.aaatext);
        addItemType(Multiple.IMG, R.layout.aaatext);
        //addItemType(Multiple.IMG_TEXT, R.layout.aaatext);
    }

    @Override
    protected void convert(BaseViewHolder helper, Multiple item) {
        helper.setText(R.id.textView,item.getContent());
    }
}
