package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Rodrigo Corvera on 12/6/2018.
 */
@Entity(tableName = "user")
public class User {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    private String password;

    private String token;

    private int isLog;

    public User(@NonNull String id, String name, String password, String token, int isLog) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.token = token;
        this.isLog = isLog;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int isLog() {
        return isLog;
    }

    public void setLog(int log) {
        isLog = log;
    }
}
