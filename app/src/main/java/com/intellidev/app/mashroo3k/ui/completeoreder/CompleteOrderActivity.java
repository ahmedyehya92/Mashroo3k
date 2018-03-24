package com.intellidev.app.mashroo3k.ui.completeoreder;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.models.CartListModel;
import com.intellidev.app.mashroo3k.data.paypalhelper.PayPalConfig;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.ui.base.MvpView;
import com.intellidev.app.mashroo3k.ui.feasibilitystuddiscription.FeasibilityStudDescriptionActivity;
import com.intellidev.app.mashroo3k.uiutilities.AlertDialogConnectionError;
import com.intellidev.app.mashroo3k.uiutilities.CustomButtonTextFont;
import com.intellidev.app.mashroo3k.uiutilities.CustomEditText;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.utilities.StaticValues;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CompleteOrderActivity extends BaseActivity implements CompleteOrderMvpView, AlertDialogConnectionError.ErrorFragmentButtonListener{
    Toolbar toolbar;
    CustomEditText etFullName, etPhoneNumber, etEmail, etAddress, etNotes;
    String fullName, phoneNumber, email, address, note;
    CustomButtonTextFont btnSend;
    Intent intent;
    boolean isMultipleItems;
    String singleItemId;
    ArrayList<String> idOfItems;
    ArrayList<CartListModel> cartListItems;
    CartListModel cartListModel;
    String paypalPrice;
    public static final int PAYPAL_REQUEST_CODE = 123;
    CompleteOrderPresenter presenter;
    ProgressBar progressBar;
    Handler handler;
    Fragment alertShown;
    Dialog dialog;
    boolean isErrorDialogDismissed = true;

    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        handler = new Handler(Looper.getMainLooper());
        initViews();
        setupActionBar();

        // TODO Start paypal service

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new CompleteOrderPresenter(dataManager);
        presenter.onAttach(this);



        intent = getIntent();
        isMultipleItems = intent.getBooleanExtra(StaticValues.KEY_IS_MULTIPLE_ITEMS,false);
        paypalPrice = intent.getStringExtra(StaticValues.KEY_PAYPAL_PRICE);
        if(!isMultipleItems)
            singleItemId = intent.getStringExtra(StaticValues.KEY_SINGLE_ID);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullName = etFullName.getText().toString();
                phoneNumber = etPhoneNumber.getText().toString();
                email = etEmail.getText().toString();
                address = etAddress.getText().toString();
                note = etNotes.getText().toString();
                idOfItems = new ArrayList<>();
                if (!isMultipleItems) {
                    idOfItems.add(singleItemId);
                } else {
                    cartListItems = new ArrayList<>();
                    cartListItems = presenter.getCartItems();
                    for (int i = 0 ; i<cartListItems.size() ; i++)
                    {
                        String itemId = cartListItems.get(i).getId();
                        idOfItems.add(itemId);
                    }
                }
                presenter.sendOrder(paypalPrice, idOfItems,fullName,phoneNumber,email,address,note);

            }
        });
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.description_action_menu,menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initViews()
    {
        toolbar = findViewById(R.id.toolbar_main);
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etAddress = findViewById(R.id.et_address);
        etNotes = findViewById(R.id.et_customer_note);
        btnSend = findViewById(R.id.btn_send);
        progressBar = findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }
    public void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar_title, null);

        ((CustomTextView)v.findViewById(R.id.title)).setText("استكمال الطلب");

        actionBar.setCustomView(v);

    }
    public static Intent getStartIntent (Context context, boolean isMultipleItems, String singleItemId, String price)
    {
        Intent intent = new Intent(context, CompleteOrderActivity.class);
        intent.putExtra(StaticValues.KEY_IS_MULTIPLE_ITEMS, isMultipleItems);
        intent.putExtra(StaticValues.KEY_PAYPAL_PRICE, price);
        if(!isMultipleItems)
            intent.putExtra(StaticValues.KEY_SINGLE_ID, singleItemId);

        return intent;
    }

    @Override
    public void showToast(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorConnectionDialog() {

        FragmentManager fm = getSupportFragmentManager();
        AlertDialogConnectionError alertDialogConnectionError = new AlertDialogConnectionError();
        alertDialogConnectionError.setButtonListener(CompleteOrderActivity.this);
        alertDialogConnectionError.show(fm, "alert_error");




        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressBar();
            }
        },500);
    }



    @Override
    public void completePurchase(String payAmount) {
        getPayment(payAmount);
    }

    @Override
    public void hideProgressBar() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                btnSend.setEnabled(true);
            }
        });
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        btnSend.setEnabled(false);
    }

    @Override
    public void unLoadEditTexts() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                etNotes.setText("");
                etAddress.setText("");
                etPhoneNumber.setText("");
                etEmail.setText("");
                etFullName.setText("");
            }
        });

    }

    @Override
    public void onErrorConnectionAlertButtonClickLisener() {
        isErrorDialogDismissed = true;
        presenter.sendOrder(paypalPrice, idOfItems,fullName,phoneNumber,email,address,note);
    }

    private void getPayment(String paymentAmount) {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Simplified Coding Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                      /*  startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount)); */

                        JSONObject resultJsonObject = confirm.toJSONObject();
                        String status = resultJsonObject.getJSONObject("response").getString("state");


                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

}
