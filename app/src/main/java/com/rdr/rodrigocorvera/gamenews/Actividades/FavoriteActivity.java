package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.rdr.rodrigocorvera.gamenews.Fragmentos.FavoriteFragment;
import com.rdr.rodrigocorvera.gamenews.R;

public class FavoriteActivity extends AppCompatActivity implements FavoriteFragment.OnFragmentInteractionListener{

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getViews();
        setContent();
    }

    public void getViews () {
        frameLayout = findViewById(R.id.frame_favorite_section);
    }

    public void setContent  () {
        FavoriteFragment favoriteFragment = FavoriteFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction().add(R.id.frame_favorite_section, favoriteFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
