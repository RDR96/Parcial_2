package com.rdr.rodrigocorvera.gamenews.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.News;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Repositorios.NoticiasRepositorio;

import java.util.List;

/**
 * Created by Rodrigo Corvera on 15/6/2018.
 */

public class GameGeneralInfoFragmentViewModel extends ViewModel{

    private LiveData<List<News>> noticia;
    private NoticiasRepositorio noticiasRepositorio;

    public GameGeneralInfoFragmentViewModel(NoticiasRepositorio noticiasRepositorio) {
        this.noticiasRepositorio = noticiasRepositorio;
        noticia = new MutableLiveData<>();
        noticia = this.noticiasRepositorio.getNews();
    }

    public LiveData<List<News>> getNoticia() {
        return noticia;
    }

}
