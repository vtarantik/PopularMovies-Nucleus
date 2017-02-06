package com.vtarantik.popularmovies.di;

import com.vtarantik.popularmovies.mvp.presenter.MainPresenter;
import com.vtarantik.popularmovies.mvp.presenter.MovieDetailPresenter;
import com.vtarantik.popularmovies.mvp.presenter.MoviesPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vtarantik on 10.1.2017.
 * Application component responsible for the dependency injection of the application singletons,
 * such as network client, etc.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainPresenter mainPresenter);

    void inject(MovieDetailPresenter movieDetailPresenter);

    void inject(MoviesPresenter moviesPresenter);
}
