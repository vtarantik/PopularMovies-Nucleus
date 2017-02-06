package com.vtarantik.popularmovies.interactor;

import com.vtarantik.popularmovies.BuildConfig;
import com.vtarantik.popularmovies.R;
import com.vtarantik.popularmovies.domain.api.ApiDescription;
import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.domain.model.PopularMovies;

import java.util.List;

import rx.Observable;

/**
 * Created by vtarantik on 10.1.2017.
 * Api interactor which provides the needs necessary to work with the real API
 */

public class ApiInteractor implements IApiInteractor{
    public static final String TAG = ApiInteractor.class.getName();
    private final ApiDescription apiDescription;

    public ApiInteractor(ApiDescription apiDescription) {
        this.apiDescription = apiDescription;
    }

    @Override
    public Observable<PopularMovies> getMovies(int page) {
        return this.apiDescription.getMovies(BuildConfig.MOVIEDB_API_KEY,page);
    }

    @Override
    public Observable<PopularMovies> findMovie(String query) {
        return this.apiDescription.findMovie(BuildConfig.MOVIEDB_API_KEY,query);
    }

}
