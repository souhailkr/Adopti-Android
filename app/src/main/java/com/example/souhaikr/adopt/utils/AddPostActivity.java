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
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.controllers.APIClient;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.interfaces.APIInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView iv;
    private static final int PICK_IMAGE_REQUEST = 100;
    String mediaPath ;
    EditText name ;
    EditText desc ;
    EditText age ;
    Spinner breedList ;
    Spinner typeList ;
    CheckBox small ;
    CheckBox medium ;
    CheckBox large ;
    CheckBox male ;
    CheckBox female ;
    Button savebtn ;
    File f ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_post);
        //android.support.v7.widget.Toolbar tb = findViewById(R.id.toolbar) ;


//        tb.setNavigationIcon(R.drawable.baseline_navigate_before_white_24dp);
//
//        tb.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               finish() ;
//            }
//        });
        name = findViewById(R.id.titleEditText) ;
        desc = findViewById(R.id.descriptionEditText) ;
        age = findViewById(R.id.editTextAge) ;
        small =findViewById(R.id.cbSmall) ;
        medium = findViewById(R.id.cbMedium) ;
        large = findViewById(R.id.cbLarge) ;
        male = findViewById(R.id.cbMale) ;
        female = findViewById(R.id.cbFemale) ;
        breedList = findViewById(R.id.breedSpinner) ;
        typeList = findViewById(R.id.typeSpinner) ;
        savebtn = findViewById(R.id.savebtn) ;

        age.addTextChangedListener(new TextWatcher() {
                                       @Override
                                       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                       }

                                       @Override
                                       public void onTextChanged(CharSequence s, int start, int before, int count) {
                                           if (age.getText().toString().trim().equals("")) {
                                               savebtn.setEnabled(false);
                                           } else {
                                               savebtn.setEnabled(true);
                                           }
                                       }

                                       @Override
                                       public void afterTextChanged(Editable s) {

                                       }
                                   }) ;




        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = (String) typeList.getSelectedItem();
                String breed = (String) breedList.getSelectedItem() ;
                String postName = name.getText().toString().trim() ;
                String postDesc = desc.getText().toString().trim() ;
                String postAge = age.getText().toString().trim() ;
                String gender = null;
                String size = null ;

                if(female.isChecked()){
                gender = "female" ;
                 
                }
                else if (male.isChecked())
                {
                    gender = "male" ;
                }

                if(small.isChecked()){
                   size = "small" ;

                }
                else if (medium.isChecked())
                {
                     size = "medium" ;
                }
                else if (large.isChecked())
                {
                    size = "large" ;
                }

                Log.i("TAG", "ErrorA: "+ gender+size+f.getName()+postAge+postDesc+postAge+type+breed+postName);

                uploadImg(f,postName,postDesc,type,breed,gender,size,postAge);
                
                    
            }
        });

        typeList.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Dog");
        categories.add("Cat");
        categories.add("Bird");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        typeList.setAdapter(dataAdapter);





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);

            return;
        }


        iv = findViewById(R.id.imageView);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            }
        });



    }


    public void onCheckboxClicked(View view) {

        switch(view.getId()) {

            case R.id.cbMale:

                female.setChecked(false);

                break;

            case R.id.cbFemale:

                male.setChecked(false);


                break;


        }
    }

    public void onCheckboxClicked2(View view) {

        switch(view.getId()) {

            case R.id.cbSmall:

                medium.setChecked(false);
                large.setChecked(false);

                break;

            case R.id.cbMedium:

                small.setChecked(false);
                large.setChecked(false);


                break;

            case R.id.cbLarge:

                small.setChecked(false);
                medium.setChecked(false);


                break;


        }
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

                        String filePath = getRealPathFromURI(imageUri);
                        File file = new File(filePath);

                        f = new File(filePath) ;

//                        Button btn = findViewById(R.id.savebtn);
//                        btn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                String filePath = getRealPathFromURI(imageUri);
//                                File file = new File(filePath);
//                                uploadImg(file);
//                                f = new File(filePath) ;
//                            }
//                        });



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

    private void uploadImg(File f,String name,String description,String type,String breed,String gender,String size,String age) {


        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), f);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("test", f.getName(), requestFile);



// add another part within the multipart request
        RequestBody image =
                RequestBody.create( okhttp3.MultipartBody.FORM, f.getName());

        RequestBody post_age =
                RequestBody.create(MediaType.parse("multipart/form-data"),age);
        RequestBody post_name =
                RequestBody.create(MediaType.parse("multipart/form-data"),name);

        RequestBody post_desc =
                RequestBody.create(MediaType.parse("multipart/form-data"),description);

        RequestBody  post_type=
                RequestBody.create(MediaType.parse("multipart/form-data"),type);

        RequestBody post_breed =
                RequestBody.create(MediaType.parse("multipart/form-data"),breed);

        RequestBody post_gender =
                RequestBody.create(MediaType.parse("multipart/form-data"),gender);

        RequestBody post_size =
                RequestBody.create(MediaType.parse("multipart/form-data"),size);


        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Pet> call = apiInterface.getDetails(body,post_name,post_desc,post_type,post_breed,post_gender,post_size,post_age,image);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item

        if (typeList.getSelectedItem().equals("Bird")) {
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            List<String> categories = new ArrayList<String>();
            categories.add("aaa");
            categories.add("bbb");
            categories.add("ccc");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        breedList.setAdapter(dataAdapter);

    }}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
