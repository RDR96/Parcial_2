package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.User;

/**
 * Created by Rodrigo Corvera on 12/6/2018.
 */
@Dao
public interface UserDao {

    @Insert
    public void addUser(User user);

    @Update
    public void updateUser(User user);

    @Query("SELECT * FROM user WHERE id=:id")
    User getCurrentUser(final String id);

    @Query("SELECT id FROM user WHERE isLog=1")
    String getIdCurrentUser();

    @Query("SELECT * FROM user WHERE isLog=1")
    User checkIfLog();

    @Query("DELETE FROM user")
    void deleteAllUses();
}
