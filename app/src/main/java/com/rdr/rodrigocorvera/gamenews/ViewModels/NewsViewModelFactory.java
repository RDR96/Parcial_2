package com.rdr.rodrigocorvera.gamenews.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Repositorios.NoticiasRepositorio;

/**
 * Created by Rodrigo Corvera on 15/6/2018.
 */

public class NewsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final NoticiasRepositorio noticiasRepositorio;

    public NewsViewModelFactory (NoticiasRepositorio noticiasRepositorio) {
        this.noticiasRepositorio = noticiasRepositorio;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new NewsFragmentViewModel(noticiasRepositorio);
    }
}
