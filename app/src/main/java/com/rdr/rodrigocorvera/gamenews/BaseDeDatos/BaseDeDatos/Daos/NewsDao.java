package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.News;

import java.util.List;

/**
 * Created by Rodrigo Corvera on 13/6/2018.
 */
@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void addNews(List<News> noticias);

    @Update
     void updateNews(News... news);

    @Query("SELECT * FROM news ORDER BY created_date DESC")
    LiveData<List<News>> getAllNews();

    @Query("SELECT * FROM news WHERE game=:game")
    LiveData<List<News>> getGameNews(final String game);

    @Query("SELECT * FROM news WHERE isFavorite=1")
    List<News> getFavoriteList();

    @Query("DELETE FROM news")
     void deleteNews();

}
