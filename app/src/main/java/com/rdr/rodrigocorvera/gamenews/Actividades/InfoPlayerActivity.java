package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdr.rodrigocorvera.gamenews.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class InfoPlayerActivity extends AppCompatActivity {

    ImageView infoPlayer;
    TextView playerName;
    TextView game;
    TextView bio;
    String intentValues[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_player);
        getViews();
        getIntentValues();
        setInfo();
    }

    void getViews () {
        infoPlayer = findViewById(R.id.image_player_container);
        playerName = findViewById(R.id.player_name_container);
        game = findViewById(R.id.player_game_container);
        bio = findViewById(R.id.player_bio_container);
    }

    void getIntentValues () {
        Intent intent = getIntent();
        intentValues = intent.getStringExtra(Intent.EXTRA_TEXT).split("/-");
    }
    void setInfo(){
        playerName.setText(intentValues[0]);
        game.setText(intentValues[1]);
        bio.setText(intentValues[2]);
        Picasso.with(getApplicationContext()).load(intentValues[3]).fit().into(infoPlayer);
    }
}
