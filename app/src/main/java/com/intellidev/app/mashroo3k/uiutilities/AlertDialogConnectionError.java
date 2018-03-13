package com.intellidev.app.mashroo3k.uiutilities;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellidev.app.mashroo3k.R;

/**
 * Created by Ahmed Yehya on 11/03/2018.
 */

public class AlertDialogConnectionError extends DialogFragment {
    private CustomButtonTextFont btnTryAgain;
    ErrorFragmentButtonListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.alert_no_connection_fragment,container,false);
        btnTryAgain = rootView.findViewById(R.id.btn_try_again);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onErrorConnectionAlertButtonClickLisener();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public interface ErrorFragmentButtonListener
    {
        public void onErrorConnectionAlertButtonClickLisener ();
    }
    public void setButtonListener (ErrorFragmentButtonListener listener)
    {
        this.listener = listener;
    }
}
