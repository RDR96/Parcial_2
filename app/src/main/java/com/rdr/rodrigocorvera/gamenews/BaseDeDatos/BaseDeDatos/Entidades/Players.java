package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rodrigo Corvera on 15/6/2018.
 */
@Entity(tableName = "players")
public class Players {
    @PrimaryKey
    @NonNull
    @SerializedName("_id")
    private String id;

    private String name;

    private String biografia;

    private String game;

    private String __v;

    public Players(@NonNull String id, String name, String biografia, String game, String __v) {
        this.id = id;
        this.name = name;
        this.biografia = biografia;
        this.game = game;
        this.__v = __v;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
