package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Rodrigo Corvera on 16/6/2018.
 */
@Entity(tableName = "games")
public class Games {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    public Games(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
