package com.rdr.rodrigocorvera.gamenews;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Interfaces.DataService;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;

    ActionBarDrawerToggle toogle;

    NavigationView mNavigationView;

    Toolbar toolbar;

    ImageView imageView;

    Resources res;

    TypedArray images;

    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getNews();
        getViews();
        setConfiguration();

    }

    public void getNews () {

        DataService api = ApiAdapter.getApiHandler();
    }

    public void setConfiguration () {
        random = new Random();
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        res = getResources();
        images = res.obtainTypedArray(R.array.header_images);
        toogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                imageView.setImageDrawable(images.getDrawable(getRandomValue(images.length())));
            }

        };

        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
    }

    public void getViews() {
        mNavigationView= findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        imageView = mNavigationView.getHeaderView(0).findViewById(R.id.header_image);
        mNavigationView = findViewById(R.id.navigation_view);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_items, menu);
        getSupportActionBar().setTitle(R.string.app_name);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //getSupportActionBar().show();
        if (toogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getRandomValue (int n) {
        return random.nextInt(n);
    }

}





