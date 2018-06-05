package com.rdr.rodrigocorvera.gamenews.Clases;


//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rodrigo Corvera on 3/6/2018.
 */

public class Usuario {

    private List<Object> favoriteNews;
    private String _id;
    private String user;
    private String avatar;
    private String password;
    private Date created_date;
    private int __v;
    private String token;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Object> getFavoriteNews() {
        return favoriteNews;
    }

    public void setFavoriteNews(List<Object> favoriteNews) {
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
        return "{" +
                "favoriteNews:" + favoriteNews +
                ", _id:'" + _id + '\'' +
                ", user:'" + user + '\'' +
                ", avatar:'" + avatar + '\'' +
                ", password:'" + password + '\'' +
                ", created_date:" + created_date +
                ", __v:" + __v +
                ", token:'" + token + '\'' +
                ", message:'" + message + '\'' +
                '}';
    }
}
