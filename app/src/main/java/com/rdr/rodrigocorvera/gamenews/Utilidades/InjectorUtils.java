/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rdr.rodrigocorvera.gamenews.Utilidades;

import android.content.Context;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppDatabase;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppExecutors;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Network.NewsNetworkDataSource;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Repositorios.NoticiasRepositorio;
import com.rdr.rodrigocorvera.gamenews.ViewModels.NewsViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for Sunshine
 */
public class InjectorUtils {

    public static NoticiasRepositorio provideRepository(Context context, String gameName, int i) {
        AppDatabase database = AppDatabase.getDatabaseInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        NewsNetworkDataSource networkDataSource =
                NewsNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return NoticiasRepositorio.getInstance(database.newsDao(), networkDataSource, executors, context, gameName, i);
    }

    public static NewsNetworkDataSource provideNetworkDataSource(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        return NewsNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static NewsViewModelFactory provideNewsViewModelFactory(Context context, String gameName, int i) {
        NoticiasRepositorio repository = provideRepository(context, gameName, i);
        return new NewsViewModelFactory(repository);
    }

   /* public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {
        SunshineRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }*/


}