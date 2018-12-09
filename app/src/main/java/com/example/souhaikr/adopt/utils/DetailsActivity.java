package com.example.souhaikr.adopt.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.controllers.APIClient;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.interfaces.APIInterface;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    APIInterface apiInterface;
    ImageView iv;
    TextView tv ;
    TextView desc ;
    TextView breed ;
    TextView age ;
    TextView gender ;
    TextView siz ;
    private Pet pet;
    ImageView userimage;
    TextView username ;
    ImageView callImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        iv = findViewById(R.id.imageView2) ;
        tv = findViewById(R.id.textView) ;
        userimage = findViewById(R.id.userimage) ;
        callImage = findViewById(R.id.call_icon) ;
        username = findViewById(R.id.username);
        desc = findViewById(R.id.desc) ;

        breed = findViewById(R.id.breed) ;
        age = findViewById(R.id.age) ;
        siz = findViewById(R.id.size) ;
        gender = findViewById(R.id.gender) ;
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String j =(String) b.get("id");
        int id = Integer.parseInt(j);



        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Pet> call = apiInterface.getPet(id);
        Log.d("TAG", "bbbbbbbbbba");
        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {

                pet = response.body();
                Log.d("TAG", String.valueOf(pet));
                Log.d("TAG", "aaaaaaaaaaaaa");

                String text = pet.getName();
                String desc1 = pet.getDesc();
                String breed1 =pet.getBreed() ;
                String gende1 = pet.getGender() ;
                String size1 = pet.getSize() ;
                String userName = pet.getUser().getFirstname() ;
                String userImage = pet.getUser().getPhoto() ;
                int ag = pet.getAge() ;


                String url = pet.getImage();
                tv.setText(text);
                desc.setText(desc1) ;
                breed.setText(breed1) ;
                gender.setText(gende1) ;
                siz.setText(size1);
                age.setText((String.valueOf(ag)+" Months"));
                username.setText(userName) ;


                Picasso.get().load(url).into(iv);
                Picasso.get().load(userImage).into(userimage);
//                    Log.d("TAG", text);
//                }
                String usernumber = pet.getUser().getNum_tel() ;

                callImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL); //use ACTION_CALL class
                        callIntent.setData(Uri.parse("tel:"+usernumber));

                        startActivity(callIntent);






                    }
                });






            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                call.cancel();
                Log.d("TAG", "ccccccc");
            }
        });






    }



}

