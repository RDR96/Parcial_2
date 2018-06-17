package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.MessageHandler;
import com.rdr.rodrigocorvera.gamenews.Clases.NewsFavoriteRoot;
import com.rdr.rodrigocorvera.gamenews.Fragmentos.NewsFragment;
import com.rdr.rodrigocorvera.gamenews.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoNewsActivity extends AppCompatActivity {

    TextView infoHeader;
    ImageView infoImage;
    ImageView favoriteButton;
    TextView infoSubheader;
    TextView infoContainer;
    String intentValues[];
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_news);
        getViews();
        getIntentValues();
        setConfig();
        setInfo();
    }

    public void getViews () {
        infoHeader = findViewById(R.id.info_header);
        infoImage = findViewById(R.id.info_image);
        infoSubheader = findViewById(R.id.info_subheader);
        infoContainer = findViewById(R.id.info_container);
        favoriteButton = findViewById(R.id.favorite_button_info_activity);
    }

    public void getIntentValues () {
        Intent intent = getIntent();
        intentValues = intent.getStringExtra(Intent.EXTRA_TEXT).split("/-");
    }

    public void setConfig() {

        if (intentValues[4].equals("isFavorite")) {
            favoriteButton.setImageResource(R.drawable.ic_star_24dp);
            isFavorite = true;
        } else {
            favoriteButton.setImageResource(R.drawable.ic_star_border_white_24dp);
            isFavorite = false;
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    removeFavorite();
                } else{
                    addFavorite();
                }
            }
        });

    }

    public void addFavorite () {
        Call<NewsFavoriteRoot> newsFavoriteRootCall = ApiAdapter.getApiHandler().setFavoriteNews(
                LoginActivity.currentUser,
                intentValues[5],
                "Bearer " + LoginActivity.tokenAccess);
        newsFavoriteRootCall.enqueue(new Callback<NewsFavoriteRoot>() {
            @Override
            public void onResponse(Call<NewsFavoriteRoot> call, Response<NewsFavoriteRoot> response) {
                if ( response.isSuccessful() ) {
                    NewsFavoriteRoot favoriteNew = response.body();
                    if ( favoriteNew.getSuccess().equals("true") ) {
                        Toast.makeText(getApplicationContext(), R.string.favorite_success , Toast.LENGTH_SHORT).show();
                    }
                }
                isFavorite = true;
                favoriteButton.setImageResource(R.drawable.ic_star_24dp);
            }
            @Override
            public void onFailure(Call<NewsFavoriteRoot> call, Throwable t) {
            }
        });
    }

    public void removeFavorite() {

        Call<MessageHandler> deleteFavoriteCall = ApiAdapter.getApiHandler().deleteFavoriteNews(
                intentValues[5],
                LoginActivity.currentUser,
                "Bearer " + LoginActivity.tokenAccess);

        deleteFavoriteCall.enqueue(new Callback<MessageHandler>() {
            @Override
            public void onResponse(Call<MessageHandler> call, Response<MessageHandler> response) {
                if (response.isSuccessful()) {
                    MessageHandler messageHandler = response.body();
                    Log.d("text",messageHandler.getMessage());

                    if (messageHandler.getMessage().equals("The New has be Removed")) {
                        isFavorite = false;
                        favoriteButton.setImageResource(R.drawable.ic_star_border_white_24dp);
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageHandler> call, Throwable t) {

            }
        });

    }

    public void setInfo() {
        infoHeader.setText(intentValues[0]);
        Picasso.with(getApplicationContext()).load(intentValues[1]).fit().into(infoImage);
        infoSubheader.setText(intentValues[2]);
        infoContainer.setText(intentValues[3]);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.onDetach();
        newsFragment.onCreate(new Bundle());
    }
}
