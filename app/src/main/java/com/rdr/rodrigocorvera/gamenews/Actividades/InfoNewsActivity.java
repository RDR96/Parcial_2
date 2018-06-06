package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdr.rodrigocorvera.gamenews.R;
import com.squareup.picasso.Picasso;

public class InfoNewsActivity extends AppCompatActivity {

    TextView infoHeader;
    ImageView infoImage;
    TextView infoSubheader;
    TextView infoContainer;
    String intentValues[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_news);
        getViews();
        getIntentValues();
        setInfo();
    }

    public void getViews () {
        infoHeader = findViewById(R.id.info_header);
        infoImage = findViewById(R.id.info_image);
        infoSubheader = findViewById(R.id.info_subheader);
        infoContainer = findViewById(R.id.info_container);
    }

    public void getIntentValues () {
        Intent intent = getIntent();
        intentValues = intent.getStringExtra(Intent.EXTRA_TEXT).split("/-");
    }

    public void setInfo() {
        infoHeader.setText(intentValues[0]);
        Picasso.with(getApplicationContext()).load(intentValues[1]).fit().into(infoImage);
        infoSubheader.setText(intentValues[2]);
        infoContainer.setText(intentValues[3]);
    }



}
