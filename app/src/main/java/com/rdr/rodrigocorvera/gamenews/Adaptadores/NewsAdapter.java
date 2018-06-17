package com.rdr.rodrigocorvera.gamenews.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rdr.rodrigocorvera.gamenews.Actividades.InfoNewsActivity;
import com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppDatabase;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.News;
import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.CurrentUser;
import com.rdr.rodrigocorvera.gamenews.Clases.MessageHandler;
import com.rdr.rodrigocorvera.gamenews.Clases.NewsFavoriteRoot;
import com.rdr.rodrigocorvera.gamenews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rodrigo Corvera on 4/6/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    Context context;
    ArrayList<News> dataNoticias;
    boolean isFavoriteSection;
    String userId;
    Thread thread;
    String currentUserId;
    public NewsAdapter (Context context, ArrayList<News> dataNoticias, boolean isFavoriteSection) {
        this.context = context;
        this.dataNoticias = dataNoticias;
        this.isFavoriteSection = isFavoriteSection;
        setUserId();
    }

    void setUserId () {
        thread = new Thread(){
            public void run(){
                AppDatabase appDatabase = AppDatabase.getDatabaseInstance(context);
                currentUserId = appDatabase.userDao().getIdCurrentUser();
            }
        };

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
    public void onBindViewHolder(final NewsViewHolder holder, final int position) { //AQUI SE PUEDE ASIGNAR EL CONTENIDO

        if ( dataNoticias.get(position).getIsFavorite() == 1) {
            holder.favoriteButton.setImageResource(R.drawable.ic_star_24dp);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_star_border_24dp);
        }

        holder.newsTittle.setText(dataNoticias.get(position).getTitle());
        holder.newsDescription.setText(dataNoticias.get(position).getDescription());

        Picasso.with(context).load(dataNoticias.get(position).getCoverImage()).fit().into(holder.newsImage);

        holder.newsImageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataNoticias.get(position).getIsFavorite() == 1) {
                    sendIntent(position, "isFavorite");
                } else {
                    sendIntent(position, "isnotFavorite");
                }
            }
        });

        holder.newsInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataNoticias.get(position).getIsFavorite() == 1) {
                    sendIntent(position, "isFavorite");
                } else {
                    sendIntent(position, "isnotFavorite");
                }
            }
        });

        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( dataNoticias.get(position).getIsFavorite() == 1 ) {
                    removeFavorite(position, holder);
                } else {
                    addFavorite(position, holder);
                }
            }// Aqui termina la llamada



        });
    }

    public void addFavorite(final int position, final NewsViewHolder holder) {
        Call<NewsFavoriteRoot> newsFavoriteRootCall = ApiAdapter.getApiHandler().setFavoriteNews(
                LoginActivity.currentUser,
                dataNoticias.get(position).getId(),
                "Bearer " + LoginActivity.tokenAccess);
        newsFavoriteRootCall.enqueue(new Callback<NewsFavoriteRoot>() {
            @Override
            public void onResponse(Call<NewsFavoriteRoot> call, Response<NewsFavoriteRoot> response) {
                if ( response.isSuccessful() ) {
                    NewsFavoriteRoot favoriteNew = response.body();
                    if ( favoriteNew.getSuccess().equals("true") ) {
                        dataNoticias.get(position).setIsFavorite(1);
                        holder.favoriteButton.setImageResource(R.drawable.ic_star_24dp);
                        Toast.makeText(context, R.string.favorite_success , Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<NewsFavoriteRoot> call, Throwable t) {
            }
        });
    }

    public void removeFavorite(final int position, final NewsViewHolder holder) {

        Call<MessageHandler> deleteFavoriteCall = ApiAdapter.getApiHandler().deleteFavoriteNews(
                dataNoticias.get(position).getId(),
                LoginActivity.currentUser,
                "Bearer " + LoginActivity.tokenAccess);

        deleteFavoriteCall.enqueue(new Callback<MessageHandler>() {
            @Override
            public void onResponse(Call<MessageHandler> call, Response<MessageHandler> response) {
                if (response.isSuccessful()) {
                    MessageHandler messageHandler = response.body();
                    Log.d("text",messageHandler.getMessage());

                    if (messageHandler.getMessage().equals("The New has be Removed")) {
                        if (isFavoriteSection) {
                            dataNoticias.remove(position);
                            notifyDataSetChanged();
                        } else {
                            dataNoticias.get(position).setIsFavorite(0);
                            holder.favoriteButton.setImageResource(R.drawable.ic_star_border_24dp);
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<MessageHandler> call, Throwable t) {

            }
        });

    }

    public void sendIntent (int position, String isFavoriteText) {
        Intent intent = new Intent(context, InfoNewsActivity.class);
        intent.setAction(intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, dataNoticias.get(position).getTitle() + "/-"+
                dataNoticias.get(position).getCoverImage()+ "/-" +
                dataNoticias.get(position).getDescription()+ "/-" +
                dataNoticias.get(position).getBody()+ "/-" +
                isFavoriteText + "/-" +
                dataNoticias.get(position).getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return dataNoticias.size();
    }

    protected class NewsViewHolder extends  RecyclerView.ViewHolder{
        TextView newsTittle;
        TextView newsDescription;
        ImageView newsImage;
        ImageView favoriteButton;
        LinearLayout newsImageContainer;
        LinearLayout newsInfoContainer;
        ArrayList<News> arregloNoticias = new ArrayList<News>();
        Context context;

        public NewsViewHolder(View itemView, Context context, ArrayList<News> data) {
            super(itemView);
            this.arregloNoticias = data;
            this.context = context;
            newsTittle = itemView.findViewById(R.id.title_section);
            newsDescription = itemView.findViewById(R.id.description_section);
            newsImage = itemView.findViewById(R.id.news_image);
            favoriteButton = itemView.findViewById(R.id.button_favorite);
            newsImageContainer = itemView.findViewById(R.id.news_image_container);
            newsInfoContainer = itemView.findViewById(R.id.news_info_container);

        }

    }


}
