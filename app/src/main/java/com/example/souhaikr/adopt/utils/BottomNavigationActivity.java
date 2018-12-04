package com.example.souhaikr.adopt.utils;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.souhaikr.adopt.R;

public class BottomNavigationActivity extends AppCompatActivity {
    private HomeFragment homeFragment ;
    private MapFragment mapFragment ;

    private TextView mTextMessage;
    private android.support.v7.app.ActionBar toolbar;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    mTextMessage.setText("");

                    setFragment(homeFragment) ;

                    return true;
                case R.id.navigation_dashboard:

                    toolbar.setTitle("Map");
                    setFragment(mapFragment) ;


                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText("search");
                    toolbar.setTitle("Search");

                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    toolbar.setTitle("profil");

                    return true;
            }
            return false;
        }
    };

    private void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction() ;
        fragmentTransaction.replace(R.id.frame,fragment);

        fragmentTransaction.detach(fragment).attach(fragment).commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Home");
        setContentView(R.layout.activity_bottom_navigation);
        mapFragment = new MapFragment() ;

        homeFragment = new HomeFragment() ;
        setFragment(homeFragment);

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
