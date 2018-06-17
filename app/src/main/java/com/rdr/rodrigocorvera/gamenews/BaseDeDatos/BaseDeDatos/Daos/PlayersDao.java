package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.Players;

import java.util.List;

/**
 * Created by Rodrigo Corvera on 15/6/2018.
 */
@Dao
public interface PlayersDao {

    @Query("Select * from players WHERE game=:game")
    LiveData<List<Players>> getPlayersByGame(final String game);

    @Query("DELETE FROM players")
    void deleteAllPlayers();
}
