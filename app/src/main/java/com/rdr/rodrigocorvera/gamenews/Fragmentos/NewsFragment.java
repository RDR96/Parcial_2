package com.rdr.rodrigocorvera.gamenews.Fragmentos;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity;
import com.rdr.rodrigocorvera.gamenews.Actividades.MainActivity;
import com.rdr.rodrigocorvera.gamenews.Adaptadores.NewsAdapter;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppDatabase;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.News;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Repositorios.NoticiasRepositorio;
import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.CurrentUser;
import com.rdr.rodrigocorvera.gamenews.Clases.Noticia;
import com.rdr.rodrigocorvera.gamenews.R;
import com.rdr.rodrigocorvera.gamenews.Utilidades.InjectorUtils;
import com.rdr.rodrigocorvera.gamenews.ViewModels.NewsFragmentViewModel;
import com.rdr.rodrigocorvera.gamenews.ViewModels.NewsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends LifecycleFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static View view;
    NewsFragmentViewModel myViewModel;
    private ArrayList<Noticia> dataNoticias;
    private NewsAdapter newsAdapter;
    private OnFragmentInteractionListener mListener;
    private ProgressBar progressBar;
    Thread thread;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        NoticiasRepositorio.changeValues("",0);
        NewsViewModelFactory factory = InjectorUtils.provideNewsViewModelFactory(MainActivity.contextGlobal,"",0);

        myViewModel = ViewModelProviders.of(this, factory).get(NewsFragmentViewModel.class);

        myViewModel.getNoticia().observe(this, noticia -> {
            progressBar.setVisibility(View.VISIBLE);
            updateNews(noticia);
        });
        InjectorUtils.provideRepository(getContext(),"",0);
        progressBar = view.findViewById(R.id.progress_bar_main_activity);
        return view;
    }

    /*public void fillArray () {

        dataNoticias = new ArrayList<Noticia>();

        view.findViewById(R.id.progress_bar_main_activity).setVisibility(View.VISIBLE);

        Call<List<Noticia>> noticias = ApiAdapter.getApiHandler().getNews("Bearer " + LoginActivity.tokenAccess);

        noticias.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {

                if ( response.isSuccessful() ) {

                    List<Noticia> allNews = response.body();

                    for (Noticia element : allNews) {
                        if ( element.getDescription()!= null && element.getCoverImage()!= null ) {
                            dataNoticias.add(element);
                        }
                    }
                    checkIfFavorite(dataNoticias, view);
                }

            }
            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {

            }
        });
    }*/

    public void updateNews(List<News> noticia) {

        ArrayList<News>  nuevasnuveas= (ArrayList) noticia;

        checkIfFavorite(nuevasnuveas);

    }

    void checkIfFavorite (ArrayList<News> news) {
        Call<CurrentUser> logInResponse = ApiAdapter.getApiHandler().getCurrentUser("Bearer "+ LoginActivity.tokenAccess);

        logInResponse.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                if ( response.isSuccessful() ) {
                    CurrentUser currentUser = response.body();
                    List<String> idNews = currentUser.getFavoriteNews();
                    ArrayList<String> favoritesNews = (ArrayList<String>) idNews;
                    for (int i = 0; i < idNews.size(); i++) {
                        for (int j = 0; j < news.size(); j++) {
                            if (idNews.get(i).equals(news.get(j).getId())) {
                                news.get(j).setIsFavorite(1);
                            }
                        }
                    }
                    newsAdapter = new NewsAdapter(getContext(), news, false);

                    RecyclerView recyclerView = view.findViewById(R.id.news_recycler_view);

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
                        @Override
                        public int getSpanSize(int position) {

                            if ( position%3 == 0) {
                                return 2;
                            } else {
                                return 1;
                            }

                        }
                    });

                    recyclerView.setLayoutManager(gridLayoutManager);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {
            }
        });

    }


    /*public void checkIfFavorite (final ArrayList<Noticia> noticias, final View view) {

        Call<CurrentUser> logInResponse = ApiAdapter.getApiHandler().getCurrentUser("Bearer "+ LoginActivity.tokenAccess);

        logInResponse.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call,final Response<CurrentUser> response) {
                if ( response.isSuccessful() ) {
                    thread = new Thread(){
                        public void run(){

                            AppDatabase appDatabase = AppDatabase.getDatabaseInstance(getContext());
                            appDatabase.favoritesDao().deleteAllFavorites();
                            appDatabase.newsDao().deleteNews();

                            CurrentUser currentUser = response.body();
                            List<String> idNews = currentUser.getFavoriteNews();
                            ArrayList<String> favoritesNews = (ArrayList<String>) idNews;

                            for ( int i = 0; i < favoritesNews.size(); i++) {
                                for (int j = 0; j < dataNoticias.size(); j++) {
                                    if (i == 0) {
                                        News news = new News(dataNoticias.get(j).get_id(),
                                                             dataNoticias.get(j).getTitle(),
                                                             dataNoticias.get(j).getBody(),
                                                             dataNoticias.get(j).getGame(),
                                                             dataNoticias.get(j).getCoverImage(),
                                                             dataNoticias.get(j).getDescription(),
                                                             dataNoticias.get(j).getCreated_date(),
                                                             dataNoticias.get(j).get__v());
                                        appDatabase.newsDao().addNews(news);
                                    }
                                    if (favoritesNews.get(i).equals(noticias.get(j).get_id())) {
                                        Favorites favorites = new Favorites(currentUser.get_id(), dataNoticias.get(j).get_id());
                                        dataNoticias.get(j).setFavorite(true);
                                    }

                                }
                            }
                            List<News> listaNoticias = appDatabase.newsDao().getAllNews();

                            int counter = 0;
                            for (int i = 0; i < listaNoticias.size(); i++) {
                                if (dataNoticias.get(i).isFavorite()) {
                                    counter++;
                                }
                            }

                            Log.d("favoritos", String.valueOf(counter));

                            view.findViewById(R.id.progress_bar_main_activity).setVisibility(View.GONE);

                            newsAdapter = new NewsAdapter(getContext(), dataNoticias, false);

                            RecyclerView recyclerView = view.findViewById(R.id.news_recycler_view);

                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

                            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
                                @Override
                                public int getSpanSize(int position) {

                                    if ( position%3 == 0) {
                                        return 2;
                                    } else {
                                        return 1;
                                    }

                                }
                            });

                            recyclerView.setLayoutManager(gridLayoutManager);

                            recyclerView.setAdapter(newsAdapter);
                        }
                    };
                    thread.start();

                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {

            }
        });

    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
