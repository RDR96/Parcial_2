package com.rdr.rodrigocorvera.gamenews.Clases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rodrigo Corvera on 3/6/2018.
 */

public class CurrentUser {

    public List<Noticia> favoriteNews;
    public String _id;
    public String user;
    public String avatar;
    public String password;
    public Date created_date;
    public int __v;

    public List<Noticia> getFavoriteNews() {
        return favoriteNews;
    }

    public void setFavoriteNews(List<Noticia> favoriteNews) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    @Override
    public String toString() {
        return "CurrentUser{" +
                "favoriteNews=" + favoriteNews +
                ", _id='" + _id + '\'' +
                ", user='" + user + '\'' +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", created_date=" + created_date +
                ", __v=" + __v +
                '}';
    }
}
