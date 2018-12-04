package com.example.souhaikr.adopt.utils;


import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.controllers.APIClient;
import com.example.souhaikr.adopt.controllers.ListPetsAdapter;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.entities.User;
import com.example.souhaikr.adopt.interfaces.APIInterface;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.security.KeyStore;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    APIInterface apiInterface;
    ImageView iv;
    TextView tv ;
    TextView desc ;
    TextView breed ;
    TextView age ;
    TextView gender ;
    TextView siz ;
    private List<Pet> contacts;





    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_details, container, false);



        iv = view.findViewById(R.id.imageView2) ;
        tv = view.findViewById(R.id.textView) ;
        desc = view.findViewById(R.id.desc) ;

        breed = view.findViewById(R.id.breed) ;
        age = view.findViewById(R.id.age) ;
        siz = view.findViewById(R.id.size) ;
        gender = view.findViewById(R.id.gender) ;
        Bundle arguments = getArguments();
        String desired_string = arguments.getString("key");
        int id = Integer.parseInt(desired_string);



        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Pet>>call = apiInterface.getPet(id);
        Log.d("TAG", "bbbbbbbbbba");
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
                Log.d("TAG", String.valueOf(contacts));
                Log.d("TAG", "aaaaaaaaaaaaa");


                for(Pet size: contacts) {


                    String text = size.getName();
                    String desc1 = size.getDesc();
                    String breed1 = size.getBreed() ;
                    String gende1 = size.getGender() ;
                    String size1 = size.getSize() ;
                    int ag = size.getAge() ;


                    String url = size.getImage();
                    tv.setText(text);
                    desc.setText(desc1) ;
                    breed.setText(breed1) ;
                    gender.setText(gende1) ;
                    siz.setText(size1);
                    age.setText((String.valueOf(ag)+" Months"));


                    Picasso.get().load(url).into(iv);
                    Log.d("TAG", text);
                }





            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                call.cancel();
                Log.d("TAG", "ccccccc");
            }
        });



        return view ;

    }

}
