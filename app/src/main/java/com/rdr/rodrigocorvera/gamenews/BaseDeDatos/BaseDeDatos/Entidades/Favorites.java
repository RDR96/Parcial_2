package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Rodrigo Corvera on 13/6/2018.
 */
@Entity(tableName = "favorites",
        foreignKeys = {@ForeignKey(entity = News.class,
                                   parentColumns = "id",
                                   childColumns = "news_id")})
public class Favorites {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String news_id;

    public Favorites(String news_id) {
        this.news_id = news_id;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }
}
