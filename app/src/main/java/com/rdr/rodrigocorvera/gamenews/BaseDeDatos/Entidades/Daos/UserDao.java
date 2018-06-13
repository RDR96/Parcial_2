package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.Entidades.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.Entidades.Entidades.User;

/**
 * Created by Rodrigo Corvera on 12/6/2018.
 */
@Dao
public interface UserDao {

    @Insert
    public void addUser(User user);

    @Update
    public void updateUser(User user);
}
