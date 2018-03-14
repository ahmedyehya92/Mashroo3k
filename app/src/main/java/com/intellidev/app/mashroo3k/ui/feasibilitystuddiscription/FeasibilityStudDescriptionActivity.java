package com.intellidev.app.mashroo3k.ui.feasibilitystuddiscription;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.ui.completeoreder.CompleteOrderActivity;
import com.intellidev.app.mashroo3k.ui.shoppingcart.ShoppingCartActivity;
import com.intellidev.app.mashroo3k.uiutilities.AlertDialogCheckoutOrder;
import com.intellidev.app.mashroo3k.uiutilities.CustomButtonTextFont;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.utilities.StaticValues;
import com.squareup.picasso.Picasso;

public class FeasibilityStudDescriptionActivity extends BaseActivity implements FeasStudDescriptionMvpView, AlertDialogCheckoutOrder.FragmentButtonsListener {

    Toolbar toolbar;
    CustomTextView tvDetails, tvPrice;
    TextView textCartItemCount;
    CollapsingToolbarLayout collapsingToolbar;
    CustomButtonTextFont btnDescription,btnProductsServices,btnMoney,btnBuy;
    ImageView imgDescrip;
    AppBarLayout appBarLayout;
    WebView mWebView;
    private Menu menu;
    Integer numberOfCartItems;

    FeasStudDescriptionPresenter presenter;
    private String id;
    private String title;
    private String price;
    private String imgUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feasibility_stud_description);
        initViews();
        setupActionBar();
        setDataAndActions();
        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new FeasStudDescriptionPresenter(dataManager);
        presenter.onAttach(this);
        numberOfCartItems = presenter.getNumberOfItemsInCart();

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!presenter.isItemExistingInCart(id)) {
                    presenter.addItemToCart(id, title, price, imgUrl);
                    textCartItemCount.setText(String.valueOf(Math.min(presenter.getNumberOfItemsInCart(), 99)));
                }
                showAlert();
            }
        });
    }

    public void showAlert()
    {
        FragmentManager fm = getSupportFragmentManager();
        AlertDialogCheckoutOrder alertDialogCheckoutOrder = new AlertDialogCheckoutOrder();
        alertDialogCheckoutOrder.setButtonListener(this);
        alertDialogCheckoutOrder.show(fm, "alert dialog");
    }

    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar_main);
        //webView = findViewById(R.id.web_details);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        tvDetails = findViewById(R.id.tv_details);
        tvDetails.setMovementMethod(new ScrollingMovementMethod());
        tvPrice= findViewById(R.id.tv_price);
        btnDescription = findViewById(R.id.btn_descrip);
        btnProductsServices = findViewById(R.id.btn_products_services);
        btnBuy = findViewById(R.id.btn_buy);
        btnMoney = findViewById(R.id.btn_money);
        imgDescrip = findViewById(R.id.img_descrip);
        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        mWebView = findViewById(R.id.webViewFace);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(true);
        collapsingToolbar.setTitle("");
        //menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_go_back_green));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.description_action_menu,menu);
        // menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_go_back_green));
        final MenuItem cartItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(cartItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(cartItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back :
                onBackPressed();
                return true;
            case R.id.action_cart :
                startActivity(ShoppingCartActivity.getStartIntent(this));
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupBadge() {

        if (textCartItemCount != null) {
                textCartItemCount.setText(String.valueOf(Math.min(presenter.getNumberOfItemsInCart(), 99)));
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        setupBadge();
    }

    private void setTextView (String text)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // only for gingerbread and newer versions
            tvDetails.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
        }
        else
            tvDetails.setText(Html.fromHtml(text));
    }
    public static Intent getStartIntent(Context context, String id, String title, String describtion, String productsServices, String money, String price, String imgUrl) {
        Intent intent = new Intent(context, FeasibilityStudDescriptionActivity.class);
        intent.putExtra(StaticValues.KEY_ID, id);
        intent.putExtra(StaticValues.KEY_TITLE,title);
        intent.putExtra(StaticValues.KEY_DESCRIPTION, describtion);
        intent.putExtra(StaticValues.KEY_PRODUCT_SERVICE,productsServices);
        intent.putExtra(StaticValues.KEY_MONEY, money);
        intent.putExtra(StaticValues.KEY_PRICE, price);
        intent.putExtra(StaticValues.KEY_IMG_URL, imgUrl);

        return intent;
    }

    private void setDataAndActions ()
    {
        Intent intent = getIntent();
        id = intent.getStringExtra(StaticValues.KEY_ID);
        title = intent.getStringExtra(StaticValues.KEY_TITLE);
        final String description = intent.getStringExtra(StaticValues.KEY_DESCRIPTION);
        final String productsAndservices = intent.getStringExtra(StaticValues.KEY_PRODUCT_SERVICE);
        final String money = intent.getStringExtra(StaticValues.KEY_MONEY);
        imgUrl = intent.getStringExtra(StaticValues.KEY_IMG_URL);
        price = intent.getStringExtra(StaticValues.KEY_PRICE);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadDataWithBaseURL("", buildHtml(description), "text/html", "utf-8", "");

                if (!(imgUrl== null || imgUrl.equals(""))) {
                    Glide.with(FeasibilityStudDescriptionActivity.this)
                            .load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder)
                            .into(imgDescrip);
                }
                else
                    Glide.with(FeasibilityStudDescriptionActivity.this)
                            .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgDescrip);

                setTextView(description);
                String showPrice = getString(R.string.price) + price + " $";
                tvPrice.setText(showPrice);
            }
        });



        setAppBarTitle(title);



        btnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.loadDataWithBaseURL("", buildHtml(description), "text/html", "utf-8", "");
            }
        });
        btnMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.loadDataWithBaseURL("", buildHtml(money), "text/html", "utf-8", "");
            }
        });
        btnProductsServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.loadDataWithBaseURL("", buildHtml(productsAndservices), "text/html", "utf-8", "");
            }
        });
    }

    private void setAppBarTitle (final String title)
    {
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbar.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(),"fonts/Cairo-SemiBold.ttf"));
        collapsingToolbar.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(),"fonts/Cairo-SemiBold.ttf"));


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(title);

                    //menu.getItem(0).setIcon(ContextCompat.getDrawable(FeasibilityStudDescriptionActivity.this,R.drawable.ic_action_go_back_icon));

                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    //menu.getItem(0).setIcon(ContextCompat.getDrawable(FeasibilityStudDescriptionActivity.this,R.drawable.ic_action_go_back_green));
                    isShow = false;
                }
            }
        });


    }

    private String buildHtml (String text)
    {
        String resultHtml = "<html dir=\"rtl\" lang=\"ar\">\n" +
                "  <head>\n" +
                "    <link rel=\"stylesheet\"\n" +
                "          href=\"https://fonts.googleapis.com/css?family=Cairo\">\n" +
                "    <style>\n" +
                "      body {\n" +
                "        font-family: 'Cairo', sans-serif;\n" +

                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body bgcolor=\"#e6e9ec\">\n" +
                "    <div>" + text + "</div>\n" +
                "  </body>\n" +
                "</html>";
        return resultHtml;
    }

    @Override
    public void onAlertButtonClickLisener(int buttonFlag) {
        switch (buttonFlag) {
            case  StaticValues.FLAG_BTN_GO_TO_CART :
                startActivity(ShoppingCartActivity.getStartIntent(this));
                break;

            case StaticValues.FLAG_BTN_COMPLETE_ORDER:
                startActivity(CompleteOrderActivity.getStartIntent(this,false,id, price));
                break;


        }
    }
}
