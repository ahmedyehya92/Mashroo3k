package com.intellidev.app.mashroo3k.ui.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.ui.aboutus.AboutUsFragment;
import com.intellidev.app.mashroo3k.ui.calculator.CalculatorFragment;
import com.intellidev.app.mashroo3k.ui.home.HomeFragment;
import com.intellidev.app.mashroo3k.ui.search.SearchActivity;
import com.intellidev.app.mashroo3k.ui.shoppingcart.ShoppingCartActivity;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.adapters.NavItemsAdapter;
import com.intellidev.app.mashroo3k.data.models.NavItemModel;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

import java.util.ArrayList;
import java.util.Stack;

import static com.intellidev.app.mashroo3k.utilities.StaticValues.BACK_STACK_ROOT_TAG;
import static com.intellidev.app.mashroo3k.utilities.StaticValues.KEY_TAB_POSITION;

public class MainActivity extends BaseActivity implements MainMvpView, NavItemsAdapter.CustomButtonListener, HomeFragment.TabItemPositionCallback {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private TextView tvMsg;
    Toolbar toolbar;
    ListView nvNumbers;
    RelativeLayout mainRelativeLayout;

    RelativeLayout draweView;
    Menu menu;
    int currentPositionNavItem = 0;
    int currentSelectedTabinHomeFragment = 0;
    HomeFragment.TabItemPositionCallback tabItemPositionCallback = this;
    View oldNavButtonView;
    private Fragment fragment;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int currentFragmentResourceId;
    Fragment currentFragment;
    MainPresenter presenter;
    Handler handle;

    private Stack<Fragment> fragmentStack;
    private TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handle = new Handler();
        initViews();
        setupActionBar();
        setupNavDrawer();
        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new MainPresenter(dataManager);
        presenter.onAttach(this);
        // setupHomeFragment();
        fragmentStack = new Stack<>();
        Intent intent = getIntent();
        int tabPosition = intent.getIntExtra(KEY_TAB_POSITION,0);
        if (tabPosition == 3) {
            showFragmentWithoutAnim(HomeFragment.newInstance(3), true);
            currentSelectedTabinHomeFragment = 3;
        }
        else
            showFragmentWithoutAnim(HomeFragment.newInstance(0), true);


    }

    public static Intent getStartIntent (Context context, int tabPosition)
    {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(StaticValues.KEY_TAB_POSITION, tabPosition);
        return intent;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.action_menu,menu);
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

    private void setupBadge() {

        if (textCartItemCount != null) {
            textCartItemCount.setText(String.valueOf(Math.min(presenter.getNumberOfItemsInCart(), 99)));
        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.item_search :
                startActivity(SearchActivity.getStartIntent(this));
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                return true;
            case R.id.action_cart :
                startActivity(ShoppingCartActivity.getStartIntent(this));
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBadge();
    }

    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        nvNumbers = findViewById(R.id.list_items);
        mainRelativeLayout = findViewById(R.id.main_layout);

    }

    @Override
    public void setupActionBar() {
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
    }

    @Override
    public void setupNavDrawer() {
        if (drawerToggle == null) {
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
                public void onDrawerClosed(View view) {
                }

                public void onDrawerOpened(View drawerView) {

                }

                public void onDrawerSlide (View drawerView, float slideOffset) {
                }

                public void onDrawerStateChanged(int newState) {

                }

            };
            drawerLayout.setDrawerListener(drawerToggle);
        }

        drawerToggle.syncState();


        ArrayList<NavItemModel> arrayList = new ArrayList<>();

        arrayList.add(new NavItemModel(StaticValues.NAV_HOME_ITEM,"الرئيسية",R.drawable.ic_home));
        arrayList.add(new NavItemModel(StaticValues.NAV_STUDIES_ITEM,"دراسات الجدوى",R.drawable.ic_nav_studies));
        arrayList.add(new NavItemModel(StaticValues.NAV_OPPORTUNITIES_ITEM,"الفرص الإستثمارية",R.drawable.ic_nav_chance));
        arrayList.add(new NavItemModel(StaticValues.NAV_CALCULATOR_ITEM,"الحاسبة",R.drawable.ic_nav_calcutor));
        arrayList.add(new NavItemModel(StaticValues.NAV_SEARCH_ITEM,"البحث عن مشروع",R.drawable.ic_nav_search));
        arrayList.add(new NavItemModel(StaticValues.NAV_ORDER_ITEM,"طلب دراسة جدوى",R.drawable.ic_nav_study_order));
        arrayList.add(new NavItemModel(StaticValues.NAV_ABOUTUS_ITEM,"عن الشركة",R.drawable.ic_nav_aboutus));
        NavItemsAdapter itemsAdapter = new NavItemsAdapter(this,arrayList);
        itemsAdapter.setCustomButtonListner(this);
        nvNumbers.setAdapter(itemsAdapter);
    }


    @Override
    public void onItemNewsClickListner(int id, View buttonView, int position) {

        if (position != currentPositionNavItem) {
            if (position != 4)
                currentPositionNavItem = position;
            switch (id) {
                case StaticValues.NAV_CALCULATOR_ITEM:
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            drawerLayout.closeDrawers();
                            showFragment(new CalculatorFragment(), true, false);
                        }
                    });

                    break;

                case StaticValues.NAV_HOME_ITEM:
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            drawerLayout.closeDrawers();
                            showFragment(HomeFragment.getHomeFragment(), true, false);
                        }
                    });
                    break;

                case StaticValues.NAV_SEARCH_ITEM:
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            drawerLayout.closeDrawers();
                        }
                    });
                    startActivity(SearchActivity.getStartIntent(this));
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    break;

                case StaticValues.NAV_STUDIES_ITEM :
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            drawerLayout.closeDrawers();
                        }
                    });
                    if (currentSelectedTabinHomeFragment != 0)
                        showFragment(HomeFragment.newInstance(0), true, false);
                    break;

                case StaticValues.NAV_OPPORTUNITIES_ITEM :
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            drawerLayout.closeDrawers();
                        }
                    });
                    if (currentSelectedTabinHomeFragment != 1)
                        showFragment(HomeFragment.newInstance(1), true, false);
                    break;

                case StaticValues.NAV_ORDER_ITEM :
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            drawerLayout.closeDrawers();
                        }
                    });
                    if (currentSelectedTabinHomeFragment != 3)
                        showFragment(HomeFragment.newInstance(3), true, false);
                    break;
                case StaticValues.NAV_ABOUTUS_ITEM :
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            drawerLayout.closeDrawers();
                        }
                    });
                    showFragment(new AboutUsFragment(),true, false);
                    break;
            }
        }
        else {
            handle.post(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawers();
                }
            });

        }
    }

    @Override
    public void setSelectedTabItem(int selectedTab) {
        currentSelectedTabinHomeFragment = selectedTab;
        Log.d("onTabSelected", "current Selected Tab : " + currentSelectedTabinHomeFragment);
    }


    public class ItemArrayAdapter extends ArrayAdapter<String> {
        String[] itemList;
        private int listItemLayout;
        public ItemArrayAdapter(Context context, int layoutId, String[] itemList) {
            super(context, layoutId, itemList);
            listItemLayout = layoutId;
            this.itemList = itemList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int pos = position;
            final String item = getItem(pos);

            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(listItemLayout, parent, false);
                viewHolder.item = convertView.findViewById(R.id.tv_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.item.setText(item);
            return convertView;
        }
        class ViewHolder {
            CustomTextView item;
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else
        {
            if (fragmentStack.size()!= 0)
                fragmentStack.pop();
            if (fragmentStack.size() ==0 || currentPositionNavItem == 0) {
                Log.d("hj-------------", "current Selected Tab : " + currentSelectedTabinHomeFragment);
                if (currentSelectedTabinHomeFragment == 0) {
                    super.onBackPressed();
                }
                else {
                    showFragment(HomeFragment.newInstance(0), false, true);
                    currentSelectedTabinHomeFragment = 0;
                }
            }

       /* else if (currentPositionNavItem == 1 || currentPositionNavItem == 2 ||
                currentPositionNavItem == 5)
                showFragment(HomeFragment.newInstance(0), false, true); */
        else
                currentSelectedTabinHomeFragment = 0;
                showFragment(HomeFragment.newInstance(0), true, true);
        }
        currentPositionNavItem = 0;
    }
    public void showFragment(Fragment fragment, boolean addToStack, boolean onBack) {

        if (fragment instanceof HomeFragment)
            ((HomeFragment) fragment).setViewPagerListener(tabItemPositionCallback);

        if (addToStack) {
            fragmentStack.push(fragment);
        }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_a, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
    public void showFragmentWithoutAnim (Fragment fragment, boolean addToStack)
    {
        if (fragment instanceof HomeFragment)
            ((HomeFragment) fragment).setViewPagerListener(tabItemPositionCallback);
        if (addToStack) {
            fragmentStack.push(fragment);
        }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_a, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();


    }
}
