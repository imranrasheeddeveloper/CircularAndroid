package com.circular.circular.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.circular.circular.CircularApplication;

public class DialogMessageWithNoButtons extends Dialog {
    public DialogMessageWithNoButtons(@NonNull Context context) {
        super(context);
    }

    public DialogMessageWithNoButtons(@NonNull Context context, int layoutResource) {
        super(context);
        setContentView(layoutResource);
    }

    public DialogMessageWithNoButtons(@NonNull Context context,
                                      int layoutResource,
                                      int nIDMessage,
                                      String strMessage) {
        super(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        setContentView(layoutResource);
        ((TextView)findViewById(nIDMessage)).setText(strMessage);
        ((TextView)findViewById(nIDMessage)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
