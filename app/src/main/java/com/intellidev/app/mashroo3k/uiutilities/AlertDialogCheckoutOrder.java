package com.intellidev.app.mashroo3k.uiutilities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

import java.util.zip.Inflater;

/**
 * Created by Ahmed Yehya on 10/03/2018.
 */

public class AlertDialogCheckoutOrder extends DialogFragment {
    private CustomTextView btnGoToCart, btnCompleteOrder;
    FragmentButtonsListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.alert_dialog_checkout_order, container, false);

        btnGoToCart = rootView.findViewById(R.id.btn_go_to_cart);
        btnCompleteOrder = rootView.findViewById(R.id.btn_complete_order);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onAlertButtonClickLisener(StaticValues.FLAG_BTN_GO_TO_CART);
            }
        });

        btnCompleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onAlertButtonClickLisener(StaticValues.FLAG_BTN_COMPLETE_ORDER);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public interface FragmentButtonsListener
    {
        public void onAlertButtonClickLisener (int buttonFlag);
    }
    public void setButtonListener (FragmentButtonsListener listener)
    {
        this.listener = listener;
    }
}
