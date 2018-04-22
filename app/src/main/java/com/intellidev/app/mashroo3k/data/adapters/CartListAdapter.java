package com.intellidev.app.mashroo3k.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.models.CartListModel;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 09/03/2018.
 */

public class CartListAdapter extends ArrayAdapter<CartListModel> {

    Context context;
    ViewHolder viewHolder;

    private RemoveButtonListener removeButtonListener;


    public CartListAdapter(Context context, ArrayList<CartListModel> items)
    {
        super(context,0,items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final CartListModel cartListModel = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_item_cart_list,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imvDescription = convertView.findViewById(R.id.img_descrip);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.tvPrice = convertView.findViewById(R.id.tv_price);
            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

            if (!(cartListModel.getImg_url()==null || cartListModel.getImg_url().equals("")))
            {
                Glide.with(context)
                        .load(cartListModel.getImg_url()).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.imvDescription);
            }
            else {
                Glide.with(context)
                        .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.imvDescription); }

            viewHolder.tvTitle.setText(cartListModel.getTitle());
            viewHolder.tvPrice.setText(cartListModel.getPrice());
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeButtonListener.onRemoveButtonClickListner(cartListModel.getDpId(),view,position);
                }
            });


        return convertView;
    }

    public class ViewHolder {
        ImageView imvDescription;
        CustomTextView tvTitle;
        CustomTextView tvPrice;
        ImageView btnDelete;
    }

    public interface RemoveButtonListener {
        public void onRemoveButtonClickListner(String dbId, View buttonView, int position);
    }
    public void setCustomButtonListner(RemoveButtonListener listener) {
        this.removeButtonListener = listener;
    }


}
