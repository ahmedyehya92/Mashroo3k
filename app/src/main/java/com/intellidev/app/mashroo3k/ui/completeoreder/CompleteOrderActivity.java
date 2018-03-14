package com.intellidev.app.mashroo3k.ui.completeoreder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.models.CartListModel;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.ui.base.MvpView;
import com.intellidev.app.mashroo3k.ui.feasibilitystuddiscription.FeasibilityStudDescriptionActivity;
import com.intellidev.app.mashroo3k.uiutilities.AlertDialogConnectionError;
import com.intellidev.app.mashroo3k.uiutilities.CustomButtonTextFont;
import com.intellidev.app.mashroo3k.uiutilities.CustomEditText;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        initViews();
        setupActionBar();

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
                presenter.sendOrder(idOfItems,fullName,phoneNumber,email,address,note);

            }
        });
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
        alertDialogConnectionError.setButtonListener(this);
        alertDialogConnectionError.show(fm,"alert_error");
    }

    @Override
    public void onErrorConnectionAlertButtonClickLisener() {
        presenter.sendOrder(idOfItems,fullName,phoneNumber,email,address,note);
    }
}
