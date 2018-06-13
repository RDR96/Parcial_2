package com.rdr.rodrigocorvera.gamenews.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity;
import com.rdr.rodrigocorvera.gamenews.Adaptadores.NewsAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.Noticia;
import com.rdr.rodrigocorvera.gamenews.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameGeneralInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameGeneralInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameGeneralInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public View view;
    private ArrayList<Noticia> dataNoticias;
    private NewsAdapter newsAdapter;
    private static Bundle args;

    private OnFragmentInteractionListener mListener;

    public GameGeneralInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameGeneralInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameGeneralInfoFragment newInstance(String param1, String param2) {
        GameGeneralInfoFragment fragment = new GameGeneralInfoFragment();
        args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_game_general_info, container, false);

        checkArguments();

        fillArray(view, mParam1);

        return view;
    }

    public void fillArray (final View view, String name) {

        dataNoticias = new ArrayList<Noticia>();

        view.findViewById(R.id.progress_bar_game_news).setVisibility(View.VISIBLE);

        Call<List<Noticia>> noticias = ApiAdapter.getApiHandler().getGameNews(name, "Bearer " + LoginActivity.tokenAccess);

        noticias.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {

                if ( response.isSuccessful() ) {

                    List<Noticia> allNews = response.body();
                    if (allNews != null) {
                        for (Noticia element : allNews) {
                            if ( element.getDescription()!= null && element.getCoverImage()!= null ) {
                                Log.d("juego: ", element.getGame());
                                dataNoticias.add(element);
                            }

                        }
                    }


                    view.findViewById(R.id.progress_bar_game_news).setVisibility(View.GONE);

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
                    //recyclerView.setLayoutParams(layoutParams);

                    recyclerView.setAdapter(newsAdapter);

                } else {
                    Log.d("ERROR", response.errorBody().toString());
                }

            }
            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
             Log.d("Error", t.getMessage());
            }
        });

    }

    public void getNewGameTitle (String name) {
        fillArray(view,name);
        args.remove(ARG_PARAM1);
        args.putString(ARG_PARAM1, name);
        this.setArguments(args);
    }

    public void checkArguments() {
        if (getArguments() != null) {
            mParam1 = args.getString(ARG_PARAM1);
            mParam2 = args.getString(ARG_PARAM2);
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
