package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity;
import com.rdr.rodrigocorvera.gamenews.Actividades.MainActivity;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppExecutors;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.News;
import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.CurrentUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity.appDatabase;

/**
 * Created by Rodrigo Corvera on 15/6/2018.
 */

public class NewsNetworkDataSource {

    private static final Object LOCK = new Object();
    private static NewsNetworkDataSource sInstance;
    private final MutableLiveData<List<News>> newsDataUpdate;
    private final Context mContext;
    private final AppExecutors mExecutors;

    private NewsNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        newsDataUpdate = new MutableLiveData<List<News>>();
    }

    public static NewsNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d("Mensaje: ", "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsNetworkDataSource(context.getApplicationContext(), executors);
                Log.d("Mensaje: ", "Made new network data source");
            }
        }
        return sInstance;
    }

    public LiveData<List<News>> getCurrentNews() {
        return newsDataUpdate;
    }

    public void startFetchNewsService() {
        Intent intentToFetch = new Intent(mContext, GamesNewsSyncIntentService.class);
        mContext.startService(intentToFetch); //
        Log.d("Estado", "Service created");
    }

    void fetchNews() {
        mExecutors.networkIO().execute(()->{
            try{
                retrofit2.Call<List<News>> noticias = ApiAdapter.getApiHandler().getNews("Bearer " + LoginActivity.tokenAccess);

                noticias.enqueue(new Callback<List<News>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<News>> call, Response<List<News>> response) {
                        if (response.isSuccessful()) {

                            List<News> allNews = response.body();
                            newsDataUpdate.postValue(allNews);

                        } else if ( response.code() == 401 ) {
                            Thread thread = new Thread(){
                                public void run() {
                                    appDatabase.newsDao().getAllNews();
                                    appDatabase.playersDao().deleteAllPlayers();
                                    appDatabase.userDao().deleteAllUses();
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    mContext.startActivity(intent);
                                }
                            };
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<News>> call, Throwable t) {

                    }
                });

            } catch (Exception e) {

            }
        });
    }



    void fetchGameNews() {

    }



}
