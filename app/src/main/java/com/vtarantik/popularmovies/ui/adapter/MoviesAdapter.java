package com.vtarantik.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vtarantik.popularmovies.R;
import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.helper.CircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vtarantik on 10.1.2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    public static final String TAG = MoviesAdapter.class.getName();

    private static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185";
    private static final String MAX_RATING = "/10";
    private static final int IMAGE_TUMB_SIZE = 120;

    private List<Movie> movies;

    public MoviesAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false));
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindView(movies.get(position));
    }

    public void setData(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_item_textview_movie_title)
        TextView txtTitle;
        @BindView(R.id.list_item_textview_movie_rating)
        TextView txtRating;
        @BindView(R.id.list_item_movie_image)
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Movie movie) {
            txtTitle.setText(movie.getTitle());

            txtRating.setText(movie.getRating()+MAX_RATING);

            poster.setVisibility(View.VISIBLE);
            Picasso.with(itemView.getContext())
                    .load(POSTER_PATH+movie.getPoster())
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.progress_animation)
                    .into(poster);
        }

    }

}

