package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.Favorites;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.News;

import java.util.List;

/**
 * Created by Rodrigo Corvera on 13/6/2018.
 */
@Dao
public interface FavoritesDao {
    @Insert
    public void addFavorite(Favorites favorites);

    @Delete
    public void deleteFavorite(Favorites favorites);

    @Query("DELETE FROM favorites")
    public void deleteAllFavorites();

    @Query("SELECT * FROM news AS nws INNER JOIN favorites AS favs ON nws.id=favs.news_id WHERE favs.user_id=:id")
    News getFavorites(String id);
}
