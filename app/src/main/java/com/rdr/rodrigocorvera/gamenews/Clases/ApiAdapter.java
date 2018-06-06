package com.rdr.rodrigocorvera.gamenews.Clases;

import android.provider.ContactsContract;

import com.rdr.rodrigocorvera.gamenews.Interfaces.DataService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rodrigo Corvera on 3/6/2018.
 */

public class ApiAdapter {

    private static DataService API_HANDLER;

    private static String baseUrl = "http://gamenewsuca.herokuapp.com/";

    public static DataService getApiHandler () {

        /*HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);*/

        if ( API_HANDLER == null ) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            API_HANDLER = retrofit.create(DataService.class);

        }

        return API_HANDLER;
    }


}
