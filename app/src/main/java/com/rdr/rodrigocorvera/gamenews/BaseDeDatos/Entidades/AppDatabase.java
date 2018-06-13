package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.Entidades;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.Entidades.Daos.UserDao;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.Entidades.Entidades.User;
import com.rdr.rodrigocorvera.gamenews.Clases.UserInfo;

/**
 * Created by Rodrigo Corvera on 12/6/2018.
 */
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public static AppDatabase DATABASE_INSTANCE;
    public abstract UserDao userDao();
    public static AppDatabase getDatabaseInstance(Context context) {
        if (DATABASE_INSTANCE == null) {
            DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "game_news")
                                .build();
        }
        return DATABASE_INSTANCE;
    }

    public static void destroyInstance () {
        DATABASE_INSTANCE = null;
    }
}
