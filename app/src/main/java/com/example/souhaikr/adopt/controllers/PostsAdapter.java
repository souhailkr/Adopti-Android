package com.example.souhaikr.adopt.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.utils.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SouhaiKr on 09/12/2018.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Pet> mData ;


    public PostsAdapter(Context mContext, List<Pet> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public PostsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.user_post_item_row,parent,false);
        return new PostsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostsAdapter.MyViewHolder holder, final int position) {


        String url = mData.get(position).getImage() ;
        Picasso.get().load(url).into(holder.thumbnail);




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView thumbnail;


        public MyViewHolder(View itemView) {
            super(itemView);


            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);



        }
    }
}