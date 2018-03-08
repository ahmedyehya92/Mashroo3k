package com.intellidev.app.mashroo3k.ui.feasibilitystuddiscription;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.utilities.StaticValues;
import com.squareup.picasso.Picasso;

public class FeasibilityStudDescriptionActivity extends BaseActivity implements FeasStudDescriptionMvpView {

    Toolbar toolbar;
    CustomTextView tvDetails, tvPrice;
    CollapsingToolbarLayout collapsingToolbar;
    Button btnDescription,btnProductsServices,btnMoney,btnBuy;
    ImageView imgDescrip;
    AppBarLayout appBarLayout;
    WebView mWebView;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feasibility_stud_description);
        initViews();
        setupActionBar();
        setDataAndActions();

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
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_action_go_back_green));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back :
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
        String id = intent.getStringExtra(StaticValues.KEY_ID);
        String title = intent.getStringExtra(StaticValues.KEY_TITLE);
        final String description = intent.getStringExtra(StaticValues.KEY_DESCRIPTION);
        final String productsAndservices = intent.getStringExtra(StaticValues.KEY_PRODUCT_SERVICE);
        final String money = intent.getStringExtra(StaticValues.KEY_MONEY);
        String imgUrl = intent.getStringExtra(StaticValues.KEY_IMG_URL);
        String price = intent.getStringExtra(StaticValues.KEY_PRICE);

        mWebView.loadDataWithBaseURL("", buildHtml(description), "text/html", "utf-8", "");


        setAppBarTitle(title);

        if (!(imgUrl== null || imgUrl.equals(""))) {
            Glide.with(this)
                    .load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(imgDescrip);
        }
        else
            Glide.with(this)
                    .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgDescrip);

        setTextView(description);
        String showPrice = getString(R.string.price) + price + " $";
        tvPrice.setText(showPrice);

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
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(FeasibilityStudDescriptionActivity.this,R.drawable.ic_action_go_back_icon));

                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(FeasibilityStudDescriptionActivity.this,R.drawable.ic_action_go_back_green));
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
}
