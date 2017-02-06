package com.vtarantik.popularmovies.mvp.presenter;

import android.os.Bundle;
import android.util.Log;

import com.vtarantik.popularmovies.App;
import com.vtarantik.popularmovies.db.dao.MovieDao;
import com.vtarantik.popularmovies.domain.model.Movie;
import com.vtarantik.popularmovies.interactor.IApiInteractor;
import com.vtarantik.popularmovies.mvp.view.IMoviesView;

import java.util.List;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vtarantik on 10.1.2017.
 */

public class MoviesPresenter extends RxPresenter<IMoviesView> {
    public static final String TAG = MoviesPresenter.class.getName();

    private static final int PAGE_SIZE = 20;

    public static final int GET_MOVIES_REQUEST = 1;

    public static final int SHOW_STORED_MOVIES = 2;

    public static final int FIND_MOVIE = 3;

    public static final int SHOW_SEARCH_RESULTS = 4;

    @Inject
    IApiInteractor apiInteractor;

    @Inject
    MovieDao movieDao;

    private List<Movie> movies;

    private boolean showProgress = true;

    private int currentPage = 1;
    private int totalPages = 0;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        App.getAppComponent().inject(this);

        if (savedState == null) {
            downloadMoviesPage(1);
        }
    }

    /*
     * Method downloads requested movie page. If there is a network error or any other problem,
     * the list of movies stored in DB is shown to the user
     */

    public void downloadMoviesPage(int pageNumber) {
        restartableLatestCache(GET_MOVIES_REQUEST,
                () -> apiInteractor.getMovies(pageNumber)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()),
                ((iMoviesView, movies1) -> {
                    if (pageNumber == 1) {
                        movieDao.clearTable();
                        Log.d(TAG,"delete table");
                    }
                    currentPage = pageNumber;
                    totalPages = movies1.getTotal_pages();
                    if (movies1.getMovies() != null && !movies1.getMovies().isEmpty()) {
                        movieDao.insertInBatch(movies1.getMovies())
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aBoolean -> viewStoredMovies());
                    }

                }),
                (iMoviesView, throwable) -> {

                    viewStoredMovies();
                }

        );

        start(GET_MOVIES_REQUEST);
    }

    public void downloadNextPage(){
        downloadMoviesPage(currentPage+1);
    }

    /*
     * Sends movies stored in DB to hte associated view
     */

    public void viewStoredMovies() {
        restartableLatestCache(SHOW_STORED_MOVIES,
                () -> movieDao.getAllMovies()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()),
                (iMoviesView, movies1) -> {
                    currentPage = movies1.size()/PAGE_SIZE;
                    iMoviesView.showData(movies1);
                },
                (iMoviesView, throwable) -> iMoviesView.showEmpty(true));

        start(SHOW_STORED_MOVIES);
    }

    /*
     * Search query resolver, movies are searched for online first, when there's an error or network
     * is unavailable, search is performed on the data stored in the DB
     */

    public void onSearchQuery(String query) {
        restartableLatestCache(FIND_MOVIE,
                () -> apiInteractor.findMovie(query)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()),
                ((iMoviesView, movies1) -> {
                    if (movies1.getMovies() != null && !movies1.getMovies().isEmpty()) {
                        iMoviesView.showData(movies1.getMovies());

                    } else {
                        iMoviesView.showEmpty(true);
                    }
                }),
                (iMoviesView, throwable) -> {
                    movieDao.searchMovies(query)
                            .subscribeOn(Schedulers.newThread())
                            .first()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(movies1 -> iMoviesView.showData(movies1), throwable1 -> iMoviesView.showEmpty(true));
                }

        );

        start(FIND_MOVIE);
    }
}
