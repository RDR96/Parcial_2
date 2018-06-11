package com.rdr.rodrigocorvera.gamenews.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdr.rodrigocorvera.gamenews.Adaptadores.ViewPagerAdapter;
import com.rdr.rodrigocorvera.gamenews.Interfaces.SendText;
import com.rdr.rodrigocorvera.gamenews.MainActivity;
import com.rdr.rodrigocorvera.gamenews.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameHolderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameHolderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameHolderFragment extends Fragment implements SendText {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TabLayout tab;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private OnFragmentInteractionListener mListener;


    public GameHolderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameHolderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameHolderFragment newInstance(String param1, String param2) {
        GameHolderFragment fragment = new GameHolderFragment();
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
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_game_holder, container, false);

        tab = view.findViewById(R.id.tab_layout);

        viewPager = view.findViewById(R.id.view_pager_games_holder);

        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        viewPagerAdapter.AddFragment(GameGeneralInfoFragment.newInstance(mParam1,""), getResources().getString(R.string.general_info));
        viewPagerAdapter.AddFragment(GameTopPlayersFragment.newInstance(mParam1,""), getResources().getString(R.string.top_players));
        viewPagerAdapter.AddFragment(GameImagesFragment.newInstance(mParam1,""), getResources().getString(R.string.images));

        viewPager.setAdapter(viewPagerAdapter);
        tab.setupWithViewPager(viewPager);

        return view;
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
    public void sendData(String name) {
        viewPager.getCurrentItem();
        String tag = "android:switcher:" + R.id.view_pager_games_holder + ":" + 0;
        GameGeneralInfoFragment gameGeneralInfoFragment = (GameGeneralInfoFragment) getFragmentManager().findFragmentByTag(tag);
        gameGeneralInfoFragment.getNewGameTitle(name);

        tag = "android:switcher:" + R.id.view_pager_games_holder + ":" + 1;
        GameTopPlayersFragment gameTopPlayersFragment  = (GameTopPlayersFragment) getFragmentManager().findFragmentByTag(tag);
        gameTopPlayersFragment.getNewGameTitle(name);

        tag = "android:switcher:" + R.id.view_pager_games_holder + ":" + 2;
        GameImagesFragment gameImagesFragment = (GameImagesFragment) getFragmentManager().findFragmentByTag(tag);
        gameImagesFragment.getNewGameTitle(name);

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
