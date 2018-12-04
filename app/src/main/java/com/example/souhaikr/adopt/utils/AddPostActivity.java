package com.example.souhaikr.adopt.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.controllers.APIClient;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.interfaces.APIInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends Activity {
    ImageView iv;
    private static final int PICK_IMAGE_REQUEST = 100;
    String mediaPath ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);

            return;
        }

        Button btn = findViewById(R.id.btn);
        iv = findViewById(R.id.imageView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case PICK_IMAGE_REQUEST:

                if (resultCode == RESULT_OK) {
                    try {


                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        iv.setImageBitmap(selectedImage);


                        Button btn = findViewById(R.id.savebtn);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String filePath = getRealPathFromURI(imageUri);
                                File file = new File(filePath);
                                uploadImg(file);
                            }
                        });







                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(AddPostActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(AddPostActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void uploadImg(File f) {


        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), f);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("test", f.getName(), requestFile);



// add another part within the multipart request
        RequestBody fullName =
                RequestBody.create( okhttp3.MultipartBody.FORM, f.getName());
        int id = 2 ;
        RequestBody age =
                RequestBody.create(MediaType.parse("multipart/form-data"),"2");


        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Pet> call = apiInterface.getDetails(body,fullName);
        call.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                //if(response.isSuccessful()){
                    // HERE!!!!
                    Log.i("TAG", "ErrorA: "+ response.message());
                    Log.i("TAG", "ErrorB: "+ response.errorBody());

//                } else {
//
//                    Log.i("TAG", "ErrorC: "+ response.message());
//                    //ResponseBody body = response.body();
//                    //Log.i("TAG", "Success "+ body);
//                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Log.e("TAG", "ERRORD: "+ t.getMessage());
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
            } else {
                // User refused to grant permission.
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI)
    {
        String result = null;

        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null)
        { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        }
        else
        {
            if(cursor.moveToFirst())
            {
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
            }
            cursor.close();
        }
        return result;
    }


}
