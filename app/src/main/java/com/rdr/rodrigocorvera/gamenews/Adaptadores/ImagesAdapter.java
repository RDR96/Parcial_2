package com.rdr.rodrigocorvera.gamenews.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdr.rodrigocorvera.gamenews.Clases.Noticia;
import com.rdr.rodrigocorvera.gamenews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rodrigo Corvera on 8/6/2018.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>{

    Context context;
    ArrayList<Noticia> dataImagenes;

    public ImagesAdapter (Context context, ArrayList<Noticia> dataImagenes) {
        this.context = context;
        this.dataImagenes = dataImagenes;
    }

    @Override
    public ImagesAdapter.ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.cardview_imagenes, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        return new ImagesAdapter.ImagesViewHolder(v,context.getApplicationContext(), dataImagenes);
    }

    @Override
    public void onBindViewHolder(ImagesAdapter.ImagesViewHolder holder, int position) {
        Picasso.with(context).load(dataImagenes.get(position).getCoverImage()).fit().into(holder.gameImage);
    }

    @Override
    public int getItemCount() {
        return dataImagenes.size();
    }

    protected class ImagesViewHolder extends  RecyclerView.ViewHolder{
        ImageView gameImage;
        ArrayList<Noticia> arregloNoticias = new ArrayList<Noticia>();
        Context context;

        public ImagesViewHolder(View itemView, Context context, ArrayList<Noticia> data) {
            super(itemView);
            this.arregloNoticias = data;
            this.context = context;
            gameImage = itemView.findViewById(R.id.only_image_container);
        }

    }
}
