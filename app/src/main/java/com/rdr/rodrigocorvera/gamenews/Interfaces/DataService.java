package com.rdr.rodrigocorvera.gamenews.Interfaces;

import com.rdr.rodrigocorvera.gamenews.Clases.Jugador;
import com.rdr.rodrigocorvera.gamenews.Clases.Noticia;
import com.rdr.rodrigocorvera.gamenews.Clases.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Rodrigo Corvera on 3/6/2018.
 */

public interface DataService {

    @FormUrlEncoded
    @POST("users")
    Call<Usuario> insert_user(@Field("user") String user,
                              @Field("avatar") String avatar,
                              @Field("password") String password,
                              @Header("Authorization") String authHeader);


    //@Headers("Content-Type: application/x-www-form-urlencoded")

    @FormUrlEncoded
    @POST("login")
    Call<Usuario> get_token(@Field("user") String user,
                            @Field("password") String password);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("news")
    Call<List<Noticia>> getNews(@Header("Authorization") String authHeader);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("news/type/list")
    Call<String[]> getGameList(@Header("Authorization") String authHeader);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("news/type/{name}")
    Call<List<Noticia>> getGameNews(@Path("name") String name, @Header("Authorization") String authHeader);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("players/type/{name}")
    Call<List<Jugador>> getGamePlayers(@Path("name") String name, @Header("Authorization") String authHeader);





}
