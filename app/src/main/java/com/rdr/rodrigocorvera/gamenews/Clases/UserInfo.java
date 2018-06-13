package com.rdr.rodrigocorvera.gamenews.Clases;

import java.util.Date;
import java.util.List;

/**
 * Created by Rodrigo Corvera on 12/6/2018.
 */

public class UserInfo {

    public List<String> favoriteNews;
    public String _id;
    public String user;
    public String password;
    public Date created_date;
    public int __v;

    public List<String> getFavoriteNews() {
        return favoriteNews;
    }

    public void setFavoriteNews(List<String> favoriteNews) {
        this.favoriteNews = favoriteNews;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
