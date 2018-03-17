package com.intellidev.app.mashroo3k.uiutilities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

/**
 * Created by Ahmed Yehya on 17/03/2018.
 */

public class AlertDialogDelete extends DialogFragment {
    private CustomButtonTextFont btnCancel, btnDelete;
    DeleteDialogButtonListener listener;

    private static final String ARG_DB_ID = "param1";
    private static final String ARG_ITEM_POSITION = "param2";

    private String dbId;
    private int position;

    public static AlertDialogDelete newInstance(String dbId, int itemPosition) {
        AlertDialogDelete fragment = new AlertDialogDelete();
        Bundle args = new Bundle();
        args.putString(ARG_DB_ID, dbId);
        args.putInt(ARG_ITEM_POSITION, itemPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dbId = getArguments().getString(ARG_DB_ID);
            position = getArguments().getInt(ARG_ITEM_POSITION);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.alert_delete_fragment, container, false);

        btnDelete = rootView.findViewById(R.id.btn_delete);
        btnCancel = rootView.findViewById(R.id.btn_cancel);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onButtonClickListener(StaticValues.FLAG_BTN_DELETE, dbId, position);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onButtonClickListener(StaticValues.FLAG_BTN_CANCEL, dbId, position);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public interface DeleteDialogButtonListener {
        public void onButtonClickListener (int btnFlag, String dbId, int position);
    }

    public void setButtonListener (DeleteDialogButtonListener listener)
    {
        this.listener = listener;
    }
}
