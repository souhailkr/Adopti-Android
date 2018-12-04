package com.example.souhaikr.adopt.interfaces;

import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.entities.User;
import com.google.gson.JsonElement;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


/**
 * Created by SouhaiKr on 25/11/2018.
 */

public interface APIInterface {

    @GET("/getpets")
    public Call<List<Pet>> doGetList() ;

    @GET("/getpet/{id}")
    public Call<List<Pet>> getPet(@Path("id") int id);

    @Multipart
    @POST("/addpet")
    Call<Pet> getDetails(
                                  @Part MultipartBody.Part photo,
                                  @Part("name") RequestBody name


    );

}
