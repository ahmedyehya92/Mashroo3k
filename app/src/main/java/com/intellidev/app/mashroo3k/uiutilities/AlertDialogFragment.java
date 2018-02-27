package com.intellidev.app.mashroo3k.uiutilities;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

/**
 * Created by Ahmed Yehya on 27/02/2018.
 */

public class AlertDialogFragment extends DialogFragment {
    CustomTextView tvTitle, tvMessage, btnOk;
    private String title, message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.alert_dialog_fragment, container, false);

        tvTitle = rootView.findViewById(R.id.tv_title);
        tvMessage = rootView.findViewById(R.id.tv_message);
        btnOk = rootView.findViewById(R.id.btn_ok);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        title = getArguments().getString(StaticValues.KEY_ALERT_TITLE);
        message = getArguments().getString(StaticValues.KEY_ALERT_MESSAGE);

        tvTitle.setText(title);
        tvMessage.setText(message);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
