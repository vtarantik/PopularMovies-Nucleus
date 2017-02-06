package com.vtarantik.popularmovies.interactor;

import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.domain.model.PopularMovies;

import java.util.List;

import rx.Observable;

/**
 * Created by vtarantik on 10.1.2017.
 */

public interface IApiInteractor {
    public Observable<PopularMovies> getMovies(int page);

    public Observable<PopularMovies> findMovie(String query);
}
