package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.MediaExtractor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.Noticia;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.GameGeneralInfoFragment;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.GameHolderFragment;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.GameImagesFragment;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.GameTopPlayersFragment;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.NewsFragment;
import com.rdr.rodrigocorvera.gamenews.Interfaces.DataService;
import com.rdr.rodrigocorvera.gamenews.Interfaces.SendText;
import com.rdr.rodrigocorvera.gamenews.Interfaces.SendText;
import com.rdr.rodrigocorvera.gamenews.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                               NewsFragment.OnFragmentInteractionListener,
                                                               GameHolderFragment.OnFragmentInteractionListener,
                                                               GameGeneralInfoFragment.OnFragmentInteractionListener,
                                                               GameTopPlayersFragment.OnFragmentInteractionListener,
                                                               GameImagesFragment.OnFragmentInteractionListener,
                                                               SendText {

    DrawerLayout drawerLayout;

    ActionBarDrawerToggle toogle;

    NavigationView mNavigationView;

    Toolbar toolbar;

    ImageView imageView;

    Resources res;

    TypedArray images;

    Random random;

    FrameLayout fm;

    SendText sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        setConfiguration();
    }


    public void setConfiguration () {
        sm = (SendText) this;
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

        fillGameList();

        NewsFragment newsFragment = new NewsFragment();
        FrameLayout fm = findViewById(R.id.frame_section);
        fm.removeAllViews();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_section, newsFragment).commit();
    }

    public void getViews() {

        fm = findViewById(R.id.frame_section);
        mNavigationView= findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        imageView = mNavigationView.getHeaderView(0).findViewById(R.id.header_image);
        mNavigationView = findViewById(R.id.navigation_view);
    }

    public void fillGameList () {

        Call<String[]> games = ApiAdapter.getApiHandler().getGameList("Bearer " + LoginActivity.tokenAccess);

        games.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {

                if ( response.isSuccessful() ) {
                    String gameList[];
                    gameList = response.body();

                    Menu items = mNavigationView.getMenu();
                    MenuItem element = items.getItem(1);
                    SubMenu subMenuGames = element.getSubMenu();

                    for (int i=0; i < gameList.length; i++ ) {
                        subMenuGames.add(setUpperCase(gameList[i]));

                    }
                }

            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {

            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int i = item.getItemId();

        if ( i != 0) {

            switch (i) {
                case R.id.log_out_option:
                    logOut();
                    break;
            }

        } else {

            drawerLayout.closeDrawers();
            String gameName = item.getTitle().toString().toLowerCase();
            Log.d("Fragmento", getSupportFragmentManager().findFragmentById(R.id.frame_section).getClass().getSimpleName());
            if (getSupportFragmentManager().findFragmentById(R.id.frame_section).getClass().getSimpleName().equals("GameHolderFragment")) {
                sm.sendData(gameName);
            } else {
                GameHolderFragment gameHolderFragmentFragment = GameHolderFragment.newInstance(gameName,"");
                fm.removeAllViews();
                getSupportFragmentManager().beginTransaction().add(R.id.frame_section, gameHolderFragmentFragment).commit();
            }

        }


        return false;
    }

    public String setUpperCase (String text) {
        return text.substring(0,1).toUpperCase() + text.substring(1);
    }

    public void logOut() {
        drawerLayout.closeDrawers();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View dialogElements = getLayoutInflater().inflate(R.layout.dialog_log_out, null);
        Button logOutButton = dialogElements.findViewById(R.id.log_out_button);
        Button notLogOutButton = dialogElements.findViewById(R.id.not_log_out_button);
        final AlertDialog alert;
        mBuilder.setView(dialogElements);
        alert = mBuilder.create();
        alert.show();

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.tokenAccess = null;
                Intent intent  = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                alert.hide();
                alert.dismiss();
                finish();
            }
        });

        notLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.hide();
                alert.dismiss();
            }
        });
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void sendData(String name) {
    Log.d("","");
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_game_holder, null);

        String tag = "android:switcher:" + view.findViewById(R.id.view_pager_games_holder).getId() + ":" + 0;
        GameGeneralInfoFragment gameGeneralInfoFragment = (GameGeneralInfoFragment) getSupportFragmentManager().findFragmentByTag(tag);
        gameGeneralInfoFragment.getNewGameTitle(name);

        tag = "android:switcher:" + view.findViewById(R.id.view_pager_games_holder).getId() + ":" + 1;
        GameTopPlayersFragment gameTopPlayersFragment  = (GameTopPlayersFragment) getSupportFragmentManager().findFragmentByTag(tag);
        gameTopPlayersFragment.getNewGameTitle(name);

        /*tag = "android:switcher:" + view.findViewById(R.id.view_pager_games_holder).getId() + ":" + 2;
        GameImagesFragment gameImagesFragment = (GameImagesFragment) getSupportFragmentManager().findFragmentByTag(tag);
        gameImagesFragment.getNewGameTitle(name);*/
    }


}





