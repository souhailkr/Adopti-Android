package com.example.souhaikr.adopt.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.entities.Pet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SouhaiKr on 08/12/2018.
 */

public class CustomerAdapter extends BaseAdapter {
    Context context;
    List<Pet> rowItems;

    public CustomerAdapter(Context context, List<Pet> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView pet_pic;
        TextView pet_name;

        TextView pet_desc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.pet_name =  convertView
                    .findViewById(R.id.pet_name);
            holder.pet_pic = convertView
                    .findViewById(R.id.pet_pic);

            holder.pet_desc =  convertView
                    .findViewById(R.id.pet_desc);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        // holder.tvName.setText("Bla Bla Bla");

        holder.pet_name.setText(rowItems.get(position).getName());
        String url = rowItems.get(position).getImage() ;
        Picasso.get().load(url).into(holder.pet_pic);
        }

        return convertView;
    }
}
