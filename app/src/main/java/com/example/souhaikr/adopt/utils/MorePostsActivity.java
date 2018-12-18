package com.example.souhaikr.adopt.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.controllers.APIClient;
import com.example.souhaikr.adopt.controllers.PostsAdapter;
import com.example.souhaikr.adopt.controllers.RecyclerViewAdapter;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.interfaces.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.android.gestures.Utils.dpToPx;

public class MorePostsActivity extends AppCompatActivity {

    APIInterface apiInterface;

    private List<Pet> contacts;
    ListView mylistview ;
    List<Pet> pets = new ArrayList<>();
    Context m ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_posts);
        RecyclerView myrv =  findViewById(R.id.recyclerview_pet_id);
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);

        Handler mHandler = new Handler(Looper.getMainLooper());

        mActionBarToolbar.setTitle("Cats");


        setSupportActionBar(mActionBarToolbar);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String type =(String) b.get("type");




        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Pet>> call = apiInterface.getPetsByType(type) ;
        call.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {



                contacts = response.body();
                //contacts.addAll(response.body());
                Log.d("TAG", String.valueOf(contacts));





                for(Pet size: contacts) {
                    System.out.println(size.toString());

                        pets.add(size) ;


                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getApplicationContext(),pets);
                        myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                        myrv.setAdapter(myAdapter);




                    }
                });






            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                call.cancel();
            }
        });



    }
}
