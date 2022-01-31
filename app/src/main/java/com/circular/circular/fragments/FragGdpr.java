package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.dialog.DialogMessageWithNoButtons;
import com.circular.circular.utils.Utils;

public class FragGdpr extends Fragment {
    private View mRootView;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_gdpr, null);
        initControls();
        return mRootView;
    }

    private void initControls() {
        initFonts();
        mRootView.findViewById(R.id.tv_frag_gdpr_submit).setOnClickListener(view -> {
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            DialogMessageWithNoButtons dlg1 = new DialogMessageWithNoButtons(requireActivity(),
                                    R.layout.dlg_message_withno_buttons,
                                    R.id.tv_dlg_message_withno_buttons_msg,
                                    "Thank you for submitting. Request received.");
                            dlg1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    requireActivity().finish();
                                }
                            });
                            dlg1.show();
                            Utils.setDialogWidth(dlg1, 0.8f, requireActivity());
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_gdpr_page_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_gdpr_content)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_gdpr_toc)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_gdpr_submit)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }
}
