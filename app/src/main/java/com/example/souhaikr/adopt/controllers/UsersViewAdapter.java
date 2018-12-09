package com.example.souhaikr.adopt.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.entities.User;
import com.example.souhaikr.adopt.utils.DetailsActivity;
import com.example.souhaikr.adopt.utils.UserProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SouhaiKr on 09/12/2018.
 */

public class UsersViewAdapter extends  RecyclerView.Adapter<UsersViewAdapter.MyViewHolder>{

    private Context mContext ;
    private List<User> mData ;


    public UsersViewAdapter(Context mContext, List<User> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public UsersViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.layout_useritem,parent,false);
        return new UsersViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersViewAdapter.MyViewHolder holder, final int position) {

        holder.user_name.setText(mData.get(position).getFirstname());
        String url = mData.get(position).getPhoto() ;
        Picasso.get().load(url).into(holder.user_image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,UserProfileActivity.class);



                int id = mData.get(position).getId() ;
                intent.putExtra("id",String.valueOf(id));


                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_name;
        ImageView user_image;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

           user_name = (TextView) itemView.findViewById(R.id.name) ;
           user_image = (ImageView) itemView.findViewById(R.id.user_image);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }
}
