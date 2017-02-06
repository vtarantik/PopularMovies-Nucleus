package com.vtarantik.popularmovies.di;

import android.app.Application;
import android.view.LayoutInflater;

import com.hannesdorfmann.sqlbrite.dao.DaoManager;
import com.vtarantik.popularmovies.App;
import com.vtarantik.popularmovies.db.dao.MovieDao;
import com.vtarantik.popularmovies.helper.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtarantik on 10.1.2017.
 * Dagger module providing application class
 */

@Module(
        includes = {
                InteractorsModule.class
        }
)
public class AppModule {
    public static final String TAG = AppModule.class.getName();

    private final App app;
    private final MovieDao movieDao;

    public AppModule(App app) {
        this.app = app;
        movieDao = new MovieDao();
        DaoManager.with(app)
                .version(Constants.DB_VERSION)
                .databaseName(Constants.DB_NAME)
                .logging(false)
                .add(movieDao)
                .build();
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(app);
    }

    @Provides
    @Singleton
    public MovieDao provideMovieDao() {
        return movieDao;
    }

}
