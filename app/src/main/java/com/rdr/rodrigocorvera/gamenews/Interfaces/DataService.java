package com.rdr.rodrigocorvera.gamenews.Interfaces;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.News;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.User;
import com.rdr.rodrigocorvera.gamenews.Clases.CurrentUser;
import com.rdr.rodrigocorvera.gamenews.Clases.Jugador;
import com.rdr.rodrigocorvera.gamenews.Clases.MessageHandler;
import com.rdr.rodrigocorvera.gamenews.Clases.NewsFavoriteRoot;
import com.rdr.rodrigocorvera.gamenews.Clases.Noticia;
import com.rdr.rodrigocorvera.gamenews.Clases.Token;
import com.rdr.rodrigocorvera.gamenews.Clases.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Rodrigo Corvera on 3/6/2018.
 */

public interface DataService {

    @FormUrlEncoded
    @POST("users")
    Call<Token> insert_user(@Field("user") String user,
                            @Field("avatar") String avatar,
                            @Field("password") String password,
                            @Header("Authorization") String authHeader);


    //@Headers("Content-Type: application/x-www-form-urlencoded")

    @FormUrlEncoded
    @POST("login")
    Call<Token> get_token(@Field("user") String user,
                          @Field("password") String password);

    @FormUrlEncoded
    @POST("users/{user_id}/fav")
    Call<NewsFavoriteRoot> setFavoriteNews(@Path("user_id") String userId,
                                           @Field("new") String newsId,
                                           @Header("Authorization") String authHeader);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("news")
    Call<List<News>> getNews(@Header("Authorization") String authHeader);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("news/type/list")
    Call<String[]> getGameList(@Header("Authorization") String authHeader);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("news/type/{name}")
    Call<List<Noticia>> getGameNews(@Path("name") String name, @Header("Authorization") String authHeader);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("players/type/{name}")
    Call<List<Jugador>> getGamePlayers(@Path("name") String name, @Header("Authorization") String authHeader);


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("users/detail")
    Call<CurrentUser> getCurrentUser(@Header("Authorization") String authHeader);



    /*@Headers("Content-Type: application/x-www-form-urlencoded")
    @DELETE("users/{user_id}/fav")
    Call<String> deleteFavoriteNews(@Path("user_id") String userId,
                                    @Field("new") String newsId,
                                    @Header("Authorization") String authHeader);*/

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "users/{user_id}/fav", hasBody = true)
    Call<MessageHandler> deleteFavoriteNews(@Field("new") String newId,
                                            @Path("user_id") String id,
                                            @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @PUT("users/{id}")
    Call<UserInfo> updateAllInfo(@Path("id") String id,
                                 @Field("user") String name,
                                 @Field("password") String password,
                                 @Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("users/{id}")
    Call<UserInfo> updateName(@Path("id") String id,
                              @Field("user") String name,
                              @Header("Authorization") String authHeader);
    @FormUrlEncoded
    @PUT("users/{id}")
    Call<UserInfo> updatePassword(@Path("id") String id,
                                     @Field("password") String password,
                                     @Header("Authorization") String authHeader);








}
