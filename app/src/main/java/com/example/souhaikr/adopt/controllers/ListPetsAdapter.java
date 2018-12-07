package com.example.souhaikr.adopt.controllers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.entities.User;
import com.example.souhaikr.adopt.utils.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SouhaiKr on 26/11/2018.
 */


public class ListPetsAdapter  {

//    Context mContext;
//    private List<User> mContact;
//    RecyclerView.ViewHolder vh = null;
//
////Constructor
//
//
//    public ListPetsAdapter(List<User> mContact, Context context) {
//
//        this.mContact = mContact;
//        this.mContext = context ;
//    }
//
//
//    public class ViewHolder {
//
//        public TextView tvName;
//        public ImageView image ;
//
//
//    }
//
//
//    @Override
//    public int getCount() {
//        return mContact.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mContact.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup viewGroup) {
//
//        View vi = convertView;
//        ViewHolder holder;
//
//        if (convertView == null) {
//
//            LayoutInflater inflater;
//            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            vi = inflater.inflate(R.layout.custom_row, null);
//
//            holder = new ViewHolder();
//            holder.tvName = (TextView) vi.findViewById(R.id.title);
//            holder.image = (ImageView) vi.findViewById(R.id.icon);
//
//
//
//            vi.setTag(holder);
//
//        } else
//            holder = (ViewHolder) vi.getTag();
//
//        // now set your text view here like
//
//        // holder.tvName.setText("Bla Bla Bla");
//
//        holder.tvName.setText(mContact.get(position).getName());
//        String url = mContact.get(position).getPassword() ;
//        Picasso.get().load(url).into(holder.image);
//
//
//
//        // return your view
//        return vi;
//    }
}
