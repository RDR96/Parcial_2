package com.rdr.rodrigocorvera.gamenews.Clases;

/**
 * Created by Rodrigo Corvera on 10/6/2018.
 */

public class NewsFavoriteRoot {

    public String success;
    public Noticia favoriteNew;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Noticia getFavoriteNew() {
        return favoriteNew;
    }

    public void setFavoriteNew(Noticia favoriteNew) {
        this.favoriteNew = favoriteNew;
    }
}
