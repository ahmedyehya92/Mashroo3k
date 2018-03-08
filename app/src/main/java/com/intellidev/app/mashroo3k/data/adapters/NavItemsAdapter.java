package com.intellidev.app.mashroo3k.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.models.NavItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class NavItemsAdapter extends ArrayAdapter<NavItemModel> {
    Context context;
    private CustomButtonListener customListener;
    ArrayList<NavItemModel> navArrayList;

    public NavItemsAdapter(Context context, ArrayList<NavItemModel> navArrayList) {
        super(context,0, navArrayList);
        this.context = context;
        this.navArrayList = navArrayList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View lisItemView = convertView;

        if (lisItemView == null) {
            lisItemView = LayoutInflater.from(getContext()).inflate(R.layout.nav_menu_item_view, parent, false);
        }
        final NavItemModel currentItem = getItem(position);
        ViewHolder viewHolder = (ViewHolder)lisItemView.getTag();

        if (viewHolder==null){
            viewHolder = new ViewHolder();
            viewHolder.txTitle = lisItemView.findViewById(R.id.tv_text);
            viewHolder.itemImage = lisItemView.findViewById(R.id.img_nav_icon);
            viewHolder.layoutContainer = lisItemView.findViewById(R.id.layout_container);

            lisItemView.setTag(viewHolder);
        }
        viewHolder.txTitle.setText(currentItem.getTitle());
        viewHolder.layoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customListener.onItemNewsClickListner(currentItem.getId(),view,position);
            }
        });
        Glide.with(context).load(currentItem.getIconResourceId()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.itemImage);

        return lisItemView;
    }

    class ViewHolder {
        CustomTextView txTitle;
        ImageView itemImage;
        LinearLayout layoutContainer;
    }

    public interface CustomButtonListener {
        public void onItemNewsClickListner(int id, View buttonView, int position);
    }
    public void setCustomButtonListner(CustomButtonListener listener) {
        this.customListener = listener;
    }
}
