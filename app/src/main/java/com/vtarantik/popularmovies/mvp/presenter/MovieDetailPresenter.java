package com.vtarantik.popularmovies.mvp.presenter;

import android.os.Bundle;

import com.vtarantik.popularmovies.App;
import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.mvp.view.IMovieDetailView;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vtarantik on 10.1.2017.
 */

public class MovieDetailPresenter extends RxPresenter<IMovieDetailView>   {
    public static final String TAG = MovieDetailPresenter.class.getName();
    public  static final String MOVIE_KEY = "movie";

    public static Bundle getArguments(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_KEY, movie);
        return args;
    }


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        App.getAppComponent().inject(this);

    }

}
