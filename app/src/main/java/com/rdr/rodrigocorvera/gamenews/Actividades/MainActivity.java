package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppDatabase;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppExecutors;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.Games;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.User;
import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.UserInfo;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.FavoriteFragment;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.GameGeneralInfoFragment;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.GameHolderFragment;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.GameImagesFragment;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.GameTopPlayersFragment;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.NewsFragment;
import com.rdr.rodrigocorvera.gamenews.Interfaces.SendText;
import com.rdr.rodrigocorvera.gamenews.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Random;

import static com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity.appDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                               NewsFragment.OnFragmentInteractionListener,
                                                               GameHolderFragment.OnFragmentInteractionListener,
                                                               GameGeneralInfoFragment.OnFragmentInteractionListener,
                                                               GameTopPlayersFragment.OnFragmentInteractionListener,
                                                               GameImagesFragment.OnFragmentInteractionListener,
                                                               FavoriteFragment.OnFragmentInteractionListener,
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

    Intent intent;

    Thread thread;

    public static UserInfo userInfo;

    public static Context contextGlobal;

    public AppExecutors appExecutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        setConfiguration();
    }


    public void setConfiguration () {
        appExecutors = AppExecutors.getInstance();
        contextGlobal = getApplicationContext();
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

                if ( response.code() == 200 ) {

                    String gameList[];
                    gameList = response.body();
                    Menu items = mNavigationView.getMenu();
                    MenuItem element = items.getItem(1);
                    SubMenu subMenuGames = element.getSubMenu();


                    thread = new Thread(){
                        public void run () {
                            appDatabase.gamesDao().deleteAllGames();
                            for (int i=0; i < gameList.length; i++ ) {
                                Games game = new Games(gameList[i]);
                                appDatabase.gamesDao().insertGamesTitle(game);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i=0; i < gameList.length; i++ ) {
                                        subMenuGames.add(setUpperCase(gameList[i]));
                                    }
                                }
                            });
                        }
                    };
                    thread.start();



                } else if ( response.code() == 401) {
                    thread = new Thread(){
                        public void run() {
                            appDatabase.newsDao().getAllNews();
                            appDatabase.playersDao().deleteAllPlayers();
                            appDatabase.userDao().deleteAllUses();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    };
                    thread.start();
                }

            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                thread = new Thread(){
                    public void run() {
                        Menu items = mNavigationView.getMenu();
                        MenuItem element = items.getItem(1);
                        SubMenu subMenuGames = element.getSubMenu();
                        List<String> gameTitles = appDatabase.gamesDao().getAllGamesTitle();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < gameTitles.size(); i++) {
                                    subMenuGames.add(setUpperCase(gameTitles.get(i)));
                                }
                            }
                        });

                    }
                };
                thread.start();
            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int i = item.getItemId();

        if ( i != 0) {

            switch (i) {
                case R.id.news_item:
                    //getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.frame_section));
                    getSupportFragmentManager().findFragmentById(R.id.frame_section).onDestroy();
                    NewsFragment newsFragment = NewsFragment.newInstance("","");
                    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.frame_section));
                    fm.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.frame_section, newsFragment).commit();
                    break;

                case R.id.favorite_option:
                        intent = new Intent(MainActivity.this, FavoriteActivity.class);
                        startActivity(intent);
                    break;

                case R.id.settings_option:
                         intent = new Intent(MainActivity.this, ConfigurationActivity.class);
                         startActivity(intent);
                    break;

                case R.id.log_out_option:
                    logOut();
                    break;
            }

        } else {

            String gameName = item.getTitle().toString().toLowerCase();
            Log.d("Fragmento", getSupportFragmentManager().findFragmentById(R.id.frame_section).getClass().getSimpleName());

            if (getSupportFragmentManager().findFragmentById(R.id.frame_section).getClass().getSimpleName().equals("GameHolderFragment")) {
                sm.sendData(gameName);
            } else {
                GameHolderFragment gameHolderFragment = GameHolderFragment.newInstance(gameName,"");
                fm.removeAllViews();
                getSupportFragmentManager().beginTransaction().add(R.id.frame_section, gameHolderFragment).commit();
            }

        }

        drawerLayout.closeDrawers();
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

                thread = new Thread(){
                    public void run(){
                        LoginActivity.tokenAccess = null;
                        appDatabase = AppDatabase.getDatabaseInstance(getApplicationContext());
                        User user = appDatabase.userDao().checkIfLog();
                        user.setToken("");
                        user.setLog(0);
                        appDatabase.userDao().updateUser(user);
                        Intent intent  = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                        finish();
                    }
                };
                thread.start();
                alert.hide();
                alert.dismiss();
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
        int i = item.getItemId();

        switch (i) {
            case R.id.action_refresh:
                    if (getSupportFragmentManager().findFragmentById(R.id.frame_section).getClass().getSimpleName().equals("GameHolderFragment")) {
                        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.fragment_game_holder, null);
                        String tag = "android:switcher:" + view.findViewById(R.id.view_pager_games_holder).getId() + ":" + 0;
                        GameGeneralInfoFragment gameGeneralInfoFragment = (GameGeneralInfoFragment) getSupportFragmentManager().findFragmentByTag(tag);
                        gameGeneralInfoFragment.getGameNews("");
                    }
                break;
        }

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


        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_game_holder, null);


        String tag = "android:switcher:" + view.findViewById(R.id.view_pager_games_holder).getId() + ":" + 0;
        GameGeneralInfoFragment gameGeneralInfoFragment = (GameGeneralInfoFragment) getSupportFragmentManager().findFragmentByTag(tag);
        gameGeneralInfoFragment.getNewGameTitle(name);

        tag = "android:switcher:" + view.findViewById(R.id.view_pager_games_holder).getId() + ":" + 1;
        GameTopPlayersFragment gameTopPlayersFragment  = (GameTopPlayersFragment) getSupportFragmentManager().findFragmentByTag(tag);
        gameTopPlayersFragment.getNewGameTitle(name);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else if (getSupportFragmentManager().findFragmentById(R.id.frame_section).getClass().getSimpleName().equals("GameHolderFragment")){
            getSupportFragmentManager().findFragmentById(R.id.frame_section).onDestroy();
            NewsFragment newsFragment = NewsFragment.newInstance("","");
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.frame_section));
            fm.removeAllViews();
            getSupportFragmentManager().beginTransaction().add(R.id.frame_section, newsFragment).commit();
        }
        else {
            super.onBackPressed();
        }
    }
}





