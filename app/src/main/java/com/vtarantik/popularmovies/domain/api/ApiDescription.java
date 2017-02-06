package com.vtarantik.popularmovies.domain.api;

import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.domain.model.PopularMovies;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vtarantik on 10.1.2017.
 * endpoint definition including used methods for retrieving data from the API
 */

public interface ApiDescription {
    String ENDPOINT_URL = "https://api.themoviedb.org/";

    @GET("3/movie/popular")
    Observable<PopularMovies> getMovies(
            @Query("api_key") String apiKey,
            @Query("page") int pageIndex
    );

    @GET("3/search/movie")
    Observable<PopularMovies> findMovie(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );
}
