package com.vtarantik.popularmovies.interactor;

import com.vtarantik.popularmovies.domain.api.ApiDescription;

/**
 * Created by vtarantik on 10.1.2017.
 * API interactor factory provides the correct API interactor based on the app setup. Here we only
 * provide the real movie db interactor but moc api interactor can be easily added for testing
 */

public class ApiInteractorFactory {
    public static final String TAG = ApiInteractorFactory.class.getName();

    private ApiInteractorFactory() {
    }

    public static IApiInteractor provideApiInteractor(ApiDescription apiDescription) {
        return new ApiInteractor(apiDescription);
    }
}
