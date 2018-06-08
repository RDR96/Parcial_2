package com.rdr.rodrigocorvera.gamenews.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdr.rodrigocorvera.gamenews.Actividades.InfoNewsActivity;
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
    public void onBindViewHolder(NewsViewHolder holder, final int position) { //AQUI SE PUEDE ASIGNAR EL CONTENIDO
        holder.newsTittle.setText(dataNoticias.get(position).getTitle());
        holder.newsDescription.setText(dataNoticias.get(position).getDescription());

        Picasso.with(context).load(dataNoticias.get(position).getCoverImage()).fit().into(holder.newsImage);

        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        Log.d("params", lp.toString());

        if (position%3 == 0) {
            StaggeredGridLayoutManager.LayoutParams sglp  = new StaggeredGridLayoutManager.LayoutParams(lp.width, lp.height);
            //StaggeredGridLayoutManager.LayoutParams sglp  = (StaggeredGridLayoutManager.LayoutParams) lp;
            sglp.setFullSpan(true);
            holder.itemView.setLayoutParams(sglp);
        }

        holder.newsImageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoNewsActivity.class);
                intent.setAction(intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, dataNoticias.get(position).getTitle() + "/-"+
                                dataNoticias.get(position).getCoverImage()+ "/-" +
                                dataNoticias.get(position).getDescription()+ "/-" +
                                dataNoticias.get(position).getBody());
                context.startActivity(intent);
            }
        });

        holder.newsInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoNewsActivity.class);
                intent.setAction(intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, dataNoticias.get(position).getTitle() + "/-"+
                        dataNoticias.get(position).getCoverImage()+ "/-" +
                        dataNoticias.get(position).getDescription()+ "/-" +
                        dataNoticias.get(position).getBody());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return dataNoticias.size();
    }

    protected class NewsViewHolder extends  RecyclerView.ViewHolder{
        TextView newsTittle;
        TextView newsDescription;
        ImageView newsImage;
        LinearLayout newsImageContainer;
        LinearLayout newsInfoContainer;
        ArrayList<Noticia> arregloNoticias = new ArrayList<Noticia>();
        Context context;

        public NewsViewHolder(View itemView, Context context, ArrayList<Noticia> data) {
            super(itemView);
            this.arregloNoticias = data;
            this.context = context;
            newsTittle = itemView.findViewById(R.id.title_section);
            newsDescription = itemView.findViewById(R.id.description_section);
            newsImage = itemView.findViewById(R.id.news_image);
            newsImageContainer = itemView.findViewById(R.id.news_image_container);
            newsInfoContainer = itemView.findViewById(R.id.news_info_container);
        }

    }


}
