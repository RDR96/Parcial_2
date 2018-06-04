package com.rdr.rodrigocorvera.gamenews.Interfaces;

import com.rdr.rodrigocorvera.gamenews.Clases.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Rodrigo Corvera on 3/6/2018.
 */

public interface DataService {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("users")
    Call<Usuario> insert_user(@Body Usuario usuario,@Header("Authorization") String authHeader);

}
