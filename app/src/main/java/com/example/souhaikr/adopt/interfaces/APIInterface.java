package com.example.souhaikr.adopt.interfaces;

import com.example.souhaikr.adopt.entities.API;
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

    @GET("/breed.list?key=1abbf326403e6d3d360d9b9a5ad90da1&animal=type&format=json")
    public Call<API> doGetLista() ;



    @GET("/getPetsByUser/{id}")
    public Call<List<Pet>> getPetsByUser(@Path("id") int id);


    @GET("/getpets")
    public Call<List<Pet>> doGetList() ;

    @GET("/showallusers")
    public Call<List<User>> doGetListUsers() ;

    @GET("/getpet/{id}")
    public Call<Pet> getPet(@Path("id") int id);

    @GET("/getpets/{type}")
    public Call<List<Pet>> getPetsByType(@Path("type") String type);

    @Multipart
    @POST("/addpet")
    Call<Pet> getDetails(
                                  @Part MultipartBody.Part photo,
                                  @Part("name") RequestBody name,
                                  @Part("description") RequestBody desc,
                                  @Part("type") RequestBody type,
                                  @Part("breed") RequestBody breed,
                                  @Part("sexe") RequestBody gender,
                                  @Part("size") RequestBody size,

                                  @Part("age") RequestBody age,
                                  @Part("image") RequestBody image,
                                  @Part("altitude") RequestBody latitude  ,
                                  @Part("longitude") RequestBody longitude


    );

}
