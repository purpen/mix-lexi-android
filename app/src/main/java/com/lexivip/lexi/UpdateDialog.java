package com.lexivip.lexi;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smart.dialog.widget.base.BaseDialog;

public class UpdateDialog extends BaseDialog<UpdateDialog> {

    private View view;
    private Button button;
    private TextView textView;

    public UpdateDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        view = View.inflate(getContext(),R.layout.dialog_update,null);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        button = view.findViewById(R.id.button);
        textView = view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
