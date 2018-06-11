package com.rdr.rodrigocorvera.gamenews.Fragmentos;

import android.content.Context;
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

import com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity;
import com.rdr.rodrigocorvera.gamenews.Adaptadores.NewsAdapter;
import com.rdr.rodrigocorvera.gamenews.Adaptadores.PlayersAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.Jugador;
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
 * {@link GameTopPlayersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameTopPlayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameTopPlayersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public View view;
    private PlayersAdapter playersAdapter;
    private ArrayList<Jugador> dataJugador;
    private OnFragmentInteractionListener mListener;
    private static Bundle args;
    public GameTopPlayersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameTopPlayersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameTopPlayersFragment newInstance(String param1, String param2) {
        GameTopPlayersFragment fragment = new GameTopPlayersFragment();
        args = new Bundle();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game_top_players, container, false);

        if (getArguments() != null) {
            mParam1 = args.getString(ARG_PARAM1);
            mParam2 = args.getString(ARG_PARAM2);
        }


        fillArray(view, mParam1);

        return view;
    }


    public void fillArray (final View view, String name) {
        dataJugador = new ArrayList<>();
        view.findViewById(R.id.progress_bar_top_players).setVisibility(View.VISIBLE);
        Call<List<Jugador>> noticias = ApiAdapter.getApiHandler().getGamePlayers(name, "Bearer " + LoginActivity.tokenAccess);

        noticias.enqueue(new Callback<List<Jugador>>() {
            @Override
            public void onResponse(Call<List<Jugador>> call, Response<List<Jugador>> response) {

                if ( response.isSuccessful() ) {

                    List<Jugador> gamePlayers = response.body();

                    for (Jugador element : gamePlayers) {
                        Log.d("Info", element.toString());
                        if (element.getAvatar() != null) {
                            dataJugador.add(element);
                        }
                    }


                    view.findViewById(R.id.progress_bar_top_players).setVisibility(View.GONE);

                    playersAdapter = new PlayersAdapter(getContext(), dataJugador);

                    RecyclerView recyclerView = view.findViewById(R.id.players_recycler_view);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

                    recyclerView.setLayoutManager(linearLayoutManager);
                    //recyclerView.setLayoutParams(layoutParams);

                    recyclerView.setAdapter(playersAdapter);

                } else {
                    Log.d("ERROR", response.errorBody().toString());
                }

            }
            @Override
            public void onFailure(Call<List<Jugador>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    public void getNewGameTitle (String name) {
        fillArray(view, name);
        args.remove(ARG_PARAM1);
        args.putString(ARG_PARAM1, name);
        setArguments(args);
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
