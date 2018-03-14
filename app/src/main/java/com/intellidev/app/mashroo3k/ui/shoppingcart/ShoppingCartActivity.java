package com.intellidev.app.mashroo3k.ui.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.adapters.CartListAdapter;
import com.intellidev.app.mashroo3k.data.models.CartListModel;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.ui.completeoreder.CompleteOrderActivity;
import com.intellidev.app.mashroo3k.uiutilities.CustomButtonTextFont;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;

import java.util.ArrayList;

import static com.intellidev.app.mashroo3k.data.dphelper.ShoppingCartItemContract.BASE_CONTENT_URI;
import static com.intellidev.app.mashroo3k.data.dphelper.ShoppingCartItemContract.PATH_ITEMS;

public class ShoppingCartActivity extends BaseActivity implements ShoppingCartMvpView, CartListAdapter.RemoveButtonListener{
    ListView cartListView;
    Toolbar toolbar;
    CustomTextView tvTotalPrice;
    CustomButtonTextFont btnCheckOutOrder;
    String totalPrice;

    ArrayList<CartListModel> cartItemsArrayList;
    CartListAdapter cartListAdapter;
    Menu menu;
    ShoppingCartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initViews();
        setupActionBar();
        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new ShoppingCartPresenter(dataManager);
        presenter.onAttach(this);

        btnCheckOutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CompleteOrderActivity.getStartIntent(ShoppingCartActivity.this,true,null, totalPrice));
            }
        });

        cartItemsArrayList = new ArrayList<>();
        cartItemsArrayList = presenter.getCartList();
        cartListAdapter = new CartListAdapter(this,cartItemsArrayList);
        cartListAdapter.setCustomButtonListner(this);
        cartListView.setAdapter(cartListAdapter);
        updateTotalPrice();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
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

    private void initViews()
    {
        cartListView = findViewById(R.id.list_view_cart);
        toolbar = findViewById(R.id.toolbar_main);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnCheckOutOrder = findViewById(R.id.btn_checkout);
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

        ((CustomTextView)v.findViewById(R.id.title)).setText("سلة المشتريات");

        actionBar.setCustomView(v);

    }



    public void updateTotalPrice()
    {
        Integer totalPrice = 0;
        int arrayLength = cartItemsArrayList.size();
        for (int i = 0; i<arrayLength;i++)
        {
            final CartListModel item = cartItemsArrayList.get(i);
            totalPrice+=Integer.parseInt(item.getPrice());

        }
        this.totalPrice = String.valueOf(totalPrice);
        String resultPrice = getString(R.string.total)+totalPrice.toString()+"$";
        tvTotalPrice.setText(resultPrice);
    }

    @Override
    public void onRemoveButtonClickListner(String dbId, View buttonView, int position) {
        removeListItem(getViewByPosition(position,cartListView),position);
        presenter.deleteItemFromCartList(buildContentUri(dbId));
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ShoppingCartActivity.class);
        return intent;
    }
    public Uri buildContentUri (String dbId){
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ITEMS+"/"+dbId);
        return CONTENT_URI;
    }

    protected void removeListItem(View rowView, final int positon) {
        final Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        rowView.startAnimation(animation);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                cartItemsArrayList.remove(positon);
                updateTotalPrice();
                cartListAdapter.notifyDataSetChanged();
                animation.cancel();


            }
        }, 100);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
