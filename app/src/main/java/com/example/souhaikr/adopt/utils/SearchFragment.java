package com.example.souhaikr.adopt.utils;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.souhaikr.adopt.R;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment  implements AdapterView.OnItemSelectedListener,RadioGroup.OnCheckedChangeListener, View.OnClickListener{
    RadioButton rb;
    Spinner type ;
    Spinner breed ;
    String size ;
    String gender ;
    Button save ;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        SegmentedGroup segmented2 =  view.findViewById(R.id.segmented2);
        SegmentedGroup segmented1 =  view.findViewById(R.id.segmented1);


        rb =  view.findViewById(R.id.button1);
        type = view.findViewById(R.id.spinner) ;
        breed = view.findViewById(R.id.spinner2) ;
        save = view.findViewById(R.id.btn) ;


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), size, Toast.LENGTH_SHORT).show();
                String bro = (String) breed.getSelectedItem();
                String typo = (String) type.getSelectedItem();


                Intent intent = new Intent(getActivity(), SearchList.class);
                Bundle extras = new Bundle();
                extras.putString("size",size);
                extras.putString("gender",gender);
                extras.putString("type",typo);
                extras.putString("breed",bro);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });


        type.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Dog");
        categories.add("Cat");
        categories.add("Bird");

        segmented2.setOnCheckedChangeListener(this);
        segmented1.setOnCheckedChangeListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        type.setAdapter(dataAdapter);




        return view ;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.button1:
                gender="" ;

                Toast.makeText(getActivity(), size, Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                gender = "Male";
                break;
            case R.id.button3:
                Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                gender = "Female";
                break;

            case R.id.btn1:

                Toast.makeText(getActivity(), size, Toast.LENGTH_SHORT).show();
                size="" ;
                break;
            case R.id.btn2:
                Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                size = "Small";
                break;
            case R.id.btn3:
                Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                size = "Medium";
                break;
            case R.id.btn4:
                Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                size = "Large";
                break;

            default:
                // Nothing to do
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (type.getSelectedItem().equals("Bird")) {

            List<String> categories = new ArrayList<String>();
            categories.add("aaa");
            categories.add("bbb");
            categories.add("ccc");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            breed.setAdapter(dataAdapter);

    }}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
