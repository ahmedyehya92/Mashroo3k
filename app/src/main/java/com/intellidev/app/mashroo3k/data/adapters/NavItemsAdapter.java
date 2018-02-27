package com.intellidev.app.mashroo3k.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

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

    public NavItemsAdapter(Context context, ArrayList<NavItemModel> hallArrayList) {
        super(context,0, hallArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View lisItemView = convertView;

        if (lisItemView == null) {
            lisItemView = LayoutInflater.from(getContext()).inflate(R.layout.nav_menu_item_view, parent, false);
        }
        NavItemModel currentItem = getItem(position);
        ViewHolder viewHolder = (ViewHolder)lisItemView.getTag();

        if (viewHolder==null){
            viewHolder = new ViewHolder();
            viewHolder.txTitle = (CustomTextView) lisItemView.findViewById(R.id.tv_text);
            viewHolder.itemImage = (ImageView) lisItemView.findViewById(R.id.img_nav_icon);
            lisItemView.setTag(viewHolder);
        }
        viewHolder.txTitle.setText(currentItem.getTitle());
        Picasso.with(context).load(currentItem.getIconResourceId()).into(viewHolder.itemImage);

        return lisItemView;
    }

    class ViewHolder {
        CustomTextView txTitle;
        ImageView itemImage;


    }
}
