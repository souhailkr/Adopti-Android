package com.example.souhaikr.adopt.utils;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.controllers.APIClient;
import com.example.souhaikr.adopt.controllers.PostsAdapter;
import com.example.souhaikr.adopt.controllers.RecyclerViewAdapter;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.interfaces.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.android.gestures.Utils.dpToPx;

public class UserProfileActivity extends AppCompatActivity {

    APIInterface apiInterface;

    private List<Pet> contacts;
    ListView mylistview ;
    List<Pet> pets = new ArrayList<>();
    Context m ;
    TextView username ;
    TextView useremail ;
    ImageView profile_picture ;
    TextView total_posts ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        RecyclerView myrv =  findViewById(R.id.posts_view);
        username = findViewById(R.id.user_name) ;
        useremail = findViewById(R.id.user_email) ;
        profile_picture = findViewById(R.id.profile_image) ;
        total_posts = findViewById(R.id.posts_number) ;

        Handler mHandler = new Handler(Looper.getMainLooper());

        Toolbar toolbar = findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String idUser =(String) b.get("id");
        int id = Integer.parseInt(idUser);


        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Pet>> call = apiInterface.getPetsByUser(id);
        call.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {



                contacts = response.body();
                //contacts.addAll(response.body());
                Log.d("TAG", String.valueOf(contacts));


                for(Pet size: contacts) {
                    System.out.println(size.toString());



                    pets.add(size) ;
                    String userName= size.getUser().getFirstname() ;
                    String userImg = size.getUser().getPhoto() ;
                    String userEmail = size.getUser().getEmail() ;
                    username.setText(userName);
                    useremail.setText(userEmail);
                    Picasso.get().load(userImg).into(profile_picture);





                }
                int total = contacts.size() ;
                total_posts.setText(String.valueOf(total));

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        PostsAdapter myAdapter = new PostsAdapter(getApplicationContext(),pets);
                        myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                        myrv.addItemDecoration(new GridSpacingItemDecoration(3, (int) dpToPx(4), true));
                        myrv.setItemAnimator(new DefaultItemAnimator());
                        myrv.setNestedScrollingEnabled(false);
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
