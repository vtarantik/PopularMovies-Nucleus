package com.vtarantik.popularmovies;

import android.app.Application;

import com.vtarantik.popularmovies.di.AppComponent;
import com.vtarantik.popularmovies.di.AppModule;
import com.vtarantik.popularmovies.di.DaggerAppComponent;

/**
 * Created by vtarantik on 10.1.2017.
 */

public class App extends Application {
    public static final String TAG = App.class.getName();

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Dagger
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
