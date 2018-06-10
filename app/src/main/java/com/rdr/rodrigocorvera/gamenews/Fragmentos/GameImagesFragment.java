package com.rdr.rodrigocorvera.gamenews.Fragmentos;

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

import com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity;
import com.rdr.rodrigocorvera.gamenews.Adaptadores.ImagesAdapter;
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
 * {@link GameImagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameImagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameImagesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Noticia> dataImagenes;
    private ImagesAdapter imagesAdapter;
    private OnFragmentInteractionListener mListener;

    public GameImagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameImagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameImagesFragment newInstance(String param1, String param2) {
        GameImagesFragment fragment = new GameImagesFragment();
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
        View view = inflater.inflate(R.layout.fragment_game_images, container, false);

        fillArray(view);

        return view;
    }

    public void fillArray (final View view) {

        dataImagenes = new ArrayList<Noticia>();

        Call<List<Noticia>> noticias = ApiAdapter.getApiHandler().getGameNews(mParam1, "Bearer " + LoginActivity.tokenAccess);

        noticias.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {

                if ( response.isSuccessful() ) {

                    List<Noticia> allNews = response.body();
                    if (allNews != null) {
                        for (Noticia element : allNews) {

                            if ( element.getCoverImage()!= null ) {
                                Log.d("juego: ", element.getGame());
                                dataImagenes.add(element);
                            }

                        }
                    }


                    view.findViewById(R.id.progress_bar_game_images).setVisibility(View.GONE);

                    imagesAdapter = new ImagesAdapter(getContext(), dataImagenes);

                    RecyclerView recyclerView = view.findViewById(R.id.images_recycler_view);

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);

                    recyclerView.setLayoutManager(gridLayoutManager);
                    //recyclerView.setLayoutParams(layoutParams);

                    recyclerView.setAdapter(imagesAdapter);

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
