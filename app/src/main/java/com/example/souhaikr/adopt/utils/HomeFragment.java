package com.example.souhaikr.adopt.utils;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.controllers.APIClient;
import com.example.souhaikr.adopt.controllers.ListPetsAdapter;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.entities.User;
import com.example.souhaikr.adopt.interfaces.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends android.support.v4.app.Fragment {






//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home, container, false);
//
//    }


    private ListPetsAdapter adapter;

    APIInterface apiInterface;
    TextView responseText;
    private List<Pet> contacts;
    private ArrayList<Pet> cats = new ArrayList<>() ;
    private ArrayList<Pet> dogs = new ArrayList<>() ;
    private ArrayList<Pet> others = new ArrayList<>() ;


    private DetailsFragment detailsFragment ;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        detailsFragment = new DetailsFragment() ;






        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Pet>> call = apiInterface.doGetList();
        call.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
//
//                for(User size: response.body()) {
//                    System.out.println(size.toString());
//                    String displayResponse = "";
//                    String text = size.name;
//                    String total = size.password;
//
//                    displayResponse += text + " Page\n" + total + " Total\n" ;
//                    Log.d("TAG",displayResponse);

                contacts = response.body();
                //contacts.addAll(response.body());
                Log.d("TAG", String.valueOf(contacts));

                dogs.clear();
                cats.clear();
                others.clear();



                for(Pet size: contacts) {
                    System.out.println(size.toString());
                    String displayResponse = "";
                    String text = size.getType();
                    String total = size.getImage();

                    if (text.equals("cat"))
                    {
                        cats.add(size);
                    }
                    else if (text.equals("dog"))
                    {
                        dogs.add(size);
                    }
                    else

                    {
                        others.add(size);
                    }


                }



                RecyclerView MyRecyclerView =  view.findViewById(R.id.cardView);
                MyRecyclerView.setHasFixedSize(true);
                LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                if (dogs.size() > 0 & MyRecyclerView != null) {
                    MyRecyclerView.setAdapter(new HomeFragment.MyAdapter(cats));
                }
                MyRecyclerView.setLayoutManager(MyLayoutManager);




                RecyclerView MyRecyclerView1 =  view.findViewById(R.id.second_recycler_view);
                MyRecyclerView.setHasFixedSize(true);
                LinearLayoutManager MyLayoutManager1 = new LinearLayoutManager(getActivity());
                MyLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                if (contacts.size() > 0 & MyRecyclerView1 != null) {
                    MyRecyclerView1.setAdapter(new HomeFragment.MyAdapter(dogs));
                }
                MyRecyclerView1.setLayoutManager(MyLayoutManager1);

                RecyclerView MyRecyclerView2 =  view.findViewById(R.id.third_recycler_view);
                MyRecyclerView.setHasFixedSize(true);
                LinearLayoutManager MyLayoutManager2 = new LinearLayoutManager(getActivity());
                MyLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                if (contacts.size() > 0 & MyRecyclerView2 != null) {
                    MyRecyclerView2.setAdapter(new HomeFragment.MyAdapter(others));
                }
                MyRecyclerView2.setLayoutManager(MyLayoutManager2);










            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                call.cancel();
            }
        });



        return view ;

    }

    private void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

    }

    public class MyAdapter extends RecyclerView.Adapter<HomeFragment.MyViewHolder> {
        private ArrayList<Pet> list;


        public MyAdapter(ArrayList<Pet> Data) {
            list = Data;

        }




        @Override
        public HomeFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_items, parent, false);
            HomeFragment.MyViewHolder holder = new HomeFragment.MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(HomeFragment.MyViewHolder holder, final int position) {

            holder.titleTextView.setText(list.get(position).getName());
            String url = list.get(position).getImage() ;
            Picasso.get().load(url).into(holder.coverImageView);
            final int x = list.get(position).getId() ;



            holder.coverImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    Log.d("TAG", String.valueOf(position));

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DetailsFragment llf = new DetailsFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString( "key" , String.valueOf(x));
                    llf.setArguments(arguments);
                    ft.replace(R.id.frame, llf);
                    ft.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;
        public ImageView likeImageView;
        public ImageView shareImageView;
        public View container;


        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);










        }
    }


}
