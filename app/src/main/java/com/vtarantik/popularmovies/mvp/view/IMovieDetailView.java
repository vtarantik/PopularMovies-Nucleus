package com.vtarantik.popularmovies.mvp.view;

import com.vtarantik.popularmovies.domain.model.Movie;

/**
 * Created by vtarantik on 10.1.2017.
 */

public interface IMovieDetailView {
    public static final String TAG = IMovieDetailView.class.getName();

    public void showMovie(Movie movie);
}
