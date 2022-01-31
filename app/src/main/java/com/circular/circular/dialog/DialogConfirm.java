package com.circular.circular.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.circular.circular.CircularApplication;

public class DialogConfirm extends Dialog {
    ConfirmDialogInterface mListener;
    public DialogConfirm(@NonNull Context context) {
        super(context);
    }

    public DialogConfirm(@NonNull Context context, int layoutResource) {
        super(context);
        setContentView(layoutResource);
    }

    public DialogConfirm(@NonNull Context context,
                         int layoutResource,
                         int nIDYes,
                         int nIDNo,
                         int nIDMessage,
                         String strMessage,
                        ConfirmDialogInterface listener) {
        super(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        setContentView(layoutResource);
        mListener = listener;
        ((TextView)findViewById(nIDMessage)).setText(strMessage);
        ((TextView)findViewById(nIDMessage)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)findViewById(nIDYes)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)findViewById(nIDNo)).setTypeface(CircularApplication.mTfMainRegular);
        findViewById(nIDYes).setOnClickListener(view -> {
            dismiss();
            if (mListener != null){
                mListener.onClickedConfirm();
            }
        });

        findViewById(nIDNo).setOnClickListener(view -> {
            dismiss();
            if (mListener != null){
                mListener.onClickedNo();
            }
        });
    }
}
