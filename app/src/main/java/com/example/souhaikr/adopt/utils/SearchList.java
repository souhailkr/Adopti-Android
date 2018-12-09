package com.example.souhaikr.adopt.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.controllers.APIClient;
import com.example.souhaikr.adopt.controllers.CustomerAdapter;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.interfaces.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchList extends AppCompatActivity {

    APIInterface apiInterface;

    private List<Pet> contacts;
    ListView mylistview ;
    List<Pet> pets = new ArrayList<>();
    Context m ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Bundle extras = getIntent().getExtras();

        String type = extras.getString("type");
        String breed = extras.getString("breed");
        String size_variable = extras.getString("size");
        String gender = extras.getString("gender");


        mylistview =  findViewById(R.id.list);
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setTitleTextColor(android.graphics.Color.WHITE) ;






        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Pet>> call = apiInterface.doGetList();
        call.enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {



                contacts = response.body();
                //contacts.addAll(response.body());
                Log.d("TAG", String.valueOf(contacts));





                for(Pet size: contacts) {
                    System.out.println(size.toString());

                    if (type.equals(size.getType()) && size_variable.equals(size.getSize()) && breed.equals(size.getBreed()) &&gender.equals(size.getGender()))

                    pets.add(size) ;





                }
                int itemCount = pets.size();
                getSupportActionBar().setTitle(itemCount + " result(s) found");



            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                call.cancel();
            }
        });




        CustomerAdapter adapter = new CustomerAdapter(this, pets);
        mylistview.setAdapter(adapter);

    }
}
