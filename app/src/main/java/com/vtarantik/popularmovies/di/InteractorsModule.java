package com.vtarantik.popularmovies.di;

import com.vtarantik.popularmovies.domain.api.ApiDescription;
import com.vtarantik.popularmovies.interactor.ApiInteractorFactory;
import com.vtarantik.popularmovies.interactor.IApiInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vtarantik on 10.1.2017.
 * Interactor modules providing the needs necessary for interaction with the API
 */

@Module(
        includes = {ServiceModule.class}
)
public class InteractorsModule {
    public static final String TAG = InteractorsModule.class.getName();

    @Provides
    @Singleton
    public IApiInteractor provideApiInteractor(ApiDescription apiDescription) {
        return ApiInteractorFactory.provideApiInteractor(apiDescription);
    }
}
