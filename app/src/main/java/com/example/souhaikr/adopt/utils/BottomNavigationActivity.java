package com.example.souhaikr.adopt.utils;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.souhaikr.adopt.R;

public class BottomNavigationActivity extends AppCompatActivity {
    private android.support.v4.app.Fragment homeFragment = new HomeFragment() ;
    private android.support.v4.app.Fragment mapFragment = new MapFragment();

    private TextView mTextMessage;
    final FragmentManager fm = getSupportFragmentManager();
    android.support.v4.app.Fragment active = homeFragment;
    private android.support.v7.app.ActionBar toolbar;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    mTextMessage.setText("");

                    //setFragment(homeFragment) ;
                    fm.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;

                    return true;
                case R.id.navigation_dashboard:


                    fm.beginTransaction().hide(active).show(mapFragment).commit();
                    active = mapFragment;
                    //setFragment(mapFragment) ;
                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText("search");


                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);


                    return true;


            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_bottom_navigation);



        fm.beginTransaction().add(R.id.frame, mapFragment, "2").hide(mapFragment).commit();
        fm.beginTransaction().add(R.id.frame,homeFragment, "1").commit();

        //setFragment(homeFragment);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out: {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.add: {
                Intent intent = new Intent(getApplicationContext(), AddPostActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

}
