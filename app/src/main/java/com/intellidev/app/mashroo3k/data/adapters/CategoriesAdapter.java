package com.intellidev.app.mashroo3k.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.models.CategoriesModel;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class CategoriesAdapter extends ArrayAdapter<CategoriesModel> {

    Context context;
    ViewHolder viewHolder;

    private customButtonListener customListener;

    public CategoriesAdapter(Context context, ArrayList<CategoriesModel> items)
    {
        super(context,0,items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final CategoriesModel itemModel = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_category_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvNameCat = convertView.findViewById(R.id.tv_cat_title);
            viewHolder.btntransition = convertView.findViewById(R.id.layout_cat_item);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvNameCat.setText(itemModel.getCatName());
        viewHolder.btntransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListener.onItemCatClickListner(itemModel.getCatId(), itemModel.getCatName(),view,position);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView tvNameCat;
        LinearLayout btntransition;
    }

    public interface customButtonListener {
        public void onItemCatClickListner(String id, String catName, View buttonView, int position);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }
}
