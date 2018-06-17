package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Repositorios;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppDatabase;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppExecutors;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Daos.NewsDao;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.News;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Network.NewsNetworkDataSource;

import java.util.List;

/**
 * Created by Rodrigo Corvera on 15/6/2018.
 */

public class NoticiasRepositorio {
    private static final String LOG_TAG = NoticiasRepositorio.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static NoticiasRepositorio sInstance;
    private final NewsDao newsDao;
    private final NewsNetworkDataSource newsNetworkDataSource;
    private final AppExecutors appExecutors;
    private boolean mInitialized = false;
    private static String gameName;
    private static int i;
    Context context;

    private NoticiasRepositorio (NewsDao newsDao, NewsNetworkDataSource newsNetworkDataSource, AppExecutors executors, Context context, String gameName, int i) {
        this.context = context;
        AppDatabase appDatabase = AppDatabase.getDatabaseInstance(context);
        this.newsDao = newsDao;
        this.newsNetworkDataSource = newsNetworkDataSource;
        this.appExecutors = executors;
        this.gameName = gameName;
        this.i = i;
        LiveData<List<News>> networkData = newsNetworkDataSource.getCurrentNews();
        networkData.observeForever(newListFromApi->{
            executors.diskIO().execute(()->{
                appDatabase.newsDao().addNews(newListFromApi);
            });
        });
    }

    public synchronized static NoticiasRepositorio getInstance(
            NewsDao newsDao, NewsNetworkDataSource weatherNetworkDataSource,
            AppExecutors executors, Context context, String gameName, int i) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NoticiasRepositorio(newsDao, weatherNetworkDataSource,
                        executors, context, gameName, i);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    private void startFetchWeatherService() {
        newsNetworkDataSource.startFetchNewsService();

    }

    public static void changeValues(String gameName, int i){
        NoticiasRepositorio.gameName = gameName;
        NoticiasRepositorio.i = i;
    }

    private synchronized void initializeData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        startFetchWeatherService();
    }

    public LiveData<List<News>> getNews(){
        initializeData();
        if (i == 0) {
            return newsDao.getAllNews();
        } else {
            return newsDao.getGameNews(gameName);
        }

    }

}
