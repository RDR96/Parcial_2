package com.rdr.rodrigocorvera.gamenews.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdr.rodrigocorvera.gamenews.Clases.Noticia;
import com.rdr.rodrigocorvera.gamenews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rodrigo Corvera on 4/6/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    Context context;
    ArrayList<Noticia> dataNoticias;

    public NewsAdapter (Context context, ArrayList<Noticia> dataNoticias) {
        this.context = context;
        this.dataNoticias = dataNoticias;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.cardview_noticias, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        return new NewsViewHolder(v,context.getApplicationContext(), dataNoticias);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) { //AQUI SE PUEDE ASIGNAR EL CONTENIDO
        holder.newsTittle.setText(dataNoticias.get(position).getTitle());
        holder.newsDescription.setText(dataNoticias.get(position).getDescription());
        Picasso.with(context).load(dataNoticias.get(position).getCoverImage()).fit().into(holder.newsImage);
    }


    @Override
    public int getItemCount() {
        return dataNoticias.size();
    }

    protected class NewsViewHolder extends  RecyclerView.ViewHolder{
        TextView newsTittle;
        TextView newsDescription;
        ImageView newsImage;
        ArrayList<Noticia> arregloNoticias = new ArrayList<Noticia>();
        Context context;

        public NewsViewHolder(View itemView, Context context, ArrayList<Noticia> data) {
            super(itemView);
            this.arregloNoticias = data;
            this.context = context;
            newsTittle = itemView.findViewById(R.id.title_section);
            newsDescription = itemView.findViewById(R.id.description_section);
            newsImage = itemView.findViewById(R.id.news_image);
        }

    }


}
