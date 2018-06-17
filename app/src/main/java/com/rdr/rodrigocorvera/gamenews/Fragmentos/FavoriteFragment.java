package com.rdr.rodrigocorvera.gamenews.Fragmentos;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import static com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity.appDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static View view;
    private NewsAdapter newsAdapter;
    Thread thread;
    private OnFragmentInteractionListener mListener;
    private NewsFragmentViewModel myViewModel;
    ProgressBar progressBar;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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

        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        //getFavorites(view);
        progressBar = view.findViewById(R.id.progress_bar_favorite_activity);
        NoticiasRepositorio.changeValues("",0);
        NewsViewModelFactory factory = InjectorUtils.provideNewsViewModelFactory(MainActivity.contextGlobal,"",0);

        myViewModel = ViewModelProviders.of(this, factory).get(NewsFragmentViewModel.class);

        myViewModel.getNoticia().observe(this, noticia -> {
            progressBar.setVisibility(View.VISIBLE);
            updateNews(noticia);
        });
        InjectorUtils.provideRepository(getContext(),"",0);


        return view;
    }

    public void updateNews(List< News > noticia) {

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
                    ArrayList<News> favoritesNews = new ArrayList<>();

                    for (int i = 0; i < idNews.size(); i++) {
                        for (int j = 0; j < news.size(); j++) {
                            if (idNews.get(i).equals(news.get(j).getId()) ) {
                                favoritesNews.add(news.get(j));
                                favoritesNews.get(i).setIsFavorite(1);
                            }
                        }
                    }
                    newsAdapter = new NewsAdapter(getContext(), favoritesNews, true);

                    RecyclerView recyclerView = view.findViewById(R.id.favorite_recycler_view);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

                    recyclerView.setLayoutManager(linearLayoutManager);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(newsAdapter);
                } else if ( response.code() == 401){
                    thread = new Thread(){
                        public void run() {
                            appDatabase.newsDao().getAllNews();
                            appDatabase.playersDao().deleteAllPlayers();
                            appDatabase.userDao().deleteAllUses();
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    };
                    thread.start();

                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {
                thread = new Thread(){
                    public void run(){
                        AppDatabase appDatabase = AppDatabase.getDatabaseInstance(getContext());
                        ArrayList<News> favoriteNews = (ArrayList) appDatabase.newsDao().getFavoriteList();
                        setList(favoriteNews);
                    }
                };
            }
        });

    }

    void setList(ArrayList<News> favoriteNews){
        newsAdapter = new NewsAdapter(getContext(), favoriteNews, false);
        RecyclerView recyclerView = view.findViewById(R.id.favorite_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(newsAdapter);
    }

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
