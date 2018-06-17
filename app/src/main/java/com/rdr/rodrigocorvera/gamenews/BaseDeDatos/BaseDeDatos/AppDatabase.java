package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos.FavoritesDao;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos.GamesDao;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos.NewsDao;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos.PlayersDao;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos.UserDao;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.Favorites;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.Games;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.News;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.Players;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.User;

/**
 * Created by Rodrigo Corvera on 12/6/2018.
 */
@Database(entities = {User.class, News.class, Players.class, Games.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase{

    private static volatile AppDatabase DATABASE_INSTANCE;

    public abstract UserDao userDao();
    public abstract NewsDao newsDao();
    public abstract PlayersDao playersDao();
    public abstract GamesDao gamesDao();

    private static final Object LOCK = new Object();

     public static AppDatabase getDatabaseInstance(Context context) {

        if (DATABASE_INSTANCE == null) {
            synchronized (LOCK){

                if (DATABASE_INSTANCE == null) {
                    DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "game_news").fallbackToDestructiveMigration().build();
                }
            }
        }
        return DATABASE_INSTANCE;
    }

    public static void destroyInstance () {
        DATABASE_INSTANCE = null;
    }
}
