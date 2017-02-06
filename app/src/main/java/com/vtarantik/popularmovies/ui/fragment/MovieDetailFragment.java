package com.vtarantik.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vtarantik.popularmovies.R;
import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.mvp.presenter.MovieDetailPresenter;
import com.vtarantik.popularmovies.mvp.view.IMovieDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusSupportFragment;

/**
 * Created by vtarantik on 10.1.2017.
 */

@RequiresPresenter(MovieDetailPresenter.class)
public class MovieDetailFragment extends NucleusSupportFragment<MovieDetailPresenter> implements IMovieDetailView{

    private static final String POSTER_PATH = "http://image.tmdb.org/t/p/w300";

    @BindView(R.id.moviedetailfragment_imageview)
    ImageView image;

    @BindView(R.id.moviedetailfragment_textview_release_date)
    TextView textViewReleaseDate;

    @BindView(R.id.moviedetailfragment_textview_rating)
    TextView textViewRating;

    @BindView(R.id.moviedetailfragment_textview_description)
    TextView textViewDescription;

    private Movie movie;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle args = getArguments();
        if(args!=null){
            movie = (Movie) args.get(MovieDetailPresenter.MOVIE_KEY);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_detail,container,false);

        ButterKnife.bind(this,v);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showMovie(movie);
    }

    @Override
    public void showMovie(Movie movie) {
        Picasso.with(getActivity())
                .load(POSTER_PATH+movie.getBackdrop())
                .fit().centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(image);

        textViewReleaseDate.setText(" "+movie.getReleaseDate().substring(0,movie.getReleaseDate().indexOf("-")));

        textViewRating.setText(" "+Double.toString(movie.getRating())+" ("+movie.getVoteCount()+"x)");

        textViewDescription.setText(movie.getOverview());
    }
}
