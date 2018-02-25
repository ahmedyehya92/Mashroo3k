package com.intellidev.app.mashroo3k.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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

import com.intellidev.app.mashroo3k.CustomTextView;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.adapters.NavItemsAdapter;
import com.intellidev.app.mashroo3k.data.models.NavItemModel;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MainMvpView {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private TextView tvMsg;
    Toolbar toolbar;
    ListView nvNumbers;
    RelativeLayout mainRelativeLayout;
    RelativeLayout draweView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupActionBar();
        setupNavDrawer();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_menu,menu);
        return true;
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
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
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

        arrayList.add(new NavItemModel("الرئيسية",R.drawable.ic_home));
        arrayList.add(new NavItemModel("دراسات الجدوى",R.drawable.ic_nav_studies));
        arrayList.add(new NavItemModel("الفرص الإستثمارية",R.drawable.ic_nav_chance));
        arrayList.add(new NavItemModel("الحاسبة",R.drawable.ic_nav_calcutor));
        arrayList.add(new NavItemModel("البحث عن مشروع",R.drawable.ic_nav_search));
        arrayList.add(new NavItemModel("طلب دراسة جدوى",R.drawable.ic_nav_study_order));
        arrayList.add(new NavItemModel("عن الشركة",R.drawable.ic_nav_aboutus));
        NavItemsAdapter itemsAdapter = new NavItemsAdapter(this,arrayList);
        nvNumbers.setAdapter(itemsAdapter);
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
}
