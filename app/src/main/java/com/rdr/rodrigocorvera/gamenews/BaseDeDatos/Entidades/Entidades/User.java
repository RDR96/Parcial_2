package com.rdr.rodrigocorvera.gamenews.BaseDeDatos.Entidades.Entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Rodrigo Corvera on 12/6/2018.
 */
@Entity(tableName = "user")
public class User {
    @PrimaryKey
    private String id;

    private String name;

    private String password;

    private boolean isLog;

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

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }
}
