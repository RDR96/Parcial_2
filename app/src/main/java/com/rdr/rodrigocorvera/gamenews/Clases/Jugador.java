package com.rdr.rodrigocorvera.gamenews.Clases;

/**
 * Created by Rodrigo Corvera on 8/6/2018.
 */

public class Jugador {

    private String _id;
    private String name;
    private String biografia;
    private String game;
    private String avatar;
    private int __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", biografia='" + biografia + '\'' +
                ", game='" + game + '\'' +
                ", avatar='" + avatar + '\'' +
                ", __v=" + __v +
                '}';
    }
}
