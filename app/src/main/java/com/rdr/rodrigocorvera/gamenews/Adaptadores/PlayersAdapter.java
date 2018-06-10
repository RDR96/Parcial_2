package com.rdr.rodrigocorvera.gamenews.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdr.rodrigocorvera.gamenews.Clases.Jugador;
import com.rdr.rodrigocorvera.gamenews.Clases.Noticia;
import com.rdr.rodrigocorvera.gamenews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo Corvera on 8/6/2018.
 */

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayersViewHolder> {

    Context context;
    ArrayList<Jugador> dataJugadores;

    public PlayersAdapter (Context context, ArrayList<Jugador> dataJugadores) {
        this.context = context;
        this.dataJugadores = dataJugadores;
    }


    @Override
    public PlayersAdapter.PlayersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.cardview_jugadores, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);


        return new PlayersViewHolder(v,context.getApplicationContext(), dataJugadores);
    }

    @Override
    public void onBindViewHolder(PlayersAdapter.PlayersViewHolder holder, int position) {
        holder.playerName.setText(dataJugadores.get(position).getName());
        holder.playerBio.setText(dataJugadores.get(position).getBiografia());
        Picasso.with(context).load(dataJugadores.get(position).getAvatar()).fit().into(holder.playerImage);
    }

    @Override
    public int getItemCount() {
        return dataJugadores.size();
    }

    protected class PlayersViewHolder extends  RecyclerView.ViewHolder{

        TextView playerName;
        TextView playerBio;
        ImageView playerImage;
        LinearLayout playerCardViewContainer;
        ArrayList<Jugador> arregloJugadores = new ArrayList<Jugador>();
        Context context;

        public PlayersViewHolder(View itemView, Context context, ArrayList<Jugador> data) {
            super(itemView);
            this.context = context;
            arregloJugadores = data;
            playerName = itemView.findViewById(R.id.name_textview);
            playerBio = itemView.findViewById(R.id.bio_textview);
            playerImage = itemView.findViewById(R.id.player_image);
            playerCardViewContainer = itemView.findViewById(R.id.player_card_container);

        }

    }
}
