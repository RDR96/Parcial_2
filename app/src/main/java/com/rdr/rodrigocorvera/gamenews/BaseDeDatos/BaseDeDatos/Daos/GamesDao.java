package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.Games;

import java.util.List;

/**
 * Created by Rodrigo Corvera on 16/6/2018.
 */
@Dao
public interface GamesDao {

    @Insert
    void insertGamesTitle(Games games);

    @Query("SELECT title FROM games")
    List<String> getAllGamesTitle();

    @Query("DELETE FROM games")
    void deleteAllGames();
}
